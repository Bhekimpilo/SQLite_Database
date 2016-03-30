package com.example.iis.datacapturer;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;


public class SignUp extends ActionBarActivity {


    EditText mName, mEmail, mPass;
    Button click;
    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mName = (EditText)findViewById(R.id.nameEdit);
        mEmail = (EditText)findViewById(R.id.emailEdit);
        mPass = (EditText)findViewById(R.id.passEdit);
        click = (Button)findViewById(R.id.signUpBtn);

        pd = new ProgressDialog(SignUp.this, R.style.Theme_AppCompat_Dialog);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setIndeterminate(true);
        pd.setMessage("Please wait...");

        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = mName.getText().toString();
                String password = mPass.getText().toString();
                String email = mEmail.getText().toString();

                if (name.isEmpty() || password.isEmpty() || email.isEmpty()) {

                    AlertDialog.Builder bill = new AlertDialog.Builder(SignUp.this, R.style.Theme_AppCompat_Dialog);
                    bill.setTitle("Error");
                    bill.setMessage("Please fill in all fields");
                    bill.setIcon(android.R.drawable.ic_dialog_alert);
                    bill.setNegativeButton("Cancel", null);
                    bill.setPositiveButton("OK", null);
                    Dialog d = bill.create();
                    d.show();
                } else if (name.length() < 3 || password.length() < 6 || !email.contains("@")) {
                    AlertDialog.Builder bill = new AlertDialog.Builder(SignUp.this, R.style.Theme_AppCompat_Dialog);
                    bill.setTitle("Error");
                    bill.setMessage("Please make sure your details are authentic");
                    bill.setIcon(android.R.drawable.ic_dialog_alert);
                    bill.setNegativeButton("Cancel", null);
                    bill.setPositiveButton("OK", null);
                    Dialog d = bill.create();
                    d.show();
                } else {

                    pd.show();
                    ParseUser user = new ParseUser();
                    user.setUsername(name);
                    user.setPassword(password);
                    user.setEmail(email);
                    user.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {
                            pd.hide();
                            if (e == null) {
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            } else {
                                AlertDialog.Builder bill = new AlertDialog.Builder(SignUp.this, R.style.Theme_AppCompat_Dialog);
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
            }
        });
    }

}
