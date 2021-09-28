package app.exception;

public class UserCreateException extends RuntimeException{
    public UserCreateException() {
        super("User with this email already exists");
    }
}

