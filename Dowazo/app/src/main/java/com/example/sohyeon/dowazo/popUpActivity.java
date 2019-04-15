package com.example.sohyeon.dowazo;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

public class popUpActivity extends AppCompatActivity {


    //사운드 사용을 위한
    private SoundPool sound_pool;
    private int sound_beep=1;
    private int streamID;

    @Override
    protected void onDestroy() {
        sound_pool.stop(streamID);

        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_pop_up);

        // 사운드풀 변수 초기화와 음원 로드
        sound_pool= new SoundPool( 5, AudioManager.STREAM_ALARM, 0 );
        sound_beep = sound_pool.load( popUpActivity.this.getBaseContext(), R.raw.beep, 1 );


        //제목부분제거
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        //

        //    requestWindowFeature(Window.FEATURE_NO_TITLE);


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND,

                WindowManager.LayoutParams.FLAG_BLUR_BEHIND);



        getWindow().addFlags(

                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED|

                        WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD|

                        WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON|

                        WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON

        );

        Toast.makeText(this, "알림" +sound_beep, Toast.LENGTH_SHORT).show();
      //  sound_pool.play( sound_beep, 1f, 1f, 0, 2, 1f );

        if( ButtonMainActivity.checked[1] ){streamID = sound_pool.play( sound_beep, 1f, 1f, 0, -1, 1f );}





        AlertDialog.Builder builder = new AlertDialog.Builder(popUpActivity.this);

        builder.setTitle(" ");

        builder.setMessage("떨어졌습니다!");

        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);



        builder.setCancelable(false);

        builder.setOnKeyListener(new DialogInterface.OnKeyListener() {

            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_APP_SWITCH && event.getRepeatCount() == 0) {
                    return true;
                }
                if (keyCode == KeyEvent.KEYCODE_HOME && event.getRepeatCount() == 0) {
                    return true;
                }
                return false;
            }

        });

        builder.setNegativeButton("확인", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                sound_pool.stop(streamID);
                finish();
                dialog.dismiss();
                startService(new Intent(popUpActivity.this, MyService.class));

            }

        });

      // builder.setCanceledOnTouchOutside(true);
       // builder.create();


        builder.show();



    }
}
