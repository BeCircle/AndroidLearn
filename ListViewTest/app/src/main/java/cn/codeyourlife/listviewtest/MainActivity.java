package cn.codeyourlife.listviewtest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

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
        CityAdapter adapter = new CityAdapter(MainActivity.this, R.layout.city_item, cityList);

        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                City city = cityList.get(i);
                Toast.makeText(MainActivity.this, city.getName(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void initCityList(){
        for (String item : data) {
            cityList.add(new City(item, R.drawable.gz));
        }
    }
}