package com.example.congl.orderfood.FragmentApp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.congl.orderfood.CustomAdapter.CustomAdapterHienThiBanAn;
import com.example.congl.orderfood.DAO.BanAnDAO;
import com.example.congl.orderfood.DTO.BanAnDTO;
import com.example.congl.orderfood.R;
import com.example.congl.orderfood.SuaBanAnActivity;
import com.example.congl.orderfood.ThemBanAnActivity;
import com.example.congl.orderfood.TrangChuActivity;

import java.util.List;

/**
 * Created by congl on 25-Mar-18.
 */

public class HienThiBanAnFragment extends Fragment {

    public static int REQUEST_CODE_THEM = 111;
    public static int REQUEST_CODE_SUA = 123;


    GridView gvHienThiBanAn;
    List<BanAnDTO> ListBanAnDTO;
    BanAnDAO banAnDAO;
    CustomAdapterHienThiBanAn customAdapterHienThiBanAn;

    SharedPreferences sharedPreferences;

    int maQuyen = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_hien_thi_ban_an, container, false);
        setHasOptionsMenu(true);
        //lấy ActionBar ở Activity mà Fragment được add vào mà ở đây là TrangChuActivity nên phải ép kiểu về
        ((TrangChuActivity)getActivity()).getSupportActionBar().setTitle(R.string.trang_chu);

        gvHienThiBanAn = view.findViewById(R.id.gvHienThiBanAn);

        //getActivity() lấy Activity của Fragment được gán vào(TrangChuActivity)
        banAnDAO = new BanAnDAO(getActivity());

        sharedPreferences = getActivity().getSharedPreferences("LUUQUYEN", Context.MODE_PRIVATE);
        maQuyen = sharedPreferences.getInt("MAQUYEN", 0);

        hienThiDanhSachBanAn();

        if (maQuyen == 1) {
            registerForContextMenu(gvHienThiBanAn);
        }


        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        //maQuyen == 1 thì là ADMIN và khi đó mới khởi tạo menu
        if (maQuyen == 1) {
            //Nhóm, menu item, vị trí, chuỗi
            MenuItem itThemBanAn = menu.add(1, R.id.itThemBanAn, 1, R.string.them_ban_an);
            itThemBanAn.setIcon(R.drawable.thembanan);
            //hiển thị icon nếu còn chỗ trống
            itThemBanAn.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.itThemBanAn:
                //đối số 1 chính là TrangChuActivity vì HienThiBanAnFragment đã được gán vào TrangChuActivity
                Intent intentThemBanAn = new Intent(getActivity(), ThemBanAnActivity.class);
                startActivityForResult(intentThemBanAn, REQUEST_CODE_THEM);
                break;
        }

        return true;
    }

    private void hienThiDanhSachBanAn(){
        ListBanAnDTO = banAnDAO.layTatCaBanAn();

        customAdapterHienThiBanAn = new CustomAdapterHienThiBanAn(getActivity(), R.layout.custom_layout_hien_thi_ban_an, ListBanAnDTO);
        gvHienThiBanAn.setAdapter(customAdapterHienThiBanAn);
        customAdapterHienThiBanAn.notifyDataSetChanged();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        //bên Fragment thì phải có thêm getActivity()
        getActivity().getMenuInflater().inflate(R.menu.menu_edit_context, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        //AdapterContextMenuInfo: lấy ra thông tin ở vị trí mà thằng context menu được chọn ở trong Adapter
        AdapterView.AdapterContextMenuInfo adapterContextMenuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int viTri = adapterContextMenuInfo.position;
        int maBan = ListBanAnDTO.get(viTri).getMaBan();

        int id = item.getItemId();
        switch (id){
            case R.id.itSua:
                Intent intent = new Intent(getActivity(), SuaBanAnActivity.class);
                intent.putExtra("MABAN", maBan);
                startActivityForResult(intent, REQUEST_CODE_SUA);
                break;

            case R.id.itXoa:
                boolean kiemTra = banAnDAO.xoaBanAnTheoMaBan(maBan);
                if (kiemTra){
                    Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.xoa_thanh_cong), Toast.LENGTH_SHORT).show();
                    hienThiDanhSachBanAn();
                }
                else {
                    Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.loi), Toast.LENGTH_SHORT).show();
                }
        }
        return super.onContextItemSelected(item);
    }

    @Override
    //vì sử dụng startActivityForResult nên phải kế thừa phương thức onActivityResult
    //khi popup tắt đi sẽ chạy vào hàm này
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_THEM && resultCode == Activity.RESULT_OK){
            Intent intent = data;
            boolean kiemTra = intent.getBooleanExtra("KIEMTRA", false);
            if (kiemTra){
                Toast.makeText(getActivity(), getResources().getString(R.string.them_thanh_cong), Toast.LENGTH_SHORT).show();
                hienThiDanhSachBanAn();
            }
            else {
                Toast.makeText(getActivity(), getResources().getString(R.string.them_that_bai), Toast.LENGTH_SHORT).show();
            }
        }
        else if (requestCode == REQUEST_CODE_SUA && resultCode == Activity.RESULT_OK){
            Intent intent = data;
            boolean kiemTra = intent.getBooleanExtra("KIEMTRA", false);
            hienThiDanhSachBanAn();
            if (kiemTra){
                Toast.makeText(getActivity(), getResources().getString(R.string.sua_thanh_cong), Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(getActivity(), getResources().getString(R.string.loi), Toast.LENGTH_SHORT).show();
            }
        }
    }
}