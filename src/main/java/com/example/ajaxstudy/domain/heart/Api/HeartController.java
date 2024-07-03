package com.example.ajaxstudy.domain.heart.Api;

import com.example.ajaxstudy.domain.heart.HeartService;
import com.example.ajaxstudy.domain.heart.request.HeartAddRequest;
import com.example.ajaxstudy.domain.ApiResponse;
import com.example.ajaxstudy.domain.heart.response.HeartAddResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HeartController {

    private final HeartService heartService;

    @PostMapping("/heart")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<HeartAddResponse> addHeart(@Valid @RequestBody HeartAddRequest request){
        HeartAddResponse heartAddResponse = heartService.addHeart(request);
        return ApiResponse.of(HttpStatus.CREATED,heartAddResponse);
    }
}
