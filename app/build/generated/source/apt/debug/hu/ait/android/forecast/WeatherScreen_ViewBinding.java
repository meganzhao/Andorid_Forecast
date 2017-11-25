// Generated code from Butter Knife. Do not modify!
package hu.ait.android.forecast;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class WeatherScreen_ViewBinding implements Unbinder {
  private WeatherScreen target;

  private View view2131558535;

  @UiThread
  public WeatherScreen_ViewBinding(WeatherScreen target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public WeatherScreen_ViewBinding(final WeatherScreen target, View source) {
    this.target = target;

    View view;
    target.tvName = Utils.findRequiredViewAsType(source, R.id.tvName, "field 'tvName'", TextView.class);
    target.tvTemp = Utils.findRequiredViewAsType(source, R.id.tvTemp, "field 'tvTemp'", TextView.class);
    target.ivWeather = Utils.findRequiredViewAsType(source, R.id.ivWeather, "field 'ivWeather'", ImageView.class);
    target.tvTemp_min = Utils.findRequiredViewAsType(source, R.id.tvTemp_min, "field 'tvTemp_min'", TextView.class);
    target.tvTemp_max = Utils.findRequiredViewAsType(source, R.id.tvTemp_max, "field 'tvTemp_max'", TextView.class);
    target.tvWeatherMain = Utils.findRequiredViewAsType(source, R.id.tvWeatherMain, "field 'tvWeatherMain'", TextView.class);
    target.tvCoord_lat = Utils.findRequiredViewAsType(source, R.id.tvCoord_lat, "field 'tvCoord_lat'", TextView.class);
    target.tvCoord_lon = Utils.findRequiredViewAsType(source, R.id.tvCoord_lon, "field 'tvCoord_lon'", TextView.class);
    view = Utils.findRequiredView(source, R.id.tvDetail, "method 'enterDetailPage'");
    view2131558535 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.enterDetailPage();
      }
    });
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
    target.tvCoord_lat = null;
    target.tvCoord_lon = null;

    view2131558535.setOnClickListener(null);
    view2131558535 = null;
  }
}
