package com.netcracker.education.model;

import com.netcracker.education.controller.AlreadyExistsException;
import java.io.Serializable;
import java.time.Duration;
import static java.time.Duration.*;
import java.util.ArrayList;
import static java.util.Collections.list;
import java.util.Comparator;
import java.util.List;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Track implements Serializable ,
        Cloneable{

    private final static String DEFAULT_ARTIST = "default";
    private final static int DEFAULT_ID = -1;
    private final static String DEFAULT_SONG_NAME = "default";
    private final static String DEFAULT_ALBUM = "default";
    private final static List<Genre> DEAFAULT_GENRE_LIST = new ArrayList();
    private final static Duration DEFAULT_LENGTH = ZERO;

    public static void TracksByLengthComparator() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    private IntegerProperty id;
    private StringProperty songName;
    private StringProperty artist;
    private StringProperty album;
    private ObjectProperty<Duration> length;
    private ObservableList<Genre> genreList;

    public Track() {
        this.id = new SimpleIntegerProperty(DEFAULT_ID);
        this.album = new SimpleStringProperty(DEFAULT_ALBUM);
        this.artist = new SimpleStringProperty(DEFAULT_ARTIST);
        this.length = new SimpleObjectProperty<Duration>(DEFAULT_LENGTH);
        ObservableList<Genre> observableList = FXCollections.observableList(new ArrayList());
        this.genreList = observableList;
    }

    ;
    public Track(String songName, String artist, String album, Duration length, List genreList) {
        if (!Track.validateString(songName)) {
            throw new IllegalArgumentException("Incorrect Songname");
        }
        if (!Track.validateString(artist)) {
            throw new IllegalArgumentException("Incorrect ArtistName");
        }
        if (!Track.validateString(album)) {
            throw new IllegalArgumentException("Incorrect Album");
        }
        this.album = new SimpleStringProperty(album);
        this.artist = new SimpleStringProperty(artist);
        this.songName = new SimpleStringProperty(songName);
        this.length = new SimpleObjectProperty<Duration>(length);
        ObservableList<Genre> observableList = FXCollections.observableList(genreList);
        this.genreList = observableList;
        this.id = new SimpleIntegerProperty(DEFAULT_ID);
    }

    public Track(String songName, String artist, String album, Duration length) {

        if (!Track.validateString(songName)) {
            throw new IllegalArgumentException("Incorrect Songname");
        }
        if (!Track.validateString(artist)) {
            throw new IllegalArgumentException("Incorrect ArtistName");
        }
        if (!Track.validateString(album)) {
            throw new IllegalArgumentException("Incorrect Album");
        }
        this.album = new SimpleStringProperty(album);
        this.artist = new SimpleStringProperty(artist);
        this.songName = new SimpleStringProperty(songName);
        this.length = new SimpleObjectProperty<Duration>(length);
        ObservableList<Genre> observableList = FXCollections.observableList(new ArrayList());
        this.genreList = observableList;
        this.id = new SimpleIntegerProperty(DEFAULT_ID);
    }

    public Track(int id, String songName, String artist, String album, Duration length) {

        if (!Track.validateString(songName)) {
            throw new IllegalArgumentException("Incorrect Songname");
        }
        if (!Track.validateString(artist)) {
            throw new IllegalArgumentException("Incorrect ArtistName");
        }
        if (!Track.validateString(album)) {
            throw new IllegalArgumentException("Incorrect Album");
        }
        this.album = new SimpleStringProperty(album);
        this.artist = new SimpleStringProperty(artist);
        this.songName = new SimpleStringProperty(songName);
        this.length = new SimpleObjectProperty<Duration>(length);
        ObservableList<Genre> observableList = FXCollections.observableList(new ArrayList());
        this.genreList = observableList;
        this.id = new SimpleIntegerProperty(id);
    }

    public Track(int id, String songName, String artist, String album, Duration length, List genreList) {

        if (!Track.validateString(songName)) {
            throw new IllegalArgumentException("Incorrect Songname");
        }
        if (!Track.validateString(artist)) {
            throw new IllegalArgumentException("Incorrect ArtistName");
        }
        if (!Track.validateString(album)) {
            throw new IllegalArgumentException("Incorrect Album");
        }
        this.album = new SimpleStringProperty(album);
        this.artist = new SimpleStringProperty(artist);
        this.songName = new SimpleStringProperty(songName);
        this.length = new SimpleObjectProperty<Duration>(length);
        ObservableList<Genre> observableList = FXCollections.observableList(genreList);
        this.genreList = observableList;
        this.id = new SimpleIntegerProperty(id);
    }

    public int getId() {
        return this.id.get();
    }

    public IntegerProperty getIdProperty() {
        return this.id;
    }

    public String getSongName() {
        return this.songName.get();
    }

    public StringProperty getSongNameProperty() {
        return this.songName;
    }

    public String getArtist() {
        return this.artist.get();
    }

    public StringProperty getArtistProperty() {
        return this.artist;
    }

    public String getAlbum() {
        return this.album.get();
    }

    public StringProperty getAlbumProperty() {
        return this.album;
    }

    public ObjectProperty<Duration> getLengthProperty() {
        return this.length;
    }

    public Duration getLength() {
        return this.length.get();
    }

    public List<Genre> getGenreList() {
        return this.genreList;
    }

    public ObservableList<Genre> getGenreListProperty() {
        return this.genreList;
    }

    public StringProperty getGenreListStringProperty() {
        String s = "";
        for (Genre g : this.genreList) {
            s += g.getGenreName();
            s += "\n";
        }
        StringProperty s2 = new SimpleStringProperty(s);
        return s2;
    }

    public StringProperty getLengthStringProperty() {
        String s = "";
        Duration temp = Duration.parse("PT0M");
        temp = this.length.getValue();
        long d, h, m, sec, ms;
        h = temp.toHours();
        temp = temp.minusHours(h);
        m = temp.toMinutes();
        temp = temp.minusMinutes(m);
        sec = temp.getSeconds();
        temp = temp.minusSeconds(sec);
        s = String.format("%02d:%02d:%02d", h, m, sec);
        StringProperty s2 = new SimpleStringProperty(s);
        return s2;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public void setSongName(String songName) {
        if (!Track.validateString(songName)) {
            throw new IllegalArgumentException("Incorrect Songname");
        }
        this.songName.set(songName);
    }

    public void setArtist(String artist) {
        if (!Track.validateString(artist)) {
            throw new IllegalArgumentException("Incorrect ArtistName");
        }
        this.artist.set(artist);
    }

    public void setLength(Duration length) {
        this.length.setValue(length);
    }

    public void setAlbum(String album) {
        if (!Track.validateString(album)) {
            throw new IllegalArgumentException("Incorrect AlbumName");
        }
        this.album.set(album);
    }

    public void setGenreList(List<Genre> genreList) {
        ObservableList<Genre> observableList = FXCollections.observableList(genreList);
        this.genreList = observableList;
    }

    public void addGenre(Genre genre) throws AlreadyExistsException {
        if (containsGenre(this.genreList, genre)) {
            throw new AlreadyExistsException("Track already has this genre!");
        }
        this.genreList.add(genre);
    }

    public void delGenre(Genre genre) {
        for (int i = 0; i < this.genreList.size(); i++) //for(Genre gen:this.genreList)
        {
            Genre gen = this.genreList.get(i);
            if (gen.getGenreName().equals(genre.getGenreName())) {
                this.genreList.remove(i);
            }
        }
    } //написать исключение 

    @Override
    public boolean equals(Object object) {
        if (object == null) {
            return false;
        }
        if (this.getClass() != object.getClass()) {
            return false;
        }
        Track track = (Track) object;
        if (this.getSongName() != track.getSongName()) {
            return false;
        }
        if (this.getArtist() != track.getArtist()) {
            return false;
        }
        if (this.getAlbum() != track.getAlbum()) {
            return false;
        }
        if (this.getGenreList() != track.getGenreList()) {
            return false;
        }
        if (this.getLength() != track.getLength()) {
            return false;
        }
        return true;
    }

    public static class TracksBySongNameComparator implements Comparator<Track> {

        @Override
        public int compare(Track o1, Track o2) {
            return o1.getSongName().compareTo(o2.getSongName());
        }
    }

    public static class TracksByArtistComparator implements Comparator<Track> {

        @Override
        public int compare(Track o1, Track o2) {
            return o1.getArtist().compareTo(o2.getArtist());
        }
    }

    public static class TracksByLengthComparator implements Comparator<Track> {

        @Override
        public int compare(Track o1, Track o2) {
            return o1.getLength().compareTo(o2.getLength());
        }
    }

    public static class TracksByAlbumComparator implements Comparator<Track> {

        @Override
        public int compare(Track o1, Track o2) {
            return o1.getAlbum().compareTo(o2.getAlbum());
        }
    }

    private static boolean validateString(String s) {
        boolean b = true;
        if (s == null) {
            return false;
        }
        if (s.trim().equals("")) {
            return false;
        }
        //if (s.charAt(0)==' ') return false;
        if (s.charAt(0) == '.') {
            return false;
        }
        if (s.charAt(0) == ',') {
            return false;
        }
        return b;
    }

    @Override
    public String toString() {
        String s;
        s = this.getId() + "\n" + this.getSongName() + "\n" + this.getArtist() + "\n" + this.getAlbum() + "\n" + this.getLength() + "\n" + this.genreList.size() + "\n";
        for (Genre genre : this.genreList) {
            s += genre.getId() + "\n" + genre.getGenreName() + "\n";
        }
        return s;
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
    @Override
        protected Object clone() throws CloneNotSupportedException {
            return super.clone();
        }
}
