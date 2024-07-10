package main.java.ru.clevertec.check;

public class ServerException extends Exception{
    private final String ERROR_TEXT;
    private String resultPath;
    public ServerException() {
        super();
        ERROR_TEXT = "INTERNAL SERVER ERROR";
    }

    public ServerException(String exceptionMessage) {
        super();
        ERROR_TEXT = exceptionMessage;
    }

    public String getResultPath() {
        return resultPath;
    }

    public ServerException(String exceptionMessage, String resultPath) {
        super();
        this.resultPath = resultPath;
        ERROR_TEXT = exceptionMessage;
    }

    public void setResultPath(String resultPath) {
        this.resultPath = resultPath;
    }

    @Override
    public String getMessage() {
        return ERROR_TEXT;
    }
}
