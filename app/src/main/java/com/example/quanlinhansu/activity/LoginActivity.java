package com.example.quanlinhansu.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.example.quanlinhansu.MainActivity;
import com.example.quanlinhansu.R;
import com.example.quanlinhansu.base.BaseActivity;
import com.example.quanlinhansu.databinding.ActivityLoginBinding;



public class LoginActivity extends BaseActivity<ActivityLoginBinding,LoginViewModel> {
    @Override
    public Class<LoginViewModel> getViewmodel() {
        return LoginViewModel.class;
    }

    @Override
    public int getLayoutID() {
        return R.layout.activity_login;
    }

    @Override
    public void setBindingViewmodel() {
         binding.setViewmodel(viewmodel);
         event();
    }

    private void event() {
        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usernam = binding.edtusername.getText().toString().trim();
                String pass = binding.edtpass.getText().toString().trim();
                if(usernam.equals("") || pass.equals("")){
                    Toast.makeText(LoginActivity.this, "Không dc bỏ trống trường nào !!!", Toast.LENGTH_SHORT).show();
                }else{
                    if(usernam.equalsIgnoreCase("sontit9x") && pass.equalsIgnoreCase("123")){
                        Toast.makeText(LoginActivity.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("user",usernam);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }else {
                        Toast.makeText(LoginActivity.this, "Tài khoản hoặc mật khẩu không chính xác !", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
