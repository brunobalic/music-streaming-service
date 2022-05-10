package com.bb.zavrsni.WebAppApiGateway.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RestTemplateInstance {

    private final RestTemplate restTemplate;

    @Autowired
    public RestTemplateInstance(RestTemplateBuilder restTemplateBuilder,
                                RestTemplateResponseErrorHandler restTemplateResponseErrorHandler) {
        this.restTemplate = restTemplateBuilder
                .errorHandler(restTemplateResponseErrorHandler)
                .build();
    }


    public RestTemplate getRestTemplate() {
        return restTemplate;
    }

}
