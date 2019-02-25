package com.example.testapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.testapp.Interface.CreateResponse;
import com.example.testapp.Model.CreateUserModel;
import com.google.gson.Gson;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CreateUser extends AppCompatActivity {

    public EditText editName;
    public EditText editJob;
    public Button click;

    private CreateUserModel cUser;
    public static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl("https://reqres.in/api/")
            .addConverterFactory(GsonConverterFactory.create());

    public static Retrofit retrofit = builder.build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);
        setTitle("Create User");


        click = (Button) findViewById(R.id.clickMe);
        editName = (EditText) findViewById(R.id.name);
        editJob = (EditText) findViewById(R.id.job);

        String name, job;

        if(savedInstanceState!= null)
        {
            name = savedInstanceState.getString("name");
            job = savedInstanceState.getString("job");

            if(name != null)
            {
                editName.setText(name);
            }
            if(job != null)
            {
                editJob.setText(job);
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("name", editName.getText().toString());
        outState.putString("job", editJob.getText().toString());
    }

    public static void start(Context context)
    {
        Intent starter = new Intent(context, CreateUser.class);
        context.startActivity(starter);
    }

    //onClick method for creating user using retrofit and displaying the response
    public void clickMe(View view)
    {
        String nameUser = editName.getText().toString(), jobUser = editJob.getText().toString();

        CreateResponse cr =retrofit.create(CreateResponse.class);
        Call<ResponseBody> call = cr.sendUser(nameUser, jobUser);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                ResponseBody sms = response.body();

                try {
                    Gson gson = new Gson();
                    String json = sms.string();
                    cUser = gson.fromJson(json, CreateUserModel.class);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(CreateUser.this, android.R.style.Theme_Material_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(CreateUser.this);
                }
                builder.setTitle("Response on success:")
                        .setMessage("Code: " + cUser.getName() + "\nID: " + cUser.getId() + "\nCreated at: " + cUser.getCreatedAt())
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                onBackPressed();
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("onFailure in CreateUser", t.toString());
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
