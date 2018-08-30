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
import android.util.Log;
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
    ArrayList<PackingListItemDB> packing_list;

    private static final String TAG = "PacklistActivity";


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

        packlist_task_button = (ImageButton) findViewById(R.id.packlist_task_button);
        packlist_taskname = (EditText) findViewById(R.id.packlist_taskname);
        packing_list = new ArrayList<>();

        packlist_task_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if (packlist_taskname.getText().length() >0){
                   packing_list.add(new PackingListItemDB());
               }
            }
        });

        PackingListItemDB item1 = new PackingListItemDB();
        item1.setName("Dosenravioli");
        item1.setChecked(true);

        PackingListItemDB item2 = new PackingListItemDB();
        item2.setName("Grillfleisch");
        item2.setChecked(true);

        PackingListItemDB item3 = new PackingListItemDB();
        item1.setName("Kekse");
        item1.setChecked(true);

        PackingListItemDB item4 = new PackingListItemDB();
        item1.setName("Muesli");
        item1.setChecked(true);

        PackingListItemDB item5 = new PackingListItemDB();
        item1.setName("Milchbroetchen");
        item1.setChecked(true);

        PackingListItemDB item6 = new PackingListItemDB();
        item1.setName("Nutella");
        item1.setChecked(true);

        PackingListItemDB item7 = new PackingListItemDB();
        item1.setName("Salzstangen");
        item1.setChecked(true);

        PackingListItemDB item8 = new PackingListItemDB();
        item1.setName("Erdnuesse");
        item1.setChecked(true);


        PackingListItemDB item9 = new PackingListItemDB();
        item1.setName("Milch");
        item1.setChecked(true);

        PackingListItemDB item10 = new PackingListItemDB();
        item1.setName("Asbach");
        item1.setChecked(true);
        PackingListItemDB item11 = new PackingListItemDB();
        item1.setName("Cola");
        item1.setChecked(true);
        PackingListItemDB item12 = new PackingListItemDB();
        item1.setName("Bier");
        item1.setChecked(true);
        PackingListItemDB item13 = new PackingListItemDB();
        item1.setName("Cider");
        item1.setChecked(true);
        PackingListItemDB item14 = new PackingListItemDB();
        item1.setName("Wasser");
        item1.setChecked(true);
        PackingListItemDB item15 = new PackingListItemDB();
        item1.setName("Hugo");
        item1.setChecked(true);
        PackingListItemDB item16 = new PackingListItemDB();
        item1.setName("Sekt");
        item1.setChecked(true);
        PackingListItemDB item17 = new PackingListItemDB();
        item1.setName("Duschgel");
        item1.setChecked(true);
        PackingListItemDB item18 = new PackingListItemDB();
        item1.setName("Handtuecher");
        item1.setChecked(true);

        PackingListItemDB item19 = new PackingListItemDB();
        item1.setName("Zahnpasta");
        item1.setChecked(true);
        PackingListItemDB item20 = new PackingListItemDB();
        item1.setName("Zahnbuerste");
        item1.setChecked(true);

        PackingListItemDB item21 = new PackingListItemDB();
        item1.setName("Hosen");
        item1.setChecked(true);
        PackingListItemDB item22 = new PackingListItemDB();
        item1.setName("T-Shirts");
        item1.setChecked(true);
        PackingListItemDB item23 = new PackingListItemDB();
        item1.setName("Socken");
        item1.setChecked(true);
        PackingListItemDB item24 = new PackingListItemDB();
        item1.setName("Unterhosen");
        item1.setChecked(true);
        PackingListItemDB item25 = new PackingListItemDB();
        item1.setName("Hut");
        item1.setChecked(true);
        PackingListItemDB item26 = new PackingListItemDB();
        item1.setName("Sonnenbrille");
        item1.setChecked(true);
        PackingListItemDB item27 = new PackingListItemDB();
        item1.setName("Campingstuhl");
        item1.setChecked(true);
        PackingListItemDB item28 = new PackingListItemDB();
        item1.setName("Zelt");
        item1.setChecked(true);
        PackingListItemDB item29 = new PackingListItemDB();
        item1.setName("Schlafsack");
        item1.setChecked(true);
        PackingListItemDB item30 = new PackingListItemDB();
        item1.setName("Kissen");
        item1.setChecked(true);
        PackingListItemDB item31 = new PackingListItemDB();
        item1.setName("Decke");
        item1.setChecked(true);
        PackingListItemDB item32 = new PackingListItemDB();
        item1.setName("Gaskocher");
        item1.setChecked(true);
        PackingListItemDB item33 = new PackingListItemDB();
        item1.setName("Messer");
        item1.setChecked(true);
        PackingListItemDB item34 = new PackingListItemDB();
        item1.setName("Gabel");
        item1.setChecked(true);
        PackingListItemDB item35 = new PackingListItemDB();
        item1.setName("Teller");
        item1.setChecked(true);
        PackingListItemDB item36 = new PackingListItemDB();
        item1.setName("Powerbank");
        item1.setChecked(true);
        PackingListItemDB item37 = new PackingListItemDB();
        item1.setName("Musikbox");
        item1.setChecked(true);


        packing_list.add(item1);
        packing_list.add(item2);
        packing_list.add(item3);
        packing_list.add(item4);
        packing_list.add(item5);
        packing_list.add(item6);
        packing_list.add(item7);
        packing_list.add(item8);
        packing_list.add(item9);
        packing_list.add(item10);
        packing_list.add(item11);
        packing_list.add(item12);
        packing_list.add(item13);
        packing_list.add(item14);
        packing_list.add(item15);
        packing_list.add(item16);
        packing_list.add(item17);
        packing_list.add(item18);
        packing_list.add(item19);
        packing_list.add(item20);
        packing_list.add(item21);
        packing_list.add(item22);
        packing_list.add(item23);
        packing_list.add(item24);
        packing_list.add(item25);
        packing_list.add(item26);
        packing_list.add(item27);
        packing_list.add(item28);
        packing_list.add(item29);
        packing_list.add(item30);
        packing_list.add(item31);
        packing_list.add(item32);
        packing_list.add(item33);
        packing_list.add(item34);
        packing_list.add(item35);
        packing_list.add(item36);

        Database db = MainFestivalActivity.getDb();

        for (int i = 0; i < packing_list.size(); i++) {
            db.insertIntoTable("PackingList", packing_list.get(i));
        }


    }

    @Override
    protected void onPause() {
        super.onPause();


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

}
