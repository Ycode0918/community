package life.ycode.community.service;

import life.ycode.community.Exception.CustomMizeErrorCode;
import life.ycode.community.Exception.CustomizeException;
import life.ycode.community.dto.PaginationDTO;
import life.ycode.community.dto.QuestionDTO;
import life.ycode.community.mapper.QuestionExtMapper;
import life.ycode.community.mapper.QuestionMapper;
import life.ycode.community.mapper.UserMapper;
import life.ycode.community.model.Question;
import life.ycode.community.model.QuestionExample;
import life.ycode.community.model.User;
import life.ycode.community.model.UserExample;
import org.apache.ibatis.session.RowBounds;
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

    @Autowired
    private QuestionExtMapper questionExtMapper;

    //首页问题查询
    public PaginationDTO list(Integer page, Integer size) {

        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalCount = (int)questionMapper.countByExample(new QuestionExample());
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
        Integer offset = page < 1 ? 0 : size * (page - 1);
        //Integer offset = size * (page-1);
        //查询所有问题内容
        List<Question> list = questionMapper.selectByExampleWithRowbounds(new QuestionExample(), new RowBounds(offset, size));
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        //遍历list集合
        for (Question question : list) {
            //通过id查询user对象
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            System.out.println(user);
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
        QuestionExample example = new QuestionExample();
        example.createCriteria().andCreatorEqualTo(userId);
        Integer totalCount = (int)questionMapper.countByExample(example);
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
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria().andCreatorEqualTo(userId);
        List<Question> list = questionMapper.selectByExampleWithRowbounds(questionExample, new RowBounds(offset, size));
        List<QuestionDTO> questionDTOList = new ArrayList<>();
        //遍历list集合
        for (Question question : list) {
            //通过id查询user对象
            User user = userMapper.selectByPrimaryKey(question.getCreator());
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
        Question question = questionMapper.selectByPrimaryKey(id);
        if (question==null){
            throw new CustomizeException(CustomMizeErrorCode.QUESTION_NOT_FOUND);
        }
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);
        User user = userMapper.selectByPrimaryKey(question.getCreator());
        questionDTO.setUser(user);
        return  questionDTO;
    }

    public void createOrUpdate(Question question) {
        if(question.getId()==null){
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            questionMapper.insert(question);
        }else {
            //更新
            Question updateQuestion = new Question();
            updateQuestion.setGmtModified(System.currentTimeMillis());
            updateQuestion.setTitle(question.getTitle());
            updateQuestion.setDescription(question.getDescription());
            updateQuestion.setTag(question.getTag());
            QuestionExample example = new QuestionExample();
            example.createCriteria()
                    .andIdEqualTo(question.getId());
            int updated = questionMapper.updateByExampleSelective(updateQuestion, example);
            if (updated!=1){
                throw new CustomizeException(CustomMizeErrorCode.QUESTION_NOT_FOUND);
            }
        }
    }

    public void incView(Integer id) {
        Question question = new Question();
        question.setId(id);
        question.setViewCount(1);
        questionExtMapper.incView(question);

    }
}
