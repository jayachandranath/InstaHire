package com.cloudeation.instahire;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;


public class MainActivity extends AppCompatActivity {
    LinearLayout linearUser,linearRegister;
    ImageView Im1,Im2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         linearUser = findViewById(R.id.linearUser);
         linearRegister = findViewById(R.id.linearRegister);
         Im1 = findViewById(R.id.ImageUser);
         Im2 = findViewById(R.id.ImageService);



        listners();
    }

    private void listners() {

        Im1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,ServiceLoginActivity2.class);
                startActivity(intent);
            }
        });
        Im2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,UserActivity.class);
                startActivity(intent);
            }
        });
    }


}
