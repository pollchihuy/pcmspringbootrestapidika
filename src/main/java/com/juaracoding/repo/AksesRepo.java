package com.juaracoding.repo;


import com.juaracoding.model.Akses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AksesRepo extends JpaRepository<Akses, Long> {

    Page<Akses> findByNamaContainingIgnoreCase(Pageable pageable,String name);
    List<Akses> findByNamaContainingIgnoreCase(String name);



}
