/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netcracker.education;

import com.netcracker.education.controller.AlreadyExistsException;
import com.netcracker.education.controller.Control;
import com.netcracker.education.model.Genre;
import com.netcracker.education.view.View;
import com.netcracker.education.model.Track;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author 1
 */
public class ViewController {

    @FXML
    private TableView<Track> trackListTable;
    @FXML
    private TableColumn<Track, String> songNameColumn;
    @FXML
    private TableColumn<Track, String> artistColumn;
    @FXML
    private TableColumn<Track, String> albumColumn;
    @FXML
    private TableColumn<Track, String> lengthColumn;
    @FXML
    private TableColumn<Track, String> genreColumn;

    @FXML
    private TextField songNameField;
    @FXML
    private TextField artistField;
    @FXML
    private TextField albumField;
    @FXML
    private TextField lengthField;
    @FXML
    private Button okButton;
    @FXML
    private Button addButton;
    @FXML
    private Button editButton;
    @FXML
    private ListView<Genre> genreListView;
    @FXML
    private Button addGenreButton;
    @FXML
    private Button editGenreButton;
    @FXML
    private Button deleteGenreButton;
    @FXML
    private Button okGenreButton;

    private boolean addIsClicked = false;
    private boolean editIsClicked = false;
    private boolean okIsClicked = false;
    private boolean cancelIsClicked = false;
    private boolean genresSelected = false;
    private Genre selectedGenre;
    private ObservableList<Genre> selectedGenres;
    private boolean okGenreButtonIsClicked = false;
    private boolean addGenreButtonIsClicked;
    private boolean editGenreButtonIsClicked;
    private boolean deleteGenreButtonIsClicked;
    private Control controller;

    @FXML
    private void handleDeleteTrack() {
        int selectedIndex = trackListTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            //view.getTrackList().remove(trackListTable.getItems().get(selectedIndex));
            controller.TrackList().remove(trackListTable.getItems().get(selectedIndex));

        } else {
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(view.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Track Selected");
            alert.setContentText("Please select a track in the table.");
            alert.showAndWait();
        }
    }

    @FXML
    private void handleAddTrack() {
        addIsClicked = true;
        songNameField.setText("");
        artistField.setText("");
        albumField.setText("");
        lengthField.setText("");
        songNameField.setEditable(true);
        artistField.setEditable(true);
        albumField.setEditable(true);
        lengthField.setEditable(true);
        okButton.setVisible(true);
    }

    @FXML
    private void handleOkButton() {

        if (validateInput()) {
            Track track = new Track(songNameField.getText(), artistField.getText(), albumField.getText(), parseLength(lengthField.getText()));
            if (isAddClicked()) {
                view.getTrackList().add(track);
            }
            if (isEditClicked()) {
                int selectedIndex = trackListTable.getSelectionModel().getSelectedIndex();
                view.getTrackList().set(selectedIndex, track);
            }
        }
        songNameField.setEditable(false);
        artistField.setEditable(false);
        albumField.setEditable(false);
        lengthField.setEditable(false);
        okButton.setVisible(false);
        editIsClicked = false;
        addIsClicked = false;
        okIsClicked = false;
    }

    @FXML
    private void handleEditTrack() {
        editIsClicked = true;
        int selectedIndex = trackListTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            songNameField.setEditable(true);
            artistField.setEditable(true);
            albumField.setEditable(true);
            lengthField.setEditable(true);
            okButton.setVisible(true);
        } else {
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(view.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Track Selected");
            alert.setContentText("Please select a track in the table.");
            alert.showAndWait();
        }
    }

    private void addGenreToTrack(int trackId, int genreId) {
        String errorMessage = "";
        try {
            view.Controller().getTrackById(trackId).addGenre(view.Controller().getGenreById(genreId));
        } catch (AlreadyExistsException e) {
            errorMessage += "TrackAlready has this genre!\n";
        }
        if (errorMessage.length() != 0) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setHeaderText("Please choose another genre");
            alert.setContentText(errorMessage);
            alert.showAndWait();
        }
    }
    
    private void addGenre(Genre genre)
    {}

    private void delGenreFromTrack(int trackId, int genreId) {
        view.Controller().getTrackById(trackId).delGenre(view.Controller().getGenreById(genreId));
    }

    @FXML
    private void handleAddGenreButton() {
        addGenreButtonIsClicked = true;
        int selectedIndex = trackListTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            okGenreButton.setVisible(true);
            genreListView.setItems(view.getGenreList());
        }
    }

    @FXML
    private void handleEditGenreButton() {
        editGenreButtonIsClicked = true;

    }
    @FXML 
    private void handleNewButton()
    {
            Genre tempGenre = new Genre();
            boolean okClicked = view.showGenreEditDialog(tempGenre);
            if (okClicked) {
                try{
                view.Controller().addGenre(tempGenre.getGenreName());
                }
                catch(AlreadyExistsException e){
                    Alert alert = new Alert(AlertType.WARNING);
                alert.initOwner(view.getPrimaryStage());
                alert.setTitle("ERROR");
                alert.setHeaderText("This genre already exists");
                alert.setContentText("Please enter another genre.");
                alert.showAndWait();
                }
                
            }
            genreListView.setItems(view.getGenreList());
    }

    @FXML
    private void handleDeleteGenreButton() {
        deleteGenreButtonIsClicked = true;
        okGenreButton.setVisible(true);
    }

    @FXML
    private void handleOkGenreButton() {
        if (addGenreButtonIsClicked) {
            int selectedIndex = trackListTable.getSelectionModel().getSelectedIndex();
            int trackId = -7;
            trackId = trackListTable.getSelectionModel().getSelectedItem().getId();
            int genreId;
            if (trackId != -7) {
                genreId = genreListView.getSelectionModel().getSelectedItem().getId();
                addGenreToTrack(trackId, genreId);
            } else {
                Alert alert = new Alert(AlertType.WARNING);
                alert.initOwner(view.getPrimaryStage());
                alert.setTitle("No Selection");
                alert.setHeaderText("No Track Selected");
                alert.setContentText("Please select a track in the table.");
                alert.showAndWait();
            }
            addGenreButtonIsClicked = false;
        } else {
            if (deleteGenreButtonIsClicked) {
                int trackId = trackListTable.getSelectionModel().getSelectedItem().getId();
                int genreId;
                if (trackId >= 0) {
                    genreId = genreListView.getSelectionModel().getSelectedItem().getId();
                    if (genreId >= 0) {
                        delGenreFromTrack(trackId, genreId);
                    }
                } else {
                    Alert alert = new Alert(AlertType.WARNING);
                    alert.initOwner(view.getPrimaryStage());
                    alert.setTitle("No Selection");
                    alert.setHeaderText("No Track Selected");
                    alert.setContentText("Please select a track in the table.");
                    alert.showAndWait();
                }
                deleteGenreButtonIsClicked = false;
            } else {
                if (editGenreButtonIsClicked) {
                    
                }
            }
        }
        okGenreButtonIsClicked = false;
        okGenreButton.setVisible(false);
        genreListView.setItems(trackListTable.getSelectionModel().getSelectedItem().getGenreListProperty());

    }
    private View view;

    public ViewController() {
    }

    @FXML
    public void initialize() {
        songNameColumn.setCellValueFactory(cellData -> cellData.getValue().getSongNameProperty());
        artistColumn.setCellValueFactory(cellData -> cellData.getValue().getArtistProperty());
        albumColumn.setCellValueFactory(cellData -> cellData.getValue().getAlbumProperty());
        lengthColumn.setCellValueFactory(cellData -> cellData.getValue().getLengthStringProperty());
        showTrackDetails(null);
        trackListTable.getSelectionModel().selectedItemProperty().addListener((observale, oldValue, newValue) -> showTrackDetails(newValue));

    }

    public void setView(View view) {
        this.view = view;
       // controller= new Control(view.getTrackList(),view.getGenreList());
        trackListTable.setItems(view.getTrackList());
        genreListView.setItems(view.getGenreList());
    }

    private static boolean validateString(String s) {
        boolean b = true;
        if (s == null) {
            return false;
        }
        if (s.trim().equals("")) {
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

    private boolean validateInput() {
        String errorMessage = "";

        if (!validateString(songNameField.getText())) {
            errorMessage += "No valid song name!\n";
        }
        if (!validateString(artistField.getText())) {
            errorMessage += "No valid artist name!\n";
        }
        if (!validateString(albumField.getText())) {
            errorMessage += "No album name!\n";
        }

        if (lengthField.getText() == null || lengthField.getText().length() == 0) {
            errorMessage += "No valid Length code!\n";
        } else {
            try {
                parseLength(lengthField.getText());
            } catch (Exception e) {
                errorMessage += "No valid Length code!\n";
            }
        }
        if (errorMessage.length() == 0) {
            return true;
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);
            alert.showAndWait();
            return false;
        }

    }

    private void showTrackDetails(Track track) {
        if (track != null) {
            songNameField.setText(track.getSongName());
            artistField.setText(track.getArtist());
            albumField.setText(track.getAlbum());
            lengthField.setText(track.getLengthStringProperty().getValue());
            genreListView.setItems(track.getGenreListProperty());
        } else {
            songNameField.setText("");
            artistField.setText("");
            albumField.setText("");
            lengthField.setText("");
        }
    }

    private Duration parseLength(String s) {
        Duration duration = null;
        try {
            String[] hmnArray = s.split(":", 3);
            duration = Duration.ofHours(Integer.parseInt(hmnArray[0]));
            duration = duration.plus(Duration.ofMinutes(Integer.parseInt(hmnArray[1])));
            duration = duration.plus(Duration.ofSeconds(Integer.parseInt(hmnArray[2])));
        } catch (Exception e) {
            System.err.println("Введен неверный формат");
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(view.getPrimaryStage());
            alert.setTitle("Invalid Format");
            alert.setHeaderText("Format Error");
            alert.setContentText("Please enter the length format hh:mm:ss.");
            alert.showAndWait();
        }
        return duration;
    }

    public boolean isOkClicked() {
        return okIsClicked;
    }

    public boolean isAddClicked() {
        return addIsClicked;
    }

    public boolean isEditClicked() {
        return editIsClicked;
    }

    public boolean isCancelClicked() {
        return cancelIsClicked;
    }
}
