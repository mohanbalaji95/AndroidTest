package com.example.testapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onCreateClicked(View v)
    {
        CreateUser.start(this);
    }

    public void onDispClicked(View v)
    {
        DisplayUser.start(this);
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}
