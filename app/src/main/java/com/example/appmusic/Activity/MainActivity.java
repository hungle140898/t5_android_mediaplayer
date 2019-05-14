package com.example.appmusic.Activity;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.appmusic.Adapter.MainView;
import com.example.appmusic.Database;
import com.example.appmusic.Fragment.Fragment_Album;
import com.example.appmusic.Fragment.Fragment_CaSi;
import com.example.appmusic.Fragment.Fragment_PlayList;
import com.example.appmusic.Fragment.Fragment_TheLoai;
import com.example.appmusic.Fragment.Fragment_TimKiem;
import com.example.appmusic.Fragment.Fragment_TrangChu;
import com.example.appmusic.R;



public class MainActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;
    public static Database database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_main);
        //
        database = new Database(this,"appmp3.sqlite",null,1);
        database.QueryData("CREATE TABLE IF NOT EXISTS Album(IDAlbum INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, TenAlbum VARCHAR NOT NULL,HinhAlbum VARCHAR)");
        database.QueryData("CREATE TABLE IF NOT EXISTS BaiHat(IDBaiHat INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, IDAlbum INTEGER NOT NULL,IDTheLoai INTEGER NOT NULL,IDCaSi INTEGER NOT NULL,TenBaiHat VARCHAR,LinkBaiHat VARCHAR)");
        database.QueryData("CREATE TABLE IF NOT EXISTS CaSi(IDCaSi INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, TenCaSi VARCHAR NOT NULL,HinhCaSi VARCHAR)");
        database.QueryData("CREATE TABLE IF NOT EXISTS PlayList(IDPlayList INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, TenPlayList VARCHAR NOT NULL)");
        database.QueryData("CREATE TABLE IF NOT EXISTS Playlist_BaiHat(IDPlayList INTEGER NOT NULL, IDBaiHat INTEGER NOT NULL,PRIMARY KEY('IDPlayList','IDBaiHat'))");
        database.QueryData("CREATE TABLE IF NOT EXISTS TheLoai(IDTheLoai INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, TenTheLoai VARCHAR NOT NULL,HinhTheLoai VARCHAR)");


/*        database.QueryData("INSERT INTO TheLoai VALUES(null,'EDM','edm')");
        database.QueryData("INSERT INTO TheLoai VALUES(null,'INDIE','indie')");
        database.QueryData("INSERT INTO TheLoai VALUES(null,'NONSTOP','nonstop')");
        database.QueryData("INSERT INTO TheLoai VALUES(null,'Nhạc buồn','sad')");
        database.QueryData("INSERT INTO TheLoai VALUES(null,'RAP','rap')");
        database.QueryData("INSERT INTO TheLoai VALUES(null,'Remix','remix')");
        database.QueryData("INSERT INTO TheLoai VALUES(null,'Piano','piano')");
        database.QueryData("INSERT INTO TheLoai VALUES(null,'Nhạc trịnh','trinh')");*/
        /*database.QueryData("Delete from TheLoai where TenTheLoai = 'nonstop'");
        database.QueryData("Delete from TheLoai where TenTheLoai = 'piano'");
        database.QueryData("Delete from TheLoai where TenTheLoai = 'sad'");
        database.QueryData("Delete from TheLoai where TenTheLoai = 'remix'");
        database.QueryData("Delete from TheLoai where TenTheLoai = 'indie'");
        database.QueryData("Delete from TheLoai where TenTheLoai = 'edm'");*/

       /* database.QueryData("INSERT INTO Album VALUES(null,'Tỏ Tình','totinh')");
        database.QueryData("INSERT INTO Album VALUES(null,'Anh Nhà Ở Đâu Thế ?','amee')");
        database.QueryData("INSERT INTO Album VALUES(null,'Bạc Phận','bacphan')");*/
        //database.QueryData("Delete from Album where TenAlbum = 'Tỏ tình'");
        showFragment();
        init();
    }
    private void init()
    {
        MainView mainViewPagerApdater = new MainView(getSupportFragmentManager());
        mainViewPagerApdater.addFragment(new Fragment_TrangChu(),"Songs");
        mainViewPagerApdater.addFragment(new Fragment_PlayList(),"PlayLists");
        mainViewPagerApdater.addFragment(new Fragment_CaSi(),"Singers");
        mainViewPagerApdater.addFragment(new Fragment_Album(),"Albums");
        mainViewPagerApdater.addFragment(new Fragment_TheLoai(),"Categories");
     //   mainViewPagerApdater.addFragment(new Fragment_TimKiem(),"Search");
        viewPager.setAdapter(mainViewPagerApdater);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.song);
        tabLayout.getTabAt(1).setIcon(R.drawable.playlist);
        tabLayout.getTabAt(2).setIcon(R.drawable.singer);
        tabLayout.getTabAt(3).setIcon(R.drawable.album);
        tabLayout.getTabAt(4).setIcon(R.drawable.category);
        //tabLayout.getTabAt(5).setIcon(R.drawable.search);
    }

    private void showFragment()
    {
        tabLayout = findViewById(R.id.TabLayout);
        viewPager = findViewById(R.id.ViewPaper);
    }

}
