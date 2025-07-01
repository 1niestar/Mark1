package com.iniestar.mark1.controller.web;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iniestar.mark1.db.entity.ApiInfo;
import com.iniestar.mark1.db.repo.ApiInfoRepository;
import com.iniestar.mark1.utils.Tool;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
public class GatewayController {

    ApiInfoRepository apiInfoRepository;

    @PostMapping(value = "add/{uri}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String addUri(@PathVariable String uri, @RequestHeader Map<String, String> headers, @RequestBody Map<String, String> params) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        ApiInfo apiInfo = new ApiInfo();
        apiInfo.setUri(uri);

        String paramString = objectMapper.writeValueAsString(apiInfo);
        apiInfo.setParams(paramString);

        String accessToken = headers.get("access-token");
        if(!Tool.isNullOrEmpty(accessToken)) {
            apiInfo.setAccessToken(accessToken);
        }

        apiInfoRepository.save(apiInfo);
        return uri;
    }

    @PostMapping(value = "getAccessToken/{uri}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String getAccessToken(HttpServletRequest request, @PathVariable String uri, @RequestHeader Map<String, String> headers, @RequestBody Map<String, String> params) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        ApiInfo apiInfo = new ApiInfo();
        apiInfo.setUri(uri);

        String paramString = objectMapper.writeValueAsString(apiInfo);
        apiInfo.setParams(paramString);

        String accessToken = headers.get("access-token");
        if(!Tool.isNullOrEmpty(accessToken)) {
            apiInfo.setAccessToken(accessToken);
        }

        apiInfoRepository.save(apiInfo);
        return uri;
    }

}
