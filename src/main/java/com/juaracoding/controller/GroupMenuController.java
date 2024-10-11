package com.juaracoding.controller;


import com.juaracoding.dto.validasi.ValGroupMenuDTO;
import com.juaracoding.service.GroupMenuService;
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
@RequestMapping("api/group-menu")
public class GroupMenuController {

    @Autowired
    private GroupMenuService groupMenuService;

    private Map<String,Object> mapSorting = new HashMap<>();
    private final String defaultSortingColumnGroupMenu = "id";

    private void mapSorting()
    {
        mapSorting.put("nama","group");
    }

    /** saat user melakukan mengklik menu group menu , ini adalah api default nya /api/group-menu*/
    @GetMapping
    @PreAuthorize("hasAuthority('GROUP-MENU')")
    public ResponseEntity<Object> defaultPage(
            HttpServletRequest request
    ){
        Pageable pageable =  PageRequest.of(0,10,
                Sort.by("id"));
        return groupMenuService.findAll(pageable,request);
    }

    @PreAuthorize("hasAuthority('GROUP-MENU')")
    @PostMapping("/v1")
    public ResponseEntity<Object> save(@Valid @RequestBody ValGroupMenuDTO valGroupMenuDTO,
                                       HttpServletRequest request
    ){
        return groupMenuService.save(groupMenuService.convertToEntity(valGroupMenuDTO), request);
    }

    @PreAuthorize("hasAuthority('GROUP-MENU')")
    @PutMapping("/v1/{id}")
    public ResponseEntity<Object> update(
                                        @PathVariable Long id,
                                        @Valid @RequestBody ValGroupMenuDTO valGroupMenuDTO,
                                        HttpServletRequest request
    ){
        return groupMenuService.update(id,groupMenuService.convertToEntity(valGroupMenuDTO), request);
    }

    @PreAuthorize("hasAuthority('GROUP-MENU')")
    @DeleteMapping("/v1/{id}")
    public ResponseEntity<Object> delete(
            @PathVariable Long id,
            HttpServletRequest request
    ){
        return groupMenuService.delete(id, request);
    }

    @PreAuthorize("hasAuthority('GROUP-MENU')")
    @GetMapping("/v1/{id}")
    public ResponseEntity<Object> findById(@PathVariable(value = "id") Long id,HttpServletRequest request){
        return groupMenuService.findById(id,request);
    }


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
        return groupMenuService.findAll(pageable,request);
    }

    @PostMapping("/v1/upload-sheet")
    public ResponseEntity<Object> uploadSheet(
            @RequestParam(value = "xlsx-file") MultipartFile csvFile,
            HttpServletRequest request
    ){
        return groupMenuService.uploadDataExcel(csvFile,request);
    }

    @GetMapping("/v1/download-sheet")
    public void downloadSheet(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam(value = "col") String kolom,
            @RequestParam(value = "val") String nilai
    ){
        groupMenuService.downloadReportExcel(kolom, nilai,request,response);
    }

    @GetMapping("/v1/download-pdf")
    public void downloadPDF(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam(value = "col") String kolom,
            @RequestParam(value = "val") String nilai
    ){
        groupMenuService.generateToPDF(kolom, nilai,request,response);
    }
}