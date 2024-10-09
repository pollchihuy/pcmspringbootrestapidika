package com.juaracoding.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "mst_menu")
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true,nullable = false,length = 30)
    private String nama;

    @Column(unique = true,nullable = false,length = 50)
    private String path;

    @ManyToOne
    @JoinColumn(name = "id_group_menu", foreignKey = @ForeignKey(name = "fk_to_group_menu"))
    private GroupMenu groupMenu;

    @ManyToMany(mappedBy = "menuList")
    private List<Akses> menuAkses ;

    public List<Akses> getMenuAkses() {
        return menuAkses;
    }

    public void setMenuAkses(List<Akses> menuAkses) {
        this.menuAkses = menuAkses;
    }

    public GroupMenu getGroupMenu() {
        return groupMenu;
    }

    public void setGroupMenu(GroupMenu groupMenu) {
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
