package com.smartech.tour.gigantestourbooking;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.gson.Gson;
import com.smartech.tour.gigantestourbooking.adapter.CagsiayExitTouristAdapter;
import com.smartech.tour.gigantestourbooking.module.card;
import com.smartech.tour.gigantestourbooking.module.guestInfo;
import com.smartech.tour.gigantestourbooking.module.operationData;
import com.whty.smartpos.gigantestourbooking.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CagsiayExitFragment extends Fragment  implements View.OnClickListener{
    Button btn_cagsiayexitregscan;
    MainActivity mainactivie;
    ImageView imv_cagsiayexitback;
    Bundle bundle;
    ArrayAdapter adapter;
    RecyclerView rv_cagsiayexittouristlist;
    ArrayList<guestInfo> _guestInfoarr;
    ArrayList<String> lst;
    CagsiayExitTouristAdapter touristAdapter;
    JSONObject objmsg=new JSONObject();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view= inflater.inflate(R.layout.fragment_cagsiay_exit , container, false);
        rv_cagsiayexittouristlist=view.findViewById(R.id.rv_cagsiayexittouristlist);
        _guestInfoarr=new ArrayList<>();
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
        rv_cagsiayexittouristlist.setLayoutManager(staggeredGridLayoutManager);
        touristAdapter = new CagsiayExitTouristAdapter(view.getContext(), _guestInfoarr,CagsiayExitFragment.this);
        rv_cagsiayexittouristlist.setAdapter(touristAdapter);

        imv_cagsiayexitback=view.findViewById(R.id.imv_cagsiayexitback);
        imv_cagsiayexitback.setOnClickListener(this);
        btn_cagsiayexitregscan=view.findViewById(R.id.btn_cagsiayexitregscan);
        btn_cagsiayexitregscan.setOnClickListener(this);
        lst=new ArrayList<>();
        adapter = new ArrayAdapter<String>(view.getContext(), R.layout.activity_cardlistview,
                R.id.lbl_carddetails, lst);
        mainactivie=((MainActivity) getActivity());
        mainactivie.progressDialog = new ProgressDialog(mainactivie);
        ;
       /* ListView listView = (ListView)view.findViewById(R.id.ls_cagsiayentryreglist);
        listView.setAdapter(adapter);*/
        bundle= this.getArguments();

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final View view = getView();
        if (view != null){
            getView().post(new Runnable() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void run() {

                    try {
                        if(bundle!=null){
                            final Handler cwjHandler = new Handler();

                            final Runnable mUpdateResults = new Runnable() {
                                @RequiresApi(api = Build.VERSION_CODES.M)
                                public void run() {
                                    // updateUI();

                                }
                            };
                            new Thread() {
                                @RequiresApi(api = Build.VERSION_CODES.M)
                                @Override
                                public void run() {
                                    super.run();

                                    ArrayList<card> _cardarr=mainactivie._card;
                                    card data = new card();
                                    Gson gson = new Gson();
                                    operationData _opd = gson.fromJson(getArguments().getString("form"), operationData.class);
                                    data.setRfid(_opd.getReceivedata());

                                    //SaveCagsiaytouristLog(_opd.getReceivedata());
                                    TouristExitLog(_opd.getReceivedata());

                                    //   cwjHandler.post(mUpdateResults);
                                    // cwjHandler.post(mUpdateResults);
                                }
                            }.start();


                            //mainactivie.progressDialog.dismiss();
                        }else{
                            mainactivie._card=new ArrayList<>();
                            // mainactivie._guestInfo=new ArrayList<>();
                            //loaddata();
                            GetAllTouristData();
                        }
                    }catch (Exception e){
                        mainactivie.ShowMessage(e.getMessage().toString(),3);
                        mainactivie.progressDialog.dismiss();
                    }

                }
            });
        }


    }
    public void TouristExitLog(final String rfid){
        mainactivie.runOnUiThread(new Runnable() {
            public void run() {
                final Handler cwjHandler = new Handler();

                final Runnable mUpdateResults = new Runnable() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    public void run() {
                        if(objmsg!=null) {


                            mainactivie.ShowMessage("Tourist Exit  successfully.", 1);
                            updateUI(rfid);
                            //btn_cagsiayentryregscan.performClick();

                        }
                    }
                };
                new Thread(new Runnable() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void run() {
                        try {
                            try{
                                JSONObject obj = new JSONObject();
                                JSONObject objdata = new JSONObject();
                                obj.put("requestfor", "savecagsiayexitlog");
                                objdata.put("rfid_id", rfid);
                                objdata.put("flag", "i");

                                obj.put("data", objdata);
                                String strResult = mainactivie.Constantvariable.funPostData(obj, "Cagsiay.php").toString();
                                Gson gson = new Gson();
                                JSONObject objmsg=new JSONObject();
                                try {
                                    objmsg.put("rfid",rfid);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                JSONObject objJsonObject = new JSONObject(strResult);
                                if(objJsonObject.getInt("resultkey")==1){
                                    JSONArray objarr=objJsonObject.getJSONArray("resultvalue");
                                    if(Integer.parseInt(objarr.getJSONObject(0).getString("rowid"))>0){
                                        cwjHandler.post(mUpdateResults);
                                    }else{
                                        mainactivie.ShowMessage("This tag already exit.",3);
                                    }
                                }else{
                                    mainactivie.ShowMessage("Please contact to administrator",3);
                                }

                            }catch (Exception e){
                                mainactivie.ShowMessage(e.getMessage().toString(),3);

                            }

                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }).start();

            }
        });

    }
    public void callCardreading(guestInfo tourist){
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        Bundle bundle = new Bundle();
        operationData opd=new operationData();
        opd.setFromname("cagsiayexit");
        opd.setId(tourist.getId());
        opd.setName(tourist.getName());

        Gson gson=new Gson();
        bundle.putString("form", gson.toJson(opd));

        CardReadingFragment cf=new CardReadingFragment();
        cf.setArguments(bundle);
        fragmentTransaction.replace(R.id.container,cf,"cagsiayexit" );
        fragmentTransaction.commit();


    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void GetAllTouristData(){
        //final String  loctourist=touristid;
        mainactivie.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                try{
                    final Handler cwjHandler = new Handler();
                    final Runnable mUpdateResults = new Runnable() {
                        @RequiresApi(api = Build.VERSION_CODES.M)
                        public void run() {
                            updateTouristUI();
                            if(objmsg!=null){
                                try {
                                    mainactivie.ShowMessage(objmsg.getString("message").toString(),objmsg.getInt("messagetype"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    };
                    new Thread() {
                        @RequiresApi(api = Build.VERSION_CODES.M)
                        @Override
                        public void run() {
                            try {
                                JSONObject obj = new JSONObject();
                                JSONObject objdata = new JSONObject();
                                obj.put("requestfor", "getAllcagsiaytourist");
                                // objdata.put("id", loctourist);
                                obj.put("data", objdata);
                                String strResult = mainactivie.Constantvariable.funPostData(obj, "Cagsiay.php").toString();
                                Gson gson = new Gson();
                                JSONObject objJsonObject = new JSONObject(strResult);
                                if (objJsonObject.get("resultkey").equals(1)){
                                    _guestInfoarr=new ArrayList<>();
                                    mainactivie._guestInfo=new ArrayList<>();
                                    JSONArray objarr=objJsonObject.getJSONArray("resultvalue");
                                    for (int i = 0; i < objarr.length(); i++) {
                                        JSONObject objguestInfo=objarr.getJSONObject(i);


                                        if(mainactivie._guestInfo.size()>0){
                                            boolean isexist=false;
                                            for (int gi = 0; gi < mainactivie._guestInfo.size(); gi++) {
                                                if(mainactivie._guestInfo.get(gi).getId().equals(objguestInfo.getInt("id"))){
                                                    isexist=true;
                                                    break;
                                                }
                                            }
                                            if(isexist==false){
                                                guestInfo _guestInfo=new guestInfo();
                                                //   if(objguestInfo.getString("rfid").equals("null")){
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
                                        }else{
                                            guestInfo _guestInfo=new guestInfo();
                                            //   if(objguestInfo.getString("rfid").equals("null")){
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



                                        //   }
                                        cwjHandler.post(mUpdateResults);

                                    }


                                }else{

                                    objmsg=new JSONObject();
                                    objmsg.put("message","Tag Alredy made entry");
                                    objmsg.put("type",2);

                                }
                            }catch (Exception ex){
                                ex.printStackTrace();
                            }
                        }
                    }.start();

                }catch (Exception ex){

                }
            }
        });


    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void updateUI(String _loctourist) {
        GetAllTouristData();
        //touristAdapter.updateList(mainactivie._guestInfo);
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void updateTouristUI(){
        touristAdapter.updateList(mainactivie._guestInfo);
    }


    @Override
    public void onClick(View v) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        switch (v.getId()) {
            case R.id.imv_cagsiayexitback:
                Fragment fragment = getFragmentManager().findFragmentByTag("cagisylog");
                if(fragment != null)
                    getFragmentManager().beginTransaction().remove(fragment).commit();
                fragmentTransaction.replace(R.id.container, new CagsiayLogFragment(),"cagisylog");
                fragmentTransaction.commit();
                break;
            case R.id.btn_cagsiayexitregscan:
                Bundle bundle = new Bundle();
                operationData opd=new operationData();
                opd.setFromname("cagsiayexit");


                Gson gson=new Gson();
                bundle.putString("form", gson.toJson(opd));

                CardReadingFragment cf=new CardReadingFragment();
                cf.setArguments(bundle);
                fragmentTransaction.replace(R.id.container,cf,"cagsiayexit" );
                fragmentTransaction.commit();
                break;
        }
    }
}