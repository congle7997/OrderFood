package com.example.congl.orderfood.CustomAdapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.congl.orderfood.DAO.LoaiMonAnDAO;
import com.example.congl.orderfood.DTO.LoaiMonAnDTO;
import com.example.congl.orderfood.R;

import java.util.List;

public class CustomAdapterHienThiLoaiMonAnThucDon extends BaseAdapter {

    Context context;
    int layout;
    List<LoaiMonAnDTO> ListLoaiMonAnDTO;
    LoaiMonAnDAO loaiMonAnDAO;

    ViewHolderHienThiLoaiThucDon viewHolderHienThiLoaiThucDon;

    public CustomAdapterHienThiLoaiMonAnThucDon(Context context, int layout, List<LoaiMonAnDTO> ListLoaiMonAnDTO){
        this.context = context;
        this.layout = layout;
        this.ListLoaiMonAnDTO = ListLoaiMonAnDTO;
        loaiMonAnDAO = new LoaiMonAnDAO(context);
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

    public class ViewHolderHienThiLoaiThucDon{
        ImageView imgHinhLoaiThucDon;
        TextView txtTenLoaiThucDon;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        View view = convertView;
        if (view == null){
            viewHolderHienThiLoaiThucDon = new ViewHolderHienThiLoaiThucDon();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(layout, viewGroup, false);

            viewHolderHienThiLoaiThucDon.imgHinhLoaiThucDon = view.findViewById(R.id.imgHinhLoaiThucDon);
            viewHolderHienThiLoaiThucDon.txtTenLoaiThucDon = view.findViewById(R.id.txtTenLoaiThucDon);

            view.setTag(viewHolderHienThiLoaiThucDon);
        }
        else {
            viewHolderHienThiLoaiThucDon = (ViewHolderHienThiLoaiThucDon) view.getTag();
        }

        LoaiMonAnDTO loaiMonAnDTO = ListLoaiMonAnDTO.get(i);
        int maLoai = loaiMonAnDTO.getMaLoai();
        String hinhAnh = loaiMonAnDAO.layHinhLoaiMonAn(maLoai);

        Uri uri = Uri.parse(hinhAnh);
        viewHolderHienThiLoaiThucDon.txtTenLoaiThucDon.setText(loaiMonAnDTO.getTenLoai());
        viewHolderHienThiLoaiThucDon.imgHinhLoaiThucDon.setImageURI(uri);

        return view;
    }
}
