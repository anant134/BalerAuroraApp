package com.smartech.tour.gigantestourbooking;

import android.os.Bundle;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.smartech.tour.gigantestourbooking.module.operationData;
import com.smartech.tour.gigantestourbooking.module.tripData;
import com.smartech.tour.gigantestourbooking.operationmodule.OperationInter;
import com.whty.smartpos.gigantestourbooking.R;

public class PrintReceiptFragment extends Fragment {
    operationData objoperationData;
    MainActivity mainactivie;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_print_receipt, container, false);
        Bundle bundle = this.getArguments();
        Gson gson = new Gson();
        mainactivie=((MainActivity) getActivity());
        objoperationData = gson.fromJson(getArguments().getString("form"), operationData.class);
        String ss="";

        return view;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final View view = getView();
        if (view != null) {
            getView().post(new Runnable() {
                               @Override
                               public void run() {
                                   // code you want to run when view is visible for the first time

                                   mainactivie.runOnUiThread(new Runnable() {
                                       public void run() {
                                           try{
                                               final Handler cwjHandler = new Handler();

                                               final Runnable mUpdateResults = new Runnable() {
                                                   public void run() {
                                                       updateUI();
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
                                               mainactivie.ShowMessage(e.getMessage().toString(),3);

                                           }

                                       }
                                   });


                               }
                           }
            );
        }
    }

    public void updateUI(){

        try {

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mainactivie.printtripdata=new tripData();
                    if(objoperationData.getFromname().equals("reprint")){
                        FragmentManager fm = getFragmentManager();
                        FragmentTransaction fragmentTransaction;
                        fragmentTransaction = fm.beginTransaction();
                        fragmentTransaction.replace(R.id.container, new RePrintManifestFragment(), "reprint");
                        fragmentTransaction.commit();
                    }else{
                        FragmentManager fm = getFragmentManager();
                        FragmentTransaction fragmentTransaction;    
                        fragmentTransaction = fm.beginTransaction();
                        fragmentTransaction.replace(R.id.container, new LandingFragment(), "landing");
                        fragmentTransaction.commit();
                    }

                }
            }, 3000);

        }catch (Exception ex){
            ex.printStackTrace();

        }
    }

}