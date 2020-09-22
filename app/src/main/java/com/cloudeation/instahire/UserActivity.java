package com.cloudeation.instahire;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.rilixtech.widget.countrycodepicker.CountryCodePicker;

import java.util.concurrent.TimeUnit;

public class UserActivity extends AppCompatActivity {
    public static final String TAG = "TAG";
    FirebaseAuth fAuth;
    EditText Phonenumber,Codeenter;
     Button nextBtn;
     String phoneNumber = "";
     String otpCode = "";
     ProgressBar progressbar;
     TextView state, resend;
     CountryCodePicker codePicker;
     String verificationId;
     PhoneAuthCredential credential;
    PhoneAuthProvider.ForceResendingToken token;
    Boolean verificationInprogress = false;
    FirebaseFirestore fStore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        fAuth = FirebaseAuth.getInstance();
        Phonenumber = findViewById(R.id.phone);//phone
        Codeenter = findViewById(R.id.codeEnter);//otpenter
        nextBtn = findViewById(R.id.nextBtn);//nexg
        progressbar = findViewById(R.id.progressBar);
        state = findViewById(R.id.state);
        codePicker= findViewById(R.id.ccp);//countrycodepicker
        fStore = FirebaseFirestore.getInstance();
        resend = findViewById(R.id.resendOtpBtn);
        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        listner();
    }

    private void listner() {

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!Phonenumber.getText().toString().isEmpty() && Phonenumber.getText().toString().length() == 10) {
                    if(!verificationInprogress){
                        nextBtn.setEnabled(false);
                        progressbar.setVisibility(View.VISIBLE);
                        state.setVisibility(View.VISIBLE);
                        String phoneNum = "+"+codePicker.getSelectedCountryCode()+Phonenumber.getText().toString();
                        Log.d("phone", "Phone No.: " + phoneNum);
                        requestOTP(phoneNum);
                    }else {
                        nextBtn.setEnabled(false);
                        Codeenter.setVisibility(View.GONE);
                        progressbar.setVisibility(View.VISIBLE);
                        state.setText("Logging in");
                        state.setVisibility(View.VISIBLE);
                        otpCode = Codeenter.getText().toString();
                        if(otpCode.isEmpty()){
                            Codeenter.setError("Required");
                            return;
                        }

                        credential = PhoneAuthProvider.getCredential(verificationId,otpCode);
                        verifyAuth(credential);
                    }

                }else {
                    Phonenumber.setError("Valid Phone Required");
                }
            }
        });

    }

    private void verifyAuth(PhoneAuthCredential credential) {
        fAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(UserActivity.this, "Authentication is Successful", Toast.LENGTH_SHORT).show();
                    checkUserProfile();
                }else {
                    progressbar.setVisibility(View.GONE);
                    state.setVisibility(View.GONE);
                    Toast.makeText(UserActivity.this, "Authentication Failed", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (fAuth.getCurrentUser() != null){
            progressbar.setVisibility(View.VISIBLE);
            state.setText("Checking");
            state.setVisibility(View.VISIBLE);
            checkUserProfile();
        }
    }

    private void checkUserProfile() {
        DocumentReference docRef = fStore.collection("serviceprovider").document(fAuth.getCurrentUser().getUid());
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()){
                    startActivity(new Intent(getApplicationContext(),HomeServiceprovider.class));
                    finish();


                }else {
                    startActivity(new Intent(getApplicationContext(),detailsActivity.class));
                    finish();

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UserActivity.this, "Profile donot Exist", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void requestOTP(String phonenum) {

        PhoneAuthProvider.getInstance().verifyPhoneNumber(phonenum, 60L, TimeUnit.SECONDS, this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                progressbar.setVisibility(View.GONE);
                state.setVisibility(View.GONE);
                verificationId = s;
                token = forceResendingToken;
                Codeenter.setVisibility(View.VISIBLE);
                nextBtn.setText("Verify");
                nextBtn.setEnabled(true);

                verificationInprogress = true;

            }

            @Override
            public void onCodeAutoRetrievalTimeOut(@NonNull String s) {
                super.onCodeAutoRetrievalTimeOut(s);
                Toast.makeText(UserActivity.this, "OTP Time Out, Please Re-generate The OTP again.", Toast.LENGTH_SHORT).show();
                resend.setVisibility(View.VISIBLE);
            }

            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                verifyAuth(phoneAuthCredential);

            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Toast.makeText(UserActivity.this, "Cannot Create Account" +e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}