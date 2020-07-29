package com.example.quanlinhansu.adapter;

import com.example.quanlinhansu.BR;
import com.example.quanlinhansu.R;
import com.example.quanlinhansu.base.BaseAdapter;
import com.example.quanlinhansu.base.CBAdapter;
import com.example.quanlinhansu.callback.CallbackPB;
import com.example.quanlinhansu.databinding.ItemPhongbanBinding;
import com.example.quanlinhansu.model.PhongBan;

public class PhongBanAdapter extends BaseAdapter<PhongBan,ItemPhongbanBinding> {
    CallbackPB callback;
    @Override
    public int getLayoutId() {
        return R.layout.item_phongban;
    }

    @Override
    public int getIdVariable() {
        return BR.phongban;
    }

    @Override
    public int getIdVariableOnclick() {
        return BR.callbackPB;
    }

    @Override
    public CallbackPB getOnclick() {
        return callback;
    }
    public void setCallback(CallbackPB callback){
        this.callback= callback;
    }
}
