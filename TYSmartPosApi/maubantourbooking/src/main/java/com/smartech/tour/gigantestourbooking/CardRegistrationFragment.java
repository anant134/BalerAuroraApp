package com.smartech.tour.gigantestourbooking;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.RequiresApi;

import com.google.gson.Gson;
import com.smartech.tour.gigantestourbooking.module.card;
import com.smartech.tour.gigantestourbooking.module.cardState;
import com.smartech.tour.gigantestourbooking.module.operationData;
import com.whty.smartpos.gigantestourbooking.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class CardRegistrationFragment extends Fragment implements View.OnClickListener {
    Button btn_cardregscan;
    MainActivity mainactivie;
    ImageView imv_cardregback;
    Bundle bundle;
    ArrayAdapter adapter;
    ArrayList<String> lst;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_card_registration, container, false);
        mainactivie=((MainActivity) getActivity());
        lst=new ArrayList<>();
        adapter = new ArrayAdapter<String>(view.getContext(), R.layout.activity_cardlistview,
                R.id.lbl_carddetails, lst);

        btn_cardregscan=(Button)view.findViewById(R.id.btn_cardregscan);
        btn_cardregscan.setOnClickListener(this);

        imv_cardregback=view.findViewById(R.id.imv_cardregback);
        imv_cardregback.setOnClickListener(this);

        mainactivie.progressDialog = new ProgressDialog(mainactivie);

        ListView listView = (ListView)view.findViewById(R.id.ls_cardreglist);
        listView.setAdapter(adapter);
        bundle= this.getArguments();


        return  view;
    }

    public  void loaddata(){

        new Thread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void run() {
                try {
                    getallregistercard();

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }
/* */
    public ArrayList<cardState> getallregistercard(){
        ArrayList<cardState> _cardState=new ArrayList<>();
        mainactivie.runOnUiThread(new Runnable() {
            public void run() {

                try{
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
                                JSONObject obj=new JSONObject();
                                JSONObject objdata=new JSONObject();
                                obj.put("requestfor","getrfids");
                                objdata.put("isrented","0");
                                obj.put("data",objdata);
                                String strResult = mainactivie.Constantvariable.funPostData(obj,"user.php").toString();

                                Gson gson = new Gson();
                                JSONObject objJsonObject = new JSONObject(strResult);
                                if(objJsonObject.getInt("resultkey")==1){
                                    JSONArray objarr=objJsonObject.getJSONArray("resultvalue");
                                    mainactivie._card=new ArrayList<>();
                                    for (int i = 0; i <objarr.length() ; i++) {
                                        lst.add(objarr.getJSONObject(i).getString("rfid"));
                                        card c=new card();
                                        c.setRfid(objarr.getJSONObject(i).getString("rfid"));
                                        mainactivie._card.add(c);
                                    }

                                    cwjHandler.post(mUpdateResults);
                                }else{
                                    mainactivie.ShowMessage("Please contact to administrator",3);
                                }



                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    }).start();

                   // mainactivie.progressDialog.dismiss();

                }catch (Exception e){
                    mainactivie.ShowMessage(e.getMessage().toString(),3);
                  /*  mainactivie.progressDialog.dismiss();*/
                }

            }
        });
        return _cardState;
    }

    public void insertcardregistratio(final String rfid){
        mainactivie.runOnUiThread(new Runnable() {
            public void run() {
                final Handler cwjHandler = new Handler();

                final Runnable mUpdateResults = new Runnable() {
                    public void run() {
                        mainactivie.ShowMessage("Card register successfully.",1);
                          updateUI();
                        btn_cardregscan.performClick();
                    }
                };
                new Thread(new Runnable() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void run() {
                        try {
                            try{
                                JSONObject obj=new JSONObject();
                                JSONObject objdata=new JSONObject();
                                obj.put("requestfor","registerrfid");
                                objdata.put("rfid",rfid);
                                objdata.put("isrented","0");
                                obj.put("data",objdata);
                                String strResult = mainactivie.Constantvariable.funPostData(obj,"user.php").toString();

                                Gson gson = new Gson();
                                JSONObject objJsonObject = new JSONObject(strResult);
                                if(objJsonObject.getInt("resultkey")==1){
                                    JSONArray objarr=objJsonObject.getJSONArray("resultvalue");
                                    if(Integer.parseInt(objarr.getJSONObject(0).getString("rowid"))>0){
                                         cwjHandler.post(mUpdateResults);
                                    }else{
                                        mainactivie.ShowMessage("This tag already register.",3);
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


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final View view = getView();
        if (view != null) {
            getView().post(new Runnable() {
                @Override
                public void run() {

                    try {
                        if(bundle!=null){

                            ArrayList<card> _cardarr=mainactivie._card;
                            card data = new card();
                            Gson gson = new Gson();
                            operationData _opd = gson.fromJson(getArguments().getString("form"), operationData.class);
                            data.setRfid(_opd.getReceivedata());

                            //insert record
                            if(mainactivie._card.size()>0){
                                boolean isexist=false;
                                for (int i = 0; i < mainactivie._card.size(); i++) {
                                    card c=mainactivie._card.get(i);
                                    if(data.getRfid().equals(c.getRfid())){
                                        mainactivie.ShowMessage("Card already register",3);
                                        isexist=true;
                                        updateUI();
                                        btn_cardregscan.performClick();
                                        return;

                                    }
                                }
                                if(!isexist){

                                    insertcardregistratio(_opd.getReceivedata());
                                    _cardarr.add(data);
                                }
                            }else{
                                insertcardregistratio(_opd.getReceivedata());
                                _cardarr.add(data);
                            }


                            //mainactivie.progressDialog.dismiss();
                        }else{
                            mainactivie._card=new ArrayList<>();
                            loaddata();
                        }
                    }catch (Exception e){
                        mainactivie.ShowMessage(e.getMessage().toString(),3);
                        mainactivie.progressDialog.dismiss();
                    }

                }
            });
        }
    }

    @Override
    public void onClick(View v) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        switch (v.getId()) {
            case R.id.btn_cardregscan:
                Bundle bundle = new Bundle();
                operationData opd=new operationData();
                opd.setFromname("cardregistration");
                Gson gson=new Gson();
                bundle.putString("form", gson.toJson(opd));

                CardReadingFragment cf=new CardReadingFragment();
                cf.setArguments(bundle);
                fragmentTransaction.replace(R.id.container,cf,"cardregistration" );
                fragmentTransaction.commit();
                break;
            case R.id.imv_cardregback:
                Fragment fragment = getFragmentManager().findFragmentByTag("Landing");
                if(fragment != null)
                    getFragmentManager().beginTransaction().remove(fragment).commit();
                fragmentTransaction.replace(R.id.container, new LandingFragment(),"Landing");
                fragmentTransaction.commit();
                break;

        }
    }

    private void updateUI() {
        //adapter.clear();


        String[] array = new String[mainactivie._card.size()];
        /*String[] mobileArray = {"Android","IPhone","WindowsMobile","Blackberry",
                "WebOS","Ubuntu","Windows7","Max OS X"};*/
        for (int i = 0; i < mainactivie._card.size(); i++) {
            array[i] = mainactivie._card.get(i).getRfid();
        }
        adapter.clear();
        adapter.addAll(array);
        adapter.notifyDataSetChanged();
       // rv_captainreg.setAdapter(captainAdapter);
    }

}