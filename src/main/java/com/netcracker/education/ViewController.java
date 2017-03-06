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
import com.netcracker.education.net.SerialClient;
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
    private Button deleteButton;

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
    private Button cancelButton;
    @FXML
    private TitledPane genresTitledPane;
    @FXML
    private TextField searchField;

    private ObservableList<Track> res = FXCollections.observableArrayList(new ArrayList<Track>());
    private boolean addIsClicked = false;
    private boolean editIsClicked = false;
    private boolean okIsClicked = false;
    private Control controller;
    private int count = 0;

    @FXML
    private void handleCancelButton() {
        searchField.setEditable(true);
        songNameField.setEditable(false);
        artistField.setEditable(false);
        albumField.setEditable(false);
        lengthField.setEditable(false);
        okButton.setVisible(false);
        editIsClicked = false;
        addIsClicked = false;
        okIsClicked = false;
        cancelButton.setVisible(false);
        this.genresTitledPane.setVisible(true);
        deleteButton.setDisable(false);
        editButton.setDisable(false);
        addButton.setDisable(false);
        if (this.getTrackLib().isEmpty()) {
            this.genreListView.getItems().clear();
        } else {
            this.showTrackDetails(this.controller.getTrackById(this.trackListTable.getSelectionModel().getSelectedItem().getId()));
        }
    }

    @FXML
    private void handleDeleteTrack() {
        if (this.getTrackLib().isEmpty()) {
            this.genreListView.getItems().clear();
        } else {

            int selectedIndex = trackListTable.getSelectionModel().getSelectedIndex();
            if (selectedIndex >= 0) {
                SerialClient.setMessage(1, null, trackListTable.getItems().get(selectedIndex).getId());
                if (SerialClient.getMessage().getException() == null) {
                    view.update();
                    this.genreListView.getItems().clear();
                } else {
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.initOwner(view.getPrimaryStage());
                    alert.setTitle("Server error");
                    alert.showAndWait();
                }
            } else {
                Alert alert = new Alert(AlertType.WARNING);
                alert.initOwner(view.getPrimaryStage());
                alert.setTitle("No Selection");
                alert.setHeaderText("No Track Selected");
                alert.setContentText("Please select a track in the table.");
                alert.showAndWait();
            }
        }
    }

    @FXML
    private void handleAddTrack() {
        deleteButton.setDisable(true);
        editButton.setDisable(true);
        addButton.setDisable(true);
        cancelButton.setVisible(true);
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
        this.genresTitledPane.setVisible(false);
        searchField.setEditable(false);
    }

    @FXML
    private void handleOkButton() {
        String errorMessage = "";
        boolean b = validateInput();

        if (b) {
            if (isAddClicked()) {
                int genreSelectedIndex = -1;
                genreSelectedIndex = genreListView.getSelectionModel().getSelectedIndex();
                SerialClient.setMessage(2, null, songNameField.getText(), artistField.getText(), albumField.getText(), parseLength(lengthField.getText()));
                if (SerialClient.getMessage().getException() == null) {
                    view.update();
                    this.genreListView.setItems(FXCollections.observableArrayList(new ArrayList<Genre>()));

                } else {

                    Alert alert = new Alert(AlertType.ERROR);
                    alert.initOwner(view.getPrimaryStage());
                    alert.setContentText("Track Already Exists");
                    alert.showAndWait();
                }

            } else {
                if (isEditClicked()) {
                    int id = trackListTable.getSelectionModel().getSelectedItem().getId();
                    Track track = trackListTable.getSelectionModel().getSelectedItem();
                    if ((track.getSongName().equals(songNameField.getText())) && (track.getArtist().equals(artistField.getText())) && (track.getAlbum().equals(albumField.getText())) && (track.getLength().equals(parseLength(lengthField.getText())))) {
                        errorMessage += "You cannot add the same track\n";
                    } else {
                        SerialClient.setMessage(3, null, id, songNameField.getText(), artistField.getText(), albumField.getText(), parseLength(lengthField.getText()));
                        if (SerialClient.getMessage().getException() == null) {
                            view.update();
                            showTrackDetails(this.controller.getTrackById(id));
                        } else {
                            Alert alert = new Alert(AlertType.ERROR);
                            alert.initOwner(view.getPrimaryStage());
                            alert.setContentText("Track Already Exists");
                            alert.showAndWait();
                        }
                    }

                }
            }
            deleteButton.setDisable(false);
            editButton.setDisable(false);
            addButton.setDisable(false);
            songNameField.setEditable(false);
            artistField.setEditable(false);
            albumField.setEditable(false);
            lengthField.setEditable(false);
            okButton.setVisible(false);
            editIsClicked = false;
            addIsClicked = false;
            okIsClicked = false;
            cancelButton.setVisible(false);
            searchField.setEditable(true);

            this.genresTitledPane.setVisible(true);
            if (errorMessage.length() != 0) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("ERROR");
                alert.setContentText(errorMessage);
                alert.showAndWait();
            } else {
                trackListTable.refresh();
            }
        }

    }

    @FXML
    private void handleEditTrack() {
        if (this.getTrackLib().isEmpty()) {
        } else {

            addButton.setDisable(true);
            deleteButton.setDisable(true);
            editButton.setDisable(true);
            editIsClicked = true;
            searchField.setEditable(false);

            this.genresTitledPane.setVisible(false);

            int selectedIndex = trackListTable.getSelectionModel().getSelectedIndex();
            if (selectedIndex >= 0) {
                songNameField.setEditable(true);
                artistField.setEditable(true);
                albumField.setEditable(true);
                lengthField.setEditable(true);
                okButton.setVisible(true);
                cancelButton.setVisible(true);
                searchField.setEditable(false);
            } else {
                Alert alert = new Alert(AlertType.WARNING);
                alert.initOwner(view.getPrimaryStage());
                alert.setTitle("No Selection");
                alert.setHeaderText("No Track Selected");
                alert.setContentText("Please select a track in the table.");
                alert.showAndWait();
                addButton.setDisable(false);
                deleteButton.setDisable(false);
                editButton.setDisable(false);
            }
        }
    }

    @FXML
    private void handleAddGenreButton() {
        if (this.getTrackLib().isEmpty()) {
        } else {
            int selectedIndex = trackListTable.getSelectionModel().getSelectedIndex();
            if (selectedIndex >= 0) {
                int selectedId = trackListTable.getSelectionModel().getSelectedItem().getId();
                ObservableList<Genre> prevGenreList = controller.getTrackById(selectedId).getGenreListProperty();
                boolean okClicked = view.showGenresDialog(controller.getTrackById(selectedId));
                if (okClicked) {
                    view.update();
                    //this.trackListTable.setItems(this.controller.TrackList());
                    showTrackDetails(this.controller.getTrackById(selectedId));
                    this.genreListView.setItems(this.controller.getTrackById(selectedId).getGenreListProperty());
                } else {
                }

            } else {
                Alert alert = new Alert(AlertType.WARNING);
                alert.initOwner(view.getPrimaryStage());
                alert.setTitle("No Selection");
                alert.setHeaderText("No Track Selected");
                alert.setContentText("Please select a track in the table.");
                alert.showAndWait();
            }
        }
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

        trackListTable.getSelectionModel().selectedItemProperty().addListener((observale, oldValue, newValue) -> showTrackDetails(newValue));
        searchField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                res = search(newValue);
                count++;
                if ((newValue.length() == 0) || (newValue.equals("*"))) {
                    res = controller.TrackList();
                    trackListTable.setItems(res);
                    showTrackDetails(null);
                } else {
                    if (res.size() == 0) {
                        trackListTable.setItems(FXCollections.observableArrayList(new ArrayList<Track>()));
                        showTrackDetails(null);
                    } else {
                        trackListTable.setItems(res);
                       showTrackDetails(null);
                    }
                }

            }
        });

    }

    public ObservableList<Track> search(String value) {
        ObservableList<Track> res = FXCollections.observableArrayList(new ArrayList<Track>());
        for (Track track : this.controller.TrackList()) {
            if ((track.getSongName().contains(value)) || (track.getArtist().contains(value)) || (track.getAlbum().contains(value))) {
                res.add(track);
            }
        }
        return res;
    }

    public void setView(View view) {

        this.view = view;
        this.controller = view.Controller();
        if (count == 0) {
            this.trackListTable.setItems(this.controller.TrackList());
            
        } else {
            
            for(Track track :this.res)
            {
                Track track2=this.controller.getTrackById(track.getId());
                track.setSongName(track2.getSongName());
                track.setAlbum(track2.getArtist());
                track.setAlbum(track2.getAlbum());
                track.setLength(track2.getLength());
                track.setGenreList(track2.getGenreList());
            }
            this.trackListTable.setItems(this.res);
        }
    }

    public ObservableList<Track> getTrackLib() {
        return this.controller.TrackList();
    }

    public ObservableList<Genre> getGenreLib() {
        return this.controller.GenreList();
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

        if (lengthField.getText() == null || lengthField.getText().length() == 0 || parseLength(lengthField.getText()) == null) {
            errorMessage += "No valid Length code!\n";
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
            view.update();
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
            genreListView.setItems(FXCollections.observableArrayList(new ArrayList<Genre>()));

        }
    }

    private Duration parseLength(String s) {
        Duration duration = null;
        try {

            String[] hmnArray = s.split(":", 3);
            if ((Integer.parseInt(hmnArray[0]) < 0) || (Integer.parseInt(hmnArray[1]) < 0) || (Integer.parseInt(hmnArray[2]) < 0)) {
            } else {
                duration = Duration.ofHours(Integer.parseInt(hmnArray[0]));
                duration = duration.plus(Duration.ofMinutes(Integer.parseInt(hmnArray[1])));
                duration = duration.plus(Duration.ofSeconds(Integer.parseInt(hmnArray[2])));
            }
        } catch (Exception e) {
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

}
