package com.example.appmusic.Activity;
import android.os.IBinder;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.appmusic.Adapter.MainView;
import com.example.appmusic.Database;
import com.example.appmusic.Fragment.Fragment_Album;
import com.example.appmusic.Fragment.Fragment_CaSi;
import com.example.appmusic.Fragment.Fragment_PlayList;
import com.example.appmusic.Fragment.Fragment_TheLoai;
import com.example.appmusic.Fragment.Fragment_TrangChu;
import com.example.appmusic.R;
import com.example.appmusic.Service.PlaySongService;


public class MainActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;
    public static Database database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //
        database = new Database(this,"appmp3.sqlite",null,1);
        database.QueryData("PRAGMA foreign_keys=ON");
        database.QueryData("CREATE TABLE IF NOT EXISTS Album(IDAlbum INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, TenAlbum VARCHAR NOT NULL,HinhAlbum VARCHAR)");
        database.QueryData("CREATE TABLE IF NOT EXISTS BaiHat(IDBaiHat INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, IDAlbum INTEGER NOT NULL,IDTheLoai INTEGER NOT NULL,IDCaSi INTEGER NOT NULL,TenBaiHat VARCHAR,LinkBaiHat VARCHAR)");
        database.QueryData("CREATE TABLE IF NOT EXISTS CaSi(IDCaSi INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, TenCaSi VARCHAR NOT NULL,HinhCaSi VARCHAR)");
        database.QueryData("CREATE TABLE IF NOT EXISTS PlayList(IDPlayList INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, TenPlayList VARCHAR NOT NULL)");
        database.QueryData("CREATE TABLE IF NOT EXISTS Playlist_BaiHat(IDPlayList INTEGER NOT NULL, IDBaiHat INTEGER NOT NULL,PRIMARY KEY('IDPlayList','IDBaiHat'))");
        database.QueryData("CREATE TABLE IF NOT EXISTS TheLoai(IDTheLoai INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, TenTheLoai VARCHAR NOT NULL,HinhTheLoai VARCHAR)");

        showFragment();
        init();
    }
    private void init()
    {
        MainView mainViewPagerApdater = new MainView(getSupportFragmentManager());
        mainViewPagerApdater.addFragment(new Fragment_TrangChu(),"Bài Hát");
        mainViewPagerApdater.addFragment(new Fragment_PlayList(),"PlayList");
        mainViewPagerApdater.addFragment(new Fragment_CaSi(),"Ca Sĩ");
        mainViewPagerApdater.addFragment(new Fragment_Album(),"Album");
        mainViewPagerApdater.addFragment(new Fragment_TheLoai(),"Thể Loại");
        viewPager.setAdapter(mainViewPagerApdater);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.song);
        tabLayout.getTabAt(1).setIcon(R.drawable.playlist);
        tabLayout.getTabAt(2).setIcon(R.drawable.singer);
        tabLayout.getTabAt(3).setIcon(R.drawable.album);
        tabLayout.getTabAt(4).setIcon(R.drawable.category);
    }
    private void showFragment()
    {
        tabLayout = findViewById(R.id.TabLayout);
        viewPager = findViewById(R.id.ViewPaper);
    }

}
