package com.smartech.tour.baleraurora;

import android.os.Bundle;


import android.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.whty.smartpos.gigantestourbooking.R;

public class EndTripFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_end_trip, container, false);
        return  view;
    }
}