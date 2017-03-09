
package com.alenag.common;

public class AlreadyExistsException extends Exception {

    public AlreadyExistsException(String msg) {
        super(msg);
    }

    AlreadyExistsException() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
