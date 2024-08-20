package com.demo.github_helper.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PropertyUtil {

    private static final Logger logger = LoggerFactory.getLogger(PropertyUtil.class);

    @Value("${app.github.baseUrl}")
    private String gitHubBaseUrl;

    @Value("${app.github.apiVersion}")
    private String gitHubApiVersion;

    //@Value("${app.github.token}")
    private String gitHubToken;

}
