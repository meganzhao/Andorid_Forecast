package hu.ait.android.forecast;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hu.ait.android.forecast.chart.MyXAxisValueFormatter;
import hu.ait.android.forecast.data.ForecastResult;
import hu.ait.android.forecast.data.WeatherResult;
import hu.ait.android.forecast.network.ForecastAPI;
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
    @BindView(R.id.tvCoord_lat)
    TextView tvCoord_lat;
    @BindView(R.id.tvCoord_lon)
    TextView tvCoord_lon;
    private String cityName;
    private double coord_lat;
    private double coord_lon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_screen);
        ButterKnife.bind(this);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.openweathermap.org")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Intent intent = getIntent();
        cityName = intent.getStringExtra("cityName");
        weatherCall(retrofit, cityName);
        forecastCall(retrofit, cityName);

    }

    @OnClick(R.id.tvDetail)
    void enterDetailPage(){
        Intent detailPageIntent = new Intent(this, DetailPage.class);
        detailPageIntent.putExtra("cityName",cityName);
        detailPageIntent.putExtra("coord_lat",coord_lat);
        detailPageIntent.putExtra("coord_lon",coord_lon);
        startActivity(detailPageIntent);
    }


    private void weatherCall(Retrofit retrofit, String cityName) {
        WeatherAPI weatherAPI = retrofit.create(WeatherAPI.class);
        Call<WeatherResult> call = weatherAPI.getWeatherResult(cityName,
                "metric",
                "24174f0b2524d2bda0ad6bd18de719de");

        call.enqueue(new Callback<WeatherResult>() {
            @Override
            public void onResponse(Call<WeatherResult> call, Response<WeatherResult> response) {
                tvName.setText("" + response.body().getName());
                tvCoord_lon.setText("  (" + response.body().getCoord().getLon());
                tvCoord_lat.setText(",  " + response.body().getCoord().getLat() + ")");
                tvTemp.setText("" + response.body().getMain().getTemp() + "˚");
                Glide.with(WeatherScreen.this).load("http://api.openweathermap.org/img/w/"
                        +response.body().getWeather().get(0).getIcon()
                        +".png").into(ivWeather);
                tvTemp_max.setText("" + response.body().getMain().getTempMax() + "˚");
                tvTemp_min.setText(" / " + response.body().getMain().getTempMin() + "˚");
                tvWeatherMain.setText(""+ response.body().getWeather().get(0).getMain());
                coord_lat = response.body().getCoord().getLat();
                coord_lon = response.body().getCoord().getLon();
            }

            @Override
            public void onFailure(Call<WeatherResult> call, Throwable t) {
                tvName.setText(t.getMessage());
            }
        });
    }

    private void forecastCall(Retrofit retrofit, String cityName) {
        ForecastAPI forecastAPI = retrofit.create(ForecastAPI.class);
        final LineChart chart = (LineChart) findViewById(R.id.chart);
        final Call<ForecastResult> forecastResultCall = forecastAPI.getForecastResult(cityName,
                "metric",
                "24174f0b2524d2bda0ad6bd18de719de");

        forecastResultCall.enqueue(new Callback<ForecastResult>() {
            @Override
            public void onResponse(Call<ForecastResult> call, Response<ForecastResult> response) {
                Double[] forecastData = new Double[5];
                for (int i = 0; i < 5; i++){
                    forecastData[i] = response.body().getList().get(i).getMain().getTemp();
                }
                createChart(chart,forecastData);
            }
            @Override
            public void onFailure(Call<ForecastResult> call, Throwable t) {
                chart.setNoDataText(t.getMessage());
            }
        });
    }

    private void createChart(LineChart chart, Double[] data) {
        chart.setAutoScaleMinMaxEnabled(true);
        chart.getDescription().setText("Next five day's weather");
        chart.getDescription().setPosition(410,295);
        chart.getDescription().setTextSize(14);
        List<Entry> entries = new ArrayList<>();
        float x = 0f;
        for (Double eachData : data) {
            entries.add(new Entry(x, eachData.floatValue()));
            x++;
        }
        LineDataSet dataSet = new LineDataSet(entries, "Temperature");

        SimpleDateFormat format = new SimpleDateFormat("E");
        Calendar calendar = Calendar.getInstance();
        String[] dates = new String[5];
        for(int i = 0; i < 5;i++){
            dates[i] = format.format(calendar.getTime());
            calendar.add(Calendar.DATE, 1);
        }
        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(new MyXAxisValueFormatter(dates));

        LineData lineData = new LineData(dataSet);
        chart.setData(lineData);
        chart.getAxisRight().setDrawLabels(false);
        chart.getXAxis().setDrawGridLines(false);
        chart.invalidate();
    }

}
