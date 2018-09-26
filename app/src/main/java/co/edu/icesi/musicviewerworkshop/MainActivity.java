package co.edu.icesi.musicviewerworkshop;

import android.graphics.ImageDecoder;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.deezer.sdk.model.Album;
import com.deezer.sdk.model.Playlist;
import com.deezer.sdk.model.Permissions;
import com.deezer.sdk.network.connect.DeezerConnect;
import com.deezer.sdk.network.connect.SessionStore;
import com.deezer.sdk.network.connect.event.DialogListener;
import com.deezer.sdk.network.request.DeezerRequest;
import com.deezer.sdk.network.request.DeezerRequestFactory;
import com.deezer.sdk.network.request.event.JsonRequestListener;
import com.deezer.sdk.network.request.event.RequestListener;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String APPLICATION_ID = "302064";

    private ListView list_Playlist;

    private PlaylistAdapter playlistAdapter;

    private DeezerConnect deezerConnect;

    private ImageButton img_search;

    private EditText et_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        deezerConnect = new DeezerConnect(this, APPLICATION_ID);

        inicializarLista("Eminem");

        playlistAdapter = new PlaylistAdapter(this);
        list_Playlist = findViewById(R.id.list_playlist);
        list_Playlist.setAdapter(playlistAdapter);

        et_search=findViewById(R.id.et_searchBar);


        img_search=findViewById(R.id.btn_search);
        img_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre=et_search.getText().toString();
                inicializarLista(nombre);
            }
        });


    }


    public void inicializarLista(String query) {
        DeezerRequest DR = DeezerRequestFactory.requestSearchPlaylists(query);

        RequestListener listener = new JsonRequestListener() {

            public void onResult(Object result, Object requestId) {
                List<Playlist> playlists = (List<Playlist>) result;
                //Log.e("TEST", "Tamanio " + playlists.size());
                playlistAdapter.setPlaylist(playlists);
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
