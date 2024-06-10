package com.viacheslav.justvpnserver

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.apache.logging.log4j.LogManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.springframework.web.util.ContentCachingRequestWrapper
import org.springframework.web.util.ContentCachingResponseWrapper
import java.nio.charset.StandardCharsets


private val myLogger = LogManager.getLogger(SecurityConfig::class.java.typeName)


@Configuration
class SecurityConfig() : WebMvcConfigurer {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        return http.requiresChannel { channel -> channel.anyRequest().requiresSecure() }
            .authorizeHttpRequests { authorize -> authorize.anyRequest().permitAll() }
            .addFilterAfter(ContentCachingFilter(), BasicAuthenticationFilter::class.java)
            .build()
    }
    class ContentCachingFilter : OncePerRequestFilter() {
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
}
