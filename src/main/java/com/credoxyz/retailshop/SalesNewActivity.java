package com.credoxyz.retailshop;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SalesNewActivity extends AppCompatActivity implements View.OnClickListener {

    private Button view, newOne;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_new);
        view = (Button) findViewById(R.id.salesView);
        newOne = findViewById(R.id.salesNew);
        view.setOnClickListener(this);
        newOne.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.salesNew:
                Intent addN = new Intent(SalesNewActivity.this, SalesActivity.class);
                startActivity(addN);
                break;

            case R.id.salesView:
                Intent viewN = new Intent(SalesNewActivity.this, SalesViewActivity.class);
                startActivity(viewN);
                break;

            default:
                break;
        }

    }

    @Override
    public void onBackPressed(){
        Intent prev = new Intent(SalesNewActivity.this, MainActivity.class);
        startActivity(prev);
    }

}
