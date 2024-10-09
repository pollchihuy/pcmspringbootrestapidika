//package com.juaracoding.service;
//
//import com.juaracoding.core.IService;
//import com.juaracoding.dto.response.UserDTO;
//import com.juaracoding.dto.validasi.RegisDTO;
//import com.juaracoding.model.User;
//import com.juaracoding.repo.UserRepo;
//import com.juaracoding.util.GlobalFunction;
//import com.juaracoding.util.Patcher;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.modelmapper.ModelMapper;
//import org.modelmapper.TypeToken;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Pageable;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//import java.util.*;
//
//
///**
// * inventory platform code = 001
// * code module user = 004
// */
//@Service
//public class UserServiceOld implements IService<User> {
//
//    @Autowired
//    UserRepo userRepo;
//
//    @Autowired
//    Patcher patcher;
//
//    private ModelMapper modelMapper ;
//    public UserServiceOld() {
//        modelMapper = new ModelMapper();
//    }
//
//    @Override
//    public ResponseEntity<Object> save(User user, HttpServletRequest request) {//001-010
//        return null;
//    }
//
//    @Override
//    public ResponseEntity<Object> delete(Long id, HttpServletRequest request) {//011-020
//        return null;
//    }
//
//    @Override
//    public ResponseEntity<Object> findAll(Pageable pageable, HttpServletRequest request) {
//        Map<String,Object> m = new HashMap<>();
////        m.put("data", getUserDTO(userRepo.findAll()));
//        m.put("data", getUserDTO(userRepo.findAll(),null));
////        m.put("data",userRepo.findAll());
//        return new ResponseEntity<>(m,HttpStatus.OK);
//    }
//
//    @Override
//    public ResponseEntity<Object> findById(Long id, HttpServletRequest request) {
//        Map<String,Object> m = new HashMap<>();
////        m.put("data", userRepo.findById(id));
//        m.put("data", getUserDTO(userRepo.findById(id),null));
//        User u = new User();
////        u.setCreatedAt(new Date());
//        return new ResponseEntity<>(m,HttpStatus.OK);
//    }
//
//    @Override
//    public ResponseEntity<Object> update(Long id, User user, HttpServletRequest request) {
//        return null;
//    }
//
//    public UserDTO getUserDTO(Optional<User> user,String strNull) {
//        if(user.isPresent()){
//            UserDTO u = modelMapper.map(user.get(),UserDTO.class);
//            return u;
//        }
//        return new UserDTO();
//    }
//
//    public List<UserDTO> getUserDTO(Iterable<User> user,String strNull) {
//        // ini buat convert dari iterator ke List
//        List<User> target = new ArrayList<>();
//        user.forEach(target::add);
//
//        List<UserDTO> userDTOS = new ArrayList<>();
//        if(!target.isEmpty()){
//            List<UserDTO> u = modelMapper.map(target,new TypeToken<List<UserDTO>>(){}.getType());
//            return u;
//        }
//        return new ArrayList<>();
//    }
//
//    public User convertUserDTOToEntity(UserDTO userDTO) {
//        return modelMapper.map(userDTO,User.class);
//    }
//
//    public User convertRegisDTOToEntity(RegisDTO regisDTO) {
//        return modelMapper.map(regisDTO,User.class);
//    }
//
//    @Override
//    public ResponseEntity<Object> findByParam(Pageable pageable, String columnName, String value,HttpServletRequest request) {
//        return null;
//    }
//
//    @Override
//    public ResponseEntity<Object> uploadDataExcel(MultipartFile multipartFile, HttpServletRequest request) {
//        return null;
//    }
//
//    @Override
//    public void downloadReportExcel(String filterBy, String value, HttpServletRequest request, HttpServletResponse response) {
//
//    }
//
//    public ResponseEntity<Object> updatePatch(User user, HttpServletRequest request) {
//        // pertama - tama harus autowired object Patcher
//        // kedua - dua cari user berdasarkan id
//        // ketiga - tiga validasi user tersebut apakah ada atau tidak
//        // ke empat-empat panggil fungsi khusus handling patcher untuk class user
//        Optional<User> optionalUser = userRepo.findById(user.getId());
//        if(!optionalUser.isPresent()) {
//            return GlobalFunction.dataTidakDitemukan(request);
//        }
//        User nextUser = optionalUser.get();
//        try {
//            patcher.userPatcher(nextUser,user);
//        } catch (IllegalAccessException e) {
//            return GlobalFunction.dataGagalDiubah("FE001081",request);
//        }
//        return GlobalFunction.dataBerhasilDiubah(request);
//    }
//}