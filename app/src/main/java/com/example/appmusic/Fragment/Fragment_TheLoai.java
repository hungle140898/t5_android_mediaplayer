package com.example.appmusic.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.example.appmusic.Adapter.TheLoaiAdapter;
import com.example.appmusic.Database;
import com.example.appmusic.Objects.TheLoai;
import com.example.appmusic.R;
import java.util.ArrayList;

public class Fragment_TheLoai extends Fragment {
    View view;
    Database database;
    ListView lvTheLoai;
    ArrayList<TheLoai> arrayTheLoai;
    TheLoaiAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_theloai, container, false);
        lvTheLoai = view.findViewById(R.id.ListViewTheLoai);
        arrayTheLoai = new ArrayList<>();
        adapter = new TheLoaiAdapter(view.getContext(), R.layout.custom_theloai, arrayTheLoai);
        lvTheLoai.setAdapter(adapter);
        //
        database = new Database(view.getContext(), "media.db", null, 1);
        database.QueryData("CREATE TABLE IF NOT EXISTS TheLoai(IDTheLoai INTEGER PRIMARY KEY AUTOINCREMENT, TenTheLoai VARCHAR(200),HinhTheLoai VARCHAR(200))");
        database.QueryData("INSERT INTO TheLoai VALUES (null,'POP','Hinh POP')");
        database.QueryData("INSERT INTO TheLoai VALUES (null,'JAZZ','Hinh JAZZ')");
        //
        Cursor dataTheLoai= database.getData("SELECT * FROM TheLoai ");
        while(dataTheLoai.moveToNext())
        {
            int id = dataTheLoai.getInt(0);
            String tenTheLoai = dataTheLoai.getString(1);
            String hinhTheLoai = dataTheLoai.getString(2);
            arrayTheLoai.add(new TheLoai(id,tenTheLoai,hinhTheLoai));
        }
        adapter.notifyDataSetChanged();
        return view;
    }
}
