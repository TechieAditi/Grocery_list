package com.elab.grocery_list.Activity;

import android.content.Intent;
import android.os.Bundle;

import com.elab.grocery_list.Data.Databasehandler;
import com.elab.grocery_list.Model.Grocery;
import com.elab.grocery_list.UI.Recyclerviewadapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.elab.grocery_list.R;

import java.io.Flushable;
import java.util.ArrayList;
import java.util.List;

public class Listactivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private Recyclerviewadapter recyclerViewAdapter;
    private List<Grocery> groceryList;
    private List<Grocery> listItems;
    private Databasehandler db;
    private AlertDialog.Builder alertdialogbuilder;
    private AlertDialog alertDialog;
    private EditText groceryItem;
    private EditText quantity;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listactivity);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                // .setAction("Action", null).show();
                createpoipdialog();

            }
        });
        db = new Databasehandler(this);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        groceryList = new ArrayList<>();
        listItems = new ArrayList<>();

        // Get items from database
        groceryList = db.getallgrocery();

        for (Grocery c : groceryList) {
            Grocery grocery = new Grocery();
            grocery.setName(c.getName());
            grocery.setQty("Qty: " + c.getQty());
            grocery.setId(c.getId());
            grocery.setDate("Added on: " + c.getDate());


            listItems.add(grocery);

        }
        recyclerViewAdapter = new Recyclerviewadapter(this, listItems);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerViewAdapter.notifyDataSetChanged();
    }
        private void createpoipdialog () {

            alertdialogbuilder = new AlertDialog.Builder(this);
            View view = getLayoutInflater().inflate(R.layout.popup, null);
            groceryItem = (EditText) view.findViewById(R.id.grocery_item);
            quantity = (EditText) view.findViewById(R.id.grocery_qty);
            saveButton = (Button) view.findViewById(R.id.button);


            alertdialogbuilder.setView(view);
            alertDialog = alertdialogbuilder.create();
            alertDialog.show();

            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    saveGrocerytodb(v);
                }
            });
        }
        private void saveGrocerytodb (View v) {


            Grocery grocery = new Grocery();

            String newGrocery = groceryItem.getText().toString();
            String geoceryquantity = quantity.getText().toString();

            grocery.setName(newGrocery);
            grocery.setQty(geoceryquantity);

            //Save to DB
            db.addgrocery(grocery);

            Snackbar.make(v, "Item Saved!", Snackbar.LENGTH_LONG).show();

            //Log.d("Item Added ID:", String.valueOf(db.getgrocerycount()));
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    alertDialog.dismiss();
                    //start a new activiry
                    startActivity(new Intent(Listactivity.this, Listactivity.class));
                    finish();

                }
            }, 1000);
        }



}


