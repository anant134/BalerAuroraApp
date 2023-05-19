package com.smartech.tour.baleraurora;

import android.os.Bundle;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.gson.Gson;
import com.smartech.tour.baleraurora.adapter.TestCardAdapter;
import com.smartech.tour.baleraurora.module.cardState;
import com.smartech.tour.baleraurora.module.guestInfo;
import com.smartech.tour.baleraurora.module.operationData;
import com.whty.smartpos.gigantestourbooking.R;

import java.util.ArrayList;

public class TestFragment extends Fragment implements  View.OnClickListener {
    Button btn_testscan;
    private RecyclerView courseRV;
    ArrayList<cardState> cardStateArrayList;
    TestCardAdapter cardStateAdapter;
    Bundle bundle;
    MainActivity mainactivie;
    ImageView imv_testcardback;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_test, container, false);
        mainactivie=((MainActivity) getActivity());
        mainactivie.progressDialog = new ProgressDialog(mainactivie);
        btn_testscan=(Button)view.findViewById(R.id.btn_testscan);
        btn_testscan.setOnClickListener(this);
        imv_testcardback=view.findViewById(R.id.imv_testcardback);
        imv_testcardback.setOnClickListener(this);
        courseRV = view.findViewById(R.id.idRVCourse);
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
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final View view = getView();
        if(view != null) {
            getView().post(new Runnable() {
                @Override
                public void run() {

                    try {
                        if(bundle!=null){
                            mainactivie.progressDialog.setMessage("Please wait..."); // Setting Message
                            mainactivie.progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
                            mainactivie.progressDialog.show(); // Display Progress Dialog
                            mainactivie.progressDialog.setCancelable(false);
                            ArrayList<cardState> _cardStatesarr=mainactivie._cardState;

                            cardState data = new cardState();


                            Gson gson = new Gson();
                            operationData _opd = gson.fromJson(getArguments().getString("form"), operationData.class);
                            data.setCardState(_opd.getReceivedata());
                            data.setGuestInfo(new guestInfo());
                            _cardStatesarr.add(data);
                            cardStateArrayList=new ArrayList<>();
                            for (int i = 0; i < _cardStatesarr.size(); i++) {
                                cardState _objcardstate=_cardStatesarr.get(i);
                                cardStateArrayList.add(_objcardstate);
                            }
                            cardStateAdapter.updateList(cardStateArrayList);
                            mainactivie.progressDialog.dismiss();
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
            case  R.id.btn_testscan:

                Bundle bundle = new Bundle();
                operationData opd=new operationData();
                opd.setFromname("test");
                Gson gson=new Gson();
                bundle.putString("form", gson.toJson(opd));

                CardReadingFragment cf=new CardReadingFragment();
                cf.setArguments(bundle);
                fragmentTransaction.replace(R.id.container,cf,"cardregistration" );
                fragmentTransaction.commit();
                break;
            case R.id.imv_testcardback:
                Fragment fragment = getFragmentManager().findFragmentByTag("Landing");
                if(fragment != null)
                    getFragmentManager().beginTransaction().remove(fragment).commit();
                fragmentTransaction.replace(R.id.container, new LandingFragment(),"Landing");
                fragmentTransaction.commit();
                break;
        }

    }


}