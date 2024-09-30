package com.juaracoding.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;

@Entity
public class Penjualan {
    @Id
    private Long id;
    private Integer pcs;
    private Double hargaSatuan;
    private Double diskon;

    @Transient
    private Double totalBayar;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPcs() {
        return pcs;
    }

    public void setPcs(Integer pcs) {
        this.pcs = pcs;
    }

    public Double getHargaSatuan() {
        return hargaSatuan;
    }

    public void setHargaSatuan(Double hargaSatuan) {
        this.hargaSatuan = hargaSatuan;
    }

    public Double getDiskon() {
        return diskon;
    }

    public void setDiskon(Double diskon) {
        this.diskon = diskon;
    }

    public Double getTotalBayar() {
        return ((pcs*hargaSatuan)-(pcs*hargaSatuan*diskon));
    }

    public void setTotalBayar(Double totalBayar) {
        this.totalBayar = totalBayar;
    }
}
