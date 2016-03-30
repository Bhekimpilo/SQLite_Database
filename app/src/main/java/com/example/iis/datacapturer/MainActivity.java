package com.example.iis.datacapturer;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseUser;


public class MainActivity extends Activity {

    EditText name;
    EditText surname;
    EditText dob;
    EditText occ;
    EditText home;
    EditText phone;
    EditText workPhone;
    EditText workAdd;
    EditText idNo;
    EditText notes;
    Button save, show;
    CheckBox male, female;
    String sex;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LogIn validate = new LogIn();


       ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser == null || validate.switchez == 1) {
            Intent intent = new Intent(MainActivity.this, LogIn.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }

        name = (EditText) findViewById(R.id.nameEdit);
        surname = (EditText) findViewById(R.id.surnameEdit);
        dob = (EditText) findViewById(R.id.dobEdit);
        occ = (EditText) findViewById(R.id.occEdit);
        home = (EditText) findViewById(R.id.homeAdEdit);
        phone = (EditText) findViewById(R.id.phoneEdit);
        workPhone = (EditText) findViewById(R.id.workPhoneEdit);
        workAdd = (EditText) findViewById(R.id.workAdEdit);
        notes = (EditText) findViewById(R.id.notesEdit);
        save = (Button) findViewById(R.id.sendBtn);
        show = (Button) findViewById(R.id.showBtn);
        male = (CheckBox) findViewById(R.id.mCheck);
        female = (CheckBox) findViewById(R.id.fCheck);
        idNo = (EditText) findViewById(R.id.idNoEdit);


        Intent intent = getIntent();
        if (intent != null) {
            String myName = intent.getStringExtra("name");
            String mySur = intent.getStringExtra("sur");

            name.setText(myName);
            surname.setText(mySur);

        }

        male.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    sex = "Male";
                    female.setChecked(false);
                }
            }
        });

        female.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        sex = "Female";
                        male.setChecked(false);
                    }
            }
            });


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cusName = name.getText().toString();
                String cusSurname = surname.getText().toString();
                String cusDOB = dob.getText().toString();
                String cusOcc = occ.getText().toString();
                String cusHomeAdd = home.getText().toString();
                String cusPhone = phone.getText().toString();
                String wkPhone = workPhone.getText().toString();
                String wkAdd = workAdd.getText().toString();
                String comments = notes.getText().toString();
                String reg = idNo.getText().toString();
                String gender = sex;

                name.requestFocus();

                cusName = cusName.trim();
                cusSurname = cusSurname.trim();
                cusDOB = cusDOB.trim();
                cusOcc = cusOcc.trim();
                cusHomeAdd = cusHomeAdd.trim();
                cusPhone = cusPhone.trim();
                wkPhone = wkPhone.trim();
                wkAdd = wkAdd.trim();
                comments = comments.trim();
                reg = reg.trim();


                if (cusName.isEmpty() || cusSurname.isEmpty() || cusDOB.isEmpty() || cusOcc.isEmpty() || cusHomeAdd.isEmpty() ||
                        cusPhone.isEmpty() || wkPhone.isEmpty() || wkAdd.isEmpty() || reg.isEmpty()) {

                    AlertDialog.Builder bkay = new AlertDialog.Builder(MainActivity.this);
                    bkay.setTitle("Error");
                    bkay.setIcon(android.R.drawable.ic_dialog_alert);
                    bkay.setMessage("Please fill in all the fields");
                    bkay.setNegativeButton("Ok", null);

                    Dialog dL = bkay.create();
                    dL.show();


                } else if (!female.isChecked() && !male.isChecked()){
                    Toast.makeText(MainActivity.this, "Please specify sex", Toast.LENGTH_LONG).show();
                }else {
                    SQLite lite = new SQLite(MainActivity.this);
                    lite.open();
                    lite.write(cusName, cusSurname, cusDOB, cusOcc, cusHomeAdd,
                            cusPhone, wkPhone, wkAdd, reg, comments, gender);
                    lite.close();
                    Log.i("Bkay", "Data was successfully written to the database");

                    name.setText(null);
                    surname.setText(null);
                    idNo.setText(null);
                    dob.setText(null);
                    occ.setText(null);
                    home.setText(null);
                    workPhone.setText(null);
                    workAdd.setText(null);
                    phone.setText(null);
                    male.setChecked(false);
                    female.setChecked(false);
                    notes.setText(null);


                    Toast.makeText(MainActivity.this, "Data has been saved", Toast.LENGTH_SHORT).show();


                }
            }
        });

        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, ListActo.class);
                intent.putExtra("sex", sex);
                startActivity(intent);

            }
        });


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater mI = getMenuInflater();
        mI.inflate(R.menu.options, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.logout) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Logout Confirmation ")
                    .setMessage("Are you sure you want to log out?")
                    .setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ParseUser.logOutInBackground();
                            Intent out = new Intent(MainActivity.this, LogIn.class);
                            out.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            out.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(out);
                        }
                    })
                    .setPositiveButton("NO", null)
                    .create().show();

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
        super.onStop();
        LogIn sign = new LogIn();
        if (sign.switchez == 0){
            Log.i("Bkay", ParseUser.getCurrentUser() + "id logged");
        }else if (sign.switchez == 1){
            ParseUser.logOut();
            Log.i("Bkay", "The app logged out" );
        }

    }
}