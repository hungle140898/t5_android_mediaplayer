package com.example.appmusic.Objects;

import java.io.Serializable;

public class Song implements  Serializable {
    private int IDBaiHat;
    private String TenBaiHat;
    private String TenCaSi;
    private String Hinh;
    private String Link;

    public Song(int idBaiHat, String tenBaiHat, String tenCaSi, String hinh, String link) {
        IDBaiHat = idBaiHat;
        TenBaiHat = tenBaiHat;
        TenCaSi = tenCaSi;
        Hinh = hinh;
        Link = link;
    }
    public Song()
    {
    }

    public int getIDBaiHat() {
        return IDBaiHat;
    }

    public void setIDBaiHat(int IDBaiHat) {
        this.IDBaiHat = IDBaiHat;
    }

    public String getTenBaiHat() {
        return TenBaiHat;
    }

    public void setTenBaiHat(String tenBaiHat) {
        TenBaiHat = tenBaiHat;
    }

    public String getTenCaSi() {
        return TenCaSi;
    }

    public void setTenCaSi(String tenCaSi) {
        TenCaSi = tenCaSi;
    }

    public String getHinh() {
        return Hinh;
    }

    public void setHinh(String hinh) {
        Hinh = hinh;
    }

    public String getLink() {
        return Link;
    }

    public void setLink(String link) {
        Link = link;
    }
}
