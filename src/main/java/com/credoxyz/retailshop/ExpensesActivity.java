package com.credoxyz.retailshop;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ExpensesActivity extends AppCompatActivity {

    private EditText date, notes, amount;
    DatePickerDialog datePickerDialog;
    private SQLiteAdapter mySQLiteAdapter;
    private Spinner typeText;
    private String date_db, type_db, amount_db, notes_db;
    private Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenses);
        date = (EditText) findViewById(R.id.date_pick);
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("d/M/yyyy");
        String formattedDate = df.format(c);
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
                datePickerDialog = new DatePickerDialog(ExpensesActivity.this,
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
        /*
         * 	Create/Open a SQLite database
         *  and fill with dummy content
         *  and close it
         */
        mySQLiteAdapter = new SQLiteAdapter(this);
        mySQLiteAdapter.openToWrite();
        //mySQLiteAdapter.deleteAll();
        addItemsOnSpinner();
        submit = findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                amount = (EditText) findViewById(R.id.amount);
                notes = (EditText) findViewById(R.id.notesText);
                // User chose the "Settings" item, show the app settings UI...
                date_db = date.getText().toString();
                notes_db = notes.getText().toString();
                type_db = typeText.getSelectedItem().toString();
                amount_db = amount.getText().toString();
                if (!date_db.equals("") || !notes_db.equals("")){
                    try {
                        mySQLiteAdapter.insertToExpenses(""+type_db, "" + date_db, ""+amount_db, "" + notes_db, "");
                    } catch (Exception e){
                        Toast.makeText(getBaseContext(), "Error!!! there was a database error",
                                Toast.LENGTH_LONG).show();
                    }
                    date.setText(null);
                    notes.setText(null);
                } else {
                    Toast.makeText(getBaseContext(), "You should fill all the values to continue.",
                            Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    // add items into spinner dynamically
    public void addItemsOnSpinner() {
        //setting the spinner for order numbers
        typeText = (Spinner) findViewById(R.id.expenseTypeText);
        List<String> orderNumberList = new ArrayList<String>();
        orderNumberList.add("Select Type");
        orderNumberList.add("Vehicle");
        orderNumberList.add("Gas");
        orderNumberList.add("water");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, orderNumberList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeText.setAdapter(dataAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflate the menu; //this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                // User chose the "Settings" item, show the app settings UI...
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
}
