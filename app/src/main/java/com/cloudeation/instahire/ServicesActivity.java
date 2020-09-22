package com.cloudeation.instahire;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;

public class ServicesActivity extends AppCompatActivity {
    LinearLayout linearcarpenter, linearplumber,linearelectrician,linearpainter;
    SharedPreferences.Editor prefEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);
        linearcarpenter = findViewById(R.id.linearcarpenter);
        prefEditor = getSharedPreferences("MyPref", MODE_PRIVATE).edit();
        linearplumber = findViewById(R.id.linearplumber);
        linearpainter = findViewById(R.id.linearpainter);
        linearelectrician = findViewById(R.id.linearelectrician);

        listners();
    }

    private void listners() {
        linearcarpenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ServicesActivity.this,CarpenterActivity.class));
            }
        });
        linearplumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ServicesActivity.this, PlumberActivity.class));
            }
        });
        linearelectrician.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ServicesActivity.this,ElectricianActivity.class));
            }
        });
        linearpainter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ServicesActivity.this,PainterActivity.class));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.logout_menu,menu);
       super.onCreateOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.logout){
            prefEditor.clear();
            prefEditor.apply();
            Intent intent = new Intent(ServicesActivity.this,FirstActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);


        }
        return super.onOptionsItemSelected(item);
    }
}