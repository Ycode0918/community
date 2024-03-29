package life.ycode.community.Exception;

public class CustomizeException extends RuntimeException {
    private String message;
    private Integer code;

    public CustomizeException(ICustomMizeErrorCode errorCode){
        this.code = errorCode.getCode();
        this.message=errorCode.getMessage();
    }


    @Override
    public String getMessage(){
        return message;
    }

    public Integer getCode() {
        return code;
    }
}
