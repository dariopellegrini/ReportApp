package com.s4win.reportapp.network;

import org.json.JSONObject;

/**
 * Created by dariopellegrini on 11/09/17.
 */

public interface NetworkCompletion {
    void onSuccess(JSONObject response);
    void onError(int statusCode, JSONObject response);
}
