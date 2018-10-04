package co.edu.icesi.musicviewerworkshop;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.deezer.sdk.model.Playlist;
import com.deezer.sdk.model.Track;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SongAdapter extends BaseAdapter {
    private Activity activity;
    private List<Track> trackList;
    private Playlist playlist;

    public SongAdapter(Activity activity) {
        this.activity = activity;
        trackList = new ArrayList<Track>();
    }

    @Override
    public int getCount() {
        return trackList.size();
    }

    @Override
    public Object getItem(int position) {
        return trackList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(activity);
        View view = inflater.inflate(R.layout.song, null, false);


        ImageView trackImage = view.findViewById(R.id.img_song);
        TextView trackName = view.findViewById(R.id.tv_songName);
        TextView trackArtist = view.findViewById(R.id.tv_songArtist);
        TextView trackRelease = view.findViewById(R.id.tv_releaseYear);

        Track track = trackList.get(position);

        try {
            URL url = new URL(track.getAlbum().getImageUrl());
            Glide.with(activity).load(url).into(trackImage);

        } catch (MalformedURLException e) {
            Log.e(this.getClass().toString(), "Error al cargar la URL de la foto de la playlist");
        } catch (IOException e) {
            Log.e(this.getClass().toString(), "Error al abrir la conexión de la foto de la playlist");
        }


        trackName.setText(track.getTitle());
        trackArtist.setText(track.getArtist().getName());
        //Log.e("adaptador song);
        trackRelease.setText("Song Rank: "+track.getRank());

        return view;
    }

    public void setValues(Playlist playlist) {
        this.trackList = playlist.getTracks();
        this.playlist = playlist;
        notifyDataSetChanged();
    }

}
