package life.ycode.community.service;

import life.ycode.community.Exception.CustomMizeErrorCode;
import life.ycode.community.Exception.CustomizeException;
import life.ycode.community.enums.CommentTypeEnum;
import life.ycode.community.model.Comment;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    public void insert(Comment comment) {
        if (comment.getParentId()==null||comment.getParentId()==0){
            throw new CustomizeException(CustomMizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }

        if(comment.getType()==null || !CommentTypeEnum.isExist(comment.getType())){
            throw  new CustomizeException(CustomMizeErrorCode.TYPE_PARAM_WRONG);
        }
    }
}
