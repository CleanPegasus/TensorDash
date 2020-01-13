package com.example.tensordash.view.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.tensordash.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);

        Button button = findViewById(R.id.button_create_new_key);
        button.setOnClickListener(v->{
            Intent intent = new Intent(LoginActivity.this, SignUpActivity   .class);
            startActivity(intent);
        });

    }
}
