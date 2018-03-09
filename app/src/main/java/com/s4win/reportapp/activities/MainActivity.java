package com.s4win.reportapp.activities;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.s4win.reportapp.R;
import com.s4win.reportapp.adapters.ReportsAdapter;
import com.s4win.reportapp.model.EndpointError;
import com.s4win.reportapp.model.Report;
import com.s4win.reportapp.service.OnCompletionListener;
import com.s4win.reportapp.service.Service;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    // Dichiarazione variabili
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeToRefresh;
    ReportsAdapter adapter;
    Service service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inizializzazione degli oggetti della UI
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        swipeToRefresh = (SwipeRefreshLayout) findViewById(R.id.swipeToRefresh);

        // Configurazione recycler view
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ReportsAdapter(new ArrayList<Report>());
        recyclerView.setAdapter(adapter);

        // Configurazione swipe to refresh
        swipeToRefresh.setOnRefreshListener(this);

        // Configurazione oggetto per la comunicazione con il network
        service = Service.getInstance();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (service != null) {
            getReports();
        }
    }

    // Operazioni con il network
    private void getReports() {

        // Attivazione swipe to refresh
        swipeToRefresh.setRefreshing(true);
        service.getReports(new OnCompletionListener<List<Report>>() {
            @Override
            public void onSuccess(List<Report> object) {
                // Disattivazione swipe to refresh
                swipeToRefresh.setRefreshing(false);

                // Aggiornamento della lista sulla UI
                adapter.setReports(object);
                adapter.notifyDataSetChanged();

                // Log delle informazioni
                Log.i("Reportapp", object.toString());
            }

            @Override
            public void onError(EndpointError error) {
                // Disattivazione swipe to refresh
                swipeToRefresh.setRefreshing(false);

                // Log dell'errore
                Log.e("Reportapp", error.getError().getMessage());
            }
        });
    }

    // Swipe to refresh
    @Override
    public void onRefresh() {
        getReports();
    }

    // Add button
    public void newReport(View view) {
        // Apertura della schermata per l'aggiunta di un nuovo report
        Intent intent = new Intent(this, NewReportActivity.class);
        startActivity(intent);
    }
}
