package com.example.dianp.midtest;

import android.Manifest;
import android.app.Activity;

import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static String ambulance = "01050668676";   //119 대신 테스트용 번호
    public static String police = "11231564789";      //112 대신 테스트용 번호
    private static String numberProtector="";

    private String text;
    private String strLocation = "";
    private int person;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
            DBManager dbmgr = new DBManager(this);

            // DB 연결
            SQLiteDatabase sdb = dbmgr.getReadableDatabase();
            Cursor cursor = sdb.rawQuery("select count(*) from person", null);
            cursor.moveToFirst();
            person = cursor.getInt(0);

            cursor.close();
            // dbmgr 객체 닫음
            dbmgr.close();

        } catch (SQLiteException e) {
            // DB 접속 또는 조회 시 에러 발생할 때
            //Toast.makeText(this, "DB 에러 발생", Toast.LENGTH_SHORT).show();
            Log.d("DB", "DB 객체 생성 에러");
        }

    }
    public void addUser(View v){
        Intent it;
        if(person == 0){    //입력된 사람이 없을 때
            it = new Intent(MainActivity.this, CustomerRegActivity.class);
        }else {             //입력된 사람이 있을 때
            it = new Intent(MainActivity.this, CustomerDetailActivity.class);
        }
        startActivity(it);
        finish();

    }
    public void call(View v){
        final String items[] = {"전화", "문자"};
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        AlertDialog dialog;
        switch (v.getId()) {
            case R.id.ambulance:
                alert.setTitle("119 응급신고");
                alert.setItems(items, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent();
                                String number = ambulance;
                                if (items[which].equals("전화")) {
                                    if (!(PermissionUtil.checkPermissions(MainActivity.this, Manifest.permission.CALL_PHONE))) {
                                        PermissionUtil.requestExternalPermissions(MainActivity.this);
                                    } else {
                                        Uri uri = Uri.parse("tel:" + number);
                                        intent = new Intent(Intent.ACTION_CALL, uri);
                                    }
                                } else {
                                    intent = new Intent(MainActivity.this, SendMessage.class);
                                    intent.putExtra("number", number);
                                }
                                startActivity(intent);
                                finish();
                            }
                        });
                alert.setPositiveButton("취소", null);
                dialog = alert.create();
                dialog.show();
                break;
            case R.id.police:
                alert.setTitle("112 범죄신고");
                alert.setItems(items, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent();
                                String number = police;
                                if (items[which].equals("전화")) {
                                    if (items[which].equals("전화")) {
                                        if (!(PermissionUtil.checkPermissions(MainActivity.this, Manifest.permission.CALL_PHONE))) {
                                            PermissionUtil.requestExternalPermissions(MainActivity.this);
                                        } else {
                                            Uri uri = Uri.parse("tel:" + number);
                                            intent = new Intent(Intent.ACTION_CALL, uri);
                                        }
                                    }
                                } else {
                                    intent = new Intent(MainActivity.this, SendMessage.class);
                                    intent.putExtra("number", number);
                                }
                                startActivity(intent);
                                finish();
                            }
                        });
                alert.setPositiveButton("취소", null);
                dialog = alert.create();
                dialog.show();
                break;
        }
    }
    public void emergency(View v){
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


    public void smsSend(String smsNumber, String smsText) {
        String SENT = "SMS_SENT";
        String DELIVERED = "SMS_DELIVERED";

        PendingIntent sentPI = PendingIntent.getBroadcast(MainActivity.this, 0, new Intent(SENT), 0);
        PendingIntent deliveredPI = PendingIntent.getBroadcast(MainActivity.this, 0, new Intent(DELIVERED), 0);

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
            MainActivity.super.onRequestPermissionsResult(requestCode, permissions, grantResult);
        }
    }

    private void showRequestAgainDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
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
