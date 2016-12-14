/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netcracker.education.controller;

import com.netcracker.education.model.*;
import com.netcracker.education.model.Track;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
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
        try{
        TrackList().get(index).setSongName(songName);
        TrackList().get(index).setArtist(artist);
        TrackList().get(index).setLength(length);
        TrackList().get(index).setAlbum(album);
        TrackList().get(index).setGenreList(genreList);
        if(TrackList().contains(TrackList().get(index))) throw new AlreadyExistsException("Track already exists!");
        }
        catch (IllegalArgumentException e){System.err.println(e.getMessage());}
        catch(AlreadyExistsException e){System.err.println(e.getMessage());}
    }
    public void addTrack(String songName,String artist, String album,Duration length)
    {
        try
        {
            int id=0;
        Track track=new Track(id,songName,artist,album,length);
        if(TrackList().contains(track)) throw new AlreadyExistsException("Track already exists!");
        if(TrackList().isEmpty())id=1; else id=TrackList().get(TrackList().size()-1).getId()+1;
        track.setId(id);
        TrackList().add(track);
        }
        catch(AlreadyExistsException e){System.err.println(e.getMessage());}
        catch (IllegalArgumentException e){System.err.println(e.getMessage());}
        
    }
    public void addTrack(String songName,String artist, String album,Duration length,List<Genre> genreList)
    {
        try
        {  
        int id=-1;
        Track track=new Track(id,songName,artist,album,length,genreList);
        if(TrackList().contains(track)) throw new AlreadyExistsException("Track already exists!");
        if(TrackList().isEmpty())id=0; else id=TrackList().get(TrackList().size()-1).getId()+1;
        track.setId(id);
        TrackList().add(track);
        }
        catch (IllegalArgumentException e){System.err.println(e.getMessage());}
        catch(AlreadyExistsException e){System.err.println(e.getMessage());}
        
        
        
    }
    public void delTrack(Track track)
    {
        TrackList().remove(track);
    }    
    public void addGenre(String s) 
    {
        try
        {
            Genre genre=new Genre(s);
            int id=-1;       if (GenreList().isEmpty()){id =0; genre.setId(id);GenreList().add(genre);}
        else
        {
        if(GenreList().contains(genre))throw new AlreadyExistsException("Genre alredy exists");
        else 
        {
            id=GenreList().get(GenreList().size()-1).getId()+1;
            this.GenreList().add(genre);genre.setId(id);
        }
        }
        }
        catch (IllegalArgumentException e){System.err.println(e.getMessage());}
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
    public void delGenre(String genreS)
    {
        Genre genre= new Genre(genreS);
        if(GenreList().contains(genre)) 
        {
            GenreList().remove(genre);
            for(Track track:TrackList()) if (track.getGenreList().contains(genre))track.getGenreList().remove(genre);
        }
    } 
    public void addGenreToTrack(int trackIndex, int genreIndex)
    {
        try{
        if (TrackList().get(trackIndex).getGenreList().contains(GenreList().get(genreIndex))) throw new AlreadyExistsException("Track alreadi has this genre!");
        TrackList().get(trackIndex).addGenre(GenreList().get(genreIndex));
        }
        catch(AlreadyExistsException e){System.err.println(e.getMessage());}
    }
    
    public void sortBySongName()
    {
        Collections.sort(TrackList(), new Track.TracksBySongNameComparator());
    }
    public void sortByArtist()
    {
        Collections.sort(TrackList(),new Track.TracksByArtistComparator());
    }
    public void sortByLength()
    {
        Collections.sort(TrackList(), new Track.TracksByLengthComparator());
    }
    public void sortByAlbum()
    {
        Collections.sort(TrackList(), new Track.TracksByAlbumComparator());
    }
    
}
