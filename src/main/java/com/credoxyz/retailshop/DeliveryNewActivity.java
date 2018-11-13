package com.credoxyz.retailshop;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DeliveryNewActivity extends AppCompatActivity implements View.OnClickListener {
    private Button view, newOne;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_new);
        view = (Button) findViewById(R.id.deliveryView);
        newOne = findViewById(R.id.deliveryNew);
        view.setOnClickListener(this);
        newOne.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.deliveryNew:
                Intent addN = new Intent(DeliveryNewActivity.this, DeliveryActivity.class);
                startActivity(addN);
                break;

            case R.id.deliveryView:
                Intent viewN = new Intent(DeliveryNewActivity.this, DeliveryViewActivity.class);
                startActivity(viewN);
                break;

            default:
                break;
        }
    }
}
