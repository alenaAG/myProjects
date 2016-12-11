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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
            // lengthLabel.setText(DateUtil.format(track.getLength()));
             lengthLabel.setText(track.getLengthStringProperty().getValue());
           //  genreList.setItems(track.getGenreListProperty());
         }
         else
         {
             songNameLabel.setText("");
             artistLabel.setText("");
             albumLabel.setText("");
             lengthLabel.setText("");
             
             
            
         }
     }
    
}
