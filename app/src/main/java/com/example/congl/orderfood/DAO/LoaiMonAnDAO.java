package com.example.congl.orderfood.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.congl.orderfood.CreateDatabase;
import com.example.congl.orderfood.DTO.LoaiMonAnDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by congl on 07-Apr-18.
 */

public class LoaiMonAnDAO {

    SQLiteDatabase sqLiteDatabase;

    public LoaiMonAnDAO(Context context){
        CreateDatabase createDatabase = new CreateDatabase(context);
        sqLiteDatabase = createDatabase.open();
    }

    public boolean themLoaiMonAn(String tenLoai){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_LOAIMONAN_TENLOAI, tenLoai);

        long kiemTra = sqLiteDatabase.insert(CreateDatabase.TB_LOAIMONAN, null, contentValues);

        if (kiemTra != 0){
            return true;
        }
        else{
            return false;
        }
    }

    public List<LoaiMonAnDTO> layDanhSachLoaiMonAn(){
        List<LoaiMonAnDTO> ListLoaiMonAnDTO = new ArrayList<>();

        String cauTruyVan = "SELECT * FROM " + CreateDatabase.TB_LOAIMONAN;
        Cursor cursor = sqLiteDatabase.rawQuery(cauTruyVan, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            LoaiMonAnDTO loaiMonAnDTO = new LoaiMonAnDTO();
            loaiMonAnDTO.setMaLoai(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_LOAIMONAN_MALOAI)));
            loaiMonAnDTO.setTenLoai(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_LOAIMONAN_TENLOAI)));

            ListLoaiMonAnDTO.add(loaiMonAnDTO);

            cursor.moveToNext();
        }
        return ListLoaiMonAnDTO;
    }

    public String layHinhLoaiMonAn(int maLoai){
        String hinhAnh = "";
        String cauTruyVan = "SELECT * FROM " + CreateDatabase.TB_MONAN
                + " WHERE " + CreateDatabase.TB_MONAN_MALOAI + " = '" + maLoai + "' "
                + " AND " + CreateDatabase.TB_MONAN_HINHANH + " != '' ORDER BY " + CreateDatabase.TB_MONAN_MAMON + " LIMIT 1";
        Cursor cursor = sqLiteDatabase.rawQuery(cauTruyVan, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            hinhAnh = cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_MONAN_HINHANH));
            cursor.moveToNext();
        }
        return hinhAnh;
    }
}
