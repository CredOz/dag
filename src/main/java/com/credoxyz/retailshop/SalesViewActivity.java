package com.credoxyz.retailshop;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class SalesViewActivity extends AppCompatActivity {
    private SQLiteAdapter mySQLiteAdapter;
    TableLayout t1;
    ArrayList<Button> mButtonList = new ArrayList<>();
    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        t1 = (TableLayout) findViewById(R.id.main_table);

        HashMap<Integer, Payments> mapList = getOrderDetails();
        try {
            //Log.d("arr", mapList.get(1).payment.toString());
            for (int i=0; i<mapList.size(); i++){
                TableRow tr_head1 = new TableRow(this);       // part1
                tr_head1.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
                String [] arr = getDetails(mapList.get(i).orderNo);
                String ordrN = mapList.get(i).orderNo;
                HashMap<Integer, Payments> saleList = getSalePaymentDetails(ordrN);
                Float total = 0.0f;
                for (int k=0; k<saleList.size(); k++){
                    total = total + Float.valueOf(saleList.get(k).payment);
                }
//
                total = total + Float.valueOf(arr[5]);
                total = Float.valueOf(arr[4]) - total;

                TextView label_order = new TextView(this);
                label_order.setText("Order #");         // part2
                label_order.setPadding(5, 5, 5, 5);
                tr_head1.addView(label_order);// add the column to the table row here

                TextView label_model = new TextView(this);    // part3
                label_model.setText("Model"); // set the text for the header
                label_model.setPadding(5, 5, 5, 5); // set the padding (if required)
                tr_head1.addView(label_model); // add the column to the table row here

                TextView label_price = new TextView(this);    // part3
                label_price.setText("Price"); // set the text for the header
                label_price.setPadding(5, 5, 5, 5); // set the padding (if required)
                tr_head1.addView(label_price); // add the column to the table row here
                t1.addView(tr_head1, new TableLayout.LayoutParams(
                        LinearLayout.LayoutParams.FILL_PARENT,                    //part4
                        LinearLayout.LayoutParams.WRAP_CONTENT));

                TableRow tr_row1 = new TableRow(this);       // part1
                tr_row1.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));

                TextView order = new TextView(this);
                order.setText(mapList.get(i).orderNo.toString());
                order.setTextColor(Color.BLACK);// part2
                order.setPadding(5, 5, 5, 5);
                tr_row1.addView(order);// add the column to the table row here

                TextView model = new TextView(this);    // part3
                model.setText(arr[1]); // set the text for the header
                model.setTextColor(Color.BLACK);
                model.setPadding(5, 5, 5, 5); // set the padding (if required)
                tr_row1.addView(model); // add the column to the table row here

                TextView price = new TextView(this);    // part3
                price.setText(arr[4]); // set the text for the header
                price.setTextColor(Color.BLACK);
                price.setPadding(5, 5, 5, 5); // set the padding (if required)
                tr_row1.addView(price); // add the column to the table row here
                t1.addView(tr_row1, new TableLayout.LayoutParams(
                        LinearLayout.LayoutParams.FILL_PARENT,                    //part4
                        LinearLayout.LayoutParams.WRAP_CONTENT));

                TableRow tr_head2 = new TableRow(this);    // part1
                tr_head2.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));

                TextView label_payment = new TextView(this);
                label_payment.setText("Payment");         // part2
                label_payment.setPadding(5, 5, 5, 5);
                tr_head2.addView(label_payment);// add the column to the table row here

                TextView label_due = new TextView(this);    // part3
                label_due.setText("Due Amount"); // set the text for the header
                label_due.setPadding(5, 5, 5, 5); // set the padding (if required)
                tr_head2.addView(label_due); // add the column to the table row here

                TextView label_date = new TextView(this);    // part3
                label_date.setText("Due Date"); // set the text for the header
                label_date.setPadding(5, 5, 5, 5); // set the padding (if required)
                tr_head2.addView(label_date); // add the column to the table row here
                t1.addView(tr_head2, new TableLayout.LayoutParams(
                        LinearLayout.LayoutParams.FILL_PARENT,                    //part4
                        LinearLayout.LayoutParams.WRAP_CONTENT));

                TableRow tr_row2 = new TableRow(this);    // part1
                tr_row2.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));

                TextView payment = new TextView(this);
                payment.setText(mapList.get(i).payment.toString());         // part2
                payment.setTextColor(Color.BLACK);
                payment.setPadding(5, 5, 5, 5);
                tr_row2.addView(payment);// add the column to the table row here

                TextView due = new TextView(this);    // part3
                due.setText(Float.toString(total)); // set the text for the header
                due.setTextColor(Color.BLACK);
                due.setPadding(5, 5, 5, 5); // set the padding (if required)
                tr_row2.addView(due); // add the column to the table row here

                TextView date = new TextView(this);    // part3
                date.setText(mapList.get(i).date.toString()); // set the text for the header
                date.setTextColor(Color.BLACK);
                date.setPadding(5, 5, 5, 5); // set the padding (if required)
                tr_row2.addView(date); // add the column to the table row here
                t1.addView(tr_row2, new TableLayout.LayoutParams(
                        LinearLayout.LayoutParams.FILL_PARENT,                    //part4
                        LinearLayout.LayoutParams.WRAP_CONTENT));

                TableRow tr_head3 = new TableRow(this);
                tr_head3.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));

                Button edit = new Button(this);
                edit.setText("Edit");
                edit.setId(Integer.valueOf(mapList.get(i).id));
                edit.setTextColor(Color.BLUE);
                edit.setPadding(5, 5, 5, 5);
                tr_head3.addView(edit);// add the column to the table row here
                edit.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        for (Button button : mButtonList) {
                            if (button.getId() == view.getId()) {
                                //button.setBackgroundColor(Color.RED);
                                int ordrId = button.getId();
                                String buttonId = Integer.toString(ordrId);
                                Intent next = new Intent(SalesViewActivity.this, SalesEditActivity.class);
                                next.putExtra("buttonId", buttonId);
                                startActivity(next);
                            } else {
                                //button.setBackgroundColor(Color.BLUE);
                            }
                        }
                    }
                });

                mButtonList.add(edit);

                t1.addView(tr_head3, new TableLayout.LayoutParams(
                        LinearLayout.LayoutParams.FILL_PARENT,                    //part4
                        LinearLayout.LayoutParams.WRAP_CONTENT));

                TableRow space = new TableRow(this);
                space.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
                space.setBackgroundColor(Color.GRAY);
                TextView spaceText = new TextView(this);    // part3
                spaceText.setPadding(5, 5, 5, 5); // set the padding (if required)
                space.addView(spaceText); // add the column to the table row here
                t1.addView(space, new TableLayout.LayoutParams(
                        LinearLayout.LayoutParams.FILL_PARENT,                    //part4
                        LinearLayout.LayoutParams.WRAP_CONTENT));
            }
        } catch (Exception e){

        }
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
                    cursor.getString(cursor.getColumnIndex("paymentMethod")), cursor.getString(cursor.getColumnIndex("date")),cursor.getString(cursor.getColumnIndex("fsNo")),
                    cursor.getString(cursor.getColumnIndex("notes")));
            list.put(i, obj);
            i++;
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public HashMap getOrderDetails(){
        mySQLiteAdapter = new SQLiteAdapter(this);
        mySQLiteAdapter.openToRead();
        Cursor cursor = mySQLiteAdapter.queueSalesAll();
        cursor.moveToFirst();
        HashMap<Integer, Payments> list = new HashMap<>();
        int i = 0;
        while(!cursor.isAfterLast()) {
            Payments obj = new Payments(cursor.getString(cursor.getColumnIndex("_id")), cursor.getString(cursor.getColumnIndex("orderNo")),cursor.getString(cursor.getColumnIndex("payment")),
                    cursor.getString(cursor.getColumnIndex("paymentType")),cursor.getString(cursor.getColumnIndex("paymentMethod")),cursor.getString(cursor.getColumnIndex("date")),
                    cursor.getString(cursor.getColumnIndex("fsNo")),cursor.getString(cursor.getColumnIndex("notes")));
            list.put(i, obj);
            i++;
            cursor.moveToNext();
        }
        cursor.close();
        return list;
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
}
