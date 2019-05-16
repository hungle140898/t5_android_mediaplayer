package com.example.appmusic.Fragment;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.SearchView;
import com.example.appmusic.Adapter.ListSingerAdapter;
import com.example.appmusic.Objects.Singer;
import com.example.appmusic.R;

import java.util.ArrayList;
import java.util.List;

import static com.example.appmusic.Activity.MainActivity.database;

public class Fragment_CaSi extends Fragment implements SearchView.OnQueryTextListener{
    View view;
    GridView gvCaSi;
    ArrayList<Singer> arrayCaSi;
    ArrayList<Singer> arrayTimKiemCaSi;
    ListSingerAdapter adapter;
    SearchView searchView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_casi, container, false);
        gvCaSi = view.findViewById(R.id.gv_casi);
        LayDanhSachCaSi();
        SetAdapter(arrayCaSi);
        adapter.notifyDataSetChanged();
        searchView = view.findViewById(R.id.search_bar);
        searchView.setOnQueryTextListener(this);
        return view;
    }
    private void SetAdapter(List<Singer> list)
    {
        adapter = new ListSingerAdapter(getActivity(), R.layout.row_casi, list);
        gvCaSi.setAdapter(adapter);
    }
    public List<Singer> LayDanhSachCaSi()
    {
        arrayCaSi = new ArrayList<>();
        Cursor c = database.getData("SELECT CaSi.IDCaSi,CaSi.TenCaSi,CaSi.HinhCaSi,count(CaSi.IDCaSi) FROM BaiHat,CaSi WHERE BaiHat.IDCaSi=CaSi.IDCaSi  GROUP By CaSi.IDCaSi");
        while (c.moveToNext())
    {
        Singer singer = new Singer();
        singer.setIDCaSi(c.getInt(0));
        singer.setTenCaSi(c.getString(1));
        singer.setHinhCaSi(c.getString(2));
        singer.setSoBaiHat(c.getInt(3));
        arrayCaSi.add(singer);
    }
        return arrayCaSi;
    }
    public List<Singer> TimDanhSachCaSi(String searchText)
    {
        arrayTimKiemCaSi = new ArrayList<>();
        String sql = "SELECT CaSi.IDCaSi,CaSi.TenCaSi,CaSi.HinhCaSi,count(CaSi.IDCaSi) FROM BaiHat,CaSi WHERE BaiHat.IDCaSi=CaSi.IDCaSi AND CaSi.TenCaSi LIKE '%"+searchText+"%'GROUP BY CaSi.IDCaSi";
        Cursor c = database.getData(sql);
        while (c.moveToNext())
        {
            Singer singer = new Singer();
            singer.setIDCaSi(c.getInt(0));
            singer.setTenCaSi(c.getString(1));
            singer.setHinhCaSi(c.getString(2));
            singer.setSoBaiHat(c.getInt(3));
            arrayTimKiemCaSi.add(singer);
        }
        return arrayTimKiemCaSi;
    }
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String searchText) {
        TimDanhSachCaSi(searchText);
        SetAdapter(arrayTimKiemCaSi);
        return true;
    }
}
