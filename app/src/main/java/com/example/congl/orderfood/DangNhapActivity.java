package com.example.congl.orderfood;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.congl.orderfood.DAO.NhanVienDAO;

/**
 * Created by congl on 23-Mar-18.
 */

public class DangNhapActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnDongYDN, btnDangKyDN;
    EditText edtTenDangNhapDN, edtMatKhauDN;
    NhanVienDAO nhanVienDAO;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dang_nhap);

        btnDongYDN = findViewById(R.id.btnDongYDN);
        btnDangKyDN = findViewById(R.id.btnDangKyDN);
        edtTenDangNhapDN = findViewById(R.id.edTenDangNhapDN);
        edtMatKhauDN = findViewById(R.id.edtMatKhauDN);

        nhanVienDAO = new NhanVienDAO(this);

        hienThiButtonDangKyVaDongY();

        btnDongYDN.setOnClickListener(this);
        btnDangKyDN.setOnClickListener(this);
    }

    private void hienThiButtonDangKyVaDongY(){
        boolean kiemTra = nhanVienDAO.kiemTraNhanVien();
        if (kiemTra){
            btnDangKyDN.setVisibility(View.GONE);
            btnDongYDN.setVisibility(View.VISIBLE);
        }
        else {
            btnDangKyDN.setVisibility(View.VISIBLE);
            btnDongYDN.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.btnDongYDN:
                btnDongYDN();
                break;
            case R.id.btnDangKyDN:
                btnDangKyDN();
                break;
        }
    }

    private void btnDongYDN(){
        String sTenDangNhap = edtTenDangNhapDN.getText().toString();
        String sMatKhau = edtMatKhauDN.getText().toString();
        //đăng nhập thành công trả về mã nhân viên
        int kiemTra = nhanVienDAO.kiemTraDangNhap(sTenDangNhap, sMatKhau);
        int maQuyen = nhanVienDAO.layMaQuyenTheoMaNhanVien(kiemTra);
        if (kiemTra != 0){
            Toast.makeText(DangNhapActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();

            Intent intentTrangChu = new Intent(DangNhapActivity.this, TrangChuActivity.class);
            intentTrangChu.putExtra("TENDN", edtTenDangNhapDN.getText().toString());
            //truyền mã nv cho TrangChuActivity để CustomAdapterHienThiBanAn gọi thông qua Intent
            intentTrangChu.putExtra("MANHANVIEN", kiemTra);
            startActivity(intentTrangChu);

            //đối số 1: tên file được lưu dưới dạng XML, đối số 2: chỉ ứng dụng này mới đọc được file này
            SharedPreferences sharedPreferences = getSharedPreferences("LUUQUYEN", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("MAQUYEN", maQuyen);
            editor.commit();

            overridePendingTransition(R.anim.anim_vao_activity, R.anim.anim_ra_activity);
        }
        else {
            Toast.makeText(DangNhapActivity.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
        }
    }

    private void btnDangKyDN(){
        Intent intentDangKy = new Intent(DangNhapActivity.this, DangKyActivity.class);
        intentDangKy.putExtra("LANDAUTIEN", 1);
        startActivity(intentDangKy);
    }

    @Override
    //Khi chuyển từ Activity này sang Activity khác thì vòng đời nhảy vào onResume()
    protected void onResume() {
        super.onResume();
        hienThiButtonDangKyVaDongY();
    }
}
