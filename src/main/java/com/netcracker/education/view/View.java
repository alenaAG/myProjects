package com.netcracker.education.view;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


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
import javafx.stage.Stage;

/**
 *
 * @author 1
 */
public class View extends Application {
    private Stage primaryStage;
    private BorderPane rootLayout;
    
    private ObservableList<Track> trackList = FXCollections.observableArrayList();
    private ObservableList<Genre> genreList=FXCollections.observableArrayList();
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage=primaryStage;
        this.primaryStage.setTitle("MUSIC LIBTRARY");
        
        initRootLayout();
        showTrackLibrary();
    }
    
    public void initRootLayout(){
        try{
        FXMLLoader loader=new FXMLLoader();
        loader.setLocation(View.class.getResource("RootLayout.fxml"));
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
        loader.setLocation(View.class.getResource("View.fxml"));
        AnchorPane trackLibrary=(AnchorPane)loader.load();
        rootLayout.setCenter(trackLibrary);
        ViewController viewController = loader.getController();
        viewController.setView(this);
        
        }
        catch(IOException e){e.printStackTrace();}
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
        Control controller=new Control(trackList,genreList);
        Duration duration = Duration.parse("PT20.345S");
        controller.addGenre("Pop");  
        controller.addGenre("Rock");
        controller.addGenre("Rock");
        controller.addGenre("NewMusic");
        controller.addGenre("Ambient");
        controller.addTrack("In NY","JAY-Z","NY", duration, genreListTR1);
        controller.addGenreToTrack(0, 1);
        controller.addTrack("Too Good", "Drake", "Good", duration, genreListTR2);
        controller.addGenreToTrack(1, 1);
        controller.addTrack("Fine","Drake","Good",duration);
        controller.addTrack("Damn","Damn","Bad",duration);
        controller.addTrack("Anapa","YOYO","Anapa",duration);
        controller.addTrack(" ", " ", " ", duration);
        controller.addGenreToTrack(0, 1);
        controller.addGenreToTrack(0, 0);
        controller.delGenre("Pop");
       /*
        System.out.println(controller.GenreList().toString());
        System.out.println(controller.TrackList().toString());
        System.out.println("Sort By SongName:");
        controller.sortBySongName();
        System.out.println(controller.TrackList().toString());
        System.out.println("Sort By Artist:");
        controller.sortByArtist();
        System.out.println(controller.TrackList().toString());
        System.out.println("Sort By Album:");
        controller.sortByAlbum();
        System.out.println(controller.TrackList().toString());
        System.out.println("Sort By Length:");
        controller.sortByLength();
        System.out.println(controller.TrackList().toString());*/
    }
    public Stage getPrimaryStage(){return primaryStage;}

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
