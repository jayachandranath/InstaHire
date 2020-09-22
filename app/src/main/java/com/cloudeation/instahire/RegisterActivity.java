package com.cloudeation.instahire;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    Button btnRegister;
    EditText etfirstname, etlastname, etMobile, etEmailId, etnpassward;
    ProgressBar progressBar;
    private FirebaseAuth mAuth;


    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
        listenr();



    }

    private void init() {
        btnRegister = findViewById(R.id.btnRegister);
        etfirstname = findViewById(R.id.etfirstname);
        etlastname = findViewById(R.id.etlastname);
        etMobile = findViewById(R.id.etnMobile);
        etEmailId = findViewById(R.id.etnEmailId);
        etnpassward = findViewById(R.id.etnpassward);
        progressBar = findViewById(R.id.progressBar);
        mAuth = FirebaseAuth.getInstance();

    }

    private void listenr() {
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean formStatus = formIsValid();
                if(formStatus) {
                    progressBar.setVisibility(View.VISIBLE);
                    String email = etEmailId.getText().toString();
                    String password = etnpassward.getText().toString();
                    signUpUser(email, password);
                }
            }
        });
    }

    private boolean formIsValid() {
        if(etfirstname.getText().toString().isEmpty()) {
//            etfirstname.setError("please enter first name");
            showToast("please enter first name");
            return false;
        }
        if(etlastname.getText().toString().isEmpty()) {
            etlastname.setError("please enter last name");
            return false;
        }
        if(etMobile.getText().toString().isEmpty()) {
            etMobile.setError("please enter mobile no.");
            return false;
        }
        if(etEmailId.getText().toString().isEmpty()) {
            etEmailId.setError("please enter email Id");
            return false;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(etEmailId.getText().toString()).matches()) {
            etEmailId.setError("please enter valid email address");
            return false;
        }
        if(etnpassward.getText().toString().isEmpty()) {
            etnpassward.setError("please enter password");
            return false;
        }
        if(etnpassward.getText().toString().length() < 6) {
            etnpassward.setError("password must be 6 charachter or more");
            return false;
        }

        return true;
    }

    void  showToast(String msg) {
        Toast.makeText(RegisterActivity.this, msg,Toast.LENGTH_SHORT).show();
    }

    private void signUpUser(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            progressBar.setVisibility(View.GONE);
                            Log.d("TAG", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            user.sendEmailVerification();
                            storeUserDetails(user);
                        } else {
                            progressBar.setVisibility(View.GONE);
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }

    private void storeUserDetails(FirebaseUser user) {
        Map<String, Object> userData = new HashMap<>();
        userData.put("firstName", etfirstname.getText().toString());
        userData.put("lastName", etlastname.getText().toString());
        userData.put("mobile", etMobile.getText().toString());
        userData.put("email", etEmailId.getText().toString());
        userData.put("password", etnpassward.getText().toString());




        db.collection("users").document(user.getUid()).set(userData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(RegisterActivity.this, "Please veryfy your email address",
                                Toast.LENGTH_SHORT).show();
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG", "Error adding document", e);
                    }
                });
    }


}
