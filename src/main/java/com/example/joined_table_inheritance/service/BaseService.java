package com.example.joined_table_inheritance.service;

import com.example.joined_table_inheritance.config.model.response.ApiResponse;
import com.example.joined_table_inheritance.config.model.response.PaginationDetails;
import com.example.joined_table_inheritance.model.entity.BaseEntityLong;
import com.example.joined_table_inheritance.model.filter_request.BaseFilterRequest;
import com.example.joined_table_inheritance.model.mapper.BaseMapper;
import com.example.joined_table_inheritance.repository.BaseRepository;
import com.example.joined_table_inheritance.specification.BaseSpecification;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.io.Serializable;

public interface BaseService<REQUEST, ENTITY extends BaseEntityLong, ID extends Serializable, RESPONSE, FILTER_REQUEST extends BaseFilterRequest> {
    // abstract methods
    BaseRepository<ENTITY, ID> getRepository();
    BaseSpecification<ENTITY, ID, FILTER_REQUEST> getSpecification();
    BaseMapper<REQUEST, ENTITY, RESPONSE> getMapper();
    void checkUniqueCreate(REQUEST request);
    void checkUniqueUpdate(REQUEST request, ID id);
    String entityName();


    // concrete methods
    default ApiResponse<RESPONSE> create(REQUEST request){
        checkUniqueCreate(request);
        ENTITY entity = getMapper().toEntity(request);
        ENTITY saved = getRepository().save(entity);
        RESPONSE response = getMapper().toResponse(saved);
        return ApiResponse.ok(response);
    }

    default ENTITY entity(ID id){
        return getRepository().findOne((root, query, criteriaBuilder) -> {
            Predicate idEquals = criteriaBuilder.equal(root.get(BaseEntityLong._id), id);
            return criteriaBuilder.and(idEquals, getSpecification().deletedFalse().toPredicate(root, query, criteriaBuilder));
        }).orElseThrow(() -> new RuntimeException(entityName() + " not found"));
    }

    default ApiResponse<RESPONSE> update(REQUEST request, ID id){
        checkUniqueUpdate(request, id);
        ENTITY entity = entity(id);
        getMapper().update(entity, request);
        ENTITY saved = getRepository().save(entity);
        RESPONSE response = getMapper().toResponse(saved);
        return ApiResponse.ok(response);
    }

    default ApiResponse<RESPONSE> findById(ID id){
        ENTITY entity = entity(id);
        ENTITY saved = getRepository().save(entity);
        RESPONSE response = getMapper().toResponse(saved);
        return ApiResponse.ok(response);
    }

    default ApiResponse<List<RESPONSE>> findAll(FILTER_REQUEST request){
        if (request.isAll()) {
            List<RESPONSE> responseList = getRepository().findAll(getSpecification().deletedFalse(), request.sort()).stream().map(getMapper()::toResponse).toList();
            return ApiResponse.ok(responseList);
        }
        Specification<ENTITY> specification = getSpecification().getSpecification(request);
        Page<RESPONSE> page = getRepository().findAll(specification, request.pageable()).map(getMapper()::toResponse);
        return ApiResponse.ok(page.getContent(), PaginationDetails.of(page));
    }

    default ApiResponse<Void> deleteById(ID id){
        ENTITY entity = entity(id);
        entity.setDeleted(true);
        getRepository().save(entity);
        return ApiResponse.ok();
    }
}
