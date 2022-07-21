package com.smartech.tour.gigantestourbooking;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.whty.smartpos.gigantestourbooking.R;

public class CagsiayLogFragment extends Fragment implements View.OnClickListener{
    MainActivity mainactivie;
    ImageView imv_cagsiayback;
    Button btn_cagsiayentry,btn_cagsiayexit;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view= inflater.inflate(R.layout.fragment_cagsiay_log, container, false);
        imv_cagsiayback=view.findViewById(R.id.imv_cagsiayback);
        imv_cagsiayback.setOnClickListener(this);
        btn_cagsiayexit=view.findViewById(R.id.btn_cagsiayexit);
        btn_cagsiayexit.setOnClickListener(this);
        btn_cagsiayentry=view.findViewById(R.id.btn_cagsiayentry);
        btn_cagsiayentry.setOnClickListener(this);
        mainactivie=((MainActivity) getActivity());
        return view;
    }
    @Override
    public void onClick(View v){
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        switch (v.getId()) {
            case R.id.imv_cagsiayback:
                Fragment fragment = getFragmentManager().findFragmentByTag("landing");
                if(fragment != null)
                    getFragmentManager().beginTransaction().remove(fragment).commit();
                fragmentTransaction.replace(R.id.container, new LandingFragment(),"landing");
                fragmentTransaction.commit();
                break;
            case R.id.btn_cagsiayexit:
                fragmentTransaction.replace(R.id.container,new CagsiayExitFragment(),"cagsiayexit" );
                fragmentTransaction.commit();
                break;
            case R.id.btn_cagsiayentry:
                fragmentTransaction.replace(R.id.container,new CagsiayEntryFragment(),"cagsiayentry" );
                fragmentTransaction.commit();
                break;
        }

    }
}
