package pl.maciejdudek.project.exceptions;

public class UserAlreadyExistException extends RuntimeException {
    public UserAlreadyExistException() {
        super("A user with at least one of these parameters already exists.");
    }
}
