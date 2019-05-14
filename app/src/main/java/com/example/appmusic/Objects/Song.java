package com.example.appmusic.Objects;

public class Song {
    private int IDBaiHat;
    private String TenBaiHat;
    private String TenCaSi;
    private String Hinh;

    public Song(int idBaiHat, String tenBaiHat, String tenCaSi, String hinh) {
        IDBaiHat = idBaiHat;
        TenBaiHat = tenBaiHat;
        TenCaSi = tenCaSi;
        Hinh = hinh;
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
}
