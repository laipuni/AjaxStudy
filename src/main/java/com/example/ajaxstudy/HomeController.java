package com.example.ajaxstudy;

import jakarta.annotation.Nullable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Slf4j
@Controller
public class HomeController {

    @GetMapping("/")
    public String home(@Nullable @RequestParam("name")String name, Model model){
        model.addAttribute("name",name);
        return "index";
    }

    @GetMapping("/ajaxTest")
    public String ajaxTest(){
        return "ajaxTest";
    }

    @GetMapping("/ajaxTest/click")
    @ResponseBody
    public List<String> ajaxClickTest(){
        log.info("ajaxClickTest");
        return List.of("a","b","c","d");
    }

}
