package com.example.appmusic.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.SearchView;

import com.example.appmusic.Activity.PlaySongActivity;
import com.example.appmusic.Adapter.TheLoaiAdapter;
import com.example.appmusic.Interface.EventListener;
import com.example.appmusic.Objects.TheLoai;
import com.example.appmusic.R;
import java.util.ArrayList;
import java.util.List;

import static com.example.appmusic.Activity.MainActivity.database;

public class Fragment_TheLoai extends Fragment implements SearchView.OnQueryTextListener, EventListener {
    View view;
    GridView lvTheLoai;
    ArrayList<TheLoai> arrayTheLoai;
    ArrayList<TheLoai> arrayTimKiemTheLoai;
    TheLoaiAdapter adapter;
    SearchView searchView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_theloai, container, false);
        lvTheLoai = view.findViewById(R.id.ListViewTheLoai);
        LayDanhSachTheLoai();
        SetAdapter(arrayTheLoai);
        adapter.notifyDataSetChanged();
        searchView = view.findViewById(R.id.search_bar);
        searchView.setOnQueryTextListener(this);
        return view;
    }
    private void SetAdapter(List<TheLoai> list)
    {
        adapter = new TheLoaiAdapter(getActivity(), R.layout.custom_theloai, list, Fragment_TheLoai.this);
        lvTheLoai.setAdapter(adapter);
    }
    public List<TheLoai> LayDanhSachTheLoai()
    {
        arrayTheLoai = new ArrayList<>();
        Cursor c = database.getData("SELECT * FROM TheLoai ");
        c.moveToFirst();
        while(!c.isAfterLast())
        {
            int id = c.getInt(0);
            String tenTheLoai = c.getString(1);
            String hinhTheLoai = c.getString(2);
            arrayTheLoai.add(new TheLoai(id,tenTheLoai,hinhTheLoai));
            c.moveToNext();
        }
        return arrayTheLoai;
    }
    public List<TheLoai> TimDanhSachTheLoai(String searchText)
    {
        arrayTimKiemTheLoai = new ArrayList<>();
        String sql = "SELECT * FROM TheLoai WHERE TenTheLoai LIKE '%"+searchText+"%'";
        Cursor c = database.getData(sql);
        c.moveToFirst();
        while(!c.isAfterLast())
        {
            int id = c.getInt(0);
            String tenTheLoai = c.getString(1);
            String hinhTheLoai = c.getString(2);
            arrayTimKiemTheLoai.add(new TheLoai(id,tenTheLoai,hinhTheLoai));
            c.moveToNext();
        }
        return arrayTimKiemTheLoai;
    }
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String searchText) {
        TimDanhSachTheLoai(searchText);
        SetAdapter(arrayTimKiemTheLoai);
        return true;
    }

    @Override
    public void showalertdialog(int id, String tenbaihat) {

    }

    @Override
    public void dialogaddplaylist(int idbaihat) {

    }

    @Override
    public void songclick(int id, int index) {

    }

    @Override
    public void playlistClick(int id) {

    }

    @Override
    public void theloaiClick(int id) {
        Intent intent=new Intent(getContext(), PlaySongActivity.class);
        int key=5;
        intent.putExtra("key",key);
        intent.putExtra("id",id);
        startActivity(intent);
    }

    @Override
    public void albumClick(int id) {

    }

    @Override
    public void casiClick(int id) {

    }

    @Override
    public void longclickplaylist(int id) {

    }
}
