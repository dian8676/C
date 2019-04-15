package com.example.dianp.fintest;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Vibrator;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends ActionBarActivity implements SensorEventListener {

    TextView tv_count;
    TextView tv_event;
    TextView tv_message;

    SensorManager sm;
    Sensor sensor_accelerometer;
    Vibrator vibrator;

    int count = 0;
    int dir_UP = 0;
    int dir_DOWN = 0;

    double gravity = 9.8;
    double acceleration = 0;

    String goal = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_message = (TextView)findViewById(R.id.goalMessage);
        tv_event = (TextView)findViewById(R.id.goalEvent);
        tv_count = (TextView)findViewById(R.id.count);
        tv_count.setText(""+count);
        tv_event.setText("");
        sm = (SensorManager)getSystemService(SENSOR_SERVICE);
        sensor_accelerometer = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        vibrator = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);

    }

    public void initialize (View v){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();
    }

    public void insertGoal (View v){
        EditText editText = (EditText)findViewById(R.id.goal);
        goal = editText.getText().toString();
        if(!goal.equals("")) {
            count = 0;
            tv_count.setText("" + count);

            tv_message.setText("목표는 " + goal + "개 입니다 힘내세요!");
        }else Toast.makeText(this, "목표 개수를 입력하세요.", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected  void onResume(){
        super.onResume();
        sm.registerListener(this, sensor_accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause(){
        super.onPause();
        sm.unregisterListener(this);
    }

    @Override
    public  void onAccuracyChanged(Sensor sensor, int accuracy){
    }

    @Override
    public void onSensorChanged(SensorEvent event){
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            acceleration = Math.sqrt(x*x + y*y + z*z);

            if (acceleration - gravity > 2){
                dir_UP = 1;
            }
            if (dir_UP == 1 && gravity - acceleration > 2){
                dir_DOWN = 1;
            }

            if (dir_DOWN == 1){
                count++;
                tv_count.setText(""+count);

                dir_UP = 0;
                dir_DOWN = 0;

                if(!((count==0) && (goal.equals(""))) && goal.equals(count+"")){
                    vibrator.vibrate(1000);
                    tv_event.setText("목표 "+goal+"개를 달성했습니다. 축하합니다.");
                    tv_message.setText(" ");
                }
            }
        }
    }
}
