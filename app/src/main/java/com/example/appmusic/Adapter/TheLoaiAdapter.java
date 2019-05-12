package com.example.appmusic.Adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.appmusic.Objects.TheLoai;
import com.example.appmusic.R;

import java.util.List;

public class TheLoaiAdapter extends BaseAdapter {
    private Context context;
    private  int layout;
    private List<TheLoai> theloaiList ;
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
        TextView txtTen,txtHinh;
    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder viewHolder;
        if(view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,null);
            viewHolder.txtTen = (TextView) view.findViewById(R.id.txtTenTheLoai);
            viewHolder.txtHinh = (TextView) view.findViewById(R.id.txtHinh);
        }
        else {
            viewHolder = (ViewHolder)view.getTag();
        }
        TheLoai theloai =theloaiList.get(position);
        viewHolder.txtTen.setText(theloai.getTenTheLoai());
        viewHolder.txtHinh.setText(theloai.getHinhTheLoai());
        return view;
    }
}
