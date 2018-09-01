package com.example.patri.festivalapp;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static java.lang.Double.parseDouble;

public class ContentFragment extends Fragment implements View.OnClickListener {

    private OnListItemChangedListener mCallback;

    /**
     * This interface allows the fragment to communicate with the activity.
     * <p/>
     * <b>NOTE: When using the one fragment layout, this Interface must be implemented by the
     * <code>ContentActiviy</code> because the <code>ContentFragment</code> resides there. In the two
     * fragment layout on big devices, this fragment resides in the <code>MainActivity</code>.
     * Therefore, this Activity must implement the Interface, too.</b>
     */
    public interface OnListItemChangedListener {
        public void onListItemChanged();
    }

    public final static String ARG_ID = "id";
    private Cost mCost = null;
    private boolean newCost = true;
    // Sets an ID for the notification
    private final static int NOTIFICATION_ID = 0;
    private final static String NOTIFICATION_CHANNEL_NAME = "CH0";
    private final static String NOTIFICATION_CHANNEL_ID = "0";

    public ContentFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.cost_add, container, false);
        Button createOrUpdateButton = (Button) view.findViewById(R.id.button_updateCost);
        Button removeButton = (Button) view.findViewById(R.id.button_removeCost);
        createOrUpdateButton.setOnClickListener(this);
        removeButton.setOnClickListener(this);
        return view;
    }

    /**
     * Loads and views a cost based on its id.
     *
     * @param id the id of the cost to display
     */
    public void viewContent(int id) {
        mCost = new Database(getActivity()).readEntryById(id);
        if (mCost != null) {
            EditText name = (EditText) getView().findViewById(R.id.content_name);
            EditText price = (EditText) getView().findViewById(R.id.content_price);
            name.setText(mCost.getName());
            price.setText(String.valueOf(price));

            newCost = false;
        } else {
            loadEmptyView();
            Toast.makeText(getActivity(), "Fehler beim Laden der Ausgaben!", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Opens an empty content view for entering a new cost
     */
    public void loadEmptyView() {
        mCost = new Cost();
        emptyContentView();
        newCost = true;
    }

    public void removeEntry(View view) {
        if (mCost == null) {
            Toast.makeText(getActivity(), "Fehler, es wurde nichts gelöscht!", Toast.LENGTH_SHORT).show();
            return;
        }
        int numRemovedRows = new Database(getActivity()).removeEntry(mCost.getId());
        if (numRemovedRows == 1) {
            Toast.makeText(getActivity(), "Ausgabe gelöscht!", Toast.LENGTH_SHORT).show();
        } else if (numRemovedRows <= 0) {
            Toast.makeText(getActivity(), "Fehler, es wurde nichts gelöscht!", Toast.LENGTH_SHORT).show();
        }
        mCost = null;
        emptyContentView();
        // Switch back to list fragment if we are in one fragment layout
        CostList costList =
                (CostList) getFragmentManager().findFragmentById(R.id.fragment_list);
        if (costList == null) {
            Intent loadMainViewIntent = new Intent(getActivity(), CostActivity.class);
            loadMainViewIntent.putExtra(CostActivity.INTENT_ITEM_SELECTED_NAME,
                    CostActivity.INTENT_ITEM_SELECTED_ID);
            startActivity(loadMainViewIntent);
        }
    }

    private void emptyContentView() {
        EditText name = (EditText) getView().findViewById(R.id.content_name);
        EditText price = (EditText) getView().findViewById(R.id.content_price);
        price.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(2)});
        name.setText("");
        price.setText("");
    }

    public void updateOrSaveEntry(View view) {
        long status;
        EditText nameEditText = (EditText) getView().findViewById(R.id.content_name);
        EditText priceEditText = (EditText) getView().findViewById(R.id.content_price);
        String name = nameEditText.getText().toString();
        if (name.isEmpty()) {
            Toast.makeText(getActivity(), "Gib bitte eine Ausgabe ein!", Toast.LENGTH_SHORT).show();
            return;
        }
        String price = priceEditText.getText().toString();
        if (price.isEmpty()) {
            Toast.makeText(getActivity(), "Gib bitte einen Preis ein!", Toast.LENGTH_SHORT).show();
            return;
        }
        mCost.setName(name);
        mCost.setPrice(Double.parseDouble(price));
        if (newCost) {
            status = new Database(getActivity()).insertCostEntry(name, Double.parseDouble(price));
            mCost.setId((int) status);
            if (status == -1) {
                Toast.makeText(getActivity(), "Fehler beim Hinzufügen deiner Ausgabe!", Toast.LENGTH_SHORT).show();
            } else if (status >= 0) {
                newCost = false;
                Toast.makeText(getActivity(), "Ausgabe hinzugefügt!", Toast.LENGTH_SHORT).show();
            }
            // Update the current entry
        } else {
            status = new Database(getActivity()).updateEntry(mCost.getId(), name, Double.parseDouble(price));
            if (status == -1) {
                Toast.makeText(getActivity(), "Fehler beim Hinzufügen deiner Ausgabe!", Toast.LENGTH_SHORT).show();
            } else if (status >= 0) {
                Toast.makeText(getActivity(), "Ausgabe aktualisiert!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_updateCost:
                updateOrSaveEntry(view);
                mCallback.onListItemChanged();
                break;
            case R.id.button_removeCost:
                removeEntry(view);
                mCallback.onListItemChanged();
                break;
            default:
                break;
        }
    }

    @SuppressWarnings("deprecation")
    // The new method onAttach(Context context) doesn't exist in API level 22 and below
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mCost = new Cost();
        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (OnListItemChangedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(
                    activity.toString() + " must implement OnListItemChangedListener!");
        }
    }
}
