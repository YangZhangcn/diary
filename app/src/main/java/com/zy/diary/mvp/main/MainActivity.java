package com.zy.diary.mvp.main;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresPermission;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.zy.diary.R;
import com.zy.diary.mvp.base.BaseActivity;


import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements MainView{

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.rv_main)
    RecyclerView rvMain;

    private String[] permissions = new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};

    @Inject
    MainPresenter<MainView> mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUnbinder(ButterKnife.bind(this));
        getActivityComponent().inject(this);
        mPresenter.onAttach(this);
        setSupportActionBar(toolbar);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                        ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissionsSafely(permissions, 200);
                } else {
                    getLocalAddress();
                }
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            Snackbar.make(fab, "权限缺失", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show();
            onError("权限缺失");
            return;
        }
        getLocalAddress();
    }

    private void getLocalAddress() {
        final LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        String bestProvider = lm.getBestProvider(getCriteria(), false);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            Snackbar.make(fab, "权限缺失", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show();
            onError("权限缺失");
            return;
        }
        lm.requestLocationUpdates(bestProvider, 5000, 8, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                String addressByGeoPoint = getAddressByGeoPoint(location.getLatitude(), location.getLongitude());
//                Snackbar.make(fab, addressByGeoPoint, Snackbar.LENGTH_LONG).setAction("Action",null).show();
                mPresenter.getLocation(addressByGeoPoint);
                onError(addressByGeoPoint);
                lm.removeUpdates(this);
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        });
    }

    //从经纬度取得Address
    public String getAddressByGeoPoint(double Latitude, double Longitude) {
        String strReturn = "";
        try {
	      /* 创建Geocoder对象，用于获得指定地点的地址 */
            Geocoder gc = new Geocoder(MainActivity.this, Locale.getDefault());

	      /* 自经纬度取得地址（可能有多行）*/
            List<Address> lstAddress = gc.getFromLocation(Latitude, Longitude, 1);
            StringBuilder sb = new StringBuilder();

	      /* 判断地址是否为多行 */
            if (lstAddress.size() > 0) {
                Address adsLocation = lstAddress.get(0);
                sb.append(adsLocation.getLocality());  //当前经纬度所在的城市（市）
            }
            strReturn = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strReturn;
    }

    private Criteria getCriteria() {
        // TODO Auto-generated method stub
        Criteria c = new Criteria();
        c.setAccuracy(Criteria.ACCURACY_COARSE);
        c.setSpeedRequired(false);
        c.setCostAllowed(false);
        c.setBearingRequired(false);
        c.setAltitudeRequired(false);
        c.setPowerRequirement(Criteria.POWER_LOW);
        return c;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
