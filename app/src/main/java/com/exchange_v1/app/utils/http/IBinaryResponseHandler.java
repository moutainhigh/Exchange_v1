package com.exchange_v1.app.utils.http;


import com.exchange_v1.app.bean.ResponseBean;

/**
 * Created by huanghh on 2017/3/24.
 */

public abstract class  IBinaryResponseHandler implements IResponseHandler {

    public void onProgress(long bytesWritten, long totalSize) {

    }

    public abstract void onSuccess(int statusCode, Header[] headers, byte[] binaryData);

    public abstract void onFail(int statusCode, Header[] headers, byte[] binaryData, Throwable error);

    @Override
    public void onSuccess(ResponseBean result) {

    }

    @Override
    public void onFail(ResponseBean result) {

    }

    @Override
    public void onCancel() {

    }

    @Override
    public void onSaveCache(ResponseBean result) {

    }

    @Override
    public ResponseBean getCache() {
        return null;
    }
}
