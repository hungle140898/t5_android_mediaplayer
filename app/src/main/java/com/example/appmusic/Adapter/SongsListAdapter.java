package com.example.appmusic.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.appmusic.R;

import static android.graphics.Color.rgb;

public class SongsListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] song;
    private final String[] singer;
    private int mPosition = 0;

    public SongsListAdapter(Activity context,
                            String[] song, String[] singer) {
        super(context, R.layout.item, song);
        this.context = context;
        this.song = song;
        this.singer = singer;

    }

    public void setActiveSong(int position){
        mPosition = position;
    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.item, null, true);
        TextView txtTitle = rowView.findViewById(R.id.songItem);
        TextView txtSinger =  rowView.findViewById(R.id.singerItem);

        txtTitle.setText(song[position]);
        txtSinger.setText(singer[position]);

        if(position == mPosition){
            txtSinger.setTextColor(rgb(204, 102, 153));
            txtTitle.setTextColor(rgb(204, 102, 153));
        }
        return rowView;
    }
}
