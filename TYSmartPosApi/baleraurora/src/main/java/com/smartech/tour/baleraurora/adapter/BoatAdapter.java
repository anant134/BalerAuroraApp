package com.smartech.tour.baleraurora.adapter;

import android.app.Fragment;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.smartech.tour.baleraurora.StartTripFragment;
import com.smartech.tour.baleraurora.module.boats;
import com.whty.smartpos.gigantestourbooking.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class BoatAdapter  extends RecyclerView.Adapter<BoatAdapter.Viewholder> {
    private Context context;
    private ArrayList<boats> boatsArrayList;
    private Fragment fragment;
    // Constructor
    public BoatAdapter(Context context, ArrayList<boats> boatsArrayList,Fragment fragment) {
        this.context = context;
        this.boatsArrayList = boatsArrayList;
        this.fragment=fragment;
    }
    @NonNull
    @NotNull
    @Override
    public BoatAdapter.Viewholder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.boatlist_layout, parent, false);
        return new BoatAdapter.Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull BoatAdapter.Viewholder holder, int position) {
        final boats model = boatsArrayList.get(position);
        holder.tv_boatname.setText(model.getName());
        holder.tv_starttripboatowner.setText(model.getOwner());
        holder.tv_starttripboatcapacity.setText(model.getCapacity().toString());
        holder.rl_starttripboat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((StartTripFragment) fragment).callTouristselection(model);
            }
        });
    }

    @Override
    public int getItemCount() {
        return boatsArrayList.size();
    }
    public class Viewholder  extends RecyclerView.ViewHolder{
        private TextView tv_boatname,tv_starttripboatowner,tv_starttripboatcapacity;
        private RelativeLayout rl_starttripboat;
        public Viewholder(@NonNull @NotNull View itemView) {
            super(itemView);
            tv_boatname = itemView.findViewById(R.id.tv_boatname);
            tv_starttripboatowner = itemView.findViewById(R.id.tv_starttripboatowner);
            tv_starttripboatcapacity = itemView.findViewById(R.id.tv_starttripboatcapacity);
            rl_starttripboat=itemView.findViewById(R.id.rl_starttripboat);

        }
    }
}
