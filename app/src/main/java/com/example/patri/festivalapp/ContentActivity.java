package com.example.patri.festivalapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class ContentActivity extends Activity implements ContentFragment.OnListItemChangedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        if (getActionBar() != null) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // TODO Use the ContentFragment in order to show the article or load an empty
        // view (dummy id is -1) when a new cost should be created

        // Get cost id from Intent
        Intent intent = getIntent();
        if (intent != null) {
            ContentFragment cf = (ContentFragment) getFragmentManager()
                    .findFragmentById(R.id.fragment_activity_content);
            // Last argument is the default value, used here to indicate a missing id
            int id = intent.getIntExtra(ContentFragment.ARG_ID, -1);
            if (cf != null) {
                if (id != -1) {
                    cf.viewContent(id);
                } else {
                    cf.loadEmptyView();
                }
            } else {
                Toast.makeText(this, "Content Fragment not there!", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Create your new cost here!", Toast.LENGTH_SHORT).show();
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

    @Override
    public void onListItemChanged() {
        MyListFragment lf =
                (MyListFragment) getFragmentManager().findFragmentById(R.id.fragment_list);
        if (lf != null) {
            lf.populateList();
        }
        // No "else" cases needed because in the one fragment layout on small devices, the reloading
        // is done automatically because the list fragment will be reloaded
    }
}
