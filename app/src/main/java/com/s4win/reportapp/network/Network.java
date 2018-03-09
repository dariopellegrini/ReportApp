package com.s4win.reportapp.network;

import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.s4win.reportapp.model.EndpointError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dariopellegrini on 11/09/17.
 */

public class Network {
    RequestQueue requestQueue;
    String baseURL;

    public Network(String baseURL, RequestQueue requestQueue) {
        this.baseURL = baseURL;
        this.requestQueue = requestQueue;
    }

    public JsonObjectRequest request(int method, String url, final Map<String, String> parameters, final NetworkCompletion completion) {

        if (parameters != null && method == Request.Method.GET) {
            url += "?";
            for (Map.Entry<String, String> entry : parameters.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue().toString();
                url += (key + "=" + value);
                url += "&";
            }
            url = url.substring(0, url.length() - 1);
        }
        // Request a string response from the provided URL.
        if (url.contains("http") == false) {
            url = baseURL + url;
        }
        JsonObjectRequest stringRequest = new JsonObjectRequest(method, url, parameters != null ? new JSONObject(parameters) : null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        completion.onSuccess(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String response = null;
                NetworkResponse networkResponse = error.networkResponse;
                if(networkResponse != null && networkResponse.data != null){
                    response = new String(networkResponse.data);
                }
                try {
                    completion.onError(error.networkResponse.statusCode, new JSONObject(response));
                } catch (JSONException e) {
                    completion.onError(error.networkResponse.statusCode, null);
                }
            }
        });

        // retry policy afer seconds of timeout
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(25000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        this.requestQueue.add(stringRequest);
        return stringRequest;
    }
}
