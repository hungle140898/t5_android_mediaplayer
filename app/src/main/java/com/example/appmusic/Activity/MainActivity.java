package com.example.appmusic.Activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.example.appmusic.Adapter.MainView;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //
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
        mainViewPagerApdater.addFragment(new Fragment_TimKiem(),"Search");
        viewPager.setAdapter(mainViewPagerApdater);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.song);
        tabLayout.getTabAt(1).setIcon(R.drawable.playlist);
        tabLayout.getTabAt(2).setIcon(R.drawable.singer);
        tabLayout.getTabAt(3).setIcon(R.drawable.album);
        tabLayout.getTabAt(4).setIcon(R.drawable.category);
        tabLayout.getTabAt(5).setIcon(R.drawable.search);
    }

    private void showFragment()
    {
        tabLayout = findViewById(R.id.TabLayout);
        viewPager = findViewById(R.id.ViewPaper);
    }

}
