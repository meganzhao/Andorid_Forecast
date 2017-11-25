// Generated code from Butter Knife. Do not modify!
package hu.ait.android.forecast;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class DetailPage_ViewBinding implements Unbinder {
  private DetailPage target;

  @UiThread
  public DetailPage_ViewBinding(DetailPage target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public DetailPage_ViewBinding(DetailPage target, View source) {
    this.target = target;

    target.tvName = Utils.findRequiredViewAsType(source, R.id.tvName, "field 'tvName'", TextView.class);
    target.tvPressure = Utils.findRequiredViewAsType(source, R.id.tvPressure, "field 'tvPressure'", TextView.class);
    target.tvHumidity = Utils.findRequiredViewAsType(source, R.id.tvHumidity, "field 'tvHumidity'", TextView.class);
    target.tvWindSpeed = Utils.findRequiredViewAsType(source, R.id.tvWindSpeed, "field 'tvWindSpeed'", TextView.class);
    target.tvWindDegree = Utils.findRequiredViewAsType(source, R.id.tvWindDegree, "field 'tvWindDegree'", TextView.class);
    target.tvSunrise = Utils.findRequiredViewAsType(source, R.id.tvSunrise, "field 'tvSunrise'", TextView.class);
    target.tvSunset = Utils.findRequiredViewAsType(source, R.id.tvSunset, "field 'tvSunset'", TextView.class);
    target.tvDescription = Utils.findRequiredViewAsType(source, R.id.tvDescription, "field 'tvDescription'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    DetailPage target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvName = null;
    target.tvPressure = null;
    target.tvHumidity = null;
    target.tvWindSpeed = null;
    target.tvWindDegree = null;
    target.tvSunrise = null;
    target.tvSunset = null;
    target.tvDescription = null;
  }
}
