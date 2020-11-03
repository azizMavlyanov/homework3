package ru.digitalhabbits.homework3.exception;

public class ConflictException extends RuntimeException{
    private static final long serialVersionUID = 2868233309862802337L;

    public ConflictException(String message) {
        super(message);
    }
}
