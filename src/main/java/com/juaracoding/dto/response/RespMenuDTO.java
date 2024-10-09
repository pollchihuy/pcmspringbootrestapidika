package com.juaracoding.dto.response;

import com.juaracoding.model.GroupMenu;

public class RespMenuDTO {

    private Long id;

    private String nama;

    private String path;

    private RespGroupMenuDTO groupMenu;

    public RespGroupMenuDTO getGroupMenu() {
        return groupMenu;
    }

    public void setGroupMenu(RespGroupMenuDTO groupMenu) {
        this.groupMenu = groupMenu;
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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
