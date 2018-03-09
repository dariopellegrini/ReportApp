package com.s4win.reportapp.model;

/**
 * Created by dariopellegrini on 11/09/17.
 */

public class EndpointError {
    private ErrorContent error;

    public EndpointError() {
    }

    public ErrorContent getError() {
        return error;
    }

    public void setError(ErrorContent error) {
        this.error = error;
    }
}
