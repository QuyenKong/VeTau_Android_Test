package com.example.vetau;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView lvVeTau;
    private static final int MENU_ITEM_VIEW = 111;
    private static final int MENU_ITEM_EDIT = 222;
    private static final int MENU_ITEM_CREATE = 333;
    private static final int MENU_ITEM_DELETE = 444;

    private static final int MY_REQUEST_CODE = 1000;

    private final List<Vetau> arrVeTau = new ArrayList<Vetau>();
    private ArrayAdapter<Vetau> lvVeTauAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get ListView object from xml
        this.lvVeTau = (ListView) findViewById(R.id.listView);

        MyDatabaseHelper db = new MyDatabaseHelper(this);
        db.createDefaultVeTausIfNeed();

        List<Vetau> list=  db.getAllVe();
        this.arrVeTau.addAll(list);

        // Define a new Adapter
        // 1 - Context
        // 2 - Layout for the row
        // 3 - ID of the TextView to which the data is written
        // 4 - the List of data
        lvVeTauAdapter = new VeTauAdapter(this,R.layout.vetau,this.arrVeTau);
        this.lvVeTau.setAdapter(lvVeTauAdapter);

        registerForContextMenu(this.lvVeTau);
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        // groupId, itemId, order, title
        menu.add(0, MENU_ITEM_VIEW , 0, "View VeTau");
        menu.add(0, MENU_ITEM_CREATE , 1, "Create VeTau");
        menu.add(0, MENU_ITEM_EDIT , 2, "Edit VeTau");
        menu.add(0, MENU_ITEM_DELETE, 4, "Delete VeTau");
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo
                info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        final Vetau selectedVeTau = (Vetau) this.lvVeTau.getItemAtPosition(info.position);

        if(item.getItemId() == MENU_ITEM_VIEW){
            Toast.makeText(MainActivity.this,""+selectedVeTau.getGaDi()+" di "+selectedVeTau.getGaDen()+" gia: "+selectedVeTau.getDonGia(),Toast.LENGTH_LONG).show();
        }
        else if(item.getItemId() == MENU_ITEM_CREATE){
            Intent intent = new Intent(this, AddEditVeTauActivity.class);

            // Start AddEditVeTauActivity, (with feedback).
            this.startActivityForResult(intent, MY_REQUEST_CODE);
        }
        // Start AddEditVeTauActivity, (with feedback).
        else if(item.getItemId() == MENU_ITEM_EDIT ){
            Intent intent = new Intent(this, AddEditVeTauActivity.class);
            intent.putExtra("vetau", selectedVeTau);

            this.startActivityForResult(intent,MY_REQUEST_CODE);
        }
        else if(item.getItemId() == MENU_ITEM_DELETE){
            // Ask before deleting.
            new AlertDialog.Builder(this)
                    .setMessage(selectedVeTau.getGaDen()+". Are you sure you want to delete?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            deleteVeTau(selectedVeTau);
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
        }
        else {
            return false;
        }
        return true;
    }

    private void deleteVeTau(Vetau vetau) {
        MyDatabaseHelper db = new MyDatabaseHelper(this);
        db.deleteVeTau(vetau);
        this.arrVeTau.remove(vetau);
        // Refresh ListView.
        this.lvVeTauAdapter.notifyDataSetChanged();
    }

    // When AddEditVeTauActivity completed, it sends feedback.
    // (If you start it using startActivityForResult ())
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && requestCode == MY_REQUEST_CODE) {
            boolean needRefresh = data.getBooleanExtra("DoneedRefresh?", true);
            // Refresh ListView
            if (needRefresh) {
                this.arrVeTau.clear();
                MyDatabaseHelper db = new MyDatabaseHelper(this);
                List<Vetau> list = db.getAllVe();
                this.arrVeTau.addAll(list);



                // Notify the data change (To refresh the ListView).
                this.lvVeTauAdapter.notifyDataSetChanged();
                Toast.makeText(getApplicationContext(),"update successfull",Toast.LENGTH_SHORT);
            }
        }
    }

}