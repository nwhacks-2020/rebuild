package com.nwhacks2020.rebuild;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class PinMenu extends AppCompatActivity {
    String items[] = new String[] {" DANGER", "SHELTER","FOOD","WATER","NEED_HELP"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_menu);
    }

    public void clicked(View view){
        Intent intent = new Intent();
        intent.putExtra("editTextValue", "Danger");
        switch (view.getId()){
            case (R.id.buttonDanger):

        }
        setResult(RESULT_OK, intent);
        finish();
    }
}
