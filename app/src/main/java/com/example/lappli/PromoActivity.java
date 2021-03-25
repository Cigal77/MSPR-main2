package com.example.lappli;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public class PromoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promo);


        List<Coupon> image_details = getListData();
        final ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(new CustomListAdapter(this, image_details));

        // When the user clicks on the ListItem
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Object o = listView.getItemAtPosition(position);
                Coupon coupon = (Coupon) o;
                Toast.makeText(PromoActivity.this, "Selected :" + " " + coupon, Toast.LENGTH_LONG).show();
            }
        });
    }

    private List<Coupon> getListData() {
        List<Coupon> list = new ArrayList<Coupon>();
        Coupon reduc1 = new Coupon("Réduction 1", "lotr1", 50);
        Coupon reduc2 = new Coupon("Réduction 2", "lotr2", 25);

        list.add(reduc1);
        list.add(reduc2);

        return list;
    }
}
