/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netcracker.education.view;

import com.netcracker.education.view.View;
import com.netcracker.education.view.View;
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

    private TableView<Track> trackListTable;
    private TableColumn<Track, String> songNameColumn;
    private TableColumn<Track, String> artistColumn;
    private TableColumn<Track, String> albumColumn;
    private TableColumn<Track, Duration> lengthColumn;
    
    
    
    private View view;
    
    public ViewController()
    {}
    
    public ViewController(View view)
    {}
    
    @FXML
     private void initialize() {
        songNameColumn.setCellValueFactory(new PropertyValueFactory<Track, String>("songName"));
        artistColumn.setCellValueFactory(new PropertyValueFactory<Track, String>("artist"));
        albumColumn.setCellValueFactory(new PropertyValueFactory<Track, String>("album"));
        lengthColumn.setCellValueFactory(new PropertyValueFactory<Track,Duration>("length"));
    }
     
     public void setView(View view) {
        this.view = view; 
        trackListTable.setItems(view.getTrackList());
    }
    
}
