package com.example.ajaxstudy.domain.heart.Api;

import com.example.ajaxstudy.domain.heart.HeartService;
import com.example.ajaxstudy.domain.heart.request.HeartAddRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(value = HeartController.class)
class HeartControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @MockBean
    protected HeartService heartService;

    @DisplayName("좋아요를 누를 경우 해당 게시믈의 boardId를 받아 좋아요를 추가한다.")
    @Test
    void addHeart() throws Exception {
        //given
        HeartAddRequest heartAddRequest = HeartAddRequest.builder()
                .boardId(0L)
                .build();
        String data = objectMapper.writeValueAsString(heartAddRequest);

        //when
        //then
        mockMvc.perform(
                MockMvcRequestBuilders.post("/heart")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(data)
        )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value("CREATED"))
                .andExpect(jsonPath("$.code").value(201))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @DisplayName("게시물 번호가 없을 경우 에러가 발생한다.")
    @Test
    void addHeartWithNullBoardId() throws Exception {
        //given
        HeartAddRequest heartAddRequest = HeartAddRequest.builder()
                .boardId(null)
                .build();
        String data = objectMapper.writeValueAsString(heartAddRequest);

        Mockito.when(heartService.addHeart(Mockito.any(HeartAddRequest.class)))
                        .thenThrow(new IllegalArgumentException("해당 게시물은 존재하지 않습니다."));

        //when
        //then
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/heart")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(data)
                )
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.code").value(400))
                .andExpect(jsonPath("$.message").value("해당 게시물은 존재하지 않습니다."))
                .andExpect(jsonPath("$.data").isEmpty());

    }
}