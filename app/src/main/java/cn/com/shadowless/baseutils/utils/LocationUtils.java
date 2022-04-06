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
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * 获取地理位置工具类
 *
 * @author sHadowLess
 */
public class LocationUtils {

    /**
     * 位置管理器
     */
    private LocationManager locationManager;
    /**
     * 上下文
     */
    private Context mContext;
    /**
     * 最短时间
     */
    private int minTime;
    /**
     * 最短距离
     */
    private int minDistance;
    /**
     * 位置回调
     */
    private LocationListener locationListener;
    /**
     * 位置信息回调
     */
    private AddressCallback addressCallback = null;

    /**
     * 构造
     *
     * @param mContext         the m context
     * @param minTime          the min time
     * @param minDistance      the min distance
     * @param locationListener the location listener
     */
    public LocationUtils(Context mContext, int minTime, int minDistance, LocationListener locationListener) {
        this.mContext = mContext;
        this.minTime = minTime;
        this.minDistance = minDistance;
        this.locationListener = locationListener;
        locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
    }

    /**
     * 构造者
     *
     * @return the net utils . net utils builder
     */
    public static LocationUtils.LocationUtilsBuilder builder() {
        return new LocationUtils.LocationUtilsBuilder();
    }

    /**
     * 构造者实体
     */
    public static class LocationUtilsBuilder {
        /**
         * 上下文
         */
        private Context mContext;
        /**
         * 最短时间
         */
        private int minTime = 30000;
        /**
         * 最短距离
         */
        private int minDistance = 10;
        /**
         * 位置回调
         */
        private LocationListener locationListener;

        /**
         * Base url net utils . net utils builder.
         *
         * @param mContext the m context
         * @return the net utils . net utils builder
         */
        public LocationUtils.LocationUtilsBuilder mContext(Context mContext) {
            this.mContext = mContext;
            return this;
        }

        /**
         * Time out net utils . net utils builder.
         *
         * @param minTime the min time
         * @return the net utils . net utils builder
         */
        public LocationUtils.LocationUtilsBuilder timeOut(int minTime) {
            this.minTime = minTime;
            return this;
        }

        /**
         * Time out unit net utils . net utils builder.
         *
         * @param minDistance the min distance
         * @return the net utils . net utils builder
         */
        public LocationUtils.LocationUtilsBuilder timeOutUnit(int minDistance) {
            this.minDistance = minDistance;
            return this;
        }

        /**
         * Location listener location utils . location utils builder.
         *
         * @param locationListener the location listener
         * @return the location utils . location utils builder
         */
        public LocationUtils.LocationUtilsBuilder locationListener(LocationListener locationListener) {
            this.locationListener = locationListener;
            return this;
        }

        /**
         * Build net utils.
         *
         * @return the net utils
         */
        public LocationUtils build() {
            return new LocationUtils(this.mContext, this.minTime, this.minDistance, this.locationListener);
        }
    }

    /**
     * 清空回调事件
     */
    public void clearAddressCallback() {
        if (locationManager != null && locationListener != null) {
            locationManager.removeUpdates(locationListener);
        }
    }

    /**
     * 获取位置
     *
     * @param addressCallback the address callback
     */
    public void getLocation(AddressCallback addressCallback) {
        this.addressCallback = addressCallback;
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(mContext, "请申请定位权限", Toast.LENGTH_SHORT).show();
            ApplicationUtils.startApplicationInfo(mContext);
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
        Location location = locationManager.getLastKnownLocation(locationProvider);
        if (location != null) {
            // 显示当前设备的位置信息
            Log.e("getLocation", "=====显示当前设备的位置信息=====");
            showLocation(location);
        } else {//当GPS信号弱没获取到位置的时候可从网络获取
            Log.e("getLocation", "=====搜索不到卫星，使用网络=====");
            getLngAndLatWithNetwork();
        }
        // 监视地理位置变化，第二个和第三个参数分别为更新的最短时间minTime和最短距离minDistace
        // LocationManager 每隔 5 秒钟会检测一下位置的变化情况，当移动距离超过 10 米的时候，
        // 就会调用 LocationListener 的 onLocationChanged() 方法，并把新的位置信息作为参数传入。
        if (locationListener == null) {
            locationListener = getLocationListener();
        }
        locationManager.requestLocationUpdates(locationProvider, minTime, minDistance, locationListener);
    }


    /**
     * Show location.
     *
     * @param location the location
     */
    private void showLocation(Location location) {
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
     * 获取定位回调
     *
     * @return the location listener
     */
    private LocationListener getLocationListener() {
        return new LocationListener() {
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

            }
        };
    }


    /**
     * 通过网络获取位置信息
     */
    private void getLngAndLatWithNetwork() {
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(mContext, "请申请定位权限", Toast.LENGTH_SHORT).show();
            ApplicationUtils.startApplicationInfo(mContext);
            return;
        }
        if (locationListener == null) {
            locationListener = getLocationListener();
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, minTime, minDistance, locationListener);
        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        showLocation(location);
    }

    /**
     * 定位是否打开
     *
     * @param context the 上下文
     */
    private void isOpen(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        // 通过GPS卫星定位，定位级别可以精确到街（通过24颗卫星定位，在室外和空旷的地方定位准确、速度快）
        boolean gps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        // 通过WLAN或移动网络(3G/2G)确定的位置（也称作AGPS，辅助GPS定位。主要用于在室内或遮盖物（建筑群或茂密的深林等）密集的地方定位）
        boolean network = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (!gps || !network) {
            openGps(context);
        }
    }

    /**
     * 跳转定位设置
     *
     * @param context the 上下文
     */
    private void openGps(Context context) {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 位置回调
     */
    public interface AddressCallback {
        /**
         * 获取地址
         *
         * @param address the 实体类
         */
        void onGetAddress(Address address);

        /**
         * 获取经纬度
         *
         * @param lat the 经度
         * @param lng the 纬度
         */
        void onGetLocation(double lat, double lng);
    }

}
