package com.example.attendance_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.ToggleButton;

public class Registration_Activity extends AppCompatActivity {

    EditText id,fname,midname,lname,age;
    ToggleButton gender;

    DBcontroller  dBcontroller ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_dialog);

        dBcontroller = new DBcontroller(this);

        id = findViewById(R.id.idnum);
        fname = findViewById(R.id.fname);
        midname = findViewById(R.id.midname);
        lname = findViewById(R.id.lname);
       // age = findViewById(R.id.age);
        //gender = findViewById(R.id.togglebutton);

        //back button

        findViewById(R.id.imageBtnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent log2 = new Intent(Registration_Activity.this,MainActivity.class);
                startActivity(log2);
            }
        });

        findViewById(R.id.sub).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //get all the string data from each editext box.....................
                insert(id.getText().toString(),fname.getText().toString(),midname.getText().toString(),lname.getText().toString());//gender not include// d
                id.setText("");
                fname.setText("");
                midname.setText("");
                lname.setText("");
            }
        });

    }


    public  void insert(String d, String f,String m,String l){

        if (d.equals("")|| f.equals("")|| m.equals("")|| l.equals("") ){
            Toast.makeText(Registration_Activity.this,"fill up all",Toast.LENGTH_SHORT).show();
        }else {
            boolean in = dBcontroller.insertReg(d,f,m,l);
            if (in){

                Toast.makeText(Registration_Activity.this,"Congrats your in",Toast.LENGTH_SHORT).show();
            }else

                Toast.makeText(Registration_Activity.this,"Account already exists",Toast.LENGTH_SHORT).show();
        }

    }


}
