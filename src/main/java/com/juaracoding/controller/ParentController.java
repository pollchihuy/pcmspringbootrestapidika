package com.juaracoding.controller;

import com.juaracoding.service.ParentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class ParentController {

    @Autowired
    ParentService parentService;

}
