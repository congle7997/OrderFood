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

import com.example.congl.orderfood.DAO.BanAnDAO;

public class SuaBanAnActivity extends AppCompatActivity implements View.OnClickListener {

    EditText edtSuaTenBanAn;
    Button btnDongYSuaTenBanAn;

    BanAnDAO banAnDAO;

    int maBan;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_sua_ban_an);

        edtSuaTenBanAn = findViewById(R.id.edtSuaTenBanAn);
        btnDongYSuaTenBanAn = findViewById(R.id.btnDongYSuaTenBanAn);

        banAnDAO = new BanAnDAO(this);

        maBan = getIntent().getIntExtra("MABAN", 0);

        btnDongYSuaTenBanAn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        String tenBan = edtSuaTenBanAn.getText().toString();
        if (tenBan.trim().equals("") || tenBan.trim() != null){
            boolean kiemTra = banAnDAO.capNhatTenBan(maBan, tenBan);
            Intent intent = new Intent();
            intent.putExtra("KIEMTRA", kiemTra);
            setResult(Activity.RESULT_OK, intent);
            finish();
        }
        else {
            Toast.makeText(SuaBanAnActivity.this, getResources().getString(R.string.vui_long_nhap_du_lieu), Toast.LENGTH_SHORT).show();
        }
    }
}
