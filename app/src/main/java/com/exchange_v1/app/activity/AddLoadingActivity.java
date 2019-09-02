package com.exchange_v1.app.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.exchange_v1.app.R;
import com.exchange_v1.app.base.BaseActivity;
import com.exchange_v1.app.base.MainActivity;
import com.exchange_v1.app.base.TApplication;
import com.exchange_v1.app.bean.AddLoadBean;
import com.exchange_v1.app.utils.ISDoubleClickUtils;
import com.exchange_v1.app.utils.IntentUtil;
import com.exchange_v1.app.utils.imageloader.ImageLoaderUtil;
import com.exchange_v1.app.utils.imageloader.ImageOptions;

/**
 * 新增启动页面
 * AddLoadingActivity
 */
public class AddLoadingActivity extends BaseActivity {
    private TextView btn_next;
    private ImageView img_content;
    private String time_lauch;
    private String img_url;
    private int count;

    private AddLoadBean addLoadBean;
    private boolean isClickLoad = false; //是否点击加载页,当前页面有效  与 TApplication.isClickLoading 区分，因为它在跳转到指定页面才修改成false

    @Override
    protected int getContentViewId() {
        return R.layout.a_loading_add;
    }

    @Override
    protected void findViews() {
        btn_next = (TextView) findViewById(R.id.btn_next);
        img_content = (ImageView) findViewById(R.id.img_content);


    }

    @Override
    protected void initGetData() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            addLoadBean = (AddLoadBean) bundle.getSerializable("AddLoadBean");
            if(addLoadBean != null){
                time_lauch = addLoadBean.getLaunchTime();
                img_url = addLoadBean.getLaunchImage();
            }else{
                time_lauch = "0";
                img_url = "";
            }

            //		TApplication.finalbitmap.display(img_content, img_url, TApplication.bitmapDisplayConfig,null);
            ImageOptions options = new ImageOptions.Builder()
                    .showImageForEmptyUri(R.mipmap.loading_bg1)
                    .showImageOnFail(R.mipmap.loading_bg1)
                    .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
                    .cacheOnDisk(true) // 设置下载的图片是否缓存在SD卡中
                    .build();

            ImageLoaderUtil.showImage(img_url, img_content, options);
        }
        count = Integer.valueOf(time_lauch);
        btn_next.setText(count + " 跳过");
        mHandler.postDelayed(toMainRunable, 1000);
    }

    Runnable toMainRunable = new Runnable() {
        @Override
        public void run() {
            count--;
            if (count <= 0 && !isClickLoad) {
                IntentUtil.gotoActivityAndFinishForNo(AddLoadingActivity.this,
                        MainActivity.class);
                return;
            }
            btn_next.setText(count + " 跳过");
            mHandler.postDelayed(toMainRunable, 1000);
        }
    };

    @Override
    protected void widgetListener() {
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //防止快速点击跳过手势密码
                if (!ISDoubleClickUtils.isTimeDoubleClick(1000)) {
                    IntentUtil.gotoActivityAndFinishForNo(context,
                            MainActivity.class);
                    //点击跳过后结束计时，否则会出现跳两次MainActivity，倒计时为1时，手势密码页不出来的bug
                    mHandler.removeCallbacks(toMainRunable);
                }
            }
        });

        img_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(addLoadBean != null){
                    if("link".equals(addLoadBean.getLinkType()) || "product".equals(addLoadBean.getLinkType()) || "oillist".equals(addLoadBean.getLinkType())){
                        //防止快速点击跳过手势密码
                        if (!ISDoubleClickUtils.isTimeDoubleClick(1000) && !isClickLoad) {
                            TApplication.isClickLoading = true;
                            isClickLoad = true;
                            IntentUtil.gotoActivityAndFinishForNo(context,
                                    MainActivity.class, getIntent().getExtras());
                            //点击跳过后结束计时，否则会出现跳两次MainActivity，倒计时为1时，手势密码页不出来的bug
                            mHandler.removeCallbacks(toMainRunable);
                        }
                    }
                }
            }
        });
    }

    @Override
    protected void init() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacks(toMainRunable);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.alpha_exit, R.anim.alpha_exit);
    }

}