package com.credoxyz.retailshop;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderEditActivity extends AppCompatActivity {
    private SQLiteAdapter mySQLiteAdapter;
    TextView orderNo;
    private Spinner product, paymentType, paymentMethod;
    private EditText date, notes, customer, phone, phone2, fsNo, customer2, price, payment;
    DatePickerDialog datePickerDialog;
    private String order_db, date_db, payment_type_db, product_db, price_db, fsNo_db, customer_db, phone_db,
            phone2_db, notes_db, customer2_db, payment_db, payment_method_db;
    Order orderP;
    Button updateButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        orderNo = findViewById(R.id.orderNoText);
        product = findViewById(R.id.productText);
        paymentType = findViewById(R.id.paymentTypeText);
        paymentMethod = findViewById(R.id.paymentMethodText);
        date = findViewById(R.id.date_pick);
        notes = findViewById(R.id.notesText);
        customer = findViewById(R.id.customerText);
        customer2 = findViewById(R.id.customer2Text);
        phone = findViewById(R.id.phone1Text);
        phone2 = findViewById(R.id.phone2Text);
        fsNo = findViewById(R.id.fsNo);
        price = findViewById(R.id.priceText);
        payment = findViewById(R.id.payment);
        updateButton = findViewById(R.id.submit);
        updateButton.setBackground(ContextCompat.getDrawable(this, R.drawable.update));

        Intent intent = getIntent();
        String ordrNo = intent.getStringExtra("orderNo");
        HashMap<Integer, Order> mapList = getOrderDetails();
        for (int i=0; i<mapList.size(); i++){
            if (mapList.get(i).order.toString().equals(ordrNo)){
                orderP = mapList.get(i);
            }
        }
        System.out.println(orderP.order.toString());
        date.setText(orderP.date);
        notes.setText(orderP.note);
        customer.setText(orderP.customer);
        customer2.setText(orderP.customer2);
        phone.setText(orderP.phone);
        phone2.setText(orderP.phone2);
        fsNo.setText(orderP.fsNo);
        price.setText(orderP.price);
        payment.setText(orderP.payment);
        orderNo.setText(orderP.order);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(OrderEditActivity.this,
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
        addItemsOnSpinner();
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                date_db = date.getText().toString();
                notes_db = notes.getText().toString();
                order_db = orderNo.getText().toString();
                product_db = product.getSelectedItem().toString();
                payment_type_db = paymentType.getSelectedItem().toString();
                price_db = price.getText().toString();
                fsNo_db = fsNo.getText().toString();
                customer_db = customer.getText().toString();
                customer2_db = customer2.getText().toString();
                phone_db = phone.getText().toString();
                phone2_db = phone2.getText().toString();
                payment_method_db = paymentMethod.getSelectedItem().toString();
                payment_db = payment.getText().toString();
                String warning = "";
                if(date_db.equals("")){
                    warning+="date is empty ";
                }
                if(product_db.equals("Product")){
                    warning+="product is empty ";
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
                if(customer_db.equals("")){
                    warning+="Customer is empty ";
                }
                if(phone_db.equals("")){
                    warning+="Phone is empty ";
                }

                Order obj = new Order(date_db, notes_db, order_db, product_db, payment_type_db, payment_db, payment_method_db, price_db, fsNo_db,
                        customer_db, customer2_db, phone_db, phone2_db, "", "");
                if (warning.equals("")) {
                    Intent next = new Intent(OrderEditActivity.this, OrderConfirmActivity.class);
                    next.putExtra("obj", (Serializable) obj);
                    next.putExtra("act", "update");
                    startActivity(next);
                } else {
                    Toast.makeText(getBaseContext(), ""+warning,
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void addItemsOnSpinner() {
        //setting the spinner for products
        product = (Spinner) findViewById(R.id.productText);
        final List<String> productList = new ArrayList<String>();
        final Map<String, String> priceList = new HashMap<>();
        priceList.put(orderP.order, orderP.price);
        priceList.put("MI21", "5900");
        priceList.put("MI42", "8900");
        priceList.put("MI43", "15630");
        productList.add(orderP.product);
        productList.add("MI21");
        productList.add("MI42");
        productList.add("MI43");
        ArrayAdapter<String> productDataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, productList);
        productDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        product.setAdapter(productDataAdapter);
        product.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                int index = parentView.getSelectedItemPosition();
                String it = productList.get(index);
                price.setText(priceList.get(it));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        //setting the spinner for paymentType
        paymentType = (Spinner) findViewById(R.id.paymentTypeText);
        List<String> paymentTypeList = new ArrayList<String>();
        paymentTypeList.add(orderP.paymentType);
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
        List<String> methodList = new ArrayList<String>();
        methodList.add(orderP.paymentMethod);
        methodList.add("cash");
        methodList.add("credit");
        ArrayAdapter<String> methodDataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, methodList);
        methodDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        paymentMethod.setAdapter(methodDataAdapter);
    }

    public HashMap getOrderDetails(){
        mySQLiteAdapter = new SQLiteAdapter(this);
        mySQLiteAdapter.openToRead();
        Cursor cursor = mySQLiteAdapter.queueOrdersAll();
        cursor.moveToFirst();
        HashMap<Integer, Order> list = new HashMap<>();
        int i = 0;
        while(!cursor.isAfterLast()) {
            Order obj = new Order(cursor.getString(cursor.getColumnIndex("date")), cursor.getString(cursor.getColumnIndex("notes")),cursor.getString(cursor.getColumnIndex("orderNo")),
                    cursor.getString(cursor.getColumnIndex("product")),cursor.getString(cursor.getColumnIndex("paymentType")),cursor.getString(cursor.getColumnIndex("payment")),
                    cursor.getString(cursor.getColumnIndex("paymentMethod")),cursor.getString(cursor.getColumnIndex("price")),cursor.getString(cursor.getColumnIndex("fsNo")),
                    cursor.getString(cursor.getColumnIndex("customer")),cursor.getString(cursor.getColumnIndex("customer2")),cursor.getString(cursor.getColumnIndex("phone")),cursor.getString(cursor.getColumnIndex("phone2")),
                    cursor.getString(cursor.getColumnIndex("retailShop")),cursor.getString(cursor.getColumnIndex("orderState")));
            list.put(i, obj);
            i++;
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }
}
