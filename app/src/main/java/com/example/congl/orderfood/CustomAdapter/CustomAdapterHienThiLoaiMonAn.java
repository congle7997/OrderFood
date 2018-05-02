package com.example.congl.orderfood.CustomAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.congl.orderfood.DTO.LoaiMonAnDTO;
import com.example.congl.orderfood.R;

import java.util.List;

/**
 * Created by congl on 07-Apr-18.
 */

public class CustomAdapterHienThiLoaiMonAn extends BaseAdapter {

    Context context;
    int layout;
    List<LoaiMonAnDTO> ListLoaiMonAnDTO;

    ViewHolderLoaiMonAn viewHolderLoaiMonAn;

    public CustomAdapterHienThiLoaiMonAn(Context context, int layout, List<LoaiMonAnDTO> ListLoaiMonAnDTO){
        this.context = context;
        this.layout = layout;
        this.ListLoaiMonAnDTO = ListLoaiMonAnDTO;
    }

    @Override
    public int getCount() {
        return ListLoaiMonAnDTO.size();
    }

    @Override
    public Object getItem(int i) {
        return ListLoaiMonAnDTO.get(i);
    }

    @Override
    public long getItemId(int i) {
        return ListLoaiMonAnDTO.get(i).getMaLoai();
    }

    public class ViewHolderLoaiMonAn{
        TextView txtTenLoai;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null){
            viewHolderLoaiMonAn = new ViewHolderLoaiMonAn();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.custom_layout_spiner_loai_thuc_don, parent, false);

            viewHolderLoaiMonAn.txtTenLoai = view.findViewById(R.id.txtTenLoai);

            view.setTag(viewHolderLoaiMonAn);
        }
        else {
            viewHolderLoaiMonAn = (ViewHolderLoaiMonAn) view.getTag();

        }

        LoaiMonAnDTO loaiMonAnDTO = ListLoaiMonAnDTO.get(position);
        viewHolderLoaiMonAn.txtTenLoai.setText(loaiMonAnDTO.getTenLoai());
        viewHolderLoaiMonAn.txtTenLoai.setTag(loaiMonAnDTO.getMaLoai());

        return view;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        View view = convertView;
        if (view == null){
            viewHolderLoaiMonAn = new ViewHolderLoaiMonAn();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.custom_layout_spiner_loai_thuc_don, viewGroup, false);

            viewHolderLoaiMonAn.txtTenLoai = view.findViewById(R.id.txtTenLoai);

            view.setTag(viewHolderLoaiMonAn);
        }
        else {
            viewHolderLoaiMonAn = (ViewHolderLoaiMonAn) view.getTag();

        }

        LoaiMonAnDTO loaiMonAnDTO = ListLoaiMonAnDTO.get(i);
        viewHolderLoaiMonAn.txtTenLoai.setText(loaiMonAnDTO.getTenLoai());
        viewHolderLoaiMonAn.txtTenLoai.setTag(loaiMonAnDTO.getMaLoai());

        return view;
    }
}
