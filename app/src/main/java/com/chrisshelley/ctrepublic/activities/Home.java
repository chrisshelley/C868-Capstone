package com.chrisshelley.ctrepublic.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.chrisshelley.ctrepublic.R;

public class Home extends AppCompatActivity {
    private Button mButtonViewCollection;
    private Button mButtonViewReports;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mButtonViewCollection = (Button) findViewById(R.id.btnViewCollection);
        mButtonViewCollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent navigationIntent = new Intent(Home.this, CollectionList.class);
                startActivity(navigationIntent);
            }
        });

        mButtonViewReports = (Button) findViewById(R.id.btnViewReports);
        mButtonViewReports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent navigationIntent = new Intent(Home.this, Reports.class);
                startActivity(navigationIntent);
            }
        });
    }
}