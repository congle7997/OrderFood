package com.example.congl.orderfood.CustomAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.congl.orderfood.DTO.ThanhToanDTO;
import com.example.congl.orderfood.R;

import java.util.List;

public class CustomAdapterHienThiThanhToan extends BaseAdapter {

    Context context;
    int layout;
    List<ThanhToanDTO> ListThanhToanDTO;

    ViewHolderThanhToan viewHolderThanhToan;

    public CustomAdapterHienThiThanhToan(Context context, int layout, List<ThanhToanDTO> ListThanhToanDTO){
        this.context = context;
        this.layout = layout;
        this.ListThanhToanDTO = ListThanhToanDTO;
    }

    @Override
    public int getCount() {
        return ListThanhToanDTO.size();
    }

    @Override
    public Object getItem(int i) {
        return ListThanhToanDTO.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public class ViewHolderThanhToan{
        TextView txtTenMonAnThanhToan, txtSoLuongThanhToan, txtGiaTienThanhToan;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        View view = convertView;
        if (view == null) {
            viewHolderThanhToan = new ViewHolderThanhToan();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(layout, viewGroup, false);

            viewHolderThanhToan.txtTenMonAnThanhToan = view.findViewById(R.id.txtTenMonAnThanhToan);
            viewHolderThanhToan.txtGiaTienThanhToan = view.findViewById(R.id.txtGiaTienThanhToan);
            viewHolderThanhToan.txtSoLuongThanhToan = view.findViewById(R.id.txtSoLuongThanhToan);

            view.setTag(viewHolderThanhToan);
        }
        else {
            viewHolderThanhToan = (ViewHolderThanhToan) view.getTag();
        }

        ThanhToanDTO thanhToanDTO = ListThanhToanDTO.get(i);

        viewHolderThanhToan.txtTenMonAnThanhToan.setText(thanhToanDTO.getTenMonAn());
        viewHolderThanhToan.txtSoLuongThanhToan.setText(String.valueOf(thanhToanDTO.getSoLuong()));
        viewHolderThanhToan.txtGiaTienThanhToan.setText(String.valueOf(thanhToanDTO.getGiaTien()));

        return view;
    }
}
