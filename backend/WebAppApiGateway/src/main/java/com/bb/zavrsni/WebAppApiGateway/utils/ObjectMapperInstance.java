package com.bb.zavrsni.WebAppApiGateway.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
public class ObjectMapperInstance {

    @Getter
    private final ObjectMapper objectMapper;

    public ObjectMapperInstance() {
        this.objectMapper = new ObjectMapper();
    }

}
