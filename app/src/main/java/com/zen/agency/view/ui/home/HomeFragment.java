package com.zen.agency.view.ui.home;

import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemLongClickListener;
import com.chad.library.adapter.base.listener.OnItemSwipeListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.zen.agency.R;
import com.zen.agency.adapter.AgencyAdapter;
import com.zen.agency.entity.AgencyConstant;
import com.zen.agency.network.http.Emp;
import com.zen.agency.room.Agency;
import com.zen.agency.util.SharedPreferencesUtils;
import com.zen.agency.view.DetailsActivty;
import com.zen.agency.view.UpdateActivity;
import com.zen.agency.viewmodel.AgencyViewModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class HomeFragment extends Fragment {
    private AgencyViewModel agencyViewModel;
    private List<Agency> currentList;
    private List<Agency> agencies;
    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
//        final TextView textView = root.findViewById(R.id.text_home);
//        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });

        FloatingActionButton fab = root.findViewById(R.id.home_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), UpdateActivity.class);
                getActivity().startActivity(intent);
            }
        });
        final RecyclerView recyclerView = root.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        currentList = new ArrayList<>();
        agencies = new ArrayList<>();

        final AgencyAdapter adapter = new AgencyAdapter(agencies);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new OnItemChildClickListener() {
            @Override
            public void onSimpleItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Agency currentAgency = (Agency) adapter.getItem(position);
                Intent intent = new Intent(getActivity(), DetailsActivty.class);
                intent.putExtra("house", currentAgency);
                getActivity().startActivity(intent);
            }
        });
        recyclerView.addOnItemTouchListener(new OnItemLongClickListener() {
            @Override
            public void onSimpleItemLongClick(BaseQuickAdapter adapter, View view, int position) {
                Agency currentAgency = (Agency) adapter.getItem(position);

                PopupMenu popupMenu = new PopupMenu(getActivity(), view);
                popupMenu.getMenuInflater().inflate(R.menu.update_menu, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.action_edit) {
                            Intent intent = new Intent(getActivity(), UpdateActivity.class);
                            intent.putExtra("house", currentAgency);
                            getActivity().startActivity(intent);
                        }
                        if (item.getItemId() == R.id.action_delete) {
                            if (agencyViewModel != null) {
                                agencyViewModel.delete(currentAgency);
                                Toast.makeText(getActivity(), "Delete SUCCESS", Toast.LENGTH_LONG).show();
                            }
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });

        agencyViewModel = ViewModelProviders.of(this).get(AgencyViewModel.class);
        String userId = (String) SharedPreferencesUtils.getParam(getActivity(), "userId", "");
        agencyViewModel.getAllAgency(userId).observe(getViewLifecycleOwner(), new Observer<List<Agency>>() {
            @Override
            public void onChanged(final List<Agency> agencies) {
                //update RecyclerView
                //   Toast.makeText(MainActivity.this, "OnChanged", Toast.LENGTH_SHORT).show();
//                adapter.setNotes(notes);
                adapter.setNewData(agencies);
                adapter.notifyDataSetChanged();
            }
        });
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

}