// Generated code from Butter Knife. Do not modify!
package hu.ait.android.forecast;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class WeatherScreen_ViewBinding implements Unbinder {
  private WeatherScreen target;

  @UiThread
  public WeatherScreen_ViewBinding(WeatherScreen target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public WeatherScreen_ViewBinding(WeatherScreen target, View source) {
    this.target = target;

    target.tvName = Utils.findRequiredViewAsType(source, R.id.tvName, "field 'tvName'", TextView.class);
    target.tvTemp = Utils.findRequiredViewAsType(source, R.id.tvTemp, "field 'tvTemp'", TextView.class);
    target.ivWeather = Utils.findRequiredViewAsType(source, R.id.ivWeather, "field 'ivWeather'", ImageView.class);
    target.tvTemp_min = Utils.findRequiredViewAsType(source, R.id.tvTemp_min, "field 'tvTemp_min'", TextView.class);
    target.tvTemp_max = Utils.findRequiredViewAsType(source, R.id.tvTemp_max, "field 'tvTemp_max'", TextView.class);
    target.tvWeatherMain = Utils.findRequiredViewAsType(source, R.id.tvWeatherMain, "field 'tvWeatherMain'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    WeatherScreen target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvName = null;
    target.tvTemp = null;
    target.ivWeather = null;
    target.tvTemp_min = null;
    target.tvTemp_max = null;
    target.tvWeatherMain = null;
  }
}
