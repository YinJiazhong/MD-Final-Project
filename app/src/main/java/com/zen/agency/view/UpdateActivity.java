package com.zen.agency.view;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.zen.agency.R;
import com.zen.agency.entity.AgencyConstant;
import com.zen.agency.room.Agency;
import com.zen.agency.util.SharedPreferencesUtils;
import com.zen.agency.viewmodel.AgencyViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public class UpdateActivity extends AppCompatActivity {
    private AgencyViewModel agencyViewModel;
    private Agency current;

    AppCompatEditText etTitle;
    AppCompatEditText etPrice;
    AppCompatEditText etSurface;
    AppCompatEditText etRoomNum;
    AppCompatEditText etDesc;
    AppCompatEditText etAddress;
    RadioButton radioHouse;
    RadioButton radioFlat;
    RadioButton radioOffice;
    RadioButton radioSold;
    RadioButton radioNotSold;
    AppCompatTextView tvLa;
    AppCompatTextView tvLo;
    AppCompatTextView tvSave;
    AppCompatTextView updateTitle;
    AppCompatImageView ivBack;
    boolean isUpdate = true;
    private Geocoder geocoder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        geocoder = new Geocoder(this);
        tvSave = findViewById(R.id.tv_save);
        updateTitle = findViewById(R.id.update_title);
        ivBack = findViewById(R.id.iv_back);
        etTitle = findViewById(R.id.et_title);
        etPrice = findViewById(R.id.et_price);
        etSurface = findViewById(R.id.et_surface);
        etRoomNum = findViewById(R.id.et_house_num);
        etDesc = findViewById(R.id.et_desc);
        etAddress = findViewById(R.id.et_address);
        tvLa = findViewById(R.id.tv_latitude);
        tvLo = findViewById(R.id.tv_longitude);
        radioHouse = findViewById(R.id.radio_house);
        radioFlat = findViewById(R.id.radio_flat);
        radioOffice = findViewById(R.id.radio_office);
        radioSold = findViewById(R.id.radio_sold);
        radioNotSold = findViewById(R.id.radio_not_sold);
        tvLa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        try {
                            List<Address> addrs = geocoder.getFromLocationName(etAddress.getText().toString(), 1);
                            if (addrs != null && addrs.size() > 0) {
                                int latE6 = (int) (addrs.get(0).getLatitude() * 1E6);
                                int lngE6 = (int) (addrs.get(0).getLongitude() * 1E6);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        tvLa.setText(latE6 + "");
                                        tvLo.setText(lngE6 + "");
                                    }
                                });
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });
//        etAddress.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                try {
//                    List<Address> addrs = geocoder.getFromLocationName(etAddress.getText().toString(), 3);
//                    if (addrs != null && addrs.size() > 0) {
//                        int latE6 = (int) (addrs.get(0).getLatitude() * 1E6);
//                        int lngE6 = (int) (addrs.get(0).getLongitude() * 1E6);
//                        etLa.setText(latE6+"");
//                        etLo.setText(lngE6+"");
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                return false;
//            }
//        });
        current = (Agency) getIntent().getSerializableExtra("house");
        if (current != null) {
            updateTitle.setText("UPDATE THIS INFO");
            Log.d("current", current.toString() + "---");
            etTitle.setText(current.getTitle());
            etPrice.setText(current.getPrice());
            etSurface.setText(current.getSurface());
            etRoomNum.setText(current.getRoomNum() + "");
            etDesc.setText(current.getDescription());
            etAddress.setText(current.getAddress());
            if (current.getType().equals("office")) {
                radioOffice.setChecked(true);
            }
            if (current.getType().equals("flat")) {
                radioFlat.setChecked(true);
            }
            if (current.getType().equals("house")) {
                radioHouse.setChecked(true);
            }
            if (current.isSold()) {
                radioSold.setChecked(true);
            }else {
                radioNotSold.setChecked(true);
            }

        } else {
            updateTitle.setText("ADD THIS INFO");
            isUpdate = false;
            current = new Agency();

        }

        agencyViewModel = ViewModelProviders.of(this).get(AgencyViewModel.class);

        tvSave.setOnClickListener(v -> {
            current.setTitle(etTitle.getText().toString());
            current.setPrice(etPrice.getText().toString());
            current.setSurface(etSurface.getText().toString());
            current.setRoomNum(Integer.parseInt(etRoomNum.getText().toString() + ""));
            current.setDescription(etDesc.getText().toString());
            current.setAddress(etAddress.getText().toString());
            if(radioSold.isChecked()){
                current.setSold(true);
            }else if(radioNotSold.isChecked()){
                current.setSold(false);
            }
            current.setLatitude(!TextUtils.isEmpty(tvLa.getText().toString())?Long.parseLong(tvLa.getText().toString()):-5);
            current.setLongitude(!TextUtils.isEmpty(tvLo.getText().toString())?Long.parseLong(tvLo.getText().toString()):141);
            if (isUpdate) {
                if (current != null) {
                    current.setUpdateTime(new Date());
                    agencyViewModel.update(current);
                    Toast.makeText(UpdateActivity.this, "update success", Toast.LENGTH_LONG).show();
                }
            } else {
                current.setCreateTime(new Date());
                String userName= (String) SharedPreferencesUtils.getParam(UpdateActivity.this,"userName","");
                current.setCreateName(userName+"");
                String userId= (String) SharedPreferencesUtils.getParam(UpdateActivity.this,"userId","");
                current.setUserId(userId);
                agencyViewModel.insert(current);
                Toast.makeText(UpdateActivity.this, "ADD success", Toast.LENGTH_LONG).show();
            }
            finish();
        });
        ivBack.setOnClickListener(v -> {
            finish();
        });
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.radio_flat:
                if (checked)
                    current.setType("flat");
                break;
            case R.id.radio_house:
                if (checked)
                    current.setType("house");
                break;
            case R.id.radio_office:
                if (checked)
                    current.setType("office");
                break;
        }
    }
}