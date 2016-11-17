package com.netcracker.education;

import com.netcracker.education.model.Genre;
import com.netcracker.education.model.Track;

import com.netcracker.education.controller.*;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        ArrayList<Genre> genreList=new ArrayList();
        ArrayList<Track> trackList=new ArrayList();
        ArrayList<Genre> genreListTR1=new ArrayList<Genre>();
        Control controller=new Control(trackList,genreList);
        Duration duration = Duration.parse("PT20.345S");
             controller.addGenre("Pop");  
             controller.addGenre("Rock");
             controller.addGenre("Rock");
             controller.addGenre("NewMusic");
             controller.addGenre("Ambient");
             controller.addTrack("In NY","JAY-Z","NY", duration, genreListTR1);
             controller.addGenreToTrack(0, 1);
             controller.addTrack("Too Good", "Drake", "Good", duration, genreListTR1);
             controller.addTrack("Fine","Drake","Good",duration);
             controller.addTrack("Damn","Damn","Bad",duration);
       
        System.out.println(controller.GenreList().toString());
        System.out.println(controller.TrackList().toString());
        System.out.println("Sort By SongName:");
        controller.sortBySongName();
        System.out.println(controller.TrackList().toString());
        System.out.println("Sort By Artist:");
        controller.sortByArtist();
        System.out.println(controller.TrackList().toString());
        System.out.println("Sort By Album:");
        controller.sortByAlbum();
        System.out.println(controller.TrackList().toString());
        System.out.println("Sort By Length:");
        controller.sortByLength();
        System.out.println(controller.TrackList().toString());
        
        
    }
}
