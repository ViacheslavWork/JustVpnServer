package com.viacheslav.justvpnserver.api.controller

import com.viacheslav.justvpnserver.advice.Answer
import com.viacheslav.justvpnserver.service.ConfigsService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ConfigsController(@Autowired val service: ConfigsService) {

    @GetMapping("/configs")
    fun getConfigs(): String {
        return service.getConfigs()
    }

    @GetMapping("/answer")
    fun getAnswer(): Answer {
        val answer = Answer("")
        answer.message = "I don't know!"
        return answer
    }
}