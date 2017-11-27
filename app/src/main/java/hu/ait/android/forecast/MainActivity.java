package hu.ait.android.forecast;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import hu.ait.android.forecast.adapter.RecyclerAdapter;
import hu.ait.android.forecast.data.CityWeather;
import hu.ait.android.forecast.data.WeatherResult;
import hu.ait.android.forecast.network.WeatherAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity{
    private RecyclerAdapter adapter;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private double[] coordArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawerLayout, R.string.drawer_open, R.string.drawer_close);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        ((WeatherApplication)getApplication()).openRealm();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rvList);
        adapter = new RecyclerAdapter(this,
                ((WeatherApplication)getApplication()).getRealm());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabAdd);

        createNavigation();
    }

    private void createNavigation() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        switch (menuItem.getItemId()) {
                            case R.id.nav_addCity:
                                createAlertDialog();
                                drawerLayout.closeDrawer(GravityCompat.START);
                                break;
                            case R.id.nav_map:
                                openMap();
                                break;
                            case R.id.nav_about:
                                showSnackBarMessage();
                                drawerLayout.closeDrawer(GravityCompat.START);
                                break;
                        }
                        return false;
                    }
                });
    }

    private void openMap() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.openweathermap.org")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Intent overviewMapIntent = new Intent(MainActivity.this, mapActivity.class);
        ArrayList<CityWeather> list = adapter.getCityWeatherList();
        coordArray = new double[list.size()*2];
        for (int i = 0; i < list.size(); i++){
            weatherCall(retrofit, i, list.get(i).getCityName());
        }
        overviewMapIntent.putParcelableArrayListExtra("list",list);
        overviewMapIntent.putExtra("coordArray", coordArray);
        startActivity(overviewMapIntent);
    }

    private void weatherCall(Retrofit retrofit, int i, String cityName) {
        final int index = i;
        WeatherAPI weatherAPI = retrofit.create(WeatherAPI.class);
        Call<WeatherResult> call = weatherAPI.getWeatherResult(cityName,
                "metric",
                "24174f0b2524d2bda0ad6bd18de719de");

        call.enqueue(new Callback<WeatherResult>() {
            @Override
            public void onResponse(Call<WeatherResult> call, Response<WeatherResult> response) {
                coordArray[index*2] = response.body().getCoord().getLat();
                coordArray[index*2+1] = response.body().getCoord().getLon();
            }
            @Override
            public void onFailure(Call<WeatherResult> call, Throwable t) {

            }
        });
    }


    @OnClick(R.id.fabAdd)
    void showDialog() {
        createAlertDialog();
    }

    public RecyclerAdapter getAdapter() {
        return adapter;
    }

    private void createAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.dislog_title));
        final EditText input = new EditText(MainActivity.this);
        builder.setView(input);
        builder.setPositiveButton(R.string.dislog_positive_botton, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                adapter.addCity(input.getText().toString());
            }
        });
        builder.setNegativeButton(R.string.dislog_negative_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void onDestroy(){
        ((WeatherApplication)getApplication()).closeRealm();
        super.onDestroy();
    }

    public void showSnackBarMessage(){
        Toast.makeText(this, "Author: Megan Zhao", Toast.LENGTH_SHORT).show();
    }
}
