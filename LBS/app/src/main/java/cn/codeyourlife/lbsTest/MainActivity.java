package cn.codeyourlife.lbsTest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public LocationClient locationClient;
    private TextView textView;
    private MapView mapView;
    private BaiduMap baiduMap;
    private boolean isFirstLocate = true;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 匿名类
        // BDAbstractLocationListener 是异步的，就不需要自建线程获取
        BDAbstractLocationListener bdLocationListener = getListener();
        locationClient = new LocationClient(getApplicationContext());
        locationClient.registerLocationListener(bdLocationListener);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        mapView = (MapView)findViewById(R.id.bmapView);
        // 获取bdMap实例地图总控制器
        baiduMap = mapView.getMap();
        // 显示自己位置
        baiduMap.setMyLocationEnabled(true);

        textView = (TextView) findViewById(R.id.position_text_view);

        // 获取运行时权限
        List<String> permissionList = new ArrayList<>();
        String [] needPermission = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        for (String per:needPermission){
            if (ContextCompat.checkSelfPermission(MainActivity.this, per)!=PackageManager.PERMISSION_GRANTED){
                permissionList.add(per);
            }
        }
        String [] requestPermission= permissionList.toArray(new String[0]);
        Log.d(TAG, "onCreate: "+permissionList.toString());
        if (!permissionList.isEmpty()){
            ActivityCompat.requestPermissions(MainActivity.this, requestPermission, 1);
        }else {
            requestLocation();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults.length > 0) {
                for (int res : grantResults) {
                    Log.d(TAG, "onRequestPermissionsResult: "+res);
                    if (res != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(this, "必须同意所有权限才能使用本程序", Toast.LENGTH_SHORT).show();
                        finish();
                        return;
                    }
                }
                requestLocation();
            } else {
                Toast.makeText(this, "发生未知错误", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    private void requestLocation(){
        initLocation();
        locationClient.start();
    }

    private void navigateTo(BDLocation bdLocation) {
        if (isFirstLocate){
            LatLng ll = new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude());
            MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(ll);
            baiduMap.animateMapStatus(update);
            update = MapStatusUpdateFactory.zoomTo(16f);
            baiduMap.animateMapStatus(update);
            isFirstLocate = false;
        }
        MyLocationData.Builder builder = new MyLocationData.Builder();
        builder.latitude(bdLocation.getLatitude());
        builder.longitude(bdLocation.getLongitude());
        MyLocationData locationData = builder.build();
        baiduMap.setMyLocationData(locationData);
    }

    private void  initLocation(){
        LocationClientOption option = new LocationClientOption();
        option.setScanSpan(5000);
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setIsNeedAddress(true);
        option.setOpenGps(true);
        locationClient.setLocOption(option);
    }

    private BDAbstractLocationListener  getListener(){
        return new BDAbstractLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                Log.d(TAG, "onReceiveLocation: ");
                StringBuilder position = new StringBuilder();
                position.append("维度：").append(bdLocation.getLatitude()).append("\n");
                position.append("经线：").append(bdLocation.getLongitude()).append("\n");
                position.append("国家：").append(bdLocation.getCountry()).append("\n");
                position.append("省：").append(bdLocation.getProvince()).append("\n");
                position.append("市：").append(bdLocation.getCity()).append("\n");
                position.append("区：").append(bdLocation.getDistrict()).append("\n");
                position.append("街道：").append(bdLocation.getStreet()).append("\n");
                position.append("定位方式：");
                if (bdLocation.getLocType() == BDLocation.TypeGpsLocation){
                    position.append("GPS");
                }else if (bdLocation.getLocType() == BDLocation.TypeNetWorkLocation){
                    position.append("网络");
                }
                textView.setText(position.toString());

                if (bdLocation.getLocType() == BDLocation.TypeGpsLocation || bdLocation.getLocType() == BDLocation.TypeNetWorkLocation){
                    navigateTo(bdLocation);
                }
            }
        };
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        locationClient.stop();
        mapView.onDestroy();
        baiduMap.setMyLocationEnabled(false);
    }
}