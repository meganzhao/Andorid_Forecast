package hu.ait.android.forecast;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import hu.ait.android.forecast.data.CityWeather;
import hu.ait.android.forecast.data.WeatherResult;
import hu.ait.android.forecast.network.WeatherAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class mapActivity extends FragmentActivity implements OnMapReadyCallback {

    private ArrayList<CityWeather> list;
    private double[] coordArray;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        Intent intent = getIntent();
        list = intent.getParcelableArrayListExtra("list");
        coordArray = intent.getDoubleArrayExtra("coordArray");

        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.overviewMap);
        mapFragment.getMapAsync(this);


    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        for (int i = 0; i < list.size(); i++){
            googleMap.addMarker(new MarkerOptions()
                    .position(new LatLng(coordArray[i*2],coordArray[i*2+1]))
                    .title(list.get(i).getCityName())
                    );
        }
        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Intent intent = new Intent(mapActivity.this, WeatherScreen.class);
                intent.putExtra("cityName",marker.getTitle());
                mapActivity.this.startActivity(intent);
                return true;
            }
        });
    }
}
