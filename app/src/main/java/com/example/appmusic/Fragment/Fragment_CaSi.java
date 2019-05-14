package com.example.appmusic.Fragment;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.appmusic.Adapter.ListSingerAdapter;
import com.example.appmusic.Objects.Singer;
import com.example.appmusic.R;

import java.util.ArrayList;

import static com.example.appmusic.Activity.MainActivity.database;

public class Fragment_CaSi extends Fragment {
    View view;
    private ListView lv_casi;
    ArrayList<Singer> listsinger = new ArrayList<>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_casi, container, false);
        getdata();
        return view;
    }
    private void getdata()
    {
        Cursor data = database.getData("SELECT CaSi.IDCaSi,CaSi.TenCaSi,CaSi.HinhCaSi,count(CaSi.IDCaSi) FROM BaiHat,CaSi WHERE BaiHat.IDCaSi=CaSi.IDCaSi  GROUP By CaSi.IDCaSi");
        listsinger.clear();
        while (data.moveToNext())
        {
            Singer singer = new Singer();
            singer.setIDCaSi(data.getInt(0));
            singer.setTenCaSi(data.getString(1));
            singer.setHinhCaSi(data.getString(2));
            singer.setSoBaiHat(data.getInt(3));
            listsinger.add(singer);
        }
        lv_casi= view.findViewById(R.id.lv_casi);
        ListSingerAdapter listSingerAdapter = new ListSingerAdapter(getActivity(),R.layout.row_casi,listsinger);
        lv_casi.setAdapter(listSingerAdapter);
    }
}
