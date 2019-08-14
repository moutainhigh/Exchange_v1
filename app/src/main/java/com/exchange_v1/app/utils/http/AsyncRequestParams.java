package com.exchange_v1.app.utils.http;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by zhuwd on 2017/3/22.
 * Async 请求参数
 */

public class AsyncRequestParams implements Serializable {

    protected final ConcurrentHashMap<String, String> urlParams;
    protected boolean forceMultipartEntity;
    protected String elapsedFieldInJsonStreamer;
    protected String contentEncoding;

    protected final ConcurrentHashMap<String, FileWrapper> fileParams;

    public AsyncRequestParams() {
        this((Map)null);
    }

    public AsyncRequestParams(Map<String, String> source) {
        this.urlParams = new ConcurrentHashMap();
        this.fileParams = new ConcurrentHashMap();

        this.forceMultipartEntity = false;
        this.elapsedFieldInJsonStreamer = "_elapsed";
        this.contentEncoding = "UTF-8";
        if(source != null) {
            Iterator var2 = source.entrySet().iterator();

            while(var2.hasNext()) {
                Entry entry = (Entry)var2.next();
                this.put((String)entry.getKey(), (String)entry.getValue());
            }
        }

    }

    public ConcurrentHashMap<String, String> getUrlParams(){
        return urlParams;
    }

    public void put(String key, String value) {
        if(key != null && value != null) {
            this.urlParams.put(key, value);
        }
    }

    public void put(String key, File file) throws FileNotFoundException {
        this.put(key, (File)file, (String)null, (String)null);
    }


    public void put(String key, File file, String contentType) throws FileNotFoundException {
        this.put(key, (File)file, contentType, (String)null);
    }

    public void putAll(Map<String,String> params) {
        if (params == null) {
            return;
        }
        convertMapAllToOtherMap(params,urlParams);

    }

    /**
     * 预防为空
     * @param params
     * @param urlParams
     */
    public static void convertMapAllToOtherMap(Map<String, String> params, ConcurrentHashMap<String, String> urlParams) {
        for (Entry<String, String> param:params.entrySet()) {

            urlParams.put(
                    StringUtil.isEmpty(param.getKey())?"":param.getKey(),
                    StringUtil.isEmpty(param.getValue())?"":param.getValue()
            );
        }
    }

    public void put(String key, File file, String contentType, String customFileName) throws FileNotFoundException {
        if(file != null && file.exists()) {
            if(key != null) {
                this.fileParams.put(key, new AsyncRequestParams.FileWrapper(file, contentType, customFileName));

            }

        } else {
            throw new FileNotFoundException();
        }
    }
    public void getMapData(){

    }



    public static class FileWrapper implements Serializable {
        public final File file;
        public final String contentType;
        public final String customFileName;

        public FileWrapper(File file, String contentType, String customFileName) {
            this.file = file;
            this.contentType = contentType;
            this.customFileName = customFileName;
        }
    }

}
