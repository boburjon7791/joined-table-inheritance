package com.example.joined_table_inheritance.model.mapper;

import com.example.joined_table_inheritance.model.entity.BaseEntityLong;

public interface BaseMapper<REQUEST, ENTITY extends BaseEntityLong, RESPONSE> {
    ENTITY toEntity(REQUEST request);
    RESPONSE toResponse(ENTITY entity);
    void update(ENTITY entity, REQUEST request);
}
