package com.juaracoding.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class ParentEntity {
    @Id
    private Long id;

    @Column(unique=true)
    private String name;

    /** modified when migration */
    @Column(length=15,nullable = false)
    private String namaParent;

    private String active;

    @OneToMany(mappedBy = "parentEntity",cascade = CascadeType.PERSIST)
    private List<ChildEntity> child;

    public String getNamaParent() {
        return namaParent;
    }

    public void setNamaParent(String namaParent) {
        this.namaParent = namaParent;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<ChildEntity> getChild() {
        return child;
    }

    public void setChild(List<ChildEntity> child) {
        this.child = child;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}