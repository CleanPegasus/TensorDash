package com.example.tensordash.view.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.tensordash.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);
    }
}
