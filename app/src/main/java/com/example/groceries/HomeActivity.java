package com.example.groceries;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

import com.example.groceries.Model.Data;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class HomeActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private FloatingActionButton fab_btn;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    newAdapter myAdapter;
    ArrayList<Data> list;

    RecyclerView recyclerView;
    TextView totalitemamount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        recyclerView = (RecyclerView)findViewById(R.id.recycler_home);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mDatabase=FirebaseDatabase.getInstance().getReference("Grocery List");

        toolbar = findViewById(R.id.home_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Grocery List");

        totalitemamount=findViewById(R.id.amountvalue);


        mAuth = FirebaseAuth.getInstance();
        FirebaseUser mUser = mAuth.getCurrentUser();
        String uId = mUser.getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Grocery List").child(uId);
        mDatabase.keepSynced(true);



        recyclerView.setHasFixedSize(true);




        fab_btn = findViewById(R.id.fab);
        fab_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customDialog();
            }
        });
        list = new ArrayList<>();
        myAdapter = new newAdapter(this,list);
        recyclerView.setAdapter(myAdapter);



        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    int totalamount=0;
                    Data data=dataSnapshot.getValue(Data.class);
                    list.add(data);
                    totalamount+=myAdapter.getItemCount();
                    String sttotal=String.valueOf(totalamount);
                    totalitemamount.setText(sttotal);



                }

                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }




    private void customDialog() {

        AlertDialog.Builder mydialog = new AlertDialog.Builder(HomeActivity.this);

        LayoutInflater inflater = LayoutInflater.from(HomeActivity.this);
        View myview = inflater.inflate(R.layout.input_data, null);
        AlertDialog dialog = mydialog.create();

        dialog.setView(myview);

        EditText type = myview.findViewById(R.id.edt_type);
        EditText amount = myview.findViewById(R.id.edt_amount);
        EditText note = myview.findViewById(R.id.edt_note);
        Button btnSave = myview.findViewById(R.id.btn_save);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String mType = type.getText().toString().trim();
                String mAmount = amount.getText().toString().trim();
                String mNote = note.getText().toString().trim();
                String id = mDatabase.push().getKey();

                int ammount = Integer.parseInt(mAmount);

                if (TextUtils.isEmpty(mType)) {
                    type.setError("Required Field..");
                    return;
                }
                if (TextUtils.isEmpty(mAmount)) {
                    amount.setError("Required Field..");
                    return;
                }
                if (TextUtils.isEmpty(mNote)) {
                    note.setError("Required Field..");
                    return;
                }


                String date = DateFormat.getDateInstance().format(new Date());
                Data data = new Data(mType,ammount, mNote, date, id);
                mDatabase.child(id).setValue(data)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            Toast.makeText(getApplicationContext(), "Data Add", Toast.LENGTH_SHORT);
                                        }
                                    }
                                });



                dialog.dismiss();
            }
        });
        dialog.show();
    }



    public void updateData(){
        AlertDialog.Builder mydialog=new AlertDialog.Builder(HomeActivity.this);
        LayoutInflater inflater=LayoutInflater.from(HomeActivity.this);
        View myView=inflater.inflate(R.layout.update_input_field,null);
        AlertDialog dialog=mydialog.create();
        dialog.setView((myView));
        dialog.show();

        EditText edt_Type=myView.findViewById(R.id.edt_type_update);
        EditText edt_Amount=myView.findViewById(R.id.edt_amount_update);
        EditText edt_Note=myView.findViewById(R.id.edt_note_update);

        Button btnUpdate=myView.findViewById(R.id.btn_save_update);
        Button btnDelete=myView.findViewById(R.id.btn_delete);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mType=edt_Type.getText().toString().trim();
                String mAmount=edt_Amount.getText().toString().trim();
                String mNote=edt_Note.getText().toString().trim();

            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }




}
