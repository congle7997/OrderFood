package com.example.congl.orderfood.FragmentApp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.congl.orderfood.R;

import java.util.Calendar;

/**
 * Created by congl on 23-Mar-18.
 */

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    @Override
    //khởi tạo Dialog
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar calendar = Calendar.getInstance();
        int iNam = calendar.get(Calendar.YEAR);
        int iThang = calendar.get(Calendar.MONTH);
        int iNgay = calendar.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), this, iNgay, iThang, iNam);
    }

    @Override
    //set thời gian được chọn
    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
        EditText edtNgaySinh = getActivity().findViewById(R.id.edtNgaySinhDK);
        String sNgaySinh = dayOfMonth + "/" + (monthOfYear+1) + "/" + year;
        edtNgaySinh.setText(sNgaySinh);
    }
}
