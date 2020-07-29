package com.example.quanlinhansu.fragment.chucvu;

import androidx.lifecycle.MutableLiveData;

import com.example.quanlinhansu.adapter.ChucVuAdapter;
import com.example.quanlinhansu.base.BaseViewmodel;
import com.example.quanlinhansu.model.ChucVu;

import java.util.ArrayList;
import java.util.List;

public class ChucVuViewModel extends BaseViewmodel {
    ChucVuAdapter chucVuAdapter = new ChucVuAdapter();
    MutableLiveData<ArrayList<ChucVu>> arrChucvu = new MutableLiveData<>();
    public MutableLiveData<ArrayList<ChucVu>> getArrChucvu(){
        List<ChucVu> list = new ArrayList<>();
        list.add(new ChucVu("CV01","Giám đốc"));
        list.add(new ChucVu("CV02","Phó Giám đốc"));
        list.add(new ChucVu("CV03","Kế toán"));
        list.add(new ChucVu("CV04","Nhân viên"));
        list.add(new ChucVu("CV05","Quản lý"));
        arrChucvu.postValue((ArrayList<ChucVu>) list);
        return arrChucvu;
    }
}
