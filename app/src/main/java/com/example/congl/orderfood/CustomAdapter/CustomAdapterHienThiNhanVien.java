package com.example.congl.orderfood.CustomAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.congl.orderfood.DTO.NhanVienDTO;
import com.example.congl.orderfood.R;

import java.util.List;

public class CustomAdapterHienThiNhanVien extends BaseAdapter {

    Context context;
    int layout;
    List<NhanVienDTO> ListNhanVienDTO;

    ViewHolderNhanVien viewHolderNhanVien;

    public CustomAdapterHienThiNhanVien(Context context, int layout, List<NhanVienDTO> ListNhanVienDTO){
        this.context = context;
        this.layout = layout;
        this.ListNhanVienDTO = ListNhanVienDTO;
    }

    @Override
    public int getCount() {
        return ListNhanVienDTO.size();
    }

    @Override
    public Object getItem(int i) {
        return ListNhanVienDTO.get(i);
    }

    @Override
    public long getItemId(int i) {
        return ListNhanVienDTO.get(i).getMANV();
    }

    public class ViewHolderNhanVien{
        ImageView imgHinhNhanVien;
        TextView txtTenNhanVien, txtCMND;
    }

    @Override
    //mỗi 1 getView là 1 đối tượng ListNhanVienDTO
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        View view = convertView;
        if (view == null){
            viewHolderNhanVien = new ViewHolderNhanVien();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(layout, viewGroup, false);

            viewHolderNhanVien.imgHinhNhanVien = view.findViewById(R.id.imgHinhNhanVien);
            viewHolderNhanVien.txtTenNhanVien = view.findViewById(R.id.txtTenNhanVien);
            viewHolderNhanVien.txtCMND = view.findViewById(R.id.txtCMND);

            view.setTag(viewHolderNhanVien);
        }
        else {
            viewHolderNhanVien = (ViewHolderNhanVien) view.getTag();
        }

        NhanVienDTO nhanVienDTO = ListNhanVienDTO.get(i);
        viewHolderNhanVien.txtTenNhanVien.setText(nhanVienDTO.getTENDN());
        //setText() bắt buộc truyền vào phải là kiểu String
        viewHolderNhanVien.txtCMND.setText(String.valueOf(nhanVienDTO.getCMND()));

        return view;
    }
}
