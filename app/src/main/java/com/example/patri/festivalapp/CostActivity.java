package com.example.patri.festivalapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class CostActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, MyListFragment.OnListItemSelectedListener,
        ContentFragment.OnListItemChangedListener {

    public static final int INTENT_ITEM_SELECTED_ID = 0;
    public static final String INTENT_ITEM_SELECTED_NAME = "IntentForLoadingSelectedCost";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_festival_cost);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_cost);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        Button newCostButton = (Button) findViewById(R.id.new_cost_button);
        newCostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentFragment contentFragment = (ContentFragment) getFragmentManager().findFragmentById(R.id.fragment_content);
                if(contentFragment != null){
                    contentFragment.loadEmptyView();
                }else {
                    Toast.makeText(CostActivity.this, "Content Fragment not there, switching!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(CostActivity.this,  ContentActivity.class);
                    startActivity(intent);
                }
            }
        });

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
            Intent i = new Intent(this,PacklistActivity.class);
            this.finish();
            startActivity(i);
        } else if (id == R.id.nav_weather) {
            Intent i = new Intent(this,WeatherActivity.class);
            this.finish();
            startActivity(i);
        } else if (id == R.id.nav_countdown) {
            Intent i = new Intent(this,CountdownActivity.class);
            this.finish();
            startActivity(i);

        } else if (id == R.id.nav_cost) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_cost);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }

    @Override
    public void onListItemChanged() {
        MyListFragment listFragment = (MyListFragment) getFragmentManager().findFragmentById(R.id.fragment_list);
        if(listFragment != null){
            listFragment.populateList();
        }
    }

    @Override
    public void onListItemSelected(int id) {
        ContentFragment contentFragment = (ContentFragment) getFragmentManager().findFragmentById(R.id.fragment_list);
        if(contentFragment != null){
            contentFragment.viewContent(id);
        }else{
            Toast.makeText(this, "Content Fragment not there, switching!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, ContentActivity.class);
            intent.putExtra(ContentFragment.ARG_ID,id);
            startActivity(intent);
        }
    }
}
