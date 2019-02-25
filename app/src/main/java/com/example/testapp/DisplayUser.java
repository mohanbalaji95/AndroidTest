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
import com.example.testapp.Model.Individual;
import com.example.testapp.Model.UserDetails;
import com.example.testapp.adapters.RecyclerViewAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DisplayUser extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    public ArrayList<Individual> endResult;
    public int num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_user);
        setTitle("Users");
        endResult = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.dispRecycle);

        recyclerViewAdapter = new RecyclerViewAdapter();

        num = 4;

        //initial call to method
        retrofitMethod("users?page=1");

        recyclerViewAdapter.setIndividualsModelList(endResult);
        Log.e("disp", endResult.toString());
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
    }

    //Method called 4 times to access 4 different pages to obtain a total of 12 users and display them in RecyclerView
    public void retrofitMethod(String url)
    {
        //Retrofit initialization
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(DetailsRequestInterface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        DetailsRequestInterface drInterface = retrofit.create(DetailsRequestInterface.class);
        Call<UserDetails> call = drInterface.getJSON(url);

        call.enqueue(new Callback<UserDetails>() {
            @Override
            public void onResponse(@NonNull Call<UserDetails> call, @NonNull Response<UserDetails> response) {

                endResult.addAll(response.body().getData());

                switch (num)
                {
                    case 4:
                        num--;
                        retrofitMethod("users?page=2");
                        break;

                    case 3:
                        num--;
                        retrofitMethod("users?page=3");
                        break;

                    case 2:
                        num--;
                        retrofitMethod("users?page=4");
                        break;

                    case 1:
                        //adapter of RecyclerView is updated
                        recyclerViewAdapter.setIndividualsModelList(endResult);
                        recyclerView.setAdapter(recyclerViewAdapter);
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
                        break;
                }

            }

            @Override
            public void onFailure( @NonNull Call<UserDetails> call, @NonNull Throwable t) {
                Log.e("onFailure DisplayUser", t.toString());
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
