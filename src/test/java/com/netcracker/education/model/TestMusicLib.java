package com.netcracker.education.model;

import org.junit.Test;
import static org.junit.Assert.*;

public class TestMusicLib {
    

    // TODO add test methods here. The name must begin with 'test'. For example:
    // public void testHello() {}
    @Test
    public void testIsEmpty() {
        boolean actual = Track.validateString(null); 
        assertFalse(actual);
        
        actual = Track.validateString(""); 
        assertFalse(actual);
        
        actual = Track.validateString("   "); 
        assertFalse(actual);
        
        actual = Track.validateString(",   "); 
        assertFalse(actual);
        
        actual = Track.validateString(".gfgfg"); 
        assertFalse(actual);
        
        actual = Track.validateString("song.mp3"); 
        assertTrue(actual);
      }
}
