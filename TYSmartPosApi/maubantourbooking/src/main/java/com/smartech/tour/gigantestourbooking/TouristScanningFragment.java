package com.smartech.tour.gigantestourbooking;

import android.os.Build;
import android.os.Bundle;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.gson.Gson;
import com.smartech.tour.gigantestourbooking.adapter.TouristScanAdapter;
import com.smartech.tour.gigantestourbooking.module.device;
import com.smartech.tour.gigantestourbooking.module.guestInfo;
import com.smartech.tour.gigantestourbooking.module.operationData;
import com.smartech.tour.gigantestourbooking.module.printData;
import com.smartech.tour.gigantestourbooking.module.tripData;
import com.whty.smartpos.gigantestourbooking.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TouristScanningFragment extends Fragment implements View.OnClickListener {
    ImageView imv_touristscanback;
    RecyclerView rv_touristscanning;
    TouristScanAdapter touristAdapter;
    Button btn_scantourist,btn_starttrip;
    ArrayList<guestInfo> _guestInfoarr;
    Bundle bundle;
    MainActivity mainactivie;
    operationData _opd;
    JSONObject objmsg=new JSONObject();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view= inflater.inflate(R.layout.fragment_tourist_scanning, container, false);
        imv_touristscanback=view.findViewById(R.id.imv_touristscanback);
        imv_touristscanback.setOnClickListener(this);
        rv_touristscanning=view.findViewById(R.id.rv_touristscanning);
        btn_scantourist=view.findViewById(R.id.btn_scantourist);
        btn_scantourist.setOnClickListener(this);
        btn_starttrip=view.findViewById(R.id.btn_starttrip);
        btn_starttrip.setOnClickListener(this);

        mainactivie=((MainActivity) getActivity());
        _guestInfoarr=new ArrayList<>();
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
        rv_touristscanning.setLayoutManager(staggeredGridLayoutManager);
        touristAdapter = new TouristScanAdapter(view.getContext(), _guestInfoarr,TouristScanningFragment.this);
        rv_touristscanning.setAdapter(touristAdapter);
        bundle= this.getArguments();
        if(bundle==null){
            callcardreading();
        }else{
            //get tag
            final Handler cwjHandler = new Handler();
            Gson gson = new Gson();
             _opd = gson.fromJson(getArguments().getString("form"), operationData.class);

            final Runnable mUpdateResults = new Runnable() {
                public void run() {
                    updateUI();
                    if(objmsg!=null){
                        try {
                            mainactivie.ShowMessage(objmsg.getString("message").toString(),2);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            };
            new Thread(new Runnable() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void run() {
                    try {
                        //sp_gettouristdetailbyid
                        getalltouristbyid(view);
                        cwjHandler.post(mUpdateResults);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }).start();


        }
        return view;
    }

    public void getalltouristbyid(View v){
        try {
            JSONObject obj = new JSONObject();
            JSONObject objdata = new JSONObject();
            objmsg=null;
            if(mainactivie._guestInfo.size()==0) {
                obj.put("requestfor", "getalltouristbyid");
            }else{
                obj.put("requestfor", "getalltouristbyidwithvaildation");
                objdata.put("id", _opd.getId());
            }
            objdata.put("rfid", _opd.getReceivedata());
            objdata.put("flag", "validatetourist");

            obj.put("data", objdata);
            String strResult = mainactivie.Constantvariable.funPostData(obj, "user.php").toString();
            Gson gson = new Gson();
            JSONObject objJsonObject = new JSONObject(strResult);
            if (objJsonObject.get("resultkey").equals(1)){
                _guestInfoarr=new ArrayList<>();

                JSONArray objarr=objJsonObject.getJSONArray("resultvalue");
                JSONArray Objtouristarr=objarr.getJSONObject(0).getJSONArray("tourist");
                if(mainactivie._guestInfo.size()==0){
                    for (int i = 0; i < Objtouristarr.length(); i++) {
                        JSONObject objguestInfo=Objtouristarr.getJSONObject(i);
                        guestInfo _guestInfo=new guestInfo();
                        // if(objguestInfo.getString("rfid").equals("null"))
                        //    {
                        _guestInfo.setId(objguestInfo.getInt("touristid"));
                        _guestInfo.setGender(objguestInfo.getString("gender").equals("0")==true?"M":"F");
                        _guestInfo.setAge(0);
                        _guestInfo.setName(objguestInfo.getString("firstname")+" "+objguestInfo.getString("lastname"));
                        _guestInfo.setIstouristscan(false);
                        _guestInfo.setRfid(objguestInfo.getString("rfid"));
                        _guestInfoarr.add(_guestInfo);

                        //  }


                    }
                    if(_guestInfoarr.size()>0){
                        mainactivie._touristscandata.addAll(_guestInfoarr);
                        for (int i = 0; i < _guestInfoarr.size(); i++) {
                            guestInfo objguest=_guestInfoarr.get(i);
                            if(objguest.getRfid().equals(_opd.getReceivedata())){
                                _guestInfoarr.remove(i);
                                break;
                            }
                        }

                    }
                    mainactivie._guestInfo.addAll(_guestInfoarr);

                }else{
                   Integer rowid =objarr.getJSONObject(0).getInt("rowid");
                   if(rowid>0){
                       String s="";
                       _guestInfoarr=mainactivie._guestInfo;
                       for (int i = 0; i < _guestInfoarr.size(); i++) {
                           guestInfo objguest=_guestInfoarr.get(i);
                           if(objguest.getRfid().equals(_opd.getReceivedata())){
                               _guestInfoarr.remove(i);
                               break;
                           }
                       }
                       mainactivie._guestInfo=_guestInfoarr;
                   }else{
                       objmsg=new JSONObject();
                       _guestInfoarr=mainactivie._guestInfo;
                       objmsg.put("message",objarr.getJSONObject(0).getString("errmsg"));
                       objmsg.put("type",2);
                   }

                }




            }else{
                mainactivie.ShowMessage(objJsonObject.getString("resultvalue"),2);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void updateUI(){
        touristAdapter.updateList(_guestInfoarr);
    }

    public void callcardreading(){
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        Bundle bundle = new Bundle();
        operationData opd=new operationData();
        opd.setFromname("touristscanning");
        Gson gson=new Gson();
        bundle.putString("form", gson.toJson(opd));
        CardReadingFragment cf=new CardReadingFragment();
        cf.setArguments(bundle);
        fragmentTransaction.replace(R.id.container,cf,"touristscanning" );
        fragmentTransaction.commit();
    }
    public void  marktouristarrive(guestInfo model){
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        Bundle bundle = new Bundle();
        operationData opd=new operationData();
        opd.setFromname("touristscanning");
        opd.setId(model.getId());
        opd.setName(model.getName());
        Gson gson=new Gson();
        bundle.putString("form", gson.toJson(opd));
        CardReadingFragment cf=new CardReadingFragment();
        cf.setArguments(bundle);
        fragmentTransaction.replace(R.id.container,cf,"touristscanning" );
        fragmentTransaction.commit();
    }
    @Override
    public void onClick(View v) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        switch (v.getId()) {

            case R.id.imv_touristscanback:
                Fragment fragment = getFragmentManager().findFragmentByTag("starttrip");
                if(fragment != null)
                    getFragmentManager().beginTransaction().remove(fragment).commit();
                fragmentTransaction.replace(R.id.container, new StartTripFragment(),"starttrip");
                fragmentTransaction.commit();
                break;
            case R.id.btn_scantourist:
                callcardreading();
                break;
            case R.id.btn_starttrip:
                new Thread(new Runnable() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void run() {

                        try {
                            JSONObject obj = new JSONObject();
                            JSONObject objdata = new JSONObject();
                            JSONArray objtouristarr = new JSONArray();
                            for (int i = 0; i <mainactivie._touristscandata.size() ; i++) {
                                JSONObject objtourist=new JSONObject();
                                guestInfo guestInfo =mainactivie._touristscandata.get(i);
                                objtourist.put("touristid",guestInfo.getId());
                                objtouristarr.put(objtourist);
                            }
                            ArrayList<device> devices=mainactivie.mydb.getdevicedetail();

                            obj.put("requestfor", "save_starttrip");
                            objdata.put("boatid",mainactivie._userSelectedTripdData.getBoatId());
                            objdata.put("captainid", mainactivie._userSelectedTripdData.getCaptainId());
                            objdata.put("touristdata", objtouristarr);
                            objdata.put("port",devices.get(0).getPort().equals("Gigantes Start")==true?1:0);

                            obj.put("data", objdata);

                            String strResult = mainactivie.Constantvariable.funPostData(obj, "tour.php").toString();
                            //{"resultkey":1,"resultvalue":[{"id":"55","errflag":"0"}]}
                            Gson gson = new Gson();
                            JSONObject objJsonObject = new JSONObject(strResult);
                            if (objJsonObject.get("resultkey").equals(1)) {
                                JSONArray objJsonArr = objJsonObject.getJSONArray("resultvalue");
                                String tripid=objJsonArr.getJSONObject(0).getString("id");
                                mainactivie._userSelectedTripdData.setTripid(Integer.parseInt(tripid));
                                setprintdata(tripid);


                            }


                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }).start();
                break;
        }
    }
    public void setprintdata(String id){
        mainactivie._printData=new ArrayList<>();
        try {
            JSONObject obj=new JSONObject();
            JSONObject objdata=new JSONObject();
            obj.put("requestfor","gettripdatabyid");
            objdata.put("flag","a");
            objdata.put("id",id);
            obj.put("data",objdata);
            String strResult = mainactivie.Constantvariable.funPostData(obj,"tour.php").toString();

            Gson gson = new Gson();
            JSONObject objJsonObject = new JSONObject(strResult);
            if (objJsonObject.get("resultkey").equals(1)){
                JSONArray objarr=objJsonObject.getJSONArray("resultvalue");
                ArrayList<printData> _printDataarr=new ArrayList<>();
                for (int i = 0; i < objarr.length(); i++) {
                    JSONObject objprintdata=objarr.getJSONObject(i);
                    printData _printData=new printData();
                    _printData.setId(objprintdata.getInt("id"));
                    _printData.setCreatedon(objprintdata.getString("createdon"));
                    _printData.setUsername(objprintdata.getString("username"));
                    _printData.setDescription(objprintdata.getString("description"));
                    _printData.setStartPort(objprintdata.getString("startport"));
                    _printData.setEndPort(objprintdata.getString("endport"));
                    _printData.setEndOn(objprintdata.getString("endtripon"));
                    _printDataarr.add(_printData);
                }
                callprint(_printDataarr.get(0));

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void callprint(final printData model){
        FragmentManager fm = getFragmentManager();
        final FragmentTransaction fragmentTransaction = fm.beginTransaction();
        mainactivie.printtripdata=new tripData();
        mainactivie.printtripdata.setBoatName(model.getDescription());
        mainactivie.printtripdata.setDateTime(model.getCreatedon());
        mainactivie.printtripdata.setFormPort(model.getStartPort());
        mainactivie.printtripdata.setToPort(model.getEndPort());
        mainactivie.printtripdata.setEndDateTime(model.getEndOn());
        if(model.getStartPort().equals("Gigantes Start")){
            mainactivie.printtripdata.setToPort("Gigantes End");
        }else{
            mainactivie.printtripdata.setToPort("Gigantes Start");
        }
        if(model.getEndPort().trim().equals("")){
            mainactivie.printtripdata.setIstripended(false);
        }else{
            mainactivie.printtripdata.setIstripended(true);
        }

        mainactivie.printtripdata.setBoatCaptain(model.getUsername());
        mainactivie.printtripdata.setId(model.getId());
        try {
            new Thread(new Runnable() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void run() {
                    try {
                        JSONObject obj=new JSONObject();
                        JSONObject objdata=new JSONObject();
                        obj.put("requestfor","gettouristdetailbyid");
                        objdata.put("tripid",mainactivie.printtripdata.getId());
                        obj.put("data",objdata);
                        String strResult = mainactivie.Constantvariable.funPostData(obj,"tour.php").toString();

                        Gson gson = new Gson();
                        JSONObject objJsonObject = new JSONObject(strResult);
                        if (objJsonObject.get("resultkey").equals(1)){
                            ArrayList<guestInfo> guestInfo=new ArrayList<>();
                            JSONArray objarr=objJsonObject.getJSONArray("resultvalue");
                            for (int i = 0; i < objarr.length(); i++) {
                                JSONObject objguestinfo=objarr.getJSONObject(i);
                                guestInfo _guestInfo=new guestInfo();
                                _guestInfo.setName(objguestinfo.getString("firstname")+" "+ objguestinfo.getString("lastname"));
                                _guestInfo.setAddress(objguestinfo.getString("address1"));
                                _guestInfo.setAge(objguestinfo.getInt("age"));
                                _guestInfo.setGender(objguestinfo.getString("gender"));
                                _guestInfo.setId(objguestinfo.getInt("id"));
                                guestInfo.add(_guestInfo);
                            }
                            mainactivie.printtripdata.setGuest(guestInfo);
                            PrintReceiptFragment cf=new PrintReceiptFragment();
                            cf.setArguments(bundle);
                            fragmentTransaction.replace(R.id.container,cf,"Landing" );
                            fragmentTransaction.commit();
                        }

                    }catch (Exception ex){

                    }
                }
            }).start();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}