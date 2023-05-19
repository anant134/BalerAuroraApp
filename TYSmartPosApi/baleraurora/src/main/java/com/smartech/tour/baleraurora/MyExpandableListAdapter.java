package com.smartech.tour.baleraurora;

import android.content.Context;
import android.database.DataSetObserver;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.TextView;

import com.whty.smartpos.gigantestourbooking.R;

import java.util.List;
import java.util.Map;

/**
 * Created by zengyahui on 2017-10-24.
 */

public class MyExpandableListAdapter implements ExpandableListAdapter {

    private Context context;
    private List<String> interfaceList;
    private Map<String, List<String>> interfaceSet;

    public MyExpandableListAdapter(Context context,List<String> interfaceList, Map<String, List<String>> interfaceSet) {
        this.context = context;
        this.interfaceList = interfaceList;
        this.interfaceSet = interfaceSet;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getGroupCount() {
        return interfaceList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return interfaceSet.get(interfaceList.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return interfaceList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return interfaceSet.get(interfaceList.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.group, null);
            viewHolder = new ViewHolder();
            viewHolder.tv = (TextView)convertView.findViewById(R.id.tv);
            TextPaint tp = viewHolder.tv.getPaint();
            tp.setFakeBoldText(true);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }
        viewHolder.tv.setText(interfaceList.get(groupPosition));
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item, null);
            viewHolder = new ViewHolder();
            viewHolder.tv = (TextView)convertView.findViewById(R.id.tv);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }
        viewHolder.tv.setText(interfaceSet.get(interfaceList.get(groupPosition)).get(childPosition));
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void onGroupExpanded(int groupPosition) {

    }

    @Override
    public void onGroupCollapsed(int groupPosition) {

    }

    @Override
    public long getCombinedChildId(long groupId, long childId) {
        return 0;
    }

    @Override
    public long getCombinedGroupId(long groupId) {
        return 0;
    }

    class ViewHolder {
        public TextView tv;
    }

}
