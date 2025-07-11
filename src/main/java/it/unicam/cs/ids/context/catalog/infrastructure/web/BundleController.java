package it.unicam.cs.ids.context.catalog.infrastructure.web;

import it.unicam.cs.ids.shared.application.Messages;
import it.unicam.cs.ids.shared.infrastructure.web.factories.ApiResponseFactory;
import it.unicam.cs.ids.shared.infrastructure.web.responses.ApiResponse;
import it.unicam.cs.ids.context.catalog.infrastructure.web.dtos.requests.CreateBundleRequest;
import it.unicam.cs.ids.context.catalog.domain.model.Bundle;
import it.unicam.cs.ids.context.catalog.application.services.BundleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("bundles")
public class BundleController {

    private final BundleService bundleService;
    private final ApiResponseFactory responseFactory;
    @Autowired
    public BundleController(BundleService bundleService, ApiResponseFactory responseFactory) {
        this.bundleService= bundleService;
        this.responseFactory = responseFactory;
    }

    @PostMapping
    ResponseEntity<ApiResponse<Bundle>> createBundle(@RequestBody CreateBundleRequest request) {
        ApiResponse<Bundle> response = responseFactory.createSuccessResponse(
                Messages.Success.BUNDLE_CREATED,
                bundleService.createBundle(request)
        );
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
