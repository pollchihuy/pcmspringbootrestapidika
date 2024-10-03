package com.juaracoding.repo;


import com.juaracoding.model.GroupMenu;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface GroupMenuRepo extends JpaRepository<GroupMenu, Long> {

    public List<GroupMenu> findByNameContainsIgnoreCase(Pageable pageable, String val);
    public Optional<GroupMenu> findTop1By();
//    public Optional<GroupMenu> findFirstBy();
}