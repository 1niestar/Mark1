package com.iniestar.mark1.config.http;

import com.iniestar.mark1.controller.ext.ApiController;
import com.iniestar.mark1.utils.Tool;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.Map;

@Slf4j
public class ProxyEngine {
    public static final int TIMEOUT_SEC = 30*1000;
    public static HttpHeaders defaultHeaders (){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        return httpHeaders;
    }

    public static String send(String uri, String method, Map<String, Object> params) {

        String body = null;
        switch (method) {
            case "POST" :
                body = post(uri, params);
                break;
            case "GET" :
                body = get(uri, params);
                break;
        }

        return body;
    }

    public static final String post(String uri, Map<String, Object> params){
        return post(uri, null, params);
    }

    /***
     * Send to Json by Http Post method
     * @param uri
     * @return
     * @throws Exception
     */
    public static String post(String uri, Map<String, String> headerMap,  Map<String, Object> params) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uri);
        if(params != null) {
            params.keySet().forEach(s -> builder.queryParam(s, params.get(s)));
        }

        HttpHeaders httpHeaders = defaultHeaders();
        RestTemplate res = getRestTemplate(headerMap, httpHeaders);
        ResponseEntity<String> response = getStringResponseEntity(HttpMethod.POST, builder, res, params, httpHeaders);
        //Response data 받는 부분
        return response.getBody();
    }

    private static RestTemplate getRestTemplate(Map<String, String> headerMap, HttpHeaders httpHeaders) {
        RestTemplate res = new RestTemplate(getFactory());
        if (headerMap != null) {
            headerMap.keySet().forEach(key -> {
                if (!Tool.isNullOrEmpty(headerMap.get(key))) httpHeaders.set(key, headerMap.get(key));
            });
        }
        return res;
    }

    public static String get(String url, Map<String, Object> params) {
        return get(url, null, params);
    }

    public static String get(String uri, Map<String, String> headerMap, Map<String, Object> params)  {
        log.debug("sendGetJson");
        log.debug(uri);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uri);
        if(params != null) {
            params.keySet().stream().forEach(s -> builder.queryParam(s, params.get(s)));
        }

        HttpHeaders httpHeaders = defaultHeaders();
        RestTemplate res = getRestTemplate(headerMap, httpHeaders);
        ResponseEntity<String> response = getStringResponseEntity(HttpMethod.GET, builder, res, null, httpHeaders);

        //Response data 받는 부분
        return response.getBody();
    }

    public static String put(String url, Map<String, Object> params) throws Exception {
        return put(url, null, params);
    }

    public static String put(String uri, Map<String, String> headerMap, Map<String, Object> params) throws Exception {
        log.debug("sendGetJson");
        log.debug(uri);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uri);
        if(params != null) {
            params.keySet().stream().forEach(s -> builder.queryParam(s, params.get(s)));
        }

        HttpHeaders httpHeaders = defaultHeaders();
        RestTemplate res = getRestTemplate(headerMap, httpHeaders);
        ResponseEntity<String> response = getStringResponseEntity(HttpMethod.PUT, builder, res, null, httpHeaders);

        //Response data 받는 부분
        return response.getBody();
    }

    public static String del(String url, Map<String, Object> params) throws Exception {
        return del(url, null, params);
    }

    public static String del(String uri, Map<String, String> headerMap, Map<String, Object> params) throws Exception {
        log.debug("sendGetJson");
        log.debug(uri);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uri);
        if(params != null) {
            params.keySet().stream().forEach(s -> builder.queryParam(s, params.get(s)));
        }

        HttpHeaders httpHeaders = defaultHeaders();
        RestTemplate res = getRestTemplate(headerMap, httpHeaders);
        ResponseEntity<String> response = getStringResponseEntity(HttpMethod.DELETE, builder, res, null, httpHeaders);

        //Response data 받는 부분
        return response.getBody();
    }

    private static HttpComponentsClientHttpRequestFactory getFactory() {
        HttpComponentsClientHttpRequestFactory requestFactory = null;

//        TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;
        try {
            requestFactory = new HttpComponentsClientHttpRequestFactory();
            requestFactory.setConnectTimeout(TIMEOUT_SEC);
            requestFactory.setReadTimeout(TIMEOUT_SEC);

            SSLContext sslContext = SSLContextBuilder.create().loadTrustMaterial(new TrustSelfSignedStrategy()).build();

            HostnameVerifier allowAllHosts = new NoopHostnameVerifier();

            SSLConnectionSocketFactory connectionFactory = new SSLConnectionSocketFactory(sslContext, allowAllHosts);
//
//            SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom()
//                    .loadTrustMaterial(new TrustSelfSignedStrategy())
//                    .build();
//
//            SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext);

            CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(connectionFactory).build();
            requestFactory.setHttpClient(httpClient);
        } catch (NoSuchAlgorithmException | KeyManagementException | KeyStoreException e) {
            e.printStackTrace();
        }
        return requestFactory;
    }

    private static ResponseEntity<String> getStringResponseEntity(HttpMethod method, UriComponentsBuilder builder, RestTemplate res,
                                                                  Map<String, Object> params, HttpHeaders httpHeaders) {
        HttpEntity<Map<String, Object>> entity = new HttpEntity(params, httpHeaders);

        ResponseEntity<String> response = res.exchange(
                builder.toUriString(),
                method,
                entity,
                String.class);
        return response;
    }
}
