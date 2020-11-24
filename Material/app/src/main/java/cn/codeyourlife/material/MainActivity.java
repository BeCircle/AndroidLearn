package cn.codeyourlife.material;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // 显示导航按钮
            actionBar.setDisplayHomeAsUpEnabled(true);
            // 修改默认图标
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }

// 导航栏
        NavigationView navigationView = findViewById(R.id.nav_view);
// 设置一个默认选项
        navigationView.setCheckedItem(R.id.nav_call);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // 只是关闭滑动菜单，可添加对应其他操作
                drawerLayout.closeDrawers();
                return true;
            }
        });

// 悬浮按钮
FloatingActionButton floatingActionButton = findViewById(R.id.fab);
floatingActionButton.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        // 使用 view 来自动寻找最外层布局
        // 这里传入 view，使用的CoordinateLayout才能监控到弹出事件，否则Snackbar并不是其子控件
        Snackbar.make(view, "点击了完成", Snackbar.LENGTH_SHORT)
                .setAction("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(MainActivity.this, "取消成功", Toast.LENGTH_SHORT).show();
                    }
                }).show();
    }
});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toobar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.backup:
                Toast.makeText(this, "点击了备份", Toast.LENGTH_SHORT).show();
                break;
            case R.id.delete:
                Toast.makeText(this, "点击了删除", Toast.LENGTH_SHORT).show();
                break;
            case R.id.settings:
                Toast.makeText(this, "点击了设置", Toast.LENGTH_SHORT).show();
                break;
            // 修改导航按钮事件为打开滑动菜单
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            default:
                break;
        }
        return true;
    }
}