package org.techtown.tastealarm;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraAnimation;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.UiSettings;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.util.FusedLocationSource;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeActivity extends Fragment implements OnMapReadyCallback {
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;
    private List<Restaurant> mList = new ArrayList<Restaurant>();
    private HomeAdapter adapter;
    private NaverMap naverMap;
    private FusedLocationSource locationSource;
    private SearchView searchView;
    LocationListener locationListener;
    private int beforePosition;
    private LatLng currLoc;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        searchView = view.findViewById(R.id.home_searchview);

        FragmentManager fm = getChildFragmentManager();
        MapFragment mapFragment = (MapFragment) fm.findFragmentById(R.id.home_naverMap);
        if(mapFragment == null) {
            mapFragment = MapFragment.newInstance();
            fm.beginTransaction().add(R.id.home_naverMap, mapFragment).commit();
        }
        mapFragment.getMapAsync(this);

        locationSource = new FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ServerURL.SERVER_URL)
                .addConverterFactory(new NullOnEmptyConverterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

        Call<List<Restaurant>> call = retrofitAPI.getResList("getResList");
        call.enqueue(new Callback<List<Restaurant>>() {
            @Override
            public void onResponse(Call<List<Restaurant>> call, Response<List<Restaurant>> response) {
                if(response.isSuccessful()) {
                    List<Restaurant> result = response.body();
                    if (mList.size() != result.size()) {
                        for (int i = 0; i < result.size(); i++) {
                            mList.add(new Restaurant(result.get(i).getId(), result.get(i).getName(), result.get(i).getAddress(),
                                    result.get(i).getCategory(), result.get(i).getPicture(), result.get(i).getLatitude(),
                                    result.get(i).getLongitude()));
                            adapter.notifyDataSetChanged();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Restaurant>> call, Throwable t) {
                Log.d("HomeActivity", "Fail" + t.getMessage());
            }
        });

        RecyclerView recyclerView = view.findViewById(R.id.home_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new HomeAdapter(mList);
        recyclerView.setAdapter(adapter);

        Marker marker = new Marker();

        adapter.setOnItemClickListener(new HomeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Log.d("ClickItem", mList.get(position).getName());
                int temp = position;
                double lat = mList.get(position).getLatitude();
                double lng = mList.get(position).getLongitude();
                double beforeLat = mList.get(beforePosition).getLatitude();
                double beforeLng = mList.get(beforePosition).getLongitude();
                CameraUpdate cameraUpdate = CameraUpdate.scrollAndZoomTo(new LatLng(lat, lng), 18)
                        .animate(CameraAnimation.Easing, 2000);
                naverMap.moveCamera(cameraUpdate);
                if(beforePosition == position) {
                    lat = beforeLat;
                    lng = beforeLng;
                }
                marker.setPosition(new LatLng(lat, lng));
                marker.setMap(naverMap);

                beforePosition = temp;
            }
        });

        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), HomeSearchActivity.class);
                intent.putExtra("list", (Serializable) mList);
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        this.naverMap = naverMap;
        naverMap.setLocationSource(locationSource);
        naverMap.setLocationTrackingMode(LocationTrackingMode.Follow);
        UiSettings uiSettings = naverMap.getUiSettings();
        uiSettings.setLocationButtonEnabled(true);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                double currLat = location.getLatitude();
                double currLng = location.getLongitude();

                currLoc = new LatLng(currLat, currLng);

                Log.d("onLocationChanged", String.valueOf(currLoc));

                for (int i = 0; i < mList.size(); i++) {
                    double resLat = mList.get(i).getLatitude();
                    double resLng = mList.get(i).getLongitude();
                    double distance = distance(currLat, currLng, resLat, resLng);
                    Log.d("Distance", String.valueOf(distance));
                    if(distance <= 15.0) {
                        Intent serviceIntent = new Intent(getContext(), TasteAlarmService.class);
                        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            getContext().startForegroundService(serviceIntent);
                        } else {
                            getContext().startService(serviceIntent);
                        }
                    }
                }
            }
        };

        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        if(ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    100);

            return;
        }

        String locationProvider;
        locationProvider = LocationManager.GPS_PROVIDER;
        locationManager.requestLocationUpdates(locationProvider, 1, 1, locationListener);

        locationProvider = LocationManager.NETWORK_PROVIDER;
        locationManager.requestLocationUpdates(locationProvider, 1, 1 , locationListener);

    }

    public static double distance(double lat1, double lng1, double lat2, double lng2) {
        double theta = lng1 - lng2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));

        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;

        dist = dist * 1609.344;

        return dist;
    }

    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }
}
