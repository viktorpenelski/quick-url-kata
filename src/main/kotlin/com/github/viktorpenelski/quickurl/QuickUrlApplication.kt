package com.github.viktorpenelski.quickurl

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class QuickUrlApplication

fun main(args: Array<String>) {
	runApplication<QuickUrlApplication>(*args)
}
