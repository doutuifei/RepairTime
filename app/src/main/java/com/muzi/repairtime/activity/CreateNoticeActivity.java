package com.muzi.repairtime.activity;

import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Base64;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;
import com.muzi.repairtime.R;
import com.muzi.repairtime.activity.base.BaseActivity;
import com.muzi.repairtime.activity.base.BaseViewModel;
import com.muzi.repairtime.databinding.ActivityCreateNoticeBinding;
import com.muzi.repairtime.entity.BaseEntity;
import com.muzi.repairtime.event.EventConstan;
import com.muzi.repairtime.event.LiveEventBus;
import com.muzi.repairtime.http.RxHttp;
import com.muzi.repairtime.http.RxUtils;
import com.muzi.repairtime.http.api.AdminApi;
import com.muzi.repairtime.observer.EntityObserver;
import com.muzi.repairtime.richeditor.ActionType;
import com.muzi.repairtime.richeditor.GlideImageLoader;
import com.muzi.repairtime.richeditor.RichEditorAction;
import com.muzi.repairtime.richeditor.RichEditorCallback;
import com.muzi.repairtime.richeditor.fragment.EditHyperlinkFragment;
import com.muzi.repairtime.richeditor.fragment.EditTableFragment;
import com.muzi.repairtime.richeditor.fragment.EditorMenuFragment;
import com.muzi.repairtime.richeditor.interfaces.OnActionPerformListener;
import com.muzi.repairtime.richeditor.keyboard.KeyboardHeightObserver;
import com.muzi.repairtime.richeditor.keyboard.KeyboardHeightProvider;
import com.muzi.repairtime.richeditor.keyboard.KeyboardUtils;
import com.muzi.repairtime.richeditor.ui.ActionImageView;
import com.muzi.repairtime.richeditor.util.FileIOUtil;
import com.muzi.repairtime.utils.StringUtils;
import com.muzi.repairtime.utils.ToastUtils;
import com.muzi.repairtime.utils.ViewUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 新建公告
 */
public class CreateNoticeActivity extends BaseActivity<ActivityCreateNoticeBinding, BaseViewModel> implements KeyboardHeightObserver {

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, CreateNoticeActivity.class);
        Bundle bundle = new Bundle();
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    private KeyboardHeightProvider keyboardHeightProvider;
    private boolean isKeyboardShowing;

    private RichEditorAction mRichEditorAction;
    private RichEditorCallback mRichEditorCallback;

    private EditorMenuFragment mEditorMenuFragment;

    private List<ActionType> mActionTypeList =
            Arrays.asList(ActionType.BOLD, ActionType.ITALIC, ActionType.UNDERLINE,
                    ActionType.STRIKETHROUGH, ActionType.SUBSCRIPT, ActionType.SUPERSCRIPT,
                    ActionType.NORMAL, ActionType.H1, ActionType.H2, ActionType.H3, ActionType.H4,
                    ActionType.H5, ActionType.H6, ActionType.INDENT, ActionType.OUTDENT,
                    ActionType.JUSTIFY_LEFT, ActionType.JUSTIFY_CENTER, ActionType.JUSTIFY_RIGHT,
                    ActionType.JUSTIFY_FULL, ActionType.ORDERED, ActionType.UNORDERED, ActionType.LINE,
                    ActionType.BLOCK_CODE, ActionType.BLOCK_QUOTE, ActionType.CODE_VIEW);

    private List<Integer> mActionTypeIconList =
            Arrays.asList(R.drawable.ic_format_bold, R.drawable.ic_format_italic,
                    R.drawable.ic_format_underlined, R.drawable.ic_format_strikethrough,
                    R.drawable.ic_format_subscript, R.drawable.ic_format_superscript,
                    R.drawable.ic_format_para, R.drawable.ic_format_h1, R.drawable.ic_format_h2,
                    R.drawable.ic_format_h3, R.drawable.ic_format_h4, R.drawable.ic_format_h5,
                    R.drawable.ic_format_h6, R.drawable.ic_format_indent_decrease,
                    R.drawable.ic_format_indent_increase, R.drawable.ic_format_align_left,
                    R.drawable.ic_format_align_center, R.drawable.ic_format_align_right,
                    R.drawable.ic_format_align_justify, R.drawable.ic_format_list_numbered,
                    R.drawable.ic_format_list_bulleted, R.drawable.ic_line, R.drawable.ic_code_block,
                    R.drawable.ic_format_quote, R.drawable.ic_code_review);

    private static final int REQUEST_CODE_CHOOSE = 0;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_create_notice;
    }

    @Override
    public int initVariableId() {
        return 0;
    }

    @Override
    public void initView() {
        super.initView();
        binding.wvContainer.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        binding.wvContainer.setWebChromeClient(new CustomWebChromeClient());
        binding.wvContainer.getSettings().setJavaScriptEnabled(true);
        binding.wvContainer.getSettings().setDomStorageEnabled(true);
        mRichEditorCallback = new MRichEditorCallback();
        binding.wvContainer.addJavascriptInterface(mRichEditorCallback, "MRichEditor");
        binding.wvContainer.loadUrl("file:///android_asset/richEditor.html");
        mRichEditorAction = new RichEditorAction(binding.wvContainer);

        keyboardHeightProvider = new KeyboardHeightProvider(this);

        binding.container.post(new Runnable() {
            @Override
            public void run() {
                keyboardHeightProvider.start();
            }
        });

        initImageLoader();

        int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40,
                getResources().getDisplayMetrics());
        int padding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 9,
                getResources().getDisplayMetrics());
        for (int i = 0, size = mActionTypeList.size(); i < size; i++) {
            final ActionImageView actionImageView = new ActionImageView(this);
            actionImageView.setLayoutParams(new LinearLayout.LayoutParams(width, width));
            actionImageView.setPadding(padding, padding, padding, padding);
            actionImageView.setActionType(mActionTypeList.get(i));
            actionImageView.setTag(mActionTypeList.get(i));
            actionImageView.setActivatedColor(R.color.colorAccent);
            actionImageView.setDeactivatedColor(R.color.tintColor);
            actionImageView.setRichEditorAction(mRichEditorAction);
            actionImageView.setBackgroundResource(R.drawable.btn_colored_material);
            actionImageView.setImageResource(mActionTypeIconList.get(i));
            actionImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    actionImageView.command();
                }
            });
            binding.llActionBarContainer.addView(actionImageView);
        }

        mEditorMenuFragment = new EditorMenuFragment();
        mEditorMenuFragment.setActionClickListener(new MOnActionPerformListener(mRichEditorAction));
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .add(R.id.fl_action, mEditorMenuFragment, EditorMenuFragment.class.getName())
                .commit();

        initListener();
    }

    private void initListener() {
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.flAction.getVisibility() == View.VISIBLE) {
                    binding.flAction.setVisibility(View.GONE);
                } else {
                    finish();
                }
            }
        });

        binding.ivAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.flAction.getVisibility() == View.VISIBLE) {
                    binding.flAction.setVisibility(View.GONE);
                } else {
                    if (isKeyboardShowing) {
                        KeyboardUtils.hideSoftInput(CreateNoticeActivity.this);
                    }
                    binding.flAction.setVisibility(View.VISIBLE);
                }
            }
        });

        binding.ivGetHtml.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRichEditorAction.refreshHtml(mRichEditorCallback, onGetHtmlListener);
            }
        });

        binding.ivActionUndo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRichEditorAction.undo();
            }
        });
        binding.ivActionRedo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRichEditorAction.redo();
            }
        });
        binding.ivActionTxtColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRichEditorAction.foreColor("blue");
            }
        });
        binding.ivActionTxtBgColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRichEditorAction.backColor("red");
            }
        });
        binding.ivActionLineHeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRichEditorAction.lineHeight(20);
            }
        });
        binding.ivActionInsertImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertImage();
            }
        });
        binding.ivActionInsertLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertLink();
            }
        });
        binding.ivActionTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertTable();
            }
        });
    }

    private void insertImage() {
        Intent intent = new Intent(CreateNoticeActivity.this, ImageGridActivity.class);
        startActivityForResult(intent, REQUEST_CODE_CHOOSE);
    }

    private void insertLink() {
        KeyboardUtils.hideSoftInput(CreateNoticeActivity.this);
        EditHyperlinkFragment fragment = new EditHyperlinkFragment();
        fragment.setOnHyperlinkListener(new EditHyperlinkFragment.OnHyperlinkListener() {
            @Override
            public void onHyperlinkOK(String address, String text) {
                mRichEditorAction.createLink(text, address);
            }
        });
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, fragment, EditHyperlinkFragment.class.getName())
                .commit();
    }

    private void insertTable() {
        KeyboardUtils.hideSoftInput(CreateNoticeActivity.this);
        EditTableFragment fragment = new EditTableFragment();
        fragment.setOnTableListener(new EditTableFragment.OnTableListener() {
            @Override
            public void onTableOK(int rows, int cols) {
                mRichEditorAction.insertTable(rows, cols);
            }
        });
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, fragment, EditHyperlinkFragment.class.getName())
                .commit();
    }

    private RichEditorCallback.OnGetHtmlListener onGetHtmlListener =
            new RichEditorCallback.OnGetHtmlListener() {
                @Override
                public void getHtml(String html) {
                    pub(html);
                }
            };

    /**
     * ImageLoader for insert Image
     */
    private void initImageLoader() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());
        imagePicker.setShowCamera(true);
        imagePicker.setCrop(false);
        imagePicker.setMultiMode(false);
        imagePicker.setSaveRectangle(true);
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);
        imagePicker.setFocusWidth(800);
        imagePicker.setFocusHeight(800);
        imagePicker.setOutPutX(256);
        imagePicker.setOutPutY(256);
    }

    private class CustomWebChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            if (newProgress == 100) {
                KeyboardUtils.showSoftInput(CreateNoticeActivity.this);
            }
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS
                && data != null
                && requestCode == REQUEST_CODE_CHOOSE) {
            ArrayList<ImageItem> images =
                    (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
            if (images != null && !images.isEmpty()) {

                //1.Insert the Base64 String (Base64.NO_WRAP)
                ImageItem imageItem = images.get(0);
                mRichEditorAction.insertImageData(imageItem.name,
                        encodeFileToBase64Binary(imageItem.path));

                //2.Insert the ImageUrl
                //mRichEditorAction.insertImageUrl(
                //    "https://avatars0.githubusercontent.com/u/5581118?v=4&u=b7ea903e397678b3675e2a15b0b6d0944f6f129e&s=400");
            }
        }
    }

    private static String encodeFileToBase64Binary(String filePath) {
        byte[] bytes = FileIOUtil.readFile2BytesByStream(filePath);
        byte[] encoded = Base64.encode(bytes, Base64.NO_WRAP);
        return new String(encoded);
    }

    @Override
    public void onResume() {
        super.onResume();
        keyboardHeightProvider.setKeyboardHeightObserver(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        keyboardHeightProvider.setKeyboardHeightObserver(null);
        if (binding.flAction.getVisibility() == View.INVISIBLE) {
            binding.flAction.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        keyboardHeightProvider.close();
    }

    @Override
    public void onKeyboardHeightChanged(int height, int orientation) {
        isKeyboardShowing = height > 0;
        if (height != 0) {
            binding.flAction.setVisibility(View.INVISIBLE);
            ViewGroup.LayoutParams params = binding.flAction.getLayoutParams();
            params.height = height;
            binding.flAction.setLayoutParams(params);
            performInputSpaceAndDel();
        } else if (binding.flAction.getVisibility() != View.VISIBLE) {
            binding.flAction.setVisibility(View.GONE);
        }
    }

    //TODO not a good solution
    private void performInputSpaceAndDel() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(100);
                    Instrumentation instrumentation = new Instrumentation();
                    instrumentation.sendKeyDownUpSync(KeyEvent.KEYCODE_SPACE);
                    instrumentation.sendKeyDownUpSync(KeyEvent.KEYCODE_DEL);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    class MRichEditorCallback extends RichEditorCallback {

        @Override
        public void notifyFontStyleChange(ActionType type, final String value) {
            ActionImageView actionImageView = binding.llActionBarContainer.findViewWithTag(type);
            if (actionImageView != null) {
                actionImageView.notifyFontStyleChange(type, value);
            }

            if (mEditorMenuFragment != null) {
                mEditorMenuFragment.updateActionStates(type, value);
            }
        }
    }

    public class MOnActionPerformListener implements OnActionPerformListener {
        private RichEditorAction mRichEditorAction;

        public MOnActionPerformListener(RichEditorAction mRichEditorAction) {
            this.mRichEditorAction = mRichEditorAction;
        }

        @Override
        public void onActionPerform(ActionType type, Object... values) {
            if (mRichEditorAction == null) {
                return;
            }

            String value = null;
            if (values != null && values.length > 0) {
                value = (String) values[0];
            }

            switch (type) {
                case SIZE:
                    mRichEditorAction.fontSize(Double.valueOf(value));
                    break;
                case LINE_HEIGHT:
                    mRichEditorAction.lineHeight(Double.valueOf(value));
                    break;
                case FORE_COLOR:
                    mRichEditorAction.foreColor(value);
                    break;
                case BACK_COLOR:
                    mRichEditorAction.backColor(value);
                    break;
                case FAMILY:
                    mRichEditorAction.fontName(value);
                    break;
                case IMAGE:
                    insertImage();
                    break;
                case LINK:
                    insertLink();
                    break;
                case TABLE:
                    insertTable();
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
                case CODE_VIEW:
                case ORDERED:
                case UNORDERED:
                case INDENT:
                case OUTDENT:
                case BLOCK_QUOTE:
                case BLOCK_CODE:
                case NORMAL:
                case H1:
                case H2:
                case H3:
                case H4:
                case H5:
                case H6:
                case LINE:
                    ActionImageView actionImageView = binding.llActionBarContainer.findViewWithTag(type);
                    if (actionImageView != null) {
                        actionImageView.performClick();
                    }
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK &&
                binding.flAction.getVisibility() == View.VISIBLE) {
            binding.flAction.setVisibility(View.GONE);
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    /**
     * 发布
     */
    private void pub(String content) {
        String title = ViewUtils.getText(binding.etTitle);
        if (StringUtils.isEmpty(title)) {
            ToastUtils.showToast("请输入标题");
            return;
        }
        if (title.length() < 5) {
            ToastUtils.showToast("最少输入5个字符");
            return;
        }
        if (StringUtils.isEmpty(content) ||
                content.equals("<p><br></p>") ||
                content.equals("&nbsp;")) {
            ToastUtils.showToast("请输入内容");
            return;
        }
        RxHttp.getApi(AdminApi.class)
                .pubNotice(title, content)
                .compose(RxUtils.<BaseEntity>scheduling())
                .compose(RxUtils.<BaseEntity>bindToLifecycle(this))
                .subscribe(new EntityObserver<BaseEntity>(this) {
                    @Override
                    public void onSuccess(BaseEntity entity) {
                        ToastUtils.showToast(entity.getMsg());
                        LiveEventBus.get()
                                .with(EventConstan.REFRESH_NOTICE)
                                .postValue(null);
                    }
                });
    }

}
