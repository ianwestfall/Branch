package com.west.branch

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.http.HttpStatus
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus

@SpringBootApplication
class BranchApplication

fun main(args: Array<String>) {
	runApplication<BranchApplication>(*args)
}
