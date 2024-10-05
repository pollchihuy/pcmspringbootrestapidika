package com.juaracoding.service;

import com.juaracoding.config.OtherConfig;
import com.juaracoding.core.IService;
import com.juaracoding.dto.response.RespGroupMenuDTO;
import com.juaracoding.dto.validasi.ValGroupMenuDTO;
import com.juaracoding.model.GroupMenu;
import com.juaracoding.repo.GroupMenuRepo;
import com.juaracoding.util.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.*;


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
    private ModelMapper modelMapper ;

    @Autowired
    private TransformToDTO transformToDTO;
    private StringBuilder sBuild = new StringBuilder();
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
        Page<GroupMenu> page = null;
        List<GroupMenu> list = null;
        try{
            page = groupMenuRepo.findAll(pageable);
            list = page.getContent();
            if(list.isEmpty()){
                return GlobalFunction.dataTidakDitemukan(request);
            }
        }catch (Exception e){
            return GlobalFunction.tidakDapatDiproses("FE001001031",request);
        }
        return transformToDTO.
                transformObject(new HashMap<>(),
                        convertToListRespGroupMenuDTO(list), page,null,null,null ,request);
    }

    @Override
    public ResponseEntity<Object> findById(Long id, HttpServletRequest request) {
        Optional<GroupMenu> opGroupMenu=groupMenuRepo.findById(id);
        if(!opGroupMenu.isPresent()){
            return GlobalFunction.dataTidakDitemukan(request);
        }
        return GlobalFunction.dataByIdDitemukan(convertToGroupMenuDTO(opGroupMenu.get()),request);
    }

    @Override
    public ResponseEntity<Object> findByParam(Pageable pageable,String columnName, String value, HttpServletRequest request) {
        Page<GroupMenu> page = null;
        List<GroupMenu> list = null;
        try{
            switch (columnName) {
                case "name": page = groupMenuRepo.findByNameContainsIgnoreCase(pageable,value);break;
                default:page = groupMenuRepo.findAll(pageable);break;
            }
            list = page.getContent();
            if(list.isEmpty()){
                return GlobalFunction.dataTidakDitemukan(request);
            }
        }catch (Exception e){
            return GlobalFunction.tidakDapatDiproses("FE001001051",request);
        }
        return transformToDTO.
                transformObject(new HashMap<>(),
                        convertToListRespGroupMenuDTO(list), page,columnName,value,null ,request);
    }

    @Override
    @Transactional
    public ResponseEntity<Object> uploadDataExcel(MultipartFile multipartFile, HttpServletRequest request) {
        String message = "";
        if (!ExcelReader.hasWorkBookFormat(multipartFile)) {return GlobalFunction.contentTypeWorkBook("FV001001061",request);}

        try {
            List lt  = new ExcelReader(multipartFile.getInputStream(),"Sheet1").getDataMap();
            if(lt.isEmpty()){
                return GlobalFunction.dataWorkBookKosong("FV001001062",request);
            }
            //KARENA DATA LIST MAP<String,String> maka harus di convert menjadi Entity
            groupMenuRepo.saveAll(convertListCsvToListEntity(lt,1L));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return GlobalFunction.tidakDapatDiproses("FE001001061",request);
        }
        return GlobalFunction.dataBerhasilDisimpan(request);
    }

    public List<GroupMenu> convertListCsvToListEntity(List<Map<String, String>> workBookData,Long userId){
        List<GroupMenu> list = new ArrayList<>();
        for (int i = 0; i < workBookData.size(); i++) {
            Map<String, String> map = workBookData.get(i);
            GroupMenu groupMenu = new GroupMenu();
            groupMenu.setName(map.get("name"));
            groupMenu.setCreatedBy(userId);
            list.add(groupMenu);
        }
        return list;
    }

    @Override
    public void downloadReportExcel(String filterBy, String value, HttpServletRequest request, HttpServletResponse response) {
        List<GroupMenu> groupMenuList = null;
        switch (filterBy){
            case "name": groupMenuList = groupMenuRepo.findByNameContainsIgnoreCase(value);break;
            default:groupMenuList = groupMenuRepo.findAll();break;
        }
        List<RespGroupMenuDTO> listRespGroupMenu = convertToListRespGroupMenuDTO(groupMenuList);
        if (listRespGroupMenu.isEmpty()) {
            GlobalFunction.manualResponse(response,GlobalFunction.dataTidakDitemukan(request));
            return;
        }
        sBuild.setLength(0);
        String headerKey = "Content-Disposition";
        sBuild.setLength(0);

        String headerValue = sBuild.append(OtherConfig.getHowToDownloadReport()).append("; filename=groupmenu_").
                append( new SimpleDateFormat("dd-MM-yyyy-HH-mm-ss.SSS").format(new Date())).//audit trails lewat nama file nya
                        append(".xlsx").toString();//buat excel
//                        append(".csv").toString();//buat csv
        response.setHeader(headerKey, headerValue);
        response.setContentType("application/octet-stream");

        String [] strHeaderArr = {"ID","NAMA GROUP MENU"};
        String[][] strBody = new String[listRespGroupMenu.size()][strHeaderArr.length];
        String strIdGroup = "";// VARIABLE UNTUK MEMFILTER DATA NYA TERLEBIH DAHULU
        String strNamaGroup = "";// VARIABLE UNTUK MEMFILTER DATA NYA TERLEBIH DAHULU
        for (int i = 0; i < listRespGroupMenu.size(); i++) {
            strIdGroup = listRespGroupMenu.get(i).getId() == null ? "-" : String.valueOf(listRespGroupMenu.get(i).getId());//null handling
            strNamaGroup = listRespGroupMenu.get(i).getName() == null ? "-" : listRespGroupMenu.get(i).getName();//null handling
            strBody[i][0] = strIdGroup;
            strBody[i][1] = strNamaGroup;
        }
//        new CSVWriter(strBody, strHeaderArr, response);
        new ExcelWriter(strBody, strHeaderArr,"sheet-1", response);
    }

    public RespGroupMenuDTO convertToGroupMenuDTO(GroupMenu groupMenu){
        return modelMapper.map(groupMenu, RespGroupMenuDTO.class);
    }

    public GroupMenu convertToEntity(ValGroupMenuDTO groupMenuDTO){
        return modelMapper.map(groupMenuDTO, GroupMenu.class);
    }

    public List<RespGroupMenuDTO> convertToListRespGroupMenuDTO(List<GroupMenu> groupMenuList){
        return modelMapper.map(groupMenuList,new TypeToken<List<RespGroupMenuDTO>>(){}.getType());
    }
}