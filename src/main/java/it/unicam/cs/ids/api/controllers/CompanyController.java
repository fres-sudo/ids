package it.unicam.cs.ids.api.controllers;

import it.unicam.cs.ids.api.responses.models.ApiResponse;
import it.unicam.cs.ids.dtos.requests.CreateBundleRequest;
import it.unicam.cs.ids.dtos.requests.CreateCompanyRequest;
import it.unicam.cs.ids.entities.Bundle;
import it.unicam.cs.ids.entities.Company;
import it.unicam.cs.ids.services.BundleService;
import it.unicam.cs.ids.services.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("companies")
public class CompanyController {

    private final CompanyService companyService;
    @Autowired
    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostMapping
    ResponseEntity<ApiResponse<Company>> createBundle(@RequestBody CreateCompanyRequest request) {
        return new ResponseEntity<>(companyService.createCompany(request), HttpStatus.CREATED);
    }
}
