package com.juaracoding.repo;

import com.juaracoding.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepo extends CrudRepository<User,Long> {


}