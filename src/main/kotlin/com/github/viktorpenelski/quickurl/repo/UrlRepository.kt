package com.github.viktorpenelski.quickurl.repo

import org.springframework.data.mongodb.repository.MongoRepository


interface UrlRepository : MongoRepository<Url?, String?> {
    fun findBySlug(short: String): Url?
}