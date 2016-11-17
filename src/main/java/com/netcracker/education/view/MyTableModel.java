/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netcracker.education.view;

import com.netcracker.education.controller.Control;
import com.netcracker.education.model.*;
import java.util.*;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

/**
 *
 * @author 1
 */
public class MyTableModel implements TableModel{
   
 
        private List<TableModelListener> listeners = new ArrayList<TableModelListener>();
        private List<Track> trackList;
        private List<Genre> genreList;
         public MyTableModel() {
            super();
        }
 
        public MyTableModel(List<Track> trackList) {
            super();
            this.trackList = trackList;
        }
 
        public void addTableModelListener(TableModelListener listener) {
            listeners.add(listener);
        }
 @Override
        public Class<?> getColumnClass(int columnIndex) {
            return String.class;
        }
 @Override
        public int getColumnCount() {
            return 5;
        }
 @Override
 
        public String getColumnName(int columnIndex) {
            switch (columnIndex) {
            case 0:
                return "SongName";
            case 1:
                return "Artist";
            case 2:
                return "Album";
            case 3:
                return "Length";
            case 4:
                return "Genres";
            default:
                return "";
            }
            
        }
 @Override
        public int getRowCount() {
            return trackList.size();
        }
 @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            Track track = trackList.get(rowIndex);
            switch (columnIndex) {
            case 0:
                return track.getSongName();
            case 1:
                return track.getArtist();
            case 2:
                return track.getAlbum();
            case 3:
                return track.getLength();
            case 4:
                return track.getGenreList();
            }
            return "";
        }
 @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return false;
        }
 @Override
        public void removeTableModelListener(TableModelListener listener) {
            listeners.remove(listener);
        }
 @Override
        public void setValueAt(Object value, int rowIndex, int columnIndex) {
           for (int i=0;i<this.getRowCount();i++)
            {
                //this.trackList.get(i)
            }
 
        }
 
    
    
}
