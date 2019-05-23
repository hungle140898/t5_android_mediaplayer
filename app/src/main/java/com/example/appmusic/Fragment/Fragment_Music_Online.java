package com.example.appmusic.Fragment;

import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.appmusic.Activity.PlaySongActivity;
import com.example.appmusic.Adapter.ListSongAdapter;
import com.example.appmusic.Interface.EventListener;
import com.example.appmusic.Objects.Song;
import com.example.appmusic.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.example.appmusic.Activity.MainActivity.database;

public class Fragment_Music_Online extends Fragment implements  EventListener{
    View view;
    ListView lvbaihatonline;
    ArrayList<Song> arrayBaiHat;
    ListSongAdapter adapter;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_fragment__music__online, container, false);
        lvbaihatonline= view.findViewById(R.id.lv_baihatonline);
        LayDanhSachBaiHat();
        //SetAdapter(arrayBaiHat);
        return view;
    }
    private void SetAdapter(List<Song> listSong)
    {
        adapter = new ListSongAdapter(getActivity(),R.layout.row_baihat,listSong, Fragment_Music_Online.this);
        lvbaihatonline.setAdapter(adapter);
    }
    public ArrayList<Song> LayDanhSachBaiHat()
    {
        arrayBaiHat = new ArrayList<>();
        arrayBaiHat.clear();

       /* while (data.moveToNext())
        {
            Song song = new Song();
            song.setIDBaiHat(data.getInt(0));
            song.setTenBaiHat(data.getString(1));
            song.setTenCaSi(data.getString(2));
            song.setHinh(data.getString(3));
            arrayBaiHat.add(song);
        }*/
       /*Song song=new Song();
       song.setTenBaiHat("Phuc test");
       song.setTenCaSi("Phuc");
       song.setHinh("https://www.google.com/url?sa=i&source=images&cd=&ved=2ahUKEwi-z6T6r6_iAhUgTI8KHXg8DZYQjRx6BAgBEAU&url=https%3A%2F%2Fthenextweb.com%2Fgoogle%2F2018%2F04%2F25%2Freport-google-play-music-will-be-killed-off-and-replaced-by-youtube-remix-this-year%2F&psig=AOvVaw1KVxzHkqTwizCxen9mtR_8&ust=1558622629633200");
       arrayBaiHat.add(song);*/
        AsyncTask asyncTask = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url("https://nhom5mp3.herokuapp.com/list").build();

                Response response = null;

                try {
                    response = client.newCall(request).execute();
                    return response.body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                try{
                    String JsonString="{\"music\":{\"arr\":"+o.toString()+"}}";

                    JSONObject object =(new JSONObject(JsonString)).getJSONObject("music");
                    JSONArray jsonArray=object.getJSONArray("arr");
                    for (int i = 0; i < jsonArray.length(); i++)
                    {
                        try{
                            JSONObject object_temp= jsonArray.getJSONObject(i);
                            Song song=new Song();
                            song.setIDBaiHat(Integer.parseInt( object_temp.getString("id")));
                            song.setTenBaiHat(object_temp.getString("tenbh"));
                            song.setTenCaSi(object_temp.getString("casi"));
                            song.setLink(object_temp.getString("link"));
                            song.setHinh("https://www.google.com/url?sa=i&source=images&cd=&ved=2ahUKEwi-z6T6r6_iAhUgTI8KHXg8DZYQjRx6BAgBEAU&url=https%3A%2F%2Fthenextweb.com%2Fgoogle%2F2018%2F04%2F25%2Freport-google-play-music-will-be-killed-off-and-replaced-by-youtube-remix-this-year%2F&psig=AOvVaw1KVxzHkqTwizCxen9mtR_8&ust=1558622629633200");
                            arrayBaiHat.add(song);
                        }
                        catch (JSONException e){
                            e.printStackTrace();

                        }
                    }
                    SetAdapter(arrayBaiHat);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.execute();
        return arrayBaiHat;
    }

    @Override
    public void showalertdialog(int id, String tenbaihat) {

    }

    @Override
    public void dialogaddplaylist(int idbaihat) {

    }

    @Override
    public void songclick(int id, int index) {
        Intent intent=new Intent(getContext(), PlaySongActivity.class);
        int key=6;
        intent.putExtra("index",index);
        intent.putExtra("key",key);
        Bundle args = new Bundle();
        args.putSerializable("ARRAYLIST", (Serializable) arrayBaiHat);
        intent.putExtra("BUNDLE", args);

        startActivity(intent);
    }

    @Override
    public void playlistClick(int id) {

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
    public void longclickplaylist(int id) {

    }
}
