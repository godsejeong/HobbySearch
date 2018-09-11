package com.hobbysearch;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.Timer;
import java.util.TimerTask;

public class InterMenuActivity extends AppCompatActivity {
    Button btn1, btn2, btn3;
    Context context = this;
    TimerTask tt = timmer();
    int s = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inter_menu);
        getSupportActionBar().setTitle("메인메뉴");
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        tt = timmer();
        new Timer().schedule(tt, 0, 1000);


        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //버튼 클릭시 유튜브로 관심사내용이 검색됨
                Uri uri = Uri.parse("https://www.youtube.com/results?search_query=" + interLoad());

                Intent it = new Intent(Intent.ACTION_VIEW, uri);

                startActivity(it);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //버튼 클릭시 네이버로 관심사내용이 검색됨
                Uri uri = Uri.parse("https://search.naver.com/search.naver?query=" + interLoad());

                Intent it = new Intent(Intent.ACTION_VIEW, uri);

                startActivity(it);
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //관심사 설정으로 이동
                Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);
                finish();
                interRemove();
            }
        });
    }

    void Notipcation() {

//        // PendingIntent를 등록 하고, noti를 클릭시에 어떤 클래스를 호출 할 것인지 등록.
        PendingIntent intent = PendingIntent.getActivity(
                InterMenuActivity.this,
                0,
                new Intent(InterMenuActivity.this, NotipcationActivity.class),
                0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        builder
                .setSmallIcon(R.drawable.youtubeicon)
                .setContentTitle(interLoad())
                .setContentText("새로운 동영상이 업데이트 되었습니다.")
                .setContentIntent(intent)
                .setAutoCancel(true)
                .setWhen(System.currentTimeMillis())
                .setDefaults(Notification.DEFAULT_ALL)
                .build();

        NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        nm.notify(1234, builder.build());
    }

    //3분후 알람이뜸
    public TimerTask timmer() {

        TimerTask tt = new TimerTask() {
            @Override
            public void run() {
                    Log.e("s", String.valueOf(s));
                    s++;

                    if (s >= 180) {
                        Notipcation();
                        s = 0;
                        this.cancel();
                    }
                }
            };
        return tt;
    }

    //관심사 설정내용을 SharedPreferences에서 가져온다
    public String interLoad() {
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        return pref.getString("inter", "");
    }
    //관심사 설정내용을 SharedPreferences에서 지운다
    public void interRemove() {
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.clear();
        editor.commit();
    }

}
