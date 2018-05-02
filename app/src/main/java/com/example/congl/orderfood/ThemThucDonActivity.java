package com.example.congl.orderfood;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.congl.orderfood.CustomAdapter.CustomAdapterHienThiLoaiMonAn;
import com.example.congl.orderfood.DAO.LoaiMonAnDAO;
import com.example.congl.orderfood.DAO.MonAnDAO;
import com.example.congl.orderfood.DTO.LoaiMonAnDTO;
import com.example.congl.orderfood.DTO.MonAnDTO;

import java.io.IOException;
import java.util.List;

/**
 * Created by congl on 01-Apr-18.
 */

//vì nó lớn nên không làm popup
public class ThemThucDonActivity extends AppCompatActivity implements View.OnClickListener {

    public static int REQUEST_CODE_THEM_LOAi_THUC_DON = 113;
    public static int REQUEST_CODE_MO_HINH = 123;

    ImageButton imgThemLoaiThucDon;
    Spinner spnLoaiThucDon;
    ImageView imgHinhThucDon;
    Button btnDongYThemMonAn, btnThoatThemMonAn;
    EditText edtTenMonAn, edtGiaTien;

    LoaiMonAnDAO loaiMonAnDAO;
    List<LoaiMonAnDTO> ListLoaiMonAnDTO;
    CustomAdapterHienThiLoaiMonAn customAdapterHienThiLoaiMonAn;

    MonAnDAO monAnDAO;

    String duongDanHinh;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_them_thuc_don);

        loaiMonAnDAO = new LoaiMonAnDAO(this);
        monAnDAO = new MonAnDAO(this);

        imgThemLoaiThucDon = findViewById(R.id.imgThemLoaiThucDon);
        spnLoaiThucDon = findViewById(R.id.spnLoaiThucDon);
        imgHinhThucDon = findViewById(R.id.imgHinhThucDon);
        btnDongYThemMonAn = findViewById(R.id.btnDongYThemMonAn);
        btnThoatThemMonAn = findViewById(R.id.btnThoatThemMonAn);
        edtTenMonAn = findViewById(R.id.edtTenMonAn);
        edtGiaTien = findViewById(R.id.edtGiaTien);

        hienThiSpinnerLoaiMonAn();

        imgThemLoaiThucDon.setOnClickListener(this);
        imgHinhThucDon.setOnClickListener(this);
        btnDongYThemMonAn.setOnClickListener(this);
        btnThoatThemMonAn.setOnClickListener(this);
    }

    private void hienThiSpinnerLoaiMonAn(){
        ListLoaiMonAnDTO = loaiMonAnDAO.layDanhSachLoaiMonAn();
        customAdapterHienThiLoaiMonAn = new CustomAdapterHienThiLoaiMonAn(ThemThucDonActivity.this, R.layout.custom_layout_spiner_loai_thuc_don, ListLoaiMonAnDTO);
        spnLoaiThucDon.setAdapter(customAdapterHienThiLoaiMonAn);
        customAdapterHienThiLoaiMonAn.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.imgThemLoaiThucDon:
                Intent intentThemLoaiThucDon = new Intent(ThemThucDonActivity.this, ThemLoaiThucDonActivity.class);
                startActivityForResult(intentThemLoaiThucDon, REQUEST_CODE_THEM_LOAi_THUC_DON);
                break;

            case R.id.imgHinhThucDon:
                Intent intentMoHinh = new Intent();
                //mở tất cả các loại hình ảnh có thể đọc được
                intentMoHinh.setType("image/*");
                //mở những hình ảnh có thể mở được
                intentMoHinh.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intentMoHinh, "Chọn hình thực đơn"), REQUEST_CODE_MO_HINH);
                break;

            case R.id.btnDongYThemMonAn:
                //trả ra vị trí item được click
                int viTri = spnLoaiThucDon.getSelectedItemPosition();
                int maLoai = ListLoaiMonAnDTO.get(viTri).getMaLoai();
                String tenMonAn = edtTenMonAn.getText().toString();
                String giaTien = edtGiaTien.getText().toString();

                if (tenMonAn != null && giaTien != null && !tenMonAn.equals("") &&!tenMonAn.equals("")){
                    MonAnDTO monAnDTO = new MonAnDTO();
                    monAnDTO.setGiaTien(giaTien);
                    monAnDTO.setHinhAnh(duongDanHinh);
                    monAnDTO.setMaLoai(maLoai);
                    monAnDTO.setTenMonAn(tenMonAn);

                    boolean kiemTra = monAnDAO.themMonAn(monAnDTO);
                    if (kiemTra){
                        Toast.makeText(this, getResources().getString(R.string.them_thanh_cong), Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(this, getResources().getString(R.string.them_that_bai), Toast.LENGTH_SHORT).show();

                    }
                }
                else {
                    Toast.makeText(this, getResources().getString(R.string.loi_them_mon_an), Toast.LENGTH_SHORT).show();
                }
                Log.d("vitri", viTri+"");
                break;

            case R.id.btnThoatThemMonAn:
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_THEM_LOAi_THUC_DON && resultCode == Activity.RESULT_OK){
            Intent intentDuLieu = data;
            boolean kiemTra = intentDuLieu.getBooleanExtra("KIEMTRALOAITHUCDON", false);
            if (kiemTra){
                hienThiSpinnerLoaiMonAn();
                Toast.makeText(this, getResources().getString(R.string.them_thanh_cong), Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(this, getResources().getString(R.string.them_that_bai), Toast.LENGTH_SHORT).show();
            }
        }
        else if (requestCode == REQUEST_CODE_MO_HINH && resultCode == Activity.RESULT_OK && data != null){
            /*nếu không dùng ImageView
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                imgHinhThucDon.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }*/
            imgHinhThucDon.setImageURI(data.getData());

            duongDanHinh = data.getData().toString();
        }
    }
}
