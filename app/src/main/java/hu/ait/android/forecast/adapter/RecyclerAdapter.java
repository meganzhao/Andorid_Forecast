package hu.ait.android.forecast.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import hu.ait.android.forecast.MainActivity;
import hu.ait.android.forecast.R;
import hu.ait.android.forecast.WeatherScreen;
import hu.ait.android.forecast.data.CityWeather;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by zhaozhaoxia on 11/20/17.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>
{
    private List<CityWeather> cityWeatherList;
    private Realm realmCityWeather;
    private Context context;
//    int[] color_arr={Color.parseColor("#81D4FA"),Color.parseColor("#4FC3F7"),Color.parseColor("#29B6F6"),
//            Color.parseColor("#03A9F4"),Color.parseColor("#039BE5"),Color.parseColor("#0288D1"),
//            Color.parseColor("#0277BD"),Color.parseColor("#01579B"),Color.parseColor("#0277BD"),
//            Color.parseColor("#0288D1"),Color.parseColor("#039BE5"),Color.parseColor("#03A9F4"),
//            Color.parseColor("#29B6F6"),Color.parseColor("#4FC3F7"),};
    int[] color_arr={Color.parseColor("#90CAF9"),Color.parseColor("#81D4FA"),Color.parseColor("#80DEEA"),
        Color.parseColor("#4DD0E1"),Color.parseColor("#4FC3F7"),Color.parseColor("#64B5F6"),
        Color.parseColor("#26C6DA"),Color.parseColor("#29B6F6"),Color.parseColor("#42A5F5")};
    int colorIndex = -1;

    public RecyclerAdapter(Context context, Realm realmCityWeather){
        this.context = context;
        this.realmCityWeather = realmCityWeather;
        this.cityWeatherList = new ArrayList<CityWeather>();
        RealmResults<CityWeather> cityResult = realmCityWeather.where(CityWeather.class).findAll().sort("cityName", Sort.ASCENDING);

        for (CityWeather city: cityResult){
            cityWeatherList.add(city);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View cityWeatherRow = LayoutInflater.from(parent.getContext()).inflate(R.layout.cityweather_row,parent,false);
        colorIndex = (colorIndex + 1) % 9;
        cityWeatherRow.setBackgroundColor(color_arr[colorIndex]);
        return new ViewHolder(cityWeatherRow);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final CityWeather cityWeatherData = cityWeatherList.get(position);
        final int removePosition = position;
        holder.tvCityName.setText(cityWeatherData.getCityName());

        holder.tvCityName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, WeatherScreen.class);
                intent.putExtra("cityName",cityWeatherData.getCityName());
                context.startActivity(intent);
            }
        });

        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeAt(removePosition);
            }
        });

    }

    public void removeAt(int position){
        CityWeather toRemoveObject = cityWeatherList.get(position);
        realmCityWeather.beginTransaction();

        toRemoveObject.deleteFromRealm();

        realmCityWeather.commitTransaction();
        cityWeatherList.remove(position);
        notifyDataSetChanged();
    }

    public void addCity(String cityName){
        realmCityWeather.beginTransaction();

        CityWeather newCity = realmCityWeather.createObject(CityWeather.class, UUID.randomUUID().toString());
        newCity.setCityName(cityName);

        realmCityWeather.commitTransaction();

        cityWeatherList.add(newCity);
        notifyItemInserted(cityWeatherList.size());
    }



    @Override
    public int getItemCount() {
        return cityWeatherList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvCityName;
        private ImageView ivDelete;
        public ViewHolder(View itemView) {
            super(itemView);
            tvCityName = itemView.findViewById(R.id.tvCityName);
            ivDelete = itemView.findViewById(R.id.ivDelete);
        }
    }
}
