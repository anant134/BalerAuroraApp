package com.smartech.tour.gigantestourbooking;

import android.os.Bundle;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Spinner;

import com.smartech.tour.gigantestourbooking.module.device;
import com.smartech.tour.gigantestourbooking.operationmodule.OperationInter;
import com.whty.smartpos.gigantestourbooking.R;

import java.util.ArrayList;


public class SettingFragment extends Fragment  implements  View.OnClickListener{

    Spinner ddl_port;
    ImageView img_settinghome;
    CheckBox chk_connecttestserver;
    Button btn_savesetting;
    MainActivity mainactivie;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_setting, container, false);
        ddl_port=(Spinner)view.findViewById(R.id.ddl_port);
        img_settinghome=(ImageView)view.findViewById(R.id.img_settinghome);
        btn_savesetting=(Button)view.findViewById(R.id.btn_savesetting);
        img_settinghome.setOnClickListener(this);
        chk_connecttestserver=(CheckBox)view.findViewById(R.id.chk_connecttestserver);
        mainactivie=((MainActivity) getActivity());

        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Gigantes Start");
        arrayList.add("Gigantes End");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(view.getContext(),R.layout.support_simple_spinner_dropdown_item,arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ddl_port.setAdapter(arrayAdapter);
        ArrayList<device> devices=getdeviceinfo();
        if(devices.size()>0){
            if(devices.get(0).getPort().equals("Gigantes Start")){
                ddl_port.setSelection(0);
            }else{
                ddl_port.setSelection(1);
            }
            chk_connecttestserver.setChecked(devices.get(0).getConnectedToDB().toString().equals("test")==true?true:false);

        }else{
            setdefault();
        }

        btn_savesetting.setOnClickListener(this);


        return  view;
    }

    public ArrayList<device> getdeviceinfo(){
        return mainactivie.mydb.getdevicedetail();
    }
    public void setdefault(){
        chk_connecttestserver.setChecked(true);
        ddl_port.setSelection(0);
    }
    @Override
    public void onClick(View v) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        switch (v.getId()) {
            case  R.id.img_settinghome:
                fragmentTransaction.replace(R.id.container,new LandingFragment() );
                fragmentTransaction.commit();
                break;
            case R.id.btn_savesetting:
                String deviceid=OperationInter.getDevice(0).execute(0);
                String port=ddl_port.getSelectedItem().toString();
                String connecteddb=chk_connecttestserver.isChecked()==true?"test":"live";

                ArrayList<device> devices=getdeviceinfo();
                if(devices.size()>0){
                    mainactivie.mydb.updatedevice(deviceid,port,connecteddb);
                    mainactivie.Constantvariable.setCurrenturl(mainactivie.Constantvariable.baseuserllive);
                    if(chk_connecttestserver.isChecked()){
                        mainactivie.Constantvariable.setCurrenturl(mainactivie.Constantvariable.baseuserltest);
                    }
                    mainactivie.ShowMessage("Data updated successfully.",1);

                }else{
                    mainactivie.mydb.insertdevice(deviceid,port,connecteddb);
                    mainactivie.ShowMessage("Data saved successfully.",1);

                }
                break;
        }
    }
}