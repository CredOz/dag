package com.credoxyz.retailshop;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MaintenanceNewActivity extends AppCompatActivity implements View.OnClickListener {
    private Button view, newOne;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintenance_new);
        view = (Button) findViewById(R.id.maintenanceView);
        newOne = findViewById(R.id.maintenanceNew);
        view.setOnClickListener(this);
        newOne.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.maintenanceNew:
                Intent addN = new Intent(MaintenanceNewActivity.this, MaintenanceActivity.class);
                startActivity(addN);
                break;

            case R.id.maintenanceView:
                Intent viewN = new Intent(MaintenanceNewActivity.this, MaintenanceViewActivity.class);
                startActivity(viewN);
                break;

            default:
                break;
        }
    }
}
