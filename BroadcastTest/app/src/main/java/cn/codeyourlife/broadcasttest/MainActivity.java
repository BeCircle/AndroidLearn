package cn.codeyourlife.broadcasttest;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private IntentFilter intent;
    private NetworkChangeReceiver networkChangeReceiver;
    private static final String TAG = "MainActivity";

    private MutableLiveData<String> liveData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 网络连接状态
        intent = new IntentFilter();
        intent.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        networkChangeReceiver = new NetworkChangeReceiver();
        registerReceiver(networkChangeReceiver, intent);

        // 自定义广播
        IntentFilter intent2 = new IntentFilter();
        intent2.setPriority(100);
        intent2.addAction("cn.codeyourlife.broadcasttest.MY_BROADCAST");
        MyReceiver receiver = new MyReceiver();
        registerReceiver(receiver, intent2);

        // 创建更新UI的观察者
        final Observer<String> observer = new Observer<String>() {
            @Override
            public void onChanged(@Nullable final String newName) {
                // 更新UI
                Log.d(TAG, "observe: liveData change");
                Toast.makeText(MainActivity.this, "LiveData change", Toast.LENGTH_SHORT).show();
            }
        };

        // liveData替代localBroadCast
        liveData = new MutableLiveData<String>();
        liveData.observe(this, observer);

        Button button = (Button) findViewById(R.id.click_me);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: ");
                Intent intent = new Intent("cn.codeyourlife.broadcasttest.MY_BROADCAST");
//                Intent intent = new Intent();
                // android 8 开始大部分接收器只能进行动态注册，这里静态注册的，指定接收器可以接收到
//                ComponentName componentName = new ComponentName(MainActivity.this,MyReceiver.class);
//                intent.setComponent(componentName);

                sendOrderedBroadcast(intent,null);

                liveData.setValue("12");
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(networkChangeReceiver);
    }

    class  NetworkChangeReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = manager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isAvailable()) {
                Toast.makeText(context, "网络可用", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(context, "网络不可用", Toast.LENGTH_SHORT).show();
            }
        }
    }
}