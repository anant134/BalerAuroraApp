package com.smartech.tour.baleraurora;

import android.app.Fragment;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import androidx.annotation.Nullable;

import com.whty.smartpos.gigantestourbooking.R;
import com.smartech.tour.baleraurora.operationmodule.OperationInter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zengyahui on 2017-10-24.
 */

public class OperateFragment extends Fragment {

    private ExpandableListView expandableListView;
    private ExpandableListAdapter adapter;
    private List<String> interfaceList;
    private Map<String, List<String>> dataSet;

    private ResultFragment rf;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_operate, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        interfaceList = Arrays.asList(getResources().getStringArray(R.array.inter));
        dataSet = new HashMap<>();
        dataSet.put(interfaceList.get(0), Arrays.asList(getResources().getStringArray(R.array.pos_terminal)));
        dataSet.put(interfaceList.get(1), Arrays.asList(getResources().getStringArray(R.array.read_card)));
        dataSet.put(interfaceList.get(2), Arrays.asList(getResources().getStringArray(R.array.emv)));
        dataSet.put(interfaceList.get(3), Arrays.asList(getResources().getStringArray(R.array.pinpad)));
        dataSet.put(interfaceList.get(4), Arrays.asList(getResources().getStringArray(R.array.printer)));
        dataSet.put(interfaceList.get(5), Arrays.asList(getResources().getStringArray(R.array.scanner)));
        dataSet.put(interfaceList.get(6), Arrays.asList(getResources().getStringArray(R.array.led)));
        dataSet.put(interfaceList.get(7), Arrays.asList(getResources().getStringArray(R.array.beeper)));

        expandableListView = (ExpandableListView) view.findViewById(R.id.interface_list);
        expandableListView.setGroupIndicator(null);
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return false;
            }
        });
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, final int groupPosition, final int childPosition, long id) {
                final String inter = dataSet.get(interfaceList.get(groupPosition)).get(childPosition);
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        rf = (ResultFragment) getActivity().getFragmentManager().findFragmentById(R.id.result);
                        rf.showTitle(inter);
                        String result = OperationInter.getDevice(groupPosition).execute(childPosition);
                        rf.appendLog(result);
                    }
                }.start();
                return false;
            }
        });
        adapter = new MyExpandableListAdapter(getActivity(), interfaceList, dataSet);
        expandableListView.setAdapter(adapter);
    }
}
