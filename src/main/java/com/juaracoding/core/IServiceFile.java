package com.juaracoding.core;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface IServiceFile {

    public ResponseEntity<Object> uploadImage(MultipartFile file, HttpServletRequest request);//081-090

}
