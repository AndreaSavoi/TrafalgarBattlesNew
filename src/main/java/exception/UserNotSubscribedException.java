package exception;

public class UserNotSubscribedException extends Exception {
    public UserNotSubscribedException(String message) {
        super(message);
    }
}