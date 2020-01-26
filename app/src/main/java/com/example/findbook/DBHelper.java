package com.example.findbook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper{
    public static final String DATABASE_NAME         = "Users-V09.db";
    public static final String USERS_TABLE_NAME      = "users";
    public static final String USERS_COLUMN_ID       = "id";
    public static final String USERS_COLUMN_NAME     = "name";
    public static final String USERS_COLUMN_PASSWORD = "psw";
    public static final String USERS_COLUMN_EMAIL    = "email";
    public static final String USERS_COLUMN_PHONE    = "phone";
    public static final String USERS_COLUMN_LOCATION = "location";

    public static final String BOOKS_TABLE_NAME       = "books";
    public static final String BOOKS_COLUMN_ID        = "id";
    public static final String BOOKS_COLUMN_BOOKTITLE = "title";
    public static final String BOOKS_COLUMN_USERID    = "userid";
    public static final String BOOKS_COLUMN_COUNTER   = "counter";
    public static final String BOOKS_COLUMN_IMAGEPATH = "imagepath";

    public DBHelper(Context context)   {
        super(context, DATABASE_NAME , null, 1);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL ("create table users (id integer primary key autoincrement not null, name text unique,psw text,email text,phone text,location text)");
        db.execSQL ("create table books (id integer primary key autoincrement not null, title text,userid integer,counter integer,imagepath text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
    public long insertUser (String name,String psw, String email,String phone,String street) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues ();
        contentValues.put (USERS_COLUMN_NAME, name.trim ());
        contentValues.put (USERS_COLUMN_PASSWORD, psw.trim());
        contentValues.put (USERS_COLUMN_EMAIL, email);
        contentValues.put (USERS_COLUMN_PHONE, phone);
        contentValues.put (USERS_COLUMN_LOCATION, street);

        return db.insert (USERS_TABLE_NAME, null, contentValues);
    }
    public boolean updateContact (Integer id,  String email,String phone, String location) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(USERS_COLUMN_EMAIL,email);
        contentValues.put(USERS_COLUMN_PHONE, phone);
        contentValues.put(USERS_COLUMN_LOCATION,location);

        db.update(USERS_TABLE_NAME, contentValues, "id = ? ", new String[] { Integer.toString(id) } );

        return true;
    }
    public long insertBook(String title,int userid,int counter,String imagepath){
        SQLiteDatabase db = this.getWritableDatabase ();

        ContentValues cv  = new ContentValues ();
        cv.put(BOOKS_COLUMN_BOOKTITLE,title);
        cv.put(BOOKS_COLUMN_USERID,userid);
        cv.put(BOOKS_COLUMN_COUNTER,counter);
        cv.put(BOOKS_COLUMN_IMAGEPATH,imagepath);
        return db.insert (BOOKS_TABLE_NAME,null,cv);
    }
    /* Methodoi pou dimiourgithika gia debug logous
    public String getBooks(int userid){
        String booklist = "";
        SQLiteDatabase db = this.getReadableDatabase();
        //Cursor res =  db.rawQuery( "select * from books where userid = "+userid+"", null);
        Cursor res = db.rawQuery("select * from books",null);
        for(res.moveToFirst(); !res.isAfterLast(); res.moveToNext()) {
            int ID       = res.getInt (res.getColumnIndex(BOOKS_COLUMN_ID));
            String title = res.getString (res.getColumnIndex (BOOKS_COLUMN_BOOKTITLE));
            int count = res.getInt (res.getColumnIndex (BOOKS_COLUMN_COUNTER));
            String imagepath = res.getString(res.getColumnIndex(BOOKS_COLUMN_IMAGEPATH));
            booklist += "ID:" + ID +" TITLE:" + title +" COUNT:"+ count + " IMAGEPATH:" + imagepath + "\n";
            res.moveToNext();
        }
        return booklist;
    }
    public Cursor getDataByUsername(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from users  where name="+username+"", null );
        return res;
    }
    //public ArrayList<String>  getBooksofUser(int userid){
    public ArrayList<ArrayList<String>> getBooksofUser(int userid) {

        ArrayList<ArrayList<String>> ret = new ArrayList<ArrayList<String>> ();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery( "select * from books where userid = "+userid+"", null);
        //Cursor res = db.rawQuery ("select * from books order by id desc", null);
        for(res.moveToFirst(); !res.isAfterLast(); res.moveToNext()) {
            int ID       = res.getInt (res.getColumnIndex(BOOKS_COLUMN_ID));
            String title = res.getString (res.getColumnIndex (BOOKS_COLUMN_BOOKTITLE));
            int count    = res.getInt (res.getColumnIndex (BOOKS_COLUMN_COUNTER));
            String path  = res.getString(res.getColumnIndex(BOOKS_COLUMN_IMAGEPATH));
            ArrayList<String> local = new ArrayList<String> ();
            //local.add(Integer.toString (ID));
            local.add(title);
            local.add(Integer.toString (count));
            local.add(path);
            ret.add(local);
            res.moveToNext();
        }
        return ret;
    }
    public String[][]  getBooksofUserV2(int userid){
        String local[][]= new String[4][];
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery( "select * from books where userid = "+userid+"", null);
        //Cursor res = db.rawQuery ("select * from books",null);
        for(res.moveToFirst(); !res.isAfterLast(); res.moveToNext()) {
            int ID       = res.getInt (res.getColumnIndex(BOOKS_COLUMN_ID));
            String title = res.getString (res.getColumnIndex (BOOKS_COLUMN_BOOKTITLE));
            int count    = res.getInt (res.getColumnIndex (BOOKS_COLUMN_COUNTER));
            String path  = res.getString(res.getColumnIndex(BOOKS_COLUMN_IMAGEPATH));

            local[0][res.getColumnCount ()]= Integer.toString (ID);
            local[1][res.getColumnCount ()] = title;
            local[2][res.getColumnCount ()] = Integer.toString (count);
            local[3][res.getColumnCount ()] = path;
        }
        return local;
    }

     */
    public ArrayList<ArrayList<String>> getBooksofUserV3(int userid) //methodos pou fernei ti lista twn bibliwn pou katexei o xristis apo ti bd
    {
        ArrayList<ArrayList<String>> local = new ArrayList<ArrayList<String>>();

        SQLiteDatabase db = this.getReadableDatabase ( );
        Cursor res = db.rawQuery ("select * from books where userid = " + userid + "", null);

        for (res.moveToFirst ( ); !res.isAfterLast ( ); res.moveToNext ( )) {

            int ID       = res.getInt (res.getColumnIndex(BOOKS_COLUMN_ID));
            String title = res.getString (res.getColumnIndex (BOOKS_COLUMN_BOOKTITLE));
            int count    = res.getInt (res.getColumnIndex (BOOKS_COLUMN_COUNTER));
            String path  = res.getString(res.getColumnIndex(BOOKS_COLUMN_IMAGEPATH));

            ArrayList<String> row = new ArrayList<String> ();

            row.add (title);
            row.add (Integer.toString (count));
            row.add (path);
            row.add (Integer.toString (ID));

            local.add(row);
        }
        return local;
    }
    public Boolean updateBookQuantity(int bookid, int counter)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(BOOKS_COLUMN_COUNTER, counter);
        db.update(BOOKS_TABLE_NAME, contentValues, "id = ? ", new String[] { Integer.toString(bookid) } );
        return true;
    }
    public Boolean deleteBook(int bookid)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(BOOKS_TABLE_NAME,
                "id = ? ",
                new String[] { Integer.toString (bookid) });
        return true;
    }

    public ArrayList<ArrayList<String>> SearchBooksByTitle(String title)
    {

        ArrayList<ArrayList<String>> local = new ArrayList<ArrayList<String>>();
        SQLiteDatabase db = this.getReadableDatabase ( );
        Cursor res = db.rawQuery (
                "select * from books left join users on books.userid = users.id where books.title LIKE ?", new String[] { "%"+String.valueOf (title)+"%"});
        for (res.moveToFirst ( ); !res.isAfterLast ( ); res.moveToNext ( )) {

            ArrayList<String> row = new ArrayList<String> ();

            int bookid       = res.getInt (res.getColumnIndex (BOOKS_COLUMN_ID));
            String booktitle = res.getString (res.getColumnIndex (BOOKS_COLUMN_BOOKTITLE));
            String imagepath = res.getString (res.getColumnIndex (BOOKS_COLUMN_IMAGEPATH));
            int count        = res.getInt (res.getColumnIndex (BOOKS_COLUMN_COUNTER));
            String username  = res.getString (res.getColumnIndex (USERS_COLUMN_NAME));
            String email     = res.getString (res.getColumnIndex (USERS_COLUMN_EMAIL));
            String phone     = res.getString (res.getColumnIndex (USERS_COLUMN_PHONE));
            String location  = res.getString (res.getColumnIndex (USERS_COLUMN_LOCATION));

            row.add (Integer.toString (bookid));
            row.add (booktitle);
            row.add (imagepath);
            row.add (Integer.toString (count));
            row.add (username);
            row.add (email);
            row.add (phone);
            row.add (location);

            local.add (row);
        }

        return local;
    }

    public boolean check(String usrnm,String psw){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from users where name='"+usrnm+"' and psw='"+psw+"'", null );
        return res.moveToFirst ();
        //return true;
    }
    public int getIdByUsername(String username){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from users where name='"+username+"'", null );
        res.moveToFirst ();
        return res.getColumnIndex (USERS_COLUMN_ID);

    }
    public ArrayList<String> getUserinfo(String username){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> userinfo = new ArrayList<String> ();
        Cursor res =  db.rawQuery( "select * from users where name='"+username+"'", null );
        for(res.moveToFirst(); !res.isAfterLast(); res.moveToNext()) {
            userinfo.add(res.getString (res.getColumnIndex (USERS_COLUMN_ID)));
            userinfo.add(res.getString (res.getColumnIndex (USERS_COLUMN_NAME)));
            userinfo.add(res.getString (res.getColumnIndex (USERS_COLUMN_EMAIL)));
            userinfo.add(res.getString (res.getColumnIndex (USERS_COLUMN_PHONE)));
            userinfo.add(res.getString (res.getColumnIndex (USERS_COLUMN_LOCATION)));
        }
        return userinfo;
    }
    public String getAllCotacts() // gia debug logous
    {
        String userinfo="";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from users", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            int ID      = res.getInt (res.getColumnIndex(USERS_COLUMN_ID));
            String name = res.getString (res.getColumnIndex (USERS_COLUMN_NAME) );
            String psw = res.getString (res.getColumnIndex (USERS_COLUMN_PASSWORD));
            userinfo += "ID:" + ID +" NAME:" + name +" CODE:"+psw+  "\n";
            res.moveToNext();
        }
        return userinfo;
    }
}
