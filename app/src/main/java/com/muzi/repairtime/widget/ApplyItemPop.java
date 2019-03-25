package com.muzi.repairtime.widget;

import android.content.Context;
import android.view.View;
import android.widget.RadioGroup;

import com.muzi.repairtime.R;
import com.muzi.repairtime.widget.easypop.EasyPopup;

/**
 * 作者: lipeng
 * 时间: 2019/3/22
 * 邮箱: lipeng@moyi365.com
 * 功能:
 */
public class ApplyItemPop extends EasyPopup {

    private CheckedChangeListener changeListener;

    public void setChangeListener(CheckedChangeListener changeListener) {
        this.changeListener = changeListener;
    }

    public ApplyItemPop(Context context) {
        super(context);
    }

    @Override
    protected void initAttributes() {
        super.initAttributes();
        setContentView(R.layout.layout_pop_apply);
    }

    @Override
    protected void initViews(View view, EasyPopup popup) {
        super.initViews(view, popup);
        RadioGroup radioGroup = view.findViewById(R.id.rg);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (group.getCheckedRadioButtonId()) {
                    case R.id.tb1:
                        if (changeListener != null) {
                            changeListener.onChecked(0);
                        }
                        break;
                    case R.id.tb2:
                        if (changeListener != null) {
                            changeListener.onChecked(1);
                        }
                        break;
                    case R.id.tb3:
                        if (changeListener != null) {
                            changeListener.onChecked(2);
                        }
                        break;
                    case R.id.tb4:
                        if (changeListener != null) {
                            changeListener.onChecked(3);
                        }
                        break;
                    case R.id.tb5:
                        if (changeListener != null) {
                            changeListener.onChecked(4);
                        }
                        break;
                }
                dismiss();
            }
        });
    }

    public interface CheckedChangeListener {
        void onChecked(int position);
    }
}
