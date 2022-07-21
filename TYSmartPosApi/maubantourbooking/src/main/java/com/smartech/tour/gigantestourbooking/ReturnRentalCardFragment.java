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

public class ReturnRentalCardFragment extends Fragment  implements  View.OnClickListener{

    Button btn_scanreturnrentalcard;
    ImageView imv_returnrentalcardback;
    Bundle bundle;
    MainActivity mainactivie;
    operationData _opd;
    JSONObject objmsg=new JSONObject();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_return_rental_card, container, false);
        imv_returnrentalcardback=view.findViewById(R.id.imv_returnrentalcardback);
        imv_returnrentalcardback.setOnClickListener(this);
        btn_scanreturnrentalcard=view.findViewById(R.id.btn_scanreturnrentalcard);
        btn_scanreturnrentalcard.setOnClickListener(this);
        mainactivie=((MainActivity) getActivity());
        bundle= this.getArguments();


        if(bundle==null){
            //getallreturnrentalcard(view);
        }else{

            final Handler cwjHandler = new Handler();
            Gson gson = new Gson();
            _opd = gson.fromJson(getArguments().getString("form"), operationData.class);

            final Runnable mUpdateResults = new Runnable() {
                public void run() {
                    if(objmsg!=null){
                        try {
                            mainactivie.ShowMessage(objmsg.getString("message").toString(),objmsg.getInt("type"));
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

                        JSONObject obj = new JSONObject();
                        JSONObject objdata = new JSONObject();
                        objmsg=null;
                        obj.put("requestfor", "resetbracelet");
                        objdata.put("rfid", _opd.getReceivedata());
                        obj.put("data", objdata);
                        String strResult = mainactivie.Constantvariable.funPostData(obj, "tourist.php").toString();
                        Gson gson = new Gson();
                        JSONObject objJsonObject = new JSONObject(strResult);
                        if (objJsonObject.get("resultkey").equals(1)){
                            objmsg=new JSONObject();
                            objmsg.put("message","Tag reset successfully");
                            objmsg.put("type",1);
                            cwjHandler.post(mUpdateResults);
                        }
                    }catch (Exception ex){
                        String s="";
                    }
                }}).start();

        }
        return view;
    }

    public void getallreturnrentalcard(View v){

    }

    @Override
    public void onClick(View v) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        switch (v.getId()) {
            case R.id.imv_returnrentalcardback:
                Fragment fragment = getFragmentManager().findFragmentByTag("landing");
                if(fragment != null)
                    getFragmentManager().beginTransaction().remove(fragment).commit();
                fragmentTransaction.replace(R.id.container, new LandingFragment(),"landing");
                fragmentTransaction.commit();
                break;
            case R.id.btn_scanreturnrentalcard:

                Bundle bundle = new Bundle();
                operationData opd=new operationData();
                opd.setFromname("returnrentalcard");
                Gson gson=new Gson();
                bundle.putString("form", gson.toJson(opd));
                CardReadingFragment cf=new CardReadingFragment();
                cf.setArguments(bundle);
                fragmentTransaction.replace(R.id.container,cf,"returnrentalcard" );
                fragmentTransaction.commit();
                break;
        }
    }
}