package com.nwhacks2020.rebuild;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class PinMenu extends AppCompatActivity {
    String items[] = new String[] {" DANGER", "SHELTER","FOOD","WATER","NEED_HELP"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_menu);


        ListView listView = (ListView) findViewById(R.id.listviewID);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,items);
        listView.setAdapter(adapter);
    }
}
