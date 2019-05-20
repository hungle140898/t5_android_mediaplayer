package com.example.appmusic.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.appmusic.Objects.PlayList;
import com.example.appmusic.Objects.Song;

import java.util.ArrayList;
import java.util.List;

public class ListSongdialog extends ArrayAdapter<Song> {
    private Context context;
    private  int resource;
    private ArrayList<Song> songs;
    public ListSongdialog( Context context, int resource, ArrayList<Song> songs) {
        super(context, resource, songs);
        this.context = context;
        this.resource = resource;
        this.songs = songs;
    }
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        TextView label = (TextView) super.getView(position, convertView, parent);
        label.setTextColor(Color.BLACK);
        Song song = songs.get(position);
        label.setText(song.getTenBaiHat());
        return label;
    }
    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        TextView label = (TextView) super.getDropDownView(position, convertView, parent);
        label.setTextColor(Color.BLACK);
        Song song = songs.get(position);
        label.setText(song.getTenBaiHat());
        return label;
    }
}
