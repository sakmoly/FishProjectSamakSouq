package qa.happytots.yameenhome.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import qa.happytots.yameenhome.model.SearchClass;
import qa.happytots.yameenhome.model.SessionClass;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "yameen_database";
    private static final String TABLE_SESSION = "yameen_table";
    private static final String TABLE_SEARCH = "Table_Search";
    private static final String KEY_ID = "id";
    private static final String KEY_SESSION_NAME = "session_name";
    private static final String KEY_NAME = "name";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_SESSION + "("
                + KEY_ID + " INTEGER PRIMARY KEY," +
                KEY_SESSION_NAME + " TEXT" +
                ")";
        db.execSQL(CREATE_CONTACTS_TABLE);



        String CREATE_SEARCH_TABLE = "CREATE TABLE " + TABLE_SEARCH + "("
                + KEY_ID + " INTEGER PRIMARY KEY," +
                KEY_NAME + " TEXT" + ")";
        db.execSQL(CREATE_SEARCH_TABLE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SESSION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SEARCH);
        onCreate(db);

    }



    public void addSession(SessionClass sessionClass) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_SESSION_NAME, sessionClass.getSession_name());

        db.insert(TABLE_SESSION, null, values);
        db.close();

    }



    SessionClass getSession(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_SESSION, new String[] { KEY_ID,
                        KEY_SESSION_NAME}, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        SessionClass contact = new SessionClass(cursor.getString(1));

        return contact;
    }


    public String getSessionValue() {

        String result = "";
        String countQuery = " SELECT " + KEY_SESSION_NAME + " from " + TABLE_SESSION + " where " + KEY_ID + " = 1;";
        SQLiteDatabase db1 = this.getReadableDatabase();
        Cursor cursor = db1.rawQuery(countQuery, null);

        if (cursor.moveToFirst()) {
            do {
                result = cursor.getString(0);
            } while (cursor.moveToNext());

        }
        cursor.close();
        return result;
    }


    public void addSearch(SearchClass contact) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, contact.getSearch()); // Contact Name

        db.insert(TABLE_SEARCH, null, values);
        db.close();
    }

    public List<SearchClass> getAllSEARCH() {
        List<SearchClass> contactList = new ArrayList<SearchClass>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_SEARCH;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                SearchClass contact = new SearchClass();
                contact.setId(cursor.getInt(0));
                contact.setSearch(cursor.getString(1));
                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        return contactList;
    }


}