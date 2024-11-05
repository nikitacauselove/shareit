package com.example.api.dto.enums;

import com.example.api.BadRequestException;

public enum BookingState {
    ALL, CURRENT, FUTURE, PAST, REJECTED, WAITING;

    public static BookingState from(String state) {
        for (BookingState value : BookingState.values()) {
            if (value.name().equals(state)) {
                return value;
            }
        }
        throw new BadRequestException("Unknown state: " + state);
    }
}
