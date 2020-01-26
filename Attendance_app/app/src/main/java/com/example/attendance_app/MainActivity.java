package com.example.attendance_app;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    DBcontroller dBcontroller;
    ArrayList<String> val_id,val_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dBcontroller = new DBcontroller(this);

        findViewById(R.id.btn_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Intent log = new Intent(MainActivity.this,ScannerActivity.class);
               // startActivity(log);
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.login_layout, null);


                final EditText idname = (EditText) mView.findViewById(R.id.idlog);
                final EditText username = (EditText) mView.findViewById(R.id.namelog);
                Button btn_cancel = (Button) mView.findViewById(R.id.cancel_log);
                Button btn_ok = (Button) mView.findViewById(R.id.ok_log);

                mBuilder.setView(mView);
                mBuilder.setCancelable(false);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();

             //login ok button method

             btn_ok.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {

                     String user = username.getText().toString();
                     String id = idname.getText().toString();


                     if(idname.getText().toString().equals("")&& username.getText().toString().equals("")){

                         Toast.makeText(MainActivity.this, "input some", Toast.LENGTH_SHORT).show();
                     }else {

                         if ( validLogin(id,user)&& user.toLowerCase().contains("admin")) {

                             dialog.cancel();
                             Intent intent = new Intent(MainActivity.this, Admin_Activity.class);
                             startActivity(intent);

                         }else if (validLogin(id,user)&& !user.toLowerCase().contains("admin")){

                             dialog.cancel();
                             Intent intent = new Intent(MainActivity.this, Student_Activity.class);
                             startActivity(intent);
                         }
                         else {Toast.makeText(MainActivity.this, "Not Valid Login!", Toast.LENGTH_SHORT).show();}
                     }


                     // Intent intent = new Intent(MainActivity.this, ScannerActivity.class);
                     // startActivity(intent);
                 }
             });

            //cancel button method
                btn_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });
            }
        });


        //register button method
        findViewById(R.id.txtregister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent log2 = new Intent(MainActivity.this,Registration_Activity.class);
                startActivity(log2);
            }
        });

    }

    //oncreate ending

    public boolean validLogin(String s,String e){
        //---------------------------------------//
        Cursor res = dBcontroller.getLogIndata();

        if(res.getCount() == 0) {
            // show message


        }
        //---------------store database from val_id-----------------------//
        val_id = new ArrayList<String>();
        val_name = new ArrayList<String>();

        while (res.moveToNext()) {

            val_id.add(res.getString(0));
            val_name.add(res.getString(1));
            //buffer.append("stud_id :"+ res.getString(0)+"\n");
            // buffer.append("date :"+ res.getString(2)+"\n");

            System.out.println("new size: " +val_id.size() );

        }

        //-------------------compare user login to the registered user-----------------------//

       if (val_id.contains(s)&&val_name.contains(e)){
           return true;
       }else{return false;}


    }


}
