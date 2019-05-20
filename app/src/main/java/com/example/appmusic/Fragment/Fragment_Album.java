package com.example.appmusic.Fragment;

import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.SearchView;

import com.example.appmusic.Activity.PlaySongActivity;
import com.example.appmusic.Adapter.AlbumAdapter;
import com.example.appmusic.Interface.EventListener;
import com.example.appmusic.Objects.Album;
import com.example.appmusic.R;
import java.util.ArrayList;
import java.util.List;

import static com.example.appmusic.Activity.MainActivity.database;

public class Fragment_Album extends Fragment implements SearchView.OnQueryTextListener, EventListener {
    EventListener listener;
    View view;
    GridView lvAlbum;
    ArrayList<Album> albumArrayList;
    ArrayList<Album> arrayTimKiemAlbum;
    AlbumAdapter adapter;
    SearchView searchView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_album, container, false);
        lvAlbum = view.findViewById(R.id.GridViewAlbum);
        LayDanhSachAlbum();
        SetAdapter(albumArrayList);
        adapter.notifyDataSetChanged();
        searchView = view.findViewById(R.id.search_bar);
        searchView.setOnQueryTextListener(this);
        return view;
    }
    private void SetAdapter(List<Album> list)
    {
        adapter = new AlbumAdapter(getActivity(), R.layout.custom_album, list, Fragment_Album.this);
        lvAlbum.setAdapter(adapter);
    }
    public List<Album> LayDanhSachAlbum()
    {
        albumArrayList = new ArrayList<>();
        Cursor c = database.getData("SELECT * FROM Album ");
        c.moveToFirst();
        while(!c.isAfterLast())
        {
            int id = c.getInt(0);
            String tenAlbum = c.getString(1);
            String hinhAlbum = c.getString(2);
            albumArrayList.add(new Album(id,tenAlbum,hinhAlbum));
            c.moveToNext();
        }
        return albumArrayList;
    }
    public List<Album> TimDanhSachAlbum(String searchText)
    {
        arrayTimKiemAlbum = new ArrayList<>();
        String sql = "SELECT * FROM Album WHERE TenAlbum LIKE '%"+searchText+"%'";
        Cursor c = database.getData(sql);
        c.moveToFirst();
        while(!c.isAfterLast())
        {
            int id = c.getInt(0);
            String tenAlbum = c.getString(1);
            String hinhAlbum = c.getString(2);
            arrayTimKiemAlbum.add(new Album(id,tenAlbum,hinhAlbum));
            c.moveToNext();
        }
        return arrayTimKiemAlbum;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String searchText) {
        TimDanhSachAlbum(searchText);
        SetAdapter(arrayTimKiemAlbum);
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

    }

    @Override
    public void albumClick(int id) {
        Intent intent=new Intent(getContext(), PlaySongActivity.class);
        int key=4;
        intent.putExtra("key",key);
        intent.putExtra("id",id);
        startActivity(intent);
    }

    @Override
    public void casiClick(int id) {

    }

    @Override
    public void longclickplaylist(int id) {

    }
}
