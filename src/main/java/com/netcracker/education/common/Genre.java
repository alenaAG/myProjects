package com.netcracker.education.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Genre implements Serializable, Cloneable {

    private IntegerProperty id;
    private StringProperty genreName;
    private ObservableList trackList;
    private final static String DEFAULT_NAME = "default";
    private final static Integer DEFAULT_ID = -1;

    public Genre() {
        this.id = new SimpleIntegerProperty(DEFAULT_ID);
        this.genreName = new SimpleStringProperty(DEFAULT_NAME);
    }

    public Genre(int id, String name) {
        if (!Genre.validateString(name)) {
            throw new IllegalArgumentException("Incorect GenreName");
        }
        this.id = new SimpleIntegerProperty(id);
        this.genreName = new SimpleStringProperty(name);
    }

    public Genre(String name) {
        if (!Genre.validateString(name)) {
            throw new IllegalArgumentException("Incorect GenreName");
        }
        this.id = new SimpleIntegerProperty(DEFAULT_ID);
        this.genreName = new SimpleStringProperty(name);
    }

    public int getId() {
        return id.getValue();
    }

    public void setId(int id) {
        this.id.setValue(id);;
    }

    public String getGenreName() {
        return genreName.getValue();
    }

    public StringProperty getGenreNameProperty() {
        return genreName;
    }

    public IntegerProperty getIdProperty() {
        return id;
    }

    public void setGenreName(String genreName) {
        if (!Genre.validateString(genreName)) {
            throw new IllegalArgumentException("Incorect GenreName");
        }
        this.genreName.setValue(genreName);
    }

    private static boolean validateString(String s) {
        boolean b = true;
        if (s.charAt(0) == ' ') {
            return false;
        }
        if (s.charAt(0) == '.') {
            return false;
        }
        if (s.charAt(0) == ',') {
            return false;
        }
        return b;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null) {
            return false;
        }
        if (!(this.getClass() == object.getClass())) {
            return false;
        }
        Genre genre = (Genre) object;
        if (this.getGenreName() == genre.getGenreName()) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        String s;
        s = this.getGenreName();
        return s;
    }

}
