package com.example.dianp.midtest;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;

public class CustomerRegActivity extends AppCompatActivity implements OnClickListener {

    private DBManager dbmgr;
    Spinner spinner;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join_form);


        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,R.array.bloodtype_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner=(Spinner)findViewById(R.id.spinner_bloodtype);
        spinner.setAdapter(adapter);

        // ID가 Button_Store인 버튼 인식 및 클릭 대기, OnClick()메소드 실행
        Button btn_store = (Button)findViewById(R.id.button_store);
        btn_store.setOnClickListener(this);

        // ID가 Button_list인 버튼 인식 및 클릭 대기, OnClick()메소드 실행
        Button btn_list = (Button)findViewById(R.id.button_cancel);
        btn_list.setOnClickListener(this);
    }

    public void onClick(View v) {

        // '목록' 버튼을 클릭한 경우, 고객목록으로 이동
        if (v.getId() == R.id.button_cancel) {
            // 현재 클래스에서 호출할 클래스 지정
            Intent it = new Intent(this, MainActivity.class);
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
                //생일 추출
                DatePicker dp_birthday = (DatePicker) findViewById(R.id.date_birthday);
                int year = dp_birthday.getYear();
                int month = dp_birthday.getMonth();
                int day = dp_birthday.getDayOfMonth();
                String str_birthday = year + "-" + (month + 1) + "-" + day;

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


                    // 임신여부 추출
                    CheckBox chk_pregnant = (CheckBox) findViewById(R.id.check_pregnant);
                    char str_pregnant = 'X';
                    if (str_sex.equals("여") && chk_pregnant.isChecked()) {
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
                            sdb.execSQL("insert into person values( 1, '"
                                    + str_name + "', '" + str_sex + "', '"
                                    + str_birthday + "', '" + spn_blood + "', '"
                                    + str_allergy + "', '" + str_precaution + "', '"
                                    + str_pregnant + "', '" + str_protector + "');");
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
