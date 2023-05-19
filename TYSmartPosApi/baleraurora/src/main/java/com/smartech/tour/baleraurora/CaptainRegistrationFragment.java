package com.smartech.tour.baleraurora;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Build;
import android.os.Bundle;

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
import com.smartech.tour.baleraurora.adapter.CaptainAdapter;
import com.smartech.tour.baleraurora.module.device;
import com.smartech.tour.baleraurora.module.operationData;
import com.smartech.tour.baleraurora.module.userinfo;
import com.whty.smartpos.gigantestourbooking.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class CaptainRegistrationFragment extends Fragment implements View.OnClickListener {
    RecyclerView rv_captainreg;
    ArrayList<userinfo> userinfoArrayList;
    CaptainAdapter captainAdapter;
    MainActivity mainactivie;
    ImageView imv_captainregback;
    Bundle bundle;
    View view=null;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_captain_registration, container, false);
        mainactivie=((MainActivity) getActivity());
        rv_captainreg=view.findViewById(R.id.rv_captainreg);
        imv_captainregback=view.findViewById(R.id.imv_captainregback);
        imv_captainregback.setOnClickListener(this);
        userinfoArrayList =new ArrayList<>();
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
        rv_captainreg.setLayoutManager(staggeredGridLayoutManager);
        //get caaptain

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
                        getallunassigncaptain(view);
                        cwjHandler.post(mUpdateResults);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }).start();
        }else{
            //register captain here
            //call api and show wait sign
            final Handler cwjHandler = new Handler();
            final String[] message = {""};
            final Integer[] messagetype = {0};
            final Runnable mUpdateResults = new Runnable() {
                public void run() {
                    updateUI();
                    mainactivie.ShowMessage(message[0],messagetype[0]);
                }
            };
            new Thread(new Runnable() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void run() {
                    try {
                        Gson gson = new Gson();
                        final operationData _opd = gson.fromJson(getArguments().getString("form"), operationData.class);
                        try{
                            ArrayList<device> devices=mainactivie.mydb.getdevicedetail();
                            JSONObject obj = new JSONObject();
                            JSONObject objdata = new JSONObject();
                            obj.put("requestfor", "boatcaptainmapping");
                            objdata.put("userid", _opd.getId());
                            objdata.put("rfid", _opd.getReceivedata());
                            objdata.put("name", _opd.getName());
                            objdata.put("flag", "i");
                            objdata.put("deviceid", devices.get(0).getDeviceID());
                            objdata.put("portofassign", devices.get(0).getPort().equals("Gigantes Start")==true?0:1);
                            obj.put("data", objdata);
                            String strResult = mainactivie.Constantvariable.funPostData(obj, "mapping.php").toString();

                            JSONObject objJsonObject = new JSONObject(strResult);
                            if (objJsonObject.get("resultkey").equals(1)){
                                //{"resultkey":1,"resultvalue":[{"rowid":"-3","errflag":"3"}]}
                                Integer rowid =objJsonObject.getJSONArray("resultvalue").getJSONObject(0).getInt("rowid");
                                if(rowid>0){
                                    getallunassigncaptain(view);
                                    message[0] ="Captain Registered successfully";
                                    messagetype[0]=1;
                                    cwjHandler.post(mUpdateResults);
                                }else{
                                    if(objJsonObject.getJSONArray("resultvalue").getJSONObject(0).getString("errflag").equals("2")){
                                        //mainactivie.ShowMessage("This tag already register.",3);
                                        getallunassigncaptain(view);
                                        message[0] ="This tag already register.";
                                        messagetype[0]=3;
                                    }else if(objJsonObject.getJSONArray("resultvalue").getJSONObject(0).getString("errflag").equals("1")){
                                       // mainactivie.ShowMessage("This tag is not register.",3);
                                        getallunassigncaptain(view);
                                        message[0] ="This tag is not register.";
                                        messagetype[0]=3;
                                    }
                                    cwjHandler.post(mUpdateResults);
                                }
                            }


                        }catch (Exception ex){

                        }


                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }).start();


        }



        return  view;
    }

    public void getallunassigncaptain(View v){

        userinfoArrayList=new ArrayList<>();
        try {
            JSONObject obj=new JSONObject();
            JSONObject objdata=new JSONObject();
            obj.put("requestfor","getuser");
            objdata.put("flag","byboatcaptian");
            obj.put("data",objdata);
            String strResult = mainactivie.Constantvariable.funPostData(obj,"user.php").toString();

            Gson gson = new Gson();
            JSONObject objJsonObject = new JSONObject(strResult);
            if (objJsonObject.get("resultkey").equals(1)){
                JSONArray objarr=objJsonObject.getJSONArray("resultvalue");
                for (int i = 0; i < objarr.length(); i++) {
                    JSONObject objusers=objarr.getJSONObject(i);
                    userinfo _userinfo=new userinfo();
                    _userinfo.setId(objusers.getInt("id"));
                    _userinfo.setUserName(objusers.getString("username"));
                    _userinfo.setEmail(objusers.getString("email"));
                    _userinfo.setRoleid(objusers.getInt("roleid"));
                    _userinfo.setRolename(objusers.getString("rolename"));
                    _userinfo.setIsactive(objusers.getString("isactive").equals("1"));
                    _userinfo.setIsmapped(objusers.getString("ismapped").equals("1"));
                    userinfoArrayList.add(_userinfo);
                }

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        captainAdapter = new CaptainAdapter(v.getContext(), userinfoArrayList,CaptainRegistrationFragment.this);
        // LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false);

    }

    public static Object fromJson(String jsonString, Type type) {
        return new Gson().fromJson(jsonString, type);
    }

    private void updateUI() {

        rv_captainreg.setAdapter(captainAdapter);
    }

    public void callCardreading(userinfo user){
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        Bundle bundle = new Bundle();
        operationData opd=new operationData();
        opd.setFromname("captainregistration");
        opd.setId(user.getId());
        opd.setName(user.getUserName());

        Gson gson=new Gson();
        bundle.putString("form", gson.toJson(opd));

        CardReadingFragment cf=new CardReadingFragment();
        cf.setArguments(bundle);
        fragmentTransaction.replace(R.id.container,cf,"cardregistration" );
        fragmentTransaction.commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.imv_captainregback:
                FragmentManager fm = getFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                Fragment fragment = getFragmentManager().findFragmentByTag("Landing");
                if(fragment != null)
                    getFragmentManager().beginTransaction().remove(fragment).commit();
                fragmentTransaction.replace(R.id.container, new LandingFragment(),"Landing");
                fragmentTransaction.commit();
                break;
        }
    }
}