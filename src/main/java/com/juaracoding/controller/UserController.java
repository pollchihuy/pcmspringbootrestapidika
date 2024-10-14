package com.juaracoding.controller;


import com.juaracoding.dto.validasi.ValUserDTO;
import com.juaracoding.service.UserService;
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
@RequestMapping("api/user")
public class UserController {

    @Autowired
    private UserService userService;

    private Map<String,Object> sortMap = new HashMap<>();
    private final String defaultSortingColumnGroupMenu = "id";

    private void mapSorting()
    {
        sortMap.put("nama","namaLengkap");
        sortMap.put("tanggal-lahir","tanggalLahir");
        sortMap.put("alamat","alamat");
        sortMap.put("username","username");
        sortMap.put("no-hp","noHp");
        sortMap.put("umur","umur");
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/v1")
    public ResponseEntity<Object> save(@Valid @RequestBody ValUserDTO valUserDTO,
                                       HttpServletRequest request
    ){
        return userService.save(userService.convertToEntity(valUserDTO), request);
    }

    @PreAuthorize("hasAuthority('USER')")
    @PutMapping("/v1/{id}")
    public ResponseEntity<Object> update(
                                        @PathVariable Long id,
                                        @Valid @RequestBody ValUserDTO valUserDTO,
                                        HttpServletRequest request
    ){
        return userService.update(id, userService.convertToEntity(valUserDTO), request);
    }

    @PreAuthorize("hasAuthority('USER')")
    @DeleteMapping("/v1/{id}")
    public ResponseEntity<Object> delete(
            @PathVariable Long id,
            HttpServletRequest request
    ){
        return userService.delete(id, request);
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/v1/{id}")
    public ResponseEntity<Object> findById(@PathVariable(value = "id") Long id,HttpServletRequest request){
        return userService.findById(id,request);
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
        Object objSortBy = sortMap.get(sortBy);
        objSortBy = sortMap.get(sortBy)==null?defaultSortingColumnGroupMenu: sortMap.get(sortBy);
        Pageable pageable =  PageRequest.of(page,
                (size==null)?10:size,
                sort.equals("desc")?Sort.by(objSortBy.toString()).descending():Sort.by(sortBy));
        return userService.findAll(pageable,request);
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/v1/{page}/{sort}/{sort-by}")
    public ResponseEntity<Object> findByParam(
            @PathVariable(value = "page") Integer page,//page yang ke ?
            @PathVariable(value = "sort") String sort,//asc desc
            @PathVariable(value = "sort-by") String sortBy,// column Name in java Variable,
            @RequestParam("size") Integer size,
            @RequestParam("col") String column,
            @RequestParam("val") String value,
            HttpServletRequest request
    ){
        page = (page==null)?0:page;
        sort   = sort.equalsIgnoreCase("desc")?"desc":"asc";
        Object objSortBy = sortMap.get(sortBy);
        objSortBy = sortMap.get(sortBy)==null?defaultSortingColumnGroupMenu: sortMap.get(sortBy);
        Pageable pageable =  PageRequest.of(page,
                (size==null)?10:size,
                sort.equals("desc")?Sort.by(objSortBy.toString()).descending():Sort.by(sortBy));

        return userService.findByParam(pageable,column,value,request);
    }

    @PostMapping("/v1/upload-sheet")
    public ResponseEntity<Object> uploadSheet(
            @RequestParam(value = "xlsx-file") MultipartFile csvFile,
            HttpServletRequest request
    ){
        return userService.uploadDataExcel(csvFile,request);
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/v1/download-sheet")
    public void downloadSheet(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam(value = "col") String kolom,
            @RequestParam(value = "val") String nilai
    ){
        userService.downloadReportExcel(kolom, nilai,request,response);
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/v1/download-pdf")
    public void downloadPDF(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam(value = "col") String kolom,
            @RequestParam(value = "val") String nilai
    ){
        userService.generateToPDF(kolom, nilai,request,response);
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/v1/download-pdf-manual")
    public void downloadPDFManual(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam(value = "col") String kolom,
            @RequestParam(value = "val") String nilai
    ){
        userService.generateReportToPDFManual(kolom, nilai,request,response);
    }
}