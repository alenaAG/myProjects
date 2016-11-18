package com.netcracker.education.model;

import java.util.ArrayList;
import java.util.List;

public class Genre {
	private int id;
	private String genreName;
	private List trackList;
        private final static String DEFAULT_NAME="default";
        private final static int DEFAULT_ID=-1;
        public Genre(){this.id=DEFAULT_ID;this.genreName=DEFAULT_NAME;}
	public Genre(int id, String name, List<Track> trackList)
         {
             if (!Genre.validateString(name)) throw new IllegalArgumentException("Incorect GenreName");
             this.id=id;
             this.genreName=name;
             this.trackList=trackList;
         }
        public Genre(String name)
         {
             if (!Genre.validateString(name)) throw new IllegalArgumentException("Incorect GenreName");
             this.id=DEFAULT_ID;
             this.genreName=name;
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
                if (!Genre.validateString(genreName)) throw new IllegalArgumentException("Incorect GenreName");
		this.genreName=genreName;
		}
        private static boolean validateString(String s)
        {
        boolean b=true;
        if (s.charAt(0)==' ') return false;
        if (s.charAt(0)=='.') return false;
        if (s.charAt(0)==',') return false;
        return b;
        }
   	@Override
        public boolean equals(Object object)
        {
            if (this.getClass()!=object.getClass()) return false;
            Genre genre=(Genre)object;
            if (this.getGenreName()==genre.getGenreName()) return true;
            return false;
        }
        @Override
        public String toString()
        {
            String s;
            s="ID: "+this.getId()+" GENRE: "+this.getGenreName();
            
            return s;
        }
        
}
