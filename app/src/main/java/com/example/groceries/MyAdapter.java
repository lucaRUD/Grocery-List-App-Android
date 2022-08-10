//package com.example.groceries;
//
//import static java.util.Collections.addAll;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AlertDialog;
//
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.groceries.Model.Data;
//import com.firebase.ui.database.FirebaseRecyclerAdapter;
//import com.firebase.ui.database.FirebaseRecyclerOptions;
//import com.google.android.gms.tasks.OnFailureListener;
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.Query;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//public class MyAdapter extends FirebaseRecyclerAdapter<Data, MyAdapter.MyViewHolder> {
//    ArrayList<Data> list;
//
//    public MyAdapter(@NonNull FirebaseRecyclerOptions<Data> options) {
//        super(options);
//    }
//
//
//    @Override
//    protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull Data model) {
//            Data data =list.get(position);
//            holder.setType(data.getType());
//            holder.setAmount(data.getAmount());
//            holder.setNote(data.getNote());
//            holder.setDate(data.getDate());
//
//    }
//
//    @NonNull
//    @Override
//    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
//        return  new MyViewHolder(view);
//
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    public static class MyViewHolder extends RecyclerView.ViewHolder{
//        View myview;
//
//        public MyViewHolder(@NonNull View itemView) {
//            super(itemView);
//            myview=itemView;
//
//        }
//        public void setType(String type){
//            TextView mType=myview.findViewById(R.id.tvtype);
//            mType.setText(type);
//        }
//        public void setNote(String note){
//            TextView mNote=myview.findViewById(R.id.tvNote);
//            mNote.setText(note);
//        }
//        public void setAmount(int ammount){
//            TextView mAmount=myview.findViewById(R.id.tvammount);
//            String stam=String.valueOf(ammount);
//            mAmount.setText(stam);
//        }
//        public void setDate(String date){
//            TextView mDate=myview.findViewById(R.id.tvdate);
//            mDate.setText(date);
//        }
//    }
//
//
//
//}
