package com.smartech.tour.baleraurora;

import android.os.Bundle;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;


import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.smartech.tour.baleraurora.module.operationData;
import com.smartech.tour.baleraurora.operationmodule.OperationInter;
import com.whty.smartpos.gigantestourbooking.R;

public class PrintManifestFragment extends Fragment implements View.OnClickListener{

    operationData objoperationData;
    MainActivity mainactivie;
    Button btn_PM_back;
    public PrintManifestFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_print_manifest, container, false);
        Bundle bundle = this.getArguments();
        Gson gson = new Gson();
        mainactivie=((MainActivity) getActivity());
      //  objoperationData = gson.fromJson(getArguments().getString("form"), operationData.class);
        String ss="";
        //btn_PM_back=(Button) fin
        btn_PM_back=view.findViewById(R.id.btn_PM_back);
        btn_PM_back.setOnClickListener(this);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final View view = getView();
        getView().post(new Runnable() {
            @Override
            public void run() {
                mainactivie.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            final Handler cwjHandler = new Handler();

                            final Runnable mUpdateResults = new Runnable() {
                                public void run() {
                                    //updateUI();
                                }
                            };
                            Thread thread = new Thread() {
                                @Override
                                public void run() {
                                    String carddetail="";
                                    OperationInter.getDevice(4).execute(13);
                                    OperationInter.getDevice(4).execute(14);
                                    cwjHandler.post(mUpdateResults);


                                }
                            };

                            thread.start();
                        }catch (Exception e){

                        }
                    }
                });
            }
        });
    }

    @Override
    public void onClick(View v) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        switch (v.getId()) {
            case R.id.btn_PM_back:
                Fragment fragment = getFragmentManager().findFragmentByTag("landing");
                if(fragment != null)
                    getFragmentManager().beginTransaction().remove(fragment).commit();
                fragmentTransaction.replace(R.id.container, new LandingFragment(),"landing");
                fragmentTransaction.commit();
                break;
        }
    }
    /*@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=  inflater.inflate(R.layout.fragment_print_manifest, container, false);
        Bundle bundle = this.getArguments();
        Gson gson = new Gson();
        mainactivie=MainActivity
        objoperationData = gson.fromJson(getArguments().getString("form"), operationData.class);
        String ss="";

        return view;

    }*/
}