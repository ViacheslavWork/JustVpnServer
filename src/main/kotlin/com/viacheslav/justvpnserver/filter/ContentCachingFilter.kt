package com.viacheslav.justvpnserver.filter

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.apache.logging.log4j.LogManager
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.util.ContentCachingRequestWrapper
import org.springframework.web.util.ContentCachingResponseWrapper
import java.nio.charset.StandardCharsets


class ContentCachingFilter : OncePerRequestFilter() {
    private val myLogger = LogManager.getLogger(this::class.java.typeName)
        override fun doFilterInternal(
            request: HttpServletRequest,
            response: HttpServletResponse,
            filterChain: FilterChain
        ) {
            val cachedResponse = ContentCachingResponseWrapper(response)
            val cachedRequest = ContentCachingRequestWrapper(request)

            filterChain.doFilter(cachedRequest, cachedResponse)
            // Get Cache
            // Get Cache
            val requestBody: ByteArray = cachedRequest.contentAsByteArray
            val responseBody: ByteArray = cachedResponse.contentAsByteArray

            val changedResponseBody = String(responseBody, StandardCharsets.UTF_8)
                .replace("test", "hello")

            cachedResponse.reset()
            cachedResponse.contentType = "text/plain;charset=UTF-8"
            cachedResponse.writer.write(changedResponseBody)

            myLogger.info("response body in filter -> ${String(responseBody, StandardCharsets.UTF_8)}")
            myLogger.info("response content size in filter -> ${cachedResponse.contentSize}")
            myLogger.info("request body in filter -> ${String(requestBody, StandardCharsets.UTF_8)}")
            cachedResponse.copyBodyToResponse()
        }
    }
