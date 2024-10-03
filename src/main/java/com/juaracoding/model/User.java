package com.juaracoding.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.Period;
import java.util.Date;

/** ANOTASI JPA */
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 50)
    private String nama;
    @Column(length = 100)
    private String alamat;
    @Column(length = 50)
    private String email;
    @Column(length = 16)
    private String noHp;
    @Column(length = 64)
    private String password;
    @Column(length = 25)
    private String username;
    private LocalDate tanggalLahir;
    @Column(updatable = false)
    private Date createdAt = new Date();

    @Column(insertable = false)
    private Date updatedAt=new Date();
    private Boolean isRegis;
    private Boolean isActive;

    @Transient
    private Integer umur;

    public String getNoHp() {
        return noHp;
    }

    public void setNoHp(String noHp) {
        this.noHp = noHp;
    }

    public Integer getUmur() {
        return Period.between(tanggalLahir,
                LocalDate.now()).getYears();
    }

    public void setUmur(Integer umur) {
        this.umur = umur;
    }

    public LocalDate getTanggalLahir() {
        return tanggalLahir;
    }

    public void setTanggalLahir(LocalDate tanggalLahir) {
        this.tanggalLahir = tanggalLahir;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Boolean getRegis() {
        return isRegis;
    }

    public void setRegis(Boolean regis) {
        isRegis = regis;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

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

    //    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }

//    public Akses getAkses() {
//        return akses;
//    }
//
//    public void setAkses(Akses akses) {
//        this.akses = akses;
//    }


}
