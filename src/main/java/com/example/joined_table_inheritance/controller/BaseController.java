package com.example.joined_table_inheritance.controller;

import com.example.joined_table_inheritance.config.model.response.ApiResponse;
import com.example.joined_table_inheritance.model.entity.BaseEntityLong;
import com.example.joined_table_inheritance.model.filter_request.BaseFilterRequest;
import com.example.joined_table_inheritance.service.BaseService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.io.Serializable;

public interface BaseController<REQUEST, ENTITY extends BaseEntityLong, ID extends Serializable, RESPONSE, FILTER_REQUEST extends BaseFilterRequest> {
    BaseService<REQUEST, ENTITY, ID, RESPONSE, FILTER_REQUEST> getService();

    @PostMapping
    default ApiResponse<RESPONSE> create(@Valid @RequestBody REQUEST request){
        return getService().create(request);
    }

    @PutMapping("/{id}")
    default ApiResponse<RESPONSE> update(@Valid @RequestBody REQUEST request, @PathVariable ID id){
        return getService().update(request, id);
    }

    @GetMapping("/{id}")
    default ApiResponse<RESPONSE> findById(@PathVariable ID id){
        return getService().findById(id);
    }

    @GetMapping
    default ApiResponse<List<RESPONSE>> findAll(FILTER_REQUEST request){
        return getService().findAll(request);
    }

    @DeleteMapping("/{id}")
    default ApiResponse<Void> deleteById(@PathVariable ID id){
        return getService().deleteById(id);
    }
}
