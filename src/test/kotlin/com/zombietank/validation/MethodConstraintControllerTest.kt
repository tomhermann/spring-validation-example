package com.zombietank.validation

import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

class MethodConstraintControllerTest {
    @Nested
    @WebMvcTest(ConformingMethodConstraintController::class)
    inner class ConformingExample {
        @Autowired
        private lateinit var mockMvc: MockMvc

        @Test
        fun `given valid request should issue response`() {
            val older = "2020-01-01T00:00Z"
            val newer = "2021-01-01T00:00Z"

            mockMvc
                .get("/conforming?from={from}&to={to}", older, newer)
                .andExpect { status { isOk() } }
                .andExpect { content { string("Testing 1,2! from: $older to: $newer") } }
        }

        @Test
        fun `given invalid request should issue client error`() {
            val older = "2020-01-01T00:00Z"
            val newer = "2021-01-01T00:00Z"

            mockMvc
                .get("/conforming?from={from}&to={to}", newer, older)
                .andExpect { status { is4xxClientError() } }
        }
    }

    @Nested
    @WebMvcTest(NonConformingMethodConstraintController::class)
    inner class NonConformingExample {
        @Autowired
        private lateinit var mockMvc: MockMvc

        @Test
        fun `given valid request should issue response`() {
            val older = "2020-01-01T00:00Z"
            val newer = "2021-01-01T00:00Z"

            mockMvc
                .get("/non-conforming?from={from}&to={to}", older, newer)
                .andExpect { status { isOk() } }
                .andExpect { content { string("Testing 1,2! from: $older to: $newer") } }
        }

        @Test
        // This test fails, which shows this code path does not apply validation
        // due to the name of the method annotated with @AssertTrue annotation.
        fun `given invalid request should issue client error`() {
            val older = "2020-01-01T00:00Z"
            val newer = "2021-01-01T00:00Z"

            mockMvc
                .get("/non-conforming?from={from}&to={to}", newer, older)
                .andExpect { status { is4xxClientError() } }
        }
    }
}
