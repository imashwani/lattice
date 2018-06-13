package com.example.ashwani.lattice;

import android.arch.persistence.room.Room;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.List;

public class AfterSignup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_signup);


       AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "user-database").build();

       List<User> userList=db.userDao().getAll();

       StringBuilder userStringBuilder=new StringBuilder("");
       for(int i=0;i<userList.size();i++){

           User u=userList.get(i);
           userStringBuilder.append(u.getUserName()+" "+u.getUserAddress()+" \n");
       }

    }
}
