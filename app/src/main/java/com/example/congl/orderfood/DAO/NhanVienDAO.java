package com.example.congl.orderfood.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.congl.orderfood.CreateDatabase;
import com.example.congl.orderfood.DTO.NhanVienDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by congl on 23-Mar-18.
 */

public class NhanVienDAO {
    SQLiteDatabase sqLiteDatabase;

    /*Cần gọi đối tượng createDatabase để gọi phương thức open()
     mà lớp CreateDatabase yêu cầu truyền vào Context nên ta cũng phải truyền vào Context*/
    public NhanVienDAO(Context context){
        CreateDatabase createDatabase = new CreateDatabase(context);
        /*Phương thức open() trả ra 1 sqLiteDatabase nên ta cần 1 đối tượng sqLiteDatabase
        để lưu trữ và thực hiện các câu truy vấn thêm, xóa, sửa, ...*/
        sqLiteDatabase = createDatabase.open();
    }

    public long themNhanVien(NhanVienDTO nhanVienDTO){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_NHANVIEN_TENDN, nhanVienDTO.getTENDN());
        contentValues.put(CreateDatabase.TB_NHANVIEN_MATKHAU, nhanVienDTO.getMATKHAU());
        contentValues.put(CreateDatabase.TB_NHANVIEN_GIOITINH, nhanVienDTO.getGIOITINH());
        contentValues.put(CreateDatabase.TB_NHANVIEN_NGAYSINH, nhanVienDTO.getNGAYSINH());
        contentValues.put(CreateDatabase.TB_NHANVIEN_CMND, nhanVienDTO.getCMND());
        contentValues.put(CreateDatabase.TB_NHANVIEN_MAQUYEN, nhanVienDTO.getMAQUYEN());


        long kiemTra = sqLiteDatabase.insert(CreateDatabase.TB_NHANVIEN, null, contentValues);
        return kiemTra;
    }

    public boolean kiemTraNhanVien(){
        String cauTruyVan = "SELECT * FROM " + CreateDatabase.TB_NHANVIEN;
        //rawQuery cho phép nhập vào 1 câu truy vấn và trả ra 1 cursor
        Cursor cursor = sqLiteDatabase.rawQuery(cauTruyVan, null);
        if (cursor.getCount() != 0){
            return true;
        }
        else {
            return false;
        }
    }

    public int kiemTraDangNhap(String sTenDangNhap, String sMatKhau){
        int maNhanVien = 0;
        String cauTruyVan = "SELECT * FROM " + CreateDatabase.TB_NHANVIEN
                + " WHERE " + CreateDatabase.TB_NHANVIEN_TENDN + " = '" + sTenDangNhap
                + "' AND " + CreateDatabase.TB_NHANVIEN_MATKHAU + " = '" + sMatKhau + "'";
        Cursor cursor = sqLiteDatabase.rawQuery(cauTruyVan, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            maNhanVien = cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_NHANVIEN_MANV));
            cursor.moveToNext();
        }
        return maNhanVien;
    }

    public List<NhanVienDTO> layDanhSachNhanVien(){
        List<NhanVienDTO> ListNhanVienDTO = new ArrayList<>();
        String cauTruyVan = "SELECT * FROM " + CreateDatabase.TB_NHANVIEN;
        Cursor cursor = sqLiteDatabase.rawQuery(cauTruyVan, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            NhanVienDTO nhanVienDTO = new NhanVienDTO();
            nhanVienDTO.setMANV(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_NHANVIEN_MANV)));
            nhanVienDTO.setCMND(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_NHANVIEN_CMND)));
            nhanVienDTO.setTENDN(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_NHANVIEN_TENDN)));
            nhanVienDTO.setMATKHAU(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_NHANVIEN_MATKHAU)));
            nhanVienDTO.setGIOITINH(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_NHANVIEN_GIOITINH)));
            nhanVienDTO.setNGAYSINH(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_NHANVIEN_NGAYSINH)));

            ListNhanVienDTO.add(nhanVienDTO);

            cursor.moveToNext();
        }
        return ListNhanVienDTO;
    }

    public boolean xoaNhanVien(int maNhanVien){
        long kiemTra = sqLiteDatabase.delete(CreateDatabase.TB_NHANVIEN, CreateDatabase.TB_NHANVIEN_MANV + " = " + maNhanVien, null);
        if (kiemTra != 0){
            return true;
        }
        else {
            return false;
        }
    }

    public NhanVienDTO layNhanVienTheoMaNhanVien(int maNhanVien){
        String cauTruyVan = "SELECT * FROM " + CreateDatabase.TB_NHANVIEN;
        Cursor cursor = sqLiteDatabase.rawQuery(cauTruyVan, null);
        cursor.moveToFirst();
        NhanVienDTO nhanVienDTO = new NhanVienDTO();
        while (!cursor.isAfterLast()){
            nhanVienDTO.setMANV(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_NHANVIEN_MANV)));
            nhanVienDTO.setCMND(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_NHANVIEN_CMND)));
            nhanVienDTO.setTENDN(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_NHANVIEN_TENDN)));
            nhanVienDTO.setMATKHAU(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_NHANVIEN_MATKHAU)));
            nhanVienDTO.setGIOITINH(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_NHANVIEN_GIOITINH)));
            nhanVienDTO.setNGAYSINH(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_NHANVIEN_NGAYSINH)));

            cursor.moveToNext();
        }
        return nhanVienDTO;
    }

    public boolean suaNhanVien(NhanVienDTO nhanVienDTO){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_NHANVIEN_TENDN, nhanVienDTO.getTENDN());
        contentValues.put(CreateDatabase.TB_NHANVIEN_MATKHAU, nhanVienDTO.getMATKHAU());
        contentValues.put(CreateDatabase.TB_NHANVIEN_GIOITINH, nhanVienDTO.getGIOITINH());
        contentValues.put(CreateDatabase.TB_NHANVIEN_NGAYSINH, nhanVienDTO.getNGAYSINH());
        contentValues.put(CreateDatabase.TB_NHANVIEN_CMND, nhanVienDTO.getCMND());

        long kiemTra = sqLiteDatabase.update(CreateDatabase.TB_NHANVIEN, contentValues, CreateDatabase.TB_NHANVIEN_MANV + " = " + nhanVienDTO.getMANV(), null);
        if (kiemTra != 0){
            return true;
        }
        else {
            return false;
        }
    }

    public int layMaQuyenTheoMaNhanVien(int maNhanVien){
        int maQuyen = 0;
        String cauTruyVan = "SELECT * FROM " + CreateDatabase.TB_NHANVIEN + " WHERE " + CreateDatabase.TB_NHANVIEN_MANV + " = " + maNhanVien;
        Cursor cursor = sqLiteDatabase.rawQuery(cauTruyVan, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            maQuyen = cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_NHANVIEN_MAQUYEN));
            cursor.moveToNext();
        }
        return maQuyen;
    }
}
