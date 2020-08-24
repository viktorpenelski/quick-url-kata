package com.github.viktorpenelski.quickurl.api

import com.github.viktorpenelski.quickurl.repo.Url

interface Validatable {
    fun validate()
}

data class UrlDto(val url: String,
                  val slug: String?) : Validatable {
    override fun validate() {
        val validUrlRegex =
                "[-a-zA-Z0-9@:%._\\+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b([-a-zA-Z0-9()@:%_\\+.~#?&//=]*)"
                        .toRegex()
        if (!validUrlRegex.containsMatchIn(url)) {
            throw BadRequestException("Invalid url, please use use a proper format like: http://foo.bar or https://www.foo.bar")
        }
    }
}

fun Url.toDto(): UrlDto {
    return UrlDto(this.url, this.slug)
}

fun UrlDto.toUrl(): Url {
    val slug: String = this.slug ?: getRandomString(6)

    return Url(url, slug)
}

private fun getRandomString(length: Int): String {
    val allowedChars = ('a'..'z') + '_' + '-'
    return (1..length)
            .map { allowedChars.random() }
            .joinToString("")
}