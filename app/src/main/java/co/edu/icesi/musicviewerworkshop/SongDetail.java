package co.edu.icesi.musicviewerworkshop;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.deezer.sdk.model.Track;
import com.deezer.sdk.network.connect.DeezerConnect;
import com.deezer.sdk.network.request.event.DeezerError;
import com.deezer.sdk.player.AlbumPlayer;
import com.deezer.sdk.player.TrackPlayer;
import com.deezer.sdk.player.exception.TooManyPlayersExceptions;
import com.deezer.sdk.player.networkcheck.WifiAndMobileNetworkStateChecker;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class SongDetail extends AppCompatActivity {


    public static final String APPLICATION_ID = "302064";

    private DeezerConnect deezerConnect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_detail);


        deezerConnect = new DeezerConnect(this, APPLICATION_ID);

        ImageView trackImage = this.findViewById(R.id.img_track);

        final Track track = (Track) getIntent().getExtras().get("track");

        TextView name = this.findViewById(R.id.tv_songName);
        name.setText(track.getTitle());
        TextView album = this.findViewById(R.id.tv_albumName);
        album.setText(track.getAlbum().getTitle());
        TextView artist = this.findViewById(R.id.tv_artistName);
        artist.setText(track.getArtist().getName());
        TextView duration = this.findViewById(R.id.tv_songDuration);
        int minutes = track.getDuration() / 60;
        int seconds = track.getDuration() - minutes * 60;
        if ((seconds + "").length() == 1)
            duration.setText(minutes + ":0" + seconds);
        else
            duration.setText(minutes + ":" + seconds);

        try {
            URL url = new URL(track.getAlbum().getImageUrl());
            Glide.with(this).load(url).into(trackImage);

        } catch (MalformedURLException e) {
            Log.e(this.getClass().toString(), "Error al cargar la URL de la foto de la playlist");
        } catch (IOException e) {
            Log.e(this.getClass().toString(), "Error al abrir la conexión de la foto de la playlist");
        }


        final Button play = this.findViewById(R.id.btn_listen);

        final boolean[] parar = {false};
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("BUTTON", "onClick: " + track.getLink());

                //play.setEnabled(false);
                ThreadGroup tg = new ThreadGroup("hilos");

                Thread hilo = new Thread(tg,"One") {
                    @Override
                    public void run() {
                        super.run();
                        TrackPlayer trackPlayer = null;
                        try {
                            trackPlayer = new TrackPlayer(getApplication(), deezerConnect, new WifiAndMobileNetworkStateChecker());

                        } catch (TooManyPlayersExceptions tooManyPlayersExceptions) {
                            tooManyPlayersExceptions.printStackTrace();
                        } catch (DeezerError deezerError) {
                            deezerError.printStackTrace();
                        }
                        trackPlayer.playTrack(track.getId());
                    }
                };
                hilo.start();

                Thread hilo2 = new Thread(tg,"two") {
                    @Override
                    public void run() {
                        super.run();
                        play.setText("Wait");
                        try {
                            sleep(32000l);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        //play.setEnabled(true);
                        play.setText("Play");
                    }
                };
                hilo2.start();



//                    Thread.sleep(150000l);
//                    trackPlayer.stop();
//                    trackPlayer.release();

            }
        });

        Button open = this.findViewById(R.id.btn_open);
        open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(track.getLink()));
                startActivity(intent);
            }
        });
    }
}
