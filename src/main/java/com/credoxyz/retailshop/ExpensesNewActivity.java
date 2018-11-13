package com.credoxyz.retailshop;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ExpensesNewActivity extends AppCompatActivity implements View.OnClickListener{
    private Button view, newOne;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenses_new);
        view = (Button) findViewById(R.id.expensesView);
        newOne = findViewById(R.id.expensesNew);
        view.setOnClickListener(this);
        newOne.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.expensesNew:
                Intent addN = new Intent(ExpensesNewActivity.this, ExpensesActivity.class);
                startActivity(addN);
                break;

            case R.id.expensesView:
                Intent viewN = new Intent(ExpensesNewActivity.this, ExpensesViewActivity.class);
                startActivity(viewN);
                break;

            default:
                break;
        }
    }
}
