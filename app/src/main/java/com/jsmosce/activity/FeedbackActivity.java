package com.jsmosce.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jsmosce.R;
import com.jsmosce.Tools.Tools;
import com.jsmosce.view.BottomMenu;
import com.jsmosce.view.TitleView;

import java.util.LinkedList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.finalteam.galleryfinal.CoreConfig;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.ThemeConfig;
import cn.finalteam.galleryfinal.model.PhotoInfo;

//意见反馈
public class FeedbackActivity extends AppCompatActivity implements TitleView.RightOnClick, View.OnClickListener, GalleryFinal.OnHanlderResultCallback, TextWatcher {

    @Bind(R.id.feedback_title)
    TitleView feedbackTitle;
    @Bind(R.id.feedback_number)
    TextView feedbackNumber;
    @Bind(R.id.feedback_img_number)
    TextView feedbackImgNumber;
    @Bind(R.id.img_gridview)
    RecyclerView imgGridview;
    GridAdapter gridAdapter;
    public LinkedList<PhotoInfo> bitmaps = new LinkedList<PhotoInfo>();
    @Bind(R.id.feedback_et)
    EditText feedbackEt;
    @Bind(R.id.feedback_sub)
    Button feedbackSub;
    private BottomMenu bottomMenu;
    private final int REQUEST_CODE_CAMERA = 1000;
    private final int REQUEST_CODE_GALLERY = 1001;
    //配置
    private FunctionConfig functionConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        ButterKnife.bind(this);
        initFinal();
        initView();

    }

    public void initView() {
        gridAdapter = new GridAdapter(getApplicationContext());
        bottomMenu = new BottomMenu(getApplicationContext(), this);
        feedbackSub.setOnClickListener(this);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 4);
        //设置布局管理器
        imgGridview.setLayoutManager(layoutManager);
        imgGridview.setHasFixedSize(true);
        //设置为垂直布局，这也是默认的
        layoutManager.setOrientation(OrientationHelper.VERTICAL);

        imgGridview.setAdapter(gridAdapter);
        feedbackEt.addTextChangedListener(this);

    }

    @Override
    public void onClick(View view) {
        //提交
        switch (view.getId()) {

            case R.id.bottom_menu_photo:
                GalleryFinal.openCamera(REQUEST_CODE_CAMERA, functionConfig, this);
                bottomMenu.dismiss();
                break;
            case R.id.bottom_menu_PhotoGallery:
                GalleryFinal.openGalleryMuti(REQUEST_CODE_GALLERY, functionConfig, this);
                bottomMenu.dismiss();
                break;
            //提交
            case R.id.feedback_sub:
//                GalleryFinal.openGalleryMuti(REQUEST_CODE_GALLERY, functionConfig, this);
//                bottomMenu.dismiss();
                Tools.myMakeText(getApplicationContext(), "感谢您的反馈");
                this.finish();
                break;

        }
    }

    //回调
    @Override
    public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
        if (resultList != null) {
            bitmaps.addAll(resultList);
            feedbackImgNumber.setText(bitmaps.size() + "/4");

            gridAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onHanlderFailure(int requestCode, String errorMsg) {
        Tools.myMakeText(getApplicationContext(), errorMsg);
    }

    //监听文本框字数
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

        feedbackNumber.setText(s.length() + "/240");
        if (s.length() > 240) {
            Toast.makeText(getApplicationContext(), "你输入的字数已经超过了限制！", Toast.LENGTH_LONG).show();
        }
    }

    class GridAdapter extends RecyclerView.Adapter<GridAdapter.MyViewHolder> implements View.OnClickListener {
        //店铺数据列表

        public GridAdapter(Context context) {
            PhotoInfo photoInfo = new PhotoInfo();
            photoInfo.setPhotoId(R.drawable.icon_addpic_unfocused);
            bitmaps.add(photoInfo);
        }

        @Override
        public int getItemCount() {
            if (bitmaps.size() > 4) {
                return 4;
            }
            return bitmaps.size();
        }

        //重写onCreateViewHolder方法，返回一个自定义的ViewHolder
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_editreply_grida, parent, false);
            MyViewHolder holder = new MyViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            if (position == bitmaps.size() - 1) {
                holder.image.setImageResource(R.drawable.icon_addpic_unfocused);
            } else {
                // holder.image.setImageBitmap(BitmapFactory.decodeFile(bitmaps.get(position).getPhotoPath()));

                Glide.with(FeedbackActivity.this.getApplicationContext())
                        .load(bitmaps.get(position).getPhotoPath())
                        .into(holder.image);
            }

            holder.parent.setTag(position);
            holder.bt.setTag(position);

            if (position == bitmaps.size() - 1) {
                holder.bt.setVisibility(View.GONE);
                Log.i("convertViewv", "隐藏" + position);
            } else {
                holder.bt.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onClick(View v) {
            int postion = (int) v.getTag();
            switch (v.getId()) {
                case R.id.item_grida_bt:
                    bitmaps.remove(postion);
                    gridAdapter.notifyDataSetChanged();
                    break;
                case R.id.edit_reply__list_parent:
                    if (postion == bitmaps.size() - 1) {
                        String sdcardState = Environment.getExternalStorageState();
                        if (Environment.MEDIA_MOUNTED.equals(sdcardState)) {
                            bottomMenu.showPopupWindow(v, 0, 0);
                        } else {
                            Toast.makeText(getApplicationContext(), "sdcard已拔出，不能选择照片",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
            }
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            View parent;
            ImageView image;
            View bt;

            public MyViewHolder(View view) {
                super(view);
                // 获取控件对象
                parent = view.findViewById(R.id.edit_reply__list_parent);
                image = (ImageView) view
                        .findViewById(R.id.item_grida_image);
                bt = view
                        .findViewById(R.id.item_grida_bt);
                bt.setOnClickListener(GridAdapter.this);

                parent.setOnClickListener(GridAdapter.this);
            }
        }
    }

    //初始化选择器
    public void initFinal() {
        //设置主题
//ThemeConfig.CYAN
        ThemeConfig theme = new ThemeConfig.Builder()
                .build();
//配置功能
        functionConfig = new FunctionConfig.Builder()
                .setEnableCamera(true)
                .setEnableEdit(true)
                .setEnableCrop(true)
                .setEnableRotate(true)
                .setCropSquare(true)
                .setEnablePreview(true)
                .setMutiSelectMaxSize(4)
                .build();


//配置imageloader
        GlideImageLoader glideImageLoader = new GlideImageLoader();
        CoreConfig coreConfig = new CoreConfig.Builder(getApplicationContext(), glideImageLoader, theme)
                .setFunctionConfig(functionConfig)
                .build();
        GalleryFinal.init(coreConfig);
    }

}
