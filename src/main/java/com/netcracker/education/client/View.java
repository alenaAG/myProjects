package com.netcracker.education.client;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.netcracker.education.common.AlreadyExistsException;
import com.netcracker.education.common.Control;
import com.netcracker.education.server.FileInOut;
import com.netcracker.education.common.Genre;
import com.netcracker.education.common.Track;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.net.Socket;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
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
import javafx.stage.WindowEvent;

/**
 *
 * @author 1
 */
public class View extends Application {

    private Stage primaryStage;
    public static Socket client;
    private BorderPane rootLayout;
    private AnchorPane centerLayout;

    private static ObservableList<Track> trackList = FXCollections.observableArrayList();
    private static ObservableList<Genre> genreList = FXCollections.observableArrayList();
    private static Control controller;
    private ViewController viewController;
    private GenresController genresController;
    

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("MUSIC LIBTRARY");
        initRootLayout();
        showTrackLibrary();
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                System.out.println("Stage is closing");
                SerialClient.setMessage(-1, null, 1);
                
            }
        });
    }

    public Control Controller() {
        return this.controller;
    }

    public void initRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(View.class.getResource("../RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();
            Scene scene = new Scene(rootLayout);
            RootLayoutController controller = loader.getController();
            controller.setViev(this);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void update()
    {
            viewController.setView(this);
            
            
    }
    public void updateG()
    {genresController.setViev(this);
    }
    public void showTrackLibrary() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(View.class.getResource("../View.fxml"));
            AnchorPane trackLibrary;
            trackLibrary = (AnchorPane) loader.load();
            rootLayout.setCenter(trackLibrary);
            trackLibrary.setPrefSize(800, 600);
            this.viewController = loader.getController();
            viewController.setView(this);

        } catch (IOException f) {
            f.printStackTrace();
        }
    }

    public boolean showGenreEditDialog(Genre genre) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(View.class.getResource("../AddGenre.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Genre");
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.initOwner(primaryStage.getScene().getWindow());
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

    public boolean showGenresDialog(Track track) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(View.class.getResource("../Genres.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Genres");
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.initOwner(primaryStage.getScene().getWindow());
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
            this.genresController = loader.getController();
            this.genresController.setDialogStage(dialogStage);
           this.genresController.setViev(this);
            if (track == null) {
                this.genresController.setGenresEditLibrary();
                //this.update();
                dialogStage.showAndWait();
            } else {
                this.genresController.setGenresOfTrack(track);
                this.genresController.setGenresLibrary();
                dialogStage.showAndWait();
            }
            //this.controller = this.genresController.getController();
            return this.genresController.isOkClicked();
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
    public static void setClient(Socket client,Control controller){
        View.client=client;
        View.controller=controller;
         View.trackList=View.controller.TrackList();
        View.genreList=View.controller.GenreList();
        
        
    }
    

    public View() {
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
               
        launch(args);
    }

}
