package io.microservices.shop.oauth.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * @author admin
 */
@Controller
@RequestMapping(value = "/oauth")
public class LoginRedirect {

    /**
     * 跳转至登录页面
     * @return
     */
    @GetMapping("/login")
    public String login(HttpServletRequest request, Model model) {
        String queryString = request.getQueryString();
        if(queryString!=null){
            String from=queryString.substring(5,queryString.length());
            System.out.println(from);
            model.addAttribute("from",from);
        }else{
            model.addAttribute("from","");
        }
        return "login";
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }
}
