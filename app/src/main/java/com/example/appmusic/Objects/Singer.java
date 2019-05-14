package com.example.appmusic.Objects;

public class Singer {
     private int IDCaSi;
     private String TenCaSi;
     private String HinhCaSi;
     private int SoBaiHat;

    public Singer(int IDCaSi, String tenCaSi, String hinhCaSi, int soBaiHat) {
        this.IDCaSi = IDCaSi;
        TenCaSi = tenCaSi;
        HinhCaSi = hinhCaSi;
        SoBaiHat = soBaiHat;
    }
    public Singer()
    {
    }

    public int getIDCaSi() {
        return IDCaSi;
    }

    public void setIDCaSi(int IDCaSi) {
        this.IDCaSi = IDCaSi;
    }

    public String getTenCaSi() {
        return TenCaSi;
    }

    public void setTenCaSi(String tenCaSi) {
        TenCaSi = tenCaSi;
    }

    public String getHinhCaSi() {
        return HinhCaSi;
    }

    public void setHinhCaSi(String hinhCaSi) {
        HinhCaSi = hinhCaSi;
    }

    public int getSoBaiHat() {
        return SoBaiHat;
    }

    public void setSoBaiHat(int soBaiHat) {
        SoBaiHat = soBaiHat;
    }
}
