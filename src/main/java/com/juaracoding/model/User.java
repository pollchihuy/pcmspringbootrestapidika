package com.juaracoding.model;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.Period;
import java.util.*;

@Entity
@Table(name = "mst_user")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true,length = 50)
    private String email;

    @Column(unique = true)
    private String noHp;

    @Column(unique = true,length = 20)
    private String username;

    @Column(length = 64,unique = true)
    private String password;//8 - 16

    @Transient
    private String passwordBaru;

    @Column(length = 50, nullable = false)
    private String namaLengkap;

    private LocalDate tanggalLahir;

    private String alamat;

    /** penanda apakah sudah melakukan konfirmasi token registrasi atau belum */
    private Boolean isRegistered = false;

    @Transient
    private Integer umur;

    @Column(length = 128)
    private String token;

    @ManyToOne
    @JoinColumn(name = "id_akses",foreignKey = @ForeignKey(name = "fk_to_akses"))
    private Akses akses;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPasswordBaru() {
        return passwordBaru;
    }

    public void setPasswordBaru(String passwordBaru) {
        this.passwordBaru = passwordBaru;
    }

    public Akses getAkses() {
        return akses;
    }

    public void setAkses(Akses akses) {
        this.akses = akses;
    }

    public Boolean getRegistered() {
        return isRegistered;
    }

    public void setRegistered(Boolean registered) {
        isRegistered = registered;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<Menu> lt = this.akses.getMenuList();
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for (Menu menu : lt) {
            grantedAuthorities.add(new SimpleGrantedAuthority(menu.getNama()));
        }
        return grantedAuthorities;
//        return List.of();
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Data analytic, kolom tidak terbentuk karena informasi ini dynamic
     * jadi proses kalkulasi ada disini !!
     */
    public Integer getUmur() {
        return Period.
                between(this.tanggalLahir,LocalDate.now())
                .getYears();
    }
    public void setUmur(Integer umur) {
        this.umur = umur;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNoHp() {
        return noHp;
    }

    public void setNoHp(String noHp) {
        this.noHp = noHp;
    }

    public String getNamaLengkap() {
        return namaLengkap;
    }

    public void setNamaLengkap(String namaLengkap) {
        this.namaLengkap = namaLengkap;
    }

    public LocalDate getTanggalLahir() {
        return tanggalLahir;
    }

    public void setTanggalLahir(LocalDate tanggalLahir) {
        this.tanggalLahir = tanggalLahir;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }
}