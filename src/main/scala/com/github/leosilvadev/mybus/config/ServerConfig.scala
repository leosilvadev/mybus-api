package com.github.leosilvadev.mybus.config

case class ServerConfig(linesFilePath: String,
                        stopsFilePath: String,
                        timesFilePath: String,
                        delaysFilePath: String,
                        serverPort: Int,
                        endpointTimeout: Long)
