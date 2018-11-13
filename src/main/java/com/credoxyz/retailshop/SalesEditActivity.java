package com.credoxyz.retailshop;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SalesEditActivity extends AppCompatActivity {
    private Spinner orderNo, paymentType, paymentMethod;
    private TextView amount_due, price, product, customer, phone, phone2, customer2;
    private EditText date, notes, fsNo, payment;
    private LinearLayout paymentHistory;
    DatePickerDialog datePickerDialog;
    private String order_db, date_db, payment_type_db, product_db, price_db, receipt_db, customer_db, phone_db, mobile_db,
            notes_db, customer2_db, payment_db, payment_method_db, fsNo_db;
    private SQLiteAdapter mySQLiteAdapter;
    Button updateButton;
    Payments orderP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales);

        product = findViewById(R.id.productText);
        price = findViewById(R.id.priceText);
        fsNo = findViewById(R.id.receiptText);
        customer = findViewById(R.id.customerText);
        customer2 = findViewById(R.id.customer2Text);
        phone = findViewById(R.id.phone1Text);
        phone2 = findViewById(R.id.phone2Text);
        notes = findViewById(R.id.notesText);
        date = (EditText) findViewById(R.id.date_pick);
        amount_due = findViewById(R.id.amount_due);
        payment = findViewById(R.id.payment);
        // perform click event on edit text
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(SalesEditActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                String date_p = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                                date.setText(date_p);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        updateButton = findViewById(R.id.submit);
        updateButton.setBackground(ContextCompat.getDrawable(this, R.drawable.update));

        Intent intent = getIntent();
        final String buttonId = intent.getStringExtra("buttonId");
        HashMap<Integer, Payments> mapList = getOrderDetails();
        for (int i=0; i<mapList.size(); i++){
            if (mapList.get(i).id.toString().equals(buttonId)){
                orderP = mapList.get(i);
            }
        }
        System.out.println(orderP.orderNo.toString());
        date.setText(orderP.date);
        notes.setText(orderP.notes);
        //customer.setText(orderP.customer);
        //customer2.setText(orderP.customer2);
        //phone.setText(orderP.phone);
        //phone2.setText(orderP.phone2);
        fsNo.setText(orderP.fsNo);
        //price.setText(orderP.price);
        payment.setText(orderP.payment);
        //orderNo.setText(orderP.order);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(SalesEditActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                String date_p = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                                date.setText(date_p);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        addItemsOnSpinner(this);
        updateButton = findViewById(R.id.submit);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                date_db = date.getText().toString();
                notes_db = notes.getText().toString();
                order_db = orderNo.getSelectedItem().toString();
                payment_type_db = paymentType.getSelectedItem().toString();
                payment_db = payment.getText().toString();
                payment_method_db = paymentMethod.getSelectedItem().toString();
                fsNo_db = fsNo.getText().toString();
                String warning = "";
                if(order_db.equals("Order")){
                    warning+="Order is empty ";
                }
                if(date_db.equals("")){
                    warning+="date is empty ";
                }
                if(notes_db.equals("")){
                    warning+="note is empty ";
                }
                if(payment_db.equals("")){
                    warning+="Payment is empty ";
                }
                if(payment_method_db.equals("")){
                    warning+="Payment method is empty ";
                }
                if(payment_type_db.equals("")){
                    warning+="Payment type is empty ";
                }
                if(fsNo_db.equals("")){
                    warning+="FS No is empty ";
                }
                Sales obj = new Sales(order_db, date_db, notes_db, payment_db, payment_type_db, payment_method_db, fsNo_db);
                if (warning.equals("")) {
                    Intent next = new Intent(SalesEditActivity.this, SalesConfirmActivity.class);
                    next.putExtra("obj", (Serializable) obj);
                    next.putExtra("act", "update");
                    next.putExtra("id", buttonId);
                    startActivity(next);
                } else {
                    Toast.makeText(getBaseContext(), ""+warning,
                            Toast.LENGTH_LONG).show();
                }
            }
        });
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
                    cursor.getString(cursor.getColumnIndex("paymentMethod")), cursor.getString(cursor.getColumnIndex("date")),cursor.getString(cursor.getColumnIndex("fsNo")),
                    cursor.getString(cursor.getColumnIndex("notes")));
            list.put(i, obj);
            i++;
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    // add items into spinner dynamically
    public void addItemsOnSpinner(Context c) {
        //setting the spinner for order numbers
        final Context cc = c;
        final String [] arr = getAllOrderNumbers();
        orderNo = (Spinner) findViewById(R.id.orderNoText);
        final Map<String, String> priceList = new HashMap<>();

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arr);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        orderNo.setAdapter(dataAdapter);
        orderNo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                Float total = 0.0f;
                paymentHistory = findViewById(R.id.paymentHistory_layout);
                paymentHistory.removeAllViews();
                int index = parentView.getSelectedItemPosition();
                String it = arr[index];
                String [] ar = getDetails(it);
                HashMap<Integer, Payments> mapList = getSalePaymentDetails(it);
                try {
                    Log.d("arr", mapList.get(0).payment.toString());
                    for (int i=0; i<mapList.size(); i++){
                        LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        TextView tv=new TextView(cc);
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
                phone.setText(ar[11]);
                phone2.setText(ar[12]);

                LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                TextView tv=new TextView(cc);
                tv.setLayoutParams(lparams);
                total = total + Float.valueOf(ar[5]);
                tv.setText(ar[7]+" - "+ar[5]);
                paymentHistory.addView(tv);
                Float amountD = Float.valueOf(ar[4]) - total;
                amount_due.setText(Float.toString(amountD));
                //price.setText(priceList.get(it));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        //setting the spinner for paymentType
        paymentType = (Spinner) findViewById(R.id.paymentTypeText);
        List<String> paymentTypeList = new ArrayList<String>();
        paymentTypeList.add("Advance Payment");
        paymentTypeList.add("Advance Payment 2");
        paymentTypeList.add("Final Payment");
        paymentTypeList.add("Full Payment");
        ArrayAdapter<String> payemntTypeDataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, paymentTypeList);
        payemntTypeDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        paymentType.setAdapter(payemntTypeDataAdapter);

        //setting the spinner for paymentType
        paymentMethod = (Spinner) findViewById(R.id.paymentMethodText);
        List<String> paymentMethodList = new ArrayList<String>();
        paymentMethodList.add("Cash");
        paymentMethodList.add("Cheque");
        ArrayAdapter<String> payemntMethodDataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, paymentMethodList);
        payemntMethodDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        paymentMethod.setAdapter(payemntMethodDataAdapter);
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
}
