package com.example.ajaxstudy.domain;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import static org.springframework.data.domain.Sort.by;

public class PageAbleFactory {
    public static final Direction DESC = Direction.DESC;
    public static final Direction ASC = Direction.ASC;

    public static Pageable create(int page, int pageSize, String sort, Direction direction){
        Sort.Order order = new Sort.Order(direction,sort);
        return PageRequest.of(page,pageSize,by(order));
    }

    public static Pageable create(int page, int pageSize){
        return PageRequest.of(page,pageSize);
    }
}
