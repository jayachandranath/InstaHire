package com.cloudeation.instahire;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

public class FirstActivity extends AppCompatActivity {
    LinearLayout linearcarpenter1, linearelectrician1, linearplumber1, linearpainter1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);


        linearcarpenter1 = findViewById(R.id.linearcarpenter1);
        linearelectrician1 = findViewById(R.id.linearelectrician1);
        linearplumber1 = findViewById(R.id.linearplumber1);
        linearpainter1 = findViewById(R.id.linearpainter1);

        listners();

    }

    private void listners() {

        linearcarpenter1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(FirstActivity.this, "Please login to view the Services", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(FirstActivity.this,MainActivity.class));
            }
        });


        linearelectrician1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(FirstActivity.this, "Please login to view the Services", Toast.LENGTH_SHORT).show();

                startActivity(new Intent(FirstActivity.this,MainActivity.class));
            }
        });

        linearplumber1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(FirstActivity.this, "Please login to view the Services", Toast.LENGTH_SHORT).show();

                startActivity(new Intent(FirstActivity.this,MainActivity.class));
            }
        });

        linearpainter1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(FirstActivity.this, "Please login to view the Services", Toast.LENGTH_SHORT).show();

                startActivity(new Intent(FirstActivity.this,MainActivity.class));

            }
        });


    }
}