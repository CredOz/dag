package com.credoxyz.retailshop;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class ExpensesViewActivity extends AppCompatActivity {
    private SQLiteAdapter mySQLiteAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenses_view);

        ListView listContent = (ListView)findViewById(R.id.contentlist);
        /*
         * 	Open the same SQLite database
         * 	and read all it's content.
         */
        try {
            mySQLiteAdapter = new SQLiteAdapter(this);
            mySQLiteAdapter.openToRead();

            Cursor cursor = mySQLiteAdapter.queueExpensesAll();
            startManagingCursor(cursor);
            String[] columns = new String[]{"type", "date", "_id", "amount", "notes"};
            int[] to = new int[]{R.id.type, R.id.date, R.id.id, R.id.amount, R.id.notes};

            SimpleCursorAdapter cursorAdapter =
                    new SimpleCursorAdapter(this, R.layout.activity_expenses_view_row, cursor, columns, to);

            listContent.setAdapter(cursorAdapter);

            mySQLiteAdapter.close();
        } catch (Exception e){
            Toast.makeText(getBaseContext(), "You just refreshed."+e,
                    Toast.LENGTH_LONG).show();
        }
    }
}
