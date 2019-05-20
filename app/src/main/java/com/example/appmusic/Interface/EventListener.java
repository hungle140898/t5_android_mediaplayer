package com.example.appmusic.Interface;

public interface EventListener {
    void showalertdialog(int id, String tenbaihat);
    void dialogaddplaylist(int idbaihat);
    void songclick(int id, int index);
    void playlistClick(int id);
    void theloaiClick(int id);
    void albumClick(int id);
    void casiClick(int id);
    void longclickplaylist(int id);
}
