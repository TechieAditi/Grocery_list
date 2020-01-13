package com.elab.grocery_list.UI;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.elab.grocery_list.Activity.Detailsactivity;
import com.elab.grocery_list.Data.Databasehandler;
import com.elab.grocery_list.Model.Grocery;
import com.elab.grocery_list.R;
import com.google.android.material.snackbar.Snackbar;

import java.security.Security;
import java.util.List;

public class Recyclerviewadapter extends RecyclerView.Adapter<Recyclerviewadapter.Viewholder> {
   private Context context;
   private AlertDialog.Builder alertdialogbuilder;
   private AlertDialog alertDialog;
   private LayoutInflater inflater;
   private List<Grocery> groceryitem;

    public Recyclerviewadapter(Context context, List<Grocery> groceryitem) {
        this.context = context;
        this.groceryitem = groceryitem;
    }

    @NonNull
    @Override
    public Recyclerviewadapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

       View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.listrow,parent,false);
        return new Viewholder(view,context);
    }

    @Override
    public void onBindViewHolder(@NonNull Recyclerviewadapter.Viewholder holder, int position) {
        Grocery  grocery=groceryitem.get(position);
        holder.groceryItemName.setText(grocery.getName());
        holder.quantity.setText(grocery.getQty());
        holder.dateAdded.setText(grocery.getDate());

    }

    @Override
    public int getItemCount() {
        return groceryitem.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder  implements View.OnClickListener {
        public TextView groceryItemName;
        public TextView quantity;
        public TextView dateAdded;
        public Button editButton;
        public Button deleteButton;
        public int id;
        public Viewholder(@NonNull View view,Context ctx) {
            super(view);
            context=ctx;
            groceryItemName = (TextView) view.findViewById(R.id.name);
            quantity = (TextView) view.findViewById(R.id.qty);
            dateAdded = (TextView) view.findViewById(R.id.date);

            editButton = (Button) view.findViewById(R.id.editbtn);
            deleteButton = (Button) view.findViewById(R.id.deletebtn);

            editButton.setOnClickListener(this);
            deleteButton.setOnClickListener(this);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // go to next screen
                    //details activity
                    int position=getAdapterPosition();
                    Grocery grocery=groceryitem.get(position);
                    Intent intent=new Intent(context, Detailsactivity.class);
                    intent.putExtra("name",grocery.getName());
                    intent.putExtra("Qty",grocery.getQty());
                    intent.putExtra("id",grocery.getId());
                    intent.putExtra("Date",grocery.getDate());
                    context.startActivity(intent);


                }
            });
        }

        @Override
        public void onClick(View view) {
            switch (view.getId())
            {
                case R.id.editbtn :
                    int position = getAdapterPosition();
                    Grocery grocery=groceryitem.get(position);


                    editItem(grocery);

                    break;
                case R.id.deletebtn :
                    position=getAdapterPosition();
                     grocery=groceryitem.get(position);
                    deleteitem(grocery.getId());
                    break;
            }

        }
        public void deleteitem(final int id)
        {
            alertdialogbuilder =new AlertDialog.Builder(context);
            inflater=LayoutInflater.from(context);
            View view=inflater.inflate(R.layout.conformationdialog,null);
            Button nobtn=(Button) view.findViewById(R.id.nobtn);
            Button yesbtn=(Button) view.findViewById(R.id.yesbtn);
            alertdialogbuilder.setView(view);
            alertDialog=alertdialogbuilder.create();
            alertDialog.show();
            nobtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    alertDialog.dismiss();
                }
            });
            yesbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Databasehandler db=new Databasehandler(context);
                    db.deletgrocery(id);
                    groceryitem.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                    alertDialog.dismiss();
                }
            });}
            public void editItem(final Grocery grocery) {

            alertdialogbuilder = new AlertDialog.Builder(context);

            inflater = LayoutInflater.from(context);
            final View view = inflater.inflate(R.layout.popup, null);

            final EditText groceryItem = (EditText) view.findViewById(R.id.grocery_item);
            final EditText quantity = (EditText) view.findViewById(R.id.grocery_qty);
            final TextView title = (TextView) view.findViewById(R.id.title);

            title.setText("Edit Grocery");
            Button saveButton = (Button) view.findViewById(R.id.button);


            alertdialogbuilder.setView(view);
            alertDialog= alertdialogbuilder.create();
            alertDialog.show();

            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Databasehandler db = new Databasehandler(context);

                    //Update item
                    grocery.setName(groceryItem.getText().toString());
                    grocery.setQty(quantity.getText().toString());

                    if (!groceryItem.getText().toString().isEmpty()
                            && !quantity.getText().toString().isEmpty()) {
                        db.updategrocery(grocery);
                        notifyItemChanged(getAdapterPosition(),grocery);
                    }else {
                        Snackbar.make(view, "Add Grocery and Quantity", Snackbar.LENGTH_LONG).show();
                    }

                    alertDialog.dismiss();

                }
            });

        }
    }
}
