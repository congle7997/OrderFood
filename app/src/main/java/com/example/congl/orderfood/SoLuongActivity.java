package com.example.congl.orderfood;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.congl.orderfood.DAO.GoiMonDAO;
import com.example.congl.orderfood.DTO.ChiTietGoiMonDTO;

public class SoLuongActivity extends AppCompatActivity implements View.OnClickListener {

    EditText edtSoLuongMonAn;
    Button btnDongYThemSoLuongMonAn;

    GoiMonDAO goiMonDAO;

    int maBan, maMonAn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_them_so_luong);

        edtSoLuongMonAn = findViewById(R.id.edtSoLuongMonAn);
        btnDongYThemSoLuongMonAn = findViewById(R.id.btnDongYThemSoLuongMonAn);

        //vì là Activity nên không cần getActivity()
        goiMonDAO = new GoiMonDAO(this);

        Intent intentSoLuong = getIntent();
        maBan = intentSoLuong.getIntExtra("MABAN", 0);
        maMonAn = intentSoLuong.getIntExtra("MAMONAN", 0);

        btnDongYThemSoLuongMonAn.setOnClickListener(this);
        Log.d("maban", maBan+"");
    }

    @Override
    public void onClick(View view) {
        int maGoiMon = (int) goiMonDAO.layMaGoiMonTheoMaBan(maBan, "false");
        boolean kiemTra = goiMonDAO.kiemTraMonAnDaTonTai(maGoiMon, maMonAn);

        if (kiemTra){
            int soLuongCu = goiMonDAO.laySoLuongMonAnTheoMaGoiMonVaMaMonAn(maGoiMon, maMonAn);
            int soLuongMoi = Integer.parseInt(edtSoLuongMonAn.getText().toString());
            int tongSoLuong = soLuongCu + soLuongMoi;

            ChiTietGoiMonDTO chiTietGoiMonDTO = new ChiTietGoiMonDTO();
            chiTietGoiMonDTO.setMaGoiMon(maGoiMon);
            chiTietGoiMonDTO.setMaMonAn(maMonAn);
            chiTietGoiMonDTO.setSoLuong(tongSoLuong);

            boolean kiemTraCapNhatSoLuong = goiMonDAO.capNhatSoLuong(chiTietGoiMonDTO);

            if (kiemTraCapNhatSoLuong){
                Toast.makeText(this, getResources().getString(R.string.them_thanh_cong), Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(this, getResources().getString(R.string.them_that_bai), Toast.LENGTH_SHORT).show();
            }
        }
        else {
            int soLuong = Integer.parseInt(edtSoLuongMonAn.getText().toString());
            ChiTietGoiMonDTO chiTietGoiMonDTO = new ChiTietGoiMonDTO();
            chiTietGoiMonDTO.setMaGoiMon(maGoiMon);
            chiTietGoiMonDTO.setMaMonAn(maMonAn);
            chiTietGoiMonDTO.setSoLuong(soLuong);

            boolean kiemTraThemChiTietGoiMon = goiMonDAO.themChiTietGoiMon(chiTietGoiMonDTO);

            if (kiemTraThemChiTietGoiMon){
                Toast.makeText(this, getResources().getString(R.string.them_thanh_cong), Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(this, getResources().getString(R.string.them_that_bai), Toast.LENGTH_SHORT).show();
            }
        }

        finish();
    }
}
