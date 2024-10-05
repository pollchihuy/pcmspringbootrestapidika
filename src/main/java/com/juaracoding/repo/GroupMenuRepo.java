package com.juaracoding.repo;

import com.juaracoding.model.GroupMenu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupMenuRepo extends JpaRepository<GroupMenu, Long> {

    /** DERIVED QUERY INI DI MySQL DB SAMA DENGAN "SELECT * FROM mst_group_menu WHERE name LIKE %?% LIMIT ?,? */
    public Page<GroupMenu> findByNameContainsIgnoreCase(Pageable pageable, String val);

    /** DERIVED QUERY INI Di MySQL DB SAMA DENGAN "SELECT * FROM mst_group_menu WHERE name LIKE %?% LIMIT ?,? */
    public Optional<GroupMenu> findTop1By();

    public List<GroupMenu> findByNameContainsIgnoreCase(String name);
    
//    public Optional<GroupMenu> findFirstBy();
}