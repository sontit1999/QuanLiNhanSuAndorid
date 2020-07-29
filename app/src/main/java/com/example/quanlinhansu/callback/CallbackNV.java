package com.example.quanlinhansu.callback;

import com.example.quanlinhansu.base.CBAdapter;
import com.example.quanlinhansu.model.NhanVien;

public interface CallbackNV extends CBAdapter {
    void onClickNhanVien(NhanVien nv);
}
