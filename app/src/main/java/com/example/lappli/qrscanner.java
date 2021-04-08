package com.example.lappli;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.Result;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.List;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class qrscanner extends AppCompatActivity implements ZXingScannerView.ResultHandler
{
    ZXingScannerView scannerView;
    DatabaseReference dbref;
    PromoActivity promoActivity = new PromoActivity();
    List<Coupon> listCoupon = promoActivity.getListData();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        scannerView= new ZXingScannerView(this);
        setContentView(scannerView);
        dbref= FirebaseDatabase.getInstance().getReference("qrscannerdata");

        Dexter.withContext(getApplicationContext())
                .withPermission(Manifest.permission.CAMERA)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        scannerView.startCamera();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();
    }

    @Override
    public void handleResult(Result rawResult) {
        String data = rawResult.getText().toString();

        //Les Tests QrScanner
        TestQrScanner(rawResult, "Réduction 1");
        TestQrScanner(rawResult, "Réduction 2");

            dbref.push().setValue(data)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            for(int i=0; i<listCoupon.size(); i++){
                                if(data.equals(listCoupon.get(i).getTitle())){
                                    Coupon coupon = (Coupon) listCoupon.get(i);

                                    //Les Tests Coupon
                                    TestCoupon(coupon, listCoupon.get(1));
                                    TestCoupon(coupon, listCoupon.get(0));

                                    //Création nouvelle fenêtre + passage des paramètres
                                    Intent intent = new Intent(getApplicationContext(), DetailitemActivity.class);
                                    intent.putExtra("ImageName", coupon.getImageName());
                                    intent.putExtra("ImageTitle", coupon.getTitle());
                                    intent.putExtra("ImageReduction", coupon.getReduction());
                                    startActivity(intent);
                                }
                            }
                            onBackPressed();
                        }
                    });
    }

    @Override
    protected void onPause() {
        super.onPause();
        scannerView.stopCamera();
    }

    @Override
    protected void onResume() {
        super.onResume();
        scannerView.setResultHandler(this);
        scannerView.startCamera();
    }

    //Test Unitaire QrScanner
    public boolean TestQrScanner(Result rawResult, String resultWanted) {
        String resultStr = rawResult.getText().toString();
        if(resultStr == resultWanted) {
            return true;
        }
        else {
            return false;
        }
    }

    //Test Unitaire Coupon
    public boolean TestCoupon(Coupon cpScanned, Coupon cpList) {
        Coupon coupon = cpScanned;
        Coupon cpFromList = cpList;
        if(coupon == cpFromList){
            return true;
        }
        else
            return false;
    }
}