package com.elab.grocery_list.Activity;

import android.app.Dialog;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;

import com.elab.grocery_list.Data.Databasehandler;
import com.elab.grocery_list.Model.Grocery;
import com.elab.grocery_list.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.solver.GoalRow;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private AlertDialog.Builder dialogbuilder;
    private AlertDialog dialog;
    private EditText groceryitem;
    private EditText groceryqty;
    private Button savebtn;
    private Databasehandler db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        db=new Databasehandler(this);
        bypassActivity();


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        //.setAction("Action", null).show();
                createpoipdialog();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
   private void  createpoipdialog(){

        dialogbuilder = new AlertDialog.Builder(this);
        View view =getLayoutInflater().inflate(R.layout.popup,null);
        groceryitem=(EditText) view.findViewById(R.id.grocery_item);
        groceryqty=(EditText) view.findViewById(R.id.grocery_qty);
        savebtn=(Button) view.findViewById(R.id.button);
        dialogbuilder.setView(view);
        dialog=dialogbuilder.create();
        dialog.show();
        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //save to database
                //go to next screen
                if(!groceryitem.getText().toString().isEmpty()&& !groceryqty.getText().toString().isEmpty())
                saveGrocerytodb(view);

            }
        });

   }
   private void  saveGrocerytodb(View v)
   {


       Grocery grocery = new Grocery();

       String newGrocery = groceryitem.getText().toString();
       String newGroceryQuantity = groceryqty.getText().toString();

       grocery.setName(newGrocery);
       grocery.setQty(newGroceryQuantity);

       //Save to DB
       db.addgrocery(grocery);

       Snackbar.make(v, "Item Saved!", Snackbar.LENGTH_LONG).show();

        //Log.d("Item Added ID:", String.valueOf(db.getgrocerycount()));
       new Handler().postDelayed(new Runnable() {
           @Override
           public void run() {
               dialog.dismiss();
               //start a new activiry
               startActivity(new Intent(MainActivity.this, Listactivity.class));

           }
       }, 1000);
   }
   public void bypassActivity()
   {
       if(db.getgrocerycount()>0)
       {
           startActivity(new Intent(MainActivity.this,Listactivity.class));
           finish();

       }
   }
}
