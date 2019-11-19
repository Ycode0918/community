package life.ycode.community.controller;


import life.ycode.community.dto.AccessTokenDTO;
import life.ycode.community.provider.GitHubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorizeController {

    @Autowired
    private GitHubProvider gitHubProvider;
    @GetMapping("/callback")
    public String collback(@RequestParam(name="code") String code,
                           @RequestParam(name="state") String state){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id("3e93fa1fce11697dc698");
        accessTokenDTO.setClient_secret("e4abbd53350f2863d2dd2cfd82b9dfc1df619556");
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri("http://localhost:8887/callback");
        accessTokenDTO.setState(state);
        gitHubProvider.getAccessToken(accessTokenDTO);
        return "index";
    }


}
