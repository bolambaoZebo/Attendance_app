package com.example.attendance_app;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.WriterException;

import java.io.File;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import androidmads.library.qrgenearator.QRGSaver;

public class Admin_Activity extends AppCompatActivity {

    String TAG = "GenerateQRCode";
    EditText edtValue1,edtValue2;
    ImageView qrImage;
    Button start, save;
    String inputValue;
   // String savePath = Environment.getExternalStorageDirectory().getPath() + "/QRCode/";
    String savePath= Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "CodeAndroid.png";
    Bitmap bitmap;
    QRGEncoder qrgEncoder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_);

        //QR code generation feature
        qrImage = (ImageView) findViewById(R.id.QR_Image);
        edtValue1 = (EditText) findViewById(R.id.edt_value1);
        edtValue2 = (EditText) findViewById(R.id.edt_value2);
        start = (Button) findViewById(R.id.start);
        save = (Button) findViewById(R.id.save);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputValue = edtValue1.getText().toString().trim()+ "/" +edtValue2.getText().toString().trim();
                if (inputValue.length() > 0) {
                    WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
                    Display display = manager.getDefaultDisplay();
                    Point point = new Point();
                    display.getSize(point);
                    int width = point.x;
                    int height = point.y;
                    int smallerDimension = width < height ? width : height;
                    smallerDimension = smallerDimension * 3 / 4;

                    qrgEncoder = new QRGEncoder(
                            inputValue, null,
                            QRGContents.Type.TEXT,
                            smallerDimension);
                    try {
                        bitmap  = qrgEncoder.encodeAsBitmap();
                        qrImage.setImageBitmap(bitmap);
                    } catch (WriterException e) {
                        Log.v(TAG, e.toString());
                    }
                } else {
                    edtValue1.setError("Required");
                }
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean save;
                String result;
                try {
                    save = QRGSaver.save(savePath, edtValue1.getText().toString().trim()+"/"+edtValue2.getText().toString().trim()
                            , bitmap, QRGContents.ImageType.IMAGE_JPEG);
                    result = save ? "Image Saved" : "Image Not Saved";
                    Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
