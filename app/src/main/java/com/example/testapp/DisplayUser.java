package com.example.testapp;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.RelativeLayout;

import com.example.testapp.Interface.DetailsRequestInterface;
import com.example.testapp.Model.UserDetails;
import com.example.testapp.adapters.RecyclerViewAdapter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DisplayUser extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_user);
        setTitle("Users");

        recyclerView = (RecyclerView) findViewById(R.id.dispRecycle);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        recyclerViewAdapter = new RecyclerViewAdapter();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(DetailsRequestInterface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        DetailsRequestInterface drInterface = retrofit.create(DetailsRequestInterface.class);
        Call<UserDetails> call = drInterface.getJSON();
        UserDetails u = new UserDetails();
        String val;
        if(u.getTotalPages() != null){
            val = u.getTotalPages().toString();
        }
        else
        {
            val = "Nothing yet";
        }
        Log.e("DisplayUser", val);



        call.enqueue(new Callback<UserDetails>() {
            @Override
            public void onResponse(@NonNull Call<UserDetails> call, @NonNull Response<UserDetails> response) {

                Log.e("Inside", response.body().getTotalPages().toString());
                Log.e("inside a","hi");
                recyclerViewAdapter.setIndividualsModelList(response.body().getData());
                recyclerView.setAdapter(recyclerViewAdapter);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
            }

            @Override
            public void onFailure( @NonNull Call<UserDetails> call, @NonNull Throwable t) {

            }
        });
    }

    public static void start(Context context)
    {
        Intent starter = new Intent(context, DisplayUser.class);
        context.startActivity(starter);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
