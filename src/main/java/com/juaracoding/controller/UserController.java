package com.juaracoding.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.juaracoding.dto.response.UserDTO;
import com.juaracoding.dto.validasi.RegisDTO;
import com.juaracoding.model.User;
import com.juaracoding.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("api")
public class UserController {

    @Autowired
    UserService userService;

    private Gson gson = new Gson();

    @GetMapping("/users/all")
    public ResponseEntity<Object> findAll() {

        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/users/{id}")
    public void findById(
            HttpServletResponse response,
            @PathVariable(value = "id") Long id) {

        String strResponse = gson.toJson(userService.findById(id));
        try {
            response.getWriter().write(strResponse);
            response.getWriter().flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
//        return ResponseEntity.ok(userService.findById(id));
    }

    @PostMapping("/users/test-validation")
    public ResponseEntity<?> testUserDTO(
            @Valid @RequestBody RegisDTO regisDTO) {
        User user = userService.convertRegisDTOToEntity(regisDTO);
        return ResponseEntity.ok(user);
    }
}
