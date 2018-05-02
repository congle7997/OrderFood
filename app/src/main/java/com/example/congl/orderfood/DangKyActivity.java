package com.example.congl.orderfood;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.congl.orderfood.DAO.NhanVienDAO;
import com.example.congl.orderfood.DAO.QuyenDAO;
import com.example.congl.orderfood.DTO.NhanVienDTO;
import com.example.congl.orderfood.DTO.QuyenDTO;
import com.example.congl.orderfood.FragmentApp.DatePickerFragment;

import java.util.ArrayList;
import java.util.List;

public class DangKyActivity extends AppCompatActivity implements View.OnClickListener, View.OnFocusChangeListener {

    EditText edtTenDangNhapDK, edtMatKhauDK, edtNgaySinhDK, edtCMNDDK;
    Button btnDongYDK, btnThoatDK;
    RadioGroup rgGioiTinh;
    Spinner spnQuyen;
    RadioButton rbNam, rbNu;
    TextView txtTieuDeDangKy;

    NhanVienDAO nhanVienDAO;
    QuyenDAO quyenDAO;
    List<QuyenDTO> ListQuyenDTO;
    List<String> ListString;

    String sGioiTinh;
    int maNhanVien = 0;
    int lanDauTien = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_dang_ky);

        edtTenDangNhapDK = findViewById(R.id.edtTenDangNhapDK);
        edtMatKhauDK = findViewById(R.id.edtMatKhauDK);
        rgGioiTinh = findViewById(R.id.rgGioiTinh);
        rbNam = findViewById(R.id.rbNam);
        rbNu = findViewById(R.id.rbNu);
        spnQuyen = findViewById(R.id.spnQuyen);
        edtNgaySinhDK = findViewById(R.id.edtNgaySinhDK);
        edtCMNDDK = findViewById(R.id.edtCMNDDK);
        btnDongYDK = findViewById(R.id.btnDongYDK);
        btnThoatDK = findViewById(R.id.btnThoatDK);
        txtTieuDeDangKy = findViewById(R.id.txtTieuDeDangKy);

        btnDongYDK.setOnClickListener(this);
        btnThoatDK.setOnClickListener(this);
        edtNgaySinhDK.setOnFocusChangeListener(this);

        nhanVienDAO = new NhanVienDAO(this);
        quyenDAO = new QuyenDAO(this);

        ListQuyenDTO = quyenDAO.layDanhSachQuyen();
        ListString = new ArrayList<>();
        for (int i=0; i<ListQuyenDTO.size(); i++){
            String tenQuyen = ListQuyenDTO.get(i).getTenQuyen();
            ListString.add(tenQuyen);
        }

        lanDauTien = getIntent().getIntExtra("LANDAUTIEN", 0);

        // == 1 là lần đầu
        if (lanDauTien == 1){
            quyenDAO.themQuyen("ADMIN");
            quyenDAO.themQuyen("NHANVIEN");
            spnQuyen.setVisibility(View.GONE);
        }
        else {
            ArrayAdapter arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ListString);
            spnQuyen.setAdapter(arrayAdapter);
            arrayAdapter.notifyDataSetChanged();
        }

        maNhanVien = getIntent().getIntExtra("MANHANVIEN", 0);
        if (maNhanVien != 0){
            txtTieuDeDangKy.setText(getResources().getString(R.string.cap_nhat_nhan_vien));

            NhanVienDTO nhanVienDTO = nhanVienDAO.layNhanVienTheoMaNhanVien(maNhanVien);
            edtTenDangNhapDK.setText(nhanVienDTO.getTENDN());
            edtMatKhauDK.setText(nhanVienDTO.getMATKHAU());
            edtNgaySinhDK.setText(nhanVienDTO.getNGAYSINH());
            edtCMNDDK.setText(String.valueOf(nhanVienDTO.getCMND()));
            String gioiTinh = nhanVienDTO.getGIOITINH();
            if (gioiTinh.equals("Nam")){
                rbNam.setChecked(true);
            }
            else {
                rbNu.setChecked(true);
            }

        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){

            case R.id.btnDongYDK:
                if (maNhanVien != 0){
                    ButtonSuaNhanVien();
                }
                else {
                    ButtonThemNhanVien();
                    break;
                }
            case R.id.btnThoatDK:
                finish();
                break;
        }
    }

    private void ButtonThemNhanVien(){
        String sTenDangNhap = edtTenDangNhapDK.getText().toString();
        String sMatKhau = edtMatKhauDK.getText().toString();
        switch (rgGioiTinh.getCheckedRadioButtonId()){
            case R.id.rbNam:
                sGioiTinh = "Nam";
                break;
            case R.id.rbNu:
                sGioiTinh = "Nữ";
                break;
        }
        String sNgaySinh = edtNgaySinhDK.getText().toString();
        int sCMND = Integer.parseInt(edtCMNDDK.getText().toString());

        if (sTenDangNhap == null || sTenDangNhap.equals("")){
            //getResources().getString thì nó sẽ gọi ra kiểu String, còn chỉ mỗi getResources() thì nó sẽ gọi ra kiểu int
            Toast.makeText(DangKyActivity.this, getResources().getString(R.string.chua_nhap_ten_dang_nhap), Toast.LENGTH_SHORT).show();
        }
        else if (sMatKhau == null || sMatKhau.equals("")){
            Toast.makeText(DangKyActivity.this, getResources().getString(R.string.chua_nhap_mat_khau), Toast.LENGTH_SHORT).show();
        }
        else {
            NhanVienDTO nhanVienDTO = new NhanVienDTO();
            nhanVienDTO.setTENDN(sTenDangNhap);
            nhanVienDTO.setMATKHAU(sMatKhau);
            nhanVienDTO.setGIOITINH(sGioiTinh);
            nhanVienDTO.setNGAYSINH(sNgaySinh);
            nhanVienDTO.setCMND(sCMND);
            //vì là lần đầu tiên nên set luôn làm ADMIN
            if (lanDauTien == 1){
                nhanVienDTO.setMAQUYEN(1);
            }
            else {
                int viTri = spnQuyen.getSelectedItemPosition();
                int maQuyen = ListQuyenDTO.get(viTri).getMaQuyen();
                nhanVienDTO.setMAQUYEN(maQuyen);
            }

            long kiemTra = nhanVienDAO.themNhanVien(nhanVienDTO);
            if (kiemTra !=0){
                Toast.makeText(DangKyActivity.this, getResources().getString(R.string.them_thanh_cong), Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(DangKyActivity.this, getResources().getString(R.string.them_that_bai), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void ButtonSuaNhanVien(){
        String sTenDangNhap = edtTenDangNhapDK.getText().toString();
        String sMatKhau = edtMatKhauDK.getText().toString();
        switch (rgGioiTinh.getCheckedRadioButtonId()){
            case R.id.rbNam:
                sGioiTinh = "Nam";
                break;
            case R.id.rbNu:
                sGioiTinh = "Nữ";
                break;
        }
        String sNgaySinh = edtNgaySinhDK.getText().toString();
        int sCMND = Integer.parseInt(edtCMNDDK.getText().toString());

        NhanVienDTO nhanVienDTO = new NhanVienDTO();
        nhanVienDTO.setMANV(maNhanVien);
        nhanVienDTO.setTENDN(sTenDangNhap);
        nhanVienDTO.setMATKHAU(sMatKhau);
        nhanVienDTO.setGIOITINH(sGioiTinh);
        nhanVienDTO.setNGAYSINH(sNgaySinh);
        nhanVienDTO.setCMND(sCMND);

        boolean kiemTra = nhanVienDAO.suaNhanVien(nhanVienDTO);
        if (kiemTra){
            Toast.makeText(DangKyActivity.this, getResources().getString(R.string.sua_thanh_cong), Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(DangKyActivity.this, getResources().getString(R.string.loi), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    //bắt sự kiện khi con trỏ đang nhấp nháy ở đâu
    public void onFocusChange(View view, boolean hasFocus) {
      int id = view.getId();
      switch (id){
          case R.id.edtNgaySinhDK:
              if (hasFocus){
                  DatePickerFragment datePickerFragment = new DatePickerFragment();
                  datePickerFragment.show(getFragmentManager(), "Ngày sinh");
              }
      }
    }
}
