package com.juaracoding.model;


import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "mst_akses")
public class Akses {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false,length = 50)
    private String nama;

    @ManyToMany
    @JoinTable(name = "map_akses_menu",
            joinColumns = @JoinColumn(name = "id_akses",foreignKey = @ForeignKey(name = "fk_to_map_akses")),
            inverseJoinColumns =@JoinColumn(name = "id_menu", foreignKey =@ForeignKey(name = "fk_to_map_menu")))
    private List<Menu> menuList;

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

    public List<Menu> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<Menu> menuList) {
        this.menuList = menuList;
    }
}