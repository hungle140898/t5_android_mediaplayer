package com.example.appmusic.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.appmusic.Objects.Singer;
import com.example.appmusic.R;
import java.util.List;

public class ListSingerAdapter extends BaseAdapter {
    private Context context;
    private  int layout;
    private List<Singer> singerList;

    public ListSingerAdapter(Context context, int layout, List<Singer> singerList) {
        this.context=context;
        this.layout = layout;
        this.singerList = singerList;
    }

    @Override
    public int getCount() {
        return singerList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public class ViewHolder {
        ImageView iv_avatarcasi;
        TextView tv_tencasi,tv_sobaihat;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder viewHolder;
        if (convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.row_casi,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.iv_avatarcasi=convertView.findViewById(R.id.tv_avatarcasi);
            viewHolder.tv_tencasi=convertView.findViewById(R.id.tv_tencasi);
            viewHolder.tv_sobaihat=convertView.findViewById(R.id.tv_sobaihat);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Singer singer = singerList.get(position);
        int ImageId = getMipmapResIdByName(singer.getHinhCaSi());
        viewHolder.iv_avatarcasi.setImageResource(ImageId);
        viewHolder.tv_tencasi.setText(singer.getTenCaSi());
        viewHolder.tv_sobaihat.setText( Integer.toString(singer.getSoBaiHat())+" Bài Hát");
        return convertView;
    }

    public int getMipmapResIdByName(String resName) {
        String pkgName = context.getPackageName();
        int resID = context.getResources().getIdentifier(resName , "mipmap", pkgName);
        return resID;
    }

}
