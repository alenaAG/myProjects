/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alenag.client;

import com.alenag.common.AlreadyExistsException;
import com.alenag.common.Control;
import com.alenag.common.Genre;
import com.alenag.common.Message;
import com.alenag.common.Track;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.Duration;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author 1
 */
public class SerialClient {

    private static Control controller;
    private static Socket client;
    private static int k = 0;
    private static Message message;
    private static ObjectOutputStream out;
    private static ObjectInputStream in;
    private static ObservableList<Track> trackList = FXCollections.observableArrayList();
    private static ObservableList<Genre> genreList = FXCollections.observableArrayList();

    public static void setMessage(int key, Exception exc, Object... args) {
        switch (key) {
            case -1: {
                SerialClient.message = new Message(key, exc, args);
                try {
                    out.writeObject(SerialClient.message);
                } catch (IOException ex) {
                    Logger.getLogger(SerialClient.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            }
            default: {
                SerialClient.message = new Message(key, exc, args);
                System.out.println(message.toString());
                try {
                    out.writeObject(SerialClient.message);
                } catch (IOException ex) {
                    Logger.getLogger(SerialClient.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    SerialClient.message = (Message) in.readObject();
                } catch (IOException ex) {
                    Logger.getLogger(SerialClient.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(SerialClient.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (SerialClient.message.getException() == null) {
                    try {
                        SerialClient.readLibs(in);
                    } catch (IOException ex) {
                        Logger.getLogger(SerialClient.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(SerialClient.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    SerialClient.controller = new Control(SerialClient.trackList, SerialClient.genreList);
                }
                else { try {
                    SerialClient.readLibs(in);
                    } catch (IOException ex) {
                        Logger.getLogger(SerialClient.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(SerialClient.class.getName()).log(Level.SEVERE, null, ex);
                    }
                SerialClient.controller = new Control(SerialClient.trackList, SerialClient.genreList);
}

                View.setClient(client, SerialClient.controller);
                break;
            }
        }
    }

    public static void readLibs(ObjectInputStream in) throws IOException, ClassNotFoundException {
        SerialClient.trackList.clear();
        
        int n = in.readInt();
        for (int i = 0; i < n; i++) {
            int id = in.readInt();
            String songname = (String) in.readObject();
            String artist = (String) in.readObject();
            String album = (String) in.readObject();
            Duration duration = (Duration) in.readObject();
            Track track = new Track(id, songname, artist, album, duration);
            int k = in.readInt();
            for (int j = 0; j < k; j++) {
                int idd = (int) in.readInt();
                String genrN = (String) in.readObject();
                Genre genre = new Genre(idd, genrN);
                try {
                    track.addGenre(genre);
                } catch (AlreadyExistsException ex) {
                    Logger.getLogger(SerialClient.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            SerialClient.trackList.add(track);

        }
        n = in.readInt();
        SerialClient.genreList.clear();
        for (int i = 0; i < n; i++) {
            int idd = (int) in.readInt();
            String genrN = (String) in.readObject();
            Genre genre = new Genre(idd, genrN);
            SerialClient.genreList.add(genre);
        }
    }

    public static Message getMessage() {
        return SerialClient.message;
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        client = new Socket("127.0.0.1", 4444);
        String s = "";
        out = new ObjectOutputStream(client.getOutputStream());
        in = new ObjectInputStream(client.getInputStream());
        System.out.println("clientINwhile");
        boolean b = true;
        SerialClient.out.writeBoolean(b);
        SerialClient.out.flush();
        SerialClient.readLibs(in);
        System.out.println("clientINwhile");
        SerialClient.controller = new Control(SerialClient.trackList, SerialClient.genreList);
        View.setClient(client, SerialClient.controller);
        View.main(null);
        out.close();
        in.close();
        client.close();

    }

}
