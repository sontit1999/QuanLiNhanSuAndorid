package com.example.quanlinhansu.activity;

import android.app.DatePickerDialog;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.quanlinhansu.R;
import com.example.quanlinhansu.base.BaseActivity;
import com.example.quanlinhansu.databinding.ActivityAddnhanvienBinding;
import com.example.quanlinhansu.model.ChucVu;
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
import java.util.List;

public class AddNhanVienActivity extends BaseActivity<ActivityAddnhanvienBinding,AddNhanVienViewModel> {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("nhanvien");
    ArrayList<NhanVien> list = new ArrayList<>();
    ArrayList<PhongBan> listPB = new ArrayList<>();
    ArrayList<ChucVu> listCV = new ArrayList<>();
    NhanVien nv;
    @Override
    public Class<AddNhanVienViewModel> getViewmodel() {
        return AddNhanVienViewModel.class;
    }

    @Override
    public int getLayoutID() {
        return R.layout.activity_addnhanvien;
    }

    @Override
    public void setBindingViewmodel() {
           if(getIntent()!=null){
               nv = (NhanVien) getIntent().getSerializableExtra("nv");
           }
           binding.setViewmodel(viewmodel);
           getData();
           setupSpinner();
           event();
           setDataUpdate();

    }

    private void getData() {
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    NhanVien nv = postSnapshot.getValue(NhanVien.class);
                    list.add(nv);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        FirebaseDatabase.getInstance().getReference("phongban").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                 Log.d("sontit", snapshot.getChildrenCount() + "")    ;
                 listPB.clear();
                 List<String> arrPB = new ArrayList<>();
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    PhongBan pb = postSnapshot.getValue(PhongBan.class);
                    arrPB.add(pb.getTenPhongBan());
                    listPB.add(pb);
                }

                // set up spinner
                ArrayAdapter<String> adapterPB = new ArrayAdapter<String>(AddNhanVienActivity.this,android.R.layout.simple_spinner_dropdown_item,arrPB);
                // Specify the layout to use when the list of choices appears
                //adapterPB.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                // Apply the adapter to the spinner
                binding.spinnerPhongban.setAdapter(adapterPB);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
    public boolean checkNvExist(NhanVien nv){
        for(NhanVien i : list){
            if(i.getManv().equals(nv.getManv())){
                return true;
            }
        }
        return false;
    }
    private void setDataUpdate() {
        if(nv!=null){
               binding.edtMaNV.setText(nv.getManv());
               binding.edtTenNV.setText(nv.getTennv());
               binding.edtDC.setText(nv.getDiachi());
               binding.edtEmail.setText(nv.getEmail());
               binding.edtHSluong.setText(nv.getHsLuong());
               binding.edtNgaysinh.setText(nv.getNgaysinh());
               binding.edtNgayvaolam.setText(nv.getNgayvaolam());
               binding.edtSoCM.setText(nv.getSoCMT());
               binding.edtSoDT.setText(nv.getSDT());
               switch (nv.getTrinhdo()){
                   case "Intern": binding.spinnerTrinhdo.setSelection(0);
                      break;
                   case "Fresher": binding.spinnerTrinhdo.setSelection(1);
                       break;
                   case "Junior": binding.spinnerTrinhdo.setSelection(2);
                       break;
                   case "Mid": binding.spinnerTrinhdo.setSelection(3);
                       break;
                   case "Senior": binding.spinnerTrinhdo.setSelection(4);
                       break;
                   case "Project manager": binding.spinnerTrinhdo.setSelection(5);
                       break;
                   case "Leader": binding.spinnerTrinhdo.setSelection(6);
                       break;
               }
               if(nv.getGioitinh().equalsIgnoreCase("Nam")){
                   binding.rvNam.setChecked(true);
               }else{
                   binding.rbNu.setSelected(false);
               }
        }
    }

    private void event() {
        binding.edtNgaysinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog(1);
            }
        });
        binding.edtNgayvaolam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog(2);
            }
        });
        binding.btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String manv = binding.edtMaNV.getText().toString();
                String tennv = binding.edtTenNV.getText().toString();
                String dc = binding.edtDC.getText().toString();
                String email = binding.edtEmail.getText().toString();
                String hsluong = binding.edtHSluong.getText().toString();
                String ngayvaolam = binding.edtNgayvaolam.getText().toString();
                String ngaysinh = binding.edtNgaysinh.getText().toString();
                String cmt = binding.edtSoCM.getText().toString();
                String sdt= binding.edtSoDT.getText().toString();
                String gioitinh;
                if(binding.rvNam.isChecked()){
                    gioitinh = "Nam";
                }else{
                    gioitinh = "Nu";
                }
                String trinhdo = binding.spinnerTrinhdo.getSelectedItem().toString();
                String mapb  = getMaPbFromName(binding.spinnerPhongban.getSelectedItem().toString());
                String macv =  getMaCVFromName(binding.spinnerChucvu.getSelectedItem().toString());
                if(manv.equals("") || tennv.equals("") || dc.equals("") || email.equals("") || hsluong.equals("") || ngaysinh.equals("") || ngayvaolam.equals("") || cmt.equals("") || sdt.equals("")){
                    Toast.makeText(AddNhanVienActivity.this, "Không được bỏ trống trường nào !", Toast.LENGTH_SHORT).show();
                }else{
                    final NhanVien nv = new NhanVien(manv,tennv,dc,mapb,macv,cmt,sdt,gioitinh,ngaysinh,email,ngayvaolam,trinhdo,hsluong);
                    if(checkNvExist(nv)){
                        Toast.makeText(AddNhanVienActivity.this, "Đã tồn tại nhân viên có mã " + nv.getManv() + " này rùi :D", Toast.LENGTH_SHORT).show();
                    }else{
                        myRef.child(nv.getManv()).setValue(nv).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(AddNhanVienActivity.this, " Đã thêm nhân viên " + nv.getTennv(), Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        });
                    }
                }
            }
        });
        binding.btnCapnhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String manv = binding.edtMaNV.getText().toString();
                String tennv = binding.edtTenNV.getText().toString();
                String dc = binding.edtDC.getText().toString();
                String email = binding.edtEmail.getText().toString();
                String hsluong = binding.edtHSluong.getText().toString();
                String ngayvaolam = binding.edtNgayvaolam.getText().toString();
                String ngaysinh = binding.edtNgaysinh.getText().toString();
                String cmt = binding.edtSoCM.getText().toString();
                String sdt= binding.edtSoDT.getText().toString();
                String gioitinh;
                if(binding.rvNam.isChecked()){
                    gioitinh = "Nam";
                }else{
                    gioitinh = "Nu";
                }
                String trinhdo = binding.spinnerTrinhdo.getSelectedItem().toString();
                String mapb  = getMaPbFromName(binding.spinnerPhongban.getSelectedItem().toString());
                String macv =  getMaCVFromName(binding.spinnerChucvu.getSelectedItem().toString());
                if(manv.equals("") || tennv.equals("") || dc.equals("") || email.equals("") || hsluong.equals("") || ngaysinh.equals("") || ngayvaolam.equals("") || cmt.equals("") || sdt.equals("")){
                    Toast.makeText(AddNhanVienActivity.this, "Không được bỏ trống trường nào !", Toast.LENGTH_SHORT).show();
                }else{
                    final NhanVien nv = new NhanVien(manv,tennv,dc,mapb,macv,cmt,sdt,gioitinh,ngaysinh,email,ngayvaolam,trinhdo,hsluong);
                    if(!checkNvExist(nv)){
                        Toast.makeText(AddNhanVienActivity.this, "Không tồn tại nhân viên có mã " + nv.getManv() + " này rùi :D", Toast.LENGTH_SHORT).show();
                    }else{
                        myRef.child(nv.getManv()).setValue(nv).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(AddNhanVienActivity.this, " Đã cập nhật nhân viên " + nv.getTennv(), Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        });
                    }
                }
            }
        });
    }
    public String getMaPbFromName(String name){
        for(PhongBan i : listPB){
            if(i.getTenPhongBan().equalsIgnoreCase(name)){
                return i.getMaPhongBan();
            }
        }
        return "PBxxx";
    }
    public String getMaCVFromName(String name){
        for(ChucVu i : listCV){
            if(i.getTenChucVu().equalsIgnoreCase(name)){
                return i.getMaChucVu();
            }
        }
        return "CVxxx";
    }
    private void setupSpinner() {
        binding.rvNam.setChecked(true);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.trinhdo_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        binding.spinnerTrinhdo.setAdapter(adapter);

        FirebaseDatabase.getInstance().getReference("chucvu").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listCV.clear();
                List<String> arrCV = new ArrayList<>();
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    ChucVu cv = postSnapshot.getValue(ChucVu.class);
                    arrCV.add(cv.getTenChucVu());
                    listCV.add(cv);
                }
                // set up spinner cv
                 ArrayAdapter<String> adapterCV = new ArrayAdapter<String>(AddNhanVienActivity.this,android.R.layout.simple_spinner_dropdown_item,arrCV);
                // Specify the layout to use when the list of choices appears
               // adapterCV.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                // Apply the adapter to the spinner
                binding.spinnerChucvu.setAdapter(adapterCV);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void showDatePickerDialog(final int type){
        DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                if(type == 1){
                    binding.edtNgaysinh.setText(i2 + "/" +  i1+ "/" + i );
                }else if(type == 2){
                    binding.edtNgayvaolam.setText(i2 + "/" +  i1+ "/" + i );
                }
            }
        },2020,7,27);
        dialog.show();
    }
}
