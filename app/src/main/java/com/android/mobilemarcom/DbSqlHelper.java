package com.android.mobilemarcom;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DbSqlHelper extends SQLiteOpenHelper{
    private Context context;
    private SQLiteDatabase myDb;

    private static final String DATABASE_NAME = "test.db";
    private static final String DATABASE_PATH = "/data/data/com.android.mobilemarcom/databases/";
    private static final int DATABASE_VERSION = 1;

    public DbSqlHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion < newVersion){
            deleteDatabase();
        }
    }

    /*
    method untuk cek database, exist or not
     */
    private boolean checkDatabase(){
        SQLiteDatabase checkDb = null;

        try{
            checkDb = SQLiteDatabase.openDatabase(
                    DATABASE_PATH+DATABASE_NAME,
                    null,
                    SQLiteDatabase.OPEN_READONLY
            );
        }catch (SQLException s){
            Log.d("SQLException", s.getMessage());
            System.out.println("checkDatabase SQLException : "+s.getMessage());
        } finally {
            if(checkDb != null) {
                checkDb.close();
            }
        }

        return checkDb == null ? false : true;
    }

    /*
    method untuk create database dengan cara import dari sql file
    menggunakan stream
    @throws IOException
     */
    private void importDatabase() throws IOException {
        //buka koneksi ke file database
        InputStream inputStream = context.getAssets().open(DATABASE_NAME);

        //siapkan path utk menyimpan database
        String outputFileName = DATABASE_PATH+DATABASE_NAME;

        //tulis database (isi)
        OutputStream outputStream = new FileOutputStream(outputFileName);

        //konversi byte jadi file
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) > 0){
            outputStream.write(buffer, 0, length);
        }

        //tutup semua connection
        outputStream.flush();
        outputStream.close();
        inputStream.close();

        System.out.println("Import Database from SQL file success");
    }

    /*
    method untuk create new database
     */
    public void createDatabaseFromImportedFile() throws IOException{
        if(checkDatabase()){
            //database sudah ada
            System.out.println("Database sudah ada!");
        }
        else{
            //database belum ada
            SQLiteDatabase readDb = this.getReadableDatabase();
            readDb.close();

            try{
                importDatabase();
            }catch (IOException e){
                System.out.println("Error importing database from SQL file : "+e.getMessage());
                throw new Error("Error importing database from SQL file");
            }
        }
    }

    /*
    method untuk buka koneksi ke database
     */
    public void openDatabase() throws SQLException{
        myDb = SQLiteDatabase.openDatabase(DATABASE_PATH+DATABASE_NAME,
                null,
                SQLiteDatabase.OPEN_READONLY);
    }

    /*
    method untuk tutup koneksi ke database
     */
    @Override
    public synchronized void close() {
        if(myDb != null){
            myDb.close();
        }

        super.close();
    }

    /*
    method untuk delete database
     */
    public void deleteDatabase(){
        File file = new File(DATABASE_PATH+DATABASE_NAME);
        if(file.exists()){
            file.delete();

            System.out.println("Delete database success!");
        }
    }
}

