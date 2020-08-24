package com.github.viktorpenelski.quickurl.api

import com.github.viktorpenelski.quickurl.repo.Url
import com.github.viktorpenelski.quickurl.repo.UrlRepository
import org.springframework.dao.DuplicateKeyException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.view.RedirectView

@RestController
class Api(private val repo: UrlRepository) {

    @GetMapping("/{shortUrl}")
    fun test(@PathVariable shortUrl: String): RedirectView {
        val longUrl = repo.findBySlug(shortUrl)?.url
                ?: throw NotFoundException("Could not find slug $shortUrl")

        return RedirectView().apply {
            url = longUrl
        }
    }

    @PostMapping("/shorten")
    fun shorten(@RequestBody url: UrlDto): ResponseEntity<UrlDto> {
        url.validate()
        val saved: Url = repo.save(url.toUrl())
        return ResponseEntity.status(HttpStatus.CREATED).body(saved.toDto())
    }

    @ExceptionHandler(value = [DuplicateKeyException::class])
    fun handleUserAlreadyExists(ex: Exception, request: WebRequest): ResponseEntity<GlobalControllerExceptionHandler.ErrorsDetails> {
        val details = GlobalControllerExceptionHandler.ErrorsDetails("\uD83D\uDCA2 Slug already exists :( Try another one! ")
        return ResponseEntity(details, HttpStatus.BAD_REQUEST)
    }
}
