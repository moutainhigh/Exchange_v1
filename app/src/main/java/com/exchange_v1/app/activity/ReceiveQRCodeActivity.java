package com.exchange_v1.app.activity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.exchange_v1.app.R;
import com.exchange_v1.app.base.BaseActivity;
import com.exchange_v1.app.bean.ResponseBean;
import com.exchange_v1.app.biz.FileBiz;
import com.exchange_v1.app.network.RequestHandle;
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
    private TextView btSubmit;
    private ImageView ivPic;
    private String picturePath;

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
        ivPic = findViewById(R.id.iv_pic);

    }

    @Override
    protected void initGetData() {
        titleView.setBackBtn();
        titleView.setTitle("通用收款码");
    }

    @Override
    protected void widgetListener() {
        btSubmit.setOnClickListener(this);
        rlQrPng.setOnClickListener(this);
    }

    @Override
    protected void init() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_submit:
//                upLoadQrCode();

                break;
            case R.id.rl_qr_png://上传二维码按钮
                selectPng();
                break;
            default:
                break;
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
            }

            @Override
            public void onFail(ResponseBean result) {
                ToastUtil.showToast(context,result.getInfo());
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
                ivPic.setVisibility(View.VISIBLE);
                ivPic.setImageBitmap(BitmapFactory.decodeFile(picturePath));

                rlQrPng.setVisibility(View.GONE);

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
