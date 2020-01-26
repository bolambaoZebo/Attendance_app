package com.example.attendance_app;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.Result;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScannerActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private ZXingScannerView scannerView;
    private TextView txtshowscan;

    DBcontroller dBcontroller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);

        scannerView = findViewById(R.id.zxScanner);
        txtshowscan = findViewById(R.id.txtscan);

        Dexter.withActivity(this)
                .withPermission(Manifest.permission.CAMERA)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {

                        scannerView.setResultHandler( ScannerActivity.this);
                        scannerView.startCamera();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {

                        Toast.makeText(ScannerActivity.this,"you accept it", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                    }
                })
                .check();

    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }

    @Override
    public void handleResult(Result result) {
        // here is the result of the the scanner QR
        //txtshowscan.setText(result.getText());
        dBcontroller  = new DBcontroller(ScannerActivity.this);
        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        String qrcode = result.getText().trim();

        dBcontroller.insertATTDSEMINAR(qrcode,date);

        Intent intent = new Intent(ScannerActivity.this, Student_Activity.class);
        startActivity(intent);
    }
}
