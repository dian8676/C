package com.example.sohyeon.dowazo;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CustomerDetailActivity extends AppCompatActivity implements OnClickListener {

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.query_detail);

        LinearLayout layout = (LinearLayout) findViewById(R.id.customer_detail);

        String name = "";
        try {
            // DBManager
            DBManager dbmgr = new DBManager(this);

            // DB
            SQLiteDatabase sdb = dbmgr.getReadableDatabase();
            // SQL
            String sql = "select * from person";
            Cursor cursor = sdb.rawQuery(sql, null);

            // cursor
            if (cursor.moveToNext()) {

                name     = cursor.getString(1);
                String sex      = cursor.getString(2);
                String birthday =cursor.getString(3);
                String bloodtype=cursor.getString(4);
                String allergy  = cursor.getString(5);
                String precaution= cursor.getString(6);
                String pregnant      = cursor.getString(7);
                String protector=cursor.getString(8);

                TextView tv_list = new TextView(this);


                tv_list.append(name);
                tv_list.setTextSize(20);
                tv_list.setTextColor(Color.rgb(255, 255, 0));
                tv_list.setBackgroundColor(Color.rgb(0, 0, 255));

                layout.addView(tv_list);


                TextView tv_list2 = new TextView(this);


                tv_list2.append("-성별 : " + sex  + "\n");
                tv_list2.append("-생일 : " + birthday  + "\n");
                tv_list2.append("-혈액형 : " + bloodtype  + "\n");
                tv_list2.append("-알레르기 및 부작용\n : " + allergy  + "\n");
                tv_list2.append("-주의사항\n : " + precaution  + "\n");
                tv_list2.append("-보호자 번호 : ");
                if(protector.equals(""))    tv_list2.append("없음\n");
                else    tv_list2.append(protector+"\n");
                if(sex.equals("여")) {
                    tv_list2.append("-임신여부 : " + pregnant + "\n");
                }
                layout.addView(tv_list2);
            }


            cursor.close();

            dbmgr.close();

        } catch (SQLiteException e) {

            TextView tv_err = new TextView(this);

            tv_err.append(e.getMessage());
            layout.addView(tv_err);
        }


        Button btn_update_form = (Button)findViewById(R.id.button_update_form);

        btn_update_form.setOnClickListener(this);
        btn_update_form.setTag(name);


        Button btn_delete = (Button)findViewById(R.id.button_delete);

        btn_delete.setOnClickListener(this);
        btn_delete.setTag(name);


        Button btn_list = (Button)findViewById(R.id.button_ok);

        btn_list.setOnClickListener(this);
        btn_list.setTag(name);
    }


    public void onClick(View v) {
        Intent it = new Intent();

        if (v.getId() == R.id.button_update_form) {
            it    = new Intent(this, CustomerModActivity.class);
        } else if (v.getId() == R.id.button_delete) {
            DBManager dbmgr = new DBManager(this);
            SQLiteDatabase sdb = dbmgr.getReadableDatabase();
            String sql = "delete from person";
            sdb.execSQL(sql);
            dbmgr.close();
            it = new Intent(this, ButtonMainActivity.class);
        } else if (v.getId() == R.id.button_ok) {
            it = new Intent(this, ButtonMainActivity.class);
        }
        startActivity(it);
        finish();
    }


}