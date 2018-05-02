package com.example.congl.orderfood;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.congl.orderfood.DAO.LoaiMonAnDAO;

/**
 * Created by congl on 07-Apr-18.
 */

public class ThemLoaiThucDonActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnDongYThemLoaiThucDon;
    EditText edtTenLoai;

    LoaiMonAnDAO loaiMonAnDAO;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_them_loai_thuc_don);

        loaiMonAnDAO = new LoaiMonAnDAO(this);

        btnDongYThemLoaiThucDon = findViewById(R.id.btnDongYThemLoaiThucDon);
        edtTenLoai = findViewById(R.id.edtTenLoai);

        btnDongYThemLoaiThucDon.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String sTenLoai = edtTenLoai.getText().toString();
        if (sTenLoai != null || sTenLoai.equals("")){
            boolean kiemTra = loaiMonAnDAO.themLoaiMonAn(sTenLoai);
            Intent intentDuLieu = new Intent();
            intentDuLieu.putExtra("KIEMTRALOAITHUCDON", kiemTra);
            setResult(Activity.RESULT_OK, intentDuLieu);
            finish();
        }
        else {
            Toast.makeText(this, getResources().getString(R.string.vui_long_nhap_du_lieu), Toast.LENGTH_SHORT).show();
        }

    }
}
