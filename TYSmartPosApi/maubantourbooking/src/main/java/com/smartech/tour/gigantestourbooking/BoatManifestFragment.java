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

import com.google.gson.Gson;
import com.smartech.tour.gigantestourbooking.module.operationData;
import com.whty.smartpos.gigantestourbooking.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class BoatManifestFragment extends Fragment implements  View.OnClickListener{

    Button btn_BMStarttrip,btn_BMEndtrip;
    ImageView imv_boatmanifestback;
    Bundle bundle;
    MainActivity mainactivie;
    JSONObject objmsg=new JSONObject();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_boat_manifest, container, false);
        mainactivie=((MainActivity) getActivity());
        imv_boatmanifestback=view.findViewById(R.id.imv_boatmanifestback);
        imv_boatmanifestback.setOnClickListener(this);
        btn_BMStarttrip=view.findViewById(R.id.btn_BMStarttrip);
        btn_BMStarttrip.setOnClickListener(this);
        btn_BMEndtrip=view.findViewById(R.id.btn_BMEndtrip);
        btn_BMEndtrip.setOnClickListener(this);
        bundle= this.getArguments();
        if(bundle!=null){
            //validate captain move to start trip
            //Thread
            final Handler cwjHandler = new Handler();

            final Runnable mUpdateResults = new Runnable() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                public void run() {
                    try {
                        mainactivie.ShowMessage(objmsg.getString("message"),objmsg.getInt("try"));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            };
            new Thread(new Runnable() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void run() {
                    try {
                        Gson gson = new Gson();
                        final operationData _opd = gson.fromJson(getArguments().getString("form"), operationData.class);

                        JSONObject obj=new JSONObject();
                        JSONObject objdata=new JSONObject();
                        obj.put("requestfor","getboatcaptainvaildation");
                        objdata.put("rfid",_opd.getReceivedata());
                        obj.put("data",objdata);
                        String strResult = mainactivie.Constantvariable.funPostData(obj,"user.php").toString();

                        JSONObject objJsonObject = new JSONObject(strResult);
                        if (objJsonObject.get("resultkey").equals(1)){
                            Integer rowid =objJsonObject.getJSONArray("resultvalue").getJSONObject(0).getInt("rowid");
                            if(rowid>0){
                                mainactivie._userSelectedTripdData.setCaptainId(rowid);
                                mainactivie._userSelectedTripdData.setCaptainName(objJsonObject.getJSONArray("resultvalue").getJSONObject(0).getString("username"));
                                FragmentManager fm = getFragmentManager();
                                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                                fragmentTransaction.replace(R.id.container,new StartTripFragment(),"starttrip" );
                                fragmentTransaction.commit();
                                objmsg.put("message",mainactivie._userSelectedTripdData.getCaptainName()+" Welcome.");
                                objmsg.put("try",1);
                                cwjHandler.post(mUpdateResults);

                            }else{
                                objmsg.put("message","Tourist register successfully.");
                                objmsg.put("try",1);
                                cwjHandler.post(mUpdateResults);
                            }
                        }

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }).start();

        }
        return  view;
    }

    @Override
    public void onClick(View v) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();

        switch (v.getId()) {

            case R.id.imv_boatmanifestback:
                Fragment fragment = getFragmentManager().findFragmentByTag("Landing");
                if(fragment != null)
                    getFragmentManager().beginTransaction().remove(fragment).commit();
                fragmentTransaction.replace(R.id.container, new LandingFragment(),"Landing");
                fragmentTransaction.commit();
                break;
            case R.id.btn_BMStarttrip:
                /*fragmentTransaction.replace(R.id.container,new StartTripFragment(),"starttrip" );
                fragmentTransaction.commit();*/
                Bundle bundle = new Bundle();
                operationData opd=new operationData();
                opd.setFromname("boatmanifest_captain");
                Gson gson=new Gson();
                bundle.putString("form", gson.toJson(opd));

                CardReadingFragment cf=new CardReadingFragment();
                cf.setArguments(bundle);
                fragmentTransaction.replace(R.id.container,cf,"boatmanifest_captain" );
                fragmentTransaction.commit();
                break;
            case R.id.btn_BMEndtrip:
                mainactivie._guestInfo=new ArrayList<>();
                mainactivie._touristscandata=new ArrayList<>();
                fragmentTransaction.replace(R.id.container,new EndTripTouristscanFragment(),"endtouristscantrip" );
                fragmentTransaction.commit();
                break;

        }

    }
}