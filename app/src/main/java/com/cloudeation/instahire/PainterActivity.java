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

public class PainterActivity extends AppCompatActivity implements PainterAdapter.OnListItemClick {

private FirebaseFirestore firebaseFirestore;
private RecyclerView mPainterlist;
private PainterAdapter  adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_painter);firebaseFirestore = FirebaseFirestore.getInstance();
        mPainterlist = findViewById(R.id.painter_firestore_list);
        CollectionReference Ref = firebaseFirestore.collection("serviceprovider");
        Query query = Ref.whereEqualTo("service","Painter");
        FirestoreRecyclerOptions<PainterModel> options = new FirestoreRecyclerOptions.Builder<PainterModel>()
                .setQuery(query, PainterModel.class).build();
        adapter = new PainterAdapter(options,this);

        mPainterlist.setHasFixedSize(true);
        mPainterlist.setLayoutManager(new LinearLayoutManager(this));
        mPainterlist.setAdapter(adapter);



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