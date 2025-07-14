package it.unicam.cs.ids.context.catalog.infrastructure.web;

import it.unicam.cs.ids.context.catalog.infrastructure.web.dtos.BundleDTO;
import it.unicam.cs.ids.context.catalog.infrastructure.web.dtos.requests.UpdateBundleRequest;
import it.unicam.cs.ids.context.company.domain.models.Company;
import it.unicam.cs.ids.context.identity.application.services.AuthService;
import it.unicam.cs.ids.shared.application.Messages;
import it.unicam.cs.ids.shared.infrastructure.web.factories.ApiResponseFactory;
import it.unicam.cs.ids.shared.infrastructure.web.responses.ApiResponse;
import it.unicam.cs.ids.context.catalog.infrastructure.web.dtos.requests.CreateBundleRequest;
import it.unicam.cs.ids.context.catalog.application.services.BundleService;
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
}
