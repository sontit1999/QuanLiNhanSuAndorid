package com.example.quanlinhansu.adapter;

import com.example.quanlinhansu.BR;
import com.example.quanlinhansu.R;
import com.example.quanlinhansu.base.BaseAdapter;
import com.example.quanlinhansu.base.CBAdapter;
import com.example.quanlinhansu.callback.CallbackCV;
import com.example.quanlinhansu.databinding.ItemChucvuBinding;
import com.example.quanlinhansu.model.ChucVu;

public class ChucVuAdapter extends BaseAdapter<ChucVu, ItemChucvuBinding> {
    CallbackCV callback;
    @Override
    public int getLayoutId() {
        return R.layout.item_chucvu;
    }

    @Override
    public int getIdVariable() {
        return BR.chucvu;
    }

    @Override
    public int getIdVariableOnclick() {
        return BR.callbackCV;
    }

    @Override
    public CBAdapter getOnclick() {
        return callback;
    }

    public void setCallback(CallbackCV callback) {
        this.callback = callback;
    }
}
