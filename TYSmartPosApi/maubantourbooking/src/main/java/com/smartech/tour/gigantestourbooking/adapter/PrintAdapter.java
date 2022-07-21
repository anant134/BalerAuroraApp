package com.smartech.tour.gigantestourbooking.adapter;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.smartech.tour.gigantestourbooking.RePrintManifestFragment;
import com.smartech.tour.gigantestourbooking.module.printData;
import com.whty.smartpos.gigantestourbooking.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class PrintAdapter extends RecyclerView.Adapter<PrintAdapter.Viewholder> {
    private Context context;
    private ArrayList<printData> printDataArrayList;
    private Fragment fragment;
    // Constructor
    public PrintAdapter(Context context, ArrayList<printData> printDataArrayList,Fragment fragment) {
        this.context = context;
        this.printDataArrayList = printDataArrayList;
        this.fragment=fragment;
    }
    @NonNull
    @Override
    public PrintAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // to inflate the layout for each item of recycler view.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reprintlist_layout, parent, false);
        return new PrintAdapter.Viewholder(view);
    }
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull PrintAdapter.Viewholder holder,  int position) {
        // to set data to textview and imageview of each card layout
        final printData model = printDataArrayList.get(position);
        holder.reprinttripboatName.setText(model.getDescription());
        holder.reprinttripcaptainName.setText(model.getUsername());
        holder.reprinttripdate.setText(model.getCreatedon());
        holder.reprinttripport.setText(model.getStartPort());
        holder.rl_reprint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((RePrintManifestFragment) fragment).callprint(model);
            }
        });
        // holder.courseIV.setImageResource(model.getCourse_image());
    }
    @Override
    public int getItemCount() {
        // this method is used for showing number
        // of card items in recycler view.
        return printDataArrayList.size();
    }
    public class Viewholder  extends RecyclerView.ViewHolder{
        private TextView reprinttripboatName,reprinttripcaptainName,reprinttripdate,reprinttripport;
        private RelativeLayout rl_reprint;
        public Viewholder(@NonNull @NotNull View itemView) {
            super(itemView);
            reprinttripboatName = itemView.findViewById(R.id.reprinttripboatName);
            reprinttripcaptainName = itemView.findViewById(R.id.reprinttripcaptainName);
            reprinttripdate = itemView.findViewById(R.id.reprinttripdate);
            reprinttripport= itemView.findViewById(R.id.reprinttripport);
            rl_reprint=itemView.findViewById(R.id.rl_reprint);

        }
    }
}