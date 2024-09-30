package com.juaracoding.dto.response;

import java.time.LocalDate;
public class UserDTO {

    private Long id;
    private String nama;
    private String alamat;
    private String email;
    private String username;
    private LocalDate tanggalLahir;
    private Integer umur;
//    private String kolomTambah;
//    private String kolomTambah2;
//    private String noHp;
//
//    public String getNoHp() {
//        return noHp;
//    }
//
//    public void setNoHp(String noHp) {
//        this.noHp = noHp;
//    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDate getTanggalLahir() {
        return tanggalLahir;
    }

    public void setTanggalLahir(LocalDate tanggalLahir) {
        this.tanggalLahir = tanggalLahir;
    }

    public Integer getUmur() {
        return umur;
    }

    public void setUmur(Integer umur) {
        this.umur = umur;
    }
}
