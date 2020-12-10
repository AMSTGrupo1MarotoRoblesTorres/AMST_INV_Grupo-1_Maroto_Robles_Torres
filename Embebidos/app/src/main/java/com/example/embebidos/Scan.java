package com.example.embebidos;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class Scan extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    private ZXingScannerView mScannerView;
    final private int REQUEST_CODE_ASK_PERMISSION = 111;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mScannerView = new ZXingScannerView(this);
        setContentView(mScannerView);
        solicitarPermisos();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void solicitarPermisos() {
        int permisoCamara = ActivityCompat.checkSelfPermission(Scan.this, Manifest.permission.CAMERA);
        if (permisoCamara!= PackageManager.PERMISSION_GRANTED){
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M);{
                requestPermissions(new String[]{Manifest.permission.CAMERA},REQUEST_CODE_ASK_PERMISSION);
            }
        }
    }

    @Override
    public void handleResult(Result result) {
        MainActivity.url = result.getText();
        MainActivity.txtResultado.setText(result.getText());
        onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }
}