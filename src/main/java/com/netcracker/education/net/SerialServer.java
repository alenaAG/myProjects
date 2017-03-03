/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netcracker.education.net;

import com.netcracker.education.controller.AlreadyExistsException;
import com.netcracker.education.controller.Control;
import com.netcracker.education.controller.FileInOut;
import com.netcracker.education.model.Genre;
import com.netcracker.education.model.Message;
import com.netcracker.education.model.Track;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;

/**
 *
 * @author 1
 */
public class SerialServer {

    private static Control controller;
    private static ObservableList<Track> trackList = FXCollections.observableArrayList();
    private static ObservableList<Genre> genreList = FXCollections.observableArrayList();

    public static void main(String[] args) throws IOException {

        ServerSocket serverSockets = null;
        try {
            serverSockets = new ServerSocket(4444);
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (true) {
            System.out.println("Ожидаем подключения клиента... ");
            Thread multiThread = new MultiThread(serverSockets.accept());
            System.out.println("Клиент подключен.");
            multiThread.start();
        }
    }

    static class MultiThread extends Thread {

        private Socket client;

        public MultiThread(Socket client) {
            this.client = client;
        }

        public void run() {
            try {
                newThread(client);

            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        }
    }

    private static void sendLibs(ObjectOutputStream out) throws IOException {
        
        out.writeInt(controller.TrackList().size());
        for (int i = 0; i < controller.TrackList().size(); i++) {
            out.writeInt(controller.TrackList().get(i).getId());
            out.writeObject(controller.TrackList().get(i).getSongName());
            out.writeObject(controller.TrackList().get(i).getArtist());
            out.writeObject(controller.TrackList().get(i).getAlbum());
            out.writeObject(controller.TrackList().get(i).getLength());
            out.writeInt(controller.TrackList().get(i).getGenreList().size());
            for (int j = 0; j < controller.TrackList().get(i).getGenreList().size(); j++) {
                out.writeInt(controller.TrackList().get(i).getGenreList().get(j).getId());
                out.writeObject(controller.TrackList().get(i).getGenreList().get(j).getGenreName());
            }
        }
        out.writeInt(controller.GenreList().size());
        for (int i = 0; i < controller.GenreList().size(); i++) {
            out.writeInt(controller.GenreList().get(i).getId());
            out.writeObject(controller.GenreList().get(i).getGenreName());
        }
        out.flush();
    }

    private static void newThread(Socket client) throws IOException, ClassNotFoundException {
        ObjectOutputStream out = new ObjectOutputStream(client.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(client.getInputStream());
        System.out.println("Начинаем работать1..");
        try (Reader in2 = new FileReader("TrackLib.txt")) {
            SerialServer.trackList = FXCollections.observableArrayList(FileInOut.readTrackLibrary(in2));
        } catch (IOException e) {
            System.err.print(e.getMessage());
        }
        try (Reader in2 = new FileReader("GenreLib.txt")) {
            SerialServer.genreList = FXCollections.observableArrayList(FileInOut.readGenreLibrary(in2));
        } catch (IOException e) {
            System.err.print(e.getMessage());
        }
        SerialServer.controller=new Control(SerialServer.trackList,SerialServer.genreList);

        if (!(in.readBoolean())) {
            System.out.println("^((..");
        } else {
            System.out.println("Начинаем работать..");
            SerialServer.sendLibs(out);
            
            System.out.println("отправили..");

        }

        int b = 0;
        while (!(b == -1)) {
            Message message;
            message = (Message) in.readObject();
            switch (message.getKey()) {
                case 1:
                    Object[] args = message.getArgs();
                     {
                        try {
                            SerialServer.deleteTrack((int) args[0]);
                            message.setException(null);
                            out.writeObject(message);
                            out.flush();
                            SerialServer.sendLibs(out);

                        } catch (Exception ex) {
                            message.setException(ex);
                            out.writeObject(message);
                            Logger.getLogger(SerialServer.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    break;
                case 2:
                    args = message.getArgs();
                     {
                        try {
                            SerialServer.addTrack((String) args[0], (String) args[1], (String) args[2], (Duration) args[3]);
                            message.setException(null);
                            out.writeObject(message);
                            out.flush();
                            SerialServer.sendLibs(out);
                        } catch (AlreadyExistsException ex) {
                            message.setException(ex);
                            out.writeObject(message);
                            Logger.getLogger(SerialServer.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    break;
                case 3:
                    args = message.getArgs();
                     {
                        try {
                            SerialServer.editTrack((int)args[0],(String) args[1], (String) args[2], (String) args[3], (Duration) args[4]);
                            message.setException(null);
                            out.writeObject(message);
                            out.flush();
                            SerialServer.sendLibs(out);
                        } catch (AlreadyExistsException ex) {
                            message.setException(ex);
                            out.writeObject(message);
                            Logger.getLogger(SerialServer.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    break;
                case -1:
                    System.out.println("Client is off");
                    b=-1;
                    break;
                default:
                    break;

            }
        }

        out.close();
        in.close();
    }

    public static void deleteTrack(int trackId) throws Exception {
        if (SerialServer.controller.getTrackById(trackId) == null) {
            throw new Exception();
        } else {
            SerialServer.controller.delTrack(SerialServer.controller.getTrackById(trackId));
        }

    }

    public static void addTrack(String songName, String artist, String album, Duration duration) throws AlreadyExistsException {
        SerialServer.controller.addTrack(songName, artist, album, duration);
    }

    public static void editTrack(int id, String songName, String artist, String album, Duration duration) throws AlreadyExistsException {
        SerialServer.controller.editTrack(id, songName, artist, album, duration);

    }

}
