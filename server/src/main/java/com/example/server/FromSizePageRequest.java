package com.example.server;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

public class FromSizePageRequest extends PageRequest {
    private FromSizePageRequest(int page, int size, Sort sort) {
        super(page, size, sort);
    }

    public static FromSizePageRequest of(int from, int size) {
        return of(from, size, Sort.unsorted());
    }

    public static FromSizePageRequest of(int from, int size, Sort sort) {
        return new FromSizePageRequest(from / size, size, sort);
    }
}
