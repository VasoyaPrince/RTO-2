package anon.rtoinfo.rtovehical.Process;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;

import androidx.core.app.ActivityCompat;

public class GpsTracker extends Service implements LocationListener {
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;
    private static final long MIN_TIME_BW_UPDATES = 60000;
    boolean canGetLocation = false;
    boolean isGPSEnabled = false;
    boolean isNetworkEnabled = false;
    double latitude;
    Location location;
    protected LocationManager locationManager;
    double longitude;
    private final Context mContext;

    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onLocationChanged(Location location2) {
    }

    public void onProviderDisabled(String str) {
    }

    public void onProviderEnabled(String str) {
    }

    public void onStatusChanged(String str, int i, Bundle bundle) {
    }

    public GpsTracker(Context context) {
        this.mContext = context;
        getLocation();
    }

    @SuppressLint("MissingPermission")
    public Location getLocation() {
        try {
            this.locationManager = (LocationManager) this.mContext.getSystemService(LOCATION_SERVICE);
            this.isGPSEnabled = this.locationManager.isProviderEnabled("gps");
            this.isNetworkEnabled = this.locationManager.isProviderEnabled("network");
            if (!this.isGPSEnabled && !this.isNetworkEnabled) {
                return this.location;
            }
            this.canGetLocation = true;
            if (this.isNetworkEnabled) {
                if (!(ActivityCompat.checkSelfPermission(this.mContext, "android.permission.ACCESS_COARSE_LOCATION") == 0 || ActivityCompat.checkSelfPermission(this.mContext, "android.permission.ACCESS_COARSE_LOCATION") == 0)) {
                    ActivityCompat.requestPermissions((Activity) this.mContext, new String[]{"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_COARSE_LOCATION"}, 101);
                }
                this.locationManager.requestLocationUpdates("network", MIN_TIME_BW_UPDATES, 10.0f, this);
                if (this.locationManager != null) {
                    this.location = this.locationManager.getLastKnownLocation("network");
                    if (this.location != null) {
                        this.latitude = this.location.getLatitude();
                        this.longitude = this.location.getLongitude();
                    }
                }
            }
            if (this.isGPSEnabled && this.location == null) {
                if (!(ActivityCompat.checkSelfPermission(this.mContext, "android.permission.ACCESS_COARSE_LOCATION") == 0 || ActivityCompat.checkSelfPermission(this.mContext, "android.permission.ACCESS_COARSE_LOCATION") == 0)) {
                    ActivityCompat.requestPermissions((Activity) this.mContext, new String[]{"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_COARSE_LOCATION"}, 101);
                }
                this.locationManager.requestLocationUpdates("gps", MIN_TIME_BW_UPDATES, 10.0f, this);
                if (this.locationManager != null) {
                    this.location = this.locationManager.getLastKnownLocation("gps");
                    if (this.location != null) {
                        this.latitude = this.location.getLatitude();
                        this.longitude = this.location.getLongitude();
                    }
                }
            }
            return this.location;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public double getLatitude() {
        Location location2 = this.location;
        if (location2 != null) {
            this.latitude = location2.getLatitude();
        }
        return this.latitude;
    }

    public double getLongitude() {
        Location location2 = this.location;
        if (location2 != null) {
            this.longitude = location2.getLongitude();
        }
        return this.longitude;
    }

    public boolean canGetLocation() {
        return this.canGetLocation;
    }
}
