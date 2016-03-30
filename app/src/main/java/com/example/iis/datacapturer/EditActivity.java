package com.example.iis.datacapturer;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;


public class EditActivity extends ActionBarActivity {

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
    String sex; long myID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        name = (EditText) findViewById(R.id.nameUpdate);
        surname = (EditText) findViewById(R.id.surnameUpdate);
        dob = (EditText) findViewById(R.id.dobUpdate);
        occ = (EditText) findViewById(R.id.occUpdate);
        home = (EditText) findViewById(R.id.homeAdUpdate);
        phone = (EditText) findViewById(R.id.phoneUpdate);
        workPhone = (EditText) findViewById(R.id.workPhoneUpdate);
        workAdd = (EditText) findViewById(R.id.workAdUpdate);
        notes = (EditText) findViewById(R.id.notesUpdate);
        save = (Button) findViewById(R.id.sendBtn);
        male = (CheckBox) findViewById(R.id.mCheck);
        female = (CheckBox) findViewById(R.id.fCheck);
        idNo = (EditText) findViewById(R.id.idNoUpdate);

        Intent tent = getIntent();
        myID = tent.getLongExtra("id", 0);


        SQLite lite = new SQLite(EditActivity.this);
        lite.open();
         String mName = lite.retrieveName(myID);
        String mSurname = lite.retrieveSurname(myID);
        String mDOB = lite.retrieveDob(myID);
        String mOcc = lite.retrieveOcc(myID);
        String mHome = lite.retrieveHomeAd(myID);
        String mPhone = lite.retrieveCell(myID);
        String mWorkPh = lite.retrieveWorkPhone(myID);
        String mWorkAdd = lite.retrievewkAd(myID);
        String mNotes = lite.retrieveNotes(myID);
        String mIDno = lite.retrieveIdNo(myID);
        sex = lite.retrieveSex(myID);
        lite.close();

        name.setText(mName);
        surname.setText(mSurname);
        dob.setText(mDOB);
        occ.setText(mOcc);
        home.setText(mHome);
        phone.setText(mPhone);
        workPhone.setText(mWorkPh);
        workAdd.setText(mWorkAdd);
        notes.setText(mNotes);
        idNo.setText(mIDno);

        if (sex.equals("Male")){
            male.setChecked(true);
        }else{
            female.setChecked(true);
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

                String mName = name.getText().toString();
                String mSurname = surname.getText().toString();
                String mDOB = dob.getText().toString();
                String mOcc = occ.getText().toString();
                String mHome = home.getText().toString();
                String mPhone = phone.getText().toString();
                String mWorkPh = workPhone.getText().toString();
                String mWorkAdd = workAdd.getText().toString();
                String mIDno = idNo.getText().toString();
                String mNotes = notes.getText().toString();

                mName = mName.trim();
                mSurname = mSurname.trim();
                mDOB = mDOB.trim();
                mOcc = mOcc.trim();
                mHome = mHome.trim();
                mPhone = mPhone.trim();
                mWorkPh = mWorkPh.trim();
                mWorkAdd = mWorkAdd.trim();
                mNotes = mNotes.trim();
                mIDno = mIDno.trim();


                if (mName.isEmpty() || mSurname.isEmpty() || mDOB.isEmpty() || mOcc.isEmpty() || mHome.isEmpty() ||
                        mPhone.isEmpty() || mWorkPh.isEmpty() || mWorkAdd.isEmpty() || mIDno.isEmpty()) {

                    AlertDialog.Builder bkay = new AlertDialog.Builder(EditActivity.this);
                    bkay.setTitle("Error");
                    bkay.setIcon(android.R.drawable.ic_dialog_alert);
                    bkay.setMessage("Please fill in all the fields");
                    bkay.setNegativeButton("Ok", null);

                    Dialog dL = bkay.create();
                    dL.show();


                } else if (!female.isChecked() && !male.isChecked()) {
                    Toast.makeText(EditActivity.this, "Please specify sex", Toast.LENGTH_LONG).show();
                } else {

                    SQLite lie = new SQLite(EditActivity.this);
                    lie.open();
                    lie.editEntry(myID, mName, mSurname, mDOB, mOcc, mHome, mPhone, mWorkPh, mWorkAdd, mIDno, mNotes, sex);
                    lie.close();
                    Toast.makeText(EditActivity.this, "Entry was successfully updated", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(EditActivity.this, ListActo.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);


                }
            }
        });


    }

}
