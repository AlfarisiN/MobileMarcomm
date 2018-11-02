package com.android.mobilemarcom;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class DbQueryHelper {
    private DbSqlHelper sqlHelper;

    private String TABEL_HEADER = "question_header";
    private String Q_ID = "q_id";
    private String Q_CODE = "q_code";
    private String Q_NAME = "q_name";
    private String Q_TYPE = "q_type";
    private String TABEL_DETAIL = "Question_Detail";
    private String Q_ID2 = "q_id";
    private String Q_QH_ID = "q_qh_id";
    private String Q_NO = "q_no";
    private String Q_QUESTION = "q_question";

    public DbQueryHelper(DbSqlHelper sqlHelper) {
        this.sqlHelper = sqlHelper;
    }

//    private void openDatabase(){
//        sqlHelper.openDatabase();
//    }
//
//    private void closeDatabase(){
//        sqlHelper.close();
//    }

    //login query definisikan disini

    //method ambil semua buku
    public Cursor getAllData(){
        SQLiteDatabase db = sqlHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+TABEL_HEADER, null);

        return cursor;
    }

    public List<Header> getAllBukuListModel(){
        List<Header> bukuList = new ArrayList<>();

        Cursor cursor = getAllData();
        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            for(int c=0; c<cursor.getCount(); c++){
                cursor.moveToPosition(c);

                Header header = new Header();
                header.setId(cursor.getInt(0));
                header.setCode(cursor.getString(1));
                header.setName(cursor.getString(2).toString());
//                header.setType(cursor.get(3).toString());

                bukuList.add(header);
            }
        }

        return bukuList;
    }

    //method insert buku
    public boolean insertBuku(String code,
                              String name,
                              String type){

        ContentValues contentValues = new ContentValues();
        contentValues.put(Q_CODE, code);
        contentValues.put(Q_NAME, name);
        contentValues.put(Q_TYPE, type);

        SQLiteDatabase db = sqlHelper.getWritableDatabase();
        long status = db.insert(TABEL_HEADER, null, contentValues);

        if(status == -1){
            //gagal
            return false;
        }
        else {
            System.out.println("Sukses input data buku!");
            return true;
        }
    }

    public List<Header> cariBuku(String keyword){
        List<Header> bukuList = new ArrayList<>();

        SQLiteDatabase db = sqlHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+TABEL_HEADER+
                        " WHERE "+Q_CODE+" LIKE '%"+keyword+"%'"+
                        " OR "+Q_NAME+" LIKE '%"+keyword+"%'"+
                        " OR "+Q_TYPE+" LIKE '%"+keyword+"%'"
                , null);

        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            for(int c=0; c<cursor.getCount(); c++){
                cursor.moveToPosition(c);

                Header header = new Header();
                header.setId(cursor.getInt(0));
                header.setCode(cursor.getString(1).toString());
                header.setName(cursor.getString(2).toString());
//                header.setType(cursor.getString(3).toString());

                bukuList.add(header);
            }
        }

        return bukuList;
    }
}
