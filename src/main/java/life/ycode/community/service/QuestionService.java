package life.ycode.community.service;

import life.ycode.community.dto.PaginationDTO;
import life.ycode.community.dto.QuestionDTO;
import life.ycode.community.mapper.QuestionMapper;
import life.ycode.community.mapper.UserMapper;
import life.ycode.community.model.Question;
import life.ycode.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    public PaginationDTO list(Integer page, Integer size) {

        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalCount = questionMapper.count();
        paginationDTO.setPagination(totalCount,page,size);
        //添加对参数page的判断，若参数超出极限，则将其设置成最大或最小值
        if (page<1){
            page=1;
        }
        if (page>paginationDTO.getTotalPage()){
            page=paginationDTO.getTotalPage();
        }
        Integer offset = size * (page-1);
        //查询所有问题内容
        List<Question> list = questionMapper.list(offset,size);
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        //遍历list集合
        for (Question question : list) {
            //通过id查询user对象
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            //将question中的值设置到questionDTO中
            BeanUtils.copyProperties(question,questionDTO);
            //设置user的属性值
            //设置user的属性值是为了拿到用户的头像
            questionDTO.setUser(user);
            //将查询到的questionDTO添加到list集合中
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setQuestions(questionDTOList);
        return paginationDTO;
    }
}
