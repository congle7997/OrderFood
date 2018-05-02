package com.example.congl.orderfood.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.congl.orderfood.CreateDatabase;
import com.example.congl.orderfood.DTO.MonAnDTO;

import java.util.ArrayList;
import java.util.List;

public class MonAnDAO {
    SQLiteDatabase sqLiteDatabase;

    public MonAnDAO(Context context){
        CreateDatabase createDatabase = new CreateDatabase(context);
        sqLiteDatabase = createDatabase.open();
    }

    public boolean themMonAn(MonAnDTO monAnDTO){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_MONAN_TENMONAN, monAnDTO.getTenMonAn());
        contentValues.put(CreateDatabase.TB_MONAN_GIATIEN, monAnDTO.getGiaTien());
        contentValues.put(CreateDatabase.TB_MONAN_MALOAI, monAnDTO.getMaLoai());
        contentValues.put(CreateDatabase.TB_MONAN_HINHANH, monAnDTO.getHinhAnh());

        long kiemTra = sqLiteDatabase.insert(CreateDatabase.TB_MONAN, null, contentValues);
        if (kiemTra != 0){
            return true;
        }
        else {
            return false;
        }
    }

    public List<MonAnDTO> layDanhSachMonAnTheoLoai(int maLoai){
        List<MonAnDTO> ListMonAnDTO = new ArrayList<>();
        String cauTruyVan = "SELECT * FROM " + CreateDatabase.TB_MONAN
                + " WHERE " + CreateDatabase.TB_MONAN_MALOAI + " = '" + maLoai + "' ";
        Cursor cursor = sqLiteDatabase.rawQuery(cauTruyVan, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            MonAnDTO monAnDTO = new MonAnDTO();
            monAnDTO.setHinhAnh(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_MONAN_HINHANH)) + "");
            monAnDTO.setTenMonAn(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_MONAN_TENMONAN)));
            monAnDTO.setGiaTien(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_MONAN_GIATIEN)));
            monAnDTO.setMaMonAn(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_MONAN_MAMON)));
            monAnDTO.setMaLoai(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_MONAN_MALOAI)));
            ListMonAnDTO.add(monAnDTO);

            cursor.moveToNext();
        }
        return ListMonAnDTO;
    }
}
