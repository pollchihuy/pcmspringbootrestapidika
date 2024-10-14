package com.juaracoding.controller;


import com.juaracoding.dto.validasi.ValAksesDTO;
import com.juaracoding.service.AksesService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/akses")
public class AksesController {

    @Autowired
    private AksesService aksesService;

    private Map<String,Object> mapSorting = new HashMap<>();
    private final String defaultSortingColumnGroupMenu = "id";

    private void mapSorting()
    {
        mapSorting.put("nama","nama");
    }

    /** saat user melakukan mengklik menu group menu , ini adalah api default nya /api/akses*/
    @GetMapping
    @PreAuthorize("hasAuthority('AKSES')")
    public ResponseEntity<Object> defaultPage(
            HttpServletRequest request
    ){
        Pageable pageable =  PageRequest.of(0,10,
                Sort.by("id"));
        return aksesService.findAll(pageable,request);
    }

    @PreAuthorize("hasAuthority('AKSES')")
    @PostMapping("/v1")
    public ResponseEntity<Object> save(@Valid @RequestBody ValAksesDTO valAksesDTO,
                                       HttpServletRequest request
    ){
        return aksesService.save(aksesService.convertToEntity(valAksesDTO), request);
    }

    @PreAuthorize("hasAuthority('AKSES')")
    @PutMapping("/v1/{id}")
    public ResponseEntity<Object> update(
                                        @PathVariable Long id,
                                        @Valid @RequestBody ValAksesDTO valAksesDTO,
                                        HttpServletRequest request
    ){
        return aksesService.update(id, aksesService.convertToEntity(valAksesDTO), request);
    }

    @PreAuthorize("hasAuthority('AKSES')")
    @DeleteMapping("/v1/{id}")
    public ResponseEntity<Object> delete(
            @PathVariable Long id,
            HttpServletRequest request
    ){
        return aksesService.delete(id, request);
    }

    @PreAuthorize("hasAuthority('AKSES')")
    @GetMapping("/v1/{id}")
    public ResponseEntity<Object> findById(@PathVariable(value = "id") Long id,HttpServletRequest request){
        return aksesService.findById(id,request);
    }


//    localhost:8080/api/v1/all/0/asc/id?column_name=id&value=no&size=2
    @GetMapping("/v1/all/{page}/{sort}/{sort-by}")
    public ResponseEntity<Object> findAll(
            @PathVariable(value = "page") Integer page,//page yang ke ?
            @PathVariable(value = "sort") String sort,//asc desc
            @PathVariable(value = "sort-by") String sortBy,// column Name in java Variable,
            @RequestParam("size") Integer size,
            HttpServletRequest request
    ){
        page = (page==null)?0:page;
        sort   = sort.equalsIgnoreCase("desc")?"desc":"asc";
        Object objSortBy = mapSorting.get(sortBy);
        objSortBy = mapSorting.get(sortBy)==null?defaultSortingColumnGroupMenu:mapSorting.get(sortBy);
        Pageable pageable =  PageRequest.of(page,
        (size==null)?10:size,
        sort.equals("desc")?Sort.by(objSortBy.toString()).descending():Sort.by(sortBy));

        return aksesService.findAll(pageable,request);
    }

    @PostMapping("/v1/upload-sheet")
    public ResponseEntity<Object> uploadSheet(
            @RequestParam(value = "xlsx-file") MultipartFile csvFile,
            HttpServletRequest request
    ){
        return aksesService.uploadDataExcel(csvFile,request);
    }

    @PreAuthorize("hasAuthority('AKSES')")
    @GetMapping("/v1/download-sheet")
    public void downloadSheet(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam(value = "col") String kolom,
            @RequestParam(value = "val") String nilai
    ){
        aksesService.downloadReportExcel(kolom, nilai,request,response);
    }

    @PreAuthorize("hasAuthority('AKSES')")
    @GetMapping("/v1/download-pdf")
    public void downloadPDF(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam(value = "col") String kolom,
            @RequestParam(value = "val") String nilai
    ){
        aksesService.generateToPDF(kolom, nilai,request,response);
    }

    @PreAuthorize("hasAuthority('AKSES')")
    @GetMapping("/v1/download-pdf-manual")
    public void downloadPDFManual(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam(value = "col") String kolom,
            @RequestParam(value = "val") String nilai
    ){
        aksesService.generateToPDF(kolom, nilai,request,response);
    }
}