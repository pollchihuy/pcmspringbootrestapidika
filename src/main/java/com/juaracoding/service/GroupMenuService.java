package com.juaracoding.service;

import com.juaracoding.config.OtherConfig;
import com.juaracoding.core.IService;
import com.juaracoding.dto.report.ReportAksesDTO;
import com.juaracoding.dto.report.ReportGroupMenuDTO;
import com.juaracoding.dto.report.ReportMenuDTO;
import com.juaracoding.dto.response.RespGroupMenuDTO;
import com.juaracoding.dto.validasi.ValGroupMenuDTO;
import com.juaracoding.handler.ResponseHandler;
import com.juaracoding.model.Akses;
import com.juaracoding.model.GroupMenu;
import com.juaracoding.model.Menu;
import com.juaracoding.repo.GroupMenuRepo;
import com.juaracoding.util.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import org.apache.commons.io.FileUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;


/**
 *  Platform Code : 001
 *  Modul Code : 001
 *  FV -> Failed Validation
 *  FE -> Failed Error
 */
@Service
public class GroupMenuService implements IService<GroupMenu> {

    @Autowired
    private GroupMenuRepo groupMenuRepo;

    @Autowired
    private ModelMapper modelMapper ;

    @Autowired
    private TransformToDTO transformToDTO;

    @Autowired
    private PdfGenerator pdfGenerator;

    @Autowired
    private SpringTemplateEngine springTemplateEngine;

    private StringBuilder sBuild = new StringBuilder();

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

    // localhost:8080/api/group-menu/12
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
            groupMenuRepo.saveAll(convertListWorkBookToListEntity(lt,1L));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return GlobalFunction.tidakDapatDiproses("FE001001061",request);
        }
        return GlobalFunction.dataBerhasilDisimpan(request);
    }

    public List<GroupMenu> convertListWorkBookToListEntity(List<Map<String, String>> workBookData, Long userId){
        List<GroupMenu> list = new ArrayList<>();
        for (int i = 0; i < workBookData.size(); i++) {
            Map<String, String> map = workBookData.get(i);
            GroupMenu groupMenu = new GroupMenu();
            groupMenu.setName(map.get("nama"));
            groupMenu.setCreatedBy(userId);
            list.add(groupMenu);
        }
        return list;
    }

    @Override
    public void downloadReportExcel(String column, String value, HttpServletRequest request, HttpServletResponse response) {
        List<GroupMenu> groupMenuList = null;
        switch (column){
            case "name": groupMenuList = groupMenuRepo.findByNameContainsIgnoreCase(value);break;
            default:groupMenuList = groupMenuRepo.findAll();break;
        }
        List<RespGroupMenuDTO> listRespGroupMenu = convertToListRespGroupMenuDTO(groupMenuList);
        if (listRespGroupMenu.isEmpty()) {
            GlobalFunction.manualResponse(response,GlobalFunction.dataTidakDitemukan(request));
            return;
        }
        String headerKey = "Content-Disposition";
        sBuild.setLength(0);

        String headerValue = sBuild.append(OtherConfig.getHowToDownloadReport()).append("; filename=groupmenu_").
                append( new SimpleDateFormat("dd-MM-yyyy-HH-mm-ss.SSS").format(new Date())).//audit trails lewat nama file nya
                        append(".xlsx").toString();//buat excel
        response.setHeader(headerKey, headerValue);
        response.setContentType("application/octet-stream");

        Map<String,Object> map = GlobalFunction.convertClassToObject(new RespGroupMenuDTO());
        List<String> listTampungSebentar = new ArrayList<>();
        for (Map.Entry<String,Object> entry : map.entrySet()) {
            listTampungSebentar.add(entry.getKey());
        }
        int intListTampungSebentar = listTampungSebentar.size();
        String [] headerArr = new String[intListTampungSebentar];
        String [] loopDataArr = new String[intListTampungSebentar];
        for (int i = 0; i < intListTampungSebentar; i++) {
            headerArr[i] = GlobalFunction.camelToStandar(String.valueOf(listTampungSebentar.get(i))).toUpperCase();//BIASANYA JUDUL KOLOM DIBUAT HURUF BESAR DENGAN FORMAT STANDARD
            loopDataArr[i] = listTampungSebentar.get(i);
        }

        String[][] strBody = new String[listRespGroupMenu.size()][intListTampungSebentar];
        for (int i = 0; i < listRespGroupMenu.size(); i++) {
            map = GlobalFunction.convertClassToObject(listRespGroupMenu.get(i));
            for (int j = 0; j < intListTampungSebentar; j++) {
                strBody[i][j] = String.valueOf(map.get(loopDataArr[j]));
            }
        }
        new ExcelWriter(strBody, headerArr,"sheet-1", response);
    }

    public void generateToPDF(String column, String value, HttpServletRequest request, HttpServletResponse response){
        List<GroupMenu> groupMenuList = null;
        Map<String,Object> payloadJwt = GlobalFunction.claimsTokenBody(request);
        switch (column){
            case "name": groupMenuList = groupMenuRepo.findByNameContainsIgnoreCase(value);break;
            default:groupMenuList = groupMenuRepo.findAll();break;
        }
        List<ReportGroupMenuDTO> listRespGroupMenu = convertToReportGroupMenuDTO(groupMenuList);
        if (listRespGroupMenu.isEmpty()) {
            GlobalFunction.manualResponse(response,GlobalFunction.dataTidakDitemukan(request));
            return;
        }
        sBuild.setLength(0);
        /** object penampung untuk thymeleaf nanti */
        Map<String,Object> map = new HashMap<>();
        String strHtml = null;
        Context context = new Context();
        Map<String,Object> mapColumnName = GlobalFunction.convertClassToObject(new ReportGroupMenuDTO());
        List<String> listTampungSebentar = new ArrayList<>();
        List<String> listHelper = new ArrayList<>();
        for (Map.Entry<String,Object> entry : mapColumnName.entrySet()) {
            listTampungSebentar.add(GlobalFunction.camelToStandar(entry.getKey()));
            listHelper.add(entry.getKey());
        }
        Map<String,Object> mapTampung = null;
        List<Map<String,Object>> listMap = new ArrayList<>();
        for (int i = 0; i < listRespGroupMenu.size(); i++) {
            mapTampung = GlobalFunction.convertClassToObject(listRespGroupMenu.get(i),null);
            listMap.add(mapTampung);
        }
        map.put("listKolom",listTampungSebentar);
        map.put("listContent",listMap);
        map.put("listHelper",listHelper);
        map.put("timestamp",GlobalFunction.formatingDateDDMMMMYYYY());
        map.put("username",payloadJwt.get("namaLengkap"));
        map.put("totalData",listRespGroupMenu.size());
        map.put("title","REPORT GROUP MENU");
        context.setVariables(map);
        strHtml = springTemplateEngine.process("global-report",context);
        pdfGenerator.htmlToPdf(strHtml,"group-menu",response);
    }

    public void generateToPDFManual(String column, String value, HttpServletRequest request, HttpServletResponse response){
        Map<String,Object> payloadJwt = GlobalFunction.claimsTokenBody(request);
        List<GroupMenu> groupMenuList = null;
        Context context = new Context();
        Map<String,Object> map = new HashMap<>();
        switch (column){
            case "name": groupMenuList = groupMenuRepo.findByNameContainsIgnoreCase(value);break;
            default:groupMenuList = groupMenuRepo.findAll();break;
        }
        List<ReportGroupMenuDTO> listReportGroupMenu = convertToReportGroupMenuDTO(groupMenuList);
        if (listReportGroupMenu.isEmpty()) {
            GlobalFunction.manualResponse(response,GlobalFunction.dataTidakDitemukan(request));
            return;
        }
        String strHtml = null;
        map.put("listContent",listReportGroupMenu);
        map.put("timestamp",GlobalFunction.formatingDateDDMMMMYYYY());
        map.put("username",payloadJwt.get("namaLengkap"));
        map.put("totalData",listReportGroupMenu.size());
        map.put("title","REPORT MENU");
        context.setVariables(map);
        strHtml = springTemplateEngine.process("groupmenu/groupmenureport",context);//path untuk akses html nya
        pdfGenerator.htmlToPdf(strHtml,"group-menu",response);
    }

    public RespGroupMenuDTO convertToGroupMenuDTO(GroupMenu groupMenu){
        return modelMapper.map(groupMenu, RespGroupMenuDTO.class);
    }

    /** khusus mapping untuk report */
    public List<ReportGroupMenuDTO> convertToReportGroupMenuDTO(List<GroupMenu> groupMenuList){
        return modelMapper.map(groupMenuList, new TypeToken<List<ReportGroupMenuDTO>>(){}.getType());
    }

    public GroupMenu convertToEntity(ValGroupMenuDTO groupMenuDTO){
        return modelMapper.map(groupMenuDTO, GroupMenu.class);
    }

    public List<RespGroupMenuDTO> convertToListRespGroupMenuDTO(List<GroupMenu> groupMenuList){
        return modelMapper.map(groupMenuList,new TypeToken<List<RespGroupMenuDTO>>(){}.getType());
    }
}