package hu.ait.android.forecast;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;
import hu.ait.android.forecast.data.WeatherResult;
import hu.ait.android.forecast.network.WeatherAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherScreen extends AppCompatActivity {
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvTemp)
    TextView tvTemp;
    @BindView(R.id.ivWeather)
    ImageView ivWeather;
    @BindView(R.id.tvTemp_min)
    TextView tvTemp_min;
    @BindView(R.id.tvTemp_max)
    TextView tvTemp_max;
    @BindView(R.id.tvWeatherMain)
    TextView tvWeatherMain;
//    @BindView(R.id.tvCoord_lat)
//    TextView tvCoord_lat;
//    @BindView(R.id.tvCoord_lon)
//    TextView tvCoord_lon;

//    @BindView(R.id.tvPressure)
//    TextView tvPressure;
//    @BindView(R.id.tvHumidity)
//    TextView tvHumidity;

//    @BindView(R.id.tvWind)
//    TextView tvWind;
//    @BindView(R.id.tvSpeed)
//    TextView tvSpeed;
//    @BindView(R.id.tvDegree)
//    TextView tvDegree;
//    @BindView(R.id.tvCloud)
//    TextView tvCloud;
//    @BindView(R.id.tvRain)
//    TextView tvRain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_screen);
        ButterKnife.bind(this);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.openweathermap.org")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        WeatherAPI weatherAPI = retrofit.create(WeatherAPI.class);

        Intent intent = getIntent();
        String cityName = intent.getStringExtra("cityName");
        Call<WeatherResult> call = weatherAPI.getWeatherResult(cityName,
                "metric",
                "24174f0b2524d2bda0ad6bd18de719de");

        call.enqueue(new Callback<WeatherResult>() {
            @Override
            public void onResponse(Call<WeatherResult> call, Response<WeatherResult> response) {
                tvName.setText("" + response.body().getName());
                tvTemp.setText("" + response.body().getMain().getTemp());
                Glide.with(WeatherScreen.this).load("http://api.openweathermap.org/img/w/"
                        +response.body().getWeather().get(0).getIcon()
                        +".png").into(ivWeather);
                tvTemp_max.setText("" + response.body().getMain().getTempMax());
                tvTemp_min.setText("" + response.body().getMain().getTempMin());
                tvWeatherMain.setText(""+ response.body().getWeather().get(0).getMain());
//                tvCoord_lat.setText("Coordinate: " + response.body().getCoord().getLat());
//                tvCoord_lon.setText("Coordinate: " + response.body().getCoord().getLon());
            }

            @Override
            public void onFailure(Call<WeatherResult> call, Throwable t) {
//                tvPressure.setText(t.getMessage());
            }
        });


    }
}
