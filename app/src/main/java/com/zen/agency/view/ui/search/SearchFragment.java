package com.zen.agency.view.ui.search;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.util.StringUtil;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemLongClickListener;
import com.zen.agency.R;
import com.zen.agency.adapter.AgencyAdapter;
import com.zen.agency.room.Agency;
import com.zen.agency.util.SharedPreferencesUtils;
import com.zen.agency.view.DetailsActivty;
import com.zen.agency.view.UpdateActivity;
import com.zen.agency.viewmodel.AgencyViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class SearchFragment extends Fragment {
    private AgencyViewModel agencyViewModel;
    private List<Agency> currentList;
    private List<Agency> agencies;
    //    private SearchViewModel searchViewModel;
    LinearLayout search_layout;
    AppCompatButton search_field;
    AppCompatButton search;
    AppCompatEditText min_price;
    AppCompatEditText max_price;
    AppCompatEditText et_address;
    AppCompatEditText min_surface;
    AppCompatEditText max_surface;
    AppCompatEditText min_num;
    AppCompatEditText max_num;
    RadioButton flat;
    RadioButton house;
    RadioButton office;
    String type = "";
    String address = "";
    Double minPrice = 0D;
    Double maxPrice = 0D;
    String etAddress = "";
    Double maxSurface = 0D;
    Double minSurface = 0D;
    Double minNum = 0D;
    Double maxNum = 0D;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
//        searchViewModel =
//                new ViewModelProvider(this).get(SearchViewModel.class);
        View root = inflater.inflate(R.layout.fragment_search, container, false);
//        final TextView textView = root.findViewById(R.id.text_notifications);
//        searchViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        search_layout = root.findViewById(R.id.search_layout);
        search_field = root.findViewById(R.id.search_field);
        min_price = root.findViewById(R.id.min_price);
        max_price = root.findViewById(R.id.max_price);
        et_address = root.findViewById(R.id.et_address);
        min_surface = root.findViewById(R.id.min_surface);
        max_surface = root.findViewById(R.id.max_surface);
        min_num = root.findViewById(R.id.min_num);
        max_num = root.findViewById(R.id.max_num);
//        flat = root.findViewById(R.id.radio_flat);
//        house = root.findViewById(R.id.radio_house);
//        office = root.findViewById(R.id.radio_office);
        RadioGroup radgroup = root.findViewById(R.id.radio_group);
        radgroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radbtn = root.findViewById(checkedId);
                type=radbtn.getText().toString();
            }
        });




        search = root.findViewById(R.id.search);
        RecyclerView recyclerView = root.findViewById(R.id.recyclerview);
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
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
//        recyclerView.setVisibility(View.GONE);
        search_field.setVisibility(View.INVISIBLE);
        agencyViewModel = ViewModelProviders.of(this).get(AgencyViewModel.class);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search_field.setVisibility(View.VISIBLE);
                search_layout.setVisibility(View.GONE);
                search_field.setText("SHOW SEARCH FIELD");
                address = et_address.getText().toString();
                if(!TextUtils.isEmpty( min_price.getText().toString())){
                    minPrice =Double.parseDouble( min_price.getText().toString());
                }
                if(!TextUtils.isEmpty( max_price.getText().toString())){
                    maxPrice =Double.parseDouble( max_price.getText().toString());
                }
                if(!TextUtils.isEmpty( min_surface.getText().toString())){
                    minSurface =Double.parseDouble( min_surface.getText().toString());
                }
                if(!TextUtils.isEmpty( max_surface.getText().toString())){
                    maxSurface =Double.parseDouble( max_surface.getText().toString());
                }
                if(!TextUtils.isEmpty( min_num.getText().toString())){
                    minNum =Double.parseDouble( min_num.getText().toString());
                }
                if(!TextUtils.isEmpty( max_num.getText().toString())){
                    maxNum =Double.parseDouble( max_num.getText().toString());
                }
//                recyclerView.setVisibility(View.VISIBLE);
                //int minSurface, int maxSurface,String type,int minPrice,int maxPrice,int minNum,int maxNum,String address
                String userId= (String) SharedPreferencesUtils.getParam(getActivity(),"userId","");
                agencyViewModel.searchAllAgency(userId,minSurface, maxSurface, type, minPrice, maxPrice, minNum, maxNum, address).observe(getViewLifecycleOwner(), new Observer<List<Agency>>() {
                    @Override
                    public void onChanged( List<Agency> agencies) {
                        //update RecyclerView
                        //   Toast.makeText(MainActivity.this, "OnChanged", Toast.LENGTH_SHORT).show();
//                adapter.setNotes(notes);
                        Log.e("num",agencies.size()+"");
                        Log.e("num",agencies.toString());
                        adapter.setNewData(agencies);
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        });

        search_field.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search_layout.setVisibility(View.VISIBLE);
//                recyclerView.setVisibility(View.GONE);
            }
        });


        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}