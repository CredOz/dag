package com.credoxyz.retailshop;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderActivity extends AppCompatActivity {
    TextView orderNo;
    private Spinner product, paymentType, paymentMethod;
    private EditText date, notes, customer, phone, mobile, fsNo, customer2, price, payment;
    DatePickerDialog datePickerDialog;
    private String order_db, date_db, payment_type_db, product_db, price_db, fsNo_db, customer_db, phone_db,
            phone2_db, notes_db, customer2_db, payment_db, payment_method_db;
    private SQLiteAdapter mySQLiteAdapter;
    private Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        orderNo = findViewById(R.id.orderNoText);
        int ordr;
        try {
            String[] orderNoArr = getAllOrderNumbers();
            ordr = Integer.parseInt(orderNoArr[orderNoArr.length - 1]) + 1;
        } catch (Exception e){
            ordr = 10000;
        }
        String orderNumberFromSystem = Integer.toString(ordr);
        orderNo.setText(orderNumberFromSystem);
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("d/M/yyyy");
        String formattedDate = df.format(c);
        price = findViewById(R.id.priceText);
        //words for these text boxes will be taken from the db and assign as follows
        //price.setHint("bill price");
        fsNo = findViewById(R.id.fsNo);
        customer = findViewById(R.id.customerText);
        customer2 = findViewById(R.id.customer2Text);
        phone = findViewById(R.id.phone1Text);
        mobile = findViewById(R.id.phone2Text);
        notes = findViewById(R.id.notesText);
        payment = findViewById(R.id.payment);
        date = (EditText) findViewById(R.id.date_pick);
        date.setText(formattedDate);
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
                datePickerDialog = new DatePickerDialog(OrderActivity.this,
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
        submit = findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
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
                phone2_db = mobile.getText().toString();
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
                        customer_db, customer2_db, phone_db, phone2_db, "22 Shop", "To Factory");
                if (warning.equals("")) {
                    Intent next = new Intent(OrderActivity.this, OrderConfirmActivity.class);
                    next.putExtra("obj", (Serializable) obj);
                    next.putExtra("act", "add");
                    startActivity(next);
                } else {
                    Toast.makeText(getBaseContext(), ""+warning,
                            Toast.LENGTH_LONG).show();
                }
            }
        });
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

    // add items into spinner dynamically
    public void addItemsOnSpinner() {
        //setting the spinner for products
        product = (Spinner) findViewById(R.id.productText);
        final List<String> productList = new ArrayList<String>();
        final Map<String, String> priceList = new HashMap<>();
        priceList.put("product", "0");
        priceList.put("MI21", "5900");
        priceList.put("MI42", "8900");
        priceList.put("MI43", "15630");
        productList.add("Product");
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
        methodList.add("cash");
        methodList.add("credit");
        ArrayAdapter<String> methodDataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, methodList);
        methodDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        paymentMethod.setAdapter(methodDataAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_settings:
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public void onBackPressed() {
        Intent prev = new Intent(OrderActivity.this, OrderNewActivity.class);
        startActivity(prev);
    }
}

class Order implements Serializable{
    public String date, note, order, product, paymentType, payment, paymentMethod, price, fsNo, customer, customer2, phone, phone2, retailShop, orderState;

    public Order(String date, String note, String order, String product, String paymentType, String payment, String paymentMethod, String price, String fsNo, String customer, String customer2, String phone, String phone2,
                 String retailShop, String orderState) {
        this.date = date;
        this.note = note;
        this.order = order;
        this.product = product;
        this.paymentType = paymentType;
        this.payment = payment;
        this.paymentMethod = paymentMethod;
        this.price = price;
        this.fsNo = fsNo;
        this.customer = customer;
        this.customer2 = customer2;
        this.phone = phone;
        this.phone2 = phone2;
        this.retailShop = retailShop;
        this.orderState = orderState;
    }
}
