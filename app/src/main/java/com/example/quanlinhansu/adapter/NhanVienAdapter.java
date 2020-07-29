package com.example.quanlinhansu.adapter;

import androidx.databinding.library.baseAdapters.BR;

import com.example.quanlinhansu.R;
import com.example.quanlinhansu.base.BaseAdapter;
import com.example.quanlinhansu.base.CBAdapter;
import com.example.quanlinhansu.callback.CallbackNV;
import com.example.quanlinhansu.databinding.ItemNhanvienBinding;
import com.example.quanlinhansu.model.NhanVien;



public class NhanVienAdapter extends BaseAdapter<NhanVien, ItemNhanvienBinding> {
    CallbackNV callbackNV;
    @Override
    public int getLayoutId() {
        return R.layout.item_nhanvien;
    }

    @Override
    public int getIdVariable() {
        return BR.nhanvien;
    }

    @Override
    public int getIdVariableOnclick() {
        return BR.callbackNV;
    }

    @Override
    public CBAdapter getOnclick() {
        return callbackNV;
    }

    public void setCallbackNV(CallbackNV callbackNV) {
        this.callbackNV = callbackNV;
    }
}
