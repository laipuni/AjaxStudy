package com.example.ajaxstudy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.support.RequestHandledEvent;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(controllers = {HomeControllerTest.class})
class HomeControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @DisplayName("메인 화면에 requestParam 넘겨줄 경우 화면에 나온다.")
    @Test
    void test() throws Exception {
        //given
        String param = "안녕하세요";

        //when//then
        mockMvc.perform(
                MockMvcRequestBuilders.get("/?name="+param)
                        .flashAttr("name",param)
        )
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("index"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("name"))
                .andExpect(MockMvcResultMatchers.model().attribute("name",param));
    }

}