package com.example.joined_table_inheritance.specification;

import com.example.joined_table_inheritance.model.entity.BaseEntityLong;
import com.example.joined_table_inheritance.model.filter_request.BaseFilterRequest;
import org.springframework.data.jpa.domain.Specification;

import java.io.Serializable;
import java.time.LocalDate;

public interface BaseSpecification<T extends BaseEntityLong, ID extends Serializable, FILTER_REQUEST extends BaseFilterRequest> {
    String toDateFunction="date";

    /*
     * abstract methods
    * */
    Specification<T> getSpecification(FILTER_REQUEST filterRequest);

    /*
     * concrete methods
     * */
    default Specification<T> fromDate(LocalDate fromDate){
        return fromDate==null ? emptySpecification() : (root, query, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(criteriaBuilder.function(toDateFunction, LocalDate.class, root.get(BaseEntityLong._createdAt)), fromDate);
    }

    default Specification<T> toDate(LocalDate toDate){
        return toDate==null ? emptySpecification() : (root, query, criteriaBuilder) -> criteriaBuilder.lessThanOrEqualTo(criteriaBuilder.function(toDateFunction, LocalDate.class, root.get(BaseEntityLong._createdAt)), toDate);
    }

    default Specification<T> emptySpecification(){
        return Specification.where(null);
    }

    default Specification<T> deletedFalse(){
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(BaseEntityLong._deleted), false);
    }

    default Specification<T> deleted(boolean deleted){
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(BaseEntityLong._deleted), deleted);
    }

    static <T extends BaseEntityLong, ID extends Serializable, FILTER_REQUEST extends BaseFilterRequest> BaseSpecification<T, ID, FILTER_REQUEST> createAnonymousSpecification(){
        return filterRequest -> (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(BaseEntityLong._deleted), false);
    }
}
