/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netcracker.education.controller;

import com.netcracker.education.model.*;
import java.time.Duration;
import java.util.List;
public class Control {
    public static void editTrack(Track track,String songName,String artist, String album,Duration length)
    {
        track.setSongName(songName);
        track.setArtist(artist);
        track.setLength(length);
        track.setAlbum(album);
    }
    public static void addTrack(List trackList,Track track)
    {
        int index=trackList.size();
        trackList.add(index, trackList);
        track.setId(index);
    }
    public static void delTrack(List trackList, Track track)
    {
        int k=trackList.lastIndexOf(track);
        trackList.remove(k);
    }
    
}
