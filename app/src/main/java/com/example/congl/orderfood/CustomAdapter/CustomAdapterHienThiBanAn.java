package com.example.congl.orderfood.CustomAdapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.congl.orderfood.DAO.BanAnDAO;
import com.example.congl.orderfood.DAO.GoiMonDAO;
import com.example.congl.orderfood.DTO.BanAnDTO;
import com.example.congl.orderfood.DTO.GoiMonDTO;
import com.example.congl.orderfood.FragmentApp.HienThiThucDonFragment;
import com.example.congl.orderfood.R;
import com.example.congl.orderfood.ThanhToanActivity;
import com.example.congl.orderfood.TrangChuActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Created by congl on 31-Mar-18.
 */

public class CustomAdapterHienThiBanAn extends BaseAdapter implements View.OnClickListener {

    Context context;
    int layout;
    List<BanAnDTO> ListBanAnDTO;

    BanAnDAO banAnDAO;
    GoiMonDAO goiMonDAO;
    FragmentManager fragmentManager;

    ViewHolderBanAn viewHolderBanAn;

    //truyền vào Activity gọi Adapter này, layout custom, dữ liệu
    public CustomAdapterHienThiBanAn(Context context, int layout, List<BanAnDTO> ListBanAnDTO){
        this.context = context;
        this.layout = layout;
        this.ListBanAnDTO = ListBanAnDTO;

        banAnDAO = new BanAnDAO(context);
        goiMonDAO = new GoiMonDAO(context);
        fragmentManager = ((TrangChuActivity)context).getSupportFragmentManager();
    }

    @Override
    public int getCount() {
        return ListBanAnDTO.size();
    }

    @Override
    //trả ra 1 đối tượng BanAnDTO
    public Object getItem(int i) {
        return ListBanAnDTO.get(i);
    }

    @Override
    public long getItemId(int i) {
        return ListBanAnDTO.get(i).getMaBan();
    }

    //tạo ViewHolder để tránh việc findViewByID() nhiều lần
    public class ViewHolderBanAn{
        TextView txtTenBanAn;
        ImageView imgBanAn, imgGoiMon, imgThanhToan, imgAnButton;
    }

    @Override
    //mỗi 1 dòng là 1 getView, mỗi 1 đối tượng là 1 convertView
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        View view = convertView;
        if (view == null){
            //tạo layout
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //khởi tạo layout cho view
            view = layoutInflater.inflate(R.layout.custom_layout_hien_thi_ban_an, viewGroup, false);
            viewHolderBanAn = new ViewHolderBanAn();
            viewHolderBanAn.txtTenBanAn = view.findViewById(R.id.txtTenBanAn);
            viewHolderBanAn.imgBanAn = view.findViewById(R.id.imgBanAn);
            viewHolderBanAn.imgGoiMon = view.findViewById(R.id.imgGoiMon);
            viewHolderBanAn.imgThanhToan = view.findViewById(R.id.imgThanhToan);
            viewHolderBanAn.imgAnButton = view.findViewById(R.id.imgAnButton);

            //lưu trữ
            view.setTag(viewHolderBanAn);
        }
        else {
            viewHolderBanAn = (ViewHolderBanAn) view.getTag();
        }

        //kiểm tra nó được chọn chưa để tránh lỗi các button hiển thị lung tunng
        if (ListBanAnDTO.get(i).isDuocChon() == true){
            hienButton();
        }
        else {
            anButton(false);
        }

        //lấy đối tượng banAnDTO thứ i
        BanAnDTO banAnDTO = ListBanAnDTO.get(i);

        String tinhTrang = banAnDAO.layTinhTrangBanAnTheoMaBan(banAnDTO.getMaBan());
        if (tinhTrang.equals("true")){
            viewHolderBanAn.imgBanAn.setImageResource(R.drawable.banantrue);
        }
        else {
            viewHolderBanAn.imgBanAn.setImageResource(R.drawable.banan);
        }

        viewHolderBanAn.txtTenBanAn.setText(banAnDTO.getTenBan());
        //lưu vị trí imgBanAn thứ i vào Tag
        viewHolderBanAn.imgBanAn.setTag(i);

        viewHolderBanAn.imgBanAn.setOnClickListener(this);
        viewHolderBanAn.imgGoiMon.setOnClickListener(this);
        viewHolderBanAn.imgThanhToan.setOnClickListener(this);
        viewHolderBanAn.imgAnButton.setOnClickListener(this);

        return view;
    }

    private void hienButton(){
        viewHolderBanAn.imgGoiMon.setVisibility(View.VISIBLE);
        viewHolderBanAn.imgThanhToan.setVisibility(View.VISIBLE);
        viewHolderBanAn.imgAnButton.setVisibility(View.VISIBLE);

        //context: đại diện cho Activity hiển thị Fragment
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.anim_hien_thi_button_ban_an);
        viewHolderBanAn.imgGoiMon.setAnimation(animation);
        viewHolderBanAn.imgThanhToan.setAnimation(animation);
        viewHolderBanAn.imgAnButton.setAnimation(animation);
    }
    private void anButton(boolean hieuUng){
        if (hieuUng){
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.anim_an_button_ban_an);
            viewHolderBanAn.imgGoiMon.setAnimation(animation);
            viewHolderBanAn.imgThanhToan.setAnimation(animation);
            viewHolderBanAn.imgAnButton.setAnimation(animation);
        }

        viewHolderBanAn.imgGoiMon.setVisibility(View.INVISIBLE);
        viewHolderBanAn.imgThanhToan.setVisibility(View.INVISIBLE);
        viewHolderBanAn.imgAnButton.setVisibility(View.INVISIBLE);
    }

    @Override
    //view truyền vào chính là view được click
    public void onClick(View view) {
        int id = view.getId();
        viewHolderBanAn = (ViewHolderBanAn) ((View)view.getParent()).getTag();
        int viTri1 = (int) viewHolderBanAn.imgBanAn.getTag();
        int maBan = ListBanAnDTO.get(viTri1).getMaBan();
        switch (id){
            case R.id.imgBanAn:
                String tenBan = viewHolderBanAn.txtTenBanAn.getText().toString();
                //lấy vị trí imgBanAn thú i vừa lưu ở Tag
                int viTri = (int) view.getTag();
                ListBanAnDTO.get(viTri).setDuocChon(true);
                hienButton();
                break;

            case R.id.imgGoiMon:
                Intent intentTrangChu = ((TrangChuActivity)context).getIntent();
                int maNhanVien = intentTrangChu.getIntExtra("MANHANVIEN", 0);

                String tinhTrang = banAnDAO.layTinhTrangBanAnTheoMaBan(maBan);

                //nếu bàn mới ngồi vào thì mới sinh ra mã gọi món
                if (tinhTrang.equals("false")){
                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yy");
                    String ngayGoi = simpleDateFormat.format(calendar.getTime());

                    GoiMonDTO goiMonDTO = new GoiMonDTO();
                    goiMonDTO.setMaBan(maBan);
                    goiMonDTO.setMaNV(maNhanVien);
                    goiMonDTO.setNgayGoi(ngayGoi);
                    goiMonDTO.setTinhTrang("false");

                    long kiemTra = goiMonDAO.themGoiMon(goiMonDTO);

                    banAnDAO.capNhatTinhTrangBan(maBan, "true");

                    if (kiemTra == 0){
                        Toast.makeText(context, context.getResources().getString(R.string.them_that_bai), Toast.LENGTH_SHORT).show();
                    }
                }

                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                HienThiThucDonFragment hienThiThucDonFragment = new HienThiThucDonFragment();

                Bundle bundleDuLieuThucDon = new Bundle();
                bundleDuLieuThucDon.putInt("MABAN", maBan);

                hienThiThucDonFragment.setArguments(bundleDuLieuThucDon);

                fragmentTransaction.replace(R.id.frContent, hienThiThucDonFragment).addToBackStack("HIENTHIBANAN");
                fragmentTransaction.commit();

                break;

            case R.id.imgThanhToan:
                //khi chuyển muốn chuyển trang nếu là Activity thì dùng Intent, còn Fragment là FragmentTransaction
                Intent intentThanhToan = new Intent(context, ThanhToanActivity.class); //truyền context vì Adapter nhận vào context
                intentThanhToan.putExtra("MABAN", maBan);
                //context ở đây đại diện cho TrangChuActivity
                context.startActivity(intentThanhToan);
                break;

            case R.id.imgAnButton:
                anButton(true);
                break;
        }
    }
}