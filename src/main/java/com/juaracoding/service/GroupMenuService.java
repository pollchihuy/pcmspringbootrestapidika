package com.juaracoding.service;


import com.juaracoding.config.OtherConfig;
import com.juaracoding.core.IService;
import com.juaracoding.model.GroupMenu;
import com.juaracoding.repo.GroupMenuRepo;
import com.juaracoding.util.GlobalFunction;
import com.juaracoding.util.LoggingFile;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;


/**
 *  Platform Code : 001
 *  Modul Code : 001
 *  FV -> Failed Validation
 *  FE -> Failed Error
 */
@Service
public class GroupMenuService implements IService<GroupMenu> {



    private GroupMenuRepo groupMenuRepo;

    @Autowired
    public GroupMenuService(GroupMenuRepo groupMenuRepo) {
        this.groupMenuRepo = groupMenuRepo;
    }

    @Override
    public ResponseEntity<Object> save(GroupMenu groupMenu, HttpServletRequest request) {
        if(groupMenu==null){
            return GlobalFunction.validasiGagal("OBJECT NULL","FV001001001",request);
        }

        try {
            groupMenuRepo.save(groupMenu);
        }catch (Exception e){
            LoggingFile.exceptionStringz("GroupMenuService","save",e,OtherConfig.getFlagLogging());
            return GlobalFunction.dataGagalDisimpan("FE001001001",request);
        }
        return GlobalFunction.dataBerhasilDisimpan(request);
    }

    @Override
    @Transactional
    public ResponseEntity<Object> update(Long id, GroupMenu groupMenu, HttpServletRequest request) {

       Optional<GroupMenu> opGroupMenu =  groupMenuRepo.findById(id);
       if(!opGroupMenu.isPresent()){
           return GlobalFunction.dataTidakDitemukan(request);
       }
       try {
           GroupMenu groupMenuDB = opGroupMenu.get();
           groupMenuDB.setName(groupMenu.getName());
           groupMenuDB.setUpdatedBy(1L);
       }catch (Exception e){
           LoggingFile.exceptionStringz("GroupMenuService","update",e,OtherConfig.getFlagLogging());
           return GlobalFunction.dataGagalDisimpan("FE001001011",request);
       }

       return GlobalFunction.dataBerhasilDiubah(request);
    }

    @Override
    public ResponseEntity<Object> delete(Long id, HttpServletRequest request) {
        Optional<GroupMenu> opGroupMenu =  groupMenuRepo.findById(id);
        if(!opGroupMenu.isPresent()){
            return GlobalFunction.dataTidakDitemukan(request);
        }
        try {
            groupMenuRepo.deleteById(id);
        }catch (Exception e){
            LoggingFile.exceptionStringz("GroupMenuService","delete",e,OtherConfig.getFlagLogging());
            return GlobalFunction.dataGagalDisimpan("FE001001021",request);
        }
        return GlobalFunction.dataBerhasilDihapus(request);
    }

    @Override
    public ResponseEntity<Object> findAll(Pageable pageable, HttpServletRequest request) {

        return null;
    }

    @Override
    public ResponseEntity<Object> findById(Long id, HttpServletRequest request) {
        Optional<GroupMenu> opGroupMenu=groupMenuRepo.findById(id);
        if(!opGroupMenu.isPresent()){
            return GlobalFunction.dataTidakDitemukan(request);
        }
        return GlobalFunction.dataByIdDitemukan(opGroupMenu.get(),request);
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
