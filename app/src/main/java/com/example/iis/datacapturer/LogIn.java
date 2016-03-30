package com.example.iis.datacapturer;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;


public class LogIn extends ActionBarActivity implements View.OnClickListener{

        EditText mName, mPass;
        Button logIn;
        TextView signUp;
        ProgressDialog pd;
        CheckBox logged;
        int switchez;
        public String name;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
            setContentView(R.layout.activity_log_in);
            mName = (EditText)findViewById(R.id.nameEdit);
            mPass = (EditText)findViewById(R.id.passEdit);
            logged = (CheckBox)findViewById(R.id.logCheck);
            logIn = (Button)findViewById(R.id.logBtn);
            signUp = (TextView)findViewById(R.id.signUpHandler);

            SharedPreferences sharedPreferences = getSharedPreferences("PREFS", 0);
            mName.setText(sharedPreferences.getString("userName", ""));


            logIn.setOnClickListener(this);
            signUp.setOnClickListener(this);
            ParseUser user = ParseUser.getCurrentUser();

            if (user != null){
                mName.setText(user.toString());
            }

            logged.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked){
                        switchez = 0;
                    }else{
                        switchez = 1;
                    }

                }
            });




            pd = new ProgressDialog(LogIn.this, R.style.Theme_AppCompat_Dialog);
            pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pd.setIndeterminate(true);
            pd.setMessage("Please wait...");

        }

        @Override
        public void onClick(View v) {
            name = mName.getText().toString();
            String password = mPass.getText().toString();

            switch (v.getId()){
                case R.id.logBtn:
                    if (name.isEmpty() && password.isEmpty()){
                        AlertDialog.Builder bill = new AlertDialog.Builder(LogIn.this, R.style.Theme_AppCompat_Dialog);
                        bill.setTitle("Error");
                        bill.setMessage("You have not entered your username and password");
                        bill.setIcon(android.R.drawable.ic_dialog_alert);
                        bill.setNegativeButton("Cancel", null);
                        bill.setPositiveButton("OK", null);
                        Dialog d = bill.create();
                        d.show();
                    }else if (name.length() <= 3 || password.length() < 6) {
                        AlertDialog.Builder bill = new AlertDialog.Builder(LogIn.this, R.style.Theme_AppCompat_Dialog);
                        bill.setTitle("Error");
                        bill.setMessage("Check the length of your text");
                        bill.setIcon(android.R.drawable.ic_dialog_alert);
                        bill.setNegativeButton("Cancel", null);
                        bill.setPositiveButton("OK", null);
                        Dialog d = bill.create();
                        d.show();
                        mPass.setText(null);

                    }else{
                        pd.show();

                        ParseUser.logInInBackground(name, password, new LogInCallback() {

                            @Override

                            public void done(ParseUser parseUser, ParseException e) {
                                pd.hide();
                                if (e == null) {
                                    Intent intent = new Intent(LogIn.this, MainActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                    Log.i("Bkay", "Login was successful");
                                } else {
                                    AlertDialog.Builder bill = new AlertDialog.Builder(LogIn.this, R.style.Theme_AppCompat_Dialog);
                                    bill.setTitle("Error");
                                    bill.setMessage(e.getMessage());
                                    bill.setIcon(android.R.drawable.ic_dialog_alert);
                                    bill.setNegativeButton("Cancel", null);
                                    bill.setPositiveButton("OK", null);
                                    Dialog d = bill.create();
                                    d.show();
                                }
                            }
                        });
                    }
                    break;
                case R.id.signUpHandler:
                    Intent intent = new Intent(getApplicationContext(), SignUp.class);
                    startActivity(intent);

                    break;
            }
        }

    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences sharedPreferences = getSharedPreferences("PREFS", 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("userName", mName.getText().toString());
        editor.apply();
    }
}



