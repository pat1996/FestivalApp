package com.example.patri.festivalapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PacklistActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ImageButton packlist_task_button;
    EditText packlist_taskname;
    ListView list_view;
    List<PacklistItem> packlistItems;
    TodoListAdapter adapter;
    File file;
    XmlParser parser;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_festival_packlist);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        packlist_task_button = (ImageButton)findViewById(R.id.packlist_task_button);
        packlist_taskname = (EditText)findViewById(R.id.packlist_taskname);
        list_view = (ListView)findViewById(R.id.list_view);
        packlistItems = new ArrayList<PacklistItem>();
        adapter = new TodoListAdapter(packlistItems,this);
        list_view.setAdapter(adapter);
        file = new File(Environment.getExternalStorageDirectory(), "tasks.xml");
        parser = new XmlParser();
        if (file.exists()) {
            try {
                packlistItems = parser.read(file);

                if (packlistItems.isEmpty()) {
                    file.delete();
                    file.createNewFile();
                }

            } catch (XmlPullParserException ex) {
                Logger.getLogger(PacklistActivity.class.getName()).log(Level.SEVERE, null, ex);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(PacklistActivity.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(PacklistActivity.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            try {
                file.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(PacklistActivity.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        packlist_task_button.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if (packlist_taskname.getText().length() > 0){
                    packlistItems.add(new PacklistItem(packlist_taskname.getText().toString(),false));
                    adapter.notifyDataSetChanged();
                    packlist_taskname.setText("");
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();

        try {
            parser.write(packlistItems, file);
        } catch (IOException ex) {
            Logger.getLogger(PacklistActivity.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void onBackPressed() {
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
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
            Intent i = new Intent(this,WeatherActivity.class);
            this.finish();
            startActivity(i);
        } else if (id == R.id.nav_countdown) {
            Intent i = new Intent(this,CountdownActivity.class);
            this.finish();
            startActivity(i);

        } else if (id == R.id.nav_cost) {
            Intent i = new Intent(this,CostActivity.class);
            this.finish();
            startActivity(i);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
