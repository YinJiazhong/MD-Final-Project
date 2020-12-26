package com.zen.agency.adapter;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zen.agency.R;
import com.zen.agency.room.Agency;
import com.zen.agency.room.User;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends BaseQuickAdapter<User,BaseViewHolder> {

    public UserAdapter(List<User> users) {
        super(R.layout.recyclerview_item_user, users);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, User item) {
        helper.setText(R.id.user_name, item.getUserName()).addOnClickListener(R.id.user_name);
    }

}
