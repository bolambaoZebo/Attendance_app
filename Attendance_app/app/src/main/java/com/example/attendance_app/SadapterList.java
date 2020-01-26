package com.example.attendance_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class SadapterList extends BaseAdapter {

    Context context;
    ArrayList<SemiAttended> listseminar;

    public SadapterList(Context context, ArrayList<SemiAttended> listseminar) {
        this.context = context;
        this.listseminar = listseminar;
    }

    @Override
    public Object getItem(int position) {
        return listseminar.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_layout1,null);

            TextView title = (TextView) convertView.findViewById(R.id.title_sem);
            TextView date = convertView.findViewById(R.id.date_sem);


            SemiAttended semlist = listseminar.get(position);

            title.setText(semlist.getTitle());
            date.setText(semlist.getDale());


        return convertView;
    }

    @Override
    public int getCount() {
        return this.listseminar.size();
    }

}
