package com.example.sohyeon.dowazo;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.sql.Statement;
import java.util.concurrent.Delayed;

import static com.example.sohyeon.dowazo.R.string.protector;

/**
 * Created by Sohyeon on 2017-05-22.
 */

public class ButtonMainActivity extends AppCompatActivity implements View.OnClickListener {
    private int person;
    Button btnWidget;
    public static String ambulance = "01050668676";   //119 대신 테스트용 번호
    public static String police = "01055025764";      //112 대신 테스트용 번호
    public static String numberProtector="";
    BroadcastReceiver receiver;

    private int checkWidget;

    private MyService mService;
    private boolean isBind;
    Intent intent = new Intent(ButtonMainActivity.this, MyService.class);



    ServiceConnection sconn = new ServiceConnection() {
        @Override //서비스가 실행될 때 호출
        public void onServiceConnected(ComponentName name, IBinder service) {
            MyService.MyBinder myBinder = (MyService.MyBinder) service;
            mService = myBinder.getService();


            isBind = true;
            Toast.makeText(mService, "서비스시작", Toast.LENGTH_SHORT).show();


            //  Log.e("LOG", "onServiceConnected()");
        }

        @Override //서비스가 종료될 때 호출
        public void onServiceDisconnected(ComponentName name) {
            mService = null;
            isBind = false;
            // Log.e("LOG", "onServiceDisconnected()");
        }
    };

    @Override
    public void onPause(){
        super.onPause();
        //   Log.e("LOG", "onPause()");
        //  mSensorManager.unregisterListener(mAccLis);
        finish();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();

    }


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(android.R.style.Theme_NoTitleBar);
        setContentView(R.layout.mainbutton);
        startService(new Intent(ButtonMainActivity.this, MyService.class));


        Intent it = getIntent();
        checkWidget = it.getIntExtra("widget",0);

        if (!(PermissionUtil.checkPermissions(this, Manifest.permission.SEND_SMS)
                && PermissionUtil.checkPermissions(this, Manifest.permission.RECEIVE_SMS)
                && PermissionUtil.checkPermissions(this, Manifest.permission.READ_PHONE_STATE)
                && PermissionUtil.checkPermissions(this, Manifest.permission.INTERNET)
                && PermissionUtil.checkPermissions(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                && PermissionUtil.checkPermissions(this, Manifest.permission.ACCESS_FINE_LOCATION)
                && PermissionUtil.checkPermissions(this, Manifest.permission.CALL_PHONE))) {
            PermissionUtil.requestExternalPermissions(this);
        }

        person = 0;

        try {
            // DBManager 객체 생성(DB 존재 않으면 생성)
            DBManager dbmgr1 = new DBManager(this);

            // DB 연결
            SQLiteDatabase sdb1 = dbmgr1.getReadableDatabase();
           Cursor cursor1 = sdb1.rawQuery("select count(*) from person", null);
            cursor1.moveToFirst();
            person = cursor1.getInt(0);

            cursor1.close();
            // dbmgr 객체 닫음
            dbmgr1.close();


            // DBManager 객체 생성(DB 존재 않으면 생성)
            DBManager dbmgr = new DBManager(ButtonMainActivity.this);

            // DB 연결
            SQLiteDatabase sdb = dbmgr.getReadableDatabase();
            // SQL문 실행 결과를 cursor 객체로 받음
            String sql = "select * from person";
            Cursor cursor = sdb.rawQuery(sql, null);

            // cursor 객체로 할당된 members 테이블 데이터를 한 행씩 이동하면서 출력함
            if (cursor.moveToNext()) {
                // 행의 첫 번째 열(0), ..., 8 번째 열(8)을 각각 추출함


                numberProtector = cursor.getString(8);

            }

            cursor.close();
            // dbmgr 객체 닫음
            dbmgr.close();

        } catch (SQLiteException e) {
            // DB 접속 또는 조회 시 에러 발생할 때
            //Toast.makeText(this, "DB 에러 발생", Toast.LENGTH_SHORT).show();
            Log.d("DB", "DB 객체 생성 에러");
        }

        Button btnPerson = (Button) findViewById(R.id.person);
        // '등록' 버튼 클릭 대기
        btnPerson.setOnClickListener(this);

        // ID가 button_send(main.xml)인 버튼 초기화
        Button btnWarning = (Button) findViewById(R.id.warning);
        // '전송' 버튼 클릭 대기
        btnWarning.setOnClickListener(this);
        btnWarning.setOnLongClickListener(mOnLongClickListener);

        // ID가 button_send(main.xml)인 버튼 초기화
        Button btnAmbulance = (Button) findViewById(R.id.ambulance);
        // '전송' 버튼 클릭 대기
        btnAmbulance.setOnClickListener(this);

        // ID가 button_send(main.xml)인 버튼 초기화
        Button btnPolice = (Button) findViewById(R.id.police);
        // '전송' 버튼 클릭 대기
        btnPolice.setOnClickListener(this);

        // ID가 button_send(main.xml)인 버튼 초기화
        Button btnSetting = (Button) findViewById(R.id.setting);
        // '전송' 버튼 클릭 대기
        btnSetting.setOnClickListener(this);

        btnWidget = (Button) findViewById(R.id.button_widget);
        btnWarning.setOnClickListener(this);

        if(checkWidget == 119){
            warning();
        }
    }

    private String text;
    private String strLocation = "";
    String item[]={ "떨어짐 감지", "사운드", "자동 문자 전송"};
    public static boolean checked[] ={ true,true,true};
    public static boolean serviceChange=false;


    public void warning(){ // 긴급 신고
        if (person != 0) {
            try {
                // Update location to get.
                LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 100, 1, locationListener);
                lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 1, locationListener);
            } catch (SecurityException ex) {
                Log.d("Location", "위치정보를 받아올 수 없음.");
            }

            try {
                // DBManager 객체 생성(DB 존재 않으면 생성)
                DBManager dbmgr = new DBManager(this);

                // DB 연결
                SQLiteDatabase sdb = dbmgr.getReadableDatabase();
                // SQL문 실행 결과를 cursor 객체로 받음
                String sql = "select * from person";
                Cursor cursor = sdb.rawQuery(sql, null);

                // cursor 객체로 할당된 members 테이블 데이터를 한 행씩 이동하면서 출력함
                if (cursor.moveToNext()) {
                    // 행의 첫 번째 열(0), ..., 8 번째 열(8)을 각각 추출함

                    String name = cursor.getString(1);
                    String sex = cursor.getString(2);
                    String birthday = cursor.getString(3);
                    String bloodtype = cursor.getString(4);
                    String allergy = cursor.getString(5);
                    String precaution = cursor.getString(6);
                    String pregnant = cursor.getString(7);
                    String protector = cursor.getString(8);

                    text = name + " / ";
                    text = text + sex + " / ";
                    text = text + birthday + " / ";
                    text = text + bloodtype;
                    if (!allergy.equals("없음"))
                        text = text + " / 알레르기 : " + allergy;
                    if (!precaution.equals("없음"))
                        text = text + " / 주의 : " + precaution;
                    if (!pregnant.equals("X"))
                        text = text + " / " + "임신";

                    numberProtector = protector;

                }
                if (strLocation.equals("")) {
                    Toast.makeText(this, "위치 정보를 불러오고 있습니다.", Toast.LENGTH_SHORT).show();
                }

            } catch (SQLiteException e) {
                // DB 접속 또는 조회 시 에러 발생할 때
            }
        } else {
            Toast.makeText(getBaseContext(), "등록된 사람이 없습니다", Toast.LENGTH_SHORT).show();
        }

    }


    Button.OnLongClickListener mOnLongClickListener = new Button.OnLongClickListener() {
        public boolean onLongClick(View v) {

            if (v.getId() == R.id.warning) {
                if (!(PermissionUtil.checkPermissions(ButtonMainActivity.this, Manifest.permission.SEND_SMS)
                        && PermissionUtil.checkPermissions(ButtonMainActivity.this, Manifest.permission.RECEIVE_SMS)
                        && PermissionUtil.checkPermissions(ButtonMainActivity.this, Manifest.permission.READ_PHONE_STATE)
                        && PermissionUtil.checkPermissions(ButtonMainActivity.this, Manifest.permission.INTERNET)
                        && PermissionUtil.checkPermissions(ButtonMainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        && PermissionUtil.checkPermissions(ButtonMainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION))) {
                    PermissionUtil.requestExternalPermissions(ButtonMainActivity.this);
                } else {
                    warning();
                }
                return true;
            }
            return false;
        }

    };






    //긴급 신고 버튼이 클릭되었을 때
    public void onClick(View v) {
        if (v.getId() == R.id.warning) {
            Toast.makeText(ButtonMainActivity.this, "길게 누르면 신고됩니다.", Toast.LENGTH_SHORT).show();
        } else {
            final String items[] = {"전화", "문자"};
            switch (v.getId()) {
                case R.id.ambulance:
                    new AlertDialog.Builder(ButtonMainActivity.this)
                            .setTitle("119 응급신고")
                            .setItems(items, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent();
                                    String number = ambulance;
                                    if (items[which].equals("전화")) {
                                        if(!(PermissionUtil.checkPermissions(ButtonMainActivity.this, Manifest.permission.CALL_PHONE))) {
                                            PermissionUtil.requestExternalPermissions(ButtonMainActivity.this);
                                        }else {
                                            Uri uri = Uri.parse("tel:" + number);
                                            intent = new Intent(Intent.ACTION_CALL, uri);
                                        }
                                    } else {
                                        intent = new Intent(ButtonMainActivity.this, SendMessage.class);
                                        intent.putExtra("number", number);
                                    }
                                    startActivity(intent);
                                    finish();
                                }
                            })
                            .setNegativeButton("취소", null)
                            .show();
                    break;
                case R.id.police:
                    new AlertDialog.Builder(ButtonMainActivity.this)
                            .setTitle("112 범죄신고")
                            .setItems(items, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent=new Intent();
                                    String number = police;
                                    if (items[which].equals("전화")) {
                                        if (items[which].equals("전화")) {
                                            if (!(PermissionUtil.checkPermissions(ButtonMainActivity.this, Manifest.permission.CALL_PHONE))) {
                                                PermissionUtil.requestExternalPermissions(ButtonMainActivity.this);
                                            } else {
                                                Uri uri = Uri.parse("tel:" + number);
                                                intent = new Intent(Intent.ACTION_CALL, uri);
                                            }
                                        }
                                    } else {
                                        intent = new Intent(ButtonMainActivity.this, SendMessage.class);
                                        intent.putExtra("number", number);
                                    }
                                    startActivity(intent);
                                    finish();
                                }
                            })
                            .setNegativeButton("취소", null)
                            .show();
                    break;
                case R.id.person:
                    Intent it;
                    if (person == 0) {
                        it = new Intent(ButtonMainActivity.this, CustomerRegActivity.class);
                    } else {
                        it = new Intent(ButtonMainActivity.this, CustomerDetailActivity.class);
                    }
                    startActivity(it);
                    finish();
                    break;
                default:
                    //설정 만들어야함


                    new AlertDialog.Builder(ButtonMainActivity.this)
                            .setTitle(R.string.setting)
                           // .setMessage(R.string.setting)

                            .setMultiChoiceItems(item, checked,
                                    new DialogInterface.OnMultiChoiceClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                            checked[which] = isChecked;
                                        }
                                    })
                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //stopService(new Intent(ButtonMainActivity.this, MyService.class));

                                    /*
                                    SoundPool sound_pool= new SoundPool( 1, AudioManager.STREAM_ALARM, 0 );
                                    int sound_beep = 2;
                                    sound_beep = sound_pool.load( ButtonMainActivity.this.getBaseContext(), R.raw.beep, 1 );


                                    Toast.makeText(ButtonMainActivity.this, "소라소리" + sound_beep, Toast.LENGTH_SHORT).show();

                                    sound_pool.play(sound_beep,1,1,0,1,1);
                                    */
                                    //Toast.makeText(ButtonMainActivity.this, sound_beep, Toast.LENGTH_SHORT).show();


                                    serviceChange=true;
                                    try{Thread.sleep(500);}catch(Exception e){}

                                    if(checked[0]){
                                        Toast.makeText(ButtonMainActivity.this, "서비스시작", Toast.LENGTH_SHORT).show();
                                        startService(new Intent(ButtonMainActivity.this, MyService.class));

                                    }



                                }
                            })

                            .show();
                    break;
            }
        }

    }


    public void smsSend(String smsNumber, String smsText) {
        String SENT = "SMS_SENT";
        String DELIVERED = "SMS_DELIVERED";

        PendingIntent sentPI = PendingIntent.getBroadcast(ButtonMainActivity.this, 0, new Intent(SENT), 0);
        PendingIntent deliveredPI = PendingIntent.getBroadcast(ButtonMainActivity.this, 0, new Intent(DELIVERED), 0);

        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        Toast.makeText(getBaseContext(), "메시지 전송 되었습니다.", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter(SENT));

        SmsManager sms = SmsManager.getDefault();

        if (smsText.length() <= 70) {
            sms.sendTextMessage(smsNumber, null, smsText, sentPI, deliveredPI);
        } else {
            String tmpText;
            for (int stNum = 0, finNum = 70; stNum < smsText.length(); stNum += 70) {
                tmpText = smsText.substring(stNum, finNum);
                sms.sendTextMessage(smsNumber, null, tmpText, sentPI, deliveredPI);
                if (finNum + 70 <= smsText.length())
                    finNum += 70;
                else finNum = smsText.length();
            }
        }// 잘라서 보낸 거..

        Toast.makeText(getBaseContext(), "메시지 전송 내용\n" + smsText, Toast.LENGTH_SHORT).show();

    }

    public LocationListener locationListener = new LocationListener() {

        @Override
        public void onLocationChanged(Location location) {
            LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            // Get the last location, and update UI.
            Location lastLotation = location;

            strLocation = lastLotation.getLatitude() + ", " + lastLotation.getLongitude();

            if (!text.equals("")) {
                text = "[긴급신고] 위치 : " + strLocation + "\n" + text;
                smsSend(ambulance, text);
                if (!numberProtector.equals("")) { //보호자 없는 경우
                    smsSend(numberProtector, text);
                }
            } else {
                Toast.makeText(getBaseContext(), "전송 실패", Toast.LENGTH_SHORT).show();
            }

            // Stop the update to prevent changing the location.
            lm.removeUpdates(this);
        }

        @Override
        public void onProviderDisabled(String provider) {
            //위치정보 사용 불가능
            Log.d("location", "onProviderDisabled, provider:" + provider);
            Toast.makeText(getBaseContext(), "위치 정보를 사용할 수 없습니다. GPS를 확인해주세요.", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onProviderEnabled(String provider) {
            //위치정보 사용 가능
            Log.d("location", "onProviderEnabled, provider:" + provider);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            //위치정보 상태 바뀜
            Log.d("location", "onStatusChanged, provider:" + provider + ", status:" + status + " ,Bundle:" + extras);
        }


    };


    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResult) {
        if (requestCode == PermissionUtil.REQUEST_STORAGE) {
            if (PermissionUtil.verifyPermission(grantResult)) {
                //요청한 권한을 얻었으므로, 원하는 메소드를 사용
            } else {
                showRequestAgainDialog();
            }
        } else {
            ButtonMainActivity.super.onRequestPermissionsResult(requestCode, permissions, grantResult);
        }
    }

    private void showRequestAgainDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ButtonMainActivity.this);
        builder.setMessage("이 권한은 신고에 꼭 필요한 권한이므로, 설정에서 활성화부탁드립니다.");
        builder.setPositiveButton("설정", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).setData(Uri.parse("package:" + getPackageName()));
                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    e.printStackTrace();
                    Intent intent = new Intent(Settings.ACTION_MANAGE_APPLICATIONS_SETTINGS);
                    startActivity(intent);
                }
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //취소하였음
            }
        });
        builder.create();
    }


}

