package com.example.congl.orderfood;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.congl.orderfood.FragmentApp.HienThiBanAnFragment;
import com.example.congl.orderfood.FragmentApp.HienThiNhanVienFragment;
import com.example.congl.orderfood.FragmentApp.HienThiThucDonFragment;

/**
 * Created by congl on 25-Mar-18.
 */

public class TrangChuActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView nvTrangChu;
    TextView txtTenNhanVienNavigation;
    FragmentManager fragmentManager;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_trang_chu);

        drawerLayout = findViewById(R.id.drawerLayout);
        toolbar = findViewById(R.id.toolbar);
        nvTrangChu = findViewById(R.id.nvTrangChu);
        //txtTenNhanVienNavigation ở bên layout_trang_chu_header_navigation
        View view = nvTrangChu.inflateHeaderView(R.layout.layout_trang_chu_header_navigation);
        txtTenNhanVienNavigation = view.findViewById(R.id.txtTenNhanVienNavigation);

        //nó chưa biết Toolbar là Actionbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //truyền toolbar vào để nó biết sự kiện click actionBarDrawerToggle của toolbar
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.mo, R.string.dong){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };

        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        //lấy màu mặc định của các icon
        nvTrangChu.setItemIconTintList(null);

        Intent intent = getIntent();
        String sTenDangNhap = intent.getStringExtra("TENDN");
        txtTenNhanVienNavigation.setText(sTenDangNhap);

        nvTrangChu.setNavigationItemSelectedListener(this);

        fragmentManager = getSupportFragmentManager();
        HienThiBanAnFragment hienThiBanAnFragment = new HienThiBanAnFragment();
        FragmentTransaction ftHienThiBanAn = fragmentManager.beginTransaction();
        ftHienThiBanAn.replace(R.id.frContent, hienThiBanAnFragment);
        ftHienThiBanAn.commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.itTrangChu:
                HienThiBanAnFragment hienThiBanAnFragment = new HienThiBanAnFragment();
                FragmentTransaction ftHienThiBanAn = fragmentManager.beginTransaction();
                ftHienThiBanAn.replace(R.id.frContent, hienThiBanAnFragment);
                ftHienThiBanAn.commit();

                item.setChecked(true);
                drawerLayout.closeDrawers();
                break;

            case R.id.itThucDon:
                HienThiThucDonFragment hienThiThucDonFragment = new HienThiThucDonFragment();
                FragmentTransaction ftHienThiThucDon = fragmentManager.beginTransaction();
                ftHienThiThucDon.setCustomAnimations(R.anim.anim_vao_activity, R.anim.anim_ra_activity);
                ftHienThiThucDon.replace(R.id.frContent, hienThiThucDonFragment);
                ftHienThiThucDon.commit();

                item.setChecked(true);
                drawerLayout.closeDrawers();
                break;

            case R.id.itNhanVien:
                HienThiNhanVienFragment hienThiNhanVienFragment = new HienThiNhanVienFragment();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frContent, hienThiNhanVienFragment);
                fragmentTransaction.commit();

                item.setChecked(true);
                drawerLayout.closeDrawers();
                break;
        }
        return false;
    }
}
