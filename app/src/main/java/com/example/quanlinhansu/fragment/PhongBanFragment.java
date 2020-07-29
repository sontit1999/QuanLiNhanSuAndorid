package com.example.quanlinhansu.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlinhansu.R;
import com.example.quanlinhansu.base.BaseFragment;
import com.example.quanlinhansu.callback.ActionbarListener;
import com.example.quanlinhansu.callback.CallbackPB;
import com.example.quanlinhansu.databinding.FragPhongbanBinding;
import com.example.quanlinhansu.fragment.detailphong.DetailsPhongFragment;
import com.example.quanlinhansu.model.ChucVu;
import com.example.quanlinhansu.model.PhongBan;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PhongBanFragment extends BaseFragment<FragPhongbanBinding,PhongBanViewModel> {
    ActionbarListener listener;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("phongban");
    ArrayList<PhongBan> list = new ArrayList<>();
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listener = (ActionbarListener) getContext();
    }

    @Override
    public Class<PhongBanViewModel> getViewmodel() {
        return PhongBanViewModel.class;
    }

    @Override
    public int getLayoutID() {
        return R.layout.frag_phongban;
    }

    @Override
    public void setBindingViewmodel() {
         binding.setViewmodel(viewmodel);
         setupRecyclerView();
        // addDatatoFirebase(); sss
    }

    private void addDatatoFirebase() {
        List<PhongBan> list = new ArrayList<>();
        list.add(new PhongBan("PB01","Giám đốc"));
        list.add(new PhongBan("PB02","Kinh doanh"));
        list.add(new PhongBan("PB03","Kế toán"));
        list.add(new PhongBan("PB04","Kĩ thuật"));
        list.add(new PhongBan("PB05","Hành chính"));
        for(int i = 0;i<list.size();i++){
            myRef.child(list.get(i).getMaPhongBan()).setValue(list.get(i));
        }
    }

    private void setupRecyclerView() {
        binding.rvPhongBan.setHasFixedSize(true);
        binding.rvPhongBan.setLayoutManager(new GridLayoutManager(getContext(),2));
        binding.rvPhongBan.setAdapter(viewmodel.adapter);
    }

    @Override
    public void ViewCreated() {
          viewmodel.getPhongBan().observe(this, new Observer<ArrayList<PhongBan>>() {
              @Override
              public void onChanged(ArrayList<PhongBan> phongBans) {
                        viewmodel.adapter.setList(phongBans);
              }
          });
          viewmodel.adapter.setCallback(new CallbackPB() {
              @Override
              public void onCLickPB(PhongBan phongBan) {
                 // Toast.makeText(getActivity(), "Click " + phongBan.getTenPhongBan(), Toast.LENGTH_SHORT).show();
                  binding.edtMaPB.setText(phongBan.getMaPhongBan());
                  binding.edtTenPB.setText(phongBan.getTenPhongBan());
                  DetailsPhongFragment detailsPhongFragment = new DetailsPhongFragment();
                  detailsPhongFragment.setphong(phongBan);
                  getActivity().getSupportFragmentManager().beginTransaction().addToBackStack(getTag()).replace(R.id.frameLayout,detailsPhongFragment).commit();
              }
          });
          event();
          readPhongBan();
    }

    private void readPhongBan() {
        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data 2at this location is updated.
               // Toast.makeText(getActivity(), "So phong :" + dataSnapshot.getChildrenCount(), Toast.LENGTH_SHORT).show();

                list.clear();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    PhongBan pb = postSnapshot.getValue(PhongBan.class);
                    list.add(pb);
                }
                viewmodel.arrPhongBan.postValue(list);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });

    }

    private void event() {
        binding.btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.edtMaPB.getText().toString().equals("") || binding.edtTenPB.getText().toString().equals("")){
                    Toast.makeText(getActivity(), "Không được bỏ trống!!!", Toast.LENGTH_SHORT).show();
                }else{
                    final PhongBan pb = new PhongBan(binding.edtMaPB.getText().toString(),binding.edtTenPB.getText().toString());
                    if(!checkExistPB(pb,true)){
                        myRef.child(pb.getMaPhongBan()).setValue(pb).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(getActivity(), "Đã thêm phòng ban mã" + pb.getMaPhongBan(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }else{
                        Toast.makeText(getActivity(), "Mã hoặc tên phòng ban đã tồn tại !!", Toast.LENGTH_SHORT).show();
                    }


                }

            }
        });
        binding.btnCapnhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.edtMaPB.getText().toString().equals("") || binding.edtTenPB.getText().toString().equals("")){
                    Toast.makeText(getActivity(), "Không được bỏ trống!!!", Toast.LENGTH_SHORT).show();
                }else{
                    final PhongBan pb = new PhongBan(binding.edtMaPB.getText().toString(),binding.edtTenPB.getText().toString());
                    if(checkExistPB(pb,false)){
                        myRef.child(pb.getMaPhongBan()).setValue(pb).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(getActivity(), "ĐÃ Cập nhật phòng ban mã" + pb.getMaPhongBan(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }else{
                        Toast.makeText(getActivity(), "Mã phòng ban không tồn tại !!", Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });
        binding.btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.edtMaPB.getText().toString().equals("") ){
                    Toast.makeText(getActivity(), "Không được bỏ trống mã phòng ban!!!", Toast.LENGTH_SHORT).show();
                }else{
                    myRef.child(binding.edtMaPB.getText().toString()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(getActivity(), "Đã Xóa phòng ban!!!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }


            }
        });
    }
    public boolean checkExistPB(PhongBan pb, boolean checkTenOrNot){
        if(checkTenOrNot){
            for(int i = 0; i<list.size();i++){
                if( list.get(i).getMaPhongBan().equals(pb.getMaPhongBan()) || list.get(i).getTenPhongBan().equals(pb.getTenPhongBan())){
                    return true;
                }
            }
            return false;
        }else {
            for(int i = 0; i<list.size();i++){
                if( list.get(i).getMaPhongBan().equals(pb.getMaPhongBan())){
                    return true;
                }
            }
            return false;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("sondz" , "oncreate view phong ban");
        return super.onCreateView(inflater, container, savedInstanceState);
    }
    @Override
    public void onResume() {
        super.onResume();
        Log.d("sondz","onresume phong ban");
        listener.onResume(1);
    }
}
