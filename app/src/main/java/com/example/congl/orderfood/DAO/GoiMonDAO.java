package com.example.congl.orderfood.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.congl.orderfood.CreateDatabase;
import com.example.congl.orderfood.DTO.ChiTietGoiMonDTO;
import com.example.congl.orderfood.DTO.GoiMonDTO;
import com.example.congl.orderfood.DTO.ThanhToanDTO;

import java.util.ArrayList;
import java.util.List;

public class GoiMonDAO {

    SQLiteDatabase sqLiteDatabase;

    public GoiMonDAO(Context context){
        CreateDatabase createDatabase = new CreateDatabase(context);
        sqLiteDatabase = createDatabase.open();
    }

    public long themGoiMon(GoiMonDTO goiMonDTO){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_GOIMON_MABAN, goiMonDTO.getMaBan());
        contentValues.put(CreateDatabase.TB_GOIMON_MANV, goiMonDTO.getMaNV());
        contentValues.put(CreateDatabase.TB_GOIMON_NGAYGOI, goiMonDTO.getNgayGoi());
        contentValues.put(CreateDatabase.TB_GOIMON_TINHTRANG, goiMonDTO.getTinhTrang());

        long kiemTra = sqLiteDatabase.insert(CreateDatabase.TB_GOIMON, null, contentValues);

        return kiemTra;
    }

    public long layMaGoiMonTheoMaBan(int maBan, String tinhTrang){
        long maGoiMon = 0;
        String cauTruyVan = "SELECT * FROM " + CreateDatabase.TB_GOIMON
                + " WHERE " + CreateDatabase.TB_GOIMON_MABAN + " = '" + maBan + "' AND " + CreateDatabase.TB_GOIMON_TINHTRANG + " = '" + tinhTrang + "'";
        Cursor cursor = sqLiteDatabase.rawQuery(cauTruyVan, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            maGoiMon = cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_GOIMON_MAGOIMON));
            cursor.moveToNext();
        }
        return maGoiMon;
    }

    //dùng chung GoiMonDAO với ChiTietGoiMonDAO

    public boolean kiemTraMonAnDaTonTai(int maGoiMon, int maMonAn){
        String cauTruyVan = "SELECT * FROM " + CreateDatabase.TB_CHITIETGOIMON
                + " WHERE " + CreateDatabase.TB_CHITIETGOIMON_MAGOIMON + " = " + maGoiMon
                + " AND " + CreateDatabase.TB_CHITIETGOIMON_MAMONAN + " = " + maMonAn;
        Cursor cursor = sqLiteDatabase.rawQuery(cauTruyVan, null);
        if (cursor.getCount() != 0){
            return true;
        }
        else {
            return false;
        }
    }

    public int laySoLuongMonAnTheoMaGoiMonVaMaMonAn(int maGoiMon, int maMonAn){
        int soLuong = 0;
        String cauTruyVan = "SELECT * FROM " + CreateDatabase.TB_CHITIETGOIMON
                + " WHERE " + CreateDatabase.TB_CHITIETGOIMON_MAGOIMON + " = " + maGoiMon
                + " AND " + CreateDatabase.TB_CHITIETGOIMON_MAMONAN + " = " + maMonAn;
        Cursor cursor = sqLiteDatabase.rawQuery(cauTruyVan, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            soLuong = cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_CHITIETGOIMON_SOLUONG));
            cursor.moveToNext();
        }
        return soLuong;
    }

    public boolean capNhatSoLuong(ChiTietGoiMonDTO chiTietGoiMonDTO){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_CHITIETGOIMON_SOLUONG, chiTietGoiMonDTO.getSoLuong());
        long kiemTra = sqLiteDatabase.update(CreateDatabase.TB_CHITIETGOIMON,
                contentValues,
                CreateDatabase.TB_CHITIETGOIMON_MAGOIMON + " = " + chiTietGoiMonDTO.getMaGoiMon() + " AND " + CreateDatabase.TB_CHITIETGOIMON_MAMONAN + " = " + chiTietGoiMonDTO.getMaMonAn(),
                null);
        if (kiemTra != 0){
            return true;
        }
        else {
            return false;
        }
    }

    public boolean themChiTietGoiMon(ChiTietGoiMonDTO chiTietGoiMonDTO){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_CHITIETGOIMON_MAGOIMON, chiTietGoiMonDTO.getMaGoiMon());
        contentValues.put(CreateDatabase.TB_CHITIETGOIMON_MAMONAN, chiTietGoiMonDTO.getMaMonAn());
        contentValues.put(CreateDatabase.TB_CHITIETGOIMON_SOLUONG, chiTietGoiMonDTO.getSoLuong());

        long kiemTra = sqLiteDatabase.insert(CreateDatabase.TB_CHITIETGOIMON, null, contentValues);

        if (kiemTra != 0){
            return true;
        }
        else {
            return false;
        }
    }

    public List<ThanhToanDTO> layDanhSachMonAnTheoMaGoiMon(int maGoiMon){
        String cauTruyVan = "SELECT * FROM " + CreateDatabase.TB_CHITIETGOIMON + " CTGM," + CreateDatabase.TB_MONAN + " MA WHERE "
                + "CTGM." + CreateDatabase.TB_CHITIETGOIMON_MAMONAN + " = MA." + CreateDatabase.TB_MONAN_MAMON
                + " AND " + CreateDatabase.TB_CHITIETGOIMON_MAGOIMON + " = " + maGoiMon;
        Cursor cursor = sqLiteDatabase.rawQuery(cauTruyVan, null);
        cursor.moveToFirst();
        List<ThanhToanDTO> ListThanhToanDTO = new ArrayList<>();
        while (!cursor.isAfterLast()){
            ThanhToanDTO thanhToanDTO = new ThanhToanDTO();
            thanhToanDTO.setSoLuong(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_CHITIETGOIMON_SOLUONG)));
            thanhToanDTO.setGiaTien(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_MONAN_GIATIEN)));
            thanhToanDTO.setTenMonAn(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_MONAN_TENMONAN)));

            ListThanhToanDTO.add(thanhToanDTO);

            cursor.moveToNext();
        }
        return ListThanhToanDTO;
    }

    public boolean capNhatTinhTrangThanhToanTheoMaBan(int maBan, String tinhTrang){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_GOIMON_TINHTRANG, tinhTrang);

        long kiemTra = sqLiteDatabase.update(CreateDatabase.TB_GOIMON, contentValues, CreateDatabase.TB_GOIMON_MABAN + " = " + maBan, null);

        if (kiemTra != 0){
            return  true;
        }
        else {
            return false;
        }
    }
}
