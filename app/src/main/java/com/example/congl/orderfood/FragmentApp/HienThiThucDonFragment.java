package com.example.congl.orderfood.FragmentApp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.congl.orderfood.CustomAdapter.CustomAdapterHienThiLoaiMonAnThucDon;
import com.example.congl.orderfood.DAO.LoaiMonAnDAO;
import com.example.congl.orderfood.DTO.LoaiMonAnDTO;
import com.example.congl.orderfood.R;
import com.example.congl.orderfood.ThemThucDonActivity;
import com.example.congl.orderfood.TrangChuActivity;

import java.util.List;

/**
 * Created by congl on 01-Apr-18.
 */

public class HienThiThucDonFragment extends Fragment {

    GridView gvHienThiThucDon;
    LoaiMonAnDAO loaiMonAnDAO;
    List<LoaiMonAnDTO> ListLoaiMonAnDTO;

    FragmentManager fragmentManager;
    SharedPreferences sharedPreferences;

    int maBan;
    int maQuyen;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_hien_thi_thuc_don, container, false);
        setHasOptionsMenu(true);
        //lấy ActionBar ở Activity mà Fragment được add vào mà ở đây là TrangChuActivity nên phải ép kiểu về
        ((TrangChuActivity)getActivity()).getSupportActionBar().setTitle(R.string.thuc_don);

        gvHienThiThucDon = view.findViewById(R.id.gvHienThiThucDon);

        fragmentManager = getActivity().getSupportFragmentManager();

        loaiMonAnDAO = new LoaiMonAnDAO(getActivity());
        ListLoaiMonAnDTO = loaiMonAnDAO.layDanhSachLoaiMonAn();

        CustomAdapterHienThiLoaiMonAnThucDon customAdapterHienThiLoaiMonAnThucDon = new CustomAdapterHienThiLoaiMonAnThucDon(getActivity(), R.layout.custom_layout_hien_loai_mon_an, ListLoaiMonAnDTO);
        gvHienThiThucDon.setAdapter(customAdapterHienThiLoaiMonAnThucDon);
        customAdapterHienThiLoaiMonAnThucDon.notifyDataSetChanged();

        Bundle bundleDuLieuThucDon = getArguments();
        if (bundleDuLieuThucDon != null){
            maBan = bundleDuLieuThucDon.getInt("MABAN");
        }

        sharedPreferences = getActivity().getSharedPreferences("LUUQUYEN", Context.MODE_PRIVATE);
        maQuyen = sharedPreferences.getInt("MAQUYEN", 0);

        gvHienThiThucDon.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                int maLoai = ListLoaiMonAnDTO.get(position).getMaLoai();
                HienThiDanhSachMonAnFragment hienThiDanhSachMonAnFragment = new HienThiDanhSachMonAnFragment();

                Bundle bundle = new Bundle();
                bundle.putInt("MALOAI", maLoai);
                bundle.putInt("MABAN", maBan);
                //setArguments dùng để truyền dữ liệu giữa 2 Fragment
                hienThiDanhSachMonAnFragment.setArguments(bundle);

                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frContent, hienThiDanhSachMonAnFragment).addToBackStack("QUAYVE");
                fragmentTransaction.commit();
            }
        });

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        if (maQuyen == 1){
            //Nhóm, menu item, vị trí, chuỗi
            MenuItem itThemThucDon = menu.add(1, R.id.itThemThucDon, 1, R.string.them_thuc_don);
            itThemThucDon.setIcon(R.drawable.logodangnhap);
            //hiển thị icon nếu còn chỗ trống
            itThemThucDon.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.itThemThucDon:
                Intent intentThemThucDon = new Intent(getActivity(), ThemThucDonActivity.class);
                startActivity(intentThemThucDon);

                getActivity().overridePendingTransition(R.anim.anim_vao_activity, R.anim.anim_ra_activity);

                break;
        }
        return super.onOptionsItemSelected(item);
    }
}