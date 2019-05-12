package com.example.appmusic.Objects;

public class TheLoai {
    public TheLoai(int id, String tenTheLoai, String hinhTheLoai) {
        this.IDTheLoai = id;
        this.TenTheLoai = tenTheLoai;
        this.HinhTheLoai = hinhTheLoai;
    }

    public int getIDTheLoai() {
        return IDTheLoai;
    }

    public void setIDTheLoai(int IDTheLoai) {
        this.IDTheLoai = IDTheLoai;
    }

    private int IDTheLoai;

    public String getTenTheLoai() {
        return TenTheLoai;
    }

    public void setTenTheLoai(String tenTheLoai) {
        TenTheLoai = tenTheLoai;
    }

    private String TenTheLoai;

    public String getHinhTheLoai() {
        return HinhTheLoai;
    }

    public void setHinhTheLoai(String hinhTheLoai) {
        HinhTheLoai = hinhTheLoai;
    }

    private String HinhTheLoai;
}
