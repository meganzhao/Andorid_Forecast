package hu.ait.android.forecast;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.SimpleDateFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import hu.ait.android.forecast.data.WeatherResult;
import hu.ait.android.forecast.network.WeatherAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailPage extends FragmentActivity implements OnMapReadyCallback {
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvPressure)
    TextView tvPressure;
    @BindView(R.id.tvHumidity)
    TextView tvHumidity;
    @BindView(R.id.tvWindSpeed)
    TextView tvWindSpeed;
    @BindView(R.id.tvWindDegree)
    TextView tvWindDegree;
    @BindView(R.id.tvSunrise)
    TextView tvSunrise;
    @BindView(R.id.tvSunset)
    TextView tvSunset;
    @BindView(R.id.tvDescription)
    TextView tvDescription;

    private String cityName;
    private double coord_lat;
    private double coord_lon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_page);

        ButterKnife.bind(this);
        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.openweathermap.org")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Intent intent = getIntent();
        cityName = intent.getStringExtra("cityName");
        coord_lat = intent.getDoubleExtra("coord_lat",50.0);
        coord_lon = intent.getDoubleExtra("coord_lon",6.0);

        weatherCall(retrofit, cityName);
    }

    private void weatherCall(Retrofit retrofit, final String cityName) {
        WeatherAPI weatherAPI = retrofit.create(WeatherAPI.class);
        Call<WeatherResult> call = weatherAPI.getWeatherResult(cityName,
                "metric",
                "24174f0b2524d2bda0ad6bd18de719de");

        call.enqueue(new Callback<WeatherResult>() {
            @Override
            public void onResponse(Call<WeatherResult> call, Response<WeatherResult> response) {
                tvName.setText("" + response.body().getName());
                tvDescription.setText("Weather: " + response.body().getWeather().get(0).getDescription());
                tvPressure.setText("Pressure: " + response.body().getMain().getPressure());
                tvHumidity.setText("Humidity: " + response.body().getMain().getHumidity());
                tvWindSpeed.setText("Wind speed: " + response.body().getWind().getSpeed());
                tvWindDegree.setText("Wind degree: " + response.body().getWind().getDeg() + "˚");
                SimpleDateFormat sdf = new SimpleDateFormat("h:mm a");
                tvSunrise.setText("Sunrise: " + sdf.format(
                        response.body().getSys().getSunrise() * 1000));
                tvSunset.setText("Sunset: " + sdf.format(
                        response.body().getSys().getSunset() * 1000));
            }

            @Override
            public void onFailure(Call<WeatherResult> call, Throwable t) {
                tvName.setText(t.getMessage());
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(coord_lat, coord_lon))
                .title(cityName));
        googleMap.moveCamera( CameraUpdateFactory.newLatLngZoom(new LatLng(coord_lat,coord_lon) , 3.0f) );
    }
}
