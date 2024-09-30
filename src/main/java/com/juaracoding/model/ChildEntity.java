package com.juaracoding.model;


import jakarta.persistence.*;

@Entity
public class ChildEntity {

    @Id
    private int id;

    private String namaChild;

    @ManyToOne
    @JoinColumn(name = "pe_id",foreignKey = @ForeignKey(name = "fk_par_to_ch"))
    private ParentEntity parentEntity;
}