package com.smartech.tour.baleraurora.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.smartech.tour.baleraurora.module.cardState;
import com.whty.smartpos.gigantestourbooking.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class TestCardAdapter extends RecyclerView.Adapter<TestCardAdapter.Viewholder> {
    private Context context;
    private ArrayList<cardState> cardStateArrayList;
    // Constructor
    public TestCardAdapter(Context context, ArrayList<cardState> cardStateArrayList) {
        this.context = context;
        this.cardStateArrayList = cardStateArrayList;
    }
    @NonNull
    @Override
    public TestCardAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // to inflate the layout for each item of recycler view.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.testcardlist_layout, parent, false);
        return new Viewholder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull TestCardAdapter.Viewholder holder, int position) {
        // to set data to textview and imageview of each card layout

        cardState model = cardStateArrayList.get(position);
        String name=model.getGuestInfo().getName();
        if (name != null && !name.isEmpty() && !name.equals("null")){
            holder.courseNameTV.setText(model.getCardState());
            holder.courseNameTV.setVisibility(View.GONE);
            holder.idTVTouristName.setText(name);
        }else{
            holder.courseNameTV.setText(model.getCardState());
            holder.idTVTouristName.setText(name);
            holder.idTVTouristName.setVisibility(View.GONE);;
        }

       // holder.courseRatingTV.setText("TV" );
       // holder.courseIV.setImageResource(model.getCourse_image());
    }
    @Override
    public int getItemCount() {
        // this method is used for showing number
        // of card items in recycler view.
        return cardStateArrayList.size();
    }
    public class Viewholder  extends RecyclerView.ViewHolder{
        private ImageView courseIV;
        private TextView courseNameTV, courseRatingTV,idTVTouristName;


        public Viewholder(@NonNull @NotNull View itemView) {
            super(itemView);
            courseIV = itemView.findViewById(R.id.idIVCourseImage);
            courseNameTV = itemView.findViewById(R.id.idTVCourseName);
            idTVTouristName = itemView.findViewById(R.id.idTVTouristName);

        }
    }
    public void updateList(ArrayList<cardState> cardStateArrayList){
        this.cardStateArrayList=cardStateArrayList;
        notifyDataSetChanged();
    }
}
