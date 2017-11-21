package hu.ait.android.forecast;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by zhaozhaoxia on 11/21/17.
 */

public class WeatherApplication extends Application{

    private Realm realm;

    public void onCreate(){
        super.onCreate();
        Realm.init(this);
    }

    public void openRealm(){
        RealmConfiguration config = new RealmConfiguration
                .Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        realm = Realm.getInstance(config);
    }

    public void closeRealm(){
        realm.close();
    }

    public Realm getRealm(){
        return realm;
    }
}
