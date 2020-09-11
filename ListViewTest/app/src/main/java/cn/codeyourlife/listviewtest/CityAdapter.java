package cn.codeyourlife.listviewtest;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class CityAdapter extends ArrayAdapter<City> {
    private static final String TAG = "CityAdapter";
    private int resourceId;

    public CityAdapter(@NonNull Context context, int resource, List<City> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Log.d(TAG, "getView: "+position);
        City city = getItem(position);
        View view;
        ViewHolder viewHolder;

        // 省略重复加载布局
        if (convertView == null){
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.cityImage = (ImageView) view.findViewById(R.id.city_image);
            viewHolder.cityName = (TextView) view.findViewById(R.id.city_name);
            // 将ViewHolder存储在View中
            view.setTag(viewHolder);
        }else{
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.cityImage.setImageResource(city.getImageId());
        viewHolder.cityName.setText(city.getName());
        return view;
    }

    class ViewHolder {
        ImageView cityImage;
        TextView cityName;
    }
}
