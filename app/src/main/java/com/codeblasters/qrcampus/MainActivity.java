package com.codeblasters.qrcampus;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.zxing.WriterException;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Random;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import me.ydcool.lib.qrmodule.activity.QrScannerActivity;
import me.ydcool.lib.qrmodule.encoding.QrGenerator;

public class MainActivity extends AppCompatActivity {
ImageView i;
Bitmap bitmap;
Button b;
    private StorageReference mStorageRef;//


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        i=findViewById(R.id.imageView);
        // Initializing the QR Encoder with your value to be encoded, type you required and Dimension
        b=findViewById(R.id.camara);
     //   mStorageRef = FirebaseStorage.getInstance().getReference();//
      //  Uri file = Uri.fromFile(new File("file:///android_asset/sounds/beep.mid/rivers.jpg"));
        //StorageReference riversRef = mStorageRef.child("images/rivers.jpg");
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bitmap qrCode = null;
                try {
                    qrCode = new QrGenerator.Builder()
                            .content("DAta")
                            .qrSize(300)
                            .margin(2)
                            .color(Color.BLACK)
                            .bgColor(Color.WHITE)
                            .ecc(ErrorCorrectionLevel.H)
                            .encode();
                } catch (WriterException e) {
                    e.printStackTrace();
                }

                i.setImageBitmap(qrCode);
                bmToJpg(qrCode);
            }
        });
//for READER
        Intent intent = new Intent(MainActivity.this, QrScannerActivity.class);
        startActivityForResult(intent, QrScannerActivity.QR_REQUEST_CODE);
    }
public void bmToJpg(Bitmap qrCode){
    String root = Environment.getExternalStorageDirectory().toString();
    File myDir = new File(root + "/req_images");
    myDir.mkdirs();
    Random generator = new Random();
    int n = 10000;
    n = generator.nextInt(n);
    String fname = "Image-" + n + ".jpg";

    File file = new File(myDir, fname);
    // Log.i(TAG, "" + file);
    if (file.exists())
        file.delete();
    try {
        FileOutputStream out = new FileOutputStream(file);
        qrCode.compress(Bitmap.CompressFormat.JPEG, 90, out);
       // out.flush();
        out.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
}
    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(getApplicationContext(),"onstart called",Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
       //code foe reader
        if (requestCode == QrScannerActivity.QR_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(getApplicationContext(), data.getExtras().getString(QrScannerActivity.QR_RESULT_STR), Toast.LENGTH_SHORT).show();
                ;
            } else {
                Toast.makeText(getApplicationContext(), "Scanned Nothing!", Toast.LENGTH_SHORT).show();
            }
        }
        }//till here
    }
