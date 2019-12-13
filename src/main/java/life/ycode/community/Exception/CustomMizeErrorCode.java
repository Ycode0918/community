package life.ycode.community.Exception;

public enum CustomMizeErrorCode implements ICustomMizeErrorCode {
    QUESTION_NOT_FOUND(2001,"你找的问题不在了，要不换个试试?"),
    TARGET_PARAM_NOT_FOUND(2002,"未选中任何问题或评论进行回复"),
    NO_LOGIN(2003,"当前操作需要登录，请登录后重试"),
    SYSTEM_ERROR(2004,"服务冒烟啦，要不您稍后再试试!!!"),
    TYPE_PARAM_WRONG(2005,"评论类型错误或不存在"),
    COMMENT_NOT_FOUND(2006,"你回复的评论不存在了，要不换个试试")
    ;


    @Override
    public String getMessage(){
        return  message;
    }
    @Override
    public Integer getCode() {
        return code;
    }

    private String message;
    private Integer code;

    CustomMizeErrorCode(Integer code, String message){
        this.message = message;
        this.code = code;
    }
}
