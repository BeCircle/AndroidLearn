package cn.codeyourlife.sharedpreference;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private static final String kvFile = "data";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnSave = (Button) findViewById(R.id.save_data);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = getSharedPreferences(kvFile, MODE_PRIVATE).edit();
                editor.putString("name", "王必权");
                editor.putInt("age", 21);
                editor.putBoolean("married", false);
                editor.apply();
            }
        });
    }
}