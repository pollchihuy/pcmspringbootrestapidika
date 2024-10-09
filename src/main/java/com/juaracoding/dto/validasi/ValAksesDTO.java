package com.juaracoding.dto.validasi;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class ValAksesDTO {

    @NotNull
    @NotBlank
    @NotEmpty
    private String nama;

    @NotNull
    private List<ValMenuDTO> menuList;

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public List<ValMenuDTO> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<ValMenuDTO> menuList) {
        this.menuList = menuList;
    }
}
