package com.credoxyz.retailshop;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class OrderNewActivity extends AppCompatActivity implements View.OnClickListener {
    private Button view, newOne;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_new);
        view = (Button) findViewById(R.id.orderView);
        newOne = findViewById(R.id.orderNew);
        view.setOnClickListener(this);
        newOne.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.orderNew:
                Intent addN = new Intent(OrderNewActivity.this, OrderActivity.class);
                startActivity(addN);
                break;

            case R.id.orderView:
                Intent viewN = new Intent(OrderNewActivity.this, OrderViewActivity.class);
                startActivity(viewN);
                break;

            default:
                break;
        }
    }

    @Override
    public void onBackPressed(){
        Intent prev = new Intent(OrderNewActivity.this, MainActivity.class);
        startActivity(prev);
    }
}
