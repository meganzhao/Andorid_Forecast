package hu.ait.android.forecast.data;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by zhaozhaoxia on 11/20/17.
 */

public class CityWeather extends RealmObject implements Parcelable{
    @PrimaryKey
    private String cityWeatherID;

    private String cityName;

    public CityWeather() {}

    public CityWeather(String cityName) {
        this.cityName = cityName;
    }

    protected CityWeather(Parcel in) {
        cityWeatherID = in.readString();
        cityName = in.readString();
    }

    public static final Creator<CityWeather> CREATOR = new Creator<CityWeather>() {
        @Override
        public CityWeather createFromParcel(Parcel in) {
            return new CityWeather(in);
        }

        @Override
        public CityWeather[] newArray(int size) {
            return new CityWeather[size];
        }
    };

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityWeatherID() {
        return cityWeatherID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(cityWeatherID);
        parcel.writeString(cityName);
    }
}
