package it.unicam.cs.ids.api.controllers;

import it.unicam.cs.ids.api.responses.models.ApiResponse;
import it.unicam.cs.ids.dtos.requests.CreateBundleRequest;
import it.unicam.cs.ids.entities.Bundle;
import it.unicam.cs.ids.services.BundleService;
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
    @Autowired
    public BundleController(BundleService bundleService) {
        this.bundleService= bundleService;
    }

    @PostMapping
    ResponseEntity<ApiResponse<Bundle>> createBundle(@RequestBody CreateBundleRequest request) {
        return new ResponseEntity<>(bundleService.createBundle(request), HttpStatus.CREATED);
    }
}
