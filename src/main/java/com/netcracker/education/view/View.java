package com.netcracker.education.view;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import com.netcracker.education.AddGenreController;
import com.netcracker.education.ViewController;
import com.netcracker.education.ViewController;
import com.netcracker.education.controller.AlreadyExistsException;
import com.netcracker.education.controller.Control;
import com.netcracker.education.model.Genre;
import com.netcracker.education.model.Track;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author 1
 */
public class View extends Application {
    private Stage primaryStage;
    private BorderPane rootLayout;
    private AnchorPane centerLayout;
    
    private ObservableList<Track> trackList = FXCollections.observableArrayList();
    private ObservableList<Genre> genreList=FXCollections.observableArrayList();
    private Control controller;
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage=primaryStage;
        this.primaryStage.setTitle("MUSIC LIBTRARY");
        initRootLayout();
        showTrackLibrary();
    }
    public Control Controller(){return this.controller;}
    
    public void initRootLayout(){
        try{
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(View.class.getResource("../RootLayout.fxml"));
        rootLayout=(BorderPane)loader.load();
        Scene scene =new Scene(rootLayout);
        primaryStage.setScene(scene);
        primaryStage.show();
        }
        catch(IOException e){e.printStackTrace();}
        
    }
    public void showTrackLibrary(){
        try{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(View.class.getResource("../View.fxml"));
        AnchorPane trackLibrary;
        trackLibrary = (AnchorPane) loader.load();
        rootLayout.setCenter(trackLibrary);
        ViewController viewController = loader.getController();
        viewController.setView(this);
        }
        catch(IOException f){f.printStackTrace();}
    }
    public boolean showGenreEditDialog(Genre genre) {
    try {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(View.class.getResource("../AddGenre.fxml"));
        AnchorPane page = (AnchorPane) loader.load();
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Edit Genre");
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(primaryStage);
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);
        AddGenreController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setGenre(genre);
        dialogStage.showAndWait();
        return controller.isOkClicked();
    } catch (IOException e) {
        e.printStackTrace();
        return false;
    }
}
    public ObservableList<Track> getTrackList() {
        return this.trackList;
    }
    public ObservableList<Genre> getGenreList() {
        return this.genreList;
    }
    public View()
    {
        
        ArrayList<Genre> genreListTR1=new ArrayList<>();
        ArrayList<Genre> genreListTR2=new ArrayList<>();
        controller=new Control(trackList,genreList);
        Duration duration = Duration.parse("PT2M3S");
        try{
        controller.addGenre("Pop");  
        controller.addGenre("Rock");
        controller.addGenre("NewMusic");
        controller.addGenre("Ambient");
        }
        catch(AlreadyExistsException e){}
        controller.addTrack("In NY","JAY-Z","NY", duration, genreListTR1);
       controller.addGenreToTrack(0, 1);
        controller.addTrack("Too Good", "Drake", "Good", duration, genreListTR2);
       controller.addGenreToTrack(1, 1);
        controller.addTrack("Fine","Drake","Good",duration);
        controller.addTrack("Damn","Damn","Bad",duration);
        controller.addTrack("Anapa","YOYO","Anapa",duration);
       controller.addGenreToTrack(0, 2);
        trackList=(ObservableList<Track>)controller.TrackList();
        genreList=(ObservableList<Genre>)controller.GenreList();
    }
    public Stage getPrimaryStage(){return primaryStage;}

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
