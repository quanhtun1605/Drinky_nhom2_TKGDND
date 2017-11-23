package com.example.minhd.drinky.Fragment;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v13.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.minhd.drinky.Activity.MainActivity;
import com.example.minhd.drinky.Activity.ProfileActivity;
import com.example.minhd.drinky.Activity.Store;
import com.example.minhd.drinky.Activity.TestActivity;
import com.example.minhd.drinky.Modules.DirectionFinder;
import com.example.minhd.drinky.Modules.DirectionFinderListener;
import com.example.minhd.drinky.Modules.Route;
import com.example.minhd.drinky.R;
import com.example.minhd.drinky.TabMessage;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabReselectListener;
import com.roughike.bottombar.OnTabSelectListener;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends FragmentActivity implements
        OnMapReadyCallback,
        GoogleMap.OnMyLocationChangeListener, GoogleMap.OnInfoWindowClickListener,
        GoogleMap.InfoWindowAdapter, View.OnClickListener, DirectionFinderListener {

    private static final int MY_PERMISSION = 0;
    private static final String TAG = MapFragment.class.getSimpleName();
    private ImageView ivDirec;
    public static GoogleMap mGoogleMap;
    public static Geocoder geocoder;
    private boolean isFirstChangeLocation;
    public static Marker marker;
    private List<Marker> originMarkers = new ArrayList<>();
    private List<Marker> destinationMarkers = new ArrayList<>();
    private List<Polyline> polylinePaths = new ArrayList<>();
    private ProgressDialog progressDialog;
    private String [] namePlace = {"Toco Toco", "Dingtea", "KFC", "Lotte", "Bobapop", "Uncle Tea", "Mr Good Tea",
            "Chevi", "Feeling Tea"};

    private String [] latPlace = {"21.037390", "21.036365", "21.040738", "21.036806", "21.032068",
            "21.037816", "21.035170","21.035971", "21.039571" };

    private String [] lngPlace = {"105.769635", "105.784202", "105.787694", "105.787882", "105.780383",
            "105.776381", "105.781009", "105.779475", "105.780357" };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
//        setStatusBarTranslucent(true);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        geocoder = new Geocoder(this, Locale.getDefault());
        mapFragment.getMapAsync(this);
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("Bundel");
        if (bundle != null) {
            String origin = bundle.getString("origin");
            String des = bundle.getString("destination");
            if (origin != null && des != null) {
                sendRequest(origin, des);
            }
        }
        BottomBar bottomBar = findViewById(R.id.bottomBarr);
        bottomBar.setDefaultTab(R.id.tab_nav);
        bottomBar.setActiveTabColor(Color.parseColor("#fc6565"));

        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
//                messageView.setText(TabMessage.get(tabId, false) +"");
                if (TabMessage.getPos(tabId, false) == 2) {

                }

                if (TabMessage.getPos(tabId, true) == 1) {
                    Intent intent = new Intent(getBaseContext(), MainActivity.class);
                    startActivity(intent);

                    onBackPressed();
                }

                if (TabMessage.getPos(tabId, false) == 3) {

                    Intent intent1 = new Intent(getBaseContext(), TestActivity.class);
                    startActivity(intent1);
                }
                if (TabMessage.getPos(tabId, false) == 4) {
                    Intent intent2 = new Intent(getBaseContext(), ProfileActivity.class);
                    startActivity(intent2);

                }

            }
        });

        bottomBar.setOnTabReselectListener(new OnTabReselectListener() {
            @Override
            public void onTabReSelected(@IdRes int tabId) {
            }
        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        initGoogleMap(mGoogleMap);
    }

    public void initGoogleMap(GoogleMap mGoogleMap) {

        MapFragment.mGoogleMap = mGoogleMap;
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);


        //set up UI
        mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
        mGoogleMap.getUiSettings().setMyLocationButtonEnabled(true);
        mGoogleMap.getUiSettings().setZoomGesturesEnabled(true);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions((Activity) this, new String[]{
                    android.Manifest.permission.ACCESS_COARSE_LOCATION,
                    android.Manifest.permission.ACCESS_NETWORK_STATE,
                    android.Manifest.permission.SYSTEM_ALERT_WINDOW,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE

            }, MY_PERMISSION);

            new AlertDialog.Builder(this)
                    .setTitle("Confirm Permission for Application")
                    .setMessage("Please Select Permissions and Enable Location")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent();
                            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package",
                                    getPackageName(), null);
                            intent.setData(uri);
                            startActivityForResult(intent, 101);
                        }
                    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    return;

                }
            }).create().show();

            return;

        } else {
            initMyLocation();
            initPlaces();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 101) {
            //permion
            int checkPermisonLocation =
                    ActivityCompat.checkSelfPermission(this,
                            android.Manifest.permission.ACCESS_FINE_LOCATION);

            if (checkPermisonLocation == PackageManager.PERMISSION_GRANTED) {
                initMyLocation();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for (int per : grantResults) {
            if (per == PackageManager.PERMISSION_DENIED) {
                return;
            }
        }

        //nguoi dung dong y het
        initMyLocation();

    }

    private void initMyLocation() {
        mGoogleMap.setOnMyLocationChangeListener(this);
//        //custom windown adpater: content marker
////        googleMap.setOnInfoWindowClickListener(this);
////        googleMap.setInfoWindowAdapter(this);
        mGoogleMap.setMyLocationEnabled(true);
        checkOpenLocation();

    }

    @Override
    public void onMyLocationChange(Location location) {
        Log.d("Adres", "location lat: " + location.getLatitude());
        Log.d("Adres", "location long: " + location.getLongitude());
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());


        if (!isFirstChangeLocation) {
            isFirstChangeLocation = true;
            //co chuc nang di chuyen den vi tri position
            CameraPosition cameraPosition =
                    new CameraPosition(latLng, 15, 0, 0);
            //dua camera position vao google map
            mGoogleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
//            MarkerOptions options = new MarkerOptions();
//            options.
//                    icon(BitmapDescriptorFactory.
//                            defaultMarker(BitmapDescriptorFactory.HUE_RED));
//            options.position(latLng);
//            options.title("My location");
//            options.snippet(getLocation(latLng));
//            marker = mGoogleMap.addMarker(options);
//            marker.showInfoWindow();

        } else {
//            marker.setTitle("My location");
//            marker.setSnippet(getLocation(latLng));
//            marker.setPosition(latLng);

        }

    }


    private void initPlaces() {
        for (int i = 0; i < namePlace.length; i++) {
            int height = 48;
            int width = 48;
            BitmapDrawable bitmapdraw=(BitmapDrawable)getResources().getDrawable(R.drawable.cup);
            Bitmap b=bitmapdraw.getBitmap();
            Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);
            BitmapDescriptor icon = BitmapDescriptorFactory.fromBitmap(smallMarker) ;
            LatLng latLng = new LatLng(Double.parseDouble(latPlace[i]), Double.parseDouble(lngPlace[i]));
            MarkerOptions option = new MarkerOptions();
            option.title(namePlace[i]);
            option.snippet(getLocation(latLng));
            option.position(latLng);
            option.icon(icon);
            Marker currentMarker = mGoogleMap.addMarker(option);
            currentMarker.showInfoWindow();
            mGoogleMap.setOnInfoWindowClickListener(this);
            mGoogleMap.setInfoWindowAdapter(new MyInfoWindowAdapter());
        }
    }


    protected void setStatusBarTranslucent(boolean makeTranslucent) {
        if (makeTranslucent) {
            this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        } else {
            this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    private String getLocation(LatLng latLng) {
        try {
            List<Address> addresses =
                    geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
            if (addresses == null || addresses.size() == 0) {
                return null;
            }
            String result = "";
            int maxLine = addresses.get(0).getMaxAddressLineIndex();
            result = addresses.get(0).getAddressLine(0);
            for (int i = 1; i < maxLine; i++) {
                result += ", " + addresses.get(0).getAddressLine(i);
            }
            result += ", " + addresses.get(0).getCountryName();
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

//check open loaction

    public boolean checkOpenLocation() {
        LocationManager lm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;

        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
        }

        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {
        }

        if (!gps_enabled && !network_enabled) {
            // notify user
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setMessage("Open location");
            dialog.setPositiveButton("Open", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    // TODO Auto-generated method stub
                    Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivityForResult(myIntent, 102);
                    //get gps
                }
            });
            dialog.setNegativeButton("Close", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    // TODO Auto-generated method stub

                }
            });
            dialog.show();
            return false;
        } else {
            return true;
        }
    }


    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
       return null ;
    }

    class MyInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

        private final View myContentsView;

        MyInfoWindowAdapter(){
            myContentsView = getLayoutInflater().inflate(R.layout.layout_info_window, null);
        }

        @Override
        public View getInfoContents(Marker marker) {
            View myContentsView = getLayoutInflater().inflate(R.layout.layout_info_window, null);
            TextView tvName = myContentsView.findViewById(R.id.namePlace);
            TextView tvLoc = myContentsView.findViewById(R.id.LocPlace);
            ImageView img = myContentsView.findViewById(R.id.img_product);

            tvName.setText(marker.getTitle());
            tvLoc.setText(getLocation(marker.getPosition()));

            return myContentsView;
        }

        @Override
        public View getInfoWindow(Marker marker) {
            // TODO Auto-generated method stub
            return null;
        }

    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        this.finish();
        Intent intent = new Intent(this, Store.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            default:
        }
    }

    private void sendRequest(String origin, String destination) {

        if (origin.isEmpty()) {
            Toast.makeText(this, "Please enter origin address!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (destination.isEmpty()) {
            Toast.makeText(this, "Please enter destination address!", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            new DirectionFinder(this, origin, destination).execute();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDirectionFinderStart() {
        progressDialog = ProgressDialog.show(this, "Please wait.",
                "Finding direction..!", true);

        if (originMarkers != null) {
            for (Marker marker : originMarkers) {
                marker.remove();
            }
        }

        if (destinationMarkers != null) {
            for (Marker marker : destinationMarkers) {
                marker.remove();
            }
        }

        if (polylinePaths != null) {
            for (Polyline polyline : polylinePaths) {
                polyline.remove();
            }
        }
    }


    @Override
    public void onDirectionFinderSuccess(List<Route> routes) {
        progressDialog.dismiss();
        polylinePaths = new ArrayList<>();
        originMarkers = new ArrayList<>();
        destinationMarkers = new ArrayList<>();

        for (Route route : routes) {
            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(route.startLocation, 16));
//            ((TextView) findViewById(R.id.tvDuration)).setText(route.duration.text);
//            ((TextView) findViewById(R.id.tvDistance)).setText(route.distance.text);

            originMarkers.add(mGoogleMap.addMarker(new MarkerOptions()
                    .title(route.startAddress)
                    .position(route.startLocation)));
            destinationMarkers.add(mGoogleMap.addMarker(new MarkerOptions()
                    .title(route.endAddress)
                    .position(route.endLocation)));

            PolylineOptions polylineOptions = new PolylineOptions().
                    geodesic(true).
                    color(Color.BLUE).
                    width(10);

            for (int i = 0; i < route.points.size(); i++)
                polylineOptions.add(route.points.get(i));

            polylinePaths.add(mGoogleMap.addPolyline(polylineOptions));
        }
    }

}
        