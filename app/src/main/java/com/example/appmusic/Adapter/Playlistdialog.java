package com.example.appmusic.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.appmusic.Objects.PlayList;

import java.util.ArrayList;

public class Playlistdialog extends ArrayAdapter<PlayList> {
    private Context context;
    private  int resource;
    private ArrayList<PlayList> playLists;
    public Playlistdialog(Context context, int resource, ArrayList<PlayList> playLists) {
        super(context, resource, playLists);
        this.context = context;
        this.resource = resource;
        this.playLists = playLists;
    }
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        TextView label = (TextView) super.getView(position, convertView, parent);
        label.setTextColor(Color.BLACK);
        PlayList playList = playLists.get(position);
        label.setText(playList.getTenPlayList());
        return label;
    }
    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        TextView label = (TextView) super.getDropDownView(position, convertView, parent);
        label.setTextColor(Color.BLACK);
        PlayList playList = playLists.get(position);
        label.setText(playList.getTenPlayList());
        return label;
    }
}
