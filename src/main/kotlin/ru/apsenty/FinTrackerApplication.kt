package ru.apsenty

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class FinTrackerApplication

fun main(args: Array<String>) {
	runApplication<FinTrackerApplication>(*args)
}
