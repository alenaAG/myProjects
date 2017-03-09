/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alenag.client;

import com.alenag.common.Control;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;

/**
 *
 * @author 1
 */
public class RootLayoutController {

    private View view;
    private Control controller;

    public RootLayoutController() {
    }

    @FXML
    private MenuItem editGenresButton;

    @FXML
    public void initialize() {

    }

    public void setViev(View view) {
        this.view = view;
        this.controller = view.Controller();
    }

    @FXML
    private void handleEditGenres() {
        boolean okClicked = view.showGenresDialog(null);
        view.showTrackLibrary();
        

    }

}
