package com.example.quanlinhansu.fragment.nhanvien;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanlinhansu.R;
import com.example.quanlinhansu.activity.AddNhanVienActivity;
import com.example.quanlinhansu.base.BaseFragment;
import com.example.quanlinhansu.callback.ActionbarListener;
import com.example.quanlinhansu.callback.CallbackNV;
import com.example.quanlinhansu.databinding.FragNhanvieBinding;
import com.example.quanlinhansu.model.NhanVien;
import com.example.quanlinhansu.model.PhongBan;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class NhanVienFramgne extends BaseFragment<FragNhanvieBinding,NhanVienViewModel> {
    ActionbarListener listener;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("nhanvien");
    ArrayList<NhanVien> list = new ArrayList<>();
    @Override
    public Class<NhanVienViewModel> getViewmodel() {
        return NhanVienViewModel.class;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listener = (ActionbarListener) getContext();
    }
    @Override
    public int getLayoutID() {
        return R.layout.frag_nhanvie;
    }

    @Override
    public void setBindingViewmodel() {
        binding.setViewmodel(viewmodel);
       // setupSpinner();
       // addNhanvientoFirebasae();
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
    public void showDialogDelete(final int pos,NhanVien nv){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
// Add the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
                viewmodel.nhanVienAdapter.removeItem(pos);
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
                dialog.dismiss();
            }
        });
        builder.setTitle("Bạn có chắc chắn muốn xóa nhân viên " + nv.getTennv() + " không ?");
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private void addNhanvientoFirebasae() {
        List<NhanVien> list = new ArrayList<>();
        list.add(new NhanVien("NV01","Sơn Tít", "Hà Tây","PB01", "CV01", "0010901230","0335275330", "Nam", "06/10/1999", "Sonkoi1995@gmail.com", "27/07/2023", "Senior", "2"));
        list.add(new NhanVien("NV02","Mai anh", "Thanh hóa","PB02", "CV02", "0010901230","0335275330", "Nu", "06/10/1999", "Sonkoi1995@gmail.com", "27/07/2023", "Junior", "2"));
        list.add(new NhanVien("NV03","Hải Yến", "Hà Tây","PB03", "CV03", "0010901230","0335275330", "Nu", "06/10/1999", "Sonkoi1995@gmail.com", "27/07/2023", "Fresher", "2"));
        list.add(new NhanVien("NV04","Minh Quang", "Hà Tây","PB04", "CV01", "0010901230","0335275330", "Nam", "06/10/1999", "Sonkoi1995@gmail.com", "27/07/2023", "Intern", "2"));
        list.add(new NhanVien("NV05","Đình Minh", "Hà Tây","PB01", "CV03", "0010901230","0335275330", "Nam", "06/10/1999", "Sonkoi1995@gmail.com", "27/07/2023", "Project Manager", "2"));
        list.add(new NhanVien("NV06","Đình THông", "Hà Tây","PB05", "CV04", "0010901230","0335275330", "Nam", "06/10/1999", "Sonkoi1995@gmail.com", "27/07/2023", "Leader", "2"));
        for(NhanVien i : list){
            myRef.child(i.getManv()).setValue(i);
        }
    }

    private void setupSpinner() {
//        // Create an ArrayAdapter using the string array and a default spinner layout
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
//                R.array.trinhdo_array, android.R.layout.simple_spinner_item);
//        // Specify the layout to use when the list of choices appears
//                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        // Apply the adapter to the spinner
//                binding.spinnerTrinhdo.setAdapter(adapter);
    }

    @Override
    public void ViewCreated() {
          event();
          readDataNhanvien();
          viewmodel.nhanVienAdapter.setCallbackNV(new CallbackNV() {
              @Override
              public void onClickNhanVien(NhanVien nv) {
                  Intent intent = new Intent(getActivity(),AddNhanVienActivity.class);
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
                    list.add(nv);
                }
                viewmodel.nhanVienAdapter.setList(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void event() {
//        binding.edtNgaysinh.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                showDatePickerDialog(1);
//            }
//        });
//        binding.edtNgayvaolam.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                showDatePickerDialog(2);
//            }
//        });
        binding.btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddNhanVienActivity.class);
                getActivity().startActivity(intent);
            }
        });
    }

    public void showDatePickerDialog(final int type){
//        DatePickerDialog dialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
//            @Override
//            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
//                  if(type == 1){
//                      binding.edtNgaysinh.setText(i2 + "/" +  i1+ "/" + i );
//                  }else if(type == 2){
//                      binding.edtNgayvaolam.setText(i2 + "/" +  i1+ "/" + i );
//                  }
//            }
//        },2020,7,27);
//        dialog.show();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("sondz" , "oncreate view nhanvien");
        return super.onCreateView(inflater, container, savedInstanceState);
    }
    @Override
    public void onResume() {
        super.onResume();
        Log.d("sondz","onresume phong ban");
        listener.onResume(2);
    }
}
