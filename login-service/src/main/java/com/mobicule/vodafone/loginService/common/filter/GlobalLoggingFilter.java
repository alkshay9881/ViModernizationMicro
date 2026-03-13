package com.mobicule.vodafone.loginService.common.filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
@Slf4j
public class GlobalLoggingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        long startTime = System.currentTimeMillis();

        ContentCachingRequestWrapper requestWrapper =
                new ContentCachingRequestWrapper(request);

        ContentCachingResponseWrapper responseWrapper =
                new ContentCachingResponseWrapper(response);

        // Continue request
        filterChain.doFilter(requestWrapper, responseWrapper);

        // Read request body
        byte[] requestArray = requestWrapper.getContentAsByteArray();
        String requestBody = new String(requestArray, StandardCharsets.UTF_8);

        // Read response body
        byte[] responseArray = responseWrapper.getContentAsByteArray();
        String responseBody = new String(responseArray, StandardCharsets.UTF_8);

        long timeTaken = System.currentTimeMillis() - startTime;

        log.info("--------------------------------------------------");
        log.info("API           : {}", request.getRequestURI());
        log.info("Method        : {}", request.getMethod());
        log.info("Request JSON  : {}", requestBody);
        log.info("Response JSON : {}", responseBody);
        log.info("Status        : {}", response.getStatus());
        log.info("Time Taken    : {} ms", timeTaken);
        log.info("--------------------------------------------------");

        // Important: return response to client
        responseWrapper.copyBodyToResponse();
    }
}