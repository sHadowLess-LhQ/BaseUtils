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
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * 定位工具类
 *
 * @author sHadowLess
 */
public class LocationUtils {

    /**
     * 定位工具类实例
     */
    private volatile static LocationUtils uniqueInstance;
    /**
     * 位置管理器实例
     */
    private LocationManager locationManager;
    /**
     * 上下文
     */
    private Context mContext;
    /**
     * 获取地址信息回调
     */
    private static ArrayList<AddressCallback> addressCallbacks = new ArrayList<>();
    /**
     * 地址信息接口
     */
    private AddressCallback addressCallback;

    /**
     * 获取地址信息回调
     *
     * @return the address callback
     */
    public AddressCallback getAddressCallback() {
        return addressCallback;
    }

    /**
     * 设置地址信息接口
     *
     * @param addressCallback the address callback
     */
    public void setAddressCallback(AddressCallback addressCallback) {
        this.addressCallback = addressCallback;
        if (isInit) {
            showLocation();
        } else {
            isInit = true;
        }
    }

    /**
     * 位置实体类
     */
    private static Location location;
    /**
     * 是否初始化标识符
     */
    private boolean isInit = false;

    /**
     * 构造
     *
     * @param context the context
     */
    private LocationUtils(Context context) {
        mContext = context;
        getLocation();
    }

    /**
     * 获取定位工具类单例
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
     * 添加回调事件
     *
     * @param addressCallback the address callback
     */
    private void addAddressCallback(AddressCallback addressCallback) {
        addressCallbacks.add(addressCallback);
        if (isInit) {
            showLocation();
        }
    }

    /**
     * 移除回调事件
     *
     * @param addressCallback the address callback
     */
    public void removeAddressCallback(AddressCallback addressCallback) {
        if (addressCallbacks.contains(addressCallback)) {
            addressCallbacks.remove(addressCallback);
        }
    }

    /**
     * 清空回调事件
     */
    public void cleareAddressCallback() {
        removeLocationUpdatesListener();
        addressCallbacks.clear();
    }

    /**
     * 获取位置
     */
    public void getLocation() {
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
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            mContext.startActivity(intent);
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
        //LocationManager 每隔 5 秒钟会检测一下位置的变化情况，当移动距离超过 10 米的时候，
        // 就会调用 LocationListener 的 onLocationChanged() 方法，并把新的位置信息作为参数传入。
        locationManager.requestLocationUpdates(locationProvider, 5000, 10, locationListener);
    }


    /**
     * 显示位置
     */
    public void showLocation() {
        if (location == null) {
            getLocation();
        } else {
            //纬度
            double latitude = location.getLatitude();
            //经度
            double longitude = location.getLongitude();
            if (addressCallback != null) {
                addressCallback.onGetLocation(latitude, longitude);
            }
            getAddress(latitude, longitude);
        }
    }

    /**
     * 通过经纬度获取详细地址
     *
     * @param latitude  the latitude
     * @param longitude the longitude
     */
    public void getAddress(double latitude, double longitude) {
        //Geocoder通过经纬度获取具体信息
        Geocoder gc = new Geocoder(mContext, Locale.getDefault());
        try {
            List<Address> locationList = gc.getFromLocation(latitude, longitude, 1);
            if (locationList != null) {
                Address address = locationList.get(0);
                for (int i = 0; address.getAddressLine(i) != null; i++) {
                    String addressLine = address.getAddressLine(i);
                    //街道名称:广东省深圳市罗湖区蔡屋围一街深圳瑞吉酒店
                    Log.e("getAddress", addressLine);
                }
                if (addressCallback != null) {
                    addressCallback.onGetAddress(address);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 移除定位更新监听
     */
    private void removeLocationUpdatesListener() {
        if (locationManager != null) {
            uniqueInstance = null;
            locationManager.removeUpdates(locationListener);
        }
    }


    /**
     * 位置监听
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
            getLocation();
        }
    };

    /**
     * 通过网络获取位置信息
     */
    private void getLngAndLatWithNetwork() {
        //添加用户权限申请判断
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationManager locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 10, locationListener);
        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        showLocation();
    }

    /**
     * 位置信息接口
     */
    public interface AddressCallback {
        /**
         * 获取地址
         *
         * @param address the address
         */
        void onGetAddress(Address address);

        /**
         * 获取详细地址
         *
         * @param lat the 经度
         * @param lng the 纬度
         */
        void onGetLocation(double lat, double lng);
    }

}
