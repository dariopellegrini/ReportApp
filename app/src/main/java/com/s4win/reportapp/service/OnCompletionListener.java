package com.s4win.reportapp.service;

import com.s4win.reportapp.model.EndpointError;

/**
 * Created by dariopellegrini on 11/09/17.
 */

// Interface per gestire la callback di risposta dal server
public interface OnCompletionListener<T> {
    void onSuccess(T object);
    void onError(EndpointError error);
}
