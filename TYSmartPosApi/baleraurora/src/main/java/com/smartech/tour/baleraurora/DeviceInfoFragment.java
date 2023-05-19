package com.smartech.tour.baleraurora;

import android.os.Bundle;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.smartech.tour.baleraurora.operationmodule.OperationInter;
import com.whty.smartpos.gigantestourbooking.R;

public class DeviceInfoFragment extends Fragment implements View.OnClickListener {
    MainActivity mainactivie;
    ImageView imv_deviceinfoback;
    TextView txtdeviceinfo;
    TextView txtdeviceinfo1;
    String carddetail1="";
    String carddetail="";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view= inflater.inflate(R.layout.fragment_device_info, container, false);
        imv_deviceinfoback=view.findViewById(R.id.imv_deviceinfoback);
        imv_deviceinfoback.setOnClickListener(this);
        txtdeviceinfo=view.findViewById(R.id.txtdeviceinfo);

        mainactivie=((MainActivity) getActivity());
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

                                                       carddetail=OperationInter.getDevice(0).execute(1);
                                                       carddetail1=OperationInter.getDevice(0).execute(2);
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
                    String s=carddetail.toString()+"-------"+carddetail1.toString();
                    txtdeviceinfo.setText(s);

                }
            }, 3000);

        }catch (Exception ex){
            ex.printStackTrace();

        }
    }
    @Override
    public void onClick(View v) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        switch (v.getId()) {
            case R.id.imv_deviceinfoback:
                Fragment fragment = getFragmentManager().findFragmentByTag("landing");
                if(fragment != null)
                    getFragmentManager().beginTransaction().remove(fragment).commit();
                fragmentTransaction.replace(R.id.container, new LandingFragment(),"landing");
                fragmentTransaction.commit();
                break;
        }

    }
}