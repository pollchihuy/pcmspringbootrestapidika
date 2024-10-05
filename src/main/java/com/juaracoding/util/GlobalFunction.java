package com.juaracoding.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.juaracoding.handler.ResponseHandler;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

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

    public static ResponseEntity<Object> contentTypeWorkBook(String errorCode, HttpServletRequest request)
    {
        return new ResponseHandler().generateResponse("Ekstensi File harus .xlsx / Format Workbook bukan dari template yang diunduh dari Platform",
                HttpStatus.BAD_REQUEST,
                null,
                errorCode, request);
    }

    public static ResponseEntity<Object> dataWorkBookKosong(String errorCode, HttpServletRequest request)
    {
        return new ResponseHandler().generateResponse("Data Di File Excel Kosong / Tidak ada data yang dapat diproses",
                HttpStatus.BAD_REQUEST,
                null,
                errorCode, request);
    }



    public static ResponseEntity<Object> dataGagalDiubah(String errorCode, HttpServletRequest request){
        return new ResponseHandler().generateResponse(
                "DATA GAGAL DIUBAH",
                HttpStatus.INTERNAL_SERVER_ERROR,
                null,
                errorCode,
                request
        );
    }

    public static ResponseEntity<Object> tidakDapatDiproses(String errorCode, HttpServletRequest request){
        return new ResponseHandler().generateResponse(
                "PERMINTAAN TIDAK DAPAT DIPROSES",
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

    /**
     * Digunakan untuk functional download file dll
     * yang bersifat tidak mengembalikan data response
     */
    public static void manualResponse(HttpServletResponse response, ResponseEntity<Object> respObject){
        try {
            response.getWriter().write(convertObjectToJson(respObject.getBody()));
            response.setStatus(respObject.getStatusCodeValue());
        } catch (IOException e) {

        }
    }

    public static ResponseEntity<Object> dataByIdDitemukan(Object object, HttpServletRequest request){
        return new ResponseHandler().generateResponse(
                "DATA DITEMUKAN",
                HttpStatus.OK,
                object,
                null,
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

    /**
     * Mengconvert Object java ke JSON
     */
    public static String convertObjectToJson(Object object) throws JsonProcessingException {
        if (object == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }
}