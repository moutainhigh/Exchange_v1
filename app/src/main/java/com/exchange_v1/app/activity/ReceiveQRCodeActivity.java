package com.exchange_v1.app.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.exchange_v1.app.R;
import com.exchange_v1.app.base.BaseActivity;
import com.exchange_v1.app.bean.QrInfoBean;
import com.exchange_v1.app.bean.ResponseBean;
import com.exchange_v1.app.bean.UpLoadBean;
import com.exchange_v1.app.biz.FileBiz;
import com.exchange_v1.app.biz.QrCodeBiz;
import com.exchange_v1.app.network.RequestHandle;
import com.exchange_v1.app.utils.FieldConfig;
import com.exchange_v1.app.utils.IntentUtil;
import com.exchange_v1.app.utils.Logger;
import com.exchange_v1.app.utils.StringUtil;
import com.exchange_v1.app.utils.ToastUtil;
import com.wildma.pictureselector.PictureSelector;

//通用收款码界面
public class ReceiveQRCodeActivity extends BaseActivity implements View.OnClickListener {

    private RelativeLayout rlQrPng;
    private EditText etPeopleName;
    private EditText etIdCard;
    private EditText etUID;

    private TextView tvLeftTopName;
    private TextView tvPeopleNameHead;
    private TextView tvIdCardHead;
    private TextView tvInputText;
    private LinearLayout llUID;
    private LinearLayout llName;
    private LinearLayout llCard;

    private TextView btSubmit;
//    private ImageView ivPic;
    private TextView tvCenterBtn;

    private String picturePath;
    private UpLoadBean upLoadBean;
    //支付宝
    public static final  String ALIBY = "ALI_BY";
    //微信
    public static final  String WXBY = "WX_BY";
    //qq
    public static final  String JHBY = "JH_BY";
    //上传二维码的类型
    private String codeType;

    private QrInfoBean bean;

    public static void openActivity(Context context, String type) {
        Bundle b = new Bundle();
        b.putString(FieldConfig.intent_str, type);
        IntentUtil.gotoActivity((Activity) context, ReceiveQRCodeActivity.class, b);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_receive_qrcode;
    }

    @Override
    protected void findViews() {
        rlQrPng = (RelativeLayout) findViewById(R.id.rl_qr_png);
        etPeopleName = (EditText) findViewById(R.id.et_people_name);
        etIdCard = (EditText) findViewById(R.id.et_id_card);
        etUID = (EditText) findViewById(R.id.et_UID);
        btSubmit = (TextView) findViewById(R.id.bt_submit);
        tvCenterBtn = (TextView) findViewById(R.id.tv_center_btn);
//        ivPic = findViewById(R.id.iv_pic);

        tvLeftTopName = (TextView) findViewById(R.id.tv_left_top_name);
        tvPeopleNameHead = (TextView) findViewById(R.id.tv_people_name_head);
        tvIdCardHead = (TextView) findViewById(R.id.tv_id_card_head);
        tvInputText = (TextView) findViewById(R.id.tv_input_text);
        llUID = (LinearLayout) findViewById(R.id.ll_UID);
        llName = (LinearLayout) findViewById(R.id.ll_name);
        llCard = (LinearLayout) findViewById(R.id.ll_card);
    }

    @Override
    protected void initGetData() {
        titleView.setBackBtn();
        titleView.setTitle("通用收款码");

        Bundle bundle = getBundle();
        codeType = bundle.getString(FieldConfig.intent_str);

        if (ALIBY.equals(codeType)){//支付宝
            titleView.setTitle("支付宝收款码");
            tvLeftTopName.setText("支付宝");
            tvPeopleNameHead.setText("支付宝昵称 ");
            tvIdCardHead.setText("支付宝账号 ");
//            llUID.setVisibility(View.VISIBLE);
        }else if (WXBY.equals(codeType)){//微信
            titleView.setTitle("微信收款码");
            tvLeftTopName.setText("微信");
            tvPeopleNameHead.setText("微信昵称 ");
            tvIdCardHead.setText("微信账号 ");
//            llUID.setVisibility(View.GONE);
        }else if (JHBY.equals(codeType)){//QQ
            titleView.setTitle("QQ收款码");
            tvLeftTopName.setText("聚合码");
            llName.setVisibility(View.GONE);
            llCard.setVisibility(View.GONE);
            tvInputText.setVisibility(View.GONE);
//            tvPeopleNameHead.setText("微信昵称 ");
//            tvIdCardHead.setText("微信账号 ");
        }

    }

    @Override
    protected void widgetListener() {
        btSubmit.setOnClickListener(this);
        rlQrPng.setOnClickListener(this);
    }

    @Override
    protected void init() {

        QrCodeBiz.getQrCodeInfo(context, codeType, new RequestHandle() {
            @Override
            public void onSuccess(ResponseBean result) {
                bean = (QrInfoBean) result.getObject();
                if (bean != null) {
//                    ivPic.setVisibility(View.VISIBLE);
//                    rlQrPng.setVisibility(View.GONE);
                    tvCenterBtn.setVisibility(View.GONE);

                    String path = bean.getPath();
                    path = path.replace("\"", "");
                    Logger.i(path);

                    Glide.with(thisA)
                            .load(path)
                            .asBitmap()
                            .into(new SimpleTarget<Bitmap>() { // 括号里可以指定图片宽高
                                @Override
                                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                    Drawable drawable = new BitmapDrawable(resource);
                                    rlQrPng.setBackgroundDrawable(drawable);
                                }
                            });

                    etPeopleName.setText(bean.getName());
                    etIdCard.setText(bean.getAccount());

                }
            }

            @Override
            public void onFail(ResponseBean result) {
//                ToastUtil.showToast(context,result.getInfo());
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_submit:
                submitInfo();

                break;
            case R.id.rl_qr_png://上传二维码按钮
                selectPng();
                break;
            default:
                break;
        }
    }

    /**
     * 绑定二维码
     */
    private void submitInfo() {
        String name = etPeopleName.getText().toString().trim();
        String card = etIdCard.getText().toString().trim();
        if (!StringUtil.isEmpty(name)&&!StringUtil.isEmpty(card)){
            if (upLoadBean!=null){
                QrCodeBiz.bindQrCode(context, upLoadBean.getId(), codeType, card, name, new RequestHandle() {
                    @Override
                    public void onSuccess(ResponseBean result) {
                        ToastUtil.showToast(context,"绑定二维码成功");
                        finish();
                    }

                    @Override
                    public void onFail(ResponseBean result) {
                        ToastUtil.showToast(context,result.getInfo());
                    }
                });
            }else {
                ToastUtil.showToast(context,"二维码不存在，请重新选择二维码上传");
            }
        }else {
            ToastUtil.showToast(context,"姓名和账号不能为空");
        }

    }

    private void selectPng() {
        PictureSelector
                .create(thisA, PictureSelector.SELECT_REQUEST_CODE)
                .selectPicture(true, 200, 200, 1, 1);

//        IntentUtil.gotoActivity(context,SettingUserAvatarActivity.class);
    }

    //上传文件接口
    private void upLoadQrCode(String path) {
        FileBiz.uploadFile(context, path, new RequestHandle() {
            @Override
            public void onSuccess(ResponseBean result) {
                Logger.i("上传图片成功");
                ToastUtil.showToast(context,"收款码已上传");
                upLoadBean = (UpLoadBean) result.getObject();
            }

            @Override
            public void onFail(ResponseBean result) {
                ToastUtil.showToast(context,result.getInfo());
                //如果上传失败了，就重新选择图片上传
//                ivPic.setVisibility(View.GONE);
//                rlQrPng.setVisibility(View.VISIBLE);
                tvCenterBtn.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /*结果回调*/
        if (requestCode == PictureSelector.SELECT_REQUEST_CODE) {
            if (data != null) {
                picturePath = data.getStringExtra(PictureSelector.PICTURE_PATH);
//                ivPic.setVisibility(View.VISIBLE);
//                ivPic.setImageBitmap(BitmapFactory.decodeFile(picturePath));

                rlQrPng.setBackgroundDrawable(new BitmapDrawable(BitmapFactory.decodeFile(picturePath)));
                tvCenterBtn.setVisibility(View.GONE);

                if (!StringUtil.isEmpty(picturePath)){
                    upLoadQrCode(picturePath);
                }

                /*使用 Glide 加载图片，由于裁剪后的图片地址是相同的，所以不能从缓存中加载*/
                /*RequestOptions requestOptions = RequestOptions
                        .circleCropTransform()
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true);
                Glide.with(this).load(picturePath).apply(requestOptions).into(mIvImage);*/
            }
        }
    }

}
