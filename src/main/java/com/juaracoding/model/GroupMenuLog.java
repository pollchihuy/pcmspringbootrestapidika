package com.juaracoding.model;


import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "log_group_menu")
public class GroupMenuLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "id_group_menu")
    private Long idGroupMenu;

    private String name;

    @Column(nullable = false,updatable = false)
    private Long createdBy;

    @Column(nullable = false,updatable = false)
    private Date createdAt;

    @Column(nullable = false,updatable = false)
    private Character flag;

    public Character getFlag() {
        return flag;
    }

    public void setFlag(Character flag) {
        this.flag = flag;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Long getIdGroupMenu() {
        return idGroupMenu;
    }

    public void setIdGroupMenu(Long idGroupMenu) {
        this.idGroupMenu = idGroupMenu;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
