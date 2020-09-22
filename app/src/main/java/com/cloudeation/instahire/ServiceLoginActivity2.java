package com.cloudeation.instahire;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ServiceLoginActivity2 extends AppCompatActivity {
    Button btnLogin;
    EditText etnuserId, etnPassword;
    TextView tvForget, tvRegister;
    private FirebaseAuth mAuth;
    SharedPreferences preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_login2);
        init();
        listenr();
        checkUserLoginORNot();
    }

    private void checkUserLoginORNot() {
        String email = preferences.getString("email","");
        if(!email.isEmpty()) {
            Intent intent = new Intent(ServiceLoginActivity2.this, ServicesActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }

    void init() {
        btnLogin = findViewById(R.id.btnLogin);
        etnuserId = findViewById(R.id.etnuserId);
        etnPassword = findViewById(R.id.etnpassword);
        tvForget = findViewById(R.id.tvForget);
        tvRegister = findViewById(R.id.tvRegister);
        mAuth = FirebaseAuth.getInstance();
        preferences = getSharedPreferences("MyPref",MODE_PRIVATE);
    }

    private void listenr() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = etnuserId.getText().toString();
                String password = etnPassword.getText().toString();
//                loginUser(email, password);
                boolean formStatus = formIsValid();
                if(formStatus) {
                    loginUser(email, password);
                }

            }

        });

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ServiceLoginActivity2.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
        tvForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ServiceLoginActivity2.this, forgetpassword.class));
            }
        });

    }
    private boolean formIsValid() {
        if(etnuserId.getText().toString().isEmpty()) {
            etnuserId.setError("please enter email Id");
            return false;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(etnuserId.getText().toString()).matches()) {
            etnuserId.setError("please enter valid email address");
            return false;
        }
        if(etnPassword.getText().toString().isEmpty()) {
            etnPassword.setError("please enter password");
            return false;
        }
        if(etnPassword.getText().toString().length() < 6) {
            etnPassword.setError("password must be 6 charachter or more");
            return false;
        }
        return true;
    }

    private void loginUser(final String email, final String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            if(user.isEmailVerified()) {
                                Toast.makeText(ServiceLoginActivity2.this, "Success: " + user.getUid().toString(),
                                        Toast.LENGTH_SHORT).show();

                                SharedPreferences.Editor pref = getSharedPreferences("MyPref", MODE_PRIVATE).edit();
                                pref.putString("email",email);
                                pref.putString("password",password);
                                pref.apply();

                                Intent intent = new Intent(ServiceLoginActivity2.this, ServicesActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);

                            } else {
                                Toast.makeText(ServiceLoginActivity2.this, "Please verify the email address",
                                        Toast.LENGTH_SHORT).show();
                                user.sendEmailVerification();
                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(ServiceLoginActivity2.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });


    }


}