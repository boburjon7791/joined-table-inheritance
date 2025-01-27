package com.example.joined_table_inheritance.model.filter_request;

import com.example.joined_table_inheritance.model.entity.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BaseFilterRequest {
    int page;
    int size=10;
    boolean all;
    LocalDate fromDate;
    LocalDate toDate;
    String search;

    public Sort sort(){
        return Sort.by(Sort.Direction.ASC, User._id);
    }

    public Pageable pageable(){
        return PageRequest.of(page, size, sort());
    }
}
