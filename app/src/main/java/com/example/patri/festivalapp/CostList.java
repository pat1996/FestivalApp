package com.example.patri.festivalapp;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CostList extends ListFragment {

    private OnListItemSelectedListener mCallback;

    /**
     * This interface allows the fragment to communicate with the activity
     */
    public interface OnListItemSelectedListener {
        public void onListItemSelected(int id);
    }

    public CostList() {
    }

    //Hier wird angegeben, welches Layout angezeigt werden soll.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.cost_list, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        populateList();
    }

    /**
     * Loads the content of the list. This method is both called when the Fragment is loaded and when
     * an update is required (after an entry was added, updated or removed).
     * <p/>
     * <b>NOTE: Usually, updating a <code>ListView</code> should be done with calling the Adapters
     * <code>notifyDataSetChanged()</code> method. But in order to show the dummy entry when the list
     * is empty, the list is recreated from the beginning.</b>
     */

    protected void populateList() {
        ArrayList<Cost> values = new Database(getActivity()).readCostData();

        // Use dummy not for informing about empty list
        if (values.size() == 0) {
            Cost dummy = new Cost();
            dummy.setId(-1);
            values = new ArrayList<Cost>();
            values.add(dummy);
        }
        CostListItemAdapter costListItemAdapter = new CostListItemAdapter(getActivity(), R.layout.cost_list_single_entry, values);
        setListAdapter(costListItemAdapter);
        refreshTotalCost();
    }

    public void refreshTotalCost() {
        TextView tvTotalCost = (TextView) getView().findViewById(R.id.total_cost_price);
        tvTotalCost.setText(String.valueOf(MainFestivalActivity.getTotalCost()) + "€");
    }

    @Override
    public void onResume() {
        super.onResume();
        populateList();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Cost clickedCost = (Cost) l.getItemAtPosition(position);
        Toast.makeText(getActivity(),"Geklickt an Pos: " + position + "; Id: " + id,
                Toast.LENGTH_LONG).show();
        if (clickedCost.getId() == -1) {
            return;
        }
        mCallback.onListItemSelected(clickedCost.getId());
    }


    //onAttach = das Fragment wird an die Activity angehängt
    @SuppressWarnings("deprecation")
    // The new method onAttach(Context context) doesn't exist in API level 22 and below
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (OnListItemSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(
                    activity.toString() + " must implement OnListItemSelectedListener!");
        }
    }
}
