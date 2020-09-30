package cn.codeyourlife.servicebestpractice;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import java.io.File;

public class DownloadService extends Service {
    private DownloadTask downloadTask;
    private String downloadUrl;

    private final String DOWNLOAD_FAILED = "下载失败";
    private final String DOWNLOAD_SUCCESS = "下载失败";
    private final String DOWNLOAD_ING = "下载中...";
    private final String PAUSED = "已暂停";
    private final String CANCELED = "已取消";

    // 匿名类
    private DownloadListener listener = new DownloadListener() {
        @Override
        public void onProgress(int progress) {
            getNotificationManager().notify(1, getNotification(DOWNLOAD_ING, progress));
        }

        @Override
        public void onSuccess() {
            downloadTask = null;
            // 关闭前台服务通知，并创建下载成功通知
            stopForeground(true);
            getNotificationManager().notify(1, getNotification(DOWNLOAD_SUCCESS, -1));
            Toast.makeText(DownloadService.this, DOWNLOAD_SUCCESS, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onFailed() {
            stopForeground(true);
            getNotificationManager().notify(1, getNotification(DOWNLOAD_FAILED, -1));
            Toast.makeText(DownloadService.this, DOWNLOAD_FAILED, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onPaused() {
            downloadTask = null;
            Toast.makeText(DownloadService.this, PAUSED, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCanceled() {
            downloadTask = null;
            stopForeground(true);
            Toast.makeText(DownloadService.this, CANCELED, Toast.LENGTH_SHORT).show();
        }
    };


    private NotificationManager getNotificationManager() {
        return (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    private Notification getNotification(String title, int progress){
        String id = "my_channel_01";
        String name = "我是渠道名字";
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification notification;

        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0, intent, 0);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(id, name, NotificationManager.IMPORTANCE_LOW);
            notificationManager.createNotificationChannel(mChannel);
            Notification.Builder notificationBuilder= new Notification.Builder(this, id)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                    .setContentIntent(pi)
                    .setContentTitle(title);
            if (progress>0){
                notificationBuilder.setContentText(progress+"%")
                        .setProgress(100, progress, false);
            }
            notification = notificationBuilder.build();
        } else {
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, id)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                    .setContentIntent(pi)
                    .setContentTitle(title);
            if (progress>0){
                notificationBuilder.setContentText(progress+"%")
                        .setProgress(100, progress, false);
            }
            notification = notificationBuilder.build();
        }
        return notification;
    }
    private DownloadBinder downloadBinder = new DownloadBinder();

    public DownloadService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return this.downloadBinder;
    }

    class DownloadBinder extends Binder {
        public void startDownload(String url){
            if (downloadTask == null) {
                downloadUrl = url;
                downloadTask = new DownloadTask(listener);
                downloadTask.execute(downloadUrl);
                startForeground(1, getNotification(DOWNLOAD_ING, 0));
                Toast.makeText(DownloadService.this, DOWNLOAD_ING, Toast.LENGTH_SHORT).show();
            }
        }
        public void pauseDownload(){
            if (downloadTask != null){
                downloadTask.pauseDownload();
            }
        }
        public void cancelDownload() {
            if (downloadTask != null){
                downloadTask.cancelDownload();
            }
            if (downloadUrl!=null){
                String fileName = downloadUrl.substring(downloadUrl.lastIndexOf("/"));
                String directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
                File file = new File(directory+fileName);
                if (file.exists()){
                    file.delete();
                }
                getNotificationManager().cancel(1);
                stopForeground(true);
                Toast.makeText(DownloadService.this, CANCELED, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
