package com.netcracker.education.model;

import java.util.ArrayList;
import java.util.List;

public class Genre {
	private int id;
	private String genreName;
	private List trackList;
        private final static String DEFAULT_NAME="default";
        private final static List DEFAULT_LIST=new ArrayList();
        private final static int DEFAULT_ID=-1;
        public Genre(){this.id=DEFAULT_ID;this.trackList=DEFAULT_LIST;this.genreName=DEFAULT_NAME;}
	public Genre(int id, String name, List trackList)
         {
             this.id=id;
             this.genreName=name;
             this.trackList=trackList;
         }
        public Genre(String name)
         {
             this.id=DEFAULT_ID;
             this.genreName=name;
             this.trackList=DEFAULT_LIST;
         }
	public int getId(){
		return id;
		}
	public void setId(int id){
		this.id=id;
		}
        public String getGenreName(){
		return genreName;
		}
	public void setGenreName(String genreName){
		this.genreName=genreName;
		}
   	public List getTrackList(){
		return trackList;
		}
	public void setTrackList(List trackList){
		this.trackList=trackList;
		}
}
