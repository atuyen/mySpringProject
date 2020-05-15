package com.seasolutions.stock_management.service.implementations;


import com.fasterxml.jackson.core.type.TypeReference;
import com.seasolutions.stock_management.model.entity.Category;
import com.seasolutions.stock_management.util.JsonUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Log4j2
@Service("assessioHttpClient")
public class AssessioHttpClient {
    private final boolean logHttpTraffic = false;

    @Value("${assessio.api}")
    private String assessioApi;

    @Value("${assessio.base64EncodedPassword}")
    private String assessioBase64Authorization; //username:password in BASE64 encoding

    @Autowired
    private RestTemplate template;





    private void logResult(final String resultBody) {
        if (logHttpTraffic) {
            log.debug("resultBody: \n" + JsonUtils.prettyPrint(resultBody));
        }
    }

    private String toUri(final String path) {
        if (path.startsWith("/")) {
            return assessioApi + path;
        }
        return assessioApi + "/" + path;
    }

    private String uriWithParams(final String uri, final Map<String, Object> params) {
        final UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(uri);
        if (params != null) {
            for (final Map.Entry<String, Object> entry : params.entrySet()) {
                builder.queryParam(entry.getKey(), entry.getValue());
            }
        }
        return builder.toUriString();
    }

    private HttpEntity<Object> authEntity(final Object body) {
        final HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.set("Authorization", "Basic " + assessioBase64Authorization);
        requestHeaders.set("Content-Type", "application/json;charset=utf-8");
        requestHeaders.set("Accept", "*/*");
        if (body != null) {
            return new HttpEntity<>(body, requestHeaders);
        } else {
            return new HttpEntity<>(requestHeaders);
        }
    }

    private HttpEntity<String> request(final HttpMethod method, final String path, final Map<String, Object> urlParams, final String body) {
        final String baseUri = toUri(path);
        final String uriWithParams = uriWithParams(baseUri, urlParams);
        log.debug(method.toString() + " " + uriWithParams);
        if (body != null && logHttpTraffic) {
            log.debug("reqBody: \n" + JsonUtils.prettyPrint(body));
        } else if (logHttpTraffic) {
            log.debug("No reqBody");
        }
        final HttpEntity<Object> requestEntity = authEntity(body);

        try {
            final HttpEntity<String> result = template.exchange(uriWithParams,
                    method, requestEntity, String.class);
            return result;
        } catch (final RestClientResponseException httpException) {
            final int statusCode = httpException.getRawStatusCode();
            final String responseBody = httpException.getResponseBodyAsString();
            log.error("Error calling Assessio Ascend {} {} \nRequest body:\n{}\n" +
                            "Response status code: {}\nResponse body\n{}",
                    method.toString(), uriWithParams, body, statusCode, responseBody);

            throw httpException;
        } catch (final Throwable t) {
            log.error("Error calling Assessio Ascend {} {} \nBody:\n{}", method.toString(), uriWithParams, body);
            throw t;
        }
    }

    private String get(final String path, final Map<String, Object> urlParams) {
        final String result = request(HttpMethod.GET, path, urlParams, null).getBody();
        logResult(result);
        return result;
    }

    private String post(final String path, final Map<String, Object> urlParams, final Object body) {
        final String result = request(HttpMethod.POST, path, urlParams, JsonUtils.toJSON(body)).getBody();
        logResult(result);
        return result;
    }

    private String put(final String path, final Map<String, Object> urlParams, final String body) {
        final String result = request(HttpMethod.PUT, path, urlParams, null).getBody();
        logResult(result);
        return result;
    }

    private String delete(final String path, final Map<String, Object> urlParams) {
        final String result = request(HttpMethod.DELETE, path, urlParams, null).getBody();
        logResult(result);
        return result;
    }

    private Map<String, Object> getMap(final String path, final Map<String, Object> params) {
        final String jsonBody = get(path, params);
        return JsonUtils.getObject(jsonBody, new HashMap<>());
    }

    private List<Map<String, Object>> getCollection(final String path, final Map<String, Object> params) {
        final String jsonBody = get(path, params);
        final List<Map<String, Object>> result = JsonUtils.getObject(jsonBody, new ArrayList<Map<String, Object>>());
        return result;
    }


    public Category test (){
        String jsonBody = get("/category",null);
        return  JsonUtils.getObject(jsonBody,Category.class);
    }

    public  List<Category> test2(){
        String jsonBody = get("/category",null);
        return JsonUtils.getList(jsonBody, new TypeReference<List<Category>>() {});
    }




















}
