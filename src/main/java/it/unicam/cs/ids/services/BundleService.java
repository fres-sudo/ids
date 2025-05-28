package it.unicam.cs.ids.services;

import it.unicam.cs.ids.api.responses.models.ApiResponse;
import it.unicam.cs.ids.dtos.BundleDTO;
import it.unicam.cs.ids.dtos.requests.CreateBundleRequest;

public interface BundleService {
    ApiResponse<BundleDTO> createBundle(CreateBundleRequest request);
}
