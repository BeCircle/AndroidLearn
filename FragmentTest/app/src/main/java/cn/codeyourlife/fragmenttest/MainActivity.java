package cn.codeyourlife.fragmenttest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private int active = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(this);
//        setFragment(new RightFragment());
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.button) {
            if (active == 0) {
//                setFragment(new AnotherRightFragment());
                active = 1;
            } else {
//                setFragment(new RightFragment());
                active = 0;
            }
        }
    }

    private void setFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.right_layout, fragment);
//        // 模拟返回栈
        transaction.addToBackStack(null);
        transaction.commit();
    }
}