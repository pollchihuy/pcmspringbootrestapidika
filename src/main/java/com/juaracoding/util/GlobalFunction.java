package com.juaracoding.util;

import com.juaracoding.handler.ResponseHandler;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class GlobalFunction {

    public static ResponseEntity<Object> dataGagalDisimpan(String errorCode, HttpServletRequest request){
        return new ResponseHandler().generateResponse(
                "DATA GAGAL DISIMPAN",
                HttpStatus.INTERNAL_SERVER_ERROR,
                null,
                errorCode,
                request
        );
    }

    public static ResponseEntity<Object> dataTidakDitemukan(HttpServletRequest request){
        return new ResponseHandler().generateResponse(
                "DATA TIDAK DITEMUKAN",
                HttpStatus.BAD_REQUEST,
                null,
                "X-01-002",
                request
        );
    }

    public static ResponseEntity<Object> dataByIdDitemukan(Object object, HttpServletRequest request){
        return new ResponseHandler().generateResponse(
                "DATA DITEMUKAN",
                HttpStatus.OK,
                object,
                "X-01-002",
                request
        );
    }

    public static ResponseEntity<Object> validasiGagal(String message, String errorCode, HttpServletRequest request){
        return new ResponseHandler().generateResponse(
                message,
                HttpStatus.BAD_REQUEST,
                null,
                errorCode,
                request
        );
    }

    public static ResponseEntity<Object> dataBerhasilDisimpan(HttpServletRequest request){
        return new ResponseHandler().generateResponse(
                "DATA BERHASIL DISIMPAN",
                HttpStatus.CREATED,
                null,
                null,
                request
        );
    }

    public static ResponseEntity<Object> dataBerhasilDiubah(HttpServletRequest request){
        return new ResponseHandler().generateResponse(
                "DATA BERHASIL DIUBAH",
                HttpStatus.OK,
                null,
                null,
                request
        );
    }

    public static ResponseEntity<Object> dataBerhasilDihapus(HttpServletRequest request){
        return new ResponseHandler().generateResponse(
                "DATA BERHASIL DIHAPUS",
                HttpStatus.OK,
                null,
                null,
                request
        );
    }
}