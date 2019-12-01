package life.ycode.community.Exception;

public enum CustomMizeErrorCode implements ICustomMizeErrorCode {
    QUESTION_NOT_FOUND("你找的问题不在了，要不换个试试?");
    private String message;

    @Override
    public String getMessage(){
        return  message;
    }

    CustomMizeErrorCode(String message){
        this.message = message;
    }
}
