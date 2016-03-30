package com.example.iis.datacapturer;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;


public class Data extends ActionBarActivity {

    TextView tv;

    String gender;
    String name;
    String lastName;
    Intent intent;
    String info, sur;
    Arrange arrange;
    String regNo;
    long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        SharedPreferences pref = getSharedPreferences("MY_PREFS", MODE_PRIVATE);

        tv = (TextView) findViewById(R.id.textView);
        SQLite sql = new SQLite(Data.this);


        intent = getIntent();
        gender = intent.getStringExtra("sex");
        name = intent.getStringExtra("name");
        lastName = intent.getStringExtra("sur");
        regNo = intent.getStringExtra("regNo");
        String occ = intent.getStringExtra("occ");
        String born = intent.getStringExtra("dob");
        String cell = intent.getStringExtra("cell");
        String homeAdd = intent.getStringExtra("home");
        String workPhone = intent.getStringExtra("workPhone");
        String workAdd = intent.getStringExtra("workAdd");
        String notes = intent.getStringExtra("notes");
        id = intent.getLongExtra("id", 0);



        tv.setText(     "Name    :   " + name + "\n" +
                        "Surname :   " + lastName + "\n" +
                        "Gender  :   " + gender + "\n"  +
                        "Nat Reg :   " + regNo + "\n" +
                        "D.O.B   :   " + born + "\n" +
                        "Cell    :   " + cell + "\n" +
                        "Ward    :   " + workAdd + "\n" +
                        "Village :   " + occ + "\n" +
                        "Garden  :   " + homeAdd + "\n" +
                        "W/point :   " + workPhone + "\n" +
                        "Notes   :   " + notes + "\n\n" + "\n"

        );

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater flat = getMenuInflater();
        flat.inflate(R.menu.menu_data, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.edit) {
            Intent tent = new Intent(Data.this, EditActivity.class);
            tent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            tent.putExtra("id", id);
            startActivity(tent);
            //Toast.makeText(Data.this, "Allows you to edit an entry", Toast.LENGTH_LONG).show();
        }

        if (item.getItemId() == R.id.delete) {

            AlertDialog.Builder kay = new AlertDialog.Builder(Data.this);
            kay.setTitle(R.string.deleteAll_title)
                    .setMessage("Are you sure you want to permanently delete this entry?")
                    .setNegativeButton("No", null)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            SQLite lytie = new SQLite(Data.this);
                            lytie.open();
                            lytie.removeFromList(id);
                            lytie.close();
                            Intent tent = new Intent(Data.this, ListActo.class);
                            tent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(tent);

                            Toast.makeText(Data.this, "Entry has been deleted", Toast.LENGTH_LONG).show();
                        }
                    }).show();



        }
        return super.onContextItemSelected(item);
    }


}