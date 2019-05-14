package com.example.appmusic.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.appmusic.Adapter.ListSongAdapter;
import com.example.appmusic.Objects.Song;
import com.example.appmusic.R;

import java.util.ArrayList;
import java.util.List;


import static com.example.appmusic.Activity.MainActivity.database;

public class Fragment_TrangChu extends Fragment implements ListSongAdapter.EventListener{
    View view;
    ArrayList<Song> songs = new ArrayList<>();
    ListView lv_baihat;
    Spinner spnCategory;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_fragment__album);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_trangchu, container, false);
        getdata();
        return view;
    }
    private void getdata()
    {
        Cursor data = database.getData("SELECT BaiHat.IDBaiHat,BaiHat.TenBaiHat,CaSi.TenCaSi,Album.HinhAlbum FROM BaiHat,CaSi,Album WHERE BaiHat.IDCaSi=CaSi.IDCaSi AND BaiHat.IDAlbum=Album.IDAlbum");
        songs.clear();
        while (data.moveToNext())
        {
            Song song = new Song();
            song.setIDBaiHat(data.getInt(0));
            song.setTenBaiHat(data.getString(1));
            song.setTenCaSi(data.getString(2));
            song.setHinh(data.getString(3));
            songs.add(song);
        }
        lv_baihat=view.findViewById(R.id.lv_baihat);
        ListSongAdapter listSongAdapter = new ListSongAdapter(getActivity(),R.layout.row_baihat,songs,Fragment_TrangChu.this);
        lv_baihat.setAdapter(listSongAdapter);
    }
   /* private void dialogaddplaylist()
    {
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_addplaylist);
        dialog.show();
    }
    private void loaddatadialog()
    {
        List<String> list = new ArrayList<>();
        list.add("Java");
        list.add("Android");
        list.add("PHP");
        list.add("C#");
        list.add("ASP.NET");
        spnCategory = (Spinner)this.findViewById(R.id.spnCategory);
        ArrayAdapter adapter = new ArrayAdapter(getContext(), simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spnCategory.setAdapter(adapter);
    }*/

    public void showalertdialog(final int id, final String tenbaihat)
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Bạn có muốn xóa bài '"+tenbaihat+"'?");
        builder.setCancelable(false);
        builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                database.QueryData("DELETE FROM BaiHat WHERE BaiHat.IDBaiHat='"+id+"'");
                Toast.makeText(getContext(),"Đã xóa bài hát "+tenbaihat,Toast.LENGTH_SHORT).show();
                getdata();
            }
        });
        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
