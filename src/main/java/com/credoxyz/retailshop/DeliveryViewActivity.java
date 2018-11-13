package com.credoxyz.retailshop;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class DeliveryViewActivity extends AppCompatActivity {
    private SQLiteAdapter mySQLiteAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_view);
        ListView listContent = (ListView)findViewById(R.id.contentlist);
        /*
         * 	Open the same SQLite database
         * 	and read all it's content.
         */
        try {
            mySQLiteAdapter = new SQLiteAdapter(this);
            mySQLiteAdapter.openToRead();

            Cursor cursor = mySQLiteAdapter.queueDeliveryAll();
            startManagingCursor(cursor);
            String[] columns = new String[]{"orderNo", "date", "_id", "customer", "notes"};
            int[] to = new int[]{R.id.orderNo, R.id.date, R.id.id, R.id.customer, R.id.notes};

            SimpleCursorAdapter cursorAdapter =
                    new SimpleCursorAdapter(this, R.layout.activity_delivery_view_row, cursor, columns, to);

            listContent.setAdapter(cursorAdapter);

            mySQLiteAdapter.close();
        } catch (Exception e){
            Toast.makeText(getBaseContext(), "You just refreshed."+e,
                    Toast.LENGTH_LONG).show();
        }
    }
}
