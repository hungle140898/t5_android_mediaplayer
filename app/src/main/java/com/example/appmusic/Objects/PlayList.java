package com.example.appmusic.Objects;

public class PlayList {
    private int IDPlayList;
    private String TenPlayList;
    private int SoBaiHat;

    public PlayList(int IDPlayList, String tenPlayList, int soBaiHat) {
        this.IDPlayList = IDPlayList;
        TenPlayList = tenPlayList;
        SoBaiHat = soBaiHat;
    }
    public PlayList()
    {

    }

    public int getIDPlayList() {
        return IDPlayList;
    }

    public void setIDPlayList(int IDPlayList) {
        this.IDPlayList = IDPlayList;
    }

    public String getTenPlayList() {
        return TenPlayList;
    }

    public void setTenPlayList(String tenPlayList) {
        TenPlayList = tenPlayList;
    }

    public int getSoBaiHat() {
        return SoBaiHat;
    }

    public void setSoBaiHat(int soBaiHat) {
        SoBaiHat = soBaiHat;
    }
}
