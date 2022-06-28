package com.iniestar.mark1.controller.ext;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.iniestar.mark1.config.exception.BusinessException;
import com.iniestar.mark1.config.http.ProxyEngine;
import com.iniestar.mark1.constant.ApiReturnCode;
import com.iniestar.mark1.constant.AuthConstant;
import com.iniestar.mark1.db.entity.ApiInfo;
import com.iniestar.mark1.db.entity.RefreshToken;
import com.iniestar.mark1.service.ApiService;
import com.iniestar.mark1.service.RefreshTokenService;
import com.iniestar.mark1.utils.TokenUtils;
import com.iniestar.mark1.utils.Tool;
import com.iniestar.mark1.structure.ApiReturn;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class ApiController {

    @Autowired
    ApiService apiService;

    @Autowired
    RefreshTokenService refreshTokenService;

    @PostMapping("/api/register/{uri}/{method}")
    public ApiReturn API_register(HttpServletRequest request,
                                  HttpServletResponse response,
                                  @PathVariable String uri,
                                  @PathVariable String method,
                                  @RequestHeader Map<String, String> headers,
                                  @RequestBody Map<String, Boolean> params) {

        // 중복된 URI일 경우 에러 처리
     //   boolean bIsEmpty = apiService.isEmptyWithUri(uri);
     //   if(!bIsEmpty) throw new BusinessException(ApiReturnCode.ERROR_DUPLICATION_DATA);

            ApiReturn apiReturn = new ApiReturn();
            ApiInfo entity = new ApiInfo();
            entity.setUri(uri);
            if(!params.isEmpty()) {
                JsonObject jsonObject = new JsonObject();

                // Map<ParamName, nullable>
                for(Map.Entry<String, Boolean> e : params.entrySet()) {
                    jsonObject.addProperty(e.getKey(), e.getValue());
                }
                entity.setParams(new Gson().toJson(jsonObject));
            }
            if(!headers.isEmpty()) {
                // Map<ParamName, nullable>
                JsonObject jsonObject = new JsonObject();

                for(Map.Entry<String, String> e : headers.entrySet()) {
                    jsonObject.addProperty(e.getKey(), e.getValue());
                }
                entity.setHeaders(new Gson().toJson(jsonObject));
            }

            String clientIp = getClientIp(request);
            entity.setClientIp(clientIp);

            entity.setChgDate(Tool.getTimestamp().toLocalDateTime());
            entity.setCrtDate(Tool.getTimestamp().toLocalDateTime());

            // 1. JWT 생성
            String randomString = Tool.randomByteString(32);
            entity.setSecretKey(randomString);
            String accessToken = TokenUtils.generateJwtToken(uri, clientIp, randomString);
            entity.setAccessToken(accessToken);
            if(null != method) entity.setMethod(method.toUpperCase());

            // 2. REFRESH TOKEN 생성
            RefreshToken refreshToken = refreshTokenService.generateRefreshToken(uri);
            entity.setRefreshToken(refreshToken);
            apiService.save(entity);

            response.addHeader(AuthConstant.ACCESS_TOKEN, accessToken);
            response.addHeader(AuthConstant.REFRESH_TOKEN, refreshToken.getToken());
            apiReturn.setReturnCode(ApiReturnCode.SUCCESS);

            return apiReturn;
    }

    @RequestMapping(value = "/api/request/{uri}", method = {RequestMethod.POST, RequestMethod.GET})
    public ApiReturn API_request(HttpServletRequest request,
                                 @PathVariable("uri") String uri,
                                 @RequestHeader(AuthConstant.ACCESS_TOKEN) String accessToken,
                                 @RequestHeader(AuthConstant.REFRESH_TOKEN) String refreshToken,
                                 @RequestBody Map<String, Object> params) {

        // 1. 등록된 URI가 없는 경우 에러 처리
        ApiInfo apiInfo = apiService.getOneByUriAndMethod(uri, request.getMethod());
        if(null == apiInfo) throw new BusinessException(ApiReturnCode.ERROR_API_NOT_EXIST);

        // 2. 요청한 JWT Token 값이 동일하지 않은 경우
        if(false == TokenUtils.isValidToken(accessToken, apiInfo.getSecretKey()))
            throw new BusinessException(ApiReturnCode.ERROR_API_INVALID_REQUEST_KEY);

        // 3. Proxy 전송
        Type token = new TypeToken<Map<String, String>>(){}.getType();
        Map<String, String> headers =  new Gson().fromJson(apiInfo.getHeaders(), token);
        String body = ProxyEngine.send(uri, request.getMethod().toUpperCase(), params);
      //  ProxyEngine.post(apiInfo.getUri(), headers, params);

        ApiReturn apiReturn = new ApiReturn();
        apiReturn.setBody(body);
        apiReturn.setReturnCode(ApiReturnCode.SUCCESS);

        return apiReturn;
    }


    private String getClientIp(HttpServletRequest req) {

        String clientIp = req.getHeader("X-Forwarded-For");
        if (clientIp == null || clientIp.length() == 0 || "unknown".equalsIgnoreCase(clientIp)) {
            clientIp = req.getHeader("Proxy-Client-IP");
        }
        if (clientIp == null || clientIp.length() == 0 || "unknown".equalsIgnoreCase(clientIp)) {
            clientIp = req.getHeader("WL-Proxy-Client-IP");
        }
        if (clientIp == null || clientIp.length() == 0 || "unknown".equalsIgnoreCase(clientIp)) {
            clientIp = req.getHeader("HTTP_CLIENT_IP");
        }
        if (clientIp == null || clientIp.length() == 0 || "unknown".equalsIgnoreCase(clientIp)) {
            clientIp = req.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (clientIp == null || clientIp.length() == 0 || "unknown".equalsIgnoreCase(clientIp)) {
            clientIp = req.getRemoteAddr();
        }

        return clientIp;

    }

}
