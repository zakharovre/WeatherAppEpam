package app.exception;

public class UserNotSaveException extends RuntimeException{
    public UserNotSaveException(long id) {
        super("Could not save user " + id);
    }
}

