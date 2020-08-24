package com.github.viktorpenelski.quickurl.repo

import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document
data class Url(
        val url: String,
        @Indexed(name = "slug_idx", unique = true)
        val slug: String,
        val created: LocalDateTime = LocalDateTime.now()
)