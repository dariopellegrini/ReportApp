package com.s4win.reportapp.activities;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.s4win.reportapp.R;
import com.s4win.reportapp.model.EndpointError;
import com.s4win.reportapp.model.Report;
import com.s4win.reportapp.service.OnCompletionListener;
import com.s4win.reportapp.service.Service;

public class NewReportActivity extends AppCompatActivity {
    // Dichiarazione variabili
    EditText titleEditText, descriptionEditText;
    ProgressBar progressBar;
    Service service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_report);

        // Inizializzazione degli oggetti della UI
        titleEditText = (EditText) findViewById(R.id.titleEditText);
        descriptionEditText = (EditText) findViewById(R.id.descriptionEditText);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        // Aggiunta del tasto back
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Configurazione oggetto per la comunicazione con il network
        service = com.s4win.reportapp.service.Service.getInstance();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Esecuzione del back in caso di pressione del tasto back
        onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    // Operazioni con il network
    public void sendReport(View view) {
        String title = titleEditText.getText().toString();
        String description = descriptionEditText.getText().toString();

        // All'invio nascondere la tastiera
        ((InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE)).toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

        // Visualizzazione di una progress bar
        progressBar.setVisibility(View.VISIBLE);
        service.addReport(title, description, new OnCompletionListener<Report>() {
            @Override
            public void onSuccess(Report object) {
                // Rimozione della progress bar
                progressBar.setVisibility(View.INVISIBLE);

                // Log delle informazioni
                Log.e("Reportapp", object.getTitle());

                // Ritorno alla schermata precedente
                NewReportActivity.this.onBackPressed();
            }

            @Override
            public void onError(EndpointError error) {
                // Rimozione della progress bar
                progressBar.setVisibility(View.INVISIBLE);

                // Log dell'errore
                Log.e("Reportapp", error.getError().getMessage());
            }
        });
    }
}
