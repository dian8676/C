package com.example.dianp.bujatest;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Sohyeon on 2017-05-09.
 */

public class Question extends AppCompatActivity {

    String tag;
    int number;
    int[] arrAnswer=new int[18];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question);

        Intent it= getIntent();
        tag = it.getStringExtra("it_tag");
        number= it.getIntExtra("it_num",0);
        arrAnswer= it.getIntArrayExtra("it_answer").clone();

        TextView textView=(TextView)findViewById(R.id.question);
        RadioGroup rg = (RadioGroup) findViewById(R.id.answerGroup);

        if(!(arrAnswer[number-1]==0)) {
            if (number == 5) {
                rg.check(rg.getChildAt(arrAnswer[number - 1]-1).getId());
            } else {
                rg.check(rg.getChildAt(5 - arrAnswer[number - 1]).getId());
            }
        }

        Resources res=getResources();
        int id_question;


        if(!(number==6||number==8||number>=14)){
            id_question=res.getIdentifier("question"+number, "string", getPackageName());
        }else {
            id_question = res.getIdentifier("question"+number+tag, "string", getPackageName());
        }
        String question=res.getString(id_question);
        textView.setText(question);


    }

    public void movePage(View v){

        RadioGroup rg = (RadioGroup) findViewById(R.id.answerGroup);
        int id=rg.getCheckedRadioButtonId();

        if(number==5){
            switch (id){
                case R.id.answer1 :
                    arrAnswer[number-1]=1;
                    break;
                case R.id.answer2 :
                    arrAnswer[number-1]=2;
                    break;
                case R.id.answer3 :
                    arrAnswer[number-1]=3;
                    break;
                case R.id.answer4 :
                    arrAnswer[number-1]=4;
                    break;
                case R.id.answer5 :
                    arrAnswer[number-1]=5;
                    break;
            }
        }else {
            switch (id){
                case R.id.answer1 :
                    arrAnswer[number-1]=5;
                    break;
                case R.id.answer2 :
                    arrAnswer[number-1]=4;
                    break;
                case R.id.answer3 :
                    arrAnswer[number-1]=3;
                    break;
                case R.id.answer4 :
                    arrAnswer[number-1]=2;
                    break;
                case R.id.answer5 :
                    arrAnswer[number-1]=1;
                    break;
            }
        }

        Intent intent;

        if(v.getId()==R.id.previous){
            if((number-1)!=0) {
                intent = new Intent(this, Question.class);
                intent.putExtra("it_tag", tag);
                intent.putExtra("it_num",number-1);
                intent.putExtra("it_answer",arrAnswer);
            }else{
                intent = new Intent(this,MainActivity.class);
            }
            startActivity(intent);
            overridePendingTransition(0, 0);
            finish();
        }else if(v.getId()==R.id.next){
            if(arrAnswer[number-1]!=0) {
                if ((number + 1) != 17) {
                    intent = new Intent(this, Question.class);
                } else {
                    intent = new Intent(this, QuestionAboutGoal.class);
                }
                intent.putExtra("it_tag", tag);
                intent.putExtra("it_num", number + 1);
                intent.putExtra("it_answer",arrAnswer);
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
