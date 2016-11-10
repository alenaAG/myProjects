
package com.netcracker.education.model;

import java.time.Duration;
import static java.time.Duration.*;
import java.util.ArrayList;
import java.util.List;

public class Track {
    
    private final static String DEFAULT_ARTIST="default";
    private final static int DEFAULT_ID=-1;
    private final static String DEFAULT_SONG_NAME="default";
    private final static String DEFAULT_ALBUM="default";
    private final static List DEAFAULT_GENRE_LIST= new ArrayList();
    private final static Duration DEFAULT_LENGTH=ZERO;
    private int id;
    private String songName;
    private String artist;
    private String album;
    private Duration length;
    private List genreList;
    public Track(){
        this.id=DEFAULT_ID;this.album=DEFAULT_ALBUM;
        this.artist=DEFAULT_ARTIST;
        this.length=DEFAULT_LENGTH;
        this.genreList=DEAFAULT_GENRE_LIST;
    };
    public Track(String name,String artist, String album,Duration length, List genreList)
    {
        this.album=album;
        this.artist=artist;
        this.songName=songName;
        this.length=length;
        this.genreList=genreList;
        this.id=DEFAULT_ID;
    }
    public Track(int id,String name,String artist, String album,Duration length, List genreList)
    {
        this.album=album;
        this.artist=artist;
        this.songName=songName;
        this.length=length;
        this.genreList=genreList;
        this.id=id;
    }
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
    public void setAlbum(String Album){this.album=album;}
    public void setGenreList(List genreList){this.genreList=genreList;}
}
