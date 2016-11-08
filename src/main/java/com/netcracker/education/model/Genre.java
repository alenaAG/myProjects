package com.netcracker.education.model;

import java.util.List;

public class Genre {
	private int id;
	private String genreName;
	private List trackList;
	
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
