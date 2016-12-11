/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netcracker.education;

import com.netcracker.education.view.View;
import com.netcracker.education.model.Track;
import java.net.URL;
import java.time.Duration;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
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
   private TableColumn<Track, Duration> lengthColumn;
    
    
    
    private View view;
    public ViewController()
    {}
    @FXML
    public void initialize() {
        songNameColumn.setCellValueFactory(cellData -> cellData.getValue().getSongNameProperty());
        artistColumn.setCellValueFactory(cellData -> cellData.getValue().getArtistProperty());
        albumColumn.setCellValueFactory(cellData -> cellData.getValue().getAlbumProperty());
       lengthColumn.setCellValueFactory(cellData -> cellData.getValue().getLengthProperty());
    }
     
     public void setView(View view) {
        this.view = view; 
        trackListTable.setItems(view.getTrackList());
    }
    
}
