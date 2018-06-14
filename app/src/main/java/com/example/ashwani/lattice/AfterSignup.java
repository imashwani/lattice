package com.example.ashwani.lattice;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ashwani.lattice.Data.UserApi;
import com.example.ashwani.lattice.Data.UserListPojo;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AfterSignup extends AppCompatActivity {
    private static final String TAG = "After Signup";
    private static AppDatabase db;
    TextView tv;
    private RecyclerView recyclerView;
    private List<User> userList = null;
    private List<UserListPojo> userListPojo;
    private UserAdapter mAdapter;
    private UserApi userApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_signup);
        userList=new ArrayList<>();


        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "user-database").build();

       if(isNetworkAvailable()){
           Toast.makeText(this, "Has network, getting data from server", Toast.LENGTH_SHORT).show();
           getDataOnline();

       }
       else{
           Toast.makeText(this, "No network, fetching data from RoomDatabase", Toast.LENGTH_SHORT).show();
           new FetchDataAsync().execute();
       }


    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.user_data_recycler_view);
        recyclerView.setHasFixedSize(true);
        mAdapter = new UserAdapter(userList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
    }

    public void getDataOnline() {
        Call<List<UserListPojo>> userListPojoCall = UserApi.getUserService().getUserList();
        userListPojoCall.enqueue(new Callback<List<UserListPojo>>() {
            @Override
            public void onResponse(Call<List<UserListPojo>> call, Response<List<UserListPojo>> response) {
                userListPojo = response.body();
                Toast.makeText(AfterSignup.this, "success", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onResponse: " + userListPojo);
                Log.d(TAG, "onResponse: fialed" + "\n" + response.raw());

                for (int i = 0; i < userListPojo.size(); i++) {
                    String username = userListPojo.get(i).getName(), userAddre = userListPojo.get(i).getAddress(),
                            email = userListPojo.get(i).getEmail(), password = userListPojo.get(i).getPassword(),
                            phone = userListPojo.get(i).getPhoneNo();
                    userList.add(new User(username, userAddre, email, phone, password));
                }
                initRecyclerView();


                if (userListPojo != null & tv != null)
                    tv.setText(userListPojo.get(0).getName() + "\n" + userListPojo.size());
            }

            @Override
            public void onFailure(Call<List<UserListPojo>> call, Throwable t) {
                //failed
                Toast.makeText(AfterSignup.this, "failed bitch", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onFailure: " + t);
            }
        });


    }

    private class FetchDataAsync extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            userList = db.userDao().getAll();
            Toast.makeText(AfterSignup.this,
                    "toal no of user in RoomDatabse " + userList.size(), Toast.LENGTH_SHORT).show();

            StringBuilder userStringBuilder = new StringBuilder("");
            for (int i = 0; i < userList.size(); i++) {
                User u = userList.get(i);
                userStringBuilder.append(u.getUserName() + " " + u.getUserAddress() + " \n");
            }

            return userStringBuilder.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            initRecyclerView();

        }
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
