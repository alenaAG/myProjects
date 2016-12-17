/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netcracker.education;

import com.netcracker.education.model.Genre;
import com.netcracker.education.model.Track;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author 1
 */
public class AddGenreController {

    @FXML
    private TextField genreNameField;

    private Stage dialogStage;
    private Genre genre;
    private boolean okClicked = false;

    @FXML
    private void initialize() {
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
        genreNameField.setText(genre.getGenreName());
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    @FXML
    private void handleOkButton() {
        if (isInputValid()) {
            genre.setGenreName(genreNameField.getText());
            okClicked = true;
            dialogStage.close();
        }

    }

    @FXML
    private void handleCancelButton() {
        dialogStage.close();
    }

    private boolean isInputValid() {
        String errorMessage = "";

        if (genreNameField.getText() == null || genreNameField.getText().length() == 0 || !(validateString(genreNameField.getText()))) {
            errorMessage += "No valid genre name!\n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Genre Name");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);
            alert.showAndWait();
            return false;
        }
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

}
