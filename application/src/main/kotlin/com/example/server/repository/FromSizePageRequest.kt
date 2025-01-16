package com.example.server.repository

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort

class FromSizePageRequest private constructor(page: Int, size: Int, sort: Sort) :
    PageRequest(page, size, sort) {
    companion object {
        @JvmStatic
        @JvmOverloads
        fun of(from: Int, size: Int, sort: Sort = Sort.unsorted()): FromSizePageRequest {
            return FromSizePageRequest(from / size, size, sort)
        }
    }
}
