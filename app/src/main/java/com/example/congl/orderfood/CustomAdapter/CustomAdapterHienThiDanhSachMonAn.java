package com.example.congl.orderfood.CustomAdapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.congl.orderfood.DTO.MonAnDTO;
import com.example.congl.orderfood.R;

import java.util.List;

public class CustomAdapterHienThiDanhSachMonAn extends BaseAdapter {

    Context context;
    int layout;
    List<MonAnDTO> ListMonAnDTO;

    ViewHolderHienThiDanhSachMonAn viewHolderHienThiDanhSachMonAn;


    public CustomAdapterHienThiDanhSachMonAn(Context context, int layout, List<MonAnDTO> ListMonAnDTO){
        this.context = context;
        this.layout = layout;
        this.ListMonAnDTO = ListMonAnDTO;
    }

    @Override
    public int getCount() {
        return ListMonAnDTO.size();
    }

    @Override
    public Object getItem(int i) {
        return ListMonAnDTO.get(i);
    }

    @Override
    public long getItemId(int i) {
        return ListMonAnDTO.get(i).getMaMonAn();
    }

    public class ViewHolderHienThiDanhSachMonAn{
        ImageView imgHinhMonAn;
        TextView txtTenMonAn, txtGiaTien;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        View view = convertView;
        if (view == null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            viewHolderHienThiDanhSachMonAn = new ViewHolderHienThiDanhSachMonAn();
            view = layoutInflater.inflate(layout, viewGroup, false);

            viewHolderHienThiDanhSachMonAn.imgHinhMonAn = view.findViewById(R.id.imgHinhMonAn);
            viewHolderHienThiDanhSachMonAn.txtTenMonAn = view.findViewById(R.id.txtTenMonAn);
            viewHolderHienThiDanhSachMonAn.txtGiaTien = view.findViewById(R.id.txtGiaTien);

            view.setTag(viewHolderHienThiDanhSachMonAn);
        }
        else {
            viewHolderHienThiDanhSachMonAn = (ViewHolderHienThiDanhSachMonAn) view.getTag();
        }

        MonAnDTO monAnDTO = ListMonAnDTO.get(i);

        String hinhAnh = monAnDTO.getHinhAnh().toString();
        if (hinhAnh == null || hinhAnh.equals("")){
            viewHolderHienThiDanhSachMonAn.imgHinhMonAn.setImageResource(R.drawable.backgroundheader1);
        }
        else {
            Uri uri = Uri.parse(hinhAnh);
            viewHolderHienThiDanhSachMonAn.imgHinhMonAn.setImageURI(uri);
        }
        viewHolderHienThiDanhSachMonAn.txtTenMonAn.setText(monAnDTO.getTenMonAn());
        //context vì đang ở Custom Adapter
        viewHolderHienThiDanhSachMonAn.txtGiaTien.setText(context.getResources().getString(R.string.gia) + monAnDTO.getGiaTien());

        return view;
    }
}
