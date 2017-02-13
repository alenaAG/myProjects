/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netcracker.education;

import com.netcracker.education.controller.AlreadyExistsException;
import com.netcracker.education.controller.Control;
import com.netcracker.education.model.Genre;
import com.netcracker.education.model.Track;
import com.netcracker.education.view.View;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 *
 * @author 1
 */
public class GenresController {

    private Stage dialogStage;

    @FXML
    private ListView<Genre> genreLibraryView;
    @FXML
    private Button editGenreButton;
    @FXML
    private Button deleteGenreButton;
    @FXML
    private Button newGenreButton;

    private boolean okClicked = false;
    private Track trackBefore;
    private Track track;
    private Control controller;
    private ObservableList<Genre> genresLibrary;
    private ObservableList<Genre> genresOfTrack;

    public GenresController() {
    }

    public Control getController() {
        return this.controller;
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    private View view;

    public void setViev(View view) {
        this.view = view;
        this.controller = view.Controller();
    }

    @FXML
    private void handleNewButton() {
        Genre tempGenre = new Genre();
        
        newGenreButton.setDisable(true);
        deleteGenreButton.setDisable(true);
        editGenreButton.setDisable(true);
        boolean okClicked = view.showGenreEditDialog(tempGenre);
        if (okClicked) {
            try {
                this.controller.addGenre(tempGenre.getGenreName());
            } catch (AlreadyExistsException e) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.initOwner(view.getPrimaryStage());
                alert.setTitle("ERROR");
                alert.setHeaderText("This genre already exists");
                alert.setContentText("Please enter another genre.");
                alert.showAndWait();
            }

        }
        deleteGenreButton.setDisable(false);
        editGenreButton.setDisable(false);
        newGenreButton.setDisable(false);

    }

    @FXML
    public void initialize() {

    }

    public void setGenresLibrary() {
        this.genresLibrary = this.controller.GenreList();
        this.genreLibraryView.setItems(this.genresLibrary);

        this.genreLibraryView.setCellFactory(CheckBoxListCell.forListView(new Callback<Genre, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(Genre item) {

                BooleanProperty observable = new SimpleBooleanProperty(containsGenre(genresOfTrack, item));
                observable.addListener((obs, wasSelected, isNowSelected)
                        -> {
                            if (!isNowSelected) {
                                track.delGenre(item);
                                System.out.println(genresOfTrack);
                            } else {
                                if (isNowSelected) {
                                    try {
                                        track.addGenre(item);
                                    } catch (AlreadyExistsException ex) {
                                        Logger.getLogger(GenresController.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                            }

                            System.out.println("Check box for " + item + " changed from " + wasSelected + " to " + isNowSelected);
                        }
                );
                return observable;
            }
        }));
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

    @FXML
    private void handleOkButton() {

        okClicked = true;

        dialogStage.close();

    }

    /* @FXML
     private void handleCancelButton() {
     this.track = this.trackBefore;
     dialogStage.close();
        

     }*/
    @FXML
    private void handleDeleteGenreButton() {
        if (this.genreLibraryView.getSelectionModel().isEmpty()) {
        } else {
            deleteGenreButton.setDisable(true);
            editGenreButton.setDisable(true);
            newGenreButton.setDisable(true);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.initOwner(view.getPrimaryStage());
            alert.setTitle("ATTENTION");
            alert.setContentText("You are deleting this genre from ALL tracks");
            Optional<ButtonType> result = alert.showAndWait();
            if ((result.isPresent()) && (result.get() == ButtonType.OK)) {

                int genreId = this.genreLibraryView.getSelectionModel().getSelectedItem().getId();
                this.controller.delGenre(this.controller.getGenreById(genreId).getGenreName());
                this.setGenresLibrary();

            }
            deleteGenreButton.setDisable(false);
            editGenreButton.setDisable(false);
            newGenreButton.setDisable(false);

        }

    }

    @FXML
    private void handleEditGenreButton() {
        int selectedId = -1;
        if (this.genreLibraryView.getSelectionModel().getSelectedIndex() >= 0) {
            deleteGenreButton.setDisable(true);
            editGenreButton.setDisable(true);
            newGenreButton.setDisable(true);
            selectedId = this.genreLibraryView.getSelectionModel().getSelectedItem().getId();
            if (selectedId >= 0) {
                Genre tempGenre = new Genre();
                tempGenre.setGenreName(this.genreLibraryView.getSelectionModel().getSelectedItem().getGenreName());
                boolean okClicked = view.showGenreEditDialog(tempGenre);
                if (okClicked) {
                    try {
                        this.controller.editGenre(this.genreLibraryView.getSelectionModel().getSelectedItem().getId(), tempGenre);
                        this.setGenresLibrary();
                    } catch (AlreadyExistsException e) {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.initOwner(view.getPrimaryStage());
                        alert.setTitle("ERROR");
                        alert.setHeaderText("This genre already exists");
                        alert.setContentText("Please enter another genre.");
                        alert.showAndWait();
                    }
                }
            } else {
            }
            deleteGenreButton.setDisable(false);
            editGenreButton.setDisable(false);
            newGenreButton.setDisable(false);
        }
    }

    @FXML
    private void handleNewGenreButton() {
        Genre tempGenre = new Genre();
        boolean okClicked = view.showGenreEditDialog(tempGenre);
        if (okClicked) {
            try {
                this.controller.addGenre(tempGenre.getGenreName());
                this.setGenresLibrary();
            } catch (AlreadyExistsException e) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.initOwner(view.getPrimaryStage());
                alert.setTitle("ERROR");
                alert.setHeaderText("This genre already exists");
                alert.setContentText("Please enter another genre.");
                alert.showAndWait();
            }

        }

    }

    public void setGenresOfTrack(Track track) {
        this.genresOfTrack = (ObservableList<Genre>) track.getGenreList();
        this.track = track;
        this.trackBefore = new Track(track.getId(), track.getSongName(), track.getArtist(), track.getAlbum(), track.getLength(), track.getGenreList());

    }

}
