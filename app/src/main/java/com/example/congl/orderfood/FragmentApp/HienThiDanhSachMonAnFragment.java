package com.example.congl.orderfood.FragmentApp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.congl.orderfood.CustomAdapter.CustomAdapterHienThiDanhSachMonAn;
import com.example.congl.orderfood.DAO.MonAnDAO;
import com.example.congl.orderfood.DTO.MonAnDTO;
import com.example.congl.orderfood.R;
import com.example.congl.orderfood.SoLuongActivity;

import java.util.List;

public class HienThiDanhSachMonAnFragment extends Fragment {

    GridView gridView;

    MonAnDAO monAnDAO;
    List<MonAnDTO> ListMonAnDTO;
    CustomAdapterHienThiDanhSachMonAn customAdapterHienThiDanhSachMonAn;

    int maBan;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //có thể tái sử dụng lại layout, quan trọng ở layout custom
        View view = inflater.inflate(R.layout.layout_hien_thi_thuc_don, container, false);

        gridView = view.findViewById(R.id.gvHienThiThucDon);

        monAnDAO = new MonAnDAO(getActivity());

        Bundle bundle = getArguments();
        if (bundle != null){
            int maLoai = bundle.getInt("MALOAI");
            maBan = bundle.getInt("MABAN");
            ListMonAnDTO = monAnDAO.layDanhSachMonAnTheoLoai(maLoai);

            customAdapterHienThiDanhSachMonAn = new CustomAdapterHienThiDanhSachMonAn(getActivity(), R.layout.custom_layout_hien_thi_danh_sach_mon_an, ListMonAnDTO);
            gridView.setAdapter(customAdapterHienThiDanhSachMonAn);
            customAdapterHienThiDanhSachMonAn.notifyDataSetChanged();

            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                    //khi có mã bàn thì mới cho nhập số lượng món, tránh trường hợp người dùng vào xem cũng bắt nhập số lượng
                    if (maBan != 0){
                        //popup là Activity, mà Activity thì dùng Intent
                        Intent intentSoluong = new Intent(getActivity(), SoLuongActivity.class);
                        intentSoluong.putExtra("MABAN", maBan);
                        //khi click vào item trên Gridview thì nó đã lấy được vị trí click
                        intentSoluong.putExtra("MAMONAN", ListMonAnDTO.get(position).getMaMonAn());
                        //không cần startActivityForResult vì không cần load lại dữ liệu
                        startActivity(intentSoluong);
                    }
                }
            });
        }

        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (keyEvent.getAction() == KeyEvent.ACTION_DOWN){
                    getFragmentManager().popBackStack("QUAYVE", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                }
                return false;
            }
        });

        return view;
    }
}
