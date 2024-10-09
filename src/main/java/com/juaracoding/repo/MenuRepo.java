package com.juaracoding.repo;

import com.juaracoding.model.Menu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuRepo extends JpaRepository<Menu,Long> {

    Page<Menu> findByNamaContainsIgnoreCase(Pageable pageable,String menuName);
    List<Menu> findByNamaContainsIgnoreCase(String menuName);
}
