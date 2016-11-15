
package com.netcracker.education.controller;

public class AlreadyExistsException extends Exception {

    public AlreadyExistsException() {
    }

    public AlreadyExistsException(String msg) {
        super(msg);
    }
}
