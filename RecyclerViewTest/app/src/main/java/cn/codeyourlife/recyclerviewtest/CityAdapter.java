package cn.codeyourlife.recyclerviewtest;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.ViewHolder> {
    private List<City> cityList;

    public CityAdapter(List<City> cityList) {
        this.cityList = cityList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.city_item, parent, false);
        final ViewHolder viewHolder =  new ViewHolder(view);
        viewHolder.cityView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int p = viewHolder.getAdapterPosition();
                City city = cityList.get(p);
                Toast.makeText(view.getContext(), "click view: "+city.getName(), Toast.LENGTH_SHORT).show();
            }
        });
        viewHolder.cityImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int p = viewHolder.getAdapterPosition();
                City city = cityList.get(p);
                Toast.makeText(view.getContext(), "click image: "+city.getName(), Toast.LENGTH_SHORT).show();
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        City city = this.cityList.get(position);
        holder.cityImage.setImageResource(city.getImageId());
        holder.cityName.setText(city.getName());
    }

    @Override
    public int getItemCount() {
        return this.cityList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView cityImage;
        TextView cityName;
        View cityView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.cityView = itemView;
            cityImage = (ImageView) itemView.findViewById(R.id.city_image);
            cityName = (TextView) itemView.findViewById(R.id.city_name);
        }
    }
}
