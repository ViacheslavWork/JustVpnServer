package com.viacheslav.justvpnserver.advice

import org.apache.logging.log4j.LogManager
import org.springframework.core.MethodParameter
import org.springframework.http.MediaType
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.server.ServerHttpRequest
import org.springframework.http.server.ServerHttpResponse
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice

@ControllerAdvice
class KotlinCustomResponseBodyAdvice : ResponseBodyAdvice<Answer> {
    private val myLogger = LogManager.getLogger("KotlinCustomResponseBodyAdvice")
    override fun supports(
        returnType: MethodParameter,
        converterType: Class<out HttpMessageConverter<*>>
    ): Boolean {
        return true
    }

    override fun beforeBodyWrite(
        answer: Answer?,
        returnType: MethodParameter,
        selectedContentType: MediaType,
        selectedConverterType: Class<out HttpMessageConverter<*>>,
        request: ServerHttpRequest,
        response: ServerHttpResponse
    ): Answer {
        println("In beforeBodyWrite() method of " + javaClass.simpleName)
        myLogger.info("log something")
        answer?.message = answer?.message + " by Spring"
        return answer!!
    }
}