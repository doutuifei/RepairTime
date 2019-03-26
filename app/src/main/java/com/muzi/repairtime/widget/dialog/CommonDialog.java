package com.muzi.repairtime.widget.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.muzi.repairtime.R;
import com.muzi.repairtime.utils.StringUtils;

/**
 * 作者: lipeng
 * 时间: 2019/3/26
 * 邮箱: lipeng@moyi365.com
 * 功能:
 */
public class CommonDialog extends Dialog {

    private OnDialogClickListener listener;
    private Builder builder;
    private Context mContext;
    private TextView tvConfirm;
    private TextView tvCancel;
    private TextView tvContent;

    private CommonDialog(Context context, Builder builder, OnDialogClickListener listener) {
        super(context, R.style.CustomDialog);
        setContentView(R.layout.layout_dialog_common);
        this.mContext = context;
        this.builder = builder;
        this.listener = listener;
        initView();
        initEvents();
        initAttributes();
    }

    private void initView() {
        TextView tvTitle = (TextView) findViewById(R.id.tv_title);
        View divider = (View) findViewById(R.id.tv_divider);
        tvContent = (TextView) findViewById(R.id.tv_content);
        tvCancel = (TextView) findViewById(R.id.tv_cancel);
        tvConfirm = (TextView) findViewById(R.id.tv_confirm);
        if (builder != null) {
            if (StringUtils.isEmpty(builder.title)) {
                tvTitle.setVisibility(View.GONE);
            } else {
                if (StringUtils.isNotEmpty(builder.title)) {
                    tvTitle.setText(builder.title);
                }
            }
            if (!builder.showCancelButton) {
                divider.setVisibility(View.GONE);
                tvCancel.setVisibility(View.GONE);
            } else {
                if (StringUtils.isNotEmpty(builder.cancelName)) {
                    tvCancel.setText(builder.cancelName);
                }
            }
            if (StringUtils.isNotEmpty(builder.confirmName)) {
                tvConfirm.setText(builder.confirmName);
            }
            tvContent.setText(builder.content);
        }

    }

    private void initEvents() {
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                if (listener != null)
                    listener.onCancelClick(view, CommonDialog.this);
            }
        });

        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                if (listener != null)
                    listener.onConfirmClick(view, CommonDialog.this);
            }
        });
    }

    private void initAttributes() {
        if (builder != null) {
            setCancelable(builder.cancelable);
            setCanceledOnTouchOutside(builder.canceledOnTouchOutside);
        }
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
    }

    public static class Builder {
        private boolean canceledOnTouchOutside = false;
        private boolean showCancelButton = true;
        private boolean cancelable = false;
        private String confirmName;
        private String cancelName;
        private Context mContext;
        private String content;
        private String title;

        public CommonDialog build(OnDialogClickListener listener) {
            return new CommonDialog(mContext, this, listener);
        }

        public Builder(Context context) {
            this.mContext = context;
        }

        public Builder setContent(String content) {
            this.content = content;
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setCancelName(String cancelName) {
            this.cancelName = cancelName;
            return this;
        }

        public Builder setConfirmName(String confirmName) {
            this.confirmName = confirmName;
            return this;
        }

        public Builder setCancelable(boolean cancelable) {
            this.cancelable = cancelable;
            return this;
        }

        public Builder setCanceledOnTouchOutside(boolean canceledOnTouchOutside) {
            this.canceledOnTouchOutside = canceledOnTouchOutside;
            return this;
        }

        public Builder setShowCancelButton(boolean showCancelButton) {
            this.showCancelButton = showCancelButton;
            return this;
        }
    }

    public void setContent(String content) {
        tvContent.setText(content);
    }


    @Override
    public void show() {
        if (mContext != null && !((Activity) mContext).isFinishing()) {
            super.show();
        }
    }

    @Override
    public void dismiss() {
        if (mContext != null && !((Activity) mContext).isFinishing()) {
            super.dismiss();
        }
    }

    public interface OnDialogClickListener {

        void onCancelClick(View v, Dialog dialog);

        void onConfirmClick(View v, Dialog dialog);
    }

}
