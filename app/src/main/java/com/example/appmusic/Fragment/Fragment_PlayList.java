package com.example.appmusic.Fragment;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.appmusic.Activity.PlaySongActivity;
import com.example.appmusic.Adapter.ListPlayListAdapter;
import com.example.appmusic.Adapter.ListSongdialog;
import com.example.appmusic.Objects.PlayList;
import com.example.appmusic.Objects.Song;
import com.example.appmusic.R;
import com.example.appmusic.Interface.EventListener;

import java.net.IDN;
import java.util.ArrayList;
import java.util.List;

import static com.example.appmusic.Activity.MainActivity.database;

public class Fragment_PlayList extends Fragment implements SearchView.OnQueryTextListener, EventListener {
    View view;
    GridView gv_PlayList;
    ArrayList<PlayList> arrayPlayList;
    ArrayList<PlayList> arrayTimKiemPlayList;
    ListPlayListAdapter adapter;
    ArrayList<Song> list = new ArrayList<>();
    SearchView searchView;
    Button bt_addplaylist;
    private boolean isViewShown = false;
    Spinner spnCategory;
    public Song song;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_playlist, container, false);
        gv_PlayList = view.findViewById(R.id.lv_Playlist);
        LayDanhSachPlayList();
        SetAdapter(arrayPlayList);
        adapter.notifyDataSetChanged();
        searchView = view.findViewById(R.id.search_bar);
        searchView.setOnQueryTextListener(this);
        bt_addplaylist = (Button) view.findViewById(R.id.bt_addplaylist);
        bt_addplaylist.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogthem();
            }
        });
        setUserVisibleHint(isViewShown);
        isViewShown=true;
        return view;
    }

    private void SetAdapter(List<PlayList> list) {
        adapter = new ListPlayListAdapter(getActivity(), R.layout.row_playlist, list, Fragment_PlayList.this);
        gv_PlayList.setAdapter(adapter);
    }

    public ArrayList<PlayList> LayDanhSachPlayList() {
        arrayPlayList = new ArrayList<>();
        Cursor data = database.getData("SELECT PlayList.IDPlayList,PlayList.TenPlayList,count(Playlist_BaiHat.IDPlayList) FROM PlayList LEFT JOIN Playlist_BaiHat ON PlayList.IDPlayList = Playlist_BaiHat.IDPlayList GROUP by PlayList.IDPlayList");
        while (data.moveToNext()) {
            PlayList playList = new PlayList();
            playList.setIDPlayList(data.getInt(0));
            playList.setTenPlayList(data.getString(1));
            playList.setSoBaiHat(data.getInt(2));
            arrayPlayList.add(playList);
        }
        return arrayPlayList;
    }

    public ArrayList<PlayList> TimKiemPlayList(String searchText) {
        arrayTimKiemPlayList = new ArrayList<>();
        Cursor data = database.getData("SELECT PlayList.IDPlayList,PlayList.TenPlayList,count(Playlist_BaiHat.IDPlayList) FROM PlayList LEFT JOIN Playlist_BaiHat ON PlayList.IDPlayList = Playlist_BaiHat.IDPlayList  WHERE PlayList.TenPlayList LIKE '%" + searchText + "%' GROUP by PlayList.IDPlayList ");
        while (data.moveToNext()) {
            PlayList playList = new PlayList();
            playList.setIDPlayList(data.getInt(0));
            playList.setTenPlayList(data.getString(1));
            playList.setSoBaiHat(data.getInt(2));
            arrayTimKiemPlayList.add(playList);
        }
        return arrayTimKiemPlayList;
    }

    private void getdata() {
        arrayPlayList.clear();
        Cursor data = database.getData("SELECT PlayList.IDPlayList,PlayList.TenPlayList,count(Playlist_BaiHat.IDPlayList) FROM PlayList LEFT JOIN Playlist_BaiHat ON PlayList.IDPlayList = Playlist_BaiHat.IDPlayList GROUP by PlayList.IDPlayList");
        while (data.moveToNext()) {
            PlayList playList = new PlayList();
            playList.setIDPlayList(data.getInt(0));
            playList.setTenPlayList(data.getString(1));
            playList.setSoBaiHat(data.getInt(2));
            arrayPlayList.add(playList);
        }
        gv_PlayList = view.findViewById(R.id.lv_Playlist);
        ListPlayListAdapter listPlayListAdapter = new ListPlayListAdapter(getActivity(), R.layout.row_playlist, arrayPlayList, Fragment_PlayList.this);
        gv_PlayList.setAdapter(listPlayListAdapter);
    }

    private void dialogthem() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.diaglog_createplaylist);
        final EditText et_nhapten = dialog.findViewById(R.id.et_nhapten);
        Button bt_huy = dialog.findViewById(R.id.bt_huy);
        Button bt_xacnhan = dialog.findViewById(R.id.bt_xacnhan);
        bt_huy.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        bt_xacnhan.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenplaylist = et_nhapten.getText().toString();
                if (tenplaylist.equals("")) {
                    Toast.makeText(getActivity(), "Vui lòng nhập đầy đủ", Toast.LENGTH_SHORT).show();
                } else {
                    database.QueryData("INSERT INTO PlayList VALUES (NULL,'" + tenplaylist + "')");
                    Toast.makeText(getActivity(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    getdata();
                }
            }
        });
        dialog.show();
    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String searchText) {
        TimKiemPlayList(searchText);
        SetAdapter(arrayTimKiemPlayList);
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
        Intent intent = new Intent(getContext(), PlaySongActivity.class);
        int key = 2;
        intent.putExtra("key", key);
        intent.putExtra("id", id);
        startActivity(intent);
        //Toast.makeText(getActivity(),String.valueOf(id),Toast.LENGTH_LONG).show();
    }

    @Override
    public void theloaiClick(int id) {

    }

    @Override
    public void albumClick(int id) {

    }

    @Override
    public void casiClick(int id) {

    }

    @Override
    public void longclickplaylist(final int idplaylist) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog_deletesongonplaylist);
        spnCategory = (Spinner)dialog.findViewById(R.id.spnCategory);
        Button btn_xacnhan = dialog.findViewById(R.id.btn_xacnhan);
        Button btn_huy = dialog.findViewById(R.id.btn_huy);
        loaddatadialog(idplaylist);
        final  ListSongdialog adapter =  new ListSongdialog(getActivity(),android.R.layout.simple_spinner_item,list);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spnCategory.setAdapter(adapter);
        spnCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                song = adapter.getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btn_xacnhan.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                database.QueryData("DELETE FROM Playlist_BaiHat WHERE Playlist_BaiHat.IDBaiHat="+song.getIDBaiHat()+" AND Playlist_BaiHat.IDPlayList IN("+song.getIDBaiHat()+","+idplaylist+")");
                Toast.makeText(getActivity(),"Bạn đã xóa bài hát ra khỏi playlist",Toast.LENGTH_LONG).show();
                getdata();
                dialog.dismiss();
            }
        });
        btn_huy.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    private void loaddatadialog(int idplaylist)
    {
        Cursor cursor = database.getData("SELECT BaiHat.IDBaiHat,BaiHat.TenBaiHat FROM BaiHat INNER JOIN Playlist_BaiHat ON BaiHat.IDBaiHat=Playlist_BaiHat.IDBaiHat WHERE Playlist_BaiHat.IDPlayList="+ idplaylist);
        list.clear();
        while (cursor.moveToNext())
        {
            Song song = new Song();
            song.setIDBaiHat(cursor.getInt(0));
            song.setTenBaiHat(cursor.getString(1));
            list.add(song);
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (getView() != null && isVisibleToUser) {
            isViewShown = true; // fetchdata() contains logic to show data when page is selected mostly asynctask to fill the data
            LayDanhSachPlayList();
            SetAdapter(arrayPlayList);
        } else {
            isViewShown = false;
        }
    }
}
