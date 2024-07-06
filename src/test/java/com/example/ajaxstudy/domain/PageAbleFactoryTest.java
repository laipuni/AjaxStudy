package com.example.ajaxstudy.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import static org.assertj.core.api.Assertions.assertThat;

class PageAbleFactoryTest {

    @DisplayName("page, size, sort, direction을 파라미터로 받아서 Pageable을 생성한다.")
    @Test
    void create(){
        //given
        int page = 0;
        int size = 10;
        String sort = "Id";
        Sort.Direction direction = Sort.Direction.DESC;

        //when
        Pageable pageable = PageAbleFactory.create(page, size, sort, direction);

        //then
        assertThat(pageable.getPageNumber()).isEqualTo(page);
        assertThat(pageable.getPageSize()).isEqualTo(size);
        assertThat(pageable.getSort().getOrderFor(sort).getDirection()).isEqualTo(direction);
        assertThat(pageable.getSort().getOrderFor(sort)).isNotNull();
    }

}