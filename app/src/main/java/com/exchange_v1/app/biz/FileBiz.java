package com.exchange_v1.app.biz;

import android.content.Context;

import com.exchange_v1.app.bean.ResponseBean;
import com.exchange_v1.app.config.ServerConfig;
import com.exchange_v1.app.network.MyRequestHandle;
import com.exchange_v1.app.network.NewsBaseBiz;
import com.exchange_v1.app.network.RequestHandle;

public class FileBiz extends BaseBiz{


    public static void uploadFile(Context context, String filePath, final MyRequestHandle mhandle) {

//        HashMap<String, String> params = getPostHeadMap();

        NewsBaseBiz.postFile(context, "正在上传中...", true, ServerConfig.UPLOAD_API,
                filePath, new RequestHandle() {
                    @Override
                    public void onSuccess(ResponseBean responseBean) {
                        mhandle.onSuccess(responseBean);
                    }

                    @Override
                    public void onFail(ResponseBean responseBean) {
                        mhandle.onFail(responseBean);
                    }

                });
    }

}
