package hu.ait.android.forecast.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        return new ViewHolder(cityWeatherRow);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final CityWeather cityWeatherData = cityWeatherList.get(position);
        holder.tvCityName.setText(cityWeatherData.getCityName());

        holder.tvCityName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, WeatherScreen.class);
                intent.putExtra("cityName",cityWeatherData.getCityName());
                context.startActivity(intent);
            }
        });
    }

    public void addCity(String cityName){
        realmCityWeather.beginTransaction();

        CityWeather newCity = realmCityWeather.createObject(CityWeather.class, UUID.randomUUID().toString());
        newCity.setCityName(cityName);

        realmCityWeather.commitTransaction();

        cityWeatherList.add(0, newCity);
        notifyItemInserted(0);
    }

    @Override
    public int getItemCount() {
        return cityWeatherList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvCityName;
        public ViewHolder(View itemView) {
            super(itemView);
            tvCityName = itemView.findViewById(R.id.tvCityName);
        }
    }
}
