package com.example.appmusic.Fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import com.example.appmusic.Adapter.Adapter_Tabs_Online;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.appmusic.R;

public class Fragment_TrangChu extends Fragment {
    View view;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_trangchu, container, false);
        TabLayout tabLayout = view.findViewById(R.id.TabLayout);
        ViewPager viewPager = view.findViewById(R.id.ViewPaper);

        Adapter_Tabs_Online adapter = new Adapter_Tabs_Online(getChildFragmentManager());
        adapter.addFragment(new Fragment_Music_Offline(), "OFFLINE ");
        adapter.addFragment(new Fragment_Music_Online(), "ONLINE ");
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(adapter);
        return view;
    }
}
