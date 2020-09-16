package kr.co.song1126.ex86_firebasecloudmessagepush_test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void clickBtn(View view) {
        //앱을 FCM서버에 등록하는 과정
        //앱을 FCM서버에 등록하면 앱을 식별할 수 있는 고유 토큰값(문자열)을 준다
        //이 토큰값(InstanceID)을 통해서 앱들(디바이스들)을 구별하여 메세지가 전달되는 것이다. : onCreate에 코드를 작성한다

        FirebaseInstanceId firebaseInstanceId=FirebaseInstanceId.getInstance();
        Task<InstanceIdResult> task =firebaseInstanceId.getInstanceId();
        task.addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                String token=task.getResult().getToken();

                //token값 출력
                Toast.makeText(MainActivity.this, token, Toast.LENGTH_SHORT).show();

                //logcat 창에 토큰값 출력
                Log.d("token",token);
                //cCPCb0NQTSea-yOgTY82Xg:APA91bH8GYz0mok-LiPu4fCO0--kPBx1mfCoDG3VWUv48si3aTKJ7mz0lQgeD9CpBan1Qybi2BIJVw2evKaD8mBmLHtayIt51q6VuIDMgRytrL09MWCtQWDcJ_p2xQWmeJNitz9QPPSV

                //실무에서는 이 token값을 본인의 웹서버(dothome서버)에
                //전송하여 웹DB에 token값 저장하도록 해야한다.
                //개별 식별 ID에 해당하는 token값을 발급해줘야한다.
            }
        });

    }
}
