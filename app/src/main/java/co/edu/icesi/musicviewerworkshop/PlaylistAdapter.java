package co.edu.icesi.musicviewerworkshop;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.deezer.sdk.network.connect.DeezerConnect;
import com.deezer.sdk.network.request.DeezerRequest;
import com.deezer.sdk.network.request.DeezerRequestFactory;
import com.deezer.sdk.network.request.event.JsonRequestListener;
import com.deezer.sdk.network.request.event.RequestListener;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class PlaylistAdapter extends BaseAdapter {

    private List<Playlist> playList;
    Activity activity;

    public PlaylistAdapter(Activity activity) {
        this.activity = activity;
        playList = new ArrayList<Playlist>();
    }

    @Override
    public int getCount() {
        return playList.size();
    }

    @Override
    public Object getItem(int position) {
        return playList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(activity);
        View view = inflater.inflate(R.layout.item, null, false);

        ImageView playlistImage = view.findViewById(R.id.img_playlist);


        Playlist playlist = playList.get(position);

        try {
            URL url = new URL(playlist.getBigImageUrl());
            Glide.with(activity).load(url).into(playlistImage);

        } catch (MalformedURLException e) {
            Log.e(this.getClass().toString(), "Error al cargar la URL de la foto de la playlist");
        } catch (IOException e) {
            Log.e(this.getClass().toString(), "Error al abrir la conexión de la foto de la playlist");
        }

        TextView playlistName = view.findViewById(R.id.tv_playlistName);
        TextView ownerName = view.findViewById(R.id.tv_ownerName);
        TextView songsNumber = view.findViewById(R.id.tv_songsNumber);

        playlistName.setText(playlist.getTitle());
        ownerName.setText(playlist.getCreator().getName());
        songsNumber.setText("Link: "+playlist.getLink());
        buscarLista(playlist.getId());

        return view;
    }

    public void setPlaylist(List<Playlist> playlist) {
        this.playList = playlist;
        notifyDataSetChanged();
    }

    public void buscarLista(long id) {

        String APPLICATION_ID = "302064";
        DeezerConnect deezerConnect = new DeezerConnect(activity, APPLICATION_ID);


        DeezerRequest DR = DeezerRequestFactory.requestPlaylist(id);

        RequestListener listener = new JsonRequestListener() {

            public void onResult(Object result, Object requestId) {
                Playlist playlist = (Playlist) result;

                Log.e("TEST", "Tamanio " + playlist.getTracks().size());
                // do something with the albums
            }

            public void onUnparsedResult(String requestResponse, Object requestId) {
            }

            public void onException(Exception e, Object requestId) {
            }
        };

        deezerConnect.requestAsync(DR, listener);


    }

}
