package com.juaracoding.service;

import com.juaracoding.core.IService;
import com.juaracoding.model.Akses;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AksesService implements IService<Akses> {

    @Override
    public ResponseEntity<Object> save(Akses akses) {
        return null;
    }

    @Override
    public ResponseEntity<Object> update(Long id, Akses akses) {
        return null;
    }

    @Override
    public ResponseEntity<Object> delete(Long id, Akses akses) {
        return null;
    }

    @Override
    public ResponseEntity<Object> findAll() {
        return null;
    }

    @Override
    public ResponseEntity<Object> findById(Long id) {
        return null;
    }
}
