package com.juaracoding.service;

import com.juaracoding.config.OtherConfig;
import com.juaracoding.core.IService;
import com.juaracoding.dto.response.RespAksesDTO;
import com.juaracoding.dto.response.RespUserDTO;
import com.juaracoding.dto.response.RespUserDTO;
import com.juaracoding.dto.validasi.RegisDTO;
import com.juaracoding.dto.validasi.ValUserDTO;
import com.juaracoding.model.Akses;
import com.juaracoding.model.User;
import com.juaracoding.model.User;
import com.juaracoding.repo.UserRepo;
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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 *  Platform Code : 002
 *  Modul Code : 004
 *  FV -> Failed Validation
 *  FE -> Failed Error
 */
@Service
public class UserService implements IService<User> {

    @Autowired
    private UserRepo userRepo;

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
    public ResponseEntity<Object> save(User user, HttpServletRequest request) {

        if(user==null){
            return GlobalFunction.validasiGagal("OBJECT NULL","FV002002002",request);
        }

        try {
            userRepo.save(user);
        }catch (Exception e){
            LoggingFile.exceptionStringz("UserService","save",e,OtherConfig.getFlagLogging());
            return GlobalFunction.dataGagalDisimpan("FE002002002",request);
        }

        return GlobalFunction.dataBerhasilDisimpan(request);
    }

    // localhost:8080/api/group-user/12

    @Override
    @Transactional
    public ResponseEntity<Object> update(Long id, User user, HttpServletRequest request) {
       Optional<User> opUser =  userRepo.findById(id);
       if(!opUser.isPresent()){
           return GlobalFunction.dataTidakDitemukan(request);
       }
       try {
           User userDB = opUser.get();
           userDB.setAkses(user.getAkses());
           userDB.setNamaLengkap(user.getNamaLengkap());
           userDB.setTanggalLahir(user.getTanggalLahir());
           userDB.setNoHp(user.getNoHp());
           userDB.setEmail(user.getEmail());
           userRepo.save(userDB);

       }catch (Exception e){
           LoggingFile.exceptionStringz("UserService","update",e,OtherConfig.getFlagLogging());
           return GlobalFunction.dataGagalDisimpan("FE002002011",request);
       }

       return GlobalFunction.dataBerhasilDiubah(request);
    }

    @Override
    public ResponseEntity<Object> delete(Long id, HttpServletRequest request) {
        Optional<User> opUser =  userRepo.findById(id);
        if(!opUser.isPresent()){
            return GlobalFunction.dataTidakDitemukan(request);
        }
        try {
            userRepo.deleteById(id);
        }catch (Exception e){
            LoggingFile.exceptionStringz("UserService","delete",e,OtherConfig.getFlagLogging());
            return GlobalFunction.dataGagalDisimpan("FE002002021",request);
        }
        return GlobalFunction.dataBerhasilDihapus(request);
    }

    @Override
    public ResponseEntity<Object> findAll(Pageable pageable, HttpServletRequest request) {
        Page<User> page = null;
        List<User> list = null;
        try{
            page = userRepo.findAll(pageable);
            list = page.getContent();
            if(list.isEmpty()){
                return GlobalFunction.dataTidakDitemukan(request);
            }
        }catch (Exception e){
            return GlobalFunction.tidakDapatDiproses("FE002002031",request);
        }
        return transformToDTO.
                transformObject(new HashMap<>(),
                        convertToListRespUserDTO(list), page,null,null,null ,request);
    }

    @Override
    public ResponseEntity<Object> findById(Long id, HttpServletRequest request) {
        Optional<User> opUser= userRepo.findById(id);
        if(!opUser.isPresent()){
            return GlobalFunction.dataTidakDitemukan(request);
        }
        return GlobalFunction.dataByIdDitemukan(convertToUserDTO(opUser.get()),request);
    }

    @Override
    public ResponseEntity<Object> findByParam(Pageable pageable,String columnName, String value, HttpServletRequest request) {
        Page<User> page = null;
        List<User> list = null;
        try{
            switch (columnName) {
                case "nama-lengkap": page = userRepo.findByNamaLengkapContainingIgnoreCase(pageable,value);break;
                case "alamat": page = userRepo.findByAlamatContainingIgnoreCase(pageable,value);break;
                case "email": page = userRepo.findByEmailContainingIgnoreCase(pageable,value);break;
                case "username": page = userRepo.findByEmailContainingIgnoreCase(pageable,value);break;
                case "no-hp": page = userRepo.findByNoHpContainingIgnoreCase(pageable,value);break;
                default:page = userRepo.findAll(pageable);break;
            }
            list = page.getContent();
            if(list.isEmpty()){
                return GlobalFunction.dataTidakDitemukan(request);
            }
        }catch (Exception e){
            return GlobalFunction.tidakDapatDiproses("FE002002051",request);
        }
        return transformToDTO.
                transformObject(new HashMap<>(),
                        convertToListRespUserDTO(list), page,columnName,value,null ,request);
    }

    @Override
    @Transactional
    public ResponseEntity<Object> uploadDataExcel(MultipartFile multipartFile, HttpServletRequest request) {
        String message = "";
        if (!ExcelReader.hasWorkBookFormat(multipartFile)) {return GlobalFunction.contentTypeWorkBook("FV002002061",request);}

        try {
            List lt  = new ExcelReader(multipartFile.getInputStream(),"Sheet1").getDataMap();
            if(lt.isEmpty()){
                return GlobalFunction.dataWorkBookKosong("FV002002062",request);
            }
            //KARENA DATA LIST MAP<String,String> maka harus di convert menjadi Entity
            userRepo.saveAll(convertListWorkBookToListEntity(lt,1L));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return GlobalFunction.tidakDapatDiproses("FE002002061",request);
        }
        return GlobalFunction.dataBerhasilDisimpan(request);
    }

    public List<User> convertListWorkBookToListEntity(List<Map<String, String>> workBookData, Long userId){
        List<User> list = new ArrayList<>();
        for (int i = 0; i < workBookData.size(); i++) {
            Map<String, String> map = workBookData.get(i);
            User user = new User();
            user.setNamaLengkap(map.get("nama_lengkap"));
            user.setNoHp(map.get("no_hp"));
            user.setAlamat(map.get("alamat"));
            user.setUmur(Integer.parseInt(map.get("umur")));
            user.setEmail(map.get("email"));
            list.add(user);
        }
        return list;
    }

    @Override
    public void downloadReportExcel(String filterBy, String value, HttpServletRequest request, HttpServletResponse response) {
        List<User> userList = null;
        switch (filterBy){
            case "nama-lengkap": userList = userRepo.findByNamaLengkapContainingIgnoreCase(value);break;
            case "alamat": userList = userRepo.findByAlamatContainingIgnoreCase(value);break;
            case "email": userList = userRepo.findByEmailContainingIgnoreCase(value);break;
            case "username": userList = userRepo.findByEmailContainingIgnoreCase(value);break;
            case "no-hp": userList = userRepo.findByNoHpContainingIgnoreCase(value);break;
            case "tanggal-lahir": userList = userRepo.findByTanggalLahirContainingIgnoreCase(value);break;

            default:userList = userRepo.findAll();break;
        }
        List<com.juaracoding.dto.response.RespUserDTO> listRespUser = convertToListRespUserDTO(userList);
        if (listRespUser.isEmpty()) {
            GlobalFunction.manualResponse(response,GlobalFunction.dataTidakDitemukan(request));
            return;
        }
        sBuild.setLength(0);
        String headerKey = "Content-Disposition";
        sBuild.setLength(0);

        String headerValue = sBuild.append(OtherConfig.getHowToDownloadReport()).append("; filename=user_").
                append( new SimpleDateFormat("dd-MM-yyyy-HH-mm-ss.SSS").format(new Date())).//audit trails lewat nama file nya
                        append(".xlsx").toString();//buat excel
        response.setHeader(headerKey, headerValue);
        response.setContentType("application/octet-stream");

        String [] strHeaderArr = {"ID","NAMA MENU"};
        String[][] strBody = new String[listRespUser.size()][strHeaderArr.length];
        String strIdUser = "";// VARIABLE UNTUK MEMFILTER DATA NYA TERLEBIH DAHULU
        String strNamaUser = "";// VARIABLE UNTUK MEMFILTER DATA NYA TERLEBIH DAHULU
        for (int i = 0; i < listRespUser.size(); i++) {
            strIdUser = listRespUser.get(i).getId() == null ? "-" : String.valueOf(listRespUser.get(i).getId());//null handling
            strNamaUser = listRespUser.get(i).getNama() == null ? "-" : listRespUser.get(i).getNama();//null handling
            strBody[i][0] = strIdUser;
            strBody[i][1] = strNamaUser;
        }
        new ExcelWriter(strBody, strHeaderArr,"sheet-1", response);
    }

    public void generateToPDF(String column, String value, HttpServletRequest request, HttpServletResponse response){
        List<User> userList = null;
        switch (column){
            case "nama-lengkap": userList = userRepo.findByNamaLengkapContainingIgnoreCase(value);break;
            case "alamat": userList = userRepo.findByAlamatContainingIgnoreCase(value);break;
            case "email": userList = userRepo.findByEmailContainingIgnoreCase(value);break;
            case "username": userList = userRepo.findByEmailContainingIgnoreCase(value);break;
            case "no-hp": userList = userRepo.findByNoHpContainingIgnoreCase(value);break;
            case "tanggal-lahir": userList = userRepo.findByTanggalLahirContainingIgnoreCase(value);break;
            default:userList = userRepo.findAll();break;
        }
        List<RespUserDTO> listRespMenu = convertToListRespUserDTO(userList);
        if (listRespMenu.isEmpty()) {
            GlobalFunction.manualResponse(response,GlobalFunction.dataTidakDitemukan(request));
            return;
        }
        try {
            FileUtils.forceMkdir(new File(OtherConfig.getPathGeneratePDF()));
        } catch (IOException e) {
            LoggingFile.exceptionStringz("UserService",
                    "generateToPDF",e,OtherConfig.getFlagLogging());
        }
        Map<String,Object> map = new HashMap<>();
        String strHtml = null;
        Context context = new Context();
        Map<String,Object> mapColumnName = GlobalFunction.convertClassToObject(new RespUserDTO());
        List<String> listTampungSebentar = new ArrayList<>();
        List<String> listHelper = new ArrayList<>();//untuk mapping otomatis di html nya
        for (Map.Entry<String,Object> entry : mapColumnName.entrySet()) {
            listTampungSebentar.add(GlobalFunction.camelToStandar(entry.getKey()));
            listHelper.add(entry.getKey());
        }
        Map<String,Object> mapTampung = null;
        List<Map<String,Object>> listMap = new ArrayList<>();
        for (int i = 0; i < listRespMenu.size(); i++) {
            mapTampung = GlobalFunction.convertClassToObject(listRespMenu.get(i),null);
            listMap.add(mapTampung);
        }
        map.put("listKolom",listTampungSebentar);
        map.put("listContent",listMap);
        map.put("listHelper",listHelper);
        map.put("timestamp",GlobalFunction.formatingDateDDMMMMYYYY());
        map.put("username","pollchihuy");//saat ini saya hardcode , nanti diambil dari value di token yah
        map.put("totalData",listRespMenu.size());
        map.put("title","REPORT AKSES");
        context.setVariables(map);
        strHtml = springTemplateEngine.process("global-report",context);
        try {
            pdfGenerator.htmlToPdf(strHtml,"user");
        } catch (IOException e) {
            LoggingFile.exceptionStringz("UserService",
                    "generateToPDF",e,OtherConfig.getFlagLogging());
        }
    }

    public com.juaracoding.dto.response.RespUserDTO convertToUserDTO(User groupUser){
        return modelMapper.map(groupUser, com.juaracoding.dto.response.RespUserDTO.class);
    }

    public User convertToEntity(ValUserDTO groupUserDTO){
        return modelMapper.map(groupUserDTO, User.class);
    }

    public User convertToEntity(RegisDTO regisDTO){
        return modelMapper.map(regisDTO, User.class);
    }

    public List<com.juaracoding.dto.response.RespUserDTO> convertToListRespUserDTO(List<User> groupUserList){
        return modelMapper.map(groupUserList,new TypeToken<List<com.juaracoding.dto.response.RespUserDTO>>(){}.getType());
    }
}