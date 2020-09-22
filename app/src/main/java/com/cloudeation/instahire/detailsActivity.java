package com.cloudeation.instahire;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class detailsActivity extends AppCompatActivity {

    public static final String TAG = "TAG";
    EditText firstName,lastName;
    Button saveBtn, location;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;
    private Spinner spinner_Services;
    List<ServicesList> servicelist;
    private ServicesAdapter servicesAdapter;
    String service = "";
    int TAKE_IMAGE_CODE = 10001;
    ImageView imageViewp;

    private String Mobile;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        location = findViewById(R.id.location);
        saveBtn = findViewById(R.id.saveBtn);
        imageViewp= findViewById(R.id.imageViewP);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
       userID = fAuth.getCurrentUser().getUid();
        spinner_Services = findViewById(R.id.spinner_Services);
        Mobile = fAuth.getCurrentUser().getPhoneNumber();


        servicelist = addData();
        servicesAdapter= new ServicesAdapter(detailsActivity.this,R.layout.custom_layout,servicelist);
        spinner_Services.setAdapter(servicesAdapter);
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user.getPhotoUrl() != null){
            Glide.with(this).load(user.getPhotoUrl()).into(imageViewp);
        }

        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(detailsActivity.this,WorkerlocationActivity.class));




            }
        });



        spinner_Services.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(detailsActivity.this, "Selected" +servicelist.get(position).getName(), Toast.LENGTH_SHORT).show();
                service = servicelist.get(position).getName();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });




        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(firstName.getText().toString().isEmpty()||lastName.getText().toString().isEmpty() ){
                    Toast.makeText(detailsActivity.this, "Fill the required Details", Toast.LENGTH_SHORT).show();
                    return;
                }

                DocumentReference docRef = fStore.collection("serviceprovider").document(userID);
                Map<String,Object> user = new HashMap<>();
                user.put("first",firstName.getText().toString());
                user.put("last",lastName.getText().toString());

                user.put("service",service);
                user.put("mobile", Mobile);





                //add user to database
                docRef.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "onSuccess: User Profile Created." + userID);

                        startActivity(new Intent(getApplicationContext(), HomeServiceprovider.class));
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "onFailure: Failed to Create User " + e.toString());
                    }
                });
            }
        });

    }



        private List<ServicesList> addData() {
        List<ServicesList> list = new ArrayList<>();
        list.add(new ServicesList(R.drawable.carser,"Carpenter"));
        list.add(new ServicesList(R.drawable.plumser,"Plumber"));
        list.add(new ServicesList(R.drawable.elecser,"Electrician"));
        list.add(new ServicesList(R.drawable.painter,"Painter"));
        return list;


    }

    public void handleImageClick(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (intent.resolveActivity(getPackageManager()) !=null) {
            startActivityForResult(intent, TAKE_IMAGE_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TAKE_IMAGE_CODE){
            switch (resultCode){
                case RESULT_OK:
                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    imageViewp.setImageBitmap(bitmap);
                    handleUpload(bitmap);
            }

        }
    }

    private void handleUpload(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos);
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        final StorageReference referance = FirebaseStorage.getInstance().getReference().child("profileImages").child(uid + ".jpeg");
        referance.putBytes(baos.toByteArray()).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                getDownloadUrl(referance);

            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "onFailure: ",e.getCause());
                    }
                });
    }
    private void getDownloadUrl (StorageReference reference){
        reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Log.d(TAG, "onSuccess: "+ uri);
                setUserProfileUrl(uri);
            }
        });

    }
    private void setUserProfileUrl(Uri uri){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        UserProfileChangeRequest request = new  UserProfileChangeRequest.Builder().setPhotoUri(uri).build();
        user.updateProfile(request).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(detailsActivity.this, "Profile Updated", Toast.LENGTH_SHORT).show();

            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(detailsActivity.this, "Profile Image upload Failed", Toast.LENGTH_SHORT).show();
                    }
                });

    }


}