package com.juaracoding.service;

import com.juaracoding.core.IService;
import com.juaracoding.dto.response.UserDTO;
import com.juaracoding.dto.validasi.RegisDTO;
import com.juaracoding.model.User;
import com.juaracoding.repo.UserRepo;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.*;


/**
 * inventory platform code = INV
 * code module user = 07
 * fe -> 001 - 010
 * fv
 * 0107FV005
 */
@Service
public class UserService implements IService<User> {

    @Autowired
    UserRepo userRepo;

    private ModelMapper modelMapper ;
    public UserService() {
        modelMapper = new ModelMapper();
    }

    @Override
    public ResponseEntity<Object> save(User user) {//001-010
//        userRepo.save(user);
        return null;
    }

    @Override
    public ResponseEntity<Object> delete(Long id, User user) {//011-020
        return null;
    }

    @Override
    public ResponseEntity<Object> findAll() {
        Map<String,Object> m = new HashMap<>();
//        m.put("data", getUserDTO(userRepo.findAll()));
        m.put("data", getUserDTO(userRepo.findAll(),null));
//        m.put("data",userRepo.findAll());

        return new ResponseEntity<>(m,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> findById(Long id) {
        Map<String,Object> m = new HashMap<>();
//        m.put("data", userRepo.findById(id));
        m.put("data", getUserDTO(userRepo.findById(id),null));
        return new ResponseEntity<>(m,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> update(Long id, User user) {
        return null;
    }

//    public List<UserDTO> getUserDTO(Iterable<User> user) {
//        List<User> target = new ArrayList<>();
//        user.forEach(target::add);
//
//        List<UserDTO> userDTOS = new ArrayList<>();
//        for(User u : user){
//            UserDTO userDTO = new UserDTO();
//            userDTO.setId(u.getId());
//            userDTO.setUsername(u.getUsername());
////            userDTO.setNoHp(u.getNoHp());
//            userDTO.setEmail(u.getEmail());
//            userDTO.setAlamat(u.getAlamat());
//            userDTO.setNama(u.getNama());
//            userDTO.setTanggalLahir(u.getTanggalLahir());
//            userDTO.setUmur(u.getUmur());
//            userDTOS.add(userDTO);
//        }
//        return userDTOS;
//    }
//
//    public UserDTO getUserDTO(Optional<User> user) {
//        UserDTO userDTO = new UserDTO();
//        if(user.isPresent()){
//                User u = user.get();
//                userDTO.setId(u.getId());
//                userDTO.setUsername(u.getUsername());
////                userDTO.setNoHp(u.getNoHp());
////                userDTO.setEmail(u.getEmail());
//                userDTO.setAlamat(u.getAlamat());
//                userDTO.setNama(u.getNama());
//
//                userDTO.setTanggalLahir(u.getTanggalLahir());
//                userDTO.setUmur(u.getUmur());
//        }
//        return userDTO;
//    }

    public UserDTO getUserDTO(Optional<User> user,String strNull) {
        if(user.isPresent()){
            UserDTO u = modelMapper.map(user.get(),UserDTO.class);
            return u;
        }
        return new UserDTO();
    }

    public List<UserDTO> getUserDTO(Iterable<User> user,String strNull) {
        // ini buat convert dari iterator ke List
        List<User> target = new ArrayList<>();
        user.forEach(target::add);

        List<UserDTO> userDTOS = new ArrayList<>();
        if(!target.isEmpty()){
            List<UserDTO> u = modelMapper.map(target,new TypeToken<List<UserDTO>>(){}.getType());
            return u;
        }

        return new ArrayList<>();
    }

    public User convertUserDTOToEntity(UserDTO userDTO) {
        return modelMapper.map(userDTO,User.class);
    }

    public User convertRegisDTOToEntity(RegisDTO regisDTO) {
        return modelMapper.map(regisDTO,User.class);
    }




}