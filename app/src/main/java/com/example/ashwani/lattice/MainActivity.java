package com.example.ashwani.lattice;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ashwani.lattice.Data.Resp;
import com.example.ashwani.lattice.Data.UserApi;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private EditText userNameET, passwordET, addressET, emailET, phoneET;
    private Button signupButton;
    private static AppDatabase db;

    private static boolean checkString(String str) {
        char ch;
        boolean length = false;
        boolean capitalFlag = false;
        boolean lowerCaseFlag = false;
        boolean numberFlag = false;
        if (str.length() < 16 && str.length() > 7)
            length = true;

        for (int i = 0; i < str.length(); i++) {
            ch = str.charAt(i);
            if (Character.isDigit(ch)) {
                numberFlag = true;
            } else if (Character.isUpperCase(ch)) {
                capitalFlag = true;
            } else if (Character.isLowerCase(ch)) {
                lowerCaseFlag = true;
            }
            if (numberFlag && capitalFlag && lowerCaseFlag && length)
                return true;
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();//find the views by id


        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "user-database").build();


        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatabaseAsync().execute();//for adding data to room database

                String username = userNameET.getText().toString(), useraddress =
                        addressET.getText().toString(), useremail = emailET.getText().toString(), userphoone =
                        phoneET.getText().toString(), userpass = passwordET.getText().toString();

                // posting
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("name", username);
                jsonObject.addProperty("address", useraddress);
                jsonObject.addProperty("email", useremail);
                jsonObject.addProperty("phone_no", userphoone);
                jsonObject.addProperty("password", userpass);


                Call<Resp> call = UserApi.getUserService().postUser(jsonObject);
                call.enqueue(new Callback<Resp>() {
                    @Override
                    public void onResponse(Call<Resp> call, Response<Resp> response) {
                        Toast.makeText(MainActivity.this, "posts happen", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "onResponse: of post" + response.raw());
                    }

                    @Override
                    public void onFailure(Call<Resp> call, Throwable t) {
                        Log.d(TAG, "onFailure: post wale " + t);
                    }
                });

                //going to new activity now
                Intent intent = new Intent(MainActivity.this, AfterSignup.class);
                startActivity(intent);
            }
        });


        userNameET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                isFormComplete();
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (userNameET.getText().toString().length() < 4) {
                    userNameET.setError("Name should be at least 4 letter long");

                    isFormComplete();
                }

            }
        });
        addressET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                isFormComplete();
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (addressET.getText().toString().length() < 10)
                    addressET.setError("Address should be at least 10 char long");
                isFormComplete();
            }
        });
        emailET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                isFormComplete();
            }

            @Override
            public void afterTextChanged(Editable s) {
                String email = emailET.getText().toString();
                if (!email.matches("([A-Za-z0-9-_.]+@[A-Za-z0-9-_]+(?:\\.[A-Za-z0-9]+)+)"))
                    emailET.setError("Not a valid email");
                isFormComplete();
            }
        });
        phoneET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                isFormComplete();
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (phoneET.getText().toString().length() < 10) {
                    phoneET.setError("Should be at least 10 digit with country code");
                    isFormComplete();
                }
            }
        });
        passwordET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                isFormComplete();
            }

            @Override
            public void afterTextChanged(Editable s) {

                String pass = passwordET.getText().toString();

                if (checkString(pass) == false)
                    passwordET.setError("password length >7 and <15 and must have uppercase, lowercase and a digit");
                isFormComplete();
            }
        });


    }

    private void isFormComplete() {
        if (emailET.getError() == null & userNameET.getError() == null && addressET.getError() == null
                && phoneET.getError() == null && passwordET.getError() == null) {
            signupButton.setVisibility(View.VISIBLE);
        } else {
            signupButton.setVisibility(View.INVISIBLE);
        }
    }

    private void findViews() {
        userNameET = findViewById(R.id.useer_name_et);
        passwordET = findViewById(R.id.user_password);
        addressET = findViewById(R.id.user_address_et);
        emailET = findViewById(R.id.user_email);
        phoneET = findViewById(R.id.user_phone);
        signupButton = findViewById(R.id.signup_button);
    }

    private class DatabaseAsync extends AsyncTask<Void, Void, Void> {

        User user;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            //Let's add some data to the Room database
            user = new User(userNameET.getText().toString(), addressET.getText().toString(), emailET.getText().toString(), phoneET.getText().toString(), passwordET.getText().toString());

            //Now access all the methods defined in DaoAccess with sampleDatabase object
            db.userDao().insertAll(user);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(MainActivity.this, "Added to database", Toast.LENGTH_SHORT).show();
            //To after addition operation here.
        }
    }
}
