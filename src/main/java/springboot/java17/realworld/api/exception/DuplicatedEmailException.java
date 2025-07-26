package springboot.java17.realworld.api.exception;

public class DuplicatedEmailException  extends RuntimeException{

    public DuplicatedEmailException(String message) {
        super(message);
    }

}
