package life.ycode.community.controller;

import life.ycode.community.dto.PaginationDTO;
import life.ycode.community.dto.QuestionDTO;
import life.ycode.community.mapper.QuestionMapper;
import life.ycode.community.mapper.UserMapper;
import life.ycode.community.model.Question;
import life.ycode.community.model.User;
import life.ycode.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {



    @Autowired
    private QuestionService questionService;
    @GetMapping("/")
    public String index(Model model,
                        @RequestParam(name = "page",defaultValue = "1")Integer page,
                        @RequestParam(name = "size",defaultValue = "5")Integer size){
        //将查询到的用户信息放入作用域中
        PaginationDTO pagination = questionService.list(page,size);
        model.addAttribute("pagination",pagination);
        return "index";
    }

}
