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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.gson.Gson;
import com.smartech.tour.gigantestourbooking.adapter.BoatAdapter;
import com.smartech.tour.gigantestourbooking.module.boats;
import com.whty.smartpos.gigantestourbooking.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class StartTripFragment extends Fragment  implements  View.OnClickListener{

    ImageView imv_starttripback;
    Spinner ddl_starttripboatselection;
    MainActivity mainactivie;
    RecyclerView rv_starttripboatselection;
    ArrayList<boats> boatsArrayList;
    BoatAdapter boatAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view= inflater.inflate(R.layout.fragment_start_trip, container, false);
        mainactivie=((MainActivity) getActivity());
        imv_starttripback=view.findViewById(R.id.imv_starttripback);
        imv_starttripback.setOnClickListener(this);
        ddl_starttripboatselection=view.findViewById(R.id.ddl_starttripboatselection);
        rv_starttripboatselection=view.findViewById(R.id.rv_starttripboatselection);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
        rv_starttripboatselection.setLayoutManager(staggeredGridLayoutManager);

        bindddl(view);

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
                    getallboats(view);
                    cwjHandler.post(mUpdateResults);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
        return  view;
    }

    public void bindddl(View v){
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("All");
        arrayList.add("1");
        arrayList.add("2");
        arrayList.add("3");
        arrayList.add("4");
        arrayList.add("5");
        arrayList.add("6");
        arrayList.add("7");
        arrayList.add("8");
        arrayList.add("9");
        arrayList.add("10");
        arrayList.add("11");
        arrayList.add("12");
        arrayList.add("13");
        arrayList.add("14");
        arrayList.add("15");
        arrayList.add("16");
        arrayList.add("17");
        arrayList.add("18");
        arrayList.add("19");
        arrayList.add("20");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(v.getContext(),R.layout.support_simple_spinner_dropdown_item,arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ddl_starttripboatselection.setAdapter(arrayAdapter);
        ddl_starttripboatselection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
    }


    public void getallboats(View v){
        boatsArrayList=new ArrayList<>();
        try {
            JSONObject obj=new JSONObject();
            JSONObject objdata=new JSONObject();
            obj.put("requestfor","getboat");
            objdata.put("flag","actv");
            obj.put("data",objdata);
            String strResult = mainactivie.Constantvariable.funPostData(obj,"boat.php").toString();

            Gson gson = new Gson();
            JSONObject objJsonObject = new JSONObject(strResult);
            if (objJsonObject.get("resultkey").equals(1)){
                JSONArray objarr=objJsonObject.getJSONArray("resultvalue");
                for (int i = 0; i < objarr.length(); i++) {
                    JSONObject objboat=objarr.getJSONObject(i);
                    boats _boatinfo=new boats();
                    _boatinfo.setId(objboat.getInt("id"));
                    _boatinfo.setCapacity(objboat.getInt("capacity"));
                    _boatinfo.setName(objboat.getString("description"));
                    _boatinfo.setOwner(objboat.getString("ownername"));
                    boatsArrayList.add(_boatinfo);
                }

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        boatAdapter = new BoatAdapter(v.getContext(), boatsArrayList,StartTripFragment.this);

    }
    public void callTouristselection(boats model){
        mainactivie._userSelectedTripdData.setBoatId(model.getId());
        mainactivie._guestInfo=new ArrayList<>();
        mainactivie._touristscandata=new ArrayList<>();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.container,new TouristScanningFragment(),"touristscanning" );
        fragmentTransaction.commit();

    }

    private void updateUI() {

        rv_starttripboatselection.setAdapter(boatAdapter);
    }
    @Override
    public void onClick(View v) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();

        switch (v.getId()) {
            case R.id.imv_starttripback:
                Fragment fragment = getFragmentManager().findFragmentByTag("boatmanifest");
                if(fragment != null)
                    getFragmentManager().beginTransaction().remove(fragment).commit();
                fragmentTransaction.replace(R.id.container, new BoatManifestFragment(),"boatmanifest");
                fragmentTransaction.commit();
                break;
        }
    }
}