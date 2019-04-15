package com.example.dianp.bujatest;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Sohyeon on 2017-05-09.
 */

public class QuestionAboutGoal extends AppCompatActivity {

    String tag;
    int number;
    int[] arrAnswer=new int[18];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_goal);

        Intent it= getIntent();
        tag = it.getStringExtra("it_tag");
        number= it.getIntExtra("it_num",0);
        arrAnswer= it.getIntArrayExtra("it_answer").clone();

        TextView textView=(TextView)findViewById(R.id.question);
        RadioGroup rg = (RadioGroup) findViewById(R.id.answerGroup);
        RadioButton answer1=(RadioButton)findViewById(R.id.answer1);
        RadioButton answer2=(RadioButton)findViewById(R.id.answer2);
        RadioButton answer3=(RadioButton)findViewById(R.id.answer3);
        RadioButton answer4=(RadioButton)findViewById(R.id.answer4);
        RadioButton answer5=(RadioButton)findViewById(R.id.answer5);
        RadioButton answer6=(RadioButton)findViewById(R.id.answer6);
        RadioButton answer7=(RadioButton)findViewById(R.id.answer7);
        RadioButton answer8=(RadioButton)findViewById(R.id.answer8);
        RadioButton answer9=(RadioButton)findViewById(R.id.answer9);
        RadioButton answer10=(RadioButton)findViewById(R.id.answer10);

        if(!(arrAnswer[number-1]==0)) {
            rg.check(rg.getChildAt(10 - arrAnswer[number - 1]).getId());
        }

        Resources res=getResources();
        int stringId;

        stringId=res.getIdentifier("question"+number+tag, "string", getPackageName());
        String myKey=res.getString(stringId);
        textView.setText(myKey);

        stringId=res.getIdentifier("answer1"+"_"+number, "string", getPackageName());
        myKey=res.getString(stringId);
        answer1.setText(myKey);
        stringId=res.getIdentifier("answer2"+"_"+number, "string", getPackageName());
        myKey=res.getString(stringId);
        answer2.setText(myKey);
        stringId=res.getIdentifier("answer3"+"_"+number, "string", getPackageName());
        myKey=res.getString(stringId);
        answer3.setText(myKey);
        stringId=res.getIdentifier("answer4"+"_"+number, "string", getPackageName());
        myKey=res.getString(stringId);
        answer4.setText(myKey);
        stringId=res.getIdentifier("answer5"+"_"+number, "string", getPackageName());
        myKey=res.getString(stringId);
        answer5.setText(myKey);
        stringId=res.getIdentifier("answer6"+"_"+number, "string", getPackageName());
        myKey=res.getString(stringId);
        answer6.setText(myKey);
        stringId=res.getIdentifier("answer7"+"_"+number, "string", getPackageName());
        myKey=res.getString(stringId);
        answer7.setText(myKey);
        stringId=res.getIdentifier("answer8"+"_"+number, "string", getPackageName());
        myKey=res.getString(stringId);
        answer8.setText(myKey);
        stringId=res.getIdentifier("answer9"+"_"+number, "string", getPackageName());
        myKey=res.getString(stringId);
        answer9.setText(myKey);
        stringId=res.getIdentifier("answer10"+"_"+number, "string", getPackageName());
        myKey=res.getString(stringId);
        answer10.setText(myKey);

    }

    public void movePage(View v){

        RadioGroup rg = (RadioGroup) findViewById(R.id.answerGroup);
        int id=rg.getCheckedRadioButtonId();
        switch (id){
            case R.id.answer1 :
                arrAnswer[number-1]=10;
                break;
            case R.id.answer2 :
                arrAnswer[number-1]=9;
                break;
            case R.id.answer3 :
                arrAnswer[number-1]=8;
                break;
            case R.id.answer4 :
                arrAnswer[number-1]=7;
                break;
            case R.id.answer5 :
                arrAnswer[number-1]=6;
                break;
            case R.id.answer6 :
                arrAnswer[number-1]=5;
                break;
            case R.id.answer7 :
                arrAnswer[number-1]=4;
                break;
            case R.id.answer8 :
                arrAnswer[number-1]=3;
                break;
            case R.id.answer9 :
                arrAnswer[number-1]=2;
                break;
            case R.id.answer10 :
                arrAnswer[number-1]=1;
                break;
        }


        Intent intent;
        if(v.getId()==R.id.previous){
            if((number-1)==16) {
                intent = new Intent(this, Question.class);
            }else{
                intent = new Intent(this, QuestionAboutGoal.class);
            }
            intent.putExtra("it_tag", tag);
            intent.putExtra("it_num", number-1);
            intent.putExtra("it_answer",arrAnswer);
            startActivity(intent);
            overridePendingTransition(0, 0);
            finish();
        }else if(v.getId()==R.id.next){
            if(arrAnswer[number-1]!=0) {
                if ((number + 1) == 19) {
                    intent = new Intent(this,Result.class);
                } else {
                    intent = new Intent(this, QuestionAboutGoal.class);
                }
                intent.putExtra("it_tag", tag);
                intent.putExtra("it_num", number + 1);
                intent.putExtra("it_answer", arrAnswer);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }else{
                Toast.makeText(this, "답을 선택하세요", Toast.LENGTH_SHORT).show();
            }
        }else{
            intent=new Intent(this, MainActivity.class);
            startActivity(intent);
            overridePendingTransition(0, 0);
            finish();
        }
    }
}
