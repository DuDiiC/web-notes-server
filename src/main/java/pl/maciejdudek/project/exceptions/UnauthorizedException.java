package pl.maciejdudek.project.exceptions;

import java.time.LocalDateTime;

public class UnauthorizedException extends RuntimeException {

    private final LocalDateTime exceptionTime;

    public LocalDateTime getExceptionTime() {
        return this.exceptionTime;
    }

    public UnauthorizedException() {
        super("You do not have permission to perform this operation.");
        this.exceptionTime = LocalDateTime.now();
    }
}

