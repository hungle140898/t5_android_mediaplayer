package com.example.appmusic.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.appmusic.Objects.Album;
import com.example.appmusic.R;
import java.util.List;

public class AlbumAdapter extends BaseAdapter {
    private Context context;
    private  int layout;
    private List<Album> albumList  ;
    public AlbumAdapter(Context context,int layout,List<Album> albumList)
    {
        this.context = context;
        this.layout = layout;
        this.albumList = albumList;
    }

    @Override
    public int getCount() {
        return albumList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    public class ViewHolder{
        ImageView imgview;
        TextView txtcasi,txttenalbum;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AlbumAdapter.ViewHolder viewHolder;
        if(convertView == null) {
            viewHolder = new AlbumAdapter.ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.custom_album,parent,false);
            viewHolder.imgview = convertView.findViewById(R.id.txtTenHinh);
            viewHolder.txtcasi = convertView.findViewById(R.id.tenCaSi);
            viewHolder.txttenalbum = convertView.findViewById(R.id.tenAlbum);
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (AlbumAdapter.ViewHolder)convertView.getTag();
        }
        Album album = albumList.get(position);
        int ImageID = getMipmapResIdByName(album.getHinhAlbum());
        viewHolder.imgview.setImageResource(ImageID);
        viewHolder.txtcasi.setText("K-ICM");
        viewHolder.txttenalbum.setText(album.getTenAlbum());
        return convertView;
    }
    public int getMipmapResIdByName(String resName) {
        String pkgName = context.getPackageName();
        int resID = context.getResources().getIdentifier(resName , "mipmap", pkgName);
        return resID;
    }
}

