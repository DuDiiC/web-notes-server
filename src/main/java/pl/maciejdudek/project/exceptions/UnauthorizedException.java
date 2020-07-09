package pl.maciejdudek.project.exceptions;

public class UnauthorizedException extends RuntimeException {

    public UnauthorizedException() {
        super("You do not have permission to perform this operation.");
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
