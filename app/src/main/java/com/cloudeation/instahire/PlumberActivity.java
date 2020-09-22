package com.cloudeation.instahire;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class PlumberActivity extends AppCompatActivity implements PlumberFirestoreAdapter.OnListItemClick {

    private FirebaseFirestore firebaseFirestore;
    private RecyclerView mPlumberList;
    private PlumberFirestoreAdapter  adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plumber);
        firebaseFirestore = FirebaseFirestore.getInstance();
        mPlumberList = findViewById(R.id.plumber_firestore_list);
        CollectionReference Ref = firebaseFirestore.collection("serviceprovider");
        Query query = Ref.whereEqualTo("service","Plumber");
        FirestoreRecyclerOptions<PlumberModel> options = new FirestoreRecyclerOptions.Builder<PlumberModel>()
                .setQuery(query, PlumberModel.class).build();

         adapter = new PlumberFirestoreAdapter(options,this);

         mPlumberList.setHasFixedSize(true);
         mPlumberList.setLayoutManager(new LinearLayoutManager(this));
         mPlumberList.setAdapter(adapter);



    }



    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }
    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public void onItemClick(int position) {

        Log.d("ItemClick", "Clicked An Item :"+ position );

    }
}