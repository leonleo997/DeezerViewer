package co.edu.icesi.musicviewerworkshop.logic;

import java.net.URL;
import java.util.Date;

public class Track {
    private String title;
    private String artistName;
    private Date realeaseDate;
    private URL albumCover;
    private String albumName;

    public Track(String title, String artistName, Date realeaseDate, URL albumCover, String albumName) {
        this.title = title;
        this.artistName = artistName;
        this.realeaseDate = realeaseDate;
        this.albumCover = albumCover;
        this.albumName = albumName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public Date getRealeaseDate() {
        return realeaseDate;
    }

    public void setRealeaseDate(Date realeaseDate) {
        this.realeaseDate = realeaseDate;
    }

    public URL getAlbumCover() {
        return albumCover;
    }

    public void setAlbumCover(URL albumCover) {
        this.albumCover = albumCover;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }
}
