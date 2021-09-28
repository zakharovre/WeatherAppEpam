package app.exception;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(long id) {
        super("Could not find user " + id);
    }
}

