package com.juaracoding.service;

import com.juaracoding.model.Akses;
import com.juaracoding.core.IService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public class AksesService implements IService<Akses> {


    @Override
    public ResponseEntity<Object> save(Akses akses, HttpServletRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<Object> update(Long id, Akses akses, HttpServletRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<Object> delete(Long id, HttpServletRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<Object> findAll(Pageable pageable, HttpServletRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<Object> findById(Long id, HttpServletRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<Object> findByParam(String columnName, String value, HttpServletRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<Object> uploadDataCSV(MultipartFile multipartFile, HttpServletRequest request) {
        return null;
    }

    @Override
    public void downloadReportExcel(Pageable pageable, String filterBy, String value, HttpServletRequest request, HttpServletResponse response) {

    }
}
