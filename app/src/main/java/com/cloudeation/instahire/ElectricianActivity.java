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

public class ElectricianActivity extends AppCompatActivity implements ElectricianAdapter.OnListItemClick {

    private FirebaseFirestore firebaseFirestore;
    private RecyclerView mElectricianlist;
    private ElectricianAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_electrician);
        firebaseFirestore = FirebaseFirestore.getInstance();
        mElectricianlist = findViewById(R.id.electrician_firestore_list);
        CollectionReference Ref = firebaseFirestore.collection("serviceprovider");
        Query query = Ref.whereEqualTo("service","Electrician");
        FirestoreRecyclerOptions<ElectricianModel> options = new FirestoreRecyclerOptions.Builder<ElectricianModel>()
                .setQuery(query, ElectricianModel.class).build();

        adapter = new ElectricianAdapter(options, this);

        mElectricianlist.setHasFixedSize(true);
        mElectricianlist.setLayoutManager(new LinearLayoutManager(this));
        mElectricianlist.setAdapter(adapter);


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

        Log.d("ItemClick", "Clicked An Item :" + position);

    }
}