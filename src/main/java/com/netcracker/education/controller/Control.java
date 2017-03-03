/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netcracker.education.controller;

import com.netcracker.education.model.*;
import com.netcracker.education.model.Track;
import java.io.Serializable;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Control implements Serializable{

    private ObservableList<Track> trackList = FXCollections.observableArrayList(new ArrayList());
    private ObservableList<Genre> genreList = FXCollections.observableArrayList(new ArrayList());

    public Control(List<Track> trackList, List<Genre> genreList) {
        this.trackList = FXCollections.observableArrayList(trackList);
        this.genreList = FXCollections.observableArrayList(genreList);
    }

    public ObservableList<Track> TrackList() {
        return this.trackList;
    }

    public ObservableList<Genre> GenreList() {
        return this.genreList;
    }

    public void editTrack(int id, String songName, String artist, String album, Duration length) throws AlreadyExistsException {
        Track track = new Track(songName, artist, album, length);
        if (this.containsTrack(track)) {
            throw new AlreadyExistsException("Track already exists!");
        } else {
            getTrackById(id).setSongName(songName);
            getTrackById(id).setArtist(artist);
            getTrackById(id).setLength(length);
            getTrackById(id).setAlbum(album);
        }

    }

    public void addTrack(String songName, String artist, String album, Duration length) throws AlreadyExistsException {
        int id = -1;
        Track track = new Track(songName, artist, album, length);
        if (containsTrack(track)) {
            throw new AlreadyExistsException("Track already exists!");
        } else {
            if (TrackList().isEmpty()) {
                id = 0;
            } else {
                id = TrackList().get(TrackList().size() - 1).getId() + 1;
            }
            track.setId(id);
            this.trackList.add(track);
        }

    }

    public void addTrack(String songName, String artist, String album, Duration length, List<Genre> genreList) throws AlreadyExistsException {
        int id = -1;
        Track track = new Track(songName, artist, album, length);
        if (this.containsTrack(track)) {
            throw new AlreadyExistsException("Track already exists!");
        } else {
            if (TrackList().isEmpty()) {
                id = 0;
            } else {
                id = TrackList().get(TrackList().size() - 1).getId() + 1;
            }
            track.setId(id);
            track.setGenreList(genreList);
            this.trackList.add(track);
        }
    }

    public void delTrack(Track track) {
        this.trackList.remove(track);
    }

    public void addGenre(String s) throws AlreadyExistsException {

        Genre genre = new Genre(s);
        int id = -1;
        if (containsGenre(this.genreList, genre)) {
            throw new AlreadyExistsException("");
        } else {
            if (GenreList().isEmpty()) {
                id = 0;
                genre.setId(id);
                this.genreList.add(genre);
            } else {
                id = GenreList().get(GenreList().size() - 1).getId() + 1;

                genre.setId(id);
                this.genreList.add(genre);
            }
        }

    }

    public void delGenre(Genre genre) {
        if (containsGenre(this.GenreList(),genre)) {
            this.genreList.remove(genre);
            for (Track track : this.trackList) {
                if (containsGenre(track.getGenreListProperty(),genre)) {
                    track.delGenre(genre);
                }
            }
        }
    }

    public void editGenre(int id, Genre genre) throws AlreadyExistsException {
        if (this.containsGenre(this.genreList, genre)) {
            throw new AlreadyExistsException("Track already exists!");
        } else {
            this.getGenreById(id).setGenreName(genre.getGenreName());
        }
    }

    public void delGenre(String genreS) {
        Genre genre = new Genre(genreS);
        if (containsGenre(this.GenreList(),genre)) {
           this.genreList.remove(genre);
            for (Track track : this.trackList) {
                if (containsGenre(track.getGenreListProperty(),genre)) {
                    track.delGenre(genre);
                    
                    
                }
            }
        }
    }

    public void addGenreToTrack(int trackIndex, int genreIndex) {
        try {
            if (containsGenre(FXCollections.observableList(TrackList().get(trackIndex).getGenreList()), GenreList().get(genreIndex))) {
                throw new AlreadyExistsException("Track alreadi has this genre!");
            } else {
                this.trackList.get(trackIndex).addGenre(GenreList().get(genreIndex));
            }
        } catch (AlreadyExistsException e) {
            System.err.println(e.getMessage());
        }
    }

    public void sortBySongName() {
        Collections.sort(TrackList(), new Track.TracksBySongNameComparator());
    }

    public void sortByArtist() {
        Collections.sort(TrackList(), new Track.TracksByArtistComparator());
    }

    public void sortByLength() {
        Collections.sort(TrackList(), new Track.TracksByLengthComparator());
    }

    public void sortByAlbum() {
        Collections.sort(TrackList(), new Track.TracksByAlbumComparator());
    }

    public Genre getGenreById(int id) {
        for (Genre genre : this.genreList) {
            if (genre.getId() == id) {
                return genre;
            }
        }
        return null;
    }

    public Track getTrackById(int id) {
        for (Track track : this.trackList) {
            if (track.getId() == id) {
                return track;
            }
        }
        return null;
    }

    public boolean containsTrack(Track track) {
        if (this.trackList.isEmpty()) {
            return false;
        }
        boolean b = true;
        for (Track tr : this.trackList) {
            b = true;
            if (!tr.getSongName().equals(track.getSongName())) {
                b = false;
            }
            if (!tr.getAlbum().equals(track.getAlbum())) {
                b = false;
            }
            if (!tr.getArtist().equals(track.getArtist())) {
                b = false;
            }
            if (!tr.getLength().equals(track.getLength())) {
                b = false;
            }
            if (b) {
                return true;
            }
        }
        return b;
    }

    public boolean containsGenre(ObservableList<Genre> genreList, Genre genre) {
        if (genreList.isEmpty()) {
            return false;
        }
        boolean b = true;
        for (Genre gen : genreList) {
            b = true;
            if (!gen.getGenreName().equals(genre.getGenreName())) {
                b = false;
            }
            if (b) {
                return true;
            }
        }
        return b;
    }

}
