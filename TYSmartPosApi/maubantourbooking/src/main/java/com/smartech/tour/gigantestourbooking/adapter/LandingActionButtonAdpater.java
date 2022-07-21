package com.smartech.tour.gigantestourbooking.adapter;

import android.app.Fragment;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.smartech.tour.gigantestourbooking.LandingFragment;
import com.smartech.tour.gigantestourbooking.module.actionButton;
import com.whty.smartpos.gigantestourbooking.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class LandingActionButtonAdpater extends RecyclerView.Adapter<LandingActionButtonAdpater.Viewholder>  {
    private Context context;
    private ArrayList<actionButton> actionButtonsArrayList;
    private Fragment fragment;
    public LandingActionButtonAdpater(Context context, ArrayList<actionButton> actionButtonsArrayList,Fragment fragment) {
        this.context = context;
        this.actionButtonsArrayList = actionButtonsArrayList;
        this.fragment=fragment;
    }
    @NonNull
    @NotNull
    @Override
    public LandingActionButtonAdpater.Viewholder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.captainlist_layout, parent, false);
        return new LandingActionButtonAdpater.Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull LandingActionButtonAdpater.Viewholder holder, int position) {
        final actionButton model = actionButtonsArrayList.get(position);
        holder.captainName.setText(model.getDesc());
        holder.rl_captain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((LandingFragment) fragment).callaction(model);
            }
        });
    }

    @Override
    public int getItemCount() {
        return actionButtonsArrayList.size();
    }
    public class Viewholder  extends RecyclerView.ViewHolder{
        private TextView captainName;
        private RelativeLayout rl_captain;
        public Viewholder(@NonNull @NotNull View itemView) {
            super(itemView);
            captainName = itemView.findViewById(R.id.captainName);
            rl_captain=itemView.findViewById(R.id.rl_captain);

        }
    }
}
