package com.example.congl.orderfood.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.congl.orderfood.CreateDatabase;
import com.example.congl.orderfood.DTO.BanAnDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by congl on 25-Mar-18.
 */

public class BanAnDAO {

    SQLiteDatabase sqLiteDatabase;

    public BanAnDAO(Context context){
        CreateDatabase createDatabase = new CreateDatabase(context);
        sqLiteDatabase = createDatabase.open();
    }

    public boolean themBanAN(String tenBanAn){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_BANAN_TENBAN, tenBanAn);
        contentValues.put(CreateDatabase.TB_BANAN_TINHTRANG, "false");

        long kiemTra = sqLiteDatabase.insert(CreateDatabase.TB_BANAN, null, contentValues);

        if (kiemTra != 0){
            return true;
        }
        else {
            return false;
        }
    }

    public List<BanAnDTO> layTatCaBanAn(){
        List<BanAnDTO> ListBanAnDTO = new ArrayList<BanAnDTO>();
        String cauTruyVan = "SELECT * FROM " + CreateDatabase.TB_BANAN;
        Cursor cursor = sqLiteDatabase.rawQuery(cauTruyVan, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            BanAnDTO banAnDTO = new BanAnDTO();
            banAnDTO.setMaBan(cursor.getInt(cursor.getColumnIndex(CreateDatabase.TB_BANAN_MABAN)));
            banAnDTO.setTenBan(cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_BANAN_TENBAN)));
            ListBanAnDTO.add(banAnDTO);
            cursor.moveToNext();
        }
        return ListBanAnDTO;
    }

    public String layTinhTrangBanAnTheoMaBan(int maBan){
        String tinhTrang = "";
        String cauTruyVan = "SELECT * FROM " + CreateDatabase.TB_BANAN + " WHERE " + CreateDatabase.TB_BANAN_MABAN + " = '" + maBan + "'";
        Cursor cursor = sqLiteDatabase.rawQuery(cauTruyVan, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            tinhTrang = cursor.getString(cursor.getColumnIndex(CreateDatabase.TB_BANAN_TINHTRANG));
            cursor.moveToNext();
        }
        return tinhTrang;
    }

    public boolean capNhatTinhTrangBan(int maBan, String tinhTrang){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_BANAN_TINHTRANG, tinhTrang);

        long kiemTra = sqLiteDatabase.update(CreateDatabase.TB_BANAN, contentValues, CreateDatabase.TB_BANAN_MABAN+" = '"+maBan+"'", null);

        if (kiemTra != 0){
            return true;
        }
        else {
            return false;
        }
    }

    public boolean xoaBanAnTheoMaBan(int maBan){
        long kiemTra = sqLiteDatabase.delete(CreateDatabase.TB_BANAN, CreateDatabase.TB_BANAN_MABAN + " = '" + maBan + "'", null);

        if (kiemTra != 0){
            return true;
        }
        else {
            return false;
        }
    }

    public boolean capNhatTenBan(int maBan, String tenBan){
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateDatabase.TB_BANAN_TENBAN, tenBan);

        long kiemTra = sqLiteDatabase.update(CreateDatabase.TB_BANAN, contentValues, CreateDatabase.TB_BANAN_MABAN+" = '"+maBan+"'", null);

        if (kiemTra != 0){
            return true;
        }
        else {
            return false;
        }
    }
}
