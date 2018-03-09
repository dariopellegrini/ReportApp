package com.s4win.reportapp.service;

import com.android.volley.Request;
import com.google.gson.Gson;
import com.s4win.reportapp.constants.Constants;
import com.s4win.reportapp.model.EndpointError;
import com.s4win.reportapp.model.ErrorContent;
import com.s4win.reportapp.model.Report;
import com.s4win.reportapp.network.Network;
import com.s4win.reportapp.network.NetworkCompletion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dariopellegrini on 11/09/17.
 */

public class Service {
    private static Service instance = null;

    Network network;
    Gson gson;
    EndpointError parsingError;

    // Recupero istanza del singleton
    public static Service getInstance() {
        if(instance == null) {
            instance = new Service();
        }
        return instance;
    }

    // Costruttore privato del singleton
    private Service() {
        gson = new Gson();
        parsingError = new EndpointError();
        ErrorContent content = new ErrorContent();
        content.setStatus(1001);
        content.setMessage("Parsing error");
        parsingError.setError(content);
    }

    // Getter e setter
    public Network getNetwork() {
        return network;
    }

    public void setNetwork(Network network) {
        this.network = network;
    }

    // Metodo per il recupero dei report
    public void getReports(final OnCompletionListener<List<Report>> completionListener) {
        // Creazione del parametri da inviare al servizio
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("sort", "-dt_create");

        // Creazione richiesta REST GET per il recupero delle informazioni
        network.request(Request.Method.GET, Constants.REPORTS_ENPOINT, parameters, new NetworkCompletion() {
            @Override
            public void onSuccess(JSONObject response) {
                try {
                    // Mapping delle informazioni provenienti da server
                    JSONArray data = response.getJSONArray("data");
                    Report[] reports = gson.fromJson(data.toString(), Report[].class);
                    completionListener.onSuccess(Arrays.asList(reports));
                } catch (JSONException e) {
                    e.printStackTrace();

                    // Gestione dell'errore di parsing dovuto a un'eccezione
                    completionListener.onError(parsingError);
                }
            }

            @Override
            public void onError(int statusCode, JSONObject response) {

                // Gestione dell'errore dal server
                EndpointError endpointError = gson.fromJson(response.toString(), EndpointError.class);
                completionListener.onError(endpointError);
            }
        });
    }

    // Metodo per l'aggiunta di un report
    public void addReport(String title, String description, final OnCompletionListener<Report> completionListener) {
        // Creazione del parametri da inviare al servizio
        Map<String, String> parameters = new HashMap<String, String>();
        parameters.put("title", title);
        parameters.put("description", description);

        // Creazione richiesta REST POST per il recupero delle informazioni
        network.request(Request.Method.POST, Constants.REPORTS_ENPOINT, parameters, new NetworkCompletion() {
            @Override
            public void onSuccess(JSONObject response) {
                // Mapping delle informazioni provenienti da server
                Report report = gson.fromJson(response.toString(), Report.class);
                completionListener.onSuccess(report);
            }

            @Override
            public void onError(int statusCode, JSONObject response) {

                // Gestione dell'errore dal server
                EndpointError endpointError = gson.fromJson(response.toString(), EndpointError.class);
                completionListener.onError(endpointError);
            }
        });
    }
}
