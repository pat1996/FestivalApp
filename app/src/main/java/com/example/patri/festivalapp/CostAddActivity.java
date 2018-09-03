package com.example.patri.festivalapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.Toast;

public class CostAddActivity extends Activity implements ContentFragment.OnListItemChangedListener {

    private GestureDetectorCompat gestureObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cost_add_fragment);
        if (getActionBar() != null) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }

        gestureObject = new GestureDetectorCompat(this, new LearnGesture());

        // Check if ContentFragment is there
        // Get cost ID from Intent
        Intent intent = getIntent();
        if (intent != null) {
            ContentFragment contentFragment = (ContentFragment) getFragmentManager()
                    .findFragmentById(R.id.fragment_cost_add);
            int id = intent.getIntExtra(ContentFragment.ARG_ID, -1);
            if (contentFragment != null) {
                if (id != -1) {
                    contentFragment.viewContent(id);
                } else {
                    contentFragment.loadEmptyView();
                }
            } else {
                Toast.makeText(this, "Content Fragment nicht vorhanden!", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Gib hier deine neue Ausgabe ein!", Toast.LENGTH_SHORT).show();
        }
    }

    // The following override method and the class LearnGesture are needed for the swipe functionality
    // So the user can go back to the MainFestivalActivity only with a swipe to the left
    @Override
    public boolean onTouchEvent(MotionEvent event){
        this.gestureObject.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    class LearnGesture extends GestureDetector.SimpleOnGestureListener{

        int minStep=30;

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float vX, float vY){

            if(e2.getX() < (e1.getX() - minStep)){
                Intent intent = new Intent(CostAddActivity.this, MainFestivalActivity.class);
                finish();
                startActivity(intent);
            }
            return true;
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
        CostList costList =
                (CostList) getFragmentManager().findFragmentById(R.id.fragment_list);
        if (costList != null) {
            costList.populateList();
        }

    }
}
