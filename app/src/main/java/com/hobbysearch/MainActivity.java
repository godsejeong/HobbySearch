package com.hobbysearch;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText interEt;
    Button interSave,interHelp;
    ImageButton interVoice;
    Intent intent;
    SpeechRecognizer recognizer;
    Context context = this;
    String interests;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //xml에 있는 id 연동
        interEt = findViewById(R.id.interEt);
        interSave = findViewById(R.id.interSave);
        interHelp = findViewById(R.id.interHelp);
        interVoice = findViewById(R.id.interVoice);

        interSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                interests = interEt.getText().toString();
                //클릭시 텍스트를 가져와서 텍스트의 상태체크
                if(interests.isEmpty()){
                    Toast.makeText(context,"관심사를 입력해 주세요",Toast.LENGTH_SHORT).show();
                }else{
                    intersave(interests);
                    Toast.makeText(context,"관심사가 저장되었습니다.",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context,InterMenuActivity.class);
                    intent.putExtra("interests",interests);
                    startActivity(intent);
                    finish();
                }
            }
        });

        //도움말을 누를때 사용법이 Toast로 나옴
       interHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"바둑을 배우고 싶은 경우 '바둑배우기'라고 입력하시거나 음성으로 말해주시고 저장을 눌러주세요",Toast.LENGTH_SHORT).show();
            }
        });

        interVoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                //Google STT에 맞는 Intent정보 가져옴
                intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getPackageName());
                //패키지 이름 설정
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ko-KR");
                //음성 언저 기본설정 - 한글로 했음
                recognizer = SpeechRecognizer.createSpeechRecognizer(context);
                //현재 엑티비티의 정보를 넣어줌
                recognizer.setRecognitionListener(listener);
                //음성인식 리스너로 연결
            }
        });
    }

    //음성인식 리스너
    private RecognitionListener listener = new RecognitionListener() {
        //음성 인식 준비가 되었으면
        @Override
        public void onReadyForSpeech(Bundle bundle) {

        }
        //입력이 시작되면
        @Override
        public void onBeginningOfSpeech() {

        }
        //입력 소리 변경 시
        @Override
        public void onRmsChanged(float v) {

        }
        //더 많은 소리를 받을 때
        @Override
        public void onBufferReceived(byte[] bytes) {

        }
        //음성 입력이 끝났으면
        @Override
        public void onEndOfSpeech() {

        }
        //에러가 발생하면
        @Override
        public void onError(int i) {

        }
        //음성 인식 결과 받음
        @Override
        public void onResults(Bundle results) {
            Log.e("음성인식","성공");
            String key = SpeechRecognizer.RESULTS_RECOGNITION;
            //성공시 키값을 받아옴
            ArrayList<String> result = results.getStringArrayList(key);
            //가져온 키값을 통해 음성인식된 텍스트 파일을 ArrayList에서 가져옴
            String[] rs = new String[result.size()];
            result.toArray(rs);
            //ArrayList를 String으로 변환
            interEt.setText("" + rs[0]);
            //변환된 String을 출력

        }
        //인식 결과의 일부가 유효할 때
        @Override
        public void onPartialResults(Bundle bundle) {

        }
        //미래의 이벤트를 추가하기 위해 미리 예약되어진 함수
        @Override
        public void onEvent(int i, Bundle bundle) {

        }
    };
    //sharedpreferences에 관심사를 저장
    public void intersave(String save){
        SharedPreferences pref = getSharedPreferences("pref",MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("inter",save);
        editor.commit();

    }
}
