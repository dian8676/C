package com.example.dianp.midtest;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;



public class CustomerModActivity extends AppCompatActivity implements OnClickListener {

    private DBManager dbmgr;
    Spinner spinner;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_form);

        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,R.array.bloodtype_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner=(Spinner)findViewById(R.id.spinner_bloodtype);
        spinner.setAdapter(adapter);

        Button btn_delete = (Button)findViewById(R.id.button_delete);
        btn_delete.setOnClickListener(this);
        // ID가 Button_Store인 버튼 인식 및 클릭 대기, OnClick()메소드 실행
        Button btn = (Button)findViewById(R.id.button_store);
        btn.setOnClickListener(this);
        // ID가 Button_list인 버튼 인식 및 클릭 대기, OnClick()메소드 실행
        Button btn_list = (Button)findViewById(R.id.button_cancel);
        btn_list.setOnClickListener(this);

        // 등록된 회원정보 출력: 시작  ////////////////////////////

        try {
            // DBManager 객체 생성(DB 존재 않으면 생성)
            DBManager dbmgr = new DBManager(this);

            // DB 연결
            SQLiteDatabase sdb = dbmgr.getReadableDatabase();
            // SQL문 실행 결과를 cursor 객체로 받음
            String sql = "select * " +
                    "  from person" +
                    " where id = 1";
            Cursor cursor = sdb.rawQuery(sql, null);

            // cursor 객체로 할당된 members 테이블 데이터를 한 행씩 이동하면서 출력함
            if (cursor.moveToNext()) {
                // 행의 첫 번째 열(0), ..., 네 번째 열(3)을 각각 추출함
                String name      = cursor.getString(1);
                String sex       = cursor.getString(2);
                String birthday  = cursor.getString(3);
                String allergy   = cursor.getString(5);
                String precaution= cursor.getString(6);
                String pregnant  = cursor.getString(7);
                String protector = cursor.getString(8);

                // 성명 추출
                EditText et_name = (EditText)findViewById(R.id.edit_name);
                et_name.setText(name);

                RadioButton rb_sex = null;
                if (sex.equals("남"))
                    rb_sex = (RadioButton)findViewById(R.id.radio_male);
                else if (sex.equals("여"))
                    rb_sex = (RadioButton)findViewById(R.id.radio_female);
                rb_sex.setChecked(true);

                //생일 추출
                EditText et_birthday=(EditText)findViewById(R.id.edit_birthday);
                et_birthday.setText(birthday);

                //혈액형 추출 (바뀌는 경우 고려)

                //알레르기 추출
                EditText et_allergy = (EditText)findViewById(R.id.edit_allergy);
                et_allergy.setText(allergy);


                //주의사항 추출
                EditText et_precaution = (EditText)findViewById(R.id.edit_precaution);
                et_precaution.setText(precaution);

                EditText et_protector = (EditText)findViewById(R.id.edit_protector);
                et_protector.setText(protector);

                // 임신여부 추출
                CheckBox   chk_pregnant = (CheckBox)findViewById(R.id.check_pregnant);
                if (pregnant.equals("O"))
                    chk_pregnant.setChecked(true);
            }

            // cursor 객체 닫음
            cursor.close();
            // dbmgr 객체 닫음
            dbmgr.close();

        } catch (SQLiteException e) {
            // DB 접속 또는 조회 시 에러 발생할 때
        }

        // 등록된 회원정보 출력: 끝  ////////////////////////////
    }

    public void onClick(View v) {
        if (v.getId() == R.id.button_delete) {
            DBManager dbmgr = new DBManager(this);
            SQLiteDatabase sdb = dbmgr.getReadableDatabase();
            String sql = "delete from person";
            sdb.execSQL(sql);
            dbmgr.close();
            Intent it = new Intent(this, MainActivity.class);
            // 이텐트에서 지정한 액티비티 실행
            startActivity(it);
            // 현재 액티비티 종료
            finish();
        }else if (v.getId() == R.id.button_cancel) {
            // 현재 클래스에서 호출할 클래스 지정
            Intent it = new Intent(this, CustomerDetailActivity.class);
            // 이텐트에서 지정한 액티비티 실행
            startActivity(it);
            // 현재 액티비티 종료
            finish();
        } else {

            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            // 성명 추출
            EditText et_name = (EditText) findViewById(R.id.edit_name);
            String test_name = et_name.getText().toString();
            test_name = test_name.trim();
            if (test_name.equals("")) {
                alert.setTitle("오류");
                alert.setMessage("이름을 입력하지 않았습니다.");
                alert.setPositiveButton("확인", null);
                alert.show();
            } else {
                String str_name = et_name.getText().toString();

                // 성별 추출
                RadioGroup rg_sex = (RadioGroup) findViewById(R.id.radiogroup_sex);
                String str_sex = "남";
                if (rg_sex.getCheckedRadioButtonId() == R.id.radio_female) {
                    str_sex = "여";
                }
                //혈액형 추출
                String spn_blood = spinner.getSelectedItem().toString();

                //알레르기 추출
                EditText et_allergy = (EditText) findViewById(R.id.edit_allergy);
                String str_allergy = et_allergy.getText().toString();
                if (str_allergy.equals("")) {
                    str_allergy = "없음";
                }
                //주의사항 추출
                EditText et_precaution = (EditText) findViewById(R.id.edit_precaution);
                String str_precaution = et_precaution.getText().toString();
                if (str_precaution.equals("")) {
                    str_precaution = "없음";
                }

                //보호자 번호 추출
                EditText et_protector = (EditText) findViewById(R.id.edit_protector);
                String str_protector = et_protector.getText().toString();
                if (str_protector.length() > 11) {
                    alert.setTitle("오류");
                    alert.setMessage("전화번호가 너무 깁니다.");
                    alert.setPositiveButton("확인", null);
                    alert.show();
                } else {

                    // 수신여부 추출
                    CheckBox chk_pregnant = (CheckBox) findViewById(R.id.check_pregnant);
                    char str_pregnant = 'X';
                    if (chk_pregnant.isChecked()) {
                        str_pregnant = 'O';
                    }
                    if (chk_pregnant.isChecked() && str_sex.equals("남")) {
                        alert.setTitle("오류");
                        alert.setMessage("남성의 경우 임신여부 체크가 불가능합니다.");
                        alert.setPositiveButton("확인", null);
                        alert.show();
                    } else {

                        try {
                            // DB객체 생성(DB가 존재하지 않으면 생성함)
                            dbmgr = new DBManager(this);

                            SQLiteDatabase sdb;

                            // DB연결
                            sdb = dbmgr.getWritableDatabase();
                            // members 테이블에 추출정보 추가
                            String sql = "update person " +
                                    "  set name  = '" + str_name + "', " +
                                    "       sex  = '" + str_sex + "',  " +
                                    " bloodtype  = '" + spn_blood + "',  " +
                                    "   allergy  = '" + str_allergy + "',  " +
                                    "precaution  = '" + str_precaution + "',  " +
                                    "  pregnant  = '" + str_pregnant + "', " +
                                    " protector  = '" + str_protector + "'  " +
                                    " where id = 1";
                            sdb.execSQL(sql);
                            // DB닫음
                            dbmgr.close();
                        } catch (SQLiteException e) {
                            // 예외처리(생략)
                        }

                        // 현재 클래스(This)에서 호출할 클래스(QueryActivity.class) 지정
                        Intent it = new Intent(this, CustomerDetailActivity.class);
                        // 입력한 성명의 값을 저장
                        it.putExtra("it_name", str_name);
                        // 이텐트에서 지정한 액티비티 실행
                        startActivity(it);
                        // 현재 액티비티 종료
                        finish();
                    }
                }
            }
        }
    }
}