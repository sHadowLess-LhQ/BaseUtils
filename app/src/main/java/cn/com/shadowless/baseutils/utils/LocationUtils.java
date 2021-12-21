package cn.com.shadowless.baseutils.utils;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * The type Location utils.
 *
 * @author sHadowLess
 */
public class LocationUtils {

    /**
     * The constant uniqueInstance.
     */
    private volatile static LocationUtils uniqueInstance = null;
    /**
     * The Location manager.
     */
    private LocationManager locationManager = null;
    /**
     * The M context.
     */
    private Context mContext = null;

    /**
     * The constant location.
     */
    private Location location = null;

    /**
     * The Address callback.
     */
    private AddressCallback addressCallback = null;

    /**
     * Instantiates a new Location utils.
     *
     * @param context the context
     */
    private LocationUtils(Context context) {
        mContext = context;
    }

    /**
     * Gets instance.
     *
     * @param context the context
     * @return the instance
     */
    public static LocationUtils getInstance(Context context) {
        if (uniqueInstance == null) {
            synchronized (LocationUtils.class) {
                if (uniqueInstance == null) {
                    uniqueInstance = new LocationUtils(context);
                }
            }
        }
        return uniqueInstance;
    }

    /**
     * 清空回调事件
     */
    public void clearAddressCallback() {
        removeLocationUpdatesListener();
    }

    /**
     * Gets location.
     *
     * @param addressCallback the address callback
     */
    public void getLocation(AddressCallback addressCallback) {
        this.addressCallback = addressCallback;
        //1.获取位置管理器
        locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        //添加用户权限申请判断
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        //2.获取位置提供器，GPS或是NetWork
        // 获取所有可用的位置提供器
        List<String> providerList = locationManager.getProviders(true);
        String locationProvider;
        if (providerList.contains(LocationManager.GPS_PROVIDER)) {
            //GPS 定位的精准度比较高，但是非常耗电。
            Log.e("getLocation", "=====GPS_PROVIDER=====");
            locationProvider = LocationManager.GPS_PROVIDER;
        } else if (providerList.contains(LocationManager.NETWORK_PROVIDER)) {
            //网络定位的精准度稍差，但耗电量比较少。
            Log.e("getLocation", "=====NETWORK_PROVIDER=====");
            locationProvider = LocationManager.NETWORK_PROVIDER;
        } else {
            Log.e("getLocation", "=====NO_PROVIDER=====");
            // 当没有可用的位置提供器时，弹出Toast提示用户
            isOpen(mContext);
            return;
        }

        //3.获取上次的位置，一般第一次运行，此值为null
        location = locationManager.getLastKnownLocation(locationProvider);
        if (location != null) {
            // 显示当前设备的位置信息
            Log.e("getLocation", "=====显示当前设备的位置信息=====");
            showLocation();
        } else {//当GPS信号弱没获取到位置的时候可从网络获取
            Log.e("getLocation", "=====搜索不到卫星，使用网络=====");
            getLngAndLatWithNetwork();
        }
        // 监视地理位置变化，第二个和第三个参数分别为更新的最短时间minTime和最短距离minDistace
        // LocationManager 每隔 5 秒钟会检测一下位置的变化情况，当移动距离超过 10 米的时候，
        // 就会调用 LocationListener 的 onLocationChanged() 方法，并把新的位置信息作为参数传入。
        locationManager.requestLocationUpdates(locationProvider, 30000, 10, locationListener);
    }


    /**
     * Show location.
     */
    private void showLocation() {
        if (location == null) {
            getLocation(addressCallback);
        } else {
            //纬度
            double latitude = location.getLatitude();
            //经度
            double longitude = location.getLongitude();
            if (addressCallback != null) {
                this.addressCallback.onGetLocation(latitude, longitude);
            }
            getAddress(latitude, longitude);
        }
    }

    /**
     * Gets address.
     *
     * @param latitude  the latitude
     * @param longitude the longitude
     */
    private void getAddress(double latitude, double longitude) {
        //Geocoder通过经纬度获取具体信息
        Geocoder gc = new Geocoder(mContext, Locale.getDefault());
        try {
            List<Address> locationList = gc.getFromLocation(latitude, longitude, 1);
            if (locationList != null) {
                Address address = locationList.get(0);
                if (addressCallback != null) {
                    this.addressCallback.onGetAddress(address);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Remove location updates listener.
     */
    private void removeLocationUpdatesListener() {
        if (locationManager != null) {
            uniqueInstance = null;
            locationManager.removeUpdates(locationListener);
        }
    }


    /**
     * The Location listener.
     */
    private LocationListener locationListener = new LocationListener() {
        // Provider的状态在可用、暂时不可用和无服务三个状态直接切换时触发此函数
        @Override
        public void onStatusChanged(String provider, int status, Bundle arg2) {
            Log.e("TAG", "onStatusChanged: " + provider + status + arg2.toString());
        }

        // Provider被enable时触发此函数，比如GPS被打开
        @Override
        public void onProviderEnabled(String provider) {
            Log.e("TAG", "onProviderEnabled: " + provider);
        }

        // Provider被disable时触发此函数，比如GPS被关闭
        @Override
        public void onProviderDisabled(String provider) {
            Log.e("TAG", "onProviderDisabled: " + provider);
        }

        @Override
        public void onLocationChanged(Location loc) {
            //getLocation();
        }
    };

    /**
     * Gets lng and lat with network.
     */
    private void getLngAndLatWithNetwork() {
        //添加用户权限申请判断
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationManager locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 30000, 10, locationListener);
        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        showLocation();
    }

    /**
     * Is open.
     *
     * @param context the context
     * @return the boolean
     */
    public static boolean isOpen(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        // 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        // 通过WLAN或移动网络(3G/2G)确定的位置（也称作AGPS，辅助GPS定位。主要用于在室内或遮盖物（建筑群或茂密的深林等）密集的地方定位）
        boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (!gps || !network) {
            openGPS(context);
            return false;
        } else {
            return true;
        }
    }

    /**
     * Open gps.
     *
     * @param activity the activity
     */
    public static void openGPS(Context activity) {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }

    /**
     * The interface Address callback.
     */
    public interface AddressCallback {
        /**
         * On get address.
         *
         * @param address the address
         */
        void onGetAddress(Address address);

        /**
         * On get location.
         *
         * @param lat the lat
         * @param lng the lng
         */
        void onGetLocation(double lat, double lng);
    }

}
