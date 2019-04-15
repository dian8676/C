package com.example.sohyeon.dowazo;

/**
 * Created by Seol on 2017-04-02.
 */

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBManager extends SQLiteOpenHelper {

    // DBManager 클래스의 객체가 만들어질 때 실행됨(생성자)
    public DBManager(Context context) {
        // DB를 생성함(이미 생성된 경우는 생성되지 않음)
        super(context, "myDB1", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 테이블을 생성함(이미 생성된 경우는 생성되지 않음)
        db.execSQL("create table person (id boolean, name text, sex char, birthday date, " +
                "bloodtype char, allergy text, precaution text, pregnant char, protector char);");
    }

    // 존재하는 DB와 버전이 다른 경우
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

}
