package com.credoxyz.retailshop;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class SalesConfirmActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView date, orderNo, price, product, paymentType, fsNo, notes, customer, customer2, phone1, phone2, payment, paymentMethod, amount_due;
    private Button confirm, back;
    Sales x;
    private LinearLayout paymentHistory;
    private SQLiteAdapter mySQLiteAdapter;
    String act, bId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_confirm);

        act = getIntent().getStringExtra("act");
        bId = getIntent().getStringExtra("id");
        confirm = findViewById(R.id.confirmButton);
        back = findViewById(R.id.backButton);
        confirm.setOnClickListener(this);
        back.setOnClickListener(this);

        x = (Sales) getIntent().getSerializableExtra("obj");
        date = findViewById(R.id.date_pick);
        orderNo = findViewById(R.id.orderNoText);
        price = findViewById(R.id.priceText);
        product = findViewById(R.id.productText);
        paymentType = findViewById(R.id.paymentTypeText);
        paymentMethod = findViewById(R.id.paymentMethodText);
        fsNo = findViewById(R.id.fsNo);
        notes = findViewById(R.id.notesText);
        customer = findViewById(R.id.customerText);
        customer2 = findViewById(R.id.customer2Text);
        phone1 = findViewById(R.id.phone1Text);
        phone2 = findViewById(R.id.phone2Text);
        payment = findViewById(R.id.payment);
        amount_due = findViewById(R.id.amount_due);

        date.setText(x.date);
        orderNo.setText(x.orderNO);
        paymentType.setText(x.paymentType);
        fsNo.setText(x.fsNo);
        notes.setText(x.notes);
        payment.setText(x.payment);
        paymentMethod.setText(x.paymentMethod);

        Float total = 0.0f;
        paymentHistory = findViewById(R.id.paymentHistory_layout);
        paymentHistory.removeAllViews();
        String [] ar = getDetails(x.orderNO);
        HashMap<Integer, Payments> mapList = getSalePaymentDetails(x.orderNO);
        try {
            Log.d("arr", mapList.get(0).payment.toString());
            for (int i=0; i<=mapList.size(); i++){
                LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                TextView tv=new TextView(this);
                tv.setLayoutParams(lparams);
                total = total + Float.valueOf(mapList.get(i).payment.toString());
                tv.setText(mapList.get(i).paymentType.toString()+" - "+mapList.get(i).payment.toString());
                paymentHistory.addView(tv);
            }
        } catch (Exception e){

        }
        product.setText(ar[2]);
        price.setText(ar[4]);
        customer.setText(ar[9]);
        customer2.setText(ar[10]);
        phone1.setText(ar[11]);
        phone2.setText(ar[12]);

        LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        TextView tv=new TextView(this);
        tv.setLayoutParams(lparams);
        total = total + Float.valueOf(ar[5]);
        tv.setText(ar[7]+" - "+ar[5]);
        paymentHistory.addView(tv);
        Float amountD = Float.valueOf(ar[4]) - total - Float.valueOf(x.payment);
        amount_due.setText(Float.toString(amountD));

    }

    public String[] getDetails(String orderNo){
        mySQLiteAdapter = new SQLiteAdapter(this);
        mySQLiteAdapter.openToRead();
        Cursor cursor = mySQLiteAdapter.queueOrder(orderNo);
        cursor.moveToFirst();
        ArrayList<String> names = new ArrayList<String>();
        names.add(cursor.getString(cursor.getColumnIndex("orderNo")));
        names.add(cursor.getString(cursor.getColumnIndex("date")));
        names.add(cursor.getString(cursor.getColumnIndex("product")));
        names.add(cursor.getString(cursor.getColumnIndex("notes")));
        names.add(cursor.getString(cursor.getColumnIndex("price")));
        names.add(cursor.getString(cursor.getColumnIndex("payment")));
        names.add(cursor.getString(cursor.getColumnIndex("paymentMethod")));
        names.add(cursor.getString(cursor.getColumnIndex("paymentType")));
        names.add(cursor.getString(cursor.getColumnIndex("fsNo")));
        names.add(cursor.getString(cursor.getColumnIndex("customer")));
        names.add(cursor.getString(cursor.getColumnIndex("customer2")));
        names.add(cursor.getString(cursor.getColumnIndex("phone")));
        names.add(cursor.getString(cursor.getColumnIndex("phone2")));
        names.add(cursor.getString(cursor.getColumnIndex("retailShop")));
        names.add(cursor.getString(cursor.getColumnIndex("orderState")));
        cursor.close();
        return names.toArray(new String[names.size()]);
    }

    public String[] getAllOrderNumbers(){
        mySQLiteAdapter = new SQLiteAdapter(this);
        mySQLiteAdapter.openToRead();
        Cursor cursor = mySQLiteAdapter.queueOrdersAll();
        cursor.moveToFirst();
        ArrayList<String> names = new ArrayList<String>();
        while(!cursor.isAfterLast()) {
            names.add(cursor.getString(cursor.getColumnIndex("orderNo")));
            cursor.moveToNext();
        }
        cursor.close();
        return names.toArray(new String[names.size()]);
    }

    public HashMap getSalePaymentDetails(String orderNo){
        mySQLiteAdapter = new SQLiteAdapter(this);
        mySQLiteAdapter.openToRead();
        Cursor cursor = mySQLiteAdapter.queueSalePayments(orderNo);
        cursor.moveToFirst();
        ArrayList<String> names = new ArrayList<String>();
        HashMap<Integer, Payments> list = new HashMap<>();
        int i = 0;
        while(!cursor.isAfterLast()) {
            Payments obj = new Payments(cursor.getString(cursor.getColumnIndex("_id")),cursor.getString(cursor.getColumnIndex("orderNo")), cursor.getString(cursor.getColumnIndex("payment")),cursor.getString(cursor.getColumnIndex("paymentType")),
                    cursor.getString(cursor.getColumnIndex("paymentMethod")), cursor.getString(cursor.getColumnIndex("date")), cursor.getString(cursor.getColumnIndex("fsNo")),
                    cursor.getString(cursor.getColumnIndex("notes")));
            list.put(i, obj);
            i++;
            cursor.moveToNext();
        }
        cursor.close();
        return list;
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
                        mySQLiteAdapter.updateToSales(bId, x.orderNO, x.date, x.notes,  x.payment, x.paymentMethod, x.paymentType, x.fsNo);
                        Intent act = new Intent(SalesConfirmActivity.this, SalesNewActivity.class);
                        startActivity(act);
                    } else {
                        mySQLiteAdapter.insertToSales(x.orderNO, x.date, x.notes,  x.payment, x.paymentMethod, x.paymentType, x.fsNo);
                        Intent act = new Intent(SalesConfirmActivity.this, SalesNewActivity.class);
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
