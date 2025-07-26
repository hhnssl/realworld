package springboot.java17.realworld.api.exception;

public class DuplicatedUsernameException extends RuntimeException{

    public DuplicatedUsernameException(String message) {
        super(message);
    }
}
