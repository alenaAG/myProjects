/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netcracker.education;

import com.netcracker.education.model.Genre;
import com.netcracker.education.view.View;
import com.netcracker.education.model.Track;
import java.net.URL;
import java.time.Duration;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
   private TableColumn<Track,String> genreColumn;
   
   @FXML
   private Label songNameLabel;
   @FXML
   private Label artistLabel;
   @FXML
   private Label albumLabel;
   @FXML
   private Label lengthLabel;
   @FXML
   private ListView<Genre> genreList;
   @FXML
    private void handleDeleteTrack() {
        int selectedIndex = trackListTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex>=0)
        {
        trackListTable.getItems().remove(selectedIndex);
        }
        else
        {
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(view.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Person Selected");
            alert.setContentText("Please select a track in the table.");
            alert.showAndWait();
        }
    }
    
    
    private View view;
    public ViewController()
    {}
    @FXML
    public void initialize() {
        songNameColumn.setCellValueFactory(cellData -> cellData.getValue().getSongNameProperty());
        artistColumn.setCellValueFactory(cellData -> cellData.getValue().getArtistProperty());
        albumColumn.setCellValueFactory(cellData -> cellData.getValue().getAlbumProperty());
       lengthColumn.setCellValueFactory(cellData -> cellData.getValue().getLengthStringProperty());
       genreColumn.setCellValueFactory(cellData -> cellData.getValue().getGenreListStringProperty());
       showTrackDetails(null);
       trackListTable.getSelectionModel().selectedItemProperty().addListener((observale, oldValue,newValue)->showTrackDetails(newValue));
    }
     
     public void setView(View view) {
        this.view = view; 
        trackListTable.setItems(view.getTrackList());
    }
     private void showTrackDetails(Track track)
     {
         if (track!=null)
         {
             songNameLabel.setText(track.getSongName());
             artistLabel.setText(track.getArtist());
             albumLabel.setText(track.getAlbum());
             lengthLabel.setText(track.getLengthStringProperty().getValue());
         }
         else
         {
             songNameLabel.setText("");
             artistLabel.setText("");
             albumLabel.setText("");
             lengthLabel.setText("");
         }
     }
     private Duration parseLength(StringProperty s)
     {
         Duration duration=null;
         try{
             String[] hmnArray= s.getValue().split(":",3);
             duration=Duration.ofHours(Integer.parseInt(hmnArray[0]));
             duration=duration.plus(Duration.ofMinutes(Integer.parseInt(hmnArray[1])));
             duration=duration.plus(Duration.ofSeconds(Integer.parseInt(hmnArray[2])));
         }
         catch(Exception e){
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
    
}
