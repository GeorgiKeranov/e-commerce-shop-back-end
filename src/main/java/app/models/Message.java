package app.models;


public class Message {

    private String message;

    private boolean isError;

    public Message() {

    }

    public Message(boolean isError) {
        this.isError = isError;
    }

    public Message(boolean isError, String message) {
        this.isError = isError;
        this.message = message;
    }

    public boolean isError() {
        return isError;
    }

    public void setError(boolean error) {
        this.isError = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
