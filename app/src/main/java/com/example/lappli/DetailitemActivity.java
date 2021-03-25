package com.example.lappli;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailitemActivity extends AppCompatActivity {

    public TextView nom;
    public TextView reduction;
    public ImageView imageView;
    public Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailitem);

        context = this;
        Intent intent = getIntent();
        nom = findViewById(R.id.title);
        reduction = findViewById(R.id.reduction);
        imageView = findViewById(R.id.imageView_icone);

        nom.setText(intent.getStringExtra("ImageTitle"));
        reduction.setText("Reduction " + intent.getIntExtra("ImageReduction", 0) + "%");
        imageView.setImageResource(getMipmapResIdByName(intent.getStringExtra("ImageName")));
    }
    public int getMipmapResIdByName(String resName)  {
        String pkgName = context.getPackageName();
        // Return 0 if not found.
        int resID = context.getResources().getIdentifier(resName , "mipmap", pkgName);
        Log.i("CustomListView", "Res Name: "+ resName+"==> Res ID = "+ resID);
        return resID;
    }
}