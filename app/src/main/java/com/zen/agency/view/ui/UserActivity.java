package com.zen.agency.view.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemLongClickListener;
import com.zen.agency.R;
import com.zen.agency.adapter.AgencyAdapter;
import com.zen.agency.adapter.UserAdapter;
import com.zen.agency.room.Agency;
import com.zen.agency.room.User;
import com.zen.agency.util.SharedPreferencesUtils;
import com.zen.agency.view.DetailsActivty;
import com.zen.agency.view.NaviActivity;
import com.zen.agency.view.UpdateActivity;
import com.zen.agency.viewmodel.AgencyViewModel;
import com.zen.agency.viewmodel.UserViewModel;

import java.util.ArrayList;
import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

public class UserActivity extends AppCompatActivity {
    private List<User> currentList;
    private List<User> users;
    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        final RecyclerView recyclerView = findViewById(R.id.user_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        currentList = new ArrayList<>();
        users = new ArrayList<>();

        final UserAdapter adapter = new UserAdapter(users);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {

                User user = (User) adapter.getItem(position);
                Intent intent = new Intent(UserActivity.this, NaviActivity.class);
                SharedPreferencesUtils.setParam(UserActivity.this, "userId", user.getId()+"");
                SharedPreferencesUtils.setParam(UserActivity.this, "userName", user.getUserName()+"");
                if (SharedPreferencesUtils.getParam(UserActivity.this, "userId", "") != null) {
                    startActivity(intent);
                }
            }
        });


        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        userViewModel.getAllUser().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(final List<User> users) {
                adapter.setNewData(users);
                adapter.notifyDataSetChanged();
            }
        });
        int first= (int) SharedPreferencesUtils.getParam(UserActivity.this,"first_init",0);
        if(first==0){
            SharedPreferencesUtils.setParam(UserActivity.this,"first_init",1);
            userViewModel.deleteAllNote();
            for (int i = 0; i < 10; i++) {
                User user = new User();
                user.setUserName("user" + i);
                userViewModel.insert(user);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
}