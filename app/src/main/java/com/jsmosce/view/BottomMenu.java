package com.jsmosce.view;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.jsmosce.R;


/**
 * 底部弹出菜单
 */
public class BottomMenu extends PopupWindow implements View.OnClickListener {
    private View contentView;

    private Context mContext;
    //点击事件
    private View.OnClickListener onClickListener;

    //选择的item
    public BottomMenu(final Context context, View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
        this.mContext = context;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        contentView = inflater.inflate(R.layout.bottom_menu, null);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow的View
        this.setFocusable(true);
        this.setTouchable(true);
        this.setBackgroundDrawable(new BitmapDrawable());
        this.setOutsideTouchable(true);
        setContentView(contentView);

        initView();
    }

    public void initView() {
        contentView.findViewById(R.id.bottom_menu_cancel).setOnClickListener(this);
        contentView.findViewById(R.id.bottom_menu_LinearLayout).setOnClickListener(this);
        contentView.findViewById(R.id.bottom_menu_photo).setOnClickListener(onClickListener);
        contentView.findViewById(R.id.bottom_menu_PhotoGallery).setOnClickListener(onClickListener);
    }

    //显示窗体
    public void showPopupWindow(View parent, int x, int y) {
        if (!this.isShowing()) {
            this.showAtLocation(parent, Gravity.CENTER, x, y);
        } else {
            this.dismiss();
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bottom_menu_cancel:
                this.dismiss();
                break;
            case R.id.bottom_menu_LinearLayout:
                this.dismiss();
                break;
        }
    }
}
