package com.smartech.tour.baleraurora.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.smartech.tour.baleraurora.module.guestInfo;
import com.whty.smartpos.gigantestourbooking.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class TouristActivityAdapter extends RecyclerView.Adapter<TouristActivityAdapter.Viewholder> {
    private Context context;
    private ArrayList<guestInfo> guestInfoArrayList;
    public TouristActivityAdapter(Context context, ArrayList<guestInfo> guestInfoArrayList){
        this.context = context;
        this.guestInfoArrayList = guestInfoArrayList;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.touristlist_layout, parent, false);
        return new TouristActivityAdapter.Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        final guestInfo model = guestInfoArrayList.get(position);
        holder.tv_touristname.setText(model.getName()+"("+model.getGender()+") ");
        holder.tv_age.setText(model.getAge().toString());
        holder.tv_touristbraceletinfo.setText("");
        holder.tv_touristismauban.setVisibility(View.GONE);
    }


    @Override
    public int getItemCount() {
        return guestInfoArrayList.size();
    }
    public class Viewholder  extends RecyclerView.ViewHolder{
        private TextView tv_touristname,tv_age,tv_touristbraceletinfo,tv_touristismauban;
        private RelativeLayout rl_tourist;
        public Viewholder(@NonNull @NotNull View itemView) {
            super(itemView);
            tv_touristname = itemView.findViewById(R.id.tv_touristname);
            tv_age = itemView.findViewById(R.id.tv_age);
            tv_touristbraceletinfo = itemView.findViewById(R.id.tv_touristbraceletinfo);
            tv_touristismauban=itemView.findViewById(R.id.tv_touristismauban);
            rl_tourist=itemView.findViewById(R.id.rl_tourist);


        }
    }
    public void updateList(ArrayList<guestInfo> guestInfoArrayList){
        this.guestInfoArrayList=guestInfoArrayList;
        notifyDataSetChanged();
    }
}
