package com.example.dianp.bujatest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
    public void test(View v){
        int id = v.getId();
        int number=1;
        int[] arrAnswer=new int[18];

        String tag;
        if(id==R.id.worker) tag="_work";
        else tag="_std";
        Intent it = new Intent(this,Question.class);
        it.putExtra("it_tag",tag);
        it.putExtra("it_num",number);
        it.putExtra("it_answer",arrAnswer);
        startActivity(it);
        overridePendingTransition(0, 0);
        finish();

        //Toast.makeText(this, "question"+number+tag, Toast.LENGTH_SHORT).show();
    }
}
