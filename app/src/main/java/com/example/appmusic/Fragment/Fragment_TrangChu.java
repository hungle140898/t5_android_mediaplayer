package com.example.appmusic.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.appmusic.Activity.PlaySongActivity;
import com.example.appmusic.Adapter.ListSongAdapter;
import com.example.appmusic.Adapter.Playlistdialog;
import com.example.appmusic.Objects.PlayList;
import com.example.appmusic.Objects.Song;
import com.example.appmusic.R;
import com.example.appmusic.Interface.EventListener;
import java.util.ArrayList;
import java.util.List;


import static com.example.appmusic.Activity.MainActivity.database;

public class Fragment_TrangChu extends Fragment implements EventListener,SearchView.OnQueryTextListener{
    View view;
    ListView lv_baihat;
    Spinner spnCategory;
    ArrayList<Song> arrayBaiHat;
    ArrayList<Song> arrayTimKiemBaiHat;
    ArrayList<PlayList> list = new ArrayList<>();
    ListSongAdapter adapter;
    SearchView searchView;
    public PlayList playList;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_trangchu, container, false);
        lv_baihat = view.findViewById(R.id.lv_baihat);
        LayDanhSachBaiHat();
        SetAdapter(arrayBaiHat);
        adapter.notifyDataSetChanged();
        searchView = view.findViewById(R.id.search_bar);
        searchView.setOnQueryTextListener(this);
        return view;
    }
    private void SetAdapter(List<Song> listSong)
    {
        adapter = new ListSongAdapter(getActivity(),R.layout.row_baihat,listSong, Fragment_TrangChu.this);
        lv_baihat.setAdapter(adapter);
    }
    private void getdata()
    {
        arrayBaiHat = new ArrayList<>();
        Cursor data = database.getData("SELECT BaiHat.IDBaiHat,BaiHat.TenBaiHat,CaSi.TenCaSi,Album.HinhAlbum FROM BaiHat,CaSi,Album WHERE BaiHat.IDCaSi=CaSi.IDCaSi AND BaiHat.IDAlbum=Album.IDAlbum");
        arrayBaiHat.clear();
        while (data.moveToNext())
        {
            Song song = new Song();
            song.setIDBaiHat(data.getInt(0));
            song.setTenBaiHat(data.getString(1));
            song.setTenCaSi(data.getString(2));
            song.setHinh(data.getString(3));
            arrayBaiHat.add(song);
        }
        lv_baihat=view.findViewById(R.id.lv_baihat);
        ListSongAdapter listSongAdapter = new ListSongAdapter(getActivity(),R.layout.row_baihat,arrayBaiHat,Fragment_TrangChu.this);
        lv_baihat.setAdapter(listSongAdapter);
    }
    public ArrayList<Song> LayDanhSachBaiHat()
    {
        arrayBaiHat = new ArrayList<>();
        Cursor data = database.getData("SELECT BaiHat.IDBaiHat,BaiHat.TenBaiHat,CaSi.TenCaSi,Album.HinhAlbum FROM BaiHat,CaSi,Album WHERE BaiHat.IDCaSi=CaSi.IDCaSi AND BaiHat.IDAlbum=Album.IDAlbum");
        arrayBaiHat.clear();
        while (data.moveToNext())
        {
            Song song = new Song();
            song.setIDBaiHat(data.getInt(0));
            song.setTenBaiHat(data.getString(1));
            song.setTenCaSi(data.getString(2));
            song.setHinh(data.getString(3));
            arrayBaiHat.add(song);
        }
        return arrayBaiHat;
    }
    public ArrayList<Song> LayDanhSachTimKiemBaiHat(String searchText)
    {
        arrayTimKiemBaiHat = new ArrayList<>();
        Cursor data = database.getData("SELECT BaiHat.IDBaiHat,BaiHat.TenBaiHat,CaSi.TenCaSi,Album.HinhAlbum FROM BaiHat,CaSi,Album WHERE BaiHat.IDCaSi=CaSi.IDCaSi AND BaiHat.IDAlbum=Album.IDAlbum " +
                "AND BaiHat.TenBaiHat LIKE '%"+searchText+"%'");
        while (data.moveToNext())
        {
            Song song = new Song();
            song.setIDBaiHat(data.getInt(0));
            song.setTenBaiHat(data.getString(1));
            song.setTenCaSi(data.getString(2));
            song.setHinh(data.getString(3));
            arrayTimKiemBaiHat.add(song);
        }
        return arrayTimKiemBaiHat;
    }
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
    public void dialogaddplaylist(final int idbaihat)
    {
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog_addplaylist);
        spnCategory = (Spinner)dialog.findViewById(R.id.spnCategory);
        Button btn_xacnhan = dialog.findViewById(R.id.btn_xacnhan);
        Button btn_huy = dialog.findViewById(R.id.btn_huy);
        loaddatadialog();
        final Playlistdialog adapter = new Playlistdialog(getActivity(), android.R.layout.simple_spinner_item,list);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spnCategory.setAdapter(adapter);
        spnCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                playList = adapter.getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btn_xacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor = database.getData("SELECT Playlist_BaiHat.IDBaiHat FROM Playlist_BaiHat WHERE Playlist_BaiHat.IDPlayList="+playList.getIDPlayList());
                int test = 0;
                while (cursor.moveToNext())
                {
                    int temp = cursor.getInt(0);
                    if (temp==idbaihat)
                    {
                        test=1;
                        break;
                    }
                }
                if (test==1)
                {
                    Toast.makeText(getActivity(),"Bài hát này đã có trong Playlist được chọn!!!",Toast.LENGTH_LONG).show();
                }else {
                    database.QueryData("INSERT INTO Playlist_BaiHat VALUES ('"+playList.getIDPlayList()+"','"+idbaihat+"')");
                    Toast.makeText(getActivity(),"Đã thêm bài hát vào playlist!!!",Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                }
            }
        });
        btn_huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    private void loaddatadialog()
    {
        Cursor cursor = database.getData("SELECT * FROM PlayList");
        list.clear();
        while (cursor.moveToNext())
        {
            PlayList playList = new PlayList();
            playList.setIDPlayList(cursor.getInt(0));
            playList.setTenPlayList(cursor.getString(1));
            list.add(playList);
        }
    }
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String searchText) {
        LayDanhSachTimKiemBaiHat(searchText);
        SetAdapter(arrayTimKiemBaiHat);
        return true;
    }
    public void songclick(int id)
    {
        Intent intent=new Intent(getContext(), PlaySongActivity.class);
        intent.putExtra("text","abc");
        startActivity(intent);
        //Toast.makeText(getActivity(),String.valueOf(id),Toast.LENGTH_LONG).show();
    }
}
