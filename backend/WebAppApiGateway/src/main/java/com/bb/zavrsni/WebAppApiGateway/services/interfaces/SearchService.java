package com.bb.zavrsni.WebAppApiGateway.services.interfaces;

import com.bb.zavrsni.WebAppApiGateway.models.dto.SearchResponseDto;

import java.util.List;

public interface SearchService {

    SearchResponseDto searchAllContent(String query, int page, int size);

    List<?> searchSpecificContent(String what, String query, int page, int size);

}
