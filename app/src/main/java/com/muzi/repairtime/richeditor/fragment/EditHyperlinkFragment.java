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
import com.muzi.repairtime.databinding.FragmentEditHyperlinkBinding;


public class EditHyperlinkFragment extends Fragment {

    private FragmentEditHyperlinkBinding bind;
    private OnHyperlinkListener mOnHyperlinkListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        bind = DataBindingUtil.inflate(inflater, R.layout.fragment_edit_hyperlink, container, false);
        return bind.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bind.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().remove(EditHyperlinkFragment.this).commit();
            }
        });
        bind.btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnHyperlinkListener != null) {
                    mOnHyperlinkListener.onHyperlinkOK(bind.etDisplayText.getText().toString(),
                            bind.etAddress.getText().toString());
                    getFragmentManager().beginTransaction().remove(EditHyperlinkFragment.this).commit();
                }
            }
        });
    }

    public void setOnHyperlinkListener(OnHyperlinkListener mOnHyperlinkListener) {
        this.mOnHyperlinkListener = mOnHyperlinkListener;
    }

    public interface OnHyperlinkListener {
        void onHyperlinkOK(String address, String text);
    }
}
