package kr.co.song1126.ex86_firebasecloudmessagepush_test;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class MyFCMReceiveService extends FirebaseMessagingService {

    //push 서버에서 보낸 메세지가 수신되었을 때 자동으로 발동하는 메소드
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        // 서비스 안에서는 알림(notification)만 적용할 수 있다.[ Toast불가능 ]
        // 리시브 확인용으로 Logcat창에 출력
        Log.d("SAM","onMessageReceived!!");

        // **** 4대 컴포넌트는 Manifest에 등록해야한다.

        //이 메소드에 파라미터로 전달된 RemoteMessage객체 : 받은 원격 메세지이다.

        //메세지를 보낸 기기명 [ Firebase 서버에서 자동 지정 이름 ]
        String fromWho=remoteMessage.getFrom();

        //알림에 대한 데이터들
        String notiTitle="title";       //제목이 오지 않았을때 기본값
        String notiBody="body text";    //제목이 오지 않았을때 기본값

        if (remoteMessage.getNotification() != null) {
            notiTitle=remoteMessage.getNotification().getTitle();
            notiBody=remoteMessage.getNotification().getBody();

            Uri notiImgUri=remoteMessage.getNotification().getImageUrl();//유료서비스만 사용가능
            //Glide 사용가능
        }

        //Firebase 푸시 메세지에 추가로 데이터가 있을 경우에[ 키 : 벨류 ] 형태로 송신된다.
        Map<String, String> data=remoteMessage.getData();

        String name="";
        String msg="";
        if (data != null) {
            name=data.get("name");
            msg=data.get("msg");
        }



        //잘받았는지 확인
        Log.i("SAM",fromWho+" : "+notiTitle+", "+notiBody+" : "+name+", "+msg );

        //받은 값들을 알림(Notification)객체를 만들어 공지하기

        NotificationManager notificationManager=(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder builder=null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel=new NotificationChannel("ch01","channel01",NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);

            builder=new NotificationCompat.Builder(this, "ch01");
        }else{
            builder=new NotificationCompat.Builder(this,null);
        }

        builder.setSmallIcon(R.drawable.ic_stat_name);
        builder.setContentTitle(notiTitle);
        builder.setContentText(notiBody);

        //==========
        //알림을 선택했을때 실행될 Activity를 실행하는 Intent생성
        Intent intent=new Intent(this, MessageActivity.class);
        intent.putExtra("name",name);
        intent.putExtra("msg",msg);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);     //
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);    //

        //보류중인 인텐트로 변환
        PendingIntent pendingIntent=PendingIntent.getActivity(this, 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        builder.setAutoCancel(true);


        Notification notification=builder.build();
        notificationManager.notify(111, notification);



    }
}
