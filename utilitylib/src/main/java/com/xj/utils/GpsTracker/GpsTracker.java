package com.xj.utils.GpsTracker;

import android.Manifest;
import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.xj.utils.utils.DebugLogs;
import com.xujian.utilitylib.R;

import java.util.List;

/**
 * Created by xujian on 15/9/8.
 * GPS定位以及转换为地理位置名称
 */
public class GpsTracker extends Service implements LocationListener {
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;
    private Context mcontext;
    //标记GSP状态
    private boolean isGPSEnabled = false;
    //标记网络状态
    private boolean isNetworkEnabled = false;
    //标记能否获取位置信息，
    private boolean isGetLocation = false;
    private Location location;//位置
    private double latitude;//纬度
    private double longitude;//纬度
    // 更新最多距离 米为单位
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 米
    // 更新最多时间 毫秒为单位
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60; // 1 分钟
    // 声明一个 Location Manager
    protected LocationManager locationManager;

    public GpsTracker(Context context) {
        this.mcontext = context;
        getLocation();
    }

    public Location getLocation() {
        try {
            locationManager = (LocationManager) mcontext.getSystemService(LOCATION_SERVICE);
            //获取GPS状态
            boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            //获取网络状态
            boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            if (isGPSEnabled || isNetworkEnabled) {
                this.isGetLocation = true;
                //首先通过网络获取地理位置
                if (isNetworkEnabled) {
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    if (locationManager != null) {
                        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                        }
                    }
                }
                if (location == null) {//当网络获取不到定位时候获取GPS定位信息
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(mcontext,mcontext.getString(R.string.gps_noPermission),Toast.LENGTH_LONG).show();
                        return null;
                    }
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    if (locationManager != null) {
                        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        DebugLogs.d("----location---->"+location);
        return location;
    }

    public String getStrAddress(){
        Geocoder geocoder = new Geocoder(mcontext);
        try{
            // 2：通过经纬度来获取地址，由于地址可能有多个，这和经纬度的精确度有关，本例限制最大返回数为5
            List<Address> list = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 5);
            if (list != null){
                Log.e("--------->",list.get(0).getCountryName());//中国
                Log.e("--------->",list.get(0).getAdminArea());//浙江省
                Log.e("--------->",list.get(0).getLocality());//城市
                Log.e("--------->",list.get(0).toString());
                return list.get(0).getAddressLine(0);//返回当前定位附近地址的第一个；
            }
            return "";
        }catch(Exception e){
            e.printStackTrace();
            return "";
        }
    }

    //获取当前的城市
    public String getCity(){
        Geocoder geocoder = new Geocoder(mcontext);
        try{
            // 2：通过经纬度来获取地址，由于地址可能有多个，这和经纬度的精确度有关，本例限制最大返回数为5
            List<Address> list = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 5);
            return list.get(0).getLocality();
        }catch(Exception e){
            e.printStackTrace();
            return "";
        }
    }

    //获取当前的省
    public String getAddress(){
        Geocoder geocoder = new Geocoder(mcontext);
        try{
            // 2：通过经纬度来获取地址，由于地址可能有多个，这和经纬度的精确度有关，本例限制最大返回数为5
            List<Address> list = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 5);
            return list.get(0).getAdminArea();
        }catch(Exception e){
            e.printStackTrace();
            return "";
        }
    }

    //获取
    public Address getCanCity(){
        Geocoder geocoder = new Geocoder(mcontext);
        try{
            List<Address> list = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 5);
            return list.get(0);
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public List<Address> getAddMessage(){
        Geocoder geocoder = new Geocoder(mcontext);
        try{
            return geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 5);
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取纬度
     **/
    public double getLatitude(){
        if(location != null){
            latitude = location.getLatitude();
        }
        return latitude;
    }

    /**
     * 获取经度
     **/
    public double getLongitude(){
        if(location != null){
            longitude = location.getLongitude();
        }
        return longitude;
    }

    /**
     * 检查GPS 网络是否可用
     * @return boolean
     * */
    public boolean isGetLocation() {
        return this.isGetLocation;
    }


    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
