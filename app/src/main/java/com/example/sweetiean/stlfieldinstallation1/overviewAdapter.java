package com.example.sweetiean.stlfieldinstallation1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by sweetiean on 2/5/2015.
 */
public class overviewAdapter extends BaseAdapter {
    ArrayList<String> syslist;
    ArrayList<String> datelist;
    ArrayList<String> tasktypelist;
    ArrayList<String> engNamelist;
    private Context context;
    private ArrayList<ArrayList<String>> list;

    public overviewAdapter(Context context, ArrayList<ArrayList<String>> objects) {
        this.list = objects;
        this.context = context;
    }


    @Override
    public int getCount() {
        return list.get(2).size() == 0 ? 1 : list.get(2).size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        syslist = list.get(0);
        tasktypelist = list.get(1);
        engNamelist = list.get(2);
        datelist = list.get(3);
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.activity_display_overview, null);

        }
        placeHolder.date = (TextView) v.findViewById(R.id.dateTextView);
        placeHolder.sysaid = (TextView) v.findViewById(R.id.sysaidIdTextView);
        placeHolder.tasktype = (TextView) v.findViewById(R.id.taskTypeTextView);
        placeHolder.engName = (TextView) v.findViewById(R.id.engNameTextView);
        placeHolder.date.setText(datelist.get(position));
        placeHolder.sysaid.setText(syslist.get(position));
        placeHolder.tasktype.setText(tasktypelist.get(position));
        placeHolder.engName.setText(engNamelist.get(position));
        return v;
    }

    public static class placeHolder {
        public static TextView date;
        public static TextView sysaid;
        public static TextView tasktype;
        public static TextView engName;
    }

    public ArrayList getsysaidId(){
        return list.get(0);
    }
}
