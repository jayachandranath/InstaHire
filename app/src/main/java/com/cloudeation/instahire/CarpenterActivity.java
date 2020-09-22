package com.cloudeation.instahire;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;


public class CarpenterActivity extends AppCompatActivity implements CarpenterAdapter.OnListItemClick {

    private CarpenterAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carpenter);
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        final StorageReference referance = FirebaseStorage.getInstance().getReference().child("profileImages").child( uid+ ".jpeg");
        RecyclerView mCarpenterlist = findViewById(R.id.carpenter_firestore_list);
        CollectionReference Ref = firebaseFirestore.collection("serviceprovider");
        Query query = Ref.whereEqualTo("service", "Carpenter");
        FirestoreRecyclerOptions<CarpenterModel> options = new FirestoreRecyclerOptions.Builder<CarpenterModel>()
                .setQuery(query, CarpenterModel.class).build();
        adapter = new CarpenterAdapter(options, this);
        mCarpenterlist.setHasFixedSize(true);
        mCarpenterlist.setLayoutManager(new LinearLayoutManager(this));
        mCarpenterlist.setAdapter(adapter);
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

