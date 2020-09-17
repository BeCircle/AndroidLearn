package cn.codeyourlife.broadcastbesttest;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {
    private ForceOfflineReceiver forceOfflineReceiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityController.addActivity(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // 注册广播接收器
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MainActivity.MSG_FORCE_OFFLINE);
        this.forceOfflineReceiver = new ForceOfflineReceiver();
        registerReceiver(this.forceOfflineReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // 取消广播接收器
        if (this.forceOfflineReceiver != null) {
            unregisterReceiver(this.forceOfflineReceiver);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityController.removeActivity(this);
    }
}
