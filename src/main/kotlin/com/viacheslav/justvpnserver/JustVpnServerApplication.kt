package com.viacheslav.justvpnserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class JustVpnServerApplication

fun main(args: Array<String>) {
    runApplication<JustVpnServerApplication>(*args)
}
