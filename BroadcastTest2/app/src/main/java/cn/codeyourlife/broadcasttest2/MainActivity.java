package cn.codeyourlife.broadcasttest2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.IntentFilter;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "2MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        IntentFilter intent = new IntentFilter();
        intent.addAction("cn.codeyourlife.broadcasttest.MY_BROADCAST");
        Receiver receiver = new Receiver();
        registerReceiver(receiver, intent);
    }
}