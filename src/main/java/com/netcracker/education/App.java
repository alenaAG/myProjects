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
        List<Genre> genreList=new ArrayList();
        List<Track> trackList=new ArrayList();
        List<Genre> genreListTR1=new ArrayList<Genre>();
        Control controller=new Control(trackList,genreList);
        Duration duration = Duration.parse("PT20.345S");
        Genre genre= new Genre("Pop");
       
             controller.addGenre(genre);                                        // иземнить добавление жанра по его названию
             controller.addGenre(new Genre("Rock"));
             controller.addGenre(new Genre("HIPHOP"));
             controller.addGenre(genre);
             genreListTR1.add(genre);
            
             controller.addTrack("In NY","JAY-Z","NY", duration, genreListTR1);
             controller.addGenreToTrack(0, 2);
       
        System.out.println(controller.GenreList().toString());
        System.out.println(controller.TrackList().toString());
    }
}
