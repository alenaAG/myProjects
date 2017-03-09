/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.alenag.server;

import com.alenag.common.Genre;
import com.alenag.common.Track;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.StreamTokenizer;
import java.io.Writer;
import java.time.Duration;
import static java.time.Duration.ZERO;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author 1
 */
public class FileInOut {

    public static void writeLibrary(List<Track> list, Writer out) throws IOException {
        String s = "";
        Writer buildingOut = new BufferedWriter(out);
        s += list.size();
        s += "\n";
        for (int i = 0; i < list.size(); i++) {
            s += list.get(i).toString();

        }
        buildingOut.append(s);
        buildingOut.close();
    }

    public static void writeGenreLibrary(List<Genre> list, Writer out) throws IOException {
        String s = "";
        Writer buildingOut = new BufferedWriter(out);
        s += list.size();
        for (int i = 0; i < list.size(); i++) {
            s += "\n";
            s += list.get(i).getId();
            s += "\n";
            s += list.get(i).getGenreName();

        }
        buildingOut.append(s);
        buildingOut.close();
    }

    public static List<Track> readTrackLibrary(Reader in) throws IOException {

        List<Track> list = new ArrayList<>();
        Track track = new Track();
        Scanner listIn = new Scanner(in);
        if (listIn.hasNextInt()) {
            listIn.useDelimiter("\n");

            int n = listIn.nextInt();
            int k = 0;
            int id = -1;
            int genreId = -1;
            String genreName = "";
            String songName = "";
            String artist = "";
            String album = "";
            Duration duration = ZERO;
            for (int i = 0; i < n; i++) {
                List<Genre> genreList = new ArrayList<>();
                id = listIn.nextInt();
                songName = (String) listIn.next();
                artist = (String) listIn.next();
                album = (String) listIn.next();
                try {
                    duration = Duration.parse(listIn.next());
                } catch (Exception e) {
                }
                k = (int) listIn.nextInt();
                for (int j = 0; j < k; j++) {
                    genreId = (int) listIn.nextInt();
                    genreName = (String) listIn.next();
                    genreList.add(new Genre(genreId, genreName));
                }
                list.add(new Track(id, songName, artist, album, duration, genreList));
            }
        }
        listIn.close();
        return list;
    }

    public static List<Genre> readGenreLibrary(Reader in) throws IOException {
        List<Genre> list = new ArrayList<>();
        Scanner listIn = new Scanner(in);
        listIn.useDelimiter("\n");
        if (listIn.hasNextInt()) {
            int n = 0;
            n = (int) listIn.nextInt();
            int genreId = -1;
            String genreName = "";
            for (int j = 0; j < n; j++) {
                genreId = (int) listIn.nextInt();
                genreName = (String) listIn.next();
                list.add(new Genre(genreId, genreName));
            }
        }

        listIn.close();
        return list;
    }

}
