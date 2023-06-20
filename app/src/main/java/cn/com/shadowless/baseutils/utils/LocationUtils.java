package cn.com.shadowless.baseutils.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

import androidx.annotation.NonNull;
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
    private final LocationManager locationManager;
    /**
     * 上下文
     */
    private final Context mContext;
    /**
     * 最短时间
     */
    private final int minTime;
    /**
     * 最短距离
     */
    private final int minDistance;

    /**
     * 重试次数
     */
    private final int retryCount;

    /**
     * 当前次数
     */
    private int currentCount = -1;

    /**
     * 位置回调
     */
    private LocationListener locationListener;

    /**
     * 位置信息回调
     */
    private final AddressCallback addressCallback;

    /**
     * 位置信息提供器
     */
    private String locationProvider = LocationManager.GPS_PROVIDER;

    /**
     * 构造
     *
     * @param mContext         the m context
     * @param minTime          the min time
     * @param minDistance      the min distance
     * @param retryCount       the retry count
     * @param locationListener the location listener
     * @param addressCallback  the address callback
     */
    public LocationUtils(Context mContext, int minTime, int minDistance, int retryCount, LocationListener locationListener, AddressCallback addressCallback) {
        this.mContext = mContext;
        this.minTime = minTime;
        this.minDistance = minDistance;
        this.retryCount = retryCount;
        this.locationListener = locationListener;
        this.addressCallback = addressCallback;
        locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
    }

    /**
     * 构造者
     *
     * @return the net utils . net utils builder
     */
    public static LocationUtilsBuilder builder() {
        return new LocationUtilsBuilder();
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
         * 重试次数
         */
        private int retryCount = 5;

        /**
         * 最短时间
         */
        private int minTime = 3000;

        /**
         * 最短距离
         */
        private int minDistance = 10;

        /**
         * 位置回调
         */
        private LocationListener locationListener;

        /**
         * 结果回调
         */
        private AddressCallback addressCallback;

        /**
         * Base url net utils . net utils builder.
         *
         * @param mContext the m context
         * @return the net utils . net utils builder
         */
        public LocationUtilsBuilder context(Context mContext) {
            this.mContext = mContext;
            return this;
        }

        /**
         * Time out net utils . net utils builder.
         *
         * @param minTime the min time
         * @return the net utils . net utils builder
         */
        public LocationUtilsBuilder minTime(int minTime) {
            this.minTime = minTime * 1000;
            return this;
        }

        /**
         * Retry count location utils builder.
         *
         * @param retryCount the retry count
         * @return the location utils builder
         */
        public LocationUtilsBuilder retryCount(int retryCount) {
            this.retryCount = retryCount;
            return this;
        }

        /**
         * Time out unit net utils . net utils builder.
         *
         * @param minDistance the min distance
         * @return the net utils . net utils builder
         */
        public LocationUtilsBuilder minDistance(int minDistance) {
            this.minDistance = minDistance;
            return this;
        }

        /**
         * Location listener location utils . location utils builder.
         *
         * @param locationListener the location listener
         * @return the location utils . location utils builder
         */
        public LocationUtilsBuilder locationListener(LocationListener locationListener) {
            this.locationListener = locationListener;
            return this;
        }

        /**
         * Address callback location utils builder.
         *
         * @param addressCallback the address callback
         * @return the location utils builder
         */
        public LocationUtilsBuilder addressCallback(AddressCallback addressCallback) {
            this.addressCallback = addressCallback;
            return this;
        }

        /**
         * Build net utils.
         *
         * @return the net utils
         */
        public LocationUtils build() {
            return new LocationUtils(this.mContext, this.minTime, this.minDistance, this.retryCount, this.locationListener, this.addressCallback);
        }
    }

    /**
     * 清空回调事件
     */
    public void clearLocationCallback() {
        if (locationManager != null && locationListener != null) {
            locationManager.removeUpdates(locationListener);
        }
    }

    /**
     * 获取位置
     */
    public void getLocationNow() {
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            error("请申请定位权限");
            return;
        }
        currentCount++;
        if (currentCount == retryCount) {
            currentCount = 0;
            error("最大重试次数结束，获取位置信息失败");
            return;
        }
        List<String> providerList = locationManager.getProviders(true);
        if (providerList.contains(LocationManager.GPS_PROVIDER)) {
            locationProvider = LocationManager.GPS_PROVIDER;
        } else if (providerList.contains(LocationManager.NETWORK_PROVIDER)) {
            locationProvider = LocationManager.NETWORK_PROVIDER;
        } else {
            getLocationNow();
            return;
        }
        Location location = locationManager.getLastKnownLocation(locationProvider);
        if (location != null) {
            showLocation(location);
        } else {
            getLngAndLatWithNetwork();
        }
    }

    /**
     * Gets location.
     */
    public void getLocation() {
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            error("请申请定位权限");
            return;
        }
        if (locationListener == null) {
            getLocationNow();
            locationListener = getLocationListener();
            locationManager.requestLocationUpdates(locationProvider, minTime, minDistance, locationListener);
        }
    }

    /**
     * Calculate address.
     *
     * @param location the location
     */
    public void getAddress(Location location) {
        //纬度
        double latitude = location.getLatitude();
        //经度
        double longitude = location.getLongitude();
        location(latitude, longitude);
        getAddressFromLatitudeAndLongitude(latitude, longitude);
    }

    /**
     * Gets address.
     *
     * @param latitude  the latitude
     * @param longitude the longitude
     */
    public void getAddressFromLatitudeAndLongitude(double latitude, double longitude) {
        //Geocoder通过经纬度获取具体信息
        Geocoder gc = new Geocoder(mContext, Locale.getDefault());
        try {
            List<Address> locationList = gc.getFromLocation(latitude, longitude, 1);
            if (locationList != null) {
                Address address = locationList.get(0);
                address(address);
            }
        } catch (IOException e) {
            error("获取详细地址详细错误");
            e.printStackTrace();
        }
    }


    /**
     * Show location.
     *
     * @param location the location
     */
    private void showLocation(Location location) {
        if (location == null) {
            getLocationNow();
        } else {
            currentCount = 0;
            getAddress(location);
        }
    }

    /**
     * 获取定位回调
     *
     * @return the location listener
     */
    private LocationListener getLocationListener() {
        if (locationListener == null) {
            locationListener = new LocationListener() {

                @Override
                public void onProviderEnabled(@NonNull String provider) {
                    statue(provider, true);
                }

                @Override
                public void onProviderDisabled(@NonNull String provider) {
                    statue(provider, false);
                }

                @Override
                public void onLocationChanged(@NonNull Location location) {
                    getAddress(location);
                }
            };
        }
        return locationListener;
    }


    /**
     * 通过网络获取位置信息
     */
    private void getLngAndLatWithNetwork() {
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            error("请申请定位权限");
            return;
        }
        locationProvider = LocationManager.NETWORK_PROVIDER;
        Location location = locationManager.getLastKnownLocation(locationProvider);
        showLocation(location);
    }

    /**
     * Error.
     *
     * @param error the error
     */
    private void error(String error) {
        if (addressCallback != null) {
            addressCallback.onError(error);
        }
    }

    /**
     * Address.
     *
     * @param address the address
     */
    private void address(Address address) {
        if (addressCallback != null) {
            addressCallback.onGetAddress(address);
        }
    }

    /**
     * Location.
     *
     * @param lat the lat
     * @param lng the lng
     */
    private void location(double lat, double lng) {
        if (addressCallback != null) {
            addressCallback.onGetLocation(lat, lng);
        }
    }

    /**
     * Statue.
     *
     * @param msg the msg
     */
    private void statue(String msg, boolean isEnable) {
        if (addressCallback != null) {
            addressCallback.statue(msg, isEnable);
        }
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

        /**
         * Statue.
         *
         * @param provider the provider
         * @param isEnable the is enable
         */
        void statue(String provider, boolean isEnable);

        /**
         * On error.
         *
         * @param error the error
         */
        void onError(String error);
    }

}
