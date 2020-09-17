package cn.codeyourlife.broadcastbesttest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends BaseActivity {

    public static final String MSG_FORCE_OFFLINE = "cn.codeyourlife.broadcastbesttest.FORCE_OFFLINE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnForceOffline = (Button) findViewById(R.id.btn_force_offline);
        btnForceOffline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MSG_FORCE_OFFLINE);
                sendBroadcast(intent);
            }
        });
    }
}