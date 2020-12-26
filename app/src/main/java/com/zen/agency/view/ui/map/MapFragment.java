package com.zen.agency.view.ui.map;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.zen.agency.R;
import com.zen.agency.room.Agency;
import com.zen.agency.util.PermissionUtils;
import com.zen.agency.util.SharedPreferencesUtils;
import com.zen.agency.viewmodel.AgencyViewModel;

import java.util.Date;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;


public class MapFragment extends Fragment implements OnMapReadyCallback, EasyPermissions.PermissionCallbacks, GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener,
        ActivityCompat.OnRequestPermissionsResultCallback {

    private static final int RC_LOCATION = 2323222;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private MapViewModel mapViewModel;
    private GoogleMap mMap;
    private AgencyViewModel agencyViewModel;
    private boolean permissionDenied = false;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        mapViewModel =
                new ViewModelProvider(this).get(MapViewModel.class);
        View root = inflater.inflate(R.layout.fragment_map
                , container, false);

//        final TextView textView = root.findViewById(R.id.text_dashboard);
//        mapViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        agencyViewModel = ViewModelProviders.of(this).get(AgencyViewModel.class);
        String userId = (String) SharedPreferencesUtils.getParam(getActivity(), "userId", "");
        agencyViewModel.getAllAgency(userId).observe(getActivity(), new Observer<List<Agency>>() {
            @Override
            public void onChanged(final List<Agency> agencies) {
                //update RecyclerView
                //   Toast.makeText(MainActivity.this, "OnChanged", Toast.LENGTH_SHORT).show();
                for (int i = 0; i < agencies.size(); i++) {
                    Agency agency = agencies.get(i);
                    LatLng latLng = new LatLng(agency.getLatitude(), agency.getLongitude());
                    if (mMap != null) {
                        mMap.addMarker(getMarkerOptions(agencies.get(i), agency.getTitle() + "," + agency.getType() + "," + "$" + agency.getPrice() + ",have " + agency.getRoomNum() + " rooms", latLng));
                    }
                    if(i==agencies.size()-1){
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                    }
                }
            }
        });

    }

    @AfterPermissionGranted(RC_LOCATION)
    private void methodRequiresTwoPermission() {
        String[] perms = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
        if (EasyPermissions.hasPermissions(getActivity(), perms)) {

        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, "need location permission",
                    RC_LOCATION, perms);
        }
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationClickListener(this);
        enableMyLocation();
        // Add a marker in Sydney and move the camera

    }

    public MarkerOptions getMarkerOptions(Agency agency, String name, LatLng latLng) {
        if (agency != null) {
            if (agency.isSold()) {
                return new MarkerOptions()
                        .title(name)
                        .position(latLng)
                        .snippet("");
            } else {
                return new MarkerOptions()
                        .title(name)
                        .position(latLng)
                        .snippet("")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
            }
        }
        return new MarkerOptions()
                .title(name)
                .position(latLng)
                .snippet("")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }

    /**
     * Enables the My Location layer if the fine location permission has been granted.
     */
    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            if (mMap != null) {
                mMap.setMyLocationEnabled(true);
            }
        } else {
            // Permission to access the location is missing. Show rationale and request permission
            PermissionUtils.requestPermission((AppCompatActivity) getActivity(), LOCATION_PERMISSION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION, true);
        }
    }

    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(getActivity(), "MyLocation button clicked", Toast.LENGTH_SHORT).show();
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        Toast.makeText(getActivity(), "Current location:\n" + location, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return;
        }

        if (PermissionUtils.isPermissionGranted(permissions, grantResults, Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Enable the my location layer if the permission has been granted.
            enableMyLocation();
        } else {
            // Permission was denied. Display an error message
            // Display the missing permission error dialog when the fragments resume.
            permissionDenied = true;
        }
    }


}