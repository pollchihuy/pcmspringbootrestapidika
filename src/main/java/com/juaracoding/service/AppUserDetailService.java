package com.juaracoding.service;

import com.juaracoding.config.OtherConfig;
import com.juaracoding.dto.response.RespMenuDTO;
import com.juaracoding.dto.validasi.*;
import com.juaracoding.model.Akses;
import com.juaracoding.model.Menu;
import com.juaracoding.model.User;
import com.juaracoding.repo.UserRepo;
import com.juaracoding.security.BcryptImpl;
import com.juaracoding.security.Crypto;
import com.juaracoding.security.JwtUtility;
import com.juaracoding.util.GlobalFunction;
import com.juaracoding.util.LoggingFile;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Transactional
public class AppUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private JwtUtility jwtUtility;

    private Map<String,Object> m = new HashMap<>();

    private ModelMapper modelMapper = new ModelMapper();

    private Random rand = new Random();

    public ResponseEntity<Object> verifyUser(User user,HttpServletRequest request){
        String s = user.getUsername();
        Optional<User> opUserResult = userRepo.findTop1ByUsernameOrNoHpOrEmailAndIsRegistered(s,s,s,true);
        User nextUser = null;
        if (!opUserResult.isPresent()) {
            throw new UsernameNotFoundException("User not found");
        }
        nextUser = opUserResult.get();
        List<String> listAkses = new ArrayList<>();
        List<Menu> menus = nextUser.getAkses().getMenuList();
        if (menus.isEmpty()) {
            GlobalFunction.customDataDitemukan("User Belum Diberi Akses",null,request);
        }
        for (int i = 0; i < menus.size(); i++) {
            listAkses.add(menus.get(i).getNama());
        }
        m.put("akses", listAkses);
        return GlobalFunction.customDataDitemukan("User Dikenal",m,request);
    }

    public ResponseEntity<Object> platformLogin(User user,HttpServletRequest request){
        String s = user.getUsername();
        Optional<User> opUserResult = userRepo.findTop1ByUsernameOrNoHpOrEmailAndIsRegistered(s,s,s,true);
        User nextUser = null;
        if (!opUserResult.isPresent()) {
            throw new UsernameNotFoundException("User not found");
        }
        nextUser = opUserResult.get();
        if(!BcryptImpl.verifyHash(user.getPassword()+user.getUsername(),
                nextUser.getPassword()))//dicombo dengan userName
        {
            return GlobalFunction.customReponse("X-03-031","username dan password salah",request);
        }
        List<String> listAkses = new ArrayList<>();
        List<Menu> menus = nextUser.getAkses().getMenuList();
        if (menus.isEmpty()) {
            GlobalFunction.customDataDitemukan("User Belum Diberi Akses",null,request);
        }
        for (int i = 0; i < menus.size(); i++) {
            listAkses.add(menus.get(i).getNama());
        }

        UserDetails userDetails = loadUserByUsername(user.getUsername());
        /** start jwt config */
        Map<String,Object> mapForClaims = new HashMap<>();
        mapForClaims.put("uid",nextUser.getId());//payload
        mapForClaims.put("ml",nextUser.getEmail());//payload
        mapForClaims.put("nl",nextUser.getNamaLengkap());//payload
        mapForClaims.put("pn",nextUser.getNoHp());//payload
        String token = jwtUtility.generateToken(userDetails,mapForClaims);
        /** end jwt config */
        m.put("token", Crypto.performEncrypt(token));
        m.put("akses", listAkses);
        return  GlobalFunction.customDataDitemukan("Login Berhasil",m,request);
    }

    /** kuota security untuk otentikasi dibedakan dengan modul lain karena modulnya digabungkan dengan global handler
     *  dikarenakan bersifat global dimana seluruh platform nantinya akan selalu melewati API ini terlebih dahulu
     *  untuk proses otentikasinya
     */
    public ResponseEntity<Object> doLogin(User user, HttpServletRequest request) throws UsernameNotFoundException {

        String s = user.getUsername();
        Optional<User> opUserResult = userRepo.findTop1ByUsernameOrNoHpOrEmailAndIsRegistered(s,s,s,true);//DATANYA PASTI HANYA 1
        User nextUser = null;
        if (!opUserResult.isPresent()) {
            throw new UsernameNotFoundException("User not found");
        }
        nextUser = opUserResult.get();
        if(!BcryptImpl.verifyHash(user.getPassword()+nextUser.getUsername(),
                nextUser.getPassword()))//dicombo dengan userName
        {
            return GlobalFunction.customReponse("X-03-031","username dan password salah",request);
        }
        UserDetails userDetails = loadUserByUsername(user.getUsername());
        /** start jwt config */
        Map<String,Object> mapForClaims = new HashMap<>();
        mapForClaims.put("uid",nextUser.getId());//payload
        mapForClaims.put("ml",nextUser.getEmail());//payload
        mapForClaims.put("nl",nextUser.getNamaLengkap());//payload
        mapForClaims.put("pn",nextUser.getNoHp());//payload
        String token = jwtUtility.generateToken(userDetails,mapForClaims);
        System.out.println("TOKEN MASIH IJO : "+token);
        m.put("token", Crypto.performEncrypt(token));
//        m.put("token", token);
        /** end jwt config */
//        m.put("token", Crypto.performEncrypt(nextUser.getUsername()));//format token custom
        m.put("menu", convertToListRespMenuDTO(nextUser.getAkses().getMenuList()));
        return  GlobalFunction.customDataDitemukan("Login Berhasil",m,request);
    }

    public ResponseEntity<Object> doRegis(User user, HttpServletRequest request) {

        List<User> userList = userRepo.findTop1ByUsernameOrNoHpOrEmail(user.getUsername(),user.getNoHp(),user.getEmail());//DATANYA PASTI HANYA 1
        //sebenar nya disini flow nya ada banyak
        //pertama cek terlebih dahulu apakah user pernah melakukan verifikasi atau tidak
        //kedua jika sudah pernah , maka cek keseluruhan data nya terlebih dahulu dimana data yg sudah terpakai maka diinformasikan ke client
        //biarkan saja error jika saat pengecekan ternyata sudah terdaftar akan tetapi pada saat dicoba dimasukkan ke database tidak bisa karena unique per kolom di no hp , email dan username
        String strPesanRegisteredTrue = "Harap Ubah ";
        Boolean isDataIsRegistered = true;
        int token = 0;
        Long idUser = 1L;
        try{
            /** user belum pernah melakukan registrasi sama sekali dengan data no hp , email maupun username yang diinput saat proses registrasi */
            if(userList.isEmpty()){
                token = rand.nextInt(100000,999999);
                //contoh disini anggap saja user belum melakukan registrasi sama sekali
                //user akan mengembalikan token
                user.setRegistered(false);
                user.setPassword(BcryptImpl.hash(user.getPassword()+user.getUsername()));//password harus dikombinasikan
                user.setToken(BcryptImpl.hash(String.valueOf(token)));//token disimpan ke table dalam keadaan sudah di hash
                //USER YANG MELAKUKAN REGISTRASI SENDIRI DIBERI DEFAULT AKSES NYA 2 YAITU MEMBER ...!!
                Akses akses = new Akses();
                akses.setId(2L);//default saat registrasi dari depan
                user.setAkses(akses);

                userRepo.save(user);
            }else {
                for(User u : userList){
                    if(u.getRegistered()){
                        if(u.getNoHp().equals(user.getNoHp())){
                            isDataIsRegistered = false;
                            strPesanRegisteredTrue = strPesanRegisteredTrue+" No HP, ";
                            break;
                        }
                        if(u.getEmail().equals(user.getEmail())){
                            isDataIsRegistered = false;
                            strPesanRegisteredTrue = strPesanRegisteredTrue+" Email ,";
                            break;
                        }
                        if(u.getUsername().equals(user.getUsername())){
                            isDataIsRegistered = false;
                            strPesanRegisteredTrue = strPesanRegisteredTrue+" Username, ";
                            break;
                        }
                    }
                    idUser = u.getId();
                }
                /** DATA DENGAN SALAH SATU INFORMASI YANG PERNAH DIINPUT SUDAH PERNAH DI DAFTARKAN DAN SUDAH TERVERIFIKASI */
                if(!isDataIsRegistered){
                    strPesanRegisteredTrue = strPesanRegisteredTrue.substring(0,strPesanRegisteredTrue.length()-1);
                    strPesanRegisteredTrue = strPesanRegisteredTrue+" Karena Sudah Terdaftar ,coba ganti data tersebut dengan data lain nya";

                    return GlobalFunction.customReponse("XV-03-041",strPesanRegisteredTrue,request);
                }

                Optional<User> optionalUser = userRepo.findById(idUser);
                User nextUser = optionalUser.get();
                /** di proses ini user sudah pernah melakukan registrasi , tetapi belum selesai ... jadi seluruh data registrasi sebelumnya harus diganti seperti password , token verifikasi dll */
                if(optionalUser.isPresent()){
                    token = rand.nextInt(100000,999999);
                    nextUser.setRegistered(false);
                    nextUser.setPassword(BcryptImpl.hash(user.getPassword()+user.getUsername()));
                    nextUser.setToken(BcryptImpl.hash(String.valueOf(token)));
                    nextUser.setNoHp(user.getNoHp());
                    nextUser.setEmail(user.getEmail());
                    nextUser.setUsername(user.getUsername());
                    nextUser.setAlamat(user.getAlamat());
                    nextUser.setTanggalLahir(user.getTanggalLahir());
                    nextUser.setNoHp(user.getNoHp());
                    nextUser.setNamaLengkap(user.getNamaLengkap());
                    userRepo.save(nextUser);
                }
            }
        }catch (Exception e) {
            /** saat validasi unique per kolom sudah tereksekusi , pastikan informasi yang diberikan ke user lengkap, agar user mengganti seluruh datanya */
            return GlobalFunction.customReponse("X-03-041","Registrasi Gagal / Beberapa Informasi sudah Digunakan, Coba ubah data no hp , email dan username dengan data lain nya",request);
        }

        /**dikembalikan saat mode automation, kalau masih menggunakan QA manual untuk testing , sengaja saya kembalikan agar bisa lanjut buat unit testing*/
        m.put("token",token);
        return  GlobalFunction.customDataDitemukan("TOKEN BERHASIL DIKIRIM KE EMAIL",m,request);
    }

    public ResponseEntity<Object> resendTokenRegis(User usr, HttpServletRequest request) {
        String email = usr.getEmail();
        Optional<User> u = userRepo.findByEmail(email);
        if(!u.isPresent()) {
            throw new UsernameNotFoundException("User not found");
        }
        User user = u.get();
        int intTokenBaru =0;
        int token = rand.nextInt(100000,999999);
        try{
            intTokenBaru = rand.nextInt(100000,999999);
            user.setToken(BcryptImpl.hash(String.valueOf(token)));
            userRepo.save(user);
        }catch (Exception e) {
            LoggingFile.exceptionStringz("AppUserDetailService","resendTokenRegis",e, OtherConfig.getFlagLogging());
            return GlobalFunction.customReponse("X-03-051","Registrasi Gagal",request);
        }
        m.put("token",token);
        m.put("email",user.getUsername());//pengalaman di salah satu framework FE , ini bisa hilang dan harus dikirim lagi dari server...
        return  GlobalFunction.customDataDitemukan("OK",m,request);
    }

    public ResponseEntity<Object> verifikasiTokenRegis(User user, HttpServletRequest request) {
        Optional<User> u = userRepo.findByEmail(user.getEmail());
        int intToken = 0;
        if(!u.isPresent()) {
            throw new UsernameNotFoundException("User not found");
        }
        User nextUser = u.get();
        try{
            intToken = rand.nextInt(100000,999999);
            if(!BcryptImpl.verifyHash(user.getToken(),nextUser.getToken())){
                return GlobalFunction.customReponse("XV-03-061","TOKEN SALAH",request);
            }
            nextUser.setRegistered(true);
            nextUser.setToken(BcryptImpl.hash(String.valueOf(intToken)));//untuk security seluruh proses ditutup dengan token baru sehingga token lama tidak dapat digunakan lagi....
        }catch (Exception e) {
            LoggingFile.exceptionStringz("AppUserDetailService","verifikasiTokenRegis",e, OtherConfig.getFlagLogging());
            return GlobalFunction.customReponse("X-03-061","Registrasi Gagal",request);
        }
        return  GlobalFunction.customDataDitemukan("REGISTRASI BERHASIL",null,request);
    }

    public ResponseEntity<Object> checkEmailForgotPassword(User user, HttpServletRequest request) {
        Optional<User> u = userRepo.findByEmailAndIsRegistered(user.getEmail(),true);// hanya bisa lupa password user yang terverifikasi saja
        if(!u.isPresent()) {
            throw new UsernameNotFoundException("User not found");
        }
        int intToken = rand.nextInt(100000,999999);
        User nextUser = u.get();
        try{
            nextUser.setToken(BcryptImpl.hash(String.valueOf(intToken)));
            userRepo.save(nextUser);
        }catch (Exception e) {
            LoggingFile.exceptionStringz("AppUserDetailService","verifikasiTokenRegis",e, OtherConfig.getFlagLogging());
            return GlobalFunction.customReponse("X-03-061","Registrasi Gagal",request);
        }
        //SENGAJA DIKIRIM UNTUK AUTOMATION
        m.put("token",intToken);
        return  GlobalFunction.customDataDitemukan("TOKEN BERHASIL TERKIRIM KE EMAIL",null,request);
    }

    public ResponseEntity<Object> resendTokenForgotPassword(User user, HttpServletRequest request) {
        Optional<User> u = userRepo.findByEmailAndIsRegistered(user.getEmail(),true);// forgot password hanya user yang terverifikasi saja
        if(!u.isPresent()) {
            throw new UsernameNotFoundException("User not found");
        }
        int intToken = rand.nextInt(100000,999999);
        User nextUser = u.get();
        try{
            nextUser.setToken(BcryptImpl.hash(String.valueOf(intToken)));
            userRepo.save(nextUser);
        }catch (Exception e) {
            LoggingFile.exceptionStringz("AppUserDetailService","verifikasiTokenRegis",e, OtherConfig.getFlagLogging());
            return GlobalFunction.customReponse("X-03-071","Token Password Gagal",request);
        }
        m.put("token",intToken);
        return  GlobalFunction.customDataDitemukan("TOKEN BERHASIL TERKIRIM KE EMAIL",null,request);
    }

    public ResponseEntity<Object> verifikasiTokenForgotPassword(User user , HttpServletRequest request){
        Optional<User> u = userRepo.findByEmailAndIsRegistered(user.getEmail(),true);
        if(!u.isPresent()) {
            throw new UsernameNotFoundException("User not found");
        }
        User nextUser = u.get();
        if(!BcryptImpl.verifyHash(user.getToken(),nextUser.getToken())){
            GlobalFunction.customReponse("X-03-081","Token Tidak Valid",request);
        }
        int intToken = rand.nextInt(100000,999999);
        String verifyingToken = BcryptImpl.hash(String.valueOf(intToken));
        nextUser.setToken(verifyingToken);
        m.put("token",verifyingToken);
        return  GlobalFunction.customDataDitemukan("TOKEN BERHASIL TERKIRIM KE EMAIL",null,request);
    }

    public ResponseEntity<Object> verifikasiPasswordBaru(User user , HttpServletRequest request){
        Optional<User> u = userRepo.findByEmailAndIsRegistered(user.getEmail(),true);
        if(!u.isPresent()) {
            throw new UsernameNotFoundException("User not found");
        }
        User nextUser = u.get();
        if(!user.getToken().equals(nextUser.getToken())){
            GlobalFunction.customReponse("X-03-081","Tidak Dapat Diproses Masalah Keamanan!!",request);
        }else if(user.getPassword().equals(nextUser.getPasswordBaru())){
            GlobalFunction.customReponse("X-03-082","Password Harus Sama",request);
        }
        // token lama harus diubah lagi agar tidak bisa digunakan.....
        nextUser.setToken(BcryptImpl.hash(String.valueOf(rand.nextInt(100000,999999))));
        nextUser.setPassword(BcryptImpl.hash(user.getPassword()+user.getUsername()));
        return  GlobalFunction.customDataDitemukan("GANTI PASSWORD BERHASIL",null,request);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<User> opUser = userRepo.findTop1ByUsernameOrNoHpOrEmailAndIsRegistered(s,s,s,true);
        if(opUser.isEmpty())
        {
            throw new UsernameNotFoundException("TOKEN TIDAK VALID");
//            return null;
        }
        User userNext = opUser.get();

        return new org.springframework.security.core.userdetails.
                User(userNext.getUsername(),userNext.getPassword(),userNext.getAuthorities());
    }

    public List<RespMenuDTO> convertToListRespMenuDTO(List<Menu> menuList){
        return modelMapper.map(menuList,new TypeToken<List<RespMenuDTO>>(){}.getType());
    }

    public User convertToEntity(RegisDTO regisDTO){
        return modelMapper.map(regisDTO,User.class);
    }
    public User convertToEntity(VerifyUserDTO verifyUserDTO){
        return modelMapper.map(verifyUserDTO,User.class);
    }
    public User convertToEntity(LoginDTO loginDTO){
        return modelMapper.map(loginDTO,User.class);
    }

    public User convertToEntity(VerifikasiTokenDTO verifikasiTokenDTO){
        return modelMapper.map(verifikasiTokenDTO,User.class);
    }

    public User convertToEntity(CheckEmailDTO resendTokenDTO){
        return modelMapper.map(resendTokenDTO,User.class);
    }

    public User convertToEntity(VerifikasiForgotPasswordDTO verifikasiForgotPasswordDTO){
        return modelMapper.map(verifikasiForgotPasswordDTO,User.class);
    }
}