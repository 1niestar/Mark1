package com.iniestar.mark1.config.http;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Map;

@Component
public class HttpUtil {

    public ResponseEntity<String> sendPost(String url, Map<String, String> headers, Object params) {
        String res = WebClient.create(url)
                .post()
                .headers(header -> headers.forEach(header::add))
                .bodyValue(params)
                .retrieve()
                .bodyToMono(String.class).block();

        return ResponseEntity.ok(res);
    }

    public ResponseEntity<String> sendGet(String url, Map<String, String> headers, Map<String, Object> params) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(url).queryParam(params.toString());
        URI uri = uriBuilder.build().toUri();
        String res = WebClient.create(url)
                .get()
                .uri(uri)
                .headers(header -> headers.forEach(header::add))
                .retrieve()
                .bodyToMono(String.class).block();

        return ResponseEntity.ok(res);
    }

    public ResponseEntity<String> send(String url, String method, Map<String, String> headers, Map<String, Object> params) {
        switch (method) {
            case "GET" :
                return sendGet(url, headers, params);
            case "POST" :
                return sendPost(url, headers, params);
            default :
                return null;
        }
    }
}
