package com.example.patri.festivalapp;

import android.text.Editable;

public class PackingListItemDB {

    private int id;
    private String name;
    private boolean isChecked;



   // public PackingListItemDB(String s, boolean b) {
    //    this.name = s;
      //  this.isChecked = b;

//    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
