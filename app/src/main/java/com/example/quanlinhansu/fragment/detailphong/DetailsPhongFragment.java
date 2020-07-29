package com.example.quanlinhansu.fragment.detailphong;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlinhansu.R;
import com.example.quanlinhansu.activity.AddNhanVienActivity;
import com.example.quanlinhansu.base.BaseFragment;
import com.example.quanlinhansu.callback.ActionbarListener;
import com.example.quanlinhansu.callback.CallbackNV;
import com.example.quanlinhansu.databinding.FragDetailsphongBinding;
import com.example.quanlinhansu.model.NhanVien;
import com.example.quanlinhansu.model.PhongBan;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class DetailsPhongFragment extends BaseFragment<FragDetailsphongBinding,DetailsPhongViewmodel> {
    PhongBan phongBan;
    ActionbarListener listener;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("nhanvien");
    ArrayList<NhanVien> list = new ArrayList<>();
    private String tenphong = "";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listener = (ActionbarListener) getContext();
    }
    public void setphong(PhongBan pb){
        this.phongBan = pb;
    }
    @Override
    public Class<DetailsPhongViewmodel> getViewmodel() {
        return DetailsPhongViewmodel.class;
    }

    @Override
    public int getLayoutID() {
        return R.layout.frag_detailsphong;
    }

    @Override
    public void setBindingViewmodel() {
         binding.setViewmodel(viewmodel);
         setupRecyclerview();
    }

    private void setupRecyclerview() {
        binding.rvNhanVien.setHasFixedSize(true);
        binding.rvNhanVien.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false));
        binding.rvNhanVien.setAdapter(viewmodel.nhanVienAdapter);
        ItemTouchHelper.SimpleCallback touchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            private final ColorDrawable background = new ColorDrawable(getResources().getColor(R.color.colorDo));

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                final String tennv = viewmodel.nhanVienAdapter.getList().get(viewHolder.getAdapterPosition()).getTennv();
                String manv = viewmodel.nhanVienAdapter.getList().get(viewHolder.getAdapterPosition()).getManv();
                //  viewmodel.nhanVienAdapter.removeItem(viewHolder.getAdapterPosition());
                myRef.child(manv).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getActivity(), "Đã xóa nhân viên " + tennv , Toast.LENGTH_SHORT).show();
                    }
                });
                // showDialogDelete(viewHolder.getAdapterPosition(),viewmodel.nhanVienAdapter.getList().get(viewHolder.getAdapterPosition()));
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(touchHelperCallback);
        itemTouchHelper.attachToRecyclerView(binding.rvNhanVien);
    }

    @Override
    public void ViewCreated() {
        readDataNhanvien();
        binding.tvTitlePhong.setText("Danh sach nhan vien phong " + phongBan.getTenPhongBan());
        viewmodel.nhanVienAdapter.setCallbackNV(new CallbackNV() {
            @Override
            public void onClickNhanVien(NhanVien nv) {
                Intent intent = new Intent(getActivity(), AddNhanVienActivity.class);
                intent.putExtra("nv", nv);
                getActivity().startActivity(intent);
            }
        });
    }

    private void readDataNhanvien() {
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    NhanVien nv = postSnapshot.getValue(NhanVien.class);
                    if(nv.getMaPB().equals(phongBan.getMaPhongBan()))
                    {
                        list.add(nv);
                    }
                }
                viewmodel.nhanVienAdapter.setList(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("sondz","onresume phong ban");
        listener.onResume(1);
    }
}
