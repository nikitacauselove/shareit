package com.example.server.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

public class FromSizePageRequest extends PageRequest {

    private FromSizePageRequest(Integer page, Integer size, Sort sort) {
        super(page, size, sort);
    }

    public static FromSizePageRequest of(Integer from, Integer size) {
        return of(from, size, Sort.unsorted());
    }

    public static FromSizePageRequest of(Integer from, Integer size, Sort sort) {
        return new FromSizePageRequest(from / size, size, sort);
    }
}
