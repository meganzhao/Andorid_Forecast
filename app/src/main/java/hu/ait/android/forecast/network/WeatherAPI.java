package hu.ait.android.forecast.network;

import hu.ait.android.forecast.data.WeatherResult;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by zhaozhaoxia on 11/21/17.
 */

public interface WeatherAPI {
    @GET("data/2.5/weather")
    Call<WeatherResult> getWeatherResult(@Query("q") String q,
                                         @Query("units") String units,
                                         @Query("appid") String appid);
}
