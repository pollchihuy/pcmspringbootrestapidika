package com.juaracoding.controller;


import com.google.gson.Gson;
import com.juaracoding.config.OtherConfig;
import com.juaracoding.dto.validasi.RegisDTO;
import com.juaracoding.model.User;
import com.juaracoding.service.UserService;
import com.juaracoding.util.LoggingFile;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api")
public class UserController {

    @Autowired
    private UserService userService;

    private Gson gson = new Gson();

    @GetMapping("/users/all")
    public ResponseEntity<Object> findAll(HttpServletRequest request) {

//        return userService.findAll(request);
        return null;
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<?> findById(
            HttpServletRequest request,
            @PathVariable(value = "id") Long id) {
//        return userService.findById(id,request);
        return null;
    }

}
