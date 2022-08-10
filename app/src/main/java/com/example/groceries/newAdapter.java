package com.example.groceries;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.groceries.Model.Data;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class newAdapter extends  RecyclerView.Adapter<newAdapter.myviewholder>
{
    Context context;

    ArrayList<Data> list;
    String type;
    int amount;
    String note;
    String date;
    DatabaseReference database;

    public newAdapter(Context context, ArrayList<Data> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position) {
        Data data = list.get(position);
        holder.type.setText(data.getType());
        holder.ammount.setText(String.valueOf(data.getAmount()));
        holder.note.setText(data.getNote());
        holder.date.setText(data.getDate());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mydialog=new AlertDialog.Builder(holder.itemView.getContext());
                LayoutInflater inflater=LayoutInflater.from(holder.itemView.getContext());
                View myView=inflater.inflate(R.layout.update_input_field,null);
                AlertDialog dialog=mydialog.create();
                dialog.setView((myView));
                dialog.show();
                type= data.getType();
                note= data.getNote();
                amount=data.getAmount();


                EditText edt_Type=myView.findViewById(R.id.edt_type_update);
                EditText edt_Amount=myView.findViewById(R.id.edt_amount_update);
                EditText edt_Note=myView.findViewById(R.id.edt_note_update);

                edt_Type.setText(type);
                edt_Type.setSelection(type.length());

                edt_Amount.setText(String.valueOf(amount));
                edt_Amount.setSelection(String.valueOf(amount).length());

                edt_Note.setText(note);
                edt_Note.setSelection(note.length());

                Button btnUpdate=myView.findViewById(R.id.btn_save_update);
                Button btnDelete=myView.findViewById(R.id.btn_delete);

                btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Map<String,Object> map = new HashMap<>();
                        map.put("type",edt_Type.getText().toString());
                        map.put("amount",edt_Amount.getText().toString());
                        map.put("note",edt_Note.getText().toString());



                        FirebaseDatabase.getInstance().getReference().child("Grocery List").child(data.getId())
                                .updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        dialog.dismiss();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        dialog.dismiss();
                                    }
                                });




                    }
                });

                btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        database = FirebaseDatabase.getInstance().getReference().child("Grocery List");

                    database.child("Grocery List").child("type").removeValue();
                    dialog.dismiss();

                    }
                });
            }
        });
//

    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(context).inflate(R.layout.item,parent,false);
        return  new myviewholder(v);
    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    class myviewholder extends RecyclerView.ViewHolder
    {

        TextView type,note,date,ammount;
        public myviewholder(@NonNull View itemView)
        {
            super(itemView);
            type=(TextView)itemView.findViewById(R.id.tvtype);
            note=(TextView)itemView.findViewById(R.id.tvNote);
            date=(TextView)itemView.findViewById(R.id.tvdate);
            ammount=(TextView) itemView.findViewById(R.id.tvammount);
        }
    }
}