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
    public static List<Track> trackList=new ArrayList();
    public static List<Genre> genreList=new ArrayList();
    public static void editTrack(Track track,String songName,String artist, String album,Duration length)
    {
        track.setSongName(songName);
        track.setArtist(artist);
        track.setLength(length);
        track.setAlbum(album);
    }
    public static void addTrack(List<Track> trackList,Track track)
    {
        int index=trackList.size();
        trackList.add(index, track);
        track.setId(index);
        trackList.add(track);
    }
    public static void delTrack(Track track)
    {
        trackList.remove(track);
        for (Genre genre :genreList)
        {
            if(genre.getTrackList().contains(track))genre.getTrackList().remove(track);
        }
    }
    
    public static void addGenre(Track track, Genre genre)
    {
        track.getGenreList().add(genre);
        genre.getTrackList().add(track);
    }
    public static void delGenre(Track track, Genre genre)
    {
        if(track.getGenreList().contains(genre)) track.getGenreList().remove(genre);
    }
    public static void setSongName(Track track, String songName)
    {
        track.setSongName(songName);
    }
    public static void setArtist(Track track, String artist)
    {
        track.setArtist(artist);
    }
    public static void setAkbum(Track track, String album)
    {
        track.setAlbum(album);
    }
    public static void setLength(Track track, Duration length)
    {
        track.setLength(length);
    }
    public static void setGenreName(Genre genre, String name)
    {
        genre.setGenreName(name);
    }
    
}
