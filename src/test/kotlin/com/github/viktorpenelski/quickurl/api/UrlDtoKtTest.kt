package com.github.viktorpenelski.quickurl.api

import com.github.viktorpenelski.quickurl.repo.Url
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow

internal class UrlDtoKtTest {

    @Test
    fun `given full UrlDto, convert to full Url`() {
        val given = UrlDto("https://bg-kug.github.io", "kug")

        val converted = given.toUrl()

        assertEquals("https://bg-kug.github.io", converted.url)
        assertEquals("kug", converted.slug)
        assertNotNull(converted.created)
    }

    @Test
    fun `given full UrlDto without slug, convert to full Url with valid slug`() {
        val given = UrlDto("https://bg-kug.github.io", null)

        val converted = given.toUrl()

        assertThat(converted.url).isEqualTo("https://bg-kug.github.io")
        assertThat(converted.slug).isNotBlank()
    }

    @Test
    fun `Url converts to UrlDto`() {
        val given = Url("google.com", "gg")

        val converted = given.toDto()

        assertThat(converted.url).isEqualTo("google.com")
        assertThat(converted.slug).isEqualTo("gg")
    }

    @Test
    fun `UrlDto validates with valid URL`() {
        val validUrls = listOf(
                UrlDto("google.com", null),
                UrlDto("www.google.eu", null),
                UrlDto("http://google.bg", null),
                UrlDto("http://www.google.ro", null),
                UrlDto("https://google.gr", null),
                UrlDto("https://www.google.fr", null)
        )

        validUrls.forEach {
            assertDoesNotThrow {
                it.validate()
            }
        }
    }

    @Test
    fun `UrlDto validate fails with invalid URL`() {
        val validUrls = listOf(
                UrlDto("brbrbrbr", null),
                UrlDto("foo/bar", null),
                UrlDto("http://derp", null)
        )

        validUrls.forEach {
            assertThrows(BadRequestException::class.java) {
                it.validate()
            }
        }
    }
}