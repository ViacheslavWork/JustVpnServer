package com.viacheslav.justvpnserver

import com.viacheslav.justvpnserver.advice.KotlinCustomResponseBodyAdvice
import org.apache.logging.log4j.LogManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice


private val myLogger = LogManager.getLogger(SecurityConfig::class.java.typeName)


@Configuration
@EnableWebMvc
class SecurityConfig() : WebMvcConfigurer {
    @Bean
    @RequestMapping
    fun reqMappingHandlerAdapter(): RequestMappingHandlerAdapter {
        val requestMappingHandlerAdapter = RequestMappingHandlerAdapter()

        val responseBodyAdvices: MutableList<ResponseBodyAdvice<*>> = ArrayList()
        responseBodyAdvices.add(KotlinCustomResponseBodyAdvice())
        requestMappingHandlerAdapter.setResponseBodyAdvice(responseBodyAdvices)

        return requestMappingHandlerAdapter
    }

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        return http.requiresChannel { channel -> channel.anyRequest().requiresSecure() }
            .authorizeHttpRequests { authorize -> authorize.anyRequest().permitAll() }
//            .addFilterAfter(ContentCachingFilter(), BasicAuthenticationFilter::class.java)
            .build()
    }

}

/*
@Configuration
@RequestMapping
class CustomWebMvcConfiguration : DelegatingWebMvcConfiguration() {

    @Bean
    @RequestMapping
    fun requestMappingHandlerAdapter(): RequestMappingHandlerAdapter {
        val requestMappingHandlerAdapter = super.createRequestMappingHandlerAdapter()

        val responseBodyAdvices: MutableList<ResponseBodyAdvice<*>> = ArrayList()
        responseBodyAdvices.add(CustomResponseBodyAdvice())
        requestMappingHandlerAdapter.setResponseBodyAdvice(responseBodyAdvices)

        return requestMappingHandlerAdapter
    }
}
*/


