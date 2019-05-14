package com.example.appmusic.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appmusic.Objects.Song;
import com.example.appmusic.R;

import java.util.ArrayList;

public class ListSongAdapter extends ArrayAdapter<Song> {
    EventListener listener;
    private Context context;
    private  int resource;
    private ArrayList<Song> songs;

  /*  public ListSongAdapter(Context context, int resource, ArrayList<Song> songs) {
      super(context, resource, songs);
      this.context = context;
      this.resource = resource;
      this.songs = songs;
     }*/
    public ListSongAdapter(Context context, int resource, ArrayList<Song> songs, EventListener listener) {
        super(context, resource, songs);
        this.context = context;
        this.resource = resource;
        this.songs = songs;
        this.listener = listener;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.row_baihat,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.iv_Avatar = convertView.findViewById(R.id.iv_Avatar);
            viewHolder.tv_tenbaihat = convertView.findViewById(R.id.tv_tenbaihat);
            viewHolder.tv_tencasi = convertView.findViewById(R.id.tv_tencasi);
            viewHolder.ib_addplaylist= convertView.findViewById(R.id.ib_addplaylist);
            viewHolder.ib_deletebaihat= convertView.findViewById(R.id.ib_deletebaihat);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final Song song = songs.get(position);
        int ImageId = getMipmapResIdByName(song.getHinh());
        viewHolder.iv_Avatar.setImageResource(ImageId);
        viewHolder.tv_tenbaihat.setText(song.getTenBaiHat());
        viewHolder.tv_tencasi.setText(song.getTenCaSi());
        viewHolder.ib_deletebaihat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.showalertdialog(song.getIDBaiHat(),song.getTenBaiHat());
            }
        });
        return convertView;
    }
    public class ViewHolder {
        ImageView iv_Avatar;
        TextView tv_tenbaihat,tv_tencasi;
        ImageButton ib_deletebaihat,ib_addplaylist;
    }
    public int getMipmapResIdByName(String resName) {
        String pkgName = context.getPackageName();
        int resID = context.getResources().getIdentifier(resName , "mipmap", pkgName);
        return resID;
    }
    public interface EventListener {
        void showalertdialog(int id, String tenbaihat);
    }
}
