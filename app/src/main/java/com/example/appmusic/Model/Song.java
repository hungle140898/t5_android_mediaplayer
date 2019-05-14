package com.example.appmusic.Model;

public class Song {

    private String mTitle;
    private String mSinger;
    private int mFile;

    public Song(String title, String singer, int file){
        mTitle = title;
        mSinger = singer;
        mFile = file;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getSinger() {
        return mSinger;
    }

    public int getFile() {
        return mFile;
    }
}
