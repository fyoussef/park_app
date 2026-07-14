package com.filipi.dtos;

import java.util.Optional;

public record HTTPResponse<T>(
        boolean success,
        String message,
        T data
) {
}
