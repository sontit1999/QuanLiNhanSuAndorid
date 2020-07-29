package com.example.quanlinhansu.fragment;

import androidx.lifecycle.MutableLiveData;

import com.example.quanlinhansu.adapter.PhongBanAdapter;
import com.example.quanlinhansu.base.BaseViewmodel;
import com.example.quanlinhansu.model.PhongBan;

import java.util.ArrayList;
import java.util.List;

public class PhongBanViewModel extends BaseViewmodel {
    MutableLiveData<ArrayList<PhongBan>> arrPhongBan = new MutableLiveData<>();
    PhongBanAdapter adapter = new PhongBanAdapter();
    public MutableLiveData<ArrayList<PhongBan>> getPhongBan(){
        List<PhongBan> list = new ArrayList<>();
        list.add(new PhongBan("PB01","Giám đốc"));
        list.add(new PhongBan("PB02","Kinh doanh"));
        list.add(new PhongBan("PB03","Kế toán"));
        list.add(new PhongBan("PB04","Kĩ thuật"));
        list.add(new PhongBan("PB05","Hành chính"));
        arrPhongBan.postValue((ArrayList<PhongBan>) list);
        return arrPhongBan;
    }
}
