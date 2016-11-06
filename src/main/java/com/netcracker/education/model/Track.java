
package com.netcracker.education.model;

import java.time.Duration;
import java.util.List;

public class Track {
    private int id;
    private String songName;
    private String artist;
    private String album;
    private Duration length;
    private List genreList;
    public int getId(){return id;}
    public String getSongName(){return songName;}
    public String getArtist(){return artist;}
    public String getAlbum(){return album;}
    public Duration getLength(){return length;}
    public List getGenreList(){return genreList;}
    public void setId(int id){this.id=id;}
    public void setSongName(String songName){this.songName=songName;}
    public void setArtist(String artist){this.album=album;}
    public void setLength(Duration length){this.length=length;}
    public void setGenreList(List genreList){this.genreList=genreList;}
}
