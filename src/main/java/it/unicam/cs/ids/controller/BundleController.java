package it.unicam.cs.ids.controller;

import it.unicam.cs.ids.dto.BundleDTO;
import it.unicam.cs.ids.web.requests.company.UpdateBundleRequest;
import it.unicam.cs.ids.model.Company;
import it.unicam.cs.ids.services.AuthService;
import it.unicam.cs.ids.shared.application.Messages;
import it.unicam.cs.ids.shared.infrastructure.api.factories.ApiResponseFactory;
import it.unicam.cs.ids.shared.infrastructure.api.ApiResponse;
import it.unicam.cs.ids.web.requests.company.CreateBundleRequest;
import it.unicam.cs.ids.services.BundleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RequestMapping("bundles")
public class BundleController {

    private final BundleService bundleService;
    private final AuthService authService;
    private final ApiResponseFactory responseFactory;

    @PostMapping
    @PreAuthorize("hasRole('DISTRIBUTOR')")
    ApiResponse<BundleDTO> createBundle(@RequestBody CreateBundleRequest request) {
        System.out.println("Creating bundle with request: " + request);
        Company authedCompany = authService.getAuthenticatedCompany();
        request.setDistributorId(authedCompany.getId());
        return responseFactory.createSuccessResponse(
                Messages.Success.BUNDLE_CREATED,
                bundleService.createBundle(request)
        );
    }

    @PatchMapping()
    @PreAuthorize("hasRole('DISTRIBUTOR')")
    ApiResponse<BundleDTO> updateBundle(@RequestBody UpdateBundleRequest request) {
        return responseFactory.createSuccessResponse(
                Messages.Success.BUNDLE_UPDATED,
                bundleService.updateBundle(request)
        );
    }

    @DeleteMapping()
    @PreAuthorize("hasRole('DISTRIBUTOR')")
    ResponseEntity<ApiResponse<Void>> deleteBundle(@RequestParam("bundleId") Long bundleId) {
        bundleService.deleteBundle(bundleId, authService.getAuthenticatedCompany().getId());
        ApiResponse<Void> response = responseFactory.createSuccessResponse(
                Messages.Success.BUNDLE_DELETED, null
        );
        return new ResponseEntity<>(response, HttpStatus.OK);

    }
}
