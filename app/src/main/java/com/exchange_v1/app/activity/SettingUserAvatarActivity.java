package com.exchange_v1.app.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.exchange_v1.R;
import com.exchange_v1.app.base.BaseActivity;
import com.exchange_v1.app.bean.ResponseBean;
import com.exchange_v1.app.bean.UpLoadBean;
import com.exchange_v1.app.biz.FileBiz;
import com.exchange_v1.app.network.RequestHandle;
import com.exchange_v1.app.utils.ToastUtil;

import java.io.File;
import java.util.UUID;


/**
 * Created by yanchuang on 2017/7/29.
 */

public class SettingUserAvatarActivity extends BaseActivity implements View.OnClickListener {
    private static final int CODE_PHOTO_REQUEST = 1;
    private static final int CODE_CAMERA_REQUEST = 2;
    private static final int CODE_PHOTO_CLIP = 3;
    private static final int REQUEST_PICK_IMAGE = 1; //相册选取
    private static final File USER_ICON = new File("/sdcard/c2b_phone/Camera/", "user_icon.jpg");
    private static final int RESULT_DATA = 7;
    private TextView mCapturingPhotos;
    private TextView mCancle;
    private TextView mChoicePhone;
    private static final String SD_PATH = "/sdcard/c2b_phone/Camera/";
    private static final String IN_PATH = "/c2b_phone/Camera/";
    private String savePath;
    private String mUrl;
    private String i;
    private String mPath;
    private Uri uritempFile; //保存图片的URI
    private static final int CARMERA_REQUEST_CODE = 1;
    private UpLoadBean bean;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_avatar_user_setting;
    }

    @Override
    protected void findViews() {
        mCapturingPhotos = (TextView) findViewById(R.id.user_setting_capturing_photos);
        mCancle = (TextView) findViewById(R.id.user_setting_cancle);
        mChoicePhone = (TextView) findViewById(R.id.user_setting_choice_phone);
    }

    @Override
    protected void initGetData() {
    }

    @Override
    protected void widgetListener() {
        mCapturingPhotos.setOnClickListener(this);
        mCancle.setOnClickListener(this);
        mChoicePhone.setOnClickListener(this);
    }

    @Override
    protected void init() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_setting_capturing_photos:
                //调用相机
                i = "0";
                if (ContextCompat.checkSelfPermission(thisA, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(thisA, new String[]{Manifest.permission.CAMERA}, CARMERA_REQUEST_CODE);
                } else {
                    getPicFromCamera();
                }
                break;
            case R.id.user_setting_choice_phone:
                i = "0";
                if (ContextCompat.checkSelfPermission(thisA, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(thisA, new String[]{Manifest.permission.CAMERA}, CARMERA_REQUEST_CODE);
                } else {
                    getPicFromLocal();
                }
                break;
            case R.id.user_setting_cancle:
                //取消选择
                finish();
                break;
        }
    }

    //调用相机
    public void getPicFromCamera() {
        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        // 下面这句指定调用相机拍照后的照片存储的路径
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(USER_ICON));
        startActivityForResult(intent, CODE_CAMERA_REQUEST);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // NOTE: delegate the permission handling to generated method
        if (requestCode == CARMERA_REQUEST_CODE && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            } else {
                Toast.makeText(thisA, "需要开启权限才能使用照相机功能", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 用户没有进行有效的设置操作，返回
        if (resultCode == RESULT_CANCELED) {
            Toast.makeText(this, "取消", Toast.LENGTH_LONG).show();
            return;
        }
        switch (requestCode) {
            case CODE_CAMERA_REQUEST:
                if (USER_ICON.exists()) {
                    photoClip(Uri.fromFile(USER_ICON));
                }
                break;
            case CODE_PHOTO_REQUEST:
                if (data != null) {
                    photoClip(data.getData());
                }
                break;
            case CODE_PHOTO_CLIP:
                Intent intent = new Intent();
                intent.putExtra("default", "result");
                setResult(RESULT_OK, intent);
                setImageToHeadView(data);
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    /**
     * 提取保存裁剪之后的图片数据，并设置头像部分的View
     */
    private void setImageToHeadView(Intent intent) {
        //图片已在剪切后保存到本地了，直接拿路径上传
        getBitmapForIntent();
    }

    //绝对路径转 URI
    public static Uri getUri(final String filePath) {
        return Uri.fromFile(new File(filePath));
    }

    /****
     * 调用系统自带切图工具对图片进行裁剪
     * 微信也是
     *
     * @param uri
     */
    private void photoClip(Uri uri) {
        // 调用系统中自带的图片剪裁
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        //        intent.putExtra("return-data", true);

        //剪切后生成图片的保存绝对路径
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            savePath = SD_PATH;
        } else {
            savePath = context.getApplicationContext().getFilesDir().getAbsolutePath() + IN_PATH;
        }
        mPath = savePath + generateFileName() + ".jpg";
        //保存图片的URI
        uritempFile = getUri(mPath);
        //剪切成功 保存图片到本地
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uritempFile);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        startActivityForResult(intent, CODE_PHOTO_CLIP);
    }

    //选择手机相册
    private void getPicFromLocal() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, REQUEST_PICK_IMAGE);
    }

    private static String generateFileName() {
        return UUID.randomUUID().toString();
    }

    public void getBitmapForIntent() {
        //网络请求上传头像
        FileBiz.uploadFile(thisA, mPath, new RequestHandle() {
            @Override
            public void onSuccess(ResponseBean result) {
                if (result != null) {
                    bean = (UpLoadBean)result.getObject();
//                    upLoadIconData();
                    ToastUtil.showToast(thisA, result.getInfo());
                }
            }

            @Override
            public void onFail(ResponseBean result) {
                ToastUtil.showToast(thisA, result.getInfo());
            }

        });
    }

}
