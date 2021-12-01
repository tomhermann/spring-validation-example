package com.zombietank.validation

import org.springframework.format.annotation.DateTimeFormat
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.time.OffsetDateTime
import javax.validation.Valid
import javax.validation.constraints.AssertTrue

@RestController
class NonConformingMethodConstraintController {

    @GetMapping("/non-conforming")
    fun validParams(@Valid params: NonConformingParams) =
        "Testing 1,2! from: ${params.from} to: ${params.to}"
}

data class NonConformingParams(
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    val from: OffsetDateTime,
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    val to: OffsetDateTime
){
    @AssertTrue(message = "from should be before to")
    fun shouldBeValidDateRange(): Boolean {
        return from.isBefore(to)
    }
}