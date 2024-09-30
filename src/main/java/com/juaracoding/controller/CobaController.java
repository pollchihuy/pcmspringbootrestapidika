package com.juaracoding.controller;

import com.juaracoding.util.FileUtility;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@RestController
@RequestMapping("hello")
public class CobaController {

    @PostMapping("/kirim-file")
    public String uploadFile(@RequestParam(value = "kiriman") MultipartFile file){

        String strFileName = file.getOriginalFilename();
        try {
            FileUtility.saveFile("user-files",strFileName,file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return strFileName;
    }
}