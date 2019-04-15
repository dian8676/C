package com.example.sohyeon.dowazo;

import android.app.Activity;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.Toast;

import static com.example.sohyeon.dowazo.ButtonMainActivity.ambulance;
import static com.example.sohyeon.dowazo.ButtonMainActivity.checked;
import static com.example.sohyeon.dowazo.ButtonMainActivity.numberProtector;


public class MyService extends Service {
    //Using the Accelometer & Gyroscoper
    private SensorManager mSensorManager = null;

    //Using the Accelometer
    private SensorEventListener mAccLis;
    private Sensor mAccelometerSensor = null;
    private double X,Y,Z;
    boolean start;
    boolean clicked;





    public MyService() {
    }

    private IBinder mIBinder = new MyBinder();

    class MyBinder extends Binder {
        MyService getService(){
            return MyService.this;
        }
    }
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {

        //Toast.makeText(this, numberProtector + checked[0] + checked[1] + checked[2], Toast.LENGTH_SHORT).show();
        //Log.e("LOG", "onCreate()");



        //센서 사용을 위한 변수 초기화
        start=true;

        //Using the Gyroscope & Accelometer
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        //Using the Accelometer
        mAccelometerSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mAccLis = new MyService.AccelometerListener();




        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //
        // Log.e("LOG", "onStartCommand()");
        new Thread(task).start();

        return super.onStartCommand(intent, flags, startId);
    }

    Runnable task = new Runnable(){

        public void run(){


            try {

                Toast.makeText(MyService.this, "서비스시작", Toast.LENGTH_SHORT).show();
                //   mSensorManager.registerListener(mAccLis, mAccelometerSensor, SensorManager.SENSOR_DELAY_UI);

            } catch (Exception e) {

                // TODO Auto-generated catch block
                mSensorManager.registerListener(mAccLis, mAccelometerSensor, SensorManager.SENSOR_DELAY_UI);
                e.printStackTrace();

            }





        }

    };


    @Override
    public  void onDestroy() {
        Log.e("LOG", "onDestroy()");
        mSensorManager.unregisterListener(mAccLis);

        super.onDestroy();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.e("LOG", "onUnbind()");
        return super.onUnbind(intent);
    }





    private class AccelometerListener implements SensorEventListener {

        @Override
        public void onSensorChanged(SensorEvent event) {
            if( !ButtonMainActivity.checked[0] ) stopSelf();
            if( ButtonMainActivity.serviceChange ){ButtonMainActivity.serviceChange=false; stopSelf();}

            if(start){   X=event.values[0]; Y=event.values[1]; Z = event.values[2]; start=false;}

            double accX = event.values[0];
            double accY = event.values[1];
            double accZ = event.values[2];
            boolean drop =false;


            double angleXZ = Math.atan2(accX,  accZ) * 180/Math.PI;
            double angleYZ = Math.atan2(accY,  accZ) * 180/Math.PI;

            // 55 아래의 숫자를 조정함으로써 어느정도 강도조정가능 작을수록 민감

            if(Math.abs(accX-X) > 15){
                drop=true;}
            else if(Math.abs(accY-Y) > 15){
                drop=true;}
            else if(Math.abs(accZ-Z) > 15){
                drop=true;}


            if(drop){


                if(ButtonMainActivity.checked[2]) {
                    try {
                        // Update location to get.
                        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                        lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 100, 1, locationListener);
                        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 1, locationListener);
                    } catch (SecurityException ex) {
                        Log.d("Location", "위치정보를 받아올 수 없음.");
                    }
                }

                //떨어졌을경우 popUp엑티비티 시작
                //popUp엑티비에서 알림음생성 확인 버튼 누를시 소리 종료
                Intent intent = new Intent(MyService.this, popUpActivity.class);

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                startActivity(intent);


                stopSelf();

            }





            X=accX; Y=accY; Z=accZ;





        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }

        String strLocation;



        public LocationListener locationListener = new LocationListener() {

            @Override
            public void onLocationChanged(Location location) {
                LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                // Get the last location, and update UI.
                Location lastLotation = location;


                strLocation = lastLotation.getLatitude() + ", " + lastLotation.getLongitude();

                if (!numberProtector.equals("")) {
                    String text = "핸드폰떨어짐이 감지되었습니다.";
                    text = "현재 위치 : " + strLocation + "\n" + text;
                    smsSend(numberProtector, text);

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

        public void smsSend(String smsNumber, String smsText) {
            String SENT = "SMS_SENT";
            String DELIVERED = "SMS_DELIVERED";

            PendingIntent sentPI = PendingIntent.getBroadcast(MyService.this, 0, new Intent(SENT), 0);
            PendingIntent deliveredPI = PendingIntent.getBroadcast(MyService.this, 0, new Intent(DELIVERED), 0);

            registerReceiver(new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    switch (getResultCode()) {
                        case Activity.RESULT_OK:
                            Toast.makeText(getBaseContext(), "보호자에게 메시지 전송 되었습니다.", Toast.LENGTH_SHORT).show();
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
    }
}
