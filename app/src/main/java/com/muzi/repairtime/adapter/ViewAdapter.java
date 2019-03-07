package com.muzi.repairtime.adapter;

import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.muzi.repairtime.command.BindingCommand;
import com.muzi.repairtime.utils.ViewUtils;

/**
 * 作者: lipeng
 * 时间: 2019/3/6
 * 邮箱: lipeng@moyi365.com
 * 功能:
 */
public class ViewAdapter {

    /**
     * 加载图片
     *
     * @param imageView
     * @param url
     * @param placeholder
     */
    @SuppressWarnings("unchecked")
    @BindingAdapter(value = {"url", "placeholder"}, requireAll = false)
    public static void setImageUri(ImageView imageView, String url, Drawable placeholder) {
        //使用Glide框架加载图片
        Glide.with(imageView.getContext())
                .load(url)
                .apply(new RequestOptions().placeholder(placeholder))
                .into(imageView);
    }

    /**
     * CheckBox切换
     *
     * @param checkBox
     * @param bindingCommand
     */
    @SuppressWarnings("unchecked")
    @BindingAdapter(value = {"onCheckedChange"}, requireAll = false)
    public static void setCheckedChanged(final CheckBox checkBox, final BindingCommand<Boolean> bindingCommand) {
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (bindingCommand != null) {
                    bindingCommand.execute(b);
                }
            }
        });
    }

    /**
     * 焦点变化
     *
     * @param view
     * @param bindingCommand
     */
    @BindingAdapter(value = {"onFocusChanged"}, requireAll = false)
    public static void setOnFocusChanged(final View view, final BindingCommand<Boolean> bindingCommand) {
        view.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (bindingCommand != null) {
                    bindingCommand.execute(hasFocus);
                }
                if (hasFocus) {
                    if (view instanceof EditText) {
                        ViewUtils.setSelection((EditText) view);
                    }
                }
            }
        });
    }

    /**
     * TextWatcher
     *
     * @param textView
     * @param bindingCommand
     */
    @BindingAdapter(value = {"onTextChanged"}, requireAll = false)
    public static void setTextChanged(TextView textView, final BindingCommand<String> bindingCommand) {
        textView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (bindingCommand != null) {
                    bindingCommand.execute(s.toString());
                }
            }
        });
    }

    /**
     * 软键盘搜索按钮
     *
     * @param textView
     * @param bindingCommand
     */
    @BindingAdapter(value = {"OnEditorAction"}, requireAll = false)
    public static void setEditorAction(TextView textView, final BindingCommand<String> bindingCommand) {
        textView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_NULL) {
                    String keyWord = v.getText().toString().trim();
                    if (bindingCommand != null) {
                        bindingCommand.execute(keyWord);
                    }
                    return false;
                }
                return true;
            }
        });
    }

}
