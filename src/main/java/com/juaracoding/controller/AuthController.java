package com.juaracoding.controller;


import com.juaracoding.dto.validasi.*;
import com.juaracoding.model.User;
import com.juaracoding.service.AppUserDetailService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
public class AuthController {

    @Autowired
    private AppUserDetailService appUserDetailService;

    @PostMapping("/v1/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginDTO loginDTO, HttpServletRequest request) {
        User user = appUserDetailService.convertToEntity(loginDTO);
        return appUserDetailService.doLogin(user, request);
    }

    @PostMapping("/v1/doregis")
    public ResponseEntity<Object> doRegis(@Valid @RequestBody RegisDTO regisDTO, HttpServletRequest request){
        User user = appUserDetailService.convertToEntity(regisDTO);
        return appUserDetailService.doRegis(user,request);
    }

    @PostMapping("/v1/verify-token-regis")
    public ResponseEntity<Object> verifyToken(@Valid @RequestBody VerifikasiTokenDTO verifikasiTokenDTO, HttpServletRequest request){
        User user = appUserDetailService.convertToEntity(verifikasiTokenDTO);
        return appUserDetailService.verifikasiTokenRegis(user,request);
    }

    @PostMapping("/v1/resend-token-regis")
    public ResponseEntity<Object> resendTokenRegis(@Valid @RequestBody CheckEmailDTO resendTokenDTO, HttpServletRequest request){
        User user = appUserDetailService.convertToEntity(resendTokenDTO);
        return appUserDetailService.resendTokenRegis(user,request);
    }

    @PostMapping("/v1/check-email-forgot-password")
    public ResponseEntity<Object> checkEmailForgotPassword(@Valid @RequestBody CheckEmailDTO checkEmailDTO, HttpServletRequest request){
        User user = appUserDetailService.convertToEntity(checkEmailDTO);
        return appUserDetailService.checkEmailForgotPassword(user,request);
    }

    @PostMapping("/v1/resend-token-forgot-password")
    public ResponseEntity<Object> resendTokenForgotPassword(@Valid @RequestBody CheckEmailDTO checkEmailDTO, HttpServletRequest request){
        User user = appUserDetailService.convertToEntity(checkEmailDTO);
        return appUserDetailService.resendTokenForgotPassword(user,request);
    }

    @PostMapping("/v1/verifikasi-token-forgot-password")
    public ResponseEntity<Object> verifyTokenForgotPassword(@Valid @RequestBody VerifikasiTokenDTO verifikasiTokenDTO, HttpServletRequest request){
        User user = appUserDetailService.convertToEntity(verifikasiTokenDTO);
        return appUserDetailService.verifikasiTokenForgotPassword(user,request);
    }
    @PostMapping("/v1/verifikasi-forgot-password")
    public ResponseEntity<Object> verifyForgotPassword(@Valid @RequestBody VerifikasiForgotPasswordDTO verifikasiForgotPasswordDTO, HttpServletRequest request){
        User user = appUserDetailService.convertToEntity(verifikasiForgotPasswordDTO);
        return appUserDetailService.verifikasiPasswordBaru(user,request);
    }
}
