package com.smartech.tour.baleraurora;

import android.os.Build;
import android.os.Bundle;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
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

import com.smartech.tour.baleraurora.adapter.LandingActionButtonAdpater;
import com.smartech.tour.baleraurora.module.actionButton;
import com.smartech.tour.baleraurora.module.device;
import com.smartech.tour.baleraurora.module.user;
import com.whty.smartpos.gigantestourbooking.R;

import java.util.ArrayList;

public class LandingFragment extends Fragment implements View.OnClickListener  {

    ImageView imv_logout,imgv_setting;
    TextView txt_landingconnecteddb,txt_landingport;
    Button btn_testcard,btn_captainregistration,btn_cardreg,btn_boatmanifest,
            btn_touristreg,btn_rentalcardreg,btn_reprint,btn_returnrentcard,btn_vehiclereg,
            btn_cagsiaylog,btn_deviceinfo,btn_TouristManiFest,btn_PrintManifest;
    MainActivity mainactivie;
    RecyclerView rv_landingactionbutton;
    LandingActionButtonAdpater landingActionButtonAdpater;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_landing, container, false);
        mainactivie=((MainActivity) getActivity());
        imv_logout=(ImageView) view.findViewById(R.id.imgv_logout);
        imv_logout.setOnClickListener(this);
        imgv_setting=(ImageView) view.findViewById(R.id.imgv_setting);
        imgv_setting.setOnClickListener(this);
//test card
        btn_testcard=(Button) view.findViewById(R.id.btn_testcard);
        btn_testcard.setOnClickListener(this);
        btn_testcard.setVisibility(View.GONE);
//card Registration
        btn_cardreg=(Button) view.findViewById(R.id.btn_cardreg);
        btn_cardreg.setOnClickListener(this);
        btn_cardreg.setVisibility(View.GONE);
//boatmanifest
        btn_boatmanifest=(Button) view.findViewById(R.id.btn_boatmanifest);
        btn_boatmanifest.setOnClickListener(this);
        btn_boatmanifest.setVisibility(View.GONE);
//tourist reg
        btn_touristreg=(Button) view.findViewById(R.id.btn_touristreg);
        btn_touristreg.setOnClickListener(this);
        btn_touristreg.setVisibility(View.GONE);

//captain reg
        btn_captainregistration=(Button) view.findViewById(R.id.btn_captainregistration);
        btn_captainregistration.setOnClickListener(this);

        btn_captainregistration.setVisibility(View.GONE);
//rental cardreg
        btn_rentalcardreg=(Button) view.findViewById(R.id.btn_rentalcardreg);
        btn_rentalcardreg.setOnClickListener(this);
        btn_rentalcardreg.setVisibility(View.GONE);
//Reprint Trip
        btn_reprint=(Button) view.findViewById(R.id.btn_reprint);
        btn_reprint.setOnClickListener(this);
        btn_reprint.setVisibility(View.GONE);
//return rentcard
        btn_returnrentcard=(Button) view.findViewById(R.id.btn_returnrentcard);
        btn_returnrentcard.setOnClickListener(this);
        btn_returnrentcard.setVisibility(View.GONE);
///Vehicle
        btn_vehiclereg=(Button) view.findViewById(R.id.btn_vehiclereg);
        btn_vehiclereg.setOnClickListener(this);
        btn_vehiclereg.setVisibility(View.GONE);
//Cagsiay Log
        btn_cagsiaylog=(Button) view.findViewById(R.id.btn_cagsiaylog);
        btn_cagsiaylog.setOnClickListener(this);
        btn_cagsiaylog.setVisibility(View.GONE);
//Deveice Info

        btn_deviceinfo=(Button) view.findViewById(R.id.btn_cagsiaylog);
        btn_deviceinfo.setOnClickListener(this);
        btn_deviceinfo.setVisibility(View.GONE);
//Toursit ManiFest

        btn_TouristManiFest=(Button) view.findViewById(R.id.btn_TouristManiFest);
        btn_TouristManiFest.setOnClickListener(this);
        btn_TouristManiFest.setVisibility(View.GONE);

//Print ManiFest

        btn_PrintManifest=(Button) view.findViewById(R.id.btn_printmanifest);
        btn_PrintManifest.setOnClickListener(this);
        btn_PrintManifest.setVisibility(View.GONE);

        rv_landingactionbutton=view.findViewById(R.id.rv_landingactionbutton);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
        rv_landingactionbutton.setLayoutManager(staggeredGridLayoutManager);










        txt_landingconnecteddb=view.findViewById(R.id.txt_landingconnecteddb);
        txt_landingport=view.findViewById(R.id.txt_landingport);
        ArrayList<device> _devicesetting= mainactivie.mydb.getdevicedetail();
        txt_landingconnecteddb.setText(R.string.connectedtoLive);
        if(_devicesetting.size()>0){
            ArrayList<user> _alluser= mainactivie.mydb.getAllUser();
            if(_alluser.size()>0){
                if(_devicesetting.get(0).getConnectedToDB().toString().equals("test")){
                    txt_landingconnecteddb.setText(R.string.connectedtoTest);
                }
                txt_landingport.setText(_devicesetting.get(0).getPort()+"  Port");
                ArrayList<actionButton> actionButtonarr=new ArrayList<>();
                for (int i = 0; i <_alluser.get(0).getAccessRole().size() ; i++) {
                    actionButton ab=new actionButton();
                    ab.setID(_alluser.get(0).getAccessRole().get(i).getID());
                    ab.setDesc(_alluser.get(0).getAccessRole().get(i).getDesc());
                    actionButtonarr.add(ab);
                }
                 landingActionButtonAdpater = new LandingActionButtonAdpater(view.getContext(),
                            actionButtonarr,LandingFragment.this);
                    rv_landingactionbutton.setAdapter(landingActionButtonAdpater);

            }else{
                imv_logout.performClick();
            }

        }else{
            imgv_setting.performClick();
        }
        return  view;
    }

    public void callaction(actionButton model){
       /* [{"id": "ffc949b7-95c6-11eb-8d12-06e672dcd768", "desc": "Boat Manifest"},
        {"id": "14c9d647-95c7-11eb-8d12-06e672dcd768", "desc": "Boat Captain Registration"},
        {"id": "1cce4ae4-95c7-11eb-8d12-06e672dcd768", "desc": "Tourist Registration"},
        {"id": "241c1d1e-95c7-11eb-8d12-06e672dcd768", "desc": "Vehicle Manifest"},
        {"id": "2c67d735-95c7-11eb-8d12-06e672dcd768", "desc": "Card Registration"},
        {"id": "33713635-95c7-11eb-8d12-06e672dcd768", "desc": "Rental Card Registration"},
        {"id": "5e431dda-95c7-11eb-8d12-06e672dcd768", "desc": "Return card"},
        {"id": "71c23166-95c7-11eb-8d12-06e672dcd768", "desc": "Test Card"}
        {"id": "80ae243c-c8d0-11eb-8d12-06e672dcd768", "desc": "Re-print"}
        {"id": "79fae8b3-f99c-11eb-8437-06e672dcd768", "desc": "Cagsiay Log"}
        {"id": "b6483275-0c9e-11ed-add5-06e672dcd768", "desc": "Device Info"}
        {"id": "f4a0614c-e421-11ed-b6d3-06e672dcd768", "desc": "Tourist Scan"}
        {"id": "46bb5416-f462-11ed-b6d3-06e672dcd768", "desc": "Print Manifest"}
        ]
        */
        switch (model.getID()){
            case "ffc949b7-95c6-11eb-8d12-06e672dcd768":
                btn_boatmanifest.performClick();
                break;
            case "14c9d647-95c7-11eb-8d12-06e672dcd768":
                btn_captainregistration.performClick();
                break;
            case "1cce4ae4-95c7-11eb-8d12-06e672dcd768":
                btn_touristreg.performClick();
                break;
            case "241c1d1e-95c7-11eb-8d12-06e672dcd768":
                btn_vehiclereg.performClick();
                break;
            case "2c67d735-95c7-11eb-8d12-06e672dcd768":
                btn_cardreg.performClick();
                break;
            case "33713635-95c7-11eb-8d12-06e672dcd768":
                btn_rentalcardreg.performClick();
                break;
            case "5e431dda-95c7-11eb-8d12-06e672dcd768":
                btn_returnrentcard.performClick();
                break;
            case "71c23166-95c7-11eb-8d12-06e672dcd768":
                btn_testcard.performClick();
                break;
            case "80ae243c-c8d0-11eb-8d12-06e672dcd768":
                btn_reprint.performClick();
                break;
            case "79fae8b3-f99c-11eb-8437-06e672dcd768":
                btn_cagsiaylog.performClick();
                break;
            case "b6483275-0c9e-11ed-add5-06e672dcd768":
                btn_deviceinfo.performClick();
                break;
            case "f4a0614c-e421-11ed-b6d3-06e672dcd768":
                btn_TouristManiFest.performClick();
                break;
            case "46bb5416-f462-11ed-b6d3-06e672dcd768":
                btn_PrintManifest.performClick();
                break;

        }
    }

    @Override
    public void onClick(View v) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        switch (v.getId()) {
            case  R.id.imgv_logout:

                fragmentTransaction.replace(R.id.container,new LoginFragment() );
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                break;
            case  R.id.imgv_setting:
                fragmentTransaction.replace(R.id.container,new SettingFragment() );
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                break;
            case  R.id.btn_testcard:
                fragmentTransaction.replace(R.id.container,new TestFragment(),"test" );
                fragmentTransaction.commit();
                break;
            case  R.id.btn_captainregistration:
                fragmentTransaction.replace(R.id.container,new CaptainRegistrationFragment(),"captainregistration" );
                fragmentTransaction.commit();
                break;
            case  R.id.btn_cardreg:
                fragmentTransaction.replace(R.id.container,new CardRegistrationFragment(),"cardregistration" );
                fragmentTransaction.commit();
                break;
            case R.id.btn_rentalcardreg:
                fragmentTransaction.replace(R.id.container,new RentalCardRegistrationFragment(),"rentalcardregistration" );
                fragmentTransaction.commit();
                break;
            case R.id.btn_reprint:
                fragmentTransaction.replace(R.id.container,new RePrintManifestFragment(),"reprint" );
                fragmentTransaction.commit();
                break;
            case R.id.btn_touristreg:
                fragmentTransaction.replace(R.id.container,new TouristRegistrationragment(),"touristregistration" );
                fragmentTransaction.commit();
                break;
            case R.id.btn_boatmanifest:
                mainactivie._guestInfo=new ArrayList<>();
                mainactivie._touristscandata=new ArrayList<>();
                fragmentTransaction.replace(R.id.container,new TouristScanningFragment(),"touristscanning" );
                fragmentTransaction.commit();
                /*fragmentTransaction.replace(R.id.container,new BoatManifestFragment(),"boatmanifest" );
                fragmentTransaction.commit();*/
                break;
            case R.id.btn_returnrentcard:
                fragmentTransaction.replace(R.id.container,new ReturnRentalCardFragment(),"returnrentalcard" );
                fragmentTransaction.commit();
                break;
            case R.id.btn_cagsiaylog:
                fragmentTransaction.replace(R.id.container,new DeviceInfoFragment(),"deviceinfo" );
                fragmentTransaction.commit();
                break;
            case R.id.btn_deviceinfo:
                fragmentTransaction.replace(R.id.container,new DeviceInfoFragment(),"deviceinfo" );
                fragmentTransaction.commit();
                break;
            case R.id.btn_TouristManiFest:
                fragmentTransaction.replace(R.id.container,new TouristManifestFragment(),"touristsacn" );
                fragmentTransaction.commit();
                break;
            case R.id.btn_printmanifest:
                fragmentTransaction.replace(R.id.container,new PrintManifestFragment(),"printmanifest" );
                fragmentTransaction.commit();
                break;
        }
    }
}