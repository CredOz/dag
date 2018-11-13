package com.credoxyz.retailshop;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;

public class OrderConfirmActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView date, orderNo, price, product, paymentType, receipt, notes, customer, customer2, phone1, phone2, payment, paymentMethod;
    private Button confirm, back;
    Order x;
    private SQLiteAdapter mySQLiteAdapter;
    String act;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirm);

        confirm = findViewById(R.id.confirmButton);
        back = findViewById(R.id.backButton);
        confirm.setOnClickListener(this);
        back.setOnClickListener(this);

        x = (Order) getIntent().getSerializableExtra("obj");
        act = getIntent().getStringExtra("act");
        System.out.println(act);

        date = findViewById(R.id.date_pick);
        orderNo = findViewById(R.id.orderNoText);
        price = findViewById(R.id.priceText);
        product = findViewById(R.id.productText);
        paymentType = findViewById(R.id.paymentTypeText);
        receipt = findViewById(R.id.fsNo);
        notes = findViewById(R.id.notesText);
        customer = findViewById(R.id.customerText);
        customer2 = findViewById(R.id.customer2Text);
        phone1 = findViewById(R.id.phone1Text);
        phone2 = findViewById(R.id.phone2Text);
        payment = findViewById(R.id.payment);

        date.setText(x.date);
        orderNo.setText(x.order);
        price.setText(x.price);
        product.setText(x.product);
        paymentType.setText(x.paymentType);
        receipt.setText(x.fsNo);
        notes.setText(x.note);
        customer.setText(x.customer);
        customer2.setText(x.customer2);
        phone1.setText(x.phone);
        phone2.setText(x.phone2);
        payment.setText(x.payment);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.confirmButton:
                 /*
                 * 	Create/Open a SQLite database
                 *  and fill with dummy content
                 *  and close it
                 */
                mySQLiteAdapter = new SQLiteAdapter(this);
                mySQLiteAdapter.openToWrite();
                try {
                    if (act.equals("update")){
                        mySQLiteAdapter.updateToOrders(x.order, x.date, x.product, x.note, x.price,  x.payment, x.paymentMethod, x.paymentType, x.fsNo
                                ,x.customer, x.customer2, x.phone, x.phone2, "", "");
                        Intent act = new Intent(OrderConfirmActivity.this, OrderActivity.class);
                        startActivity(act);
                    } else {
                        mySQLiteAdapter.insertToOrders(x.order, x.date, x.product, x.note, x.price,  x.payment, x.paymentMethod, x.paymentType, x.fsNo
                                ,x.customer, x.customer2, x.phone, x.phone2, x.retailShop, x.orderState);
                        Intent act = new Intent(OrderConfirmActivity.this, OrderActivity.class);
                        startActivity(act);
                    }
                } catch (Exception e){
                    Toast.makeText(getBaseContext(), "Error!!! there was a database error"+e,
                            Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.backButton:
                super.onBackPressed();
                finish();
                break;
            default:
                break;
        }
    }
}
