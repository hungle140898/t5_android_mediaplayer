package com.example.appmusic.Fragment;

import android.app.Dialog;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appmusic.Adapter.ListPlayListAdapter;
import com.example.appmusic.Objects.PlayList;
import com.example.appmusic.R;

import java.util.ArrayList;

import static com.example.appmusic.Activity.MainActivity.database;

public class Fragment_PlayList extends Fragment {
    View view;
    ListView lv_PlayList;
    ArrayList<PlayList> playLists = new ArrayList<>();
    Button bt_addplaylist;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_fragment__album);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_playlist, container, false);
        bt_addplaylist = (Button) view.findViewById(R.id.bt_addplaylist);
        bt_addplaylist.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogthem();
            }
        });
        getdata();
        return view;
    }

    private void getdata() {
        playLists.clear();
        Cursor data = database.getData("SELECT PlayList.IDPlayList,PlayList.TenPlayList,count(Playlist_BaiHat.IDPlayList) FROM PlayList LEFT JOIN Playlist_BaiHat ON PlayList.IDPlayList = Playlist_BaiHat.IDPlayList GROUP by PlayList.IDPlayList");

        while (data.moveToNext()) {
            PlayList playList = new PlayList();
            playList.setIDPlayList(data.getInt(0));
            playList.setTenPlayList(data.getString(1));
            playList.setSoBaiHat(data.getInt(2));
            playLists.add(playList);
        }
        lv_PlayList = view.findViewById(R.id.lv_Playlist);
        ListPlayListAdapter listPlayListAdapter = new ListPlayListAdapter(getActivity(), R.layout.row_playlist, playLists);
        lv_PlayList.setAdapter(listPlayListAdapter);
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


}
