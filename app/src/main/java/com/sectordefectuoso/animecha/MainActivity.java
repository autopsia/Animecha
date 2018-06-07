package com.sectordefectuoso.animecha;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;

public class MainActivity extends AppCompatActivity {
    //firebase
    DatabaseReference ref;
    //
    GridView mainGrid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

}
