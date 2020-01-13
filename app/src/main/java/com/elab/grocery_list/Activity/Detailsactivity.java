package com.elab.grocery_list.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.elab.grocery_list.R;

public class Detailsactivity extends AppCompatActivity {

    private TextView itemname;
    private TextView qty;
    private TextView date;
    private int groceryid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailsactivity);
        itemname=(TextView)findViewById(R.id.itemnamedet);
        qty=(TextView)findViewById(R.id.itemqtydet);
        date=(TextView)findViewById(R.id.itemdatedet);
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null) {
            itemname.setText(bundle.getString("name"));
            qty.setText(bundle.getString("qty"));
            date.setText(bundle.getString("Date"));
            groceryid=bundle.getInt("id");

        }

    }
}
