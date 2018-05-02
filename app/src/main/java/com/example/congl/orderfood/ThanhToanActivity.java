package com.example.congl.orderfood;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.congl.orderfood.CustomAdapter.CustomAdapterHienThiThanhToan;
import com.example.congl.orderfood.DAO.BanAnDAO;
import com.example.congl.orderfood.DAO.GoiMonDAO;
import com.example.congl.orderfood.DTO.ThanhToanDTO;
import com.example.congl.orderfood.FragmentApp.HienThiBanAnFragment;

import java.util.List;

public class ThanhToanActivity extends AppCompatActivity implements View.OnClickListener {

    GridView gvThanhToan;
    Button btnThanhToan, btnThoatThanhToan;
    TextView txtTongTien;

    FragmentManager fragmentManager;

    GoiMonDAO goiMonDAO;
    BanAnDAO banAnDAO;
    List<ThanhToanDTO> ListThanhToanDTO;
    CustomAdapterHienThiThanhToan customAdapterHienThiThanhToan;

    long tongTien = 0;
    int maBan;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_thanh_toan);

        gvThanhToan = findViewById(R.id.gvThanhToan);
        btnThanhToan = findViewById(R.id.btnThanhToan);
        btnThoatThanhToan = findViewById(R.id.btnThoatThanhToan);
        txtTongTien = findViewById(R.id.txtTongTien);

        fragmentManager = getSupportFragmentManager();

        goiMonDAO = new GoiMonDAO(this);
        banAnDAO = new BanAnDAO(this);

        maBan = getIntent().getIntExtra("MABAN", 0);
        if (maBan != 0){


            hienThiThanhToan();

            for (int i=0; i<ListThanhToanDTO.size(); i++){
                int soLuong = ListThanhToanDTO.get(i).getSoLuong();
                int giaTien = ListThanhToanDTO.get(i).getGiaTien();

                tongTien += (soLuong*giaTien);
            }

            txtTongTien.setText(getResources().getString(R.string.tong_tien) + tongTien);
        }

        btnThanhToan.setOnClickListener(this);
        btnThoatThanhToan.setOnClickListener(this);
    }

    private void hienThiThanhToan(){
        //lấy bàn chưa thanh toán
        int maGoiMon = (int) goiMonDAO.layMaGoiMonTheoMaBan(maBan, "false");
        ListThanhToanDTO = goiMonDAO.layDanhSachMonAnTheoMaGoiMon(maGoiMon);
        customAdapterHienThiThanhToan = new CustomAdapterHienThiThanhToan(this, R.layout.custom_layout_thanh_toan, ListThanhToanDTO);
        gvThanhToan.setAdapter(customAdapterHienThiThanhToan);
        customAdapterHienThiThanhToan.notifyDataSetChanged();
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.btnThanhToan:
                //set tình trạng true -> đã thanh toán
                boolean kiemTraGoiMon = goiMonDAO.capNhatTinhTrangThanhToanTheoMaBan(maBan, "true");
                //set tình trạng false -> chưa có người ngồi
                boolean kiemTraBanAn = banAnDAO.capNhatTinhTrangBan(maBan, "false");

                if (kiemTraGoiMon && kiemTraBanAn){
                    Toast.makeText(ThanhToanActivity.this, getResources().getString(R.string.thanh_toan_thanh_cong), Toast.LENGTH_SHORT).show();
                    hienThiThanhToan();
                }
                else {
                    Toast.makeText(ThanhToanActivity.this, getResources().getString(R.string.loi), Toast.LENGTH_SHORT).show();

                }
                break;

            case R.id.btnThoatThanhToan:
                finish();
                break;
        }
    }
}
