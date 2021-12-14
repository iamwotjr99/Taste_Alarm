package org.techtown.tastealarm;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

public class TasteAlarmService extends Service {

    NotificationManager notificationManager;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Intent alarmIntent = new Intent(getApplicationContext(), LoginActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                alarmIntent, PendingIntent.FLAG_CANCEL_CURRENT);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String CHANNEL_ID = "taste_alarm_service";
            NotificationChannel channel = new NotificationChannel("CHANNEL_ID", "alarm",
                    NotificationManager.IMPORTANCE_DEFAULT);

            notificationManager = ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE));
            notificationManager.createNotificationChannel(channel);
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "CHANNEL_ID")
                .setSmallIcon(R.drawable.chicken_white)
                .setContentTitle("맛알람")
                .setContentText("주변에 맛집이 있어요! 터치하면 확인 하실 수 있습니다!")
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        notificationManager.notify(1, builder.build());

        startForeground(1, builder.build());
        return START_STICKY; // 포그라운드 서비스가 불가피하게 종료되었을 때, 서비스 재생성 후 onStartCommand() 다시 호출
    }
}
