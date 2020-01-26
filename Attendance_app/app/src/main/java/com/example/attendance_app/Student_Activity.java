package com.example.attendance_app;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Student_Activity extends AppCompatActivity {

    ImageButton img_btnScan;

    ListView listView;
    SadapterList sadapterList;
    ArrayList<SemiAttended> semiAttendeds;
    DBcontroller dBcontroller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_);

        img_btnScan = findViewById(R.id.scanQR);

        listView = findViewById(R.id.sListView);
        dBcontroller = new DBcontroller(this);
        semiAttendeds = new ArrayList<>();

        loadDataseminar();


        img_btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentscan = new Intent(Student_Activity.this,ScannerActivity.class);
                startActivity(intentscan);
            }
        });
    }

    private void loadDataseminar(){

        semiAttendeds = dBcontroller.getAllSeminar();
        sadapterList = new SadapterList(this,semiAttendeds);
        listView.setAdapter(sadapterList);
        sadapterList.notifyDataSetChanged();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {


                AlertDialog.Builder builder1 = new AlertDialog.Builder(Student_Activity.this);
                builder1.setMessage("Delete Attended Seminar");
                builder1.setCancelable(false);

                final int p = position;

                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                SemiAttended tempsem = semiAttendeds.get(p);
                                dBcontroller.deleteList(tempsem.getId());
                                Toast.makeText(Student_Activity.this,tempsem.getTitle()+" deleted",Toast.LENGTH_SHORT).show();

                                semiAttendeds.clear();
                                semiAttendeds.addAll(dBcontroller.getAllSeminar());
                                sadapterList.notifyDataSetChanged();
                                listView.invalidateViews();
                                listView.refreshDrawableState();
                                dialog.cancel();
                            }
                        });

                builder1.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();
            }
        });
    }
}
