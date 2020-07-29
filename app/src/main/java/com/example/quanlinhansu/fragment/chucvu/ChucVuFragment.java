package com.example.quanlinhansu.fragment.chucvu;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlinhansu.R;
import com.example.quanlinhansu.base.BaseFragment;
import com.example.quanlinhansu.callback.ActionbarListener;
import com.example.quanlinhansu.callback.CallbackCV;
import com.example.quanlinhansu.databinding.FragChucvuBinding;
import com.example.quanlinhansu.model.ChucVu;
import com.example.quanlinhansu.model.PhongBan;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class ChucVuFragment extends BaseFragment<FragChucvuBinding,ChucVuViewModel> {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("chucvu");
    ArrayList<ChucVu> list = new ArrayList<>();
    ActionbarListener listener;
    @Override
    public Class<ChucVuViewModel> getViewmodel() {
        return ChucVuViewModel.class;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listener = (ActionbarListener) getContext();
    }
    @Override
    public int getLayoutID() {
        return R.layout.frag_chucvu;
    }

    @Override
    public void setBindingViewmodel() {
         binding.setViewmodel(viewmodel);
         setUpRecyclerview();
       //  addDatatofirebase();
    }

    private void addDatatofirebase() {
        List<ChucVu> list = new ArrayList<>();
        list.add(new ChucVu("CV01","Giám đốc"));
        list.add(new ChucVu("CV02","Phó Giám đốc"));
        list.add(new ChucVu("CV03","Kế toán"));
        list.add(new ChucVu("CV04","Nhân viên"));
        list.add(new ChucVu("CV05","Quản lý"));
        for(ChucVu i : list)
        {
            myRef.child(i.getMaChucVu()).setValue(i);
        }
    }

    private void setUpRecyclerview() {
       binding.rvChucVu.setHasFixedSize(true);
       binding.rvChucVu.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false));
       binding.rvChucVu.setAdapter(viewmodel.chucVuAdapter);
    }

    @Override
    public void ViewCreated() {
        viewmodel.getArrChucvu().observe(this, new Observer<ArrayList<ChucVu>>() {
            @Override
            public void onChanged(ArrayList<ChucVu> chucVus) {
                viewmodel.chucVuAdapter.setList(chucVus);
            }
        });
        viewmodel.chucVuAdapter.setCallback(new CallbackCV() {
            @Override
            public void onClickCV(ChucVu chucVu) {
                binding.edtMaCV.setText(chucVu.getMaChucVu());
                binding.edtTenCV.setText(chucVu.getTenChucVu());
            }
        });
        event();
        readChucvu();
    }

    private void readChucvu() {
          myRef.addValueEventListener(new ValueEventListener() {
              @Override
              public void onDataChange(@NonNull DataSnapshot snapshot) {
                  list.clear();
                  for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                      ChucVu cv = postSnapshot.getValue(ChucVu.class);
                      list.add(cv);
                  }
                  viewmodel.arrChucvu.postValue(list);
              }

              @Override
              public void onCancelled(@NonNull DatabaseError error) {

              }
          });
    }

    private void event() {
        binding.btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.edtMaCV.getText().toString().equals("") || binding.edtTenCV.getText().toString().equals("")){
                    Toast.makeText(getActivity(), "Không được bỏ trống!!!", Toast.LENGTH_SHORT).show();
                }else{

                    final ChucVu cv = new ChucVu(binding.edtMaCV.getText().toString(),binding.edtTenCV.getText().toString());
                   // Toast.makeText(getActivity(), " Số chức vụ trong live Data :" + viewmodel.arrChucvu.getValue().size(), Toast.LENGTH_SHORT).show();
                    if(checkExistChucvu(cv,true)){
                        Toast.makeText(getActivity(), "Đã tồn tại chức vụ này !", Toast.LENGTH_SHORT).show();
                    }else{
                        myRef.child(cv.getMaChucVu()).setValue(cv).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(getActivity(), "Đã thêm chức vụ " + cv.getTenChucVu(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }


                }

            }
        });
        binding.btnCapnhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.edtMaCV.getText().toString().equals("") || binding.edtTenCV.getText().toString().equals("")){
                    Toast.makeText(getActivity(), "Không được bỏ trống!!!", Toast.LENGTH_SHORT).show();
                }else{
                    final ChucVu cv = new ChucVu(binding.edtMaCV.getText().toString(),binding.edtTenCV.getText().toString());
                    if(checkExistChucvu(cv,false)){
                        myRef.child(cv.getMaChucVu()).setValue(cv).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(getActivity(), "ĐÃ Cập nhật chức vụ " + cv.getTenChucVu(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }else{
                        Toast.makeText(getActivity(), "Không có chức vụ nào có mã  "  + cv.getMaChucVu(), Toast.LENGTH_SHORT).show();
                    }


                }

            }
        });
        binding.btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(binding.edtMaCV.getText().toString().equals("") ){
                    Toast.makeText(getActivity(), "Không được bỏ trống mã chức vụ!!!", Toast.LENGTH_SHORT).show();
                }else{
                    myRef.child(binding.edtMaCV.getText().toString()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(getActivity(), "Đã Xóa chức vụ !!!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }


            }
        });
    }
    public boolean checkExistChucvu(ChucVu cv,boolean checkTenOrNot){
       if(checkTenOrNot){
           for(int i = 0; i<list.size();i++){
               if( list.get(i).getMaChucVu().equals(cv.getMaChucVu()) || list.get(i).getTenChucVu().equals(cv.getTenChucVu())){
                   return true;
               }
           }
           return false;
       }else {
           for(int i = 0; i<list.size();i++){
               if( list.get(i).getMaChucVu().equals(cv.getMaChucVu())){
                   return true;
               }
           }
           return false;
       }
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("sondz" , "oncreate view chucvu");
        return super.onCreateView(inflater, container, savedInstanceState);
    }
    @Override
    public void onResume() {
        super.onResume();
        Log.d("sondz","onresume phong ban");
        listener.onResume(3);
    }
}
