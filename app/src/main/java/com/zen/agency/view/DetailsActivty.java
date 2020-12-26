package com.zen.agency.view;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.zen.agency.R;
import com.zen.agency.entity.AgencyConstant;
import com.zen.agency.room.Agency;
import com.zen.agency.viewmodel.AgencyViewModel;

import java.util.Date;
import java.util.List;

public class DetailsActivty extends AppCompatActivity implements OnMapReadyCallback {
    private AppCompatTextView title;
    private AppCompatTextView tv_price;
    private AppCompatTextView tv_describe;
    private AppCompatTextView tv_address;
    private AppCompatTextView tv_type;
    private AppCompatTextView tv_surface;
    private AppCompatTextView tv_sold;
    private AppCompatTextView tv_room;
    //    private AppCompatImageView imageView;
    private GoogleMap mMap;
    private AgencyViewModel agencyViewModel;
    private AppCompatButton simulator;
    private AppCompatButton convert;
    Agency current;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        ActionBar actionBar = getActionBar();
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setContentView(R.layout.activity_details);
        simulator = findViewById(R.id.simulator);
        convert = findViewById(R.id.convert);
        tv_price = findViewById(R.id.tv_price);
        tv_describe = findViewById(R.id.tv_describe);
        tv_address = findViewById(R.id.tv_address);
        tv_type = findViewById(R.id.tv_type);
        tv_surface = findViewById(R.id.tv_surface);
        tv_sold = findViewById(R.id.tv_sold);
        tv_room = findViewById(R.id.tv_room);
        title = findViewById(R.id.title_details);
        current = (Agency) getIntent().getSerializableExtra("house");
        String housePrice=current.getPrice();
        String ctitle=current.getTitle();
        if(!TextUtils.isEmpty(housePrice)){
            tv_price.setText("price is €"+housePrice);
        }
        tv_surface.setText("Surface Area is " +current.getSurface() +"m²");
        tv_room.setText(current.getRoomNum() + " Rooms");
        tv_describe.setText(current.getDescription());
        tv_address.setText(current.getAddress());
        tv_type.setText(current.getType());

        title.setText(ctitle);
        tv_sold.setText(current.isSold()?"this house is sold":"NOT SOLD");
        simulator.setOnClickListener(v -> {
            Intent intent = new Intent(DetailsActivty.this, SimulatorActivity.class);

            if(!TextUtils.isEmpty(housePrice)){
                intent.putExtra("price", housePrice);
            }
            startActivity(intent);
        });
        convert.setOnClickListener(v -> {
            Intent intent = new Intent(DetailsActivty.this, ConverterActivity.class);
            if(!TextUtils.isEmpty(housePrice)){
                intent.putExtra("price", housePrice);
            }
            startActivity(intent);
        });
//        CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(this);
//        circularProgressDrawable.setStrokeWidth(100f);
//        circularProgressDrawable.setStyle(CircularProgressDrawable.LARGE);
//        circularProgressDrawable.setCenterRadius(300f);
//        circularProgressDrawable.start();
//        Glide.with(getApplicationContext())
//                .load(images)
//                .placeholder(circularProgressDrawable)
//                .into(imageView);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        agencyViewModel = ViewModelProviders.of(this).get(AgencyViewModel.class);
        agencyViewModel.getAllAgency().observe(this, new Observer<List<Agency>>() {
            @Override
            public void onChanged(final List<Agency> agencies) {
                //update RecyclerView
                //   Toast.makeText(MainActivity.this, "OnChanged", Toast.LENGTH_SHORT).show();
                for (int i = 0; i < agencies.size(); i++) {
                    Agency agency=agencies.get(i);
                    LatLng latLng = new LatLng(agency.getLatitude(), agency.getLongitude());
                    if(mMap!=null){
                        mMap.addMarker(getMarkerOptions(agencies.get(i),agency.getTitle() + "," + agency.getType() + "," + "$" + agency.getPrice() + ",have " + agency.getRoomNum() + " rooms", latLng));
                    }
                }
            }
        });
    }

//    public boolean onOptionsItemSelected(MenuItem item) {
//        Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
//        startActivityForResult(myIntent, 0);
//        return true;
//    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(current.getLatitude(), current.getLongitude());
//        mMap.addMarker(new MarkerOptions().position(sydney).title(titles+","+type+","+"$"+price+",have "+roomNum+" rooms"));

        mMap.addMarker(getMarkerOptions(null,current.getTitle()+" "+current.getPrice()+" " ,sydney));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    public MarkerOptions getMarkerOptions(Agency agency, String name, LatLng latLng) {
        if (agency != null) {
            if (agency.isSold()) {
                return new MarkerOptions()
                        .title(name)
                        .position(latLng)
                        .snippet("");
            }else {
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
}
