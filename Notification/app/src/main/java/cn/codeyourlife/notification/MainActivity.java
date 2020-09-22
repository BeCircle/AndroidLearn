package cn.codeyourlife.notification;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnSendNotify = (Button) findViewById(R.id.btn_send_notify);
        btnSendNotify.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_send_notify){
                String id = "my_channel_01";
                String name="我是渠道名字";
                NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                Notification notification;

            Intent intent = new Intent(this, NotificationActivity.class);
            PendingIntent pi = PendingIntent.getActivity(this, 0, intent, 0);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    NotificationChannel mChannel = new NotificationChannel(id, name, NotificationManager.IMPORTANCE_LOW);
                    notificationManager.createNotificationChannel(mChannel);
                    notification = new Notification.Builder(this, id)
                            .setContentTitle("5 new messages")
                            .setStyle(new Notification.BigPictureStyle().bigPicture(BitmapFactory.decodeResource(getResources(), R.mipmap.constant_data_type)))
                            .setContentIntent(pi)
                            .setAutoCancel(true)
                            .setSmallIcon(R.mipmap.ic_launcher).build();
                } else {
                    NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, id)
                            .setContentTitle("5 new messages")
                            .setContentText("hahaha")
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setContentIntent(pi)
                            .setAutoCancel(true)
                            .setOngoing(true);

                    notification = notificationBuilder.build();
                }
                notificationManager.notify(111123, notification);
                Toast.makeText(MainActivity.this, "发送成功", Toast.LENGTH_SHORT).show();
        }
    }
}