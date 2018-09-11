package com.hobbysearch;

import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class NotipcationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notipcation);
        finish();

        Uri uri = Uri.parse("https://www.youtube.com/results?search_query=" + interLoad());
        Intent it  = new Intent(Intent.ACTION_VIEW,uri);
        startActivity(it);

        // notification 매니저 생성
        NotificationManager nm =
                (NotificationManager)getSystemService(NOTIFICATION_SERVICE);

        // 등록된 notification 을 제거 한다.
        nm.cancel(1234);


    }

    public String interLoad(){
        SharedPreferences pref = getSharedPreferences("pref",MODE_PRIVATE);
        return pref.getString("inter","");
    }
}
