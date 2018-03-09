package com.s4win.reportapp.model;

/**
 * Created by dariopellegrini on 11/09/17.
 */

public class ErrorContent {
    private String message;
    private int status;

    public ErrorContent() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
