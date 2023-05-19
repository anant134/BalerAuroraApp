package com.smartech.tour.baleraurora.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import android.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.smartech.tour.baleraurora.CaptainRegistrationFragment;
import com.smartech.tour.baleraurora.module.userinfo;
import com.whty.smartpos.gigantestourbooking.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class CaptainAdapter extends RecyclerView.Adapter<CaptainAdapter.Viewholder> {
    private Context context;
    private ArrayList<userinfo> userinfoArrayList;
    private Fragment fragment;
    // Constructor
    public CaptainAdapter(Context context, ArrayList<userinfo> userinfoArrayList,Fragment fragment) {
        this.context = context;
        this.userinfoArrayList = userinfoArrayList;
        this.fragment=fragment;
    }
    @NonNull
    @Override
    public CaptainAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // to inflate the
        // for each item of recycler view.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.captainlist_layout, parent, false);
        return new CaptainAdapter.Viewholder(view);
    }
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull CaptainAdapter.Viewholder holder,  int position) {
        // to set data to textview and imageview of each card layout
        final userinfo model = userinfoArrayList.get(position);
        holder.captainName.setText(model.getUserName());
        holder.rl_captain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((CaptainRegistrationFragment) fragment).callCardreading(model);
            }
        });
        // holder.courseIV.setImageResource(model.getCourse_image());
    }
    @Override
    public int getItemCount() {
        // this method is used for showing number
        // of card items in recycler view.
        return userinfoArrayList.size();
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
