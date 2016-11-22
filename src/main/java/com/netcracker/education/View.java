package com.netcracker.education;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.IOException;
import java.net.URL;
import javafx.application.Application;
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
        }
        catch(IOException e){e.printStackTrace();}
    }
    
    public Stage getPrimaryStage(){return primaryStage;}

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
