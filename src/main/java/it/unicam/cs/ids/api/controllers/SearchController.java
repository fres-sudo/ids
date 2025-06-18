package it.unicam.cs.ids.api.controllers;

import it.unicam.cs.ids.api.responses.models.PaginatedApiResponse;
import it.unicam.cs.ids.services.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("search")
public class SearchController {

    private final SearchService searchService;
    @Autowired
    public SearchController(SearchService searchService) {
        this.searchService= searchService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<PaginatedApiResponse<?>> search(@Param("type") String type) {
        PaginatedApiResponse<?> response = searchService.search(type, null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
