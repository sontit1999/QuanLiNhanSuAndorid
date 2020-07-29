package com.example.quanlinhansu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.quanlinhansu.base.BaseActivity;
import com.example.quanlinhansu.callback.ActionbarListener;
import com.example.quanlinhansu.databinding.ActivityMainBinding;
import com.example.quanlinhansu.fragment.PhongBanFragment;
import com.example.quanlinhansu.fragment.chucvu.ChucVuFragment;
import com.example.quanlinhansu.fragment.nhanvien.NhanVienFramgne;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends BaseActivity<ActivityMainBinding,MainViewModel> implements ActionbarListener {

    @Override
    public Class<MainViewModel> getViewmodel() {
        return MainViewModel.class;
    }

    @Override
    public int getLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    public void setBindingViewmodel() {
          binding.setViewmodel(viewmodel);
          binding.navigationview.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
              @Override
              public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                  switch (menuItem.getItemId()){
                      case R.id.menuphongban:
                          addFragmet(new PhongBanFragment());
                          binding.tvTitle.setText("Phòng ban");
                          binding.drawler.closeDrawers();
                          break;
                      case R.id.menunhanvien:
                          binding.tvTitle.setText("Nhân viên");
                          addFragmet(new NhanVienFramgne());
                         // Toast.makeText(MainActivity.this, "Nhân viên", Toast.LENGTH_SHORT).show();
                          binding.drawler.closeDrawers();
                          break;
                      case R.id.menuchucvu:
                          binding.tvTitle.setText("Chức vụ");
                          addFragmet(new ChucVuFragment());
                          binding.drawler.closeDrawers();
                          break;
                      case R.id.menulogout:
                          //Toast.makeText(MainActivity.this, "Đăng xuất", Toast.LENGTH_SHORT).show();
                          binding.drawler.closeDrawers();
                          finish();
                          break;
                  }
                  return false;
              }
          });
          binding.iconMenuToggle.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  binding.drawler.openDrawer(Gravity.LEFT);
              }
          });

          // set default fragment
        addFragmet(new PhongBanFragment());
    }
    public void addFragmet(Fragment fragment){
        FragmentManager manager = getSupportFragmentManager();

        manager.beginTransaction().replace(R.id.frameLayout,fragment).commit();
    }
    public void addFragmenthaveBackstack(Fragment fragment){
        FragmentManager manager = getSupportFragmentManager();

        manager.beginTransaction().replace(R.id.frameLayout,fragment).addToBackStack(fragment.getTag()).commit();
    }
    @Override
    public void onBackPressed() {
        Log.d("sondz" , "back press!");
        super.onBackPressed();
    }

    @Override
    public void onResume(int type) {
        switch (type){
            case 1:
                binding.tvTitle.setText("Phòng ban");
                break;

            case 2:
                binding.tvTitle.setText("Nhân viên");
                break;
            case 3:
                binding.tvTitle.setText("Chức vụ");
                break;
        }
    }
}