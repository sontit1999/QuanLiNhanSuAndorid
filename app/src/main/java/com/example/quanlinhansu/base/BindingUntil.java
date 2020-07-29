package com.example.quanlinhansu.base;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.example.quanlinhansu.model.NhanVien;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class BindingUntil {
    public static   FirebaseDatabase database = FirebaseDatabase.getInstance();
    public static DatabaseReference myRef = database.getReference("nhanvien");
    @BindingAdapter({"bind:imageUrl"})
    public static void loadImage(ImageView view, String imageUrl) {
        Glide
                .with(view.getContext())
                .load(imageUrl)
                .into(view);

    }
    @BindingAdapter({"bind:getnumberstaff"})
    public static void getStaff(final TextView view, final String getnumberstaff) {
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int count = 0;
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    NhanVien nv = postSnapshot.getValue(NhanVien.class);
                    if(nv.getMaPB().equals(getnumberstaff)){
                        count ++;
                    }
                }
                view.setText("Số nv:" + count);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
