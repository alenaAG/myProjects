/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alenag.client;

import com.alenag.common.AlreadyExistsException;
import com.alenag.common.Control;
import com.alenag.common.Genre;
import com.alenag.common.Track;
import java.util.Optional;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListCell;
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
                view.update();
                view.updateG();
            } catch (AlreadyExistsException e) {
                view.update();
                view.updateG();
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

    @FXML
    private void handleUpdateButton() {
        SerialClient.setMessage(9, null, okClicked);
        view.updateG();
        view.update();
        if (!(this.track == null)) {
            this.track=this.controller.getTrackById(track.getId());
            this.genresOfTrack=track.getGenreListProperty();
            this.setGenresLibrary();
        } else {
            this.setGenresEditLibrary();
        }
    }

    public void setGenresLibrary() {
        view.update();
        view.updateG();
        this.genresLibrary = this.controller.GenreList();
        this.genreLibraryView.setItems(this.genresLibrary);

        this.genreLibraryView.setCellFactory(CheckBoxListCell.forListView(new Callback<Genre, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(Genre item) {

                BooleanProperty observable = new SimpleBooleanProperty(containsGenre(genresOfTrack, item));
                observable.addListener((obs, wasSelected, isNowSelected)  -> {
                            if (!isNowSelected) {
                                SerialClient.setMessage(7, null, item.getId(), track.getId());
                                if (SerialClient.getMessage().getException() == null) {

                                } else {
                                    view.update();
                                    view.updateG();
                                    Alert alert = new Alert(Alert.AlertType.WARNING);
                                    alert.initOwner(view.getPrimaryStage());
                                    alert.setTitle("ERROR");
                                    alert.setContentText("This genre doesn't exist anymore");
                                    alert.showAndWait();
                                }
                                System.out.println(genresOfTrack);
                            } else {
                                if (isNowSelected) {
                                    SerialClient.setMessage(8, null, item.getId(), track.getId(), item.getGenreName());
                                    if (SerialClient.getMessage().getException() == null) {
                                    }
                                    System.out.println(genresOfTrack);
                                } else {
                                    view.update();
                                    view.updateG();
                                    Alert alert = new Alert(Alert.AlertType.WARNING);
                                    alert.initOwner(view.getPrimaryStage());
                                    alert.setTitle("ERROR");
                                    alert.setContentText("This genre doesn't exist anymore");
                                    alert.showAndWait();
                                }
                            }

                            //System.out.println("Check box for " + item + " changed from " + wasSelected + " to " + isNowSelected);
                        }
                );
                return observable;
            }
        }));
        view.update();
        view.updateG();
    }

    public void setGenresEditLibrary() {
        view.update();
        view.updateG();
        this.genresLibrary = this.controller.GenreList();
        this.genreLibraryView.setItems(this.controller.GenreList());
        this.deleteGenreButton.setVisible(true);
        this.editGenreButton.setVisible(true);
        this.genreLibraryView.setCellFactory(new Callback<ListView<Genre>, ListCell<Genre>>() {

            @Override
            public ListCell<Genre> call(ListView<Genre> param) {
                ListCell<Genre> cell = new ListCell<Genre>() {

                    @Override
                    protected void updateItem(Genre item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            setText(item.getGenreName());
                        } else {
                            setText("");
                        }
                    }
                };
                return cell;
            }
        });
        view.update();
        view.updateG();

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
                SerialClient.setMessage(5, null, genreId, this.controller.getGenreById(genreId).getGenreName());
                if (SerialClient.getMessage().getException() == null) {
                    //view.update();
                    this.setGenresEditLibrary();
                } else {
                    view.update();
                    view.updateG();
                    this.setGenresEditLibrary();
                    alert = new Alert(Alert.AlertType.WARNING);
                    alert.initOwner(view.getPrimaryStage());
                    alert.setTitle("ERROR");
                    alert.setContentText("This genre doesn't exist anymore");
                    alert.showAndWait();
                }

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
                    SerialClient.setMessage(6, null, this.genreLibraryView.getSelectionModel().getSelectedItem().getId(), tempGenre.getGenreName());
                    if (SerialClient.getMessage().getException() == null) {
                        this.setGenresEditLibrary();
                    } else {
                        if (SerialClient.getMessage().getException() instanceof AlreadyExistsException) {

                            view.update();
                            view.updateG();
                            this.setGenresEditLibrary();
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.initOwner(view.getPrimaryStage());
                            alert.setContentText("Genre Already Exists");
                            alert.showAndWait();
                            view.update();
                        } else {

                            view.update();
                            view.updateG();
                            this.setGenresEditLibrary();
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.initOwner(view.getPrimaryStage());
                            alert.setContentText("Server doesn't have this genre anymore");
                            alert.showAndWait();
                            view.update();
                        }
                    }
                    //view.update();
                    //this.setGenresEditLibrary();
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
            SerialClient.setMessage(4, null, tempGenre.getGenreName());
            if (SerialClient.getMessage().getException() == null) {
                if (!(track == null)) {
                    this.setGenresLibrary();
                } else {
                    this.setGenresEditLibrary();
                }
            } else {
                view.updateG();
                view.update();
                if (!(track == null)) {
                    this.setGenresLibrary();
                } else {
                    this.setGenresEditLibrary();
                }
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.initOwner(view.getPrimaryStage());
                alert.setTitle("ERROR");
                alert.setHeaderText("This genre already exists");
                alert.setContentText("Please enter another genre.");
                alert.showAndWait();
            }

        }
        view.update();
        view.updateG();

    }

    public void setGenresOfTrack(Track track) {

        this.deleteGenreButton.setVisible(false);
        this.editGenreButton.setVisible(false);
        this.genresOfTrack = (ObservableList<Genre>) track.getGenreList();
        this.track = track;
        this.trackBefore = new Track(track.getId(), track.getSongName(), track.getArtist(), track.getAlbum(), track.getLength(), track.getGenreList());
        view.update();
        view.updateG();

    }

}
