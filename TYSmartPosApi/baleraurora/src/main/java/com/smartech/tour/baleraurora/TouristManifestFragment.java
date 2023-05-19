package com.smartech.tour.baleraurora;

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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.gson.Gson;
import com.smartech.tour.baleraurora.adapter.TestCardAdapter;
import com.smartech.tour.baleraurora.adapter.TouristActivityAdapter;
import com.smartech.tour.baleraurora.adapter.TouristScanAdapter;
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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TouristManifestFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TouristManifestFragment extends Fragment implements  View.OnClickListener {

    Button btn_TMscan;
    private RecyclerView courseRV;
    ArrayList<guestInfo> _guestInfoarr;
    Bundle bundle;
    MainActivity mainactivie;
    ImageView imv_TMback;
    JSONObject ovjmsg=new JSONObject();

    TextView txtdeviceid;
    ArrayList<cardState> cardStateArrayList;
    TestCardAdapter cardStateAdapter;
    ArrayList<cardState> _cardStatesarr;
    operationData _opd =new operationData();
    public TouristManifestFragment() {
        // Required empty public constructor
    }

    public static TouristManifestFragment newInstance(String param1, String param2) {
        TouristManifestFragment fragment = new TouristManifestFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
          /*  mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);*/
        }
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final View view = getView();
        if(view != null) {
            try {
                getView().post(new Runnable() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void run() {
                        if(bundle!=null){


                            cardStateArrayList=new ArrayList<>();
                            final JSONObject objmsg=new JSONObject();
                            Gson gson = new Gson();
                             _opd  = gson.fromJson(getArguments().getString("form"), operationData.class);
                            final Handler cwjHandler = new Handler();

                            final Runnable mUpdateResults = new Runnable() {
                                @RequiresApi(api = Build.VERSION_CODES.M)
                                public void run() {
                                    try {
                                      //  mainactivie.ShowMessage(ovjmsg.getString("message"),ovjmsg.getInt("try"));
                                        if(ovjmsg.length() != 0){
                                            if(ovjmsg.getInt("try")==0){
                                                mainactivie.ShowMessage("Tourist mark as arrive",1);
                                                updateUIForTrousit(view);
                                            }else{
                                                mainactivie.ShowMessage(ovjmsg.getString("message"),ovjmsg.getInt("try"));
                                            }


                                        }else{
                                            mainactivie.ShowMessage("Contact administrator!",ovjmsg.getInt("try"));
                                        }

                                    } catch (Exception e) {
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
                                        if(!_opd.getReceivedata().isEmpty()){
                                            JSONObject obj = new JSONObject();
                                            JSONObject objdata = new JSONObject();
                                            ovjmsg = new JSONObject();
                                            obj.put("requestfor", "touristscaned");
                                            objdata.put("rfid", _opd.getReceivedata());
                                            objdata.put("flag", "validatetourist");
                                            objdata.put("deviceid", mainactivie.Constantvariable.deviceid);
                                            obj.put("data", objdata);
                                            String strResult = mainactivie.Constantvariable.funPostData(obj, "user.php").toString();
                                            Gson gson = new Gson();
                                            final JSONObject objJsonObject = new JSONObject(strResult);
                                            if (objJsonObject.get("resultkey").equals(1)){
                                                _guestInfoarr=new ArrayList<>();

                                                JSONArray objarr=objJsonObject.getJSONArray("resultvalue");
                                                JSONArray Objtouristarr=objarr.getJSONObject(0).getJSONArray("tourist");
                                                if(objarr.getJSONObject(0).getString("errflag").equals("0")){
                                                    for (int i = 0; i < Objtouristarr.length(); i++) {
                                                        JSONObject objguestInfo=Objtouristarr.getJSONObject(i);
                                                        guestInfo _guestInfo=new guestInfo();
                                                        _guestInfo.setId(objguestInfo.getInt("touristid"));
                                                        _guestInfo.setGender(objguestInfo.getString("gender").equals("0")==true?"M":"F");
                                                        _guestInfo.setAddress("");
                                                        _guestInfo.setAge(0);
                                                        _guestInfo.setName(objguestInfo.getString("firstname")+" "+objguestInfo.getString("lastname"));
                                                        cardState cd=new cardState();
                                                        cd.setGuestInfo(_guestInfo);
                                                        cd.setCardState(_opd.getReceivedata());
                                                        cardStateArrayList.add(cd);
                                                    }
                                                    ovjmsg.put("message","Tourist mark as arrived.");
                                                    ovjmsg.put("try",0);
                                                    cwjHandler.post(mUpdateResults);
                                                }else{
                                                    final String message=objarr.getJSONObject(0).getString("errmsg");
                                                    ovjmsg.put("message",message);
                                                    ovjmsg.put("try",2);
                                                    cwjHandler.post(mUpdateResults);
                                                }
                                            }
                                        }else{

                                            ovjmsg.put("message","Please place tag again!.");
                                            ovjmsg.put("try",2);
                                            cwjHandler.post(mUpdateResults);
                                           // objmsg.put("","");
                                        }

                                    }catch (Exception e){

                                    }
                                }
                            }.start();

                        }
                    }
                });

            }catch (Exception e){
               // mainactivie.ShowMessage(e.getMessage().toString(),3);
                // mainactivie.progressDialog.dismiss();
            }

        }else{

        }
    }
    public void  updateUIForTrousit(View v){
        cardStateAdapter.updateList(cardStateArrayList);
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    private guestInfo getalltouristbyid(String id){
       // boolean res=false;
        guestInfo _guestInfo=new guestInfo();
        try {

            JSONObject obj = new JSONObject();
            JSONObject objdata = new JSONObject();
            ovjmsg = null;
            obj.put("requestfor", "touristscaned");
            objdata.put("rfid", id);
            objdata.put("flag", "validatetourist");
            objdata.put("deviceid", mainactivie.Constantvariable.deviceid);
            obj.put("data", objdata);
            String strResult = mainactivie.Constantvariable.funPostData(obj, "user.php").toString();
            Gson gson = new Gson();
            final JSONObject objJsonObject = new JSONObject(strResult);

            if (objJsonObject.get("resultkey").equals(1)){
                _guestInfoarr=new ArrayList<>();

                JSONArray objarr=objJsonObject.getJSONArray("resultvalue");
                JSONArray Objtouristarr=objarr.getJSONObject(0).getJSONArray("tourist");
                if(objarr.getJSONObject(0).getString("errflag").equals("0")){
                    for (int i = 0; i < Objtouristarr.length(); i++) {
                        JSONObject objguestInfo=Objtouristarr.getJSONObject(i);

                        _guestInfo.setId(objguestInfo.getInt("id"));
                        _guestInfo.setGender(objguestInfo.getString("gender").equals("0")==true?"M":"F");
                        _guestInfo.setAddress(objguestInfo.getString("address1"));
                        _guestInfo.setAge(objguestInfo.getInt("age"));
                        _guestInfo.setName(objguestInfo.getString("firstname")+" "+objguestInfo.getString("lastname"));
                        /*guestInfoArrayList.add(_guestInfo);
                        updateRecycleUI();*/
                      //  mainactivie._guestInfo.add(_guestInfo);
                        //return true;
                    }
                  //  cwjHandlerinner.post(mUpdateResultsinner);


                }else{
                    final String message=objarr.getJSONObject(0).getString("errmsg");
                   /* ovjmsg=new JSONObject();
                    ovjmsg.put("message",message);
                    ovjmsg.put("type",2);*/
                    mainactivie.runOnUiThread(new Runnable() {
                        public void run() {
                            mainactivie.ShowMessage(message,2);
                        }
                    });
                  //  return false;

                }
            }else{
                mainactivie.ShowMessage(objJsonObject.getString("resultvalue"),2);
                //return false;
            }

            //return  res;
        } catch (Exception e) {
           // return  false;
            //e.printStackTrace();
        }
        return _guestInfo;
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view= inflater.inflate(R.layout.fragment_tourist_manifest, container, false);
        mainactivie=((MainActivity) getActivity());
        mainactivie.progressDialog = new ProgressDialog(mainactivie);
        btn_TMscan=(Button)view.findViewById(R.id.btn_TMscan);
        btn_TMscan.setOnClickListener(this);
        imv_TMback=view.findViewById(R.id.imv_TMback);
        imv_TMback.setOnClickListener(this);
        courseRV = view.findViewById(R.id.idRVTMCourse);
        txtdeviceid=(TextView) view.findViewById(R.id.txtdeviceid);
        txtdeviceid.setText("Device ID:"+mainactivie.Constantvariable.deviceid.toString());
     //   touristAdapter = new TouristActivityAdapter(view.getContext(), guestInfoArrayList);
        cardStateArrayList=new ArrayList<>();
        cardStateAdapter = new TestCardAdapter(view.getContext(), cardStateArrayList);
        // LinearLayoutManager linearLayoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);

        // in below two lines we are setting layoutmanager and adapter to our recycler view.
        courseRV.setLayoutManager(staggeredGridLayoutManager);
        courseRV.setAdapter(cardStateAdapter);
        bundle= this.getArguments();
        return view;


    }

    @Override
    public void onClick(View v) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        switch (v.getId()){
            case  R.id.btn_TMscan:
                Bundle bundle = new Bundle();
                operationData opd=new operationData();
                opd.setFromname("touristscan");
                Gson gson=new Gson();
                bundle.putString("form", gson.toJson(opd));

                CardReadingFragment cf=new CardReadingFragment();
                cf.setArguments(bundle);
                fragmentTransaction.replace(R.id.container,cf,"cardregistration" );
                fragmentTransaction.commit();

                break;
            case  R.id.imv_TMback:
                Fragment fragment = getFragmentManager().findFragmentByTag("Landing");
                if(fragment != null)
                    getFragmentManager().beginTransaction().remove(fragment).commit();
                fragmentTransaction.replace(R.id.container, new LandingFragment(),"Landing");
                fragmentTransaction.commit();
                break;
        }
    }
}