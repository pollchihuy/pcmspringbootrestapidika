package com.juaracoding.service;


import com.juaracoding.model.Contoh;
import com.juaracoding.model.ParentEntity;
import com.juaracoding.model.User;
import com.juaracoding.repo.ContohRepo;
import com.juaracoding.repo.ParentRepo;
import com.juaracoding.repo.UserRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParentService {

    @Autowired
    ParentRepo parentRepo;

    @Autowired
    ContohRepo contohRepo;

    @Autowired
    UserRepo userRepo;


    @Transactional
    public String save(ParentEntity parentEntity,Contoh contoh,User user){
        try{
            parentRepo.save(parentEntity);
            int intX = 1/0;
            contohRepo.save(contoh);
            userRepo.save(user);
            userRepo.findAll();
        }catch (Exception e){
            return "ERROR , GAGAL SIMPAN";
        }
        return "BERHASIL SIMPAN";
    }
}