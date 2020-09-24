package cn.codeyourlife.playautio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;
import java.io.IOException;

public class VideoPlayActivity extends AppCompatActivity implements View.OnClickListener{

    private VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_play);

        videoView = (VideoView)findViewById(R.id.video_view);
        // 设置对象为监听器，然后在一个函数中switch-case处理所有按钮点击
        Button btnPlay = (Button) findViewById(R.id.btn_video_play);
        Button btnPause = (Button) findViewById(R.id.btn_video_pause);
        Button btnStop = (Button) findViewById(R.id.btn_video_replay);
        btnPlay.setOnClickListener(this);
        btnPause.setOnClickListener(this);
        btnStop.setOnClickListener(this);

        // 权限检查
        if (ContextCompat.checkSelfPermission(VideoPlayActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(VideoPlayActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }else
        {
            initVideoPath();
        }
    }

    public void initVideoPath(){
        File file = new File(Environment.getExternalStorageDirectory(), "DCIM/Camera/VID_20200924_204307.mp4");
        videoView.setVideoPath(file.getPath());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if (grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    initVideoPath();
                }else{
                    Toast.makeText(this, "拒绝权限将无法使用程序", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_video_play:
                if (!videoView.isPlaying()){
                    videoView.start();
                }
                break;
            case R.id.btn_video_pause:
                if (videoView.isPlaying()){
                    videoView.pause();
                }
                break;
            case R.id.btn_video_replay:
                if (videoView.isPlaying()){
                    videoView.resume();
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (videoView!=null){
            videoView.suspend();
        }
    }
}