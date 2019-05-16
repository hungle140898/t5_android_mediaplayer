package com.example.appmusic.Objects;

public class Album {
    public Album(int id, String tenAlbum,String hinhAlbum)
    {
        this.IDAlbum = id;
        this.TenAlbum = tenAlbum;
        this.HinhAlbum = hinhAlbum;
    }
    public int getIDAlbum() {
        return IDAlbum;
    }

    public void setIDAlbum(int IDAlbum) {
        this.IDAlbum = IDAlbum;
    }

    private int IDAlbum;

    public String getTenAlbum() {
        return TenAlbum;
    }

    public void setTenAlbum(String tenAlbum) {
        TenAlbum = tenAlbum;
    }

    private String TenAlbum;

    private String HinhAlbum;

    public String getHinhAlbum() {
        return HinhAlbum;
    }

    public void setHinhAlbum(String hinhAlbum) {
        HinhAlbum = hinhAlbum;
    }
}
