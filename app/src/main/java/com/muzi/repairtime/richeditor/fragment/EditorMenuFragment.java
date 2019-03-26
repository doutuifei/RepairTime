package com.muzi.repairtime.richeditor.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.muzi.repairtime.R;
import com.muzi.repairtime.databinding.FragmentEditorMenuBinding;
import com.muzi.repairtime.richeditor.ActionType;
import com.muzi.repairtime.richeditor.interfaces.OnActionPerformListener;
import com.muzi.repairtime.richeditor.widget.ColorPaletteView;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Editor Menu Fragment
 * Created by even.wu on 8/8/17.
 */

public class EditorMenuFragment extends Fragment implements View.OnClickListener {

    private FragmentEditorMenuBinding bind;

    private OnActionPerformListener mActionClickListener;

    private Map<Integer, ActionType> mViewTypeMap = new HashMap<Integer, ActionType>() {
        {
            put(R.id.iv_action_bold, ActionType.BOLD);
            put(R.id.iv_action_italic, ActionType.ITALIC);
            put(R.id.iv_action_underline, ActionType.UNDERLINE);
            put(R.id.iv_action_strikethrough, ActionType.STRIKETHROUGH);
            put(R.id.iv_action_justify_left, ActionType.JUSTIFY_LEFT);
            put(R.id.iv_action_justify_center, ActionType.JUSTIFY_CENTER);
            put(R.id.iv_action_justify_right, ActionType.JUSTIFY_RIGHT);
            put(R.id.iv_action_justify_full, ActionType.JUSTIFY_FULL);
            put(R.id.iv_action_subscript, ActionType.SUBSCRIPT);
            put(R.id.iv_action_superscript, ActionType.SUPERSCRIPT);
            put(R.id.iv_action_insert_numbers, ActionType.ORDERED);
            put(R.id.iv_action_insert_bullets, ActionType.UNORDERED);
            put(R.id.iv_action_indent, ActionType.INDENT);
            put(R.id.iv_action_outdent, ActionType.OUTDENT);
            put(R.id.iv_action_code_view, ActionType.CODE_VIEW);
            put(R.id.iv_action_blockquote, ActionType.BLOCK_QUOTE);
            put(R.id.iv_action_code_block, ActionType.BLOCK_CODE);
            put(R.id.ll_normal, ActionType.NORMAL);
            put(R.id.ll_h1, ActionType.H1);
            put(R.id.ll_h2, ActionType.H2);
            put(R.id.ll_h3, ActionType.H3);
            put(R.id.ll_h4, ActionType.H4);
            put(R.id.ll_h5, ActionType.H5);
            put(R.id.ll_h6, ActionType.H6);
            put(R.id.iv_action_insert_image, ActionType.IMAGE);
            put(R.id.iv_action_insert_link, ActionType.LINK);
            put(R.id.iv_action_table, ActionType.TABLE);
            put(R.id.iv_action_line, ActionType.LINE);
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        bind = DataBindingUtil.inflate(inflater, R.layout.fragment_editor_menu, container, false);
        return bind.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initListener();
    }

    private void initView() {
        bind.cpvFontTextColor.setOnColorChangeListener(new ColorPaletteView.OnColorChangeListener() {
            @Override
            public void onColorChange(String color) {
                if (mActionClickListener != null) {
                    mActionClickListener.onActionPerform(ActionType.FORE_COLOR, color);
                }
            }
        });

        bind.cpvHighlightColor.setOnColorChangeListener(new ColorPaletteView.OnColorChangeListener() {
            @Override
            public void onColorChange(String color) {
                if (mActionClickListener != null) {
                    mActionClickListener.onActionPerform(ActionType.BACK_COLOR, color);
                }
            }
        });
    }

    private void initListener() {
        bind.llFontSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFontSettingFragment(FontSettingFragment.TYPE_SIZE);
            }
        });
        bind.llLineHeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFontSettingFragment(FontSettingFragment.TYPE_LINE_HGEIGHT);
            }
        });
        bind.tvFontName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFontSettingFragment(FontSettingFragment.TYPE_FONT_FAMILY);
            }
        });

        bind.ivActionBold.setOnClickListener(this);
        bind.ivActionItalic.setOnClickListener(this);
        bind.ivActionUnderline.setOnClickListener(this);
        bind.ivActionStrikethrough.setOnClickListener(this);
        bind.ivActionJustifyLeft.setOnClickListener(this);
        bind.ivActionJustifyCenter.setOnClickListener(this);
        bind.ivActionJustifyRight.setOnClickListener(this);
        bind.ivActionJustifyFull.setOnClickListener(this);
        bind.ivActionSubscript.setOnClickListener(this);
        bind.ivActionSuperscript.setOnClickListener(this);
        bind.ivActionInsertNumbers.setOnClickListener(this);
        bind.ivActionInsertBullets.setOnClickListener(this);
        bind.ivActionIndent.setOnClickListener(this);
        bind.ivActionOutdent.setOnClickListener(this);
        bind.ivActionBlockquote.setOnClickListener(this);
        bind.ivActionCodeView.setOnClickListener(this);
        bind.ivActionCodeBlock.setOnClickListener(this);
        bind.llNormal.setOnClickListener(this);
        bind.llH1.setOnClickListener(this);
        bind.llH2.setOnClickListener(this);
        bind.llH4.setOnClickListener(this);
        bind.llH5.setOnClickListener(this);
        bind.llH6.setOnClickListener(this);
        bind.ivActionInsertImage.setOnClickListener(this);
        bind.ivActionInsertLink.setOnClickListener(this);
        bind.ivActionTable.setOnClickListener(this);
        bind.ivActionLine.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (mActionClickListener == null) {
            return;
        }
        ActionType type = mViewTypeMap.get(v.getId());
        mActionClickListener.onActionPerform(type);
    }

    private void openFontSettingFragment(final int type) {
        FontSettingFragment fontSettingFragment = new FontSettingFragment();

        Bundle bundle = new Bundle();
        bundle.putInt(FontSettingFragment.TYPE, type);
        fontSettingFragment.setArguments(bundle);

        fontSettingFragment.setOnResultListener(new FontSettingFragment.OnResultListener() {
            @Override
            public void onResult(String result) {
                if (mActionClickListener != null) {
                    switch (type) {
                        case FontSettingFragment.TYPE_SIZE:
                            bind.tvFontSize.setText(result);
                            mActionClickListener.onActionPerform(ActionType.SIZE, result);
                            break;
                        case FontSettingFragment.TYPE_LINE_HGEIGHT:
                            bind.tvFontSpacing.setText(result);
                            mActionClickListener.onActionPerform(ActionType.LINE_HEIGHT, result);
                            break;
                        case FontSettingFragment.TYPE_FONT_FAMILY:
                            bind.tvFontName.setText(result);
                            mActionClickListener.onActionPerform(ActionType.FAMILY, result);
                            break;
                        default:
                            break;
                    }
                }
            }
        });

        FragmentManager fm = getFragmentManager();
        fm.beginTransaction()
                .add(R.id.fl_action, fontSettingFragment, FontSettingFragment.class.getName())
                .hide(EditorMenuFragment.this)
                .commit();
    }

    public void updateActionStates(final ActionType type, final boolean isActive) {
        bind.getRoot().post(new Runnable() {
            @Override
            public void run() {
                View view = null;
                for (Map.Entry<Integer, ActionType> e : mViewTypeMap.entrySet()) {
                    Integer key = e.getKey();
                    if (e.getValue() == type) {
                        view = bind.getRoot().findViewById(key);
                        break;
                    }
                }

                if (view == null) {
                    return;
                }

                switch (type) {
                    case BOLD:
                    case ITALIC:
                    case UNDERLINE:
                    case SUBSCRIPT:
                    case SUPERSCRIPT:
                    case STRIKETHROUGH:
                    case JUSTIFY_LEFT:
                    case JUSTIFY_CENTER:
                    case JUSTIFY_RIGHT:
                    case JUSTIFY_FULL:
                    case ORDERED:
                    case CODE_VIEW:
                    case UNORDERED:
                        if (isActive) {
                            ((ImageView) view).setColorFilter(
                                    ContextCompat.getColor(getContext(), R.color.colorAccent));
                        } else {
                            ((ImageView) view).setColorFilter(
                                    ContextCompat.getColor(getContext(), R.color.tintColor));
                        }
                        break;
                    case NORMAL:
                    case H1:
                    case H2:
                    case H3:
                    case H4:
                    case H5:
                    case H6:
                        if (isActive) {
                            view.setBackgroundResource(R.drawable.round_rectangle_blue);
                        } else {
                            view.setBackgroundResource(R.drawable.round_rectangle_white);
                        }
                        break;
                    default:
                        break;
                }
            }
        });
    }

    public void setActionClickListener(OnActionPerformListener mActionClickListener) {
        this.mActionClickListener = mActionClickListener;
    }

    public void updateActionStates(ActionType type, final String value) {
        switch (type) {
            case FAMILY:
                updateFontFamilyStates(value);
                break;
            case SIZE:
                updateFontStates(ActionType.SIZE, Double.valueOf(value));
                break;
            case FORE_COLOR:
            case BACK_COLOR:
                updateFontColorStates(type, value);
                break;
            case LINE_HEIGHT:
                updateFontStates(ActionType.LINE_HEIGHT, Double.valueOf(value));
                break;
            case BOLD:
            case ITALIC:
            case UNDERLINE:
            case SUBSCRIPT:
            case SUPERSCRIPT:
            case STRIKETHROUGH:
            case JUSTIFY_LEFT:
            case JUSTIFY_CENTER:
            case JUSTIFY_RIGHT:
            case JUSTIFY_FULL:
            case NORMAL:
            case H1:
            case H2:
            case H3:
            case H4:
            case H5:
            case H6:
            case ORDERED:
            case UNORDERED:
                updateActionStates(type, Boolean.valueOf(value));
                break;
            default:
                break;
        }
    }

    private void updateFontFamilyStates(final String font) {
        bind.getRoot().post(new Runnable() {
            @Override
            public void run() {
                bind.tvFontName.setText(font);
            }
        });
    }

    private void updateFontStates(final ActionType type, final double value) {
        bind.getRoot().post(new Runnable() {
            @Override
            public void run() {
                switch (type) {
                    case SIZE:
                        bind.tvFontSize.setText(String.valueOf((int) value));
                        break;
                    case LINE_HEIGHT:
                        bind.tvFontSpacing.setText(String.valueOf(value));
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private void updateFontColorStates(final ActionType type, final String color) {
        bind.getRoot().post(new Runnable() {
            @Override
            public void run() {
                String selectedColor = rgbToHex(color);
                if (selectedColor != null) {
                    if (type == ActionType.FORE_COLOR) {
                        bind.cpvFontTextColor.setSelectedColor(selectedColor);
                    } else if (type == ActionType.BACK_COLOR) {
                        bind.cpvHighlightColor.setSelectedColor(selectedColor);
                    }
                }
            }
        });
    }

    public static String rgbToHex(String rgb) {
        Pattern c = Pattern.compile("rgb *\\( *([0-9]+), *([0-9]+), *([0-9]+) *\\)");
        Matcher m = c.matcher(rgb);
        if (m.matches()) {
            return String.format("#%02x%02x%02x", Integer.valueOf(m.group(1)),
                    Integer.valueOf(m.group(2)), Integer.valueOf(m.group(3)));
        }
        return null;
    }


}
