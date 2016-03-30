package com.example.iis.datacapturer;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;


public class ListActo extends ListActivity {

    List<Arrange> show;
    Arrange arrange;
    long myId;
    View bk;
    String gen,  myName, mySur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);


        Intent intent = getIntent();
        gen = intent.getStringExtra("sex");
        registerForContextMenu(getListView());


        SQLite lite = new SQLite(ListActo.this);
        lite.open();
        show = lite.list("id", "id DESC");
        if (show == null) {
            lite.write("cusName", "cusSurname", "cusDOB", "cusOcc", "cusHomeAdd", "cusPhone", "wkPhone", "wkAdd", "reg", "sex", "comments");
            lite.list("id", "id DESC");
        }

        lite.close();

        refresh();
    }

    private void refresh() {
        ArrayAdapter<Arrange> mine = new ArrayAdapter<>(ListActo.this, android.R.layout.simple_list_item_1, show);
        setListAdapter(mine);
    }


    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        arrange = show.get(position);
        myId = arrange.getId();

        bk = v;

        Intent intent = new Intent(ListActo.this, Data.class);

        myName = arrange.getCustomer();
        mySur = arrange.getSurname();
        intent.putExtra("name", myName);
        intent.putExtra("sur", mySur);
        intent.putExtra("regNo", arrange.getIdNo());
        intent.putExtra("occ", arrange.getOccupation());
        intent.putExtra("dob", arrange.getDob());
        intent.putExtra("cell", arrange.getHomePhone());
        intent.putExtra("home", arrange.getHomeAdd());
        intent.putExtra("workPhone", arrange.getWorkPhone());
        intent.putExtra("workAdd", arrange.getWorkAdd());
        intent.putExtra("notes", arrange.getImage());
        intent.putExtra("sex", arrange.getSex());
        intent.putExtra("id", myId);

        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = new MenuInflater(getApplicationContext());
        menuInflater.inflate(R.menu.listoptions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.deleteAll){
            AlertDialog.Builder bkay = new AlertDialog.Builder(ListActo.this);
            bkay.setTitle(R.string.deleteAll_title)
                    .setMessage(R.string.delAll_message)
                    .setNegativeButton("No", null)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            SQLite lite = new SQLite(getApplicationContext());
                            lite.open();
                            lite.remove();
                            show = lite.list("id", "id > 0");
                            lite.close();
                            refresh();
                        }
                    });
            Dialog bro = bkay.create();
            bro.show();
        }

        if(item.getItemId() == R.id.unsort){
            SQLite lite = new SQLite(ListActo.this);
            lite.open();
            show = lite.list("id", "id ASC");
            lite.close();
            refresh();
        }

        if(item.getItemId() == R.id.resort){
            SQLite lite = new SQLite(ListActo.this);
            lite.open();
            show = lite.list("id", "id DESC");
            lite.close();
            refresh();
        }

        return super.onOptionsItemSelected(item);
    }
}
