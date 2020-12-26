package com.zen.agency.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zen.agency.entity.AgencyConstant;
import com.zen.agency.view.DetailsActivty;
import com.zen.agency.R;
import com.zen.agency.network.http.Emp;
import com.zen.agency.room.Agency;
import com.zen.agency.view.UpdateActivity;

import java.util.ArrayList;
import java.util.List;

public class AgencyAdapter extends BaseQuickAdapter<Agency,BaseViewHolder> {
    private List<Agency> agencies = new ArrayList<>();
    public AgencyAdapter(List<Agency> agencies) {
        super(R.layout.recyclerview_item, agencies);
        this.agencies=agencies;
    }


    @Override
    protected void convert(@NonNull BaseViewHolder helper, Agency item) {
        helper.setText(R.id.title, item.getAddress()).addOnClickListener(R.id.item_all).setText(R.id.describe,item.getDescription())
        .setText(R.id.room,""+item.getRoomNum()+" ROOMs").setText(R.id.surface,item.getSurface()+" m²")
        .setText(R.id.sold,item.isSold()?"sold":"NOT SOLD");
    }

//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        final Agency currentAgency = agencies.get(position);

//        holder.title_tv.setText(currentAgency.getAddress());

//        String x = notes.get(position).getImageUrl();
//        Glide.with(context)
//                .load(x)
//
//                .listener(new RequestListener<Drawable>() {
//                    @Override
//                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
//                        holder.progressBar.setVisibility(View.VISIBLE);
//                        return false;
//                    }
//
//                    @Override
//                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
//                        holder.progressBar.setVisibility(View.VISIBLE);
//                        return false;
//                    }
//                })
//                .into(holder.imge_tv);
//        holder.cardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context, DetailsActivty.class);
//                intent.putExtra(AgencyConstant.TITLE, currentAgency.getTitle());
//                intent.putExtra(AgencyConstant.ADDRSSS, currentAgency.getAddress());
//                intent.putExtra(AgencyConstant.DESC, currentAgency.getDescription());
//                intent.putExtra(AgencyConstant.ROOM_NUMBER, currentAgency.getRoomNum());
//                intent.putExtra(AgencyConstant.PRICE, currentAgency.getPrice());
//                intent.putExtra(AgencyConstant.SURFACE, currentAgency.getSurface());
//                intent.putExtra(AgencyConstant.CREATE_TIME, currentAgency.getCreateName());
//                intent.putExtra(AgencyConstant.CREATE_NAME, currentAgency.getCreateName());
//                intent.putExtra(AgencyConstant.UPDATE_TIME, currentAgency.getUpdateTime());
//                context.startActivity(intent);
//            }
//        });
//
//        holder.updateButton.setOnClickListener(v -> {
//            Intent intent = new Intent(context, UpdateActivity.class);
//            intent.putExtra(AgencyConstant.TITLE, currentAgency.getTitle());
//            intent.putExtra(AgencyConstant.ADDRSSS, currentAgency.getAddress());
//            intent.putExtra(AgencyConstant.DESC, currentAgency.getDescription());
//            intent.putExtra(AgencyConstant.ROOM_NUMBER, currentAgency.getRoomNum());
//            intent.putExtra(AgencyConstant.PRICE, currentAgency.getPrice());
//            intent.putExtra(AgencyConstant.SURFACE, currentAgency.getSurface());
//            intent.putExtra(AgencyConstant.CREATE_TIME, currentAgency.getCreateName());
//            intent.putExtra(AgencyConstant.CREATE_NAME, currentAgency.getCreateName());
//            intent.putExtra(AgencyConstant.UPDATE_TIME, currentAgency.getUpdateTime());
//            context.startActivity(intent);
//        });

//    }


//    @Override
//    public int getItemCount() {
//        return agencies.size();
//    }

    public void setNotes(List<Agency> agencies) {
        this.agencies = agencies;
        notifyDataSetChanged();
    }
}
