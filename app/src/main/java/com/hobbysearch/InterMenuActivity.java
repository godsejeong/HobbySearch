package com.hobbysearch;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class InterMenuActivity extends AppCompatActivity {
    Button btn1,btn2,btn3;
    Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inter_menu);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //버튼 클릭시 유튜브로 관심사내용이 검색됨
                Uri uri = Uri.parse("https://www.youtube.com/results?search_query=" + interLoad());

                Intent it  = new Intent(Intent.ACTION_VIEW,uri);

                startActivity(it);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //버튼 클릭시 네이버로 관심사내용이 검색됨
                Uri uri = Uri.parse("https://search.naver.com/search.naver?query=" + interLoad());

                Intent it  = new Intent(Intent.ACTION_VIEW,uri);

                startActivity(it);
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //관심사 설정으로 이동
                Intent intent = new Intent(context,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    //관심사 설정내용을 SharedPreferences에서 가져온다
    public String interLoad(){
        SharedPreferences pref = getSharedPreferences("pref",MODE_PRIVATE);
        return pref.getString("inter","");
    }
}
