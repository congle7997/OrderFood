package com.example.congl.orderfood;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.congl.orderfood.DAO.BanAnDAO;

/**
 * Created by congl on 25-Mar-18.
 */

public class ThemBanAnActivity extends AppCompatActivity implements View.OnClickListener {

    EditText edtTenBanAn;
    Button btnDongYThemBanAn;
    BanAnDAO banAnDAO;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_them_ban_an);

        edtTenBanAn = findViewById(R.id.edtTenBanAn);
        btnDongYThemBanAn = findViewById(R.id.btnDongYThemBanAn);

        banAnDAO = new BanAnDAO(this);
        btnDongYThemBanAn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String sTenBanAn = edtTenBanAn.getText().toString();
        if (sTenBanAn != null || sTenBanAn.equals("")){
            boolean kiemTra = banAnDAO.themBanAN(sTenBanAn);
            Intent intent = new Intent();
            intent.putExtra("KIEMTRA", kiemTra);
            setResult(Activity.RESULT_OK, intent);
            //tắt popup
            finish();
        }
    }
}
