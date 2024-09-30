package com.juaracoding.repo;

import com.juaracoding.model.ParentEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;


public interface ParentRepo extends CrudRepository<ParentEntity, Long> {


    @Query("SELECT u FROM  ParentEntity u WHERE  u.active=?1 ")
    public List<ParentEntity> getData(String active);

    public Optional<ParentEntity> findByNameOrNamaParentOrActive(String x,String y,String z);
}