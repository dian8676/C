package com.example.dianp.midtest;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;


public class SendMessage extends AppCompatActivity implements View.OnClickListener {

    private String number="";
    private String strLocation="";
    private String smsText="";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_message);

        MainActivity main= new MainActivity();
        Intent intent=getIntent();
        number=intent.getStringExtra("number");
        if(number.equals(main.ambulance)){
            setTitle("119 문자 신고");
        }else if(number.equals(main.police)){
            setTitle("112 문자 신고");
        }


        // ID가 button_send(main.xml)인 버튼 초기화
        Button btnCancel = (Button) findViewById(R.id.button_cancel);
        // '전송' 버튼 클릭 대기
        btnCancel.setOnClickListener(this);

        // ID가 button_send(main.xml)인 버튼 초기화
        Button btnSend = (Button) findViewById(R.id.button_send);
        // '전송' 버튼 클릭 대기
        btnSend.setOnClickListener(this);

    }
    public void onClick(View v) {
        Intent it = new Intent(this, MainActivity.class);
        switch (v.getId()) {
            case R.id.button_send:
                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                EditText etMessage = (EditText) findViewById(R.id.message);
                smsText = etMessage.getText().toString();
                CheckBox chkWhere = (CheckBox) findViewById(R.id.where);
                if(smsText.equals("")){
                    alert.setTitle("오류");
                    alert.setMessage("메세지를 입력하세요.");
                    alert.setPositiveButton("확인", null);
                    alert.show();
                }else {
                    if (!(PermissionUtil.checkPermissions(this, Manifest.permission.SEND_SMS)
                            && PermissionUtil.checkPermissions(this, Manifest.permission.RECEIVE_SMS)
                            && PermissionUtil.checkPermissions(this, Manifest.permission.READ_PHONE_STATE)
                            && PermissionUtil.checkPermissions(this, Manifest.permission.INTERNET)
                            && PermissionUtil.checkPermissions(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                            && PermissionUtil.checkPermissions(this, Manifest.permission.ACCESS_FINE_LOCATION))) {
                        PermissionUtil.requestExternalPermissions(this);
                    }else {
                        if (chkWhere.isChecked()) {
                            try {
                                // Update location to get.
                                LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                                lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 100, 1, locationListener);
                                lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 1, locationListener);
                            } catch (SecurityException ex) {
                            }
                            if (strLocation.equals("")) {
                                Toast.makeText(this, "위치 정보를 불러오고 있습니다.", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            smsSend(number, "[일반신고] " + smsText);
                        }
                        startActivity(it);
                        finish();
                    }
                }
                break;
            case R.id.button_cancel:
                startActivity(it);
                finish();
                break;
        }

    }

    public void smsSend(String smsNumber,String smsText) {
        String SENT = "SMS_SENT";
        String DELIVERED = "SMS_DELIVERED";

        PendingIntent sentPI = PendingIntent.getBroadcast(this, 0, new Intent(SENT), 0);
        PendingIntent deliveredPI = PendingIntent.getBroadcast(this, 0, new Intent(DELIVERED),0);

        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch(getResultCode()){
                    case Activity.RESULT_OK:
                        Toast.makeText(getBaseContext(), "메시지 전송 되었습니다.", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter(SENT));

        SmsManager sms = SmsManager.getDefault();

        if(smsText.length()<=70) {
            sms.sendTextMessage(smsNumber, null, smsText, sentPI, deliveredPI);
        }else{
            String tmpText;
            for(int stNum=0, finNum=70; stNum<smsText.length(); stNum+=70){
                tmpText=smsText.substring(stNum,finNum);
                sms.sendTextMessage(smsNumber, null, tmpText, sentPI, deliveredPI);
                if(finNum+70<=smsText.length())
                    finNum+=70;
                else finNum=smsText.length();
            }
        }// 잘라서 보낸 거..

        Toast.makeText(getBaseContext(), "메시지 전송 내용\n"+smsText, Toast.LENGTH_SHORT).show();

    }

    private LocationListener locationListener = new LocationListener() {

        @Override
        public void onLocationChanged(Location location) {
            LocationManager lm = (LocationManager)getSystemService(Context. LOCATION_SERVICE);
            // Get the last location, and update UI.
            Location lastLotation = location;

            strLocation= lastLotation.getLatitude()+", "+lastLotation.getLongitude();

            if(!smsText.equals("")) {
                smsText = "[일반신고] 위치 : " + strLocation +"\n" + smsText;
                smsSend(number,smsText);
            }else {
                Toast.makeText(getBaseContext(), "전송 실패", Toast.LENGTH_SHORT).show();
            }

            // Stop the update to prevent changing the location.
            lm.removeUpdates( this );
        }

        @Override
        public void onProviderDisabled(String provider) {
            Log.d("test", "onProviderDisabled, provider:" + provider);
        }

        @Override
        public void onProviderEnabled(String provider) {
            Log.d("test", "onProviderEnabled, provider:" + provider);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            Log.d("test", "onStatusChanged, provider:" + provider + ", status:" + status + " ,Bundle:" + extras);
        }


    };
}
