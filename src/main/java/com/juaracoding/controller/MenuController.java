package com.juaracoding.controller;


import com.juaracoding.dto.validasi.ValMenuDTO;
import com.juaracoding.service.MenuService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    private Map<String,Object> mapSorting = new HashMap<>();
    private final String defaultSortingColumnGroupMenu = "id";

    private void mapSorting()
    {
        mapSorting.put("nama","group");
    }

    @PostMapping("/v1")
    public ResponseEntity<Object> save(@Valid @RequestBody ValMenuDTO valMenuDTO,
                                       HttpServletRequest request
    ){
        return menuService.save(menuService.convertToEntity(valMenuDTO), request);
    }

    @PutMapping("/v1/{id}")
    public ResponseEntity<Object> update(
                                        @PathVariable Long id,
                                        @Valid @RequestBody ValMenuDTO valMenuDTO,
                                        HttpServletRequest request
    ){
        return menuService.update(id, menuService.convertToEntity(valMenuDTO), request);
    }

    @DeleteMapping("/v1/{id}")
    public ResponseEntity<Object> delete(
            @PathVariable Long id,
            HttpServletRequest request
    ){
        return menuService.delete(id, request);
    }

    @GetMapping("/v1/{id}")
    public ResponseEntity<Object> findById(@PathVariable(value = "id") Long id,HttpServletRequest request){
        return menuService.findById(id,request);
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
        /** function yang bersifat global di paging , untuk memberikan default jika data request tidak mengirim format sort dengan benar asc/desc */
        sort   = sort.equalsIgnoreCase("desc")?"desc":"asc";
        Object objSortBy = mapSorting.get(sortBy);
        objSortBy = mapSorting.get(sortBy)==null?defaultSortingColumnGroupMenu:mapSorting.get(sortBy);
//        Pageable pageable =  PageRequest.of(page,
//        (size==null)?10:size,
//        sort.equals("desc")?Sort.by(sortBy).descending():Sort.by(sortBy)
        Pageable pageable =  PageRequest.of(page,size,
                Sort.by("id"));
        return menuService.findAll(pageable,request);
    }

    @PostMapping("/v1/upload-sheet")
    public ResponseEntity<Object> uploadSheet(
            @RequestParam(value = "xlsx-file") MultipartFile csvFile,
            HttpServletRequest request
    ){
        return menuService.uploadDataExcel(csvFile,request);
    }

    @GetMapping("/v1/download-sheet")
    public void downloadSheet(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam(value = "col") String kolom,
            @RequestParam(value = "val") String nilai
    ){
        menuService.downloadReportExcel(kolom, nilai,request,response);
    }
}