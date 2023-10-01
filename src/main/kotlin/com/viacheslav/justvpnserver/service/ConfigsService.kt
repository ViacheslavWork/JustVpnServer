package com.viacheslav.justvpnserver.service

import org.springframework.stereotype.Service
import java.io.File

@Service
class ConfigsService {

    private val files =
        File("/Users/Viacheslav/test/").walk().toList().filter { it.isFile && it.name.endsWith(".conf") }

/*
    private val filesUbuntu =
        File("/home/viacheslav/algo/configs/10.0.2.15/wireguard/").walk().toList()
            .filter { it.isFile && it.name.endsWith(".conf") }
*/

    private var queueConfigsCounter = 0

    fun getConfigs(): String {
        val result = files[queueConfigsCounter++].inputStream().readBytes().toString(Charsets.UTF_8)
        if (queueConfigsCounter >= files.size) {
            queueConfigsCounter = 0
        }
        return result
    }
}