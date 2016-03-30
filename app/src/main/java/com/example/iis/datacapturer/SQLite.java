package com.example.iis.datacapturer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IIS on 9/4/2015.
 */
public class SQLite {

    public static final String DATABASE_NAME = "lionel.db";
    public static final int DATABASE_VERSION = 2;

    public static final String TABLE_DETAILS = "details";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_SEX = "sex";
    public static final String COLUMN_SURNAME = "surname";
    public static final String COLUMN_DOB = "dob";
    public static final String COLUMN_OCCUPATION = "occupation";
    public static final String COLUMN_HOME_AD = "homeAd";
    public static final String COLUMN_HOME_PHONE = "homePhone";
    public static final String COLUMN_WORK_AD = "workAd";
    public static final String COLUMN_WORK_PHONE = "workPhone";
    public static final String COLUMN_NO = "idNo";
    public static final String COLUMN_IMAGE = "image";

    private static final String TABLE_CREATE = "CREATE TABLE " + TABLE_DETAILS + " (" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_NAME + " TEXT, " +
            COLUMN_SURNAME + " TEXT, " +
            COLUMN_NO + " TEXT, " +
            COLUMN_DOB + " TEXT, " +
            COLUMN_HOME_PHONE + " TEXT, " +
            COLUMN_WORK_AD + " TEXT, " +
            COLUMN_OCCUPATION + " TEXT, " +
            COLUMN_HOME_AD + " TEXT, " +
            COLUMN_WORK_PHONE + " TEXT, " +
            COLUMN_SEX + " TEXT, " +
            COLUMN_IMAGE + " TEXT" + ");";

    String[] columns = {COLUMN_ID, COLUMN_NAME, COLUMN_SURNAME, COLUMN_DOB, COLUMN_WORK_AD, COLUMN_WORK_PHONE,
            COLUMN_HOME_AD, COLUMN_HOME_PHONE, COLUMN_OCCUPATION, COLUMN_IMAGE, COLUMN_NO, COLUMN_SEX};

    SQLiteDatabase database;
    OpenHelper helper;
    Context con;


    public class OpenHelper extends SQLiteOpenHelper {

        public OpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);

        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(TABLE_CREATE);
            Log.i("Bk", "Table was created");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_DETAILS);
            onCreate(db);

        }

    }

    public SQLite(Context context) {
        con = context;
    }

    public SQLite open() {
        helper = new OpenHelper(con);
        database = helper.getWritableDatabase();
        return this;
    }

    public void close() {
        helper.close();
    }

    public long write(String cusName, String cusSurname, String cusDOB, String cusOcc, String cusHomeAdd,
                      String cusPhone, String wkPhone, String wkAdd, String reg, String comments, String gender) {

        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME, cusName);
        cv.put(COLUMN_SURNAME, cusSurname);
        cv.put(COLUMN_DOB, cusDOB);
        cv.put(COLUMN_NO, reg);
        cv.put(COLUMN_OCCUPATION, cusOcc);
        cv.put(COLUMN_HOME_AD, cusHomeAdd);
        cv.put(COLUMN_HOME_PHONE, cusPhone);
        cv.put(COLUMN_WORK_PHONE, wkPhone);
        cv.put(COLUMN_WORK_AD, wkAdd);
        cv.put(COLUMN_SEX, gender);
        cv.put(COLUMN_IMAGE, comments);

        return database.insert(TABLE_DETAILS, null, cv);


    }

    public List<Arrange> list(String select, String criteria) {
        List<Arrange> kneel = new ArrayList<>();
        Cursor cursor = database.query(TABLE_DETAILS, columns, select, null, null, null, criteria);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                Arrange arr = new Arrange();
                arr.setId(cursor.getLong(cursor.getColumnIndex(COLUMN_ID)));
                arr.setCustomer(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
                arr.setSurname(cursor.getString(cursor.getColumnIndex(COLUMN_SURNAME)));
                arr.setOccupation(cursor.getString(cursor.getColumnIndex(COLUMN_OCCUPATION)));
                arr.setHomeAdd(cursor.getString(cursor.getColumnIndex(COLUMN_HOME_AD)));
                arr.setHomePhone(cursor.getString(cursor.getColumnIndex(COLUMN_HOME_PHONE)));
                arr.setDob(cursor.getString(cursor.getColumnIndex(COLUMN_DOB)));
                arr.setWorkAdd(cursor.getString(cursor.getColumnIndex(COLUMN_WORK_AD)));
                arr.setWorkPhone(cursor.getString(cursor.getColumnIndex(COLUMN_WORK_PHONE)));
                arr.setIdNo(cursor.getString(cursor.getColumnIndex(COLUMN_NO)));
                arr.setImage(cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE)));
                arr.setSex(cursor.getString(cursor.getColumnIndex(COLUMN_SEX)));

                kneel.add(arr);
            }
        }
        cursor.close();
        return kneel;
    }


    public String read(String idea) {
        Cursor cursor = database.query(TABLE_DETAILS, columns, null, null, null, null, null);
        String result = "";
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                result =
                        "Name:          " + cursor.getString(cursor.getColumnIndex(COLUMN_NAME)) + "\n" +
                                "Surname:       " + cursor.getString(cursor.getColumnIndex(COLUMN_SURNAME)) + "\n" +
                                "Occupation:    " + cursor.getString(cursor.getColumnIndex(COLUMN_OCCUPATION)) + "\n" +
                                "DOB:           " + cursor.getString(cursor.getColumnIndex(COLUMN_DOB)) + "\n" +
                                "Work Address:  " + cursor.getString(cursor.getColumnIndex(COLUMN_WORK_AD)) + "\n" +
                                "Home Address:  " + cursor.getString(cursor.getColumnIndex(COLUMN_HOME_AD)) + "\n" +
                                "Cell:          " + cursor.getString(cursor.getColumnIndex(COLUMN_HOME_PHONE)) + "\n" +
                                "WorkPhone:     " + cursor.getString(cursor.getColumnIndex(COLUMN_WORK_PHONE)) + "\n" +
                                "ID number:     " + cursor.getString(cursor.getColumnIndex(COLUMN_NO));

            }
        }
        cursor.close();
        return result;
    }



    public boolean remove() {
        int result = database.delete(TABLE_DETAILS, COLUMN_ID, null);
        return (result == 1);
    }

    public boolean removeFromList(long id) {

        int result = database.delete(TABLE_DETAILS, COLUMN_ID + "=" + id, null);
        return (result == 1);
    }

    public String retrieveName(long row) {

        Cursor que = database.query(TABLE_DETAILS, columns, COLUMN_ID + "=" + row, null, null, null, null);
        if (que != null) {
            que.moveToFirst();
            String name = que.getString(1);
            return name;
        }
        que.close();
        return null;
    }

    public String retrieveSurname(long row) {
        Cursor que = database.query(TABLE_DETAILS, columns, COLUMN_ID + "=" + row, null, null, null, null);
        if (que != null) {
            que.moveToFirst();
            String surname = que.getString(2);
            return surname;
        }
        return null;
    }


    public String retrieveOcc(long row) {
        Cursor que = database.query(TABLE_DETAILS, columns, COLUMN_ID + "=" + row, null, null, null, null);
        if (que != null) {
            que.moveToFirst();
            String occ = que.getString(que.getColumnIndex(COLUMN_OCCUPATION));
            return occ;
        }
        return null;
    }

    public String retrieveHomeAd(long row) {
        Cursor que = database.query(TABLE_DETAILS, columns, COLUMN_ID + "=" + row, null, null, null, null);
        if (que != null) {
            que.moveToFirst();
            String homeAd = que.getString(que.getColumnIndex(COLUMN_HOME_AD));
            return homeAd;
        }
        return null;
    }

    public String retrieveCell(long row) {
        Cursor que = database.query(TABLE_DETAILS, columns, COLUMN_ID + "=" + row, null, null, null, null);
        if (que != null) {
            que.moveToFirst();
            String cell = que.getString(que.getColumnIndex(COLUMN_HOME_PHONE));
            return cell;
        }
        return null;
    }

    public String retrieveDob(long id) {
        Cursor que = database.query(TABLE_DETAILS, columns, COLUMN_ID + "=" + id, null, null, null, null);
        if (que != null) {
            que.moveToFirst();
            String dob = que.getString(que.getColumnIndex(COLUMN_DOB));
            return dob;
        }
        return null;
    }

    public String retrievewkAd(long id) {
        Cursor que = database.query(TABLE_DETAILS, columns, COLUMN_ID + "=" + id, null, null, null, null);
        if (que != null) {
            que.moveToFirst();
            String wkAd = que.getString(que.getColumnIndex(COLUMN_WORK_AD));
            return wkAd;
        }
        return null;
    }

    public String retrieveWorkPhone(long id){
        Cursor que = database.query(TABLE_DETAILS, columns, COLUMN_ID + "=" + id, null, null, null, null);
        if (que != null) {
            que.moveToFirst();
            String wkAd = que.getString(que.getColumnIndex(COLUMN_WORK_PHONE));
            return wkAd;
        }
        return null;
    }

    public String retrieveNotes(long myID) {
        Cursor que = database.query(TABLE_DETAILS, columns, COLUMN_ID + "=" + myID, null, null, null, null);
        if (que != null) {
            que.moveToFirst();
            String wkAd = que.getString(que.getColumnIndex(COLUMN_IMAGE));
            return wkAd;
        }
        return null;
    }

    public String retrieveIdNo(long myID) {
        Cursor que = database.query(TABLE_DETAILS, columns, COLUMN_ID + "=" + myID, null, null, null, null);
        if (que != null) {
            que.moveToFirst();
            String wkAd = que.getString(que.getColumnIndex(COLUMN_NO));
            return wkAd;
        }
        return null;
    }


    public String retrieveSex(long myID) {
        Cursor que = database.query(TABLE_DETAILS, columns, COLUMN_ID + "=" + myID, null, null, null, null);
        if (que != null) {
            que.moveToFirst();
            String wkAd = que.getString(que.getColumnIndex(COLUMN_SEX));
            return wkAd;
        }
        return null;
    }


    public void editEntry(long myID, String mName, String mSurname, String mDOB, String mOcc, String mHome,
                          String mPhone, String mWorkPh, String mWorkAdd, String mIDno, String mNotes, String sex) {
        ContentValues conVal = new ContentValues();
        conVal.put(COLUMN_NAME, mName);
        conVal.put(COLUMN_SURNAME, mSurname);
        conVal.put(COLUMN_DOB, mDOB);
        conVal.put(COLUMN_SEX, sex);
        conVal.put(COLUMN_NO, mIDno);
        conVal.put(COLUMN_IMAGE, mNotes);
        conVal.put(COLUMN_OCCUPATION, mOcc);
        conVal.put(COLUMN_HOME_AD, mHome);
        conVal.put(COLUMN_HOME_PHONE, mPhone);
        conVal.put(COLUMN_WORK_AD, mWorkAdd);
        conVal.put(COLUMN_WORK_PHONE, mWorkPh);
        database.update(TABLE_DETAILS, conVal, COLUMN_ID + "=" + myID, null);
    }
}
