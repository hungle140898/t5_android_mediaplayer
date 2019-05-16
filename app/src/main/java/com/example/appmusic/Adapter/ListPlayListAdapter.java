package com.example.appmusic.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appmusic.Objects.PlayList;
import com.example.appmusic.R;

import java.util.ArrayList;
import java.util.List;

public class ListPlayListAdapter extends BaseAdapter {

    private Context context;
    private  int layout;
    private List<PlayList> playLists;
    public ListPlayListAdapter( Context context, int layout,  List<PlayList> playLists) {
        this.context = context;
        this.layout = layout;
        this.playLists = playLists;
    }

    @Override
    public int getCount() {
        return playLists.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        final ViewHolder viewHolder;
        if (convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.row_playlist,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.iv_avatarPlayList=convertView.findViewById(R.id.iv_avatarPlayList);
            viewHolder.tv_tenplaylist=convertView.findViewById(R.id.tv_tenplaylist);
            viewHolder.tv_sobaihat=convertView.findViewById(R.id.tv_sobaihat);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        PlayList playList =playLists.get(position);
        viewHolder.tv_tenplaylist.setText(playList.getTenPlayList());
        viewHolder.tv_sobaihat.setText( Integer.toString(playList.getSoBaiHat()) + " Bài Hát");
        return convertView;
    }
    public class ViewHolder {
        ImageView iv_avatarPlayList;
        TextView tv_tenplaylist,tv_sobaihat;
    }
}
