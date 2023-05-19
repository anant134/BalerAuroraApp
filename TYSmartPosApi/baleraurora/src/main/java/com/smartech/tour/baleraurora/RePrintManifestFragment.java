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
import android.widget.ImageView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.gson.Gson;
import com.smartech.tour.baleraurora.adapter.PrintAdapter;
import com.smartech.tour.baleraurora.module.guestInfo;
import com.smartech.tour.baleraurora.module.operationData;
import com.smartech.tour.baleraurora.module.printData;
import com.smartech.tour.baleraurora.module.tripData;
import com.whty.smartpos.gigantestourbooking.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RePrintManifestFragment extends Fragment implements View.OnClickListener {
    ArrayList<printData> printDataArrayList;
    MainActivity mainactivie;
    RecyclerView rv_reprint;
    PrintAdapter printAdapter;
    ImageView imv_reprintback;
    Bundle bundle;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view= inflater.inflate(R.layout.fragment_re_print_manifest, container, false);
        mainactivie=((MainActivity) getActivity());
        rv_reprint=view.findViewById(R.id.rv_reprint);
        imv_reprintback=view.findViewById(R.id.imv_reprintback);
        imv_reprintback.setOnClickListener(this);
        printDataArrayList =new ArrayList<>();
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
        rv_reprint.setLayoutManager(staggeredGridLayoutManager);
        bundle= this.getArguments();
        if(bundle==null){
            final Handler cwjHandler = new Handler();

            final Runnable mUpdateResults = new Runnable() {
                public void run() {
                    updateUI();
                }
            };
            new Thread(new Runnable() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void run() {
                    try {
                        getalltripdata(view);
                        cwjHandler.post(mUpdateResults);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }).start();
        }else{
            //register captain here
            //call api and show wait sign

            mainactivie.ShowMessage("Captain Registered successfully",1);
        }

        return  view;
    }

    public void getalltripdata(View v){

        mainactivie._printData=new ArrayList<>();
        try {
            JSONObject obj=new JSONObject();
            JSONObject objdata=new JSONObject();
            obj.put("requestfor","getalltrip");
            objdata.put("flag","a");
            objdata.put("port","1");
            obj.put("data",objdata);
            String strResult = mainactivie.Constantvariable.funPostData(obj,"tour.php").toString();

            Gson gson = new Gson();
            JSONObject objJsonObject = new JSONObject(strResult);
            if (objJsonObject.get("resultkey").equals(1)){
                JSONArray objarr=objJsonObject.getJSONArray("resultvalue");
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
                    printDataArrayList.add(_printData);
                }

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        printAdapter = new PrintAdapter(v.getContext(), printDataArrayList,RePrintManifestFragment.this);
       // rv_reprint.setAdapter(printAdapter);
        // LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false);

    }


    private void updateUI() {

        rv_reprint.setAdapter(printAdapter);
    }

    public  void  callprint(final printData model){

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
                            Bundle bundle = new Bundle();
                            operationData opd=new operationData();
                            opd.setFromname("reprint");
                            bundle.putString("form", gson.toJson(opd));
                            cf.setArguments(bundle);
                            fragmentTransaction.replace(R.id.container,cf,"reprint" );
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
    @Override
    public void onClick(View v) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        switch (v.getId()) {
            case R.id.imv_reprintback:
                Fragment fragment = getFragmentManager().findFragmentByTag("Landing");
                if(fragment != null)
                    getFragmentManager().beginTransaction().remove(fragment).commit();
                fragmentTransaction.replace(R.id.container, new LandingFragment(),"Landing");
                fragmentTransaction.commit();
                break;
        }
    }
}