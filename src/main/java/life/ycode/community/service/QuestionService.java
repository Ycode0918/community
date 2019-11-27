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

    //首页问题查询
    public PaginationDTO list(Integer page, Integer size) {

        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalCount = questionMapper.count();
        Integer totalPage;
        //添加对参数page的判断，若参数超出极限，则将其设置成最大或最小值
        if (totalCount%size==0){
            totalPage = totalCount/size;
        }else {
            totalPage = totalCount/size +1;
        }
        if (page<1){
            page=1;
        }
        if (page>totalPage){
            page=totalPage;
        }
        paginationDTO.setPagination(totalPage,page);
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

    //查询我的问题
    public PaginationDTO list(Integer userId, Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalPage;
        Integer totalCount = questionMapper.countByUserId(userId);
        //添加对参数page的判断，若参数超出极限，则将其设置成最大或最小值
        if (totalCount%size==0){
            totalPage = totalCount/size;
        }else {
            totalPage = totalCount/size +1;
        }
        if (page<1){
            page=1;
        }
        if (page>totalPage){
            page=totalPage;
        }
        paginationDTO.setPagination(totalPage,page);
        Integer offset = size * (page-1);
        //查询所有问题内容
        List<Question> list = questionMapper.listByUserId(userId,offset,size);
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

    public QuestionDTO getById(Integer id) {
        Question question = questionMapper.getById(id);
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);
        User user = userMapper.findById(question.getCreator());
        questionDTO.setUser(user);
        return  questionDTO;
    }
}
