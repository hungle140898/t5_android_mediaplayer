package com.example.appmusic.Adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appmusic.Objects.TheLoai;
import com.example.appmusic.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TheLoaiAdapter extends BaseAdapter {
    private Context context;
    private  int layout;
    private List<TheLoai> theloaiList  ;
    private ArrayList<TheLoai> theLoaiArrayList;
    public TheLoaiAdapter(Context context,int layout,List<TheLoai> theloaiList)
    {
        this.context = context;
        this.layout = layout;
        this.theloaiList = theloaiList;
    }


    @Override
    public int getCount() {
        return theloaiList.size();
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
    }

      @Override
    public View getView(int position, View converView, ViewGroup parent) {
            ViewHolder viewHolder;
            if(converView == null) {
                viewHolder = new ViewHolder();
                converView = LayoutInflater.from(context).inflate(R.layout.custom_theloai,parent,false);
                viewHolder.imgview = converView.findViewById(R.id.txtTenHinh);
                converView.setTag(viewHolder);
            }
            else
            {
                viewHolder = (ViewHolder)converView.getTag();
            }
            TheLoai theloai = theloaiList.get(position);
            int ImageID = getMipmapResIdByName(theloai.getHinhTheLoai());
            viewHolder.imgview.setImageResource(ImageID);
            return converView;
    }
    public int getMipmapResIdByName(String resName) {
        String pkgName = context.getPackageName();
        int resID = context.getResources().getIdentifier(resName , "mipmap", pkgName);
        return resID;
    }
}
