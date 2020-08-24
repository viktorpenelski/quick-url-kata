package com.github.viktorpenelski.quickurl.api

class NotFoundException(msg: String) : RuntimeException(msg)
class BadRequestException(msg: String) : RuntimeException(msg)