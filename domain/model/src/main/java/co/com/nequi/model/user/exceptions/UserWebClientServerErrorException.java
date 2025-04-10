package co.com.nequi.model.user.exceptions;

public class UserWebClientServerErrorException extends RuntimeException {
    public UserWebClientServerErrorException(String message) {
        super(message);
    }
}
