package com.s4win.reportapp.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.s4win.reportapp.R;
import com.s4win.reportapp.model.Report;

import java.util.List;

/**
 * Created by dariopellegrini on 11/09/17.
 */

public class ReportsAdapter extends RecyclerView.Adapter<ReportsAdapter.Holder> {
    private List<Report> reports;

    // Provide a suitable constructor (depends on the kind of dataset)
    public ReportsAdapter(List<Report> reports) {
        this.reports = reports;
    }

    public List<Report> getReports() {
        return reports;
    }

    public void setReports(List<Report> reports) {
        this.reports = reports;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ReportsAdapter.Holder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.report_cell, parent, false);
        return new Holder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(Holder holder, int position) {
        if (reports != null) {
            Report report = reports.get(position);
            if (report != null) {
                holder.configureWithReport(report);
            }
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return reports.size();
    }


    public class Holder extends RecyclerView.ViewHolder {
        Report report;
        TextView titleTextView, descriptionTextView;
        ImageButton editImageButton;

        public Holder(View itemView) {
            super(itemView);
            titleTextView = (TextView) itemView.findViewById(R.id.titleTextView);
            descriptionTextView = (TextView) itemView.findViewById(R.id.descriptionTextView);
        }

        public void configureWithReport(final Report report) {
            this.report = report;
            titleTextView.setText(report.getTitle());
            descriptionTextView.setText(report.getDescription());
        }
    }
}
