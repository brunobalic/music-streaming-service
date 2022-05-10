package com.bb.zavrsni.WebAppApiGateway.utils;

import com.bb.zavrsni.WebAppApiGateway.exceptions.UniversalException;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

@Component
public class RestTemplateResponseErrorHandler implements ResponseErrorHandler {

    private final ObjectMapperInstance objectMapper;

    @Autowired
    public RestTemplateResponseErrorHandler(ObjectMapperInstance objectMapperInstance) {
        this.objectMapper = objectMapperInstance;
    }

    @Override
    public boolean hasError(ClientHttpResponse clientHttpResponse) throws IOException {
        return (clientHttpResponse.getStatusCode().series() == HttpStatus.Series.CLIENT_ERROR ||
                clientHttpResponse.getStatusCode().series() == HttpStatus.Series.SERVER_ERROR);
    }

    @Override
    public void handleError(ClientHttpResponse clientHttpResponse) throws IOException {
        if (clientHttpResponse.getStatusCode().series() == HttpStatus.Series.CLIENT_ERROR) {

            // * Kada se InputStream jedan put consume-a ne moze de vise citat
            // * bude exc. java.io.IOException: Attempted read from closed stream.
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(clientHttpResponse.getBody()));
            String httpBodyResponse = bufferedReader.lines().collect(Collectors.joining());

            System.out.println(httpBodyResponse);

            JsonNode jsonNode = objectMapper.getObjectMapper().readTree(httpBodyResponse);

            // mogao sam mapirati u konkretan object...
            // ali ne treba mi nist drugo pa cu samo procitat message

            if (jsonNode.has("message")) {
                throw new UniversalException(
                        jsonNode.get("message").isTextual() ? jsonNode.get("message").textValue() : jsonNode.get("message").toString()
                );
            } else {
                throw new UniversalException("Unknown error occurred.");
            }

        } else if (clientHttpResponse.getStatusCode().series() == HttpStatus.Series.SERVER_ERROR) {
            System.out.println("server error");
            throw new UniversalException("Server error");
        }
    }

}
