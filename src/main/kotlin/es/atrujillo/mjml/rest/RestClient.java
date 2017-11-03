package es.atrujillo.mjml.rest;

import org.jetbrains.annotations.Nullable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

public interface RestClient<R> {

    <T> ResponseEntity<T> get(String path, ParameterizedTypeReference<T> type, @Nullable MultiValueMap<String, String> params, @Nullable HttpHeaders headers);

    <T> ResponseEntity<T> post(R request, String path, ParameterizedTypeReference<T> type, @Nullable MultiValueMap<String, String> params, @Nullable HttpHeaders headers);

    <T> ResponseEntity<T> patch(R request, String path, ParameterizedTypeReference<T> type, @Nullable MultiValueMap<String, String> params, @Nullable HttpHeaders headers);
}
