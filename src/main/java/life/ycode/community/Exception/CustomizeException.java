package life.ycode.community.Exception;

public class CustomizeException extends RuntimeException {
    private String message;

    public CustomizeException(ICustomMizeErrorCode errorCode){
        this.message=errorCode.getMessage();
    }
    public CustomizeException(String message){
        this.message=message;
    }

    @Override
    public String getMessage(){
        return message;
    }
}
