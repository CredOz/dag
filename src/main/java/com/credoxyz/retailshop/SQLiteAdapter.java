package com.credoxyz.retailshop;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class SQLiteAdapter {

	public static final String MYDATABASE_NAME = "MY_DATABASE";
	public static final int MYDATABASE_VERSION = 1;

	//create table MY_DATABASE (ID integer primary key, Content text not null);
	private static final String SCRIPT_CREATE_MAINTENANCE =
		"create table maintenance ( _id integer primary key autoincrement, type text not null, due_date text not null, status text not null, " +
                "notes text not null, retailShop text not null, date text not null);";
    private static final String SCRIPT_CREATE_EXPENSES =
            "create table expenses ( _id integer primary key autoincrement, type text not null, date text not null, " +
                    "amount text not null, notes text not null, retailShop text not null);";
    private static final String SCRIPT_CREATE_ORDERS =
            "create table orders ( _id integer primary key autoincrement, orderNo text not null, date text not null, product text not null, notes text not null," +
                    "price text not null, payment text not null,paymentType text not null, paymentMethod not null,fsNo text not null,customer text not null, customer2 text not null,phone text not null,phone2 text not null, " +
                    "retailShop text not null, orderState text not null);";
    private static final String SCRIPT_CREATE_SALES =
            "create table sales ( _id integer primary key autoincrement, orderNo text not null, date text not null, notes text not null," +
                    "payment text not null, paymentMethod text not null,paymentType text not null,fsNo text not null);";
    private static final String SCRIPT_CREATE_DELIVERY =
			"create table delivery ( _id integer primary key autoincrement, orderNo text not null, date text not null, " +
					"deliveryNo text not null, driver text not null, notes text not null, retailShop text not null);";
    private static final String SCRIPT_CREATE_STOCK =
            "create table stock ( _id integer primary key autoincrement, orderNo text not null, date text not null, product text not null, notes text not null," +
                    "price text not null, payment text not null,paymentType text not null, paymentMethod not null,fsNo text not null,customer text not null, customer2 text not null,phone text not null,phone2 text not null, " +
                    "retailShop text not null, orderState text not null);";
    private static final String SCRIPT_CREATE_PRODUCTS =
            "create table products ( _id integer primary key autoincrement, name text not null, price text not null);";
    private static final String SCRIPT_CREATE_DRIVERS =
            "create table drivers ( _id integer primary key autoincrement, name text not null);";

    private SQLiteHelper sqLiteHelper;
	private SQLiteDatabase sqLiteDatabase;

	private Context context;
	
	public SQLiteAdapter(Context c){
		context = c;
	}
	
	public SQLiteAdapter openToRead() throws android.database.SQLException {
		sqLiteHelper = new SQLiteHelper(context, MYDATABASE_NAME, null, MYDATABASE_VERSION);
		sqLiteDatabase = sqLiteHelper.getReadableDatabase();
		return this;	
	}
	
	public SQLiteAdapter openToWrite() throws android.database.SQLException {
		sqLiteHelper = new SQLiteHelper(context, MYDATABASE_NAME, null, MYDATABASE_VERSION);
		sqLiteDatabase = sqLiteHelper.getWritableDatabase();
		return this;	
	}
	
	public void close(){
		sqLiteHelper.close();
	}

	//Sales
    public long insertToSales(String orderNo, String date, String notes, String payment, String paymentMethod,
    String paymentType, String fsNo){
        ContentValues contentValues = new ContentValues();
        contentValues.put("orderNo", orderNo);
        contentValues.put("date", date);
        contentValues.put("notes", notes);
        contentValues.put("payment", payment);
        contentValues.put("paymentMethod", paymentMethod);
        contentValues.put("paymentType", paymentType);
        contentValues.put("fsNo", fsNo);
        return sqLiteDatabase.insert("sales", null, contentValues);
    }

    public long updateToSales(String id, String orderNo, String date, String notes, String payment, String paymentMethod,
                              String paymentType, String fsNo){
        ContentValues contentValues = new ContentValues();
        contentValues.put("orderNo", orderNo);
        contentValues.put("date", date);
        contentValues.put("notes", notes);
        contentValues.put("payment", payment);
        contentValues.put("paymentMethod", paymentMethod);
        contentValues.put("paymentType", paymentType);
        contentValues.put("fsNo", fsNo);
        String where = "_id="+id;
        return sqLiteDatabase.update("sales", contentValues, where, null);
    }

    public int deleteAllSales(){
        return sqLiteDatabase.delete("sales", null, null);
    }

    public Cursor queueSalesAll(){
        String[] columns = new String[]{"_id", "orderNo", "date", "notes", "payment", "paymentMethod", "paymentType", "fsNo"};
        Cursor cursor = sqLiteDatabase.query("sales", columns,
                null, null, null, null, null);

        return cursor;
    }

    public Cursor queueSalePayments(String orderNo){
        String[] columns = new String[]{"_id", "orderNo", "date", "notes", "payment", "paymentMethod", "paymentType", "fsNo"};
        String arg = "orderNo="+orderNo;
        Cursor cursor = sqLiteDatabase.query("sales", columns,
                arg, null, null, null, null);

        return cursor;
    }

    //Stock
    public long insertToStock(String orderNo, String date, String product, String notes, String price,
                              String paymentType, String receipt, String customer, String customer2,String phone, String mobile){
        ContentValues contentValues = new ContentValues();
        contentValues.put("orderNo", orderNo);
        contentValues.put("date", date);
        contentValues.put("product", product);
        contentValues.put("notes", notes);
        contentValues.put("price", price);
        contentValues.put("paymentType", paymentType);
        contentValues.put("receipt", receipt);
        contentValues.put("customer", customer);
        contentValues.put("customer2", customer2);
        contentValues.put("phone", phone);
        contentValues.put("mobile", mobile);
        return sqLiteDatabase.insert("stock", null, contentValues);
    }

    public int deleteAllStock(){
        return sqLiteDatabase.delete("stock", null, null);
    }

    public Cursor queueStockAll(){
        String[] columns = new String[]{"_id", "orderNo", "date", "product", "notes", "price", "paymentType", "receipt",
                "customer", "customer2", "phone", "mobile"};
        Cursor cursor = sqLiteDatabase.query("stock", columns,
                null, null, null, null, null);

        return cursor;
    }

    //orders
    public long insertToOrders(String orderNo, String date, String product, String notes, String price, String payment, String paymentMethod,
                              String paymentType, String fsNo, String customer, String customer2,String phone, String phone2, String retailShop, String orderState){
        ContentValues contentValues = new ContentValues();
        contentValues.put("orderNo", orderNo);
        contentValues.put("date", date);
        contentValues.put("product", product);
        contentValues.put("notes", notes);
        contentValues.put("price", price);
        contentValues.put("payment", payment);
        contentValues.put("paymentMethod", paymentMethod);
        contentValues.put("paymentType", paymentType);
        contentValues.put("fsNo", fsNo);
        contentValues.put("customer", customer);
        contentValues.put("customer2", customer2);
        contentValues.put("phone", phone);
        contentValues.put("phone2", phone2);
        contentValues.put("retailShop", retailShop);
        contentValues.put("orderState", orderState);
        return sqLiteDatabase.insert("orders", null, contentValues);
    }

    public long updateToOrders(String orderNo, String date, String product, String notes, String price, String payment, String paymentMethod,
                               String paymentType, String fsNo, String customer, String customer2,String phone, String phone2, String retailShop, String orderState){
        ContentValues contentValues = new ContentValues();
        contentValues.put("orderNo", orderNo);
        contentValues.put("date", date);
        contentValues.put("product", product);
        contentValues.put("notes", notes);
        contentValues.put("price", price);
        contentValues.put("payment", payment);
        contentValues.put("paymentMethod", paymentMethod);
        contentValues.put("paymentType", paymentType);
        contentValues.put("fsNo", fsNo);
        contentValues.put("customer", customer);
        contentValues.put("customer2", customer2);
        contentValues.put("phone", phone);
        contentValues.put("phone2", phone2);
        contentValues.put("retailShop", retailShop);
        contentValues.put("orderState", orderState);
        String where = "OrderNo="+orderNo;
        return sqLiteDatabase.update("orders", contentValues, where, null);
    }

    public int deleteAllOrders(){
        return sqLiteDatabase.delete("orders", null, null);
    }

    public Cursor queueOrdersAll(){
        String[] columns = new String[]{"_id", "orderNo", "date", "product", "notes", "price", "payment", "paymentMethod", "paymentType", "fsNo",
                "customer", "customer2", "phone", "phone2", "retailShop", "orderState"};
        Cursor cursor = sqLiteDatabase.query("orders", columns,
                null, null, null, null, null);

        return cursor;
    }
    public Cursor queueOrder(String orderNo){
        String[] columns = new String[]{"_id", "orderNo", "date", "product", "notes", "price", "payment", "paymentMethod", "paymentType", "fsNo",
                "customer", "customer2", "phone", "phone2", "retailShop", "orderState"};
        String arg = "orderNo="+orderNo;
        Cursor cursor = sqLiteDatabase.query("orders", columns,
                arg, null, null, null, null);

        return cursor;
    }

	//Maintenance
	public long insertToMaintenance(String type, String due_date, String status, String notes, String retailShop, String date){
		ContentValues contentValues = new ContentValues();
		contentValues.put("type", type);
		contentValues.put("due_date", due_date);
        contentValues.put("status", status);
        contentValues.put("notes", notes);
        contentValues.put("retailShop", retailShop);
        contentValues.put("date", date);
		return sqLiteDatabase.insert("maintenance", null, contentValues);
	}
	
	public int deleteAllMaintenance(){
		return sqLiteDatabase.delete("maintenance", null, null);
	}
	
	public Cursor queueMaintenanceAll(){
		String[] columns = new String[]{"_id", "type", "due_date", "status", "notes", "retailShop", "date"};
		Cursor cursor = sqLiteDatabase.query("maintenance", columns,
				null, null, null, null, null);
		
		return cursor;
	}

    //Expenses
    public long insertToExpenses(String type, String date, String amount, String notes, String retailShop){
        ContentValues contentValues = new ContentValues();
        contentValues.put("type", type);
        contentValues.put("date", date);
        contentValues.put("amount", amount);
        contentValues.put("notes", notes);
        contentValues.put("retailShop", retailShop);
        return sqLiteDatabase.insert("expenses", null, contentValues);
    }

    public int deleteAllExpenses(){
        return sqLiteDatabase.delete("expenses", null, null);
    }

    public Cursor queueExpensesAll(){
        String[] columns = new String[]{"_id", "type", "date", "amount", "notes", "retailShop"};
        Cursor cursor = sqLiteDatabase.query("expenses", columns,
                null, null, null, null, null);

        return cursor;
    }

    //Products
    public long insertToProducts(String name, String price){
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("price", price);
        return sqLiteDatabase.insert("products", null, contentValues);
    }

    public int deleteAllProducts(){
        return sqLiteDatabase.delete("products", null, null);
    }

    public Cursor queueProductsAll(){
        String[] columns = new String[]{"_id", "name", "price"};
        Cursor cursor = sqLiteDatabase.query("products", columns,
                null, null, null, null, null);

        return cursor;
    }

    //Drivers
    public long insertToDrivers(String name){
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        return sqLiteDatabase.insert("drivers", null, contentValues);
    }

    public int deleteAllDrivers(){
        return sqLiteDatabase.delete("drivers", null, null);
    }

    public Cursor queueDriversAll(){
        String[] columns = new String[]{"_id", "name"};
        Cursor cursor = sqLiteDatabase.query("drivers", columns,
                null, null, null, null, null);

        return cursor;
    }

	//Delivery
	public long insertToDelivery(String orderNo, String date, String deliveryNo, String driver, String notes, String retailShop){
		ContentValues contentValues = new ContentValues();
		contentValues.put("orderNo", orderNo);
		contentValues.put("date", date);
		contentValues.put("deliveryNo", deliveryNo);
		contentValues.put("driver", driver);
		contentValues.put("notes", notes);
		contentValues.put("retailShop", retailShop);
		return sqLiteDatabase.insert("delivery", null, contentValues);
	}

	public int deleteAllDelivery(){
		return sqLiteDatabase.delete("delivery", null, null);
	}

	public Cursor queueDeliveryAll(){
		String[] columns = new String[]{"_id", "orderNo", "date", "customer", "deliveryNo", "driver", "notes", "retailShop"};
		Cursor cursor = sqLiteDatabase.query("delivery", columns,
				null, null, null, null, null);

		return cursor;
	}
	
	public class SQLiteHelper extends SQLiteOpenHelper {

		public SQLiteHelper(Context context, String name,
				CursorFactory factory, int version) {
			super(context, name, factory, version);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			db.execSQL(SCRIPT_CREATE_MAINTENANCE);
			db.execSQL(SCRIPT_CREATE_SALES);
            db.execSQL(SCRIPT_CREATE_ORDERS);
			db.execSQL(SCRIPT_CREATE_EXPENSES);
			db.execSQL(SCRIPT_CREATE_DELIVERY);
            db.execSQL(SCRIPT_CREATE_STOCK);
            db.execSQL(SCRIPT_CREATE_PRODUCTS);
            db.execSQL(SCRIPT_CREATE_DRIVERS);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub

		}

	}
	
}
