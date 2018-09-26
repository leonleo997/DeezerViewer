package co.edu.icesi.musicviewerworkshop.logic;

import com.deezer.sdk.model.Track;
import com.deezer.sdk.model.User;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Playlist {

    private String title;
    private String creator;
    private int nb_tracks;
    private String description;
    private int fans;
    private URL img;

    private List<Track> tracks;


    public Playlist(String title, String creator, int nb_tracks, String description, int fans, URL img) {
        this.title = title;
        this.creator = creator;
        this.nb_tracks = nb_tracks;
        this.description = description;
        this.fans = fans;
        this.img = img;

        tracks=new ArrayList<Track>();

    }


    public List<Track> getTracks() {
        return tracks;
    }

    public void setTracks(List<Track> tracks) {
        this.tracks = tracks;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public void setNb_tracks(int nb_tracks) {
        this.nb_tracks = nb_tracks;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setFans(int fans) {
        this.fans = fans;
    }

    public void setImg(URL img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public String getCreator() {
        return creator;
    }

    public int getNb_tracks() {
        return nb_tracks;
    }

    public String getDescription() {
        return description;
    }

    public int getFans() {
        return fans;
    }

    public URL getImg() {
        return img;
    }
}
