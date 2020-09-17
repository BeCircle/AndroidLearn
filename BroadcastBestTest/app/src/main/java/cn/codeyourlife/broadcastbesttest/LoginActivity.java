package cn.codeyourlife.broadcastbesttest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    private EditText nameEdit;
    private EditText passwdEdit;
    private Button login;
    private CheckBox rememberPassword;
    private SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        nameEdit = (EditText) findViewById(R.id.edit_name);
        passwdEdit = (EditText) findViewById(R.id.edit_passwd);
        login = (Button) findViewById(R.id.btn_login);
        rememberPassword = (CheckBox) findViewById(R.id.remember_password);
        pref = getPreferences(MODE_PRIVATE);

        boolean isRemember = pref.getBoolean("remember_password", false);
        if (isRemember) {
            nameEdit.setText(pref.getString("name", ""));
            passwdEdit.setText(pref.getString("password", ""));
            rememberPassword.setChecked(isRemember);
        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameEdit.getText().toString();
                String passwd = passwdEdit.getText().toString();
                if (name.equals("admin") && passwd.equals("123456")) {
                    // 记住密码
                    SharedPreferences.Editor  editor = pref.edit();
                    if (rememberPassword.isChecked()) {
                        editor.putBoolean("remember_password", true);
                        editor.putString("name", name);
                        editor.putString("password", passwd);
                    }else{
                        editor.clear();
                    }
                    editor.apply();

                    // 登录成功，跳转到main
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(LoginActivity.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}