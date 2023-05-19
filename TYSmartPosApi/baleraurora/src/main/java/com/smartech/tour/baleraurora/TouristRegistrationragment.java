package com.smartech.tour.baleraurora;

import android.os.Build;
import android.os.Bundle;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.gson.Gson;
import com.smartech.tour.baleraurora.adapter.TouristAdapter;
import com.smartech.tour.baleraurora.module.cardState;
import com.smartech.tour.baleraurora.module.device;
import com.smartech.tour.baleraurora.module.guestInfo;
import com.smartech.tour.baleraurora.module.operationData;
import com.smartech.tour.baleraurora.operationmodule.OperationInter;
import com.whty.smartpos.gigantestourbooking.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class TouristRegistrationragment extends Fragment  implements  View.OnClickListener{

    ImageView imv_touristregistrationback;
    ImageButton imvbutton_touristscan;
    MainActivity mainactivie;
    ArrayList<guestInfo> _guestInfoarr;
    RecyclerView rv_touristlist;
    TouristAdapter touristAdapter;
    Bundle bundle;
    JSONObject ovjmsg=new JSONObject();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view =inflater.inflate(R.layout.fragment_tourist_registrationragment, container, false);
        mainactivie=((MainActivity) getActivity());
        rv_touristlist=view.findViewById(R.id.rv_touristlist);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
        rv_touristlist.setLayoutManager(staggeredGridLayoutManager);
        _guestInfoarr=new ArrayList<>();
        touristAdapter = new TouristAdapter(view.getContext(), _guestInfoarr,TouristRegistrationragment.this);
        rv_touristlist.setAdapter(touristAdapter);

        imv_touristregistrationback=view.findViewById(R.id.imv_touristregistrationback);
        imv_touristregistrationback.setOnClickListener(this);
        imvbutton_touristscan=view.findViewById(R.id.imvbutton_touristscan);
        imvbutton_touristscan.setOnClickListener(this);
        bundle= this.getArguments();

        return  view;

    }

    @Override
    public void onClick(View v) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        switch (v.getId()) {
            case R.id.imv_touristregistrationback:
                Fragment fragment = getFragmentManager().findFragmentByTag("Landing");
                if(fragment != null)
                    getFragmentManager().beginTransaction().remove(fragment).commit();
                fragmentTransaction.replace(R.id.container, new LandingFragment(),"Landing");
                fragmentTransaction.commit();
                break;
            case R.id.imvbutton_touristscan:
                mainactivie.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        try{
                            final Handler cwjHandler = new Handler();

                            final Runnable mUpdateResults = new Runnable() {
                                @RequiresApi(api = Build.VERSION_CODES.M)
                                public void run() {
                                    updateRecycleUI();
                                }
                            };
                            new Thread() {
                                @RequiresApi(api = Build.VERSION_CODES.M)
                                @Override
                                public void run() {
                                    super.run();

                                    final String data= OperationInter.getDevice(5).execute(1);
                                    GetAllTouristData(data.split("_")[0]);

                                    //   cwjHandler.post(mUpdateResults);
                                    cwjHandler.post(mUpdateResults);
                                }
                            }.start();
                        }catch (Exception ex){

                        }
                    }
                });


                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void updateRecycleUI() {

        touristAdapter.updateList(_guestInfoarr);
    }

    public void callCardreading(guestInfo tourist){
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        Bundle bundle = new Bundle();
        operationData opd=new operationData();
        opd.setFromname("touristregistration");
        opd.setId(tourist.getId());
        opd.setName(tourist.getName());

        Gson gson=new Gson();
        bundle.putString("form", gson.toJson(opd));

        CardReadingFragment cf=new CardReadingFragment();
        cf.setArguments(bundle);
        fragmentTransaction.replace(R.id.container,cf,"touristregistration" );
        fragmentTransaction.commit();
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    public void GetAllTouristData(String ticketid){
        try {
            JSONObject obj = new JSONObject();
            JSONObject objdata = new JSONObject();
            obj.put("requestfor", "gettouristbyregid");
            objdata.put("reg", ticketid);
            obj.put("data", objdata);
            String strResult = mainactivie.Constantvariable.funPostData(obj, "tour.php").toString();
            Gson gson = new Gson();
            JSONObject objJsonObject = new JSONObject(strResult);
            if (objJsonObject.get("resultkey").equals(1)){
                _guestInfoarr=new ArrayList<>();
                mainactivie._guestInfo=new ArrayList<>();
                JSONArray objarr=objJsonObject.getJSONArray("resultvalue");
                for (int i = 0; i < objarr.length(); i++) {
                    JSONObject objguestInfo=objarr.getJSONObject(i);
                    guestInfo _guestInfo=new guestInfo();
                    if(objguestInfo.getString("rfid").equals("null")){
                        _guestInfo.setId(objguestInfo.getInt("id"));
                        _guestInfo.setGender(objguestInfo.getString("gender").equals("0")==true?"M":"F");
                        _guestInfo.setAddress(objguestInfo.getString("address1"));
                        _guestInfo.setAge(objguestInfo.getInt("age"));
                        _guestInfo.setName(objguestInfo.getString("firstname")+" "+objguestInfo.getString("lastname"));
                        _guestInfo.setIsmaubancitizen(objguestInfo.getInt("ismaubancitizen")==0?false:true);
                        _guestInfo.setIsrentbracelet(objguestInfo.getInt("isrentbracelet")==0?false:true);
                        _guestInfo.setIsreturningguest(objguestInfo.getInt("isreturningguest")==0?false:true);
                        _guestInfo.setIsbraceletreturned(objguestInfo.getInt("isbraceletreturned")==0?false:true);
                        _guestInfo.setIsbraceletavailable(objguestInfo.getInt("isbraceletavailable")==0?false:true);
                        _guestInfo.setIsislandhopping(objguestInfo.getInt("isislandhopping")==0?false:true);
                        _guestInfo.setIstouristscan(false);
                        _guestInfoarr.add(_guestInfo);
                        mainactivie._guestInfo.add(_guestInfo);
                    }

                }


            }else{
                mainactivie.ShowMessage(objJsonObject.getString("resultvalue"),2);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final View view = getView();
        if(view != null) {
            getView().post(new Runnable() {
                @Override
                public void run() {

                    try {
                        if(bundle!=null){

                            cardState data = new cardState();


                            Gson gson = new Gson();
                            final operationData _opd = gson.fromJson(getArguments().getString("form"), operationData.class);
                            data.setCardState(_opd.getReceivedata());
                            //map tourist rfid
                            final Handler cwjHandler = new Handler();

                            final Runnable mUpdateResults = new Runnable() {
                                @RequiresApi(api = Build.VERSION_CODES.M)
                                public void run() {
                                    try {
                                        mainactivie.ShowMessage(ovjmsg.getString("message"),ovjmsg.getInt("try"));
                                        updateRecycleUI();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                            };
                            new Thread() {
                                @RequiresApi(api = Build.VERSION_CODES.M)
                                @Override
                                public void run() {
                                    super.run();
                                    try {
                                        guestInfo guest = null;
                                        ArrayList<device> devices=mainactivie.mydb.getdevicedetail();
                                        for (int i = 0; i < mainactivie._guestInfo.size(); i++) {
                                            if(mainactivie._guestInfo.get(i).getId().equals(_opd.getId())){
                                                guest=mainactivie._guestInfo.get(i);
                                                break;
                                            }
                                        }
                                        if(guest!=null){
                                            JSONObject obj = new JSONObject();
                                            JSONObject objdata = new JSONObject();
                                            obj.put("requestfor", "touristmapping");
                                            objdata.put("touristid", _opd.getId());
                                            objdata.put("rfid", _opd.getReceivedata());
                                            objdata.put("name", guest.getName());
                                            objdata.put("flag", "i");
                                            objdata.put("deviceid", mainactivie.Constantvariable.deviceid);
                                            objdata.put("portofassign", 0);
                                            obj.put("data", objdata);
                                            String strResult = mainactivie.Constantvariable.funPostData(obj, "mapping.php").toString();
                                            Gson gson = new Gson();

                                            JSONObject objJsonObject = new JSONObject(strResult);
                                            if (objJsonObject.get("resultkey").equals(1)){
                                                //{"resultkey":1,"resultvalue":[{"rowid":"-3","errflag":"3"}]}
                                                Integer rowid =objJsonObject.getJSONArray("resultvalue").getJSONObject(0).getInt("rowid");
                                               if(rowid>0){
                                                   for (int i = 0; i < mainactivie._guestInfo.size(); i++) {
                                                       if(mainactivie._guestInfo.get(i).getId().equals(_opd.getId())){
                                                           mainactivie._guestInfo.remove(i);
                                                           break;
                                                       }
                                                   }
                                                   _guestInfoarr.addAll(mainactivie._guestInfo);
                                                   ovjmsg.put("message","Tourist register successfully.");
                                                   ovjmsg.put("try",1);
                                                   cwjHandler.post(mUpdateResults);



                                               }else{
                                                   _guestInfoarr.addAll(mainactivie._guestInfo);
                                                   ovjmsg.put("message",objJsonObject.getJSONArray("resultvalue").getJSONObject(0).getString("errmsg"));
                                                   ovjmsg.put("try",2);

                                                   cwjHandler.post(mUpdateResults);
                                               }

                                            }else{
                                                _guestInfoarr.addAll(mainactivie._guestInfo);
                                                ovjmsg.put("message",objJsonObject.getString("resultvalue"));
                                                ovjmsg.put("try",2);

                                                cwjHandler.post(mUpdateResults);

                                            }
                                        }

                                    }catch (Exception ex){

                                    }

                                }
                            }.start();






                        }
                    }catch (Exception e){
                        mainactivie.ShowMessage(e.getMessage().toString(),3);
                       // mainactivie.progressDialog.dismiss();
                    }

                }
            });
        }
    }


}