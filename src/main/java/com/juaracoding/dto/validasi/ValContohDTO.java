package com.juaracoding.dto.validasi;

import java.time.LocalDate;

public class ValContohDTO {

    private Short id;
    private String namaContoh;
    private String deskripsiContoh;
    private Double contohDouble;
    private Byte contohByte;
    private Short contohShort;
    private Boolean contohBoolean;
    private Character contohChar;
    private LocalDate createdAt;

    public Short getId() {
        return id;
    }

    public void setId(Short id) {
        this.id = id;
    }

    public String getNamaContoh() {
        return namaContoh;
    }

    public void setNamaContoh(String namaContoh) {
        this.namaContoh = namaContoh;
    }

    public String getDeskripsiContoh() {
        return deskripsiContoh;
    }

    public void setDeskripsiContoh(String deskripsiContoh) {
        this.deskripsiContoh = deskripsiContoh;
    }

    public Double getContohDouble() {
        return contohDouble;
    }

    public void setContohDouble(Double contohDouble) {
        this.contohDouble = contohDouble;
    }

    public Byte getContohByte() {
        return contohByte;
    }

    public void setContohByte(Byte contohByte) {
        this.contohByte = contohByte;
    }

    public Short getContohShort() {
        return contohShort;
    }

    public void setContohShort(Short contohShort) {
        this.contohShort = contohShort;
    }

    public Boolean getContohBoolean() {
        return contohBoolean;
    }

    public void setContohBoolean(Boolean contohBoolean) {
        this.contohBoolean = contohBoolean;
    }

    public Character getContohChar() {
        return contohChar;
    }

    public void setContohChar(Character contohChar) {
        this.contohChar = contohChar;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }
}
