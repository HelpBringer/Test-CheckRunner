package main.java.ru.clevertec.check;

public class ServerException extends Exception{
    private final String ERROR_TEXT;

    public ServerException() {
        super();
        ERROR_TEXT = "INTERNAL SERVER ERROR";
    }

    public ServerException(String exceptionMessage) {
        super();
        ERROR_TEXT = exceptionMessage;
    }

    @Override
    public String getMessage() {
        return ERROR_TEXT;
    }
}
