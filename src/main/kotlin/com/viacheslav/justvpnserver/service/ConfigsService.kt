package com.viacheslav.justvpnserver.service

import org.springframework.stereotype.Service
import java.io.File

@Service
class ConfigsService {
    private val files =
        File("/root/algo/configs/176.222.52.146/wireguard/").walk().toList()
            .filter { it.isFile && it.name.endsWith(".conf") }

    private var queueConfigsCounter = 0

    fun getConfigs(): String {
        val result = files[queueConfigsCounter++].inputStream().readBytes().toString(Charsets.UTF_8)
        if (queueConfigsCounter >= files.size) {
            queueConfigsCounter = 0
        }
        return result
    }
}