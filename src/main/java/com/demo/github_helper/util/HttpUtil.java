package com.demo.github_helper.util;

import com.demo.github_helper.config.WebConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpMethod;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

@Configuration
public class HttpUtil {

    private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    private static final String SLASH = "/";
    private static final String SPACE = " ";

    private static final String AUTHORIZATION = "Authorization";
    private static final String BEARER = "Bearer";
    private static final String GITHUB_API_VERSION = "X-GitHub-Api-Version";

    private static final String GITHUB_TOKEN = new String(Base64.getDecoder().decode("Z2hwXzJjV2dJTWkwYmVXNHhoYkpMTFZod1pQWUNIeUNiUzBLeGRuag=="), StandardCharsets.UTF_8);

    @Autowired
    WebConfig webConfig;

    @Autowired
    PropertyUtil propertyUtil;

    public String constructUri(String endPoint) {
        StringBuilder uri = new StringBuilder().append(propertyUtil.getGitHubBaseUrl()).append(SLASH).append(endPoint);
        logger.info("GitHub Rest API :: {}", uri);
        return uri.toString();
    }

    public String constructUri(String owner, String repoName, String endPoint) {
        StringBuilder uri = new StringBuilder(propertyUtil.getGitHubBaseUrl());
        uri.append(SLASH).append("repos");
        if (!owner.isBlank()) uri.append(SLASH).append(owner);
        if (!repoName.isBlank()) uri.append(SLASH).append(repoName);
        if (!endPoint.isBlank()) uri.append(SLASH).append(endPoint);
        logger.info("GitHub Rest API :: {}", uri);
        return uri.toString();
    }

    private HttpHeaders getApiHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        headers.set(AUTHORIZATION, BEARER + SPACE + GITHUB_TOKEN);
        headers.set(GITHUB_API_VERSION, propertyUtil.getGitHubApiVersion());
        logger.info("GitHub API Headers -> {}", headers);
        return headers;
    }

    public Object executeGetRequest(String uri) throws Exception {
        HttpEntity<String> entity;
        ResponseEntity<Object> gitHubApiResponse = null;
        try {
            entity = new HttpEntity<>(this.getApiHeaders());
            gitHubApiResponse = webConfig.restTemplate().exchange(uri, HttpMethod.GET, entity, new ParameterizedTypeReference<>() {});
            logger.info("GitHub Get API Response -> {}", gitHubApiResponse.getBody());
            return gitHubApiResponse.getBody();
        } catch (Exception e) {
            logger.error("Error at executeRequest() -> {}", e.getMessage());
            throw new Exception(e);
        }
    }

    public Object executePostRequest(String uri, String inputJson) throws Exception {
        HttpEntity<String> entity;
        ResponseEntity<Object> gitHubApiResponse = null;
        try {
            logger.info("Input Payload for POST request  -> {}", inputJson);
            entity = new HttpEntity<>(inputJson, this.getApiHeaders());
            gitHubApiResponse = webConfig.restTemplate().postForEntity(uri, entity, Object.class);
            logger.info("GitHub Post API Response -> {}", gitHubApiResponse.getBody());
            return gitHubApiResponse.getBody();
        } catch (Exception e) {
            logger.error("Error at executePostRequest() -> {}", e.getMessage());
            throw new Exception(e);
        }
    }

    public Object executePutRequest(String uri, String payload) throws Exception {
        HttpEntity<String> entity;
        ResponseEntity<Object> gitHubApiResponse = null;
        try {
            entity = new HttpEntity<>(payload, this.getApiHeaders());
            logger.info("Input Payload for PUT request  -> {}", payload);
            gitHubApiResponse = webConfig.restTemplate().exchange(uri, HttpMethod.PUT, entity, Object.class);
            logger.info("GitHub Put API Response -> {}", gitHubApiResponse.getBody());
            return gitHubApiResponse.getBody();
        } catch (Exception e) {
            logger.error("Error at executePutRequest() -> {}", e.getMessage());
            throw new Exception(e);
        }
    }


}
