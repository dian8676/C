package com.example.dianp.bujatest;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created by Sohyeon on 2017-05-24.
 */

public class Result extends AppCompatActivity {


    String tag;
    int number;
    int[] arrAnswer=new int[18];

    Resources res;
    Animation growAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_page);

        Intent it= getIntent();
        tag = it.getStringExtra("it_tag");
        number=it.getIntExtra("it_num",0);
        arrAnswer= it.getIntArrayExtra("it_answer").clone();

        float indexSum=0;
        for(int i=0;i<16;i++){
            indexSum+=arrAnswer[i];
        }
        float goalSum=arrAnswer[16]+arrAnswer[17];

        //백분율로 표시
        float resultIndex = indexSum / ( 5 * (number - 3) ) * 100;
        float resultGoal = goalSum / (10 * 2 ) * 100;

        TextView tvIndex=(TextView)findViewById(R.id.result_index);
        TextView tvGoal=(TextView)findViewById(R.id.result_goal);

        String result=(int)resultIndex+" 점";
        tvIndex.setText(result);
        result=(int)resultGoal+" 점";
        tvGoal.setText(result);

        res = getResources();

        growAnim = AnimationUtils.loadAnimation(this, R.anim.grow);

        LinearLayout indexLayout = (LinearLayout)findViewById(R.id.indexLayout);

        int value = (int)resultIndex;

        LinearLayout itemLayout = new LinearLayout(this);
        itemLayout.setOrientation(LinearLayout.VERTICAL);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        ProgressBar proBar = new ProgressBar(this, null,
                android.R.attr.progressBarStyleHorizontal);
        proBar.setIndeterminate(false);
        proBar.setMax(100);
        proBar.setProgress(100);
        proBar.setProgressDrawable(getResources().getDrawable(R.drawable.progressbar));

        proBar.setAnimation(growAnim);

        params.height = 70;
        params.width = value * 10;
        params.gravity = Gravity.CENTER_VERTICAL;
        itemLayout.addView(proBar, params);

        indexLayout.addView(itemLayout, params2);

        LinearLayout goalLayout = (LinearLayout)findViewById(R.id.goalLayout);

        value = (int)resultGoal;

        itemLayout = new LinearLayout(this);
        itemLayout.setOrientation(LinearLayout.VERTICAL);

        params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        params2 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        proBar = new ProgressBar(this, null,
                android.R.attr.progressBarStyleHorizontal);
        proBar.setIndeterminate(false);
        proBar.setMax(100);
        proBar.setProgress(100);
        proBar.setProgressDrawable(getResources().getDrawable(R.drawable.progressbar));

        proBar.setAnimation(growAnim);

        params.height = 70;
        params.width = value * 10;
        params.gravity = Gravity.CENTER_VERTICAL;
        itemLayout.addView(proBar, params);

        goalLayout.addView(itemLayout, params2);

    }

    public void movePage(View v) {
        Intent intent=new Intent(this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        if(hasFocus) {
            growAnim.start();
        } else {
            growAnim.reset();
        }
    }
}
