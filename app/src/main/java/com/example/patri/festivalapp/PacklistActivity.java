package com.example.patri.festivalapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class PacklistActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ImageButton packlist_task_button;
    EditText packlist_taskname;
    ListView lv;
    ArrayList<PackingListItemDB> packing_list;
    TodoListAdapter adapter;

    private static final String TAG = "PacklistActivity";
    private GestureDetectorCompat gestureObjectCOSTACTIVITY;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_festival_packlist);
        gestureObjectCOSTACTIVITY = new GestureDetectorCompat(this, new LearnGesture());
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        packlist_taskname = (EditText) findViewById(R.id.packlist_taskname);
        packlist_task_button = (ImageButton) findViewById(R.id.packlist_task_button);
        packing_list = new ArrayList<PackingListItemDB>();
        lv = (ListView) findViewById(R.id.packlist_List_view);
        adapter = new TodoListAdapter(packing_list, this);
        lv.setAdapter(adapter);

        readDatabase();

        packlist_task_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveOnButton();
            }
        });


    }

    @Override
    protected void onPause() {
        super.onPause();
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        onTouchEvent(event);
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.gestureObjectCOSTACTIVITY.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    class LearnGesture extends GestureDetector.SimpleOnGestureListener {

        int minStep = 500;

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float vX, float vY) {

            if (e2.getX() < (e1.getX() - minStep)) {
                Intent intent = new Intent(PacklistActivity.this, MainFestivalActivity.class);
                finish();
                startActivity(intent);
            }
            return true;
        }
    }

    @Override
    public void onBackPressed() {
        insertDatabase();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_festival, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_packlist) {

        } else if (id == R.id.nav_weather) {
            Intent i = new Intent(this, WeatherActivity.class);
            this.finish();
            startActivity(i);
        } else if (id == R.id.nav_countdown) {
            Intent i = new Intent(this, CountdownActivity.class);
            this.finish();
            startActivity(i);

        } else if (id == R.id.nav_cost) {
            Intent i = new Intent(this, CostActivity.class);
            this.finish();
            startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void saveOnButton() {
        PackingListItemDB item = new PackingListItemDB();
        if (packlist_taskname.getText().length() > 0) {
            item.setName(packlist_taskname.getText().toString());
            item.setChecked(false);

            packing_list.add(item);
            adapter.notifyDataSetChanged();
        }

        packlist_taskname.setText("");
    }

    private void insertDatabase() {
        Database db = MainFestivalActivity.getDb();
        db.dropTable("PackingList");
        db.createTable("PackingList");

        for (PackingListItemDB item : packing_list) {
            Log.e("INSERT", item.getName()+" "+item.isChecked() );
            db.insertIntoTable("PackingList", item);
        }
    }

    private void readDatabase() {
        Database db = MainFestivalActivity.getDb();
        Cursor res = db.selectAllFromTable("PackingList");

        res.moveToFirst();

        while (!res.isAfterLast()) {
            PackingListItemDB item = new PackingListItemDB();
            String name = res.getString(res.getColumnIndex("Name"));
            boolean isChecked = false;
            if (res.getInt(res.getColumnIndex("IsChecked")) == 1) {
                isChecked = true;
            }
            item.setName(name);
            item.setChecked(isChecked);

            packing_list.add(item);

            res.moveToNext();
        }

        adapter.notifyDataSetChanged();
    }
}
