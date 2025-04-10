package co.com.nequi.model.user.exceptions;

public enum ExceptionsEnum {
    USER_NOT_FOUND("User not found"),
    USER_WEB_CLIENT_NOT_FOUND("User not found in external api"),
    ERROR_USER_WEB_CLIENT("Error in user web client"),
    USER_ALREADY_EXIST("User already exist"),
    ;

    private final String message;

    ExceptionsEnum(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
