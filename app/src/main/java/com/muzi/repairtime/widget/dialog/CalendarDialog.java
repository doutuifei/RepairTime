package com.muzi.repairtime.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Window;
import android.view.WindowManager;

import com.muzi.repairtime.R;
import com.muzi.repairtime.databinding.LayoutDialogCalendarBinding;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

/**
 * 作者: lipeng
 * 时间: 2019/3/26
 * 邮箱: lipeng@moyi365.com
 * 功能:
 */
public abstract class CalendarDialog extends Dialog {

    private Context context;
    private int requestCode = -1;
    private LayoutDialogCalendarBinding binding;

    public CalendarDialog(Context context) {
        super(context, R.style.CustomDialog);
        this.context = context;
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.layout_dialog_calendar, null, false);
        setContentView(binding.getRoot());
        initAttribute();
        initView();
    }

    private void initView() {
        binding.calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                onSelect(requestCode, date.getYear() + "/" + date.getMonth() + "/" + date.getDay());
                dismiss();
            }
        });
    }

    private void initAttribute() {
        setCancelable(true);
        setCanceledOnTouchOutside(true);

        Window window = getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.gravity = Gravity.CENTER;
        window.setAttributes(layoutParams);
    }

    public void show(int requestCode) {
        this.requestCode = requestCode;
        show();
    }

    @Override
    public void show() {
        try {
            if (context != null) {
                super.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dismiss() {
        try {
            if (context != null) {
                super.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public abstract void onSelect(int requestCode, String date);

}
