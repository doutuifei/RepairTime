package com.muzi.repairtime.bindingadapter;

import android.databinding.BindingAdapter;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.muzi.repairtime.command.BindingCommand;

import io.reactivex.Observable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;

/**
 * 作者: lipeng
 * 时间: 2019/3/8
 * 邮箱: lipeng@moyi365.com
 * 功能:
 */
public class TextViewAdapter {

    /**
     * TextWatcher
     *
     * @param textView
     * @param bindingCommand
     */
    @BindingAdapter(value = {"onTextChanged"}, requireAll = false)
    public static void setTextChanged(TextView textView, final BindingCommand<String> bindingCommand) {
        RxTextView.textChanges(textView)
                .subscribe(new Consumer<CharSequence>() {
                    @Override
                    public void accept(CharSequence charSequence) throws Exception {
                        if (bindingCommand != null) {
                            bindingCommand.execute(charSequence.toString());
                        }
                    }
                });
    }

    /**
     * Textview输入内容失去焦点时回调
     *
     * @param textView
     * @param bindingCommand
     */
    @BindingAdapter(value = {"onTextLostFocus"}, requireAll = false)
    public static void setTextLostFocus(TextView textView, final BindingCommand<String> bindingCommand) {
        Observable.zip(
                RxTextView.textChanges(textView),
                RxView.focusChanges(textView),
                new BiFunction<CharSequence, Boolean, String>() {
                    @Override
                    public String apply(CharSequence charSequence, Boolean aBoolean) throws Exception {
                        if (!aBoolean) {
                            return charSequence.toString();
                        }
                        return "";
                    }
                })
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        if (bindingCommand != null) {
                            bindingCommand.execute(s);
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
