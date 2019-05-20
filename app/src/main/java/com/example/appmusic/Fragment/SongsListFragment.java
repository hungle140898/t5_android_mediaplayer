package com.example.appmusic.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import com.example.appmusic.Activity.PlaySongActivity;
import com.example.appmusic.Adapter.SongsListAdapter;
import com.example.appmusic.Objects.Song;
import com.example.appmusic.R;

public class SongsListFragment extends Fragment {
    ArrayList<Song> arrayList = new ArrayList<>();
    int mPosition = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_songs_list, container, false);
        ListView resultsListView = view.findViewById(R.id.listview);
        String[] song = new String[1000];
        String[] singer = new String[1000];
        arrayList = (ArrayList) container.getTag(-16110110);
        mPosition = Integer.parseInt(container.getTag(-16110111).toString());
        if (arrayList.size() > 0) {
            for (int i = 0; i < arrayList.size(); i++) {
                song[i] = arrayList.get(i).getTenBaiHat();
                singer[i] = arrayList.get(i).getTenCaSi();
            }
            SongsListAdapter adapter = new
                    SongsListAdapter(getActivity(), song, singer);
            adapter.setActiveSong(mPosition);
            resultsListView.setAdapter(adapter);

            resultsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    ((PlaySongActivity) getActivity()).mPosition = position;
                    ((PlaySongActivity) getActivity()).psService.setSong(position);
                    ((PlaySongActivity) getActivity()).psService.playSong();
                    ((PlaySongActivity) getActivity()).psService.go();
                    ((PlaySongActivity) getActivity()).viewPager.setTag(-16110400, arrayList.get(position).getTenBaiHat());
                    ((PlaySongActivity) getActivity()).viewPager.setTag(-16110401, arrayList.get(position).getTenCaSi());
                    ((PlaySongActivity) getActivity()).setTotalTime();
                }
            });
        }
        return view;
    }

}
