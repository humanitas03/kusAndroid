package dlswp113.com.kwebview.helper;

/**
 * Author: Ravi Tamada
 * URL: www.androidhive.info
 * twitter: http://twitter.com/ravitamada
 * */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.HashMap;

public class SQLiteHandler extends SQLiteOpenHelper{

    private static final String TAG = SQLiteHandler.class.getSimpleName();

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "unipair_database_tables";

    // Login table name
    private static final String TABLE_USER = "user_info";

    // Login Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_PHONENUMBER = "phonenumber";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_UID = "uid";
    private static final String KEY_CREATED_AT = "created_at";
    private static final String KEY_ADDRESS = "address";
    private static final String KEY_ENCRYPTED_PW = "encrypted_password";
    private static final String KEY_IS_VENDOR = "is_vendor";

    public SQLiteHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        Log.i("Create Table" , "CREATE TABLE");
        String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_USER + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_PHONENUMBER + " TEXT,"
                + KEY_EMAIL + " TEXT UNIQUE," + KEY_UID + " TEXT,"
                + KEY_CREATED_AT + " TEXT," + KEY_ADDRESS + " TEXT," +
                KEY_IS_VENDOR + " TEXT," +
                KEY_ENCRYPTED_PW +" TEXT" + ")";
        db.execSQL(CREATE_LOGIN_TABLE);

        Log.d(TAG, "Database tables created");
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        Log.i("UPGRADE" , "UPGRADE");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);

        // Create tables again
        onCreate(db);
    }

    /**
     * Storing user details in database
     * */
    public void addUser(String phonenumber, String email, String uid,
                        String created_at, String address, String encrypted_pw,
                        String is_vendor) {
        SQLiteDatabase db = this.getWritableDatabase();
        Log.i("email!!!", "EMAIL: " + email);
        ContentValues values = new ContentValues();
        values.put(KEY_PHONENUMBER, phonenumber); // Name
        values.put(KEY_EMAIL, email); // Email
        values.put(KEY_UID, uid); // Email
        values.put(KEY_CREATED_AT, created_at); // Created At
        values.put(KEY_ADDRESS, address); // address
        values.put(KEY_ENCRYPTED_PW, encrypted_pw);
        values.put(KEY_IS_VENDOR, is_vendor);
        // Inserting Row
        long id = db.insert(TABLE_USER, null, values);
        db.close(); // Closing database connection

        Log.d(TAG, "New user inserted into sqlite: " + id);
    }

    public void addVUser(String vphonenumber, String vemail, String uid,
                         String created_at, String vaddress,
                         String vcertnum, String vcerrier, String vexpert, String is_vendor) {
        SQLiteDatabase db = this.getWritableDatabase();
        Log.i("email!!!", "EMAIL: " + vemail);
        ContentValues values = new ContentValues();
        values.put(KEY_PHONENUMBER, vphonenumber); // Name
        values.put(KEY_EMAIL, vemail); // Email
        values.put(KEY_UID, uid); // Email
        values.put(KEY_CREATED_AT, created_at); // Created At
        values.put(KEY_ADDRESS, vaddress); // address
        values.put(KEY_IS_VENDOR, is_vendor);
        //values.put(KEY_VCERTNUM, vcertnum); // certnum
        //values.put(KEY_VCERRIER, vcerrier); // cerrier
        //values.put(KEY_VEXPERT, vexpert); // vexert



        // Inserting Row
        long id = db.insert(TABLE_USER, null, values);
        db.close(); // Closing database connection

        Log.d(TAG, "New Vuser inserted into sqlite: " + id);
    }

    /**
     * Getting user data from database
     * */
    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<String, String>();
        String selectQuery = "SELECT  * FROM " + TABLE_USER;


        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        String[] columns = cursor.getColumnNames();

        for(int i=0; i<8; i++) {
            Log.i("columns", columns[i]);
        }
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            user.put("name", cursor.getString(1));
            user.put("email", cursor.getString(2));
            user.put("uid", cursor.getString(3));
            user.put("created_at", cursor.getString(4));
            user.put("address", cursor.getString(5));
            user.put("is_vendor", cursor.getString(6));
            user.put("encrypted_password", cursor.getString(7));
        }
        cursor.close();
        db.close();
        // return user
        Log.d(TAG, "Fetching user from Sqlite: " + user.toString());

        return user;
    }

    /**
     * Re crate database Delete all tables and create them again
     * */
    public void deleteUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_USER, null, null);
        db.close();

        Log.d(TAG, "Deleted all user info from sqlite");
    }

}
