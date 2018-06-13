package com.example.ashwani.lattice;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.ashwani.lattice.Data.ApiUtils;
import com.example.ashwani.lattice.Data.UserJson2pojoSchema;
import com.example.ashwani.lattice.Data.UserService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AfterSignup extends AppCompatActivity {
private  RecyclerView recyclerView;
private UserService userService;
    List<User> userList;
    UserAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_signup);
        userList=new ArrayList<>();

        userService= ApiUtils.getUserService();


       AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "user-database").build();

       if(isNetworkAvailable()){
           userService.getAllUser().enqueue(new Callback<UserJson2pojoSchema>() {
               @Override
               public void onResponse(Call<UserJson2pojoSchema> call, Response<UserJson2pojoSchema> response) {

                   userList.add(new User(response.body().getName(),response.body().getAddress(),
                           response.body().getEmail(),response.body().getPhoneNo(),
                           response.body().getPassword()));
               }

               @Override
               public void onFailure(Call<UserJson2pojoSchema> call, Throwable t) {
                   Toast.makeText(getBaseContext(),"Some error occured",Toast.LENGTH_LONG).show();
               }
           });
       }
       else{
           userList=db.userDao().getAll();
       }

       StringBuilder userStringBuilder=new StringBuilder("");
       for(int i=0;i<userList.size();i++){
           User u=userList.get(i);
           userStringBuilder.append(u.getUserName()+" "+u.getUserAddress()+" \n");
       }

        recyclerView = (RecyclerView) findViewById(R.id.user_data_recycler_view);
       recyclerView.setHasFixedSize(true);
         mAdapter = new UserAdapter(userList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
