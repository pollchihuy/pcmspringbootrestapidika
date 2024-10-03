package com.juaracoding.repo;

import com.juaracoding.model.Akses;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AksesRepo extends JpaRepository<Akses, Integer> {

    //    @Query("SELECT a FROM Akses a WHERE a.id=?1 ")
    @Modifying
    @Transactional
    @Query("UPDATE Akses a SET a.id=?1 ")
    public void updateDahAhh(String val);

}
