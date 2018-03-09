package com.s4win.reportapp.application;

import android.app.Application;

import com.android.volley.toolbox.Volley;
import com.s4win.reportapp.constants.Constants;
import com.s4win.reportapp.network.Network;
import com.s4win.reportapp.service.Service;

/**
 * Created by dariopellegrini on 11/09/17.
 */

public class ReportAppApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Inizializzazione del singleton per la comunicazione con il server
        Service.getInstance().setNetwork(new Network(Constants.BASE_URL, Volley.newRequestQueue(getApplicationContext())));
    }
}
