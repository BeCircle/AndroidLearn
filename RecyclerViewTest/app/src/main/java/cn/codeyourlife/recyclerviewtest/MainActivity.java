package cn.codeyourlife.recyclerviewtest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<City> cityList = new ArrayList<>();

    private String[] data = {"北京", "上海","广州", "深圳","重庆","天津","成都","苏州","杭州","武汉","南京","长沙","合肥","济南","青岛","厦门"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initCityList();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        StaggeredGridLayoutManager LayoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(LayoutManager);
        // 适配器
        CityAdapter adapter = new CityAdapter(cityList);
        recyclerView.setAdapter(adapter);
    }

    private void initCityList(){
        for (String item : data) {
            cityList.add(new City(item, R.drawable.gz));
        }
    }
}