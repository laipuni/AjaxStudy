package com.example.ajaxstudy.domain.heart;

import com.example.ajaxstudy.domain.board.Board;
import com.example.ajaxstudy.domain.board.BoardRepository;
import com.example.ajaxstudy.domain.heart.request.HeartAddRequest;
import com.example.ajaxstudy.domain.heart.response.HeartAddResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class HeartService {

    private final HeartRepository heartRepository;
    private final BoardRepository boardRepository;

    @Transactional
    public HeartAddResponse addHeart(HeartAddRequest request){
        Board board = boardRepository.findById(request.getBoardId())
                .orElseThrow(() -> new IllegalArgumentException("해당 게시물은 존재하지 않습니다."));
        Heart heart = Heart.of(board);
        heartRepository.save(heart);
        return HeartAddResponse.of(board.getHeartNum());
    }

}
