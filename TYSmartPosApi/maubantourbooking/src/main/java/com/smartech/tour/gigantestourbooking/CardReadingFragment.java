package com.smartech.tour.gigantestourbooking;

import android.os.Bundle;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.smartech.tour.gigantestourbooking.module.operationData;
import com.smartech.tour.gigantestourbooking.operationmodule.OperationInter;
import com.whty.smartpos.gigantestourbooking.R;

public class CardReadingFragment extends Fragment implements View.OnClickListener {
    Button btn_cardreadingback;
    operationData objoperationData;
    TextView txt_cardreadingmsg;
    MainActivity mainactivie;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_card_reading, container, false);
        btn_cardreadingback=(Button)view.findViewById(R.id.btn_cardreadingback);
        btn_cardreadingback.setOnClickListener(this);
        txt_cardreadingmsg=view.findViewById(R.id.txt_cardreadingmsg);
        Bundle bundle = this.getArguments();
        Gson gson = new Gson();
        objoperationData = gson.fromJson(getArguments().getString("form"), operationData.class);
        switch (objoperationData.getFromname()){
            case "test":
                txt_cardreadingmsg.setText("Please tap wristband now.");
                break;
            case "captainregistration":
                txt_cardreadingmsg.setText(objoperationData.getName()+"\nPlease tap wristband now.");
                break;
            case "cardregistration":
                txt_cardreadingmsg.setText("Please tap wristband now.");
            case "rentalcardregistration":
                txt_cardreadingmsg.setText("Please tap wristband now.");
                break;
            case "touristregistration":
                txt_cardreadingmsg.setText("Please tap tourist wristband now.");
                break;
            case "boatmanifest_captain":
                txt_cardreadingmsg.setText("Please tap captain wristband now.");
                break;
            case "touristscanning":
                txt_cardreadingmsg.setText("Please tap tourist wristband now.");
                break;
            case "endtriptouristscanning":
                txt_cardreadingmsg.setText("Please tap wristband now.");
                break;
            case "returnrentalcard":
                txt_cardreadingmsg.setText("Please tap wristband now.");
                break;



        }
        return view;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final View view = getView();
        if(view != null) {
            getView().post(new Runnable() {
                               @Override
                               public void run() {
                                   // code you want to run when view is visible for the first time


                                   Thread thread = new Thread() {
                                       @Override
                                       public void run() {
                                           String carddetail="";

                                           OperationInter.getDevice(1).execute(0);
                                           carddetail=OperationInter.getDevice(1).execute(1);
                                           if(carddetail.equals("")){
                                               carddetail=OperationInter.getDevice(1).execute(1);
                                           }else{
                                               operationData opd=new operationData();
                                               opd.setId(objoperationData.getId());
                                               opd.setName(objoperationData.getName());
                                               Gson gson=new Gson();
                                               FragmentManager fm = getFragmentManager();
                                               FragmentTransaction fragmentTransaction;
                                               Bundle bundle = new Bundle();
                                               switch (objoperationData.getFromname()){

                                                   case "test":
                                                       Fragment fragmenttest = getFragmentManager().findFragmentByTag("test");
                                                       if(fragmenttest != null)
                                                           getFragmentManager().beginTransaction().remove(fragmenttest).commit();


                                                       opd.setReceivedata(carddetail);
                                                       bundle.putString("form", gson.toJson(opd));
                                                       TestFragment tf=new TestFragment();
                                                       tf.setArguments(bundle);
                                                       fragmentTransaction = fm.beginTransaction();
                                                       fragmentTransaction.replace(R.id.container,tf,"test" );
                                                       fragmentTransaction.commit();
                                                       break;
                                                   case "captainregistration":

                                                       Fragment fragmentcaptainreg = getFragmentManager().findFragmentByTag("captainregistration");
                                                       if(fragmentcaptainreg != null)
                                                           getFragmentManager().beginTransaction().remove(fragmentcaptainreg).commit();

                                                       opd.setReceivedata(carddetail);
                                                       bundle.putString("form", gson.toJson(opd));
                                                       CaptainRegistrationFragment cpf=new CaptainRegistrationFragment();
                                                       cpf.setArguments(bundle);
                                                       fragmentTransaction = fm.beginTransaction();
                                                       fragmentTransaction.replace(R.id.container,cpf,"captainregistration" );
                                                       fragmentTransaction.commit();

                                                       break;
                                                   case "cardregistration":
                                                       Fragment fragmentcardreg = getFragmentManager().findFragmentByTag("cardregistration");
                                                       if(fragmentcardreg != null)
                                                           getFragmentManager().beginTransaction().remove(fragmentcardreg).commit();

                                                       opd.setReceivedata(carddetail);
                                                       bundle.putString("form", gson.toJson(opd));
                                                       CardRegistrationFragment crf=new CardRegistrationFragment();
                                                       crf.setArguments(bundle);
                                                       fragmentTransaction = fm.beginTransaction();
                                                       fragmentTransaction.replace(R.id.container,crf,"cardregistration" );
                                                       fragmentTransaction.commit();
                                                       break;
                                                   case "rentalcardregistration":
                                                       Fragment fragmentrentalcardreg = getFragmentManager().findFragmentByTag("rentalcardregistration");
                                                       if(fragmentrentalcardreg != null)
                                                           getFragmentManager().beginTransaction().remove(fragmentrentalcardreg).commit();

                                                       opd.setReceivedata(carddetail);
                                                       bundle.putString("form", gson.toJson(opd));
                                                       RentalCardRegistrationFragment rcrf=new RentalCardRegistrationFragment();
                                                       rcrf.setArguments(bundle);
                                                       fragmentTransaction = fm.beginTransaction();
                                                       fragmentTransaction.replace(R.id.container,rcrf,"rentalcardregistration" );
                                                       fragmentTransaction.commit();
                                                       break;
                                                   case "touristregistration":
                                                       Fragment fragmenttourisreg = getFragmentManager().findFragmentByTag("touristregistration");
                                                       if(fragmenttourisreg != null)
                                                           getFragmentManager().beginTransaction().remove(fragmenttourisreg).commit();
                                                       opd.setReceivedata(carddetail);
                                                       bundle.putString("form", gson.toJson(opd));
                                                       TouristRegistrationragment trf=new TouristRegistrationragment();
                                                       trf.setArguments(bundle);
                                                       fragmentTransaction = fm.beginTransaction();
                                                       fragmentTransaction.replace(R.id.container,trf,"touristregistration" );
                                                       fragmentTransaction.commit();
                                                       break;
                                                   case "boatmanifest_captain":
                                                       Fragment fragmentboatmanifest = getFragmentManager().findFragmentByTag("boatmanifest_captain");
                                                       if(fragmentboatmanifest != null)
                                                           getFragmentManager().beginTransaction().remove(fragmentboatmanifest).commit();
                                                       opd.setReceivedata(carddetail);
                                                       bundle.putString("form", gson.toJson(opd));
                                                       BoatManifestFragment bmcf=new BoatManifestFragment();
                                                       bmcf.setArguments(bundle);
                                                       fragmentTransaction = fm.beginTransaction();
                                                       fragmentTransaction.replace(R.id.container,bmcf,"boatmanifest_captain" );
                                                       fragmentTransaction.commit();
                                                       break;
                                                   case "touristscanning":
                                                       Fragment fragmenttouristscan = getFragmentManager().findFragmentByTag("touristscanning");
                                                       if(fragmenttouristscan != null)
                                                           getFragmentManager().beginTransaction().remove(fragmenttouristscan).commit();
                                                       opd.setReceivedata(carddetail);
                                                       bundle.putString("form", gson.toJson(opd));
                                                       TouristScanningFragment ts=new TouristScanningFragment();
                                                       ts.setArguments(bundle);
                                                       fragmentTransaction = fm.beginTransaction();
                                                       fragmentTransaction.replace(R.id.container,ts,"touristscanning" );
                                                       fragmentTransaction.commit();
                                                       break;
                                                   case "endtriptouristscanning":
                                                       Fragment fragmentendtriptouristscan = getFragmentManager().findFragmentByTag("endtriptouristscanning");
                                                       if(fragmentendtriptouristscan != null)
                                                           getFragmentManager().beginTransaction().remove(fragmentendtriptouristscan).commit();
                                                       opd.setReceivedata(carddetail);
                                                       bundle.putString("form", gson.toJson(opd));
                                                       EndTripTouristscanFragment ets=new EndTripTouristscanFragment();
                                                       ets.setArguments(bundle);
                                                       fragmentTransaction = fm.beginTransaction();
                                                       fragmentTransaction.replace(R.id.container,ets,"touristscanning" );
                                                       fragmentTransaction.commit();
                                                       break;
                                                   case "returnrentalcard":
                                                       Fragment fragmentreturnrentalcard = getFragmentManager().findFragmentByTag("returnrentalcard");
                                                       if(fragmentreturnrentalcard != null)
                                                           getFragmentManager().beginTransaction().remove(fragmentreturnrentalcard).commit();
                                                       opd.setReceivedata(carddetail);
                                                       bundle.putString("form", gson.toJson(opd));
                                                       ReturnRentalCardFragment rrc=new ReturnRentalCardFragment();
                                                       rrc.setArguments(bundle);
                                                       fragmentTransaction = fm.beginTransaction();
                                                       fragmentTransaction.replace(R.id.container,rrc,"returnrentalcard" );
                                                       fragmentTransaction.commit();
                                                       break;
                                                   case "cagsiayentry":
                                                       Fragment fragmentcagsiayentry = getFragmentManager().findFragmentByTag("cagsiayentry");
                                                       if(fragmentcagsiayentry != null)
                                                           getFragmentManager().beginTransaction().remove(fragmentcagsiayentry).commit();
                                                       opd.setReceivedata(carddetail);
                                                       bundle.putString("form", gson.toJson(opd));
                                                       CagsiayEntryFragment ce=new CagsiayEntryFragment();
                                                       ce.setArguments(bundle);
                                                       fragmentTransaction = fm.beginTransaction();
                                                       fragmentTransaction.replace(R.id.container,ce,"cagsiayentry" );
                                                       fragmentTransaction.commit();
                                                       break;
                                                   case "cagsiayexit":
                                                       Fragment fragmentcagsiayexit = getFragmentManager().findFragmentByTag("cagsiayentry");
                                                       if(fragmentcagsiayexit != null)
                                                           getFragmentManager().beginTransaction().remove(fragmentcagsiayexit).commit();
                                                       opd.setReceivedata(carddetail);
                                                       bundle.putString("form", gson.toJson(opd));
                                                       CagsiayExitFragment cexist=new CagsiayExitFragment();
                                                       cexist.setArguments(bundle);
                                                       fragmentTransaction = fm.beginTransaction();
                                                       fragmentTransaction.replace(R.id.container,cexist,"cagsiayexit" );
                                                       fragmentTransaction.commit();
                                                       break;

                                               }

                                           }

                                       }
                                   };

                                   thread.start();

                               }
                           }
            );
        }
    }
    @Override
    public void onStart() {
        super.onStart();


    }

    @Override
    public void onResume() {
        super.onResume();
      /*  */
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_cardreadingback:
                OperationInter.getDevice(1).execute(3);
                FragmentManager fm = getFragmentManager();
                FragmentTransaction fragmentTransaction;
                switch (objoperationData.getFromname()) {

                    case "test":
                        fragmentTransaction = fm.beginTransaction();
                        fragmentTransaction.replace(R.id.container, new TestFragment(), "test");
                        fragmentTransaction.commit();
                        break;
                    case "captainregistration":
                        fragmentTransaction = fm.beginTransaction();
                        fragmentTransaction.replace(R.id.container, new CaptainRegistrationFragment(), "captainregistration");
                        fragmentTransaction.commit();
                        break;
                    case "cardregistration":
                        fragmentTransaction = fm.beginTransaction();
                        fragmentTransaction.replace(R.id.container, new CardRegistrationFragment(), "cardregistration");
                        fragmentTransaction.commit();
                        break;
                    case "rentalcardregistration":
                        fragmentTransaction = fm.beginTransaction();
                        fragmentTransaction.replace(R.id.container, new RentalCardRegistrationFragment(), "rentalcardregistration");
                        fragmentTransaction.commit();
                        break;
                    case "boatmanifest_captain":
                        fragmentTransaction = fm.beginTransaction();
                        fragmentTransaction.replace(R.id.container, new BoatManifestFragment(), "boatmanifest_captain");
                        fragmentTransaction.commit();
                        break;
                    case "cagsiayentry":
                        fragmentTransaction = fm.beginTransaction();
                        fragmentTransaction.replace(R.id.container, new CagsiayEntryFragment(), "cagsiayentry");
                        fragmentTransaction.commit();
                        break;
                }
                break;
        }
    }
}