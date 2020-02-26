package com.example.myapplication.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.myapplication.R;


/**
 * @author HuangFusheng
 * 自定义Toolbar
 */
public class EditTextWithTitle extends FrameLayout {

    private Context mContext;

    private int type;

    public static final int TEXT_VIEW = 0;
    public static final int EDIT_TEXT = 1;

    private View mEditTextLayout;
    private View mTextViewLayout;

    /**
     * EditText标题
     */
    private TextView mEtTitle;
    /**
     * EditText
     */
    private EditText etContent;

    /**
     * TextView标题
     */
    private TextView mTvTitle;
    /**
     * TextView
     */
    private TextView TvContent;


    private String mTitle;
    private int mTitltColor;
    private String mEtContent;
    private String mEtHint;


    public EditTextWithTitle(Context context) {
        this(context, null);
    }

    public EditTextWithTitle(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EditTextWithTitle(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;

        initAttrs(attrs);
    }

    private void initAttrs(AttributeSet attrs) {

        if (attrs != null) {
            TypedArray a = mContext.obtainStyledAttributes(attrs, R.styleable.EditTextWithTitle, 0, 0);
            mTitle = a.getString(R.styleable.EditTextWithTitle_title_text);
            mTitltColor = a.getResourceId(R.styleable.EditTextWithTitle_title_color, getResources().getColor(R.color.colorPrimary));
            mEtContent = a.getString(R.styleable.EditTextWithTitle_et_content);
            mEtHint = a.getString(R.styleable.EditTextWithTitle_et_hint);
            type = a.getInt(R.styleable.EditTextWithTitle_view_type, EDIT_TEXT);
        }

        init(type);
    }


    private void init(int type) {

        LayoutInflater inflater = LayoutInflater.from(mContext);

        mTextViewLayout = inflater.inflate(R.layout.text_view_with_title, null);
        mEditTextLayout = inflater.inflate(R.layout.edit_text_with_title, null);

        mTvTitle = mTextViewLayout.findViewById(R.id.tv_tv_title);
        TvContent = mTextViewLayout.findViewById(R.id.tv_content);
        mEtTitle = mEditTextLayout.findViewById(R.id.tv_et_title);
        etContent = mEditTextLayout.findViewById(R.id.et_content);


        if (TEXT_VIEW == type) {

            if (mTvTitle != null) {
                mTvTitle.setText(mTitle);
            }

            if (TvContent != null) {
                TvContent.setText(mEtContent);
            }
            this.addView(mTextViewLayout);

        } else {

            if (mTitle != null) {
                mEtTitle.setText(mTitle);
            }

            if (mEtContent != null) {
                etContent.setText(mEtContent);
            } else if (mEtHint != null) {
                etContent.setHint(mEtHint);
            }

            addView(mEditTextLayout);
        }
    }

    /**
     * 设置title的内容
     *
     * @param text
     */
    public void setMainTitle(String text) {

        if (TEXT_VIEW == type) {
            mTvTitle.setText(text);
        } else {
            mEtTitle.setText(text);
        }
    }

    /**
     * 设置title的文字的颜色
     *
     * @param color
     */
    public void setTitleColor(int color) {
        if (TEXT_VIEW == type) {
            mTvTitle.setTextColor(color);
        } else {
            mEtTitle.setTextColor(color);
        }
    }


    /**
     * 设置具体内容
     *
     * @param text
     */
    public void setContentText(String text) {
        if (TEXT_VIEW == type) {
            TvContent.setText(text);
        } else {
            etContent.setText(text);
        }
    }


    /**
     * 设置title的内容
     *
     * @param text
     */
    public void setEtContentHint(String text) {
        etContent.setHint(text);
    }

    /**
     * 获取EditText的文字内容
     *
     * @return
     */
    public String getText() {
        if (TEXT_VIEW == type) {
            if (TvContent == null) {
                return null;
            }
            return TvContent.getText().toString().trim();

        } else {
            if (etContent == null) {
                return null;
            }
            return etContent.getText().toString().trim();
        }

    }
}
