package hu.ait.android.forecast.network;

import hu.ait.android.forecast.data.ForecastResult;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by zhaozhaoxia on 11/22/17.
 */

public interface ForecastAPI {
    @GET("data/2.5/forecast")
    Call<ForecastResult> getForecastResult(@Query("q") String q,
                                           @Query("units") String units,
                                           @Query("appid") String appid);
}
