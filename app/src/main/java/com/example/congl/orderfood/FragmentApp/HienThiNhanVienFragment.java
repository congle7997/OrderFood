package com.example.congl.orderfood.FragmentApp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.congl.orderfood.CustomAdapter.CustomAdapterHienThiNhanVien;
import com.example.congl.orderfood.DAO.NhanVienDAO;
import com.example.congl.orderfood.DTO.NhanVienDTO;
import com.example.congl.orderfood.DangKyActivity;
import com.example.congl.orderfood.R;

import java.util.List;

public class HienThiNhanVienFragment extends Fragment {

    ListView lvNhanvien;

    NhanVienDAO nhanVienDAO;
    List<NhanVienDTO> ListNhanVienDTO;

    SharedPreferences sharedPreferences;

    int maQuyen;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_hien_thi_nhan_vien, container, false);

        setHasOptionsMenu(true);

        lvNhanvien = view.findViewById(R.id.lvNhanVien);

        nhanVienDAO = new NhanVienDAO(getActivity());

        hienThiDanhSachNhanVien();

        sharedPreferences = getActivity().getSharedPreferences("LUUQUYEN", Context.MODE_PRIVATE);
        maQuyen = sharedPreferences.getInt("MAQUYEN", 0);

        if (maQuyen == 1) {
            registerForContextMenu(lvNhanvien);
        }

        return view;
    }

    private void hienThiDanhSachNhanVien() {
        ListNhanVienDTO = nhanVienDAO.layDanhSachNhanVien();

        CustomAdapterHienThiNhanVien customAdapterHienThiNhanVien = new CustomAdapterHienThiNhanVien(getActivity(), R.layout.custom_layout_hien_thi_nhan_vien, ListNhanVienDTO);
        lvNhanvien.setAdapter(customAdapterHienThiNhanVien);
        customAdapterHienThiNhanVien.notifyDataSetChanged();
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getActivity().getMenuInflater().inflate(R.menu.menu_edit_context, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId();
        AdapterView.AdapterContextMenuInfo adapterContextMenuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int viTri = adapterContextMenuInfo.position;
        int maNhanVien = ListNhanVienDTO.get(viTri).getMANV();

        switch (id){
            case R.id.itSua:
                Intent intentDangKy = new Intent(getActivity(), DangKyActivity.class);
                intentDangKy.putExtra("MANHANVIEN", maNhanVien);
                startActivity(intentDangKy);
                break;

            case R.id.itXoa:
                boolean kiemTra = nhanVienDAO.xoaNhanVien(maNhanVien);
                if (kiemTra){
                    hienThiDanhSachNhanVien();
                    Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.xoa_thanh_cong), Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.loi), Toast.LENGTH_SHORT).show();
                }
                break;
        }

        return true;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        if (maQuyen == 1){
            MenuItem itThemNhanVien = menu.add(1, R.id.itThemNhanVien, 1, R.string.them_nhan_vien);
            itThemNhanVien.setIcon(R.drawable.nhanvien);
            itThemNhanVien.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.itThemNhanVien:
                Intent intentDangKy = new Intent(getActivity(), DangKyActivity.class);
                startActivity(intentDangKy);
                break;
        }
        return true;
    }
}
