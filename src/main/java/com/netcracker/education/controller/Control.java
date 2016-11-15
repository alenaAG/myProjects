/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netcracker.education.controller;

import com.netcracker.education.model.*;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
public class Control {
    private List<Track> trackList=new ArrayList();
    private List<Genre> genreList=new ArrayList();
    public Control(List<Track> trackList,List<Genre> genreList)
    {
        this.trackList=trackList;
        this.genreList=genreList;
    }
    public List<Track> TrackList()
    {return trackList;}
    public List<Genre> GenreList()
    {return genreList;}
    public void editTrack(int index,String songName,String artist, String album,Duration length,List<Genre> genreList) 
    {
        TrackList().get(index).setSongName(songName);
        TrackList().get(index).setArtist(artist);
        TrackList().get(index).setLength(length);
        TrackList().get(index).setAlbum(album);
        TrackList().get(index).setGenreList(genreList);
    }
    public void addTrack(String songName,String artist, String album,Duration length,List<Genre> genreList)
    {
        try
        {
            int id=0;
        Track track=new Track(id,songName,artist,album,length,genreList);
        if(TrackList().contains(track)) throw new AlreadyExistsException();
        if(TrackList().isEmpty())id=1; else id=TrackList().get(TrackList().size()-1).getId()+1;
        track.setId(id);
        TrackList().add(track);
        }
        catch(AlreadyExistsException e){System.err.println(e.getMessage());}
        
    }
    public void delTrack(Track track)
    {
        TrackList().remove(track);
    }    
    public void addGenre(Genre genre) 
    {
        try
        {int id=0;
        if (GenreList().isEmpty())id =1; else id=GenreList().get(GenreList().size()-1).getId()+1;
        
        if(GenreList().contains(genre))throw new AlreadyExistsException();
        else 
        {
            this.GenreList().add(genre);genre.setId(id);
        }
        }
        catch(AlreadyExistsException e){System.err.println(e.getMessage());}
                
    }
    public void delGenre(Genre genre)
    {
        if(GenreList().contains(genre)) 
        {
            GenreList().remove(genre);
            for(Track track:TrackList()) if (track.getGenreList().contains(genre))track.getGenreList().remove(genre);
        }
    }  
    public void addGenreToTrack(int trackIndex, int genreIndex)
    {
        TrackList().get(trackIndex).addGenre(GenreList().get(genreIndex));
    }
}
