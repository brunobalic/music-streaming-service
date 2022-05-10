package com.bb.zavrsni.WebAppApiGateway.controllers;

import com.bb.zavrsni.WebAppApiGateway.models.dto.SearchResponseDto;
import com.bb.zavrsni.WebAppApiGateway.services.interfaces.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/search")
public class ContentSearchController {

    private final SearchService searchService;

    @Autowired
    public ContentSearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    /**
     * Search all content.
     *
     * @param searchString Query string
     * @return SearchResponseDto - containing list of queries of all content (artists, albums, playlists, tracks, ...)
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<SearchResponseDto> searchAllContent(
            @RequestParam("q") String searchString,
            @RequestParam(value = "p", required = false) Optional<Integer> page,
            @RequestParam(value = "s", required = false) Optional<Integer> size,
            Authentication authentication,
            @AuthenticationPrincipal Jwt jwt) {

        System.out.println("getAuthorities() - " + authentication.getAuthorities());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(searchService.searchAllContent(searchString, page.orElse(0), size.orElse(5)));
    }


    /**
     * Search specific content.
     *
     * @param what         content type (exact specific value needed)
     * @param searchString query string
     * @param page         page
     * @param size         page size
     * @return List of objects of requested content type
     */
    @RequestMapping(value = "/{what}", method = RequestMethod.GET)
    public ResponseEntity<List<?>> searchSpecificContent(
            @PathVariable("what") String what,
            @RequestParam("q") String searchString,
            @RequestParam(value = "p", required = false) Optional<Integer> page,
            @RequestParam(value = "s", required = false) Optional<Integer> size) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(searchService.searchSpecificContent(what, searchString, page.orElse(0), size.orElse(5)));
    }

}
