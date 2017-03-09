/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.netcracker.education.common;

import java.io.Serializable;

/**
 *
 * @author 1
 */
public class Message implements Serializable{

    private int key;
    private Object[] args;
    private Exception exc;

    public Message() {
        this.key = 0;
        this.args = null;
        this.exc=null;
    }

    public Message(int key,Exception exc, Object... args) {
        this.key = key;
        this.args = args;
        this.exc=exc;

    }

    public void setKey(int key) {
        this.key = key;
    }

    public void setArgs(Object... args) {
        this.args = args;
    }

    public Object[] getArgs() {
        return this.args;
    }

    public int getKey() {
        return this.key;
    }

    public Exception getException() {
        return this.exc;
    }

    public void setException(Exception exc) {
        this.exc = exc;
    }

}
