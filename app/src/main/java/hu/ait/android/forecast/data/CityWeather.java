package hu.ait.android.forecast.data;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by zhaozhaoxia on 11/20/17.
 */

public class CityWeather extends RealmObject {
    @PrimaryKey
    private String cityWeatherID;

    private String cityName;

    public CityWeather() {}

    public CityWeather(String cityName) {
        this.cityName = cityName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityWeatherID() {
        return cityWeatherID;
    }
}
