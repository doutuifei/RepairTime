package com.muzi.repairtime.richeditor.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.muzi.repairtime.R;
import com.muzi.repairtime.databinding.FragmentEditTableBinding;


/**
 * Edit Table Fragment
 * Created by even.wu on 10/8/17.
 */

public class EditTableFragment extends Fragment {

    private FragmentEditTableBinding bind;

    private OnTableListener mOnTableListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        bind = DataBindingUtil.inflate(inflater, R.layout.fragment_edit_table, container, false);
        return bind.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initListener();
    }

    private void initListener() {
        bind.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().remove(EditTableFragment.this).commit();
            }
        });
        bind.btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnTableListener != null) {
                    mOnTableListener.onTableOK(Integer.valueOf(bind.etRows.getText().toString()),
                            Integer.valueOf(bind.etCols.getText().toString()));
                    getFragmentManager().beginTransaction().remove(EditTableFragment.this).commit();
                }
            }
        });
    }

    public void setOnTableListener(OnTableListener mOnTableListener) {
        this.mOnTableListener = mOnTableListener;
    }

    public interface OnTableListener {
        void onTableOK(int rows, int cols);
    }
}
