package cn.codeyourlife.service;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "wbq812 MainActivity";

    private MyService.DownloadBinder downloadBinder;
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.d(TAG, "onServiceConnected: ");
            downloadBinder = (MyService.DownloadBinder) iBinder;
            downloadBinder.startDownload();
            downloadBinder.getProgress();
        }

        // 取消绑定不会调用，异常退出才会调用
        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.d(TAG, "onServiceDisconnected: ");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnStart = (Button) findViewById(R.id.btn_svc_start);
        Button btnStop = (Button) findViewById(R.id.btn_svc_stop);
        btnStart.setOnClickListener(this);
        btnStop.setOnClickListener(this);

        Button btnBind = (Button) findViewById(R.id.btn_svc_bind);
        Button btnUnbind = (Button) findViewById(R.id.btn_svc_unbind);
        btnBind.setOnClickListener(this);
        btnUnbind.setOnClickListener(this);

        Button btnIntentStart = (Button) findViewById(R.id.btn_int_svc_start);
        btnIntentStart.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_svc_start:
                Intent intent = new Intent(this, MyService.class);
                startService(intent);
                break;
            case R.id.btn_svc_stop:
                Intent intentStop = new Intent(this, MyService.class);
                stopService(intentStop);
                break;
            case R.id.btn_svc_bind:
                Intent intentBind = new Intent(this, MyService.class);
                bindService(intentBind, connection, BIND_AUTO_CREATE);
                break;
            case R.id.btn_svc_unbind:
                unbindService(connection);
                break;
            case R.id.btn_int_svc_start:
                Log.d(TAG, "onClick: "+Thread.currentThread().getId());
                Intent intentSvc = new Intent(this, MyIntentService.class);
                startService(intentSvc);
                break;
            default:
                break;
        }
    }
}