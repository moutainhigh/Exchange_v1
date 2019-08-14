package com.exchange_v1.app.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.apache.http.util.ByteArrayBuffer;
import org.apache.http.util.EncodingUtils;

import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.TimeoutException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

/**
 * 网络操作类.
 *
 * @author CodeApe
 * @version 1.0
 * @Description 用于网络的POST 、 GET 、 MutilPart等操作
 * @date 2014年3月30日
 * @Copyright: Copyright (c) 2013 深圳光汇云油电商有限公司
 */
public class NetUtil {

    /**
     * 本地文件上传失败
     */
    public static final String EXCEPTION_UPLOAD_ERROR_STATUS = "805";

    /**
     * 返回结果
     */
    private String result;
    /**
     * 输入流
     */
    private BufferedInputStream bis;
    /**
     * http链接
     */
    private HttpURLConnection mConnection;
    /**
     * 输出流
     */
    private DataOutputStream dos;
    /**
     * 记录上传文件总长度变量
     */
    public static long totalSize = 0;

    /**
     * 获取当前网络链接状态.
     *
     * @param context 上下文
     * @return true网络可用，false网络不可用
     * @version 1.0
     * @createTime 2014年12月31日, 上午3:02:55
     * @updateTime 2014年12月31日, 上午3:02:55
     * @createAuthor CodeApe
     * @updateAuthor CodeApe
     * @updateInfo (此处输入修改内容, 若无修改可不写.)
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE); // 获取网络服务
        if (connectivity == null) {
            // 判断网络服务是否为空
            return false;
        } else {
            // 判断当前是否有任意网络服务开启
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 像指定地址发送post请求提交数据.
     *
     * @param path      数据提交路径.
     * @param timeout   超时时间(毫秒).
     * @param attribute 发送请求参数,key为属性名,value为属性值.
     * @return 服务器的响应信息, 当发生错误时返回响应码.
     * @throws IOException      网络连接错误时抛出IOException.
     * @throws TimeoutException 网络连接超时时抛出TimeoutException.
     * @version 1.0
     * @createTime 2014年12月31日, 上午2:06:53
     * @updateTime 2014年12月31日, 上午2:06:53
     * @createAuthor CodeApe
     * @updateAuthor CodeApe
     * @updateInfo (此处输入修改内容, 若无修改可不写.)
     */
//    public String sendPost(String path, int timeout,
//                           HashMap<String, String> attribute) throws IOException,
//            TimeoutException {
//        try {
//            URL url = new URL(path);
//            // 判断是http请求还是https请求
//            // if (url.getProtocol().toLowerCase().equals("https")) {
//            // trustAllHosts();
//            // mConnection = (HttpsURLConnection) url.openConnection();
//            // ((HttpsURLConnection)
//            // mConnection).setHostnameVerifier(DO_NOT_VERIFY);// 不进行主机名确认
//            //
//            // } else {
//            // mConnection = (HttpURLConnection) url.openConnection();
//            // }
//            mConnection = getUrlConnectionByUrl(url);
//
//            // mConnection = (HttpURLConnection) url.openConnection();
//            if (TApplication.iSOUTNET == 0) {// 添加HTTP 请求头密文
//
//                mConnection.setRequestProperty("Authorization",
//                        "Basic YndvaWw6YndvaWwxMjM=");// 主要是为了添加服务器验证
//            }
//            mConnection.setDoOutput(true); // 设置输出,post请求必须设置.
//            mConnection.setDoInput(true); // 设置输入,post请求必须设置.
//            mConnection.setUseCaches(false); // 设置是否启用缓存,post请求不能使用缓存.
//            // 设置Content-Type属性.
//            // conn.setRequestProperty("Content-Type",
//            // "text/html;charset=utf-8");
//            mConnection.setConnectTimeout(timeout);
//            mConnection.setReadTimeout(timeout);
//            mConnection.setRequestMethod("POST");
//            mConnection.connect(); // 打开网络链接.
//            dos = new DataOutputStream(mConnection.getOutputStream());
//            String encode = DES3.encode(JSON.toJSONString(attribute));// 请求的数据字符串进行MD5加密
//            // LogUtil.i("send:" + path.replace(ServerConfig.SERVER_API_URL, "")
//            // + "\n"
//            // + attribute.toString());
//            HashMap<String, String> hashMap = new HashMap<String, String>();
//            hashMap.put("params", encode);
//
//            dos.writeBytes(getParams(hashMap)); // 将请求参数写入网络链接.
//            dos.flush();
//            return readResponse();
//        } catch (SocketTimeoutException e) {
//            throw new TimeoutException(e.getMessage());
//        } catch (Exception e) {
//            throw new TimeoutException(e.getMessage());
//        }
//    }

    /**
     * 获取Con
     *
     * @param url
     * @return
     * @throws IOException
     */
//    public static HttpURLConnection getUrlConnectionByUrl(URL url)
//            throws IOException, Exception {
//        HttpURLConnection con;
//
//        if (url.getProtocol().toLowerCase().equals("https")) {
//            // trustAllHosts();
//            con = (HttpsURLConnection) url.openConnection();
//            ((HttpsURLConnection) con).setHostnameVerifier(DO_NOT_VERIFY);// 不进行主机名确认
//            SSLContext sslCtx = HttpsUtil.createSslContext(false);
//            ((HttpsURLConnection) con).setSSLSocketFactory(sslCtx
//                    .getSocketFactory());
//
//        } else {
//            con = (HttpURLConnection) url.openConnection();
//        }
//        return con;
//    }

    /**
     * ====================https忽略服务器证书==============================
     */

    static HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier() {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            // TODO Auto-generated method stub
            // System.out.println("Warning: URL Host: " + hostname + " vs. "
            // + session.getPeerHost());
            if (!hostname.contains("brightoilonline")) {
                return false;
            }

            return true;
        }
    };

    /**
     * --------------------上传文件https设置忽然证书-----------------------------------
     */
//    private static HttpClient sslClient(HttpClient client) {
//        try {
//            MyX509TrustManager tm = new MyX509TrustManager();
//            SSLContext ctx = SSLContext.getInstance("TLS");
//            ctx.init(null, new TrustManager[]{tm}, null);
//            SSLSocketFactory ssf = new MySSLSocketFactory(ctx);
//            ssf.setHostnameVerifier(SSLSocketFactory.STRICT_HOSTNAME_VERIFIER);
//            ClientConnectionManager ccm = client.getConnectionManager();
//            SchemeRegistry sr = ccm.getSchemeRegistry();
//            sr.register(new Scheme("https", ssf, 443));
//            return new DefaultHttpClient(ccm, client.getParams());
//        } catch (Exception ex) {
//            return null;
//        }
//    }

//    public static class MySSLSocketFactory extends SSLSocketFactory {
//        SSLContext sslContext = SSLContext.getInstance("TLS");
//
//        public MySSLSocketFactory(KeyStore truststore)
//                throws NoSuchAlgorithmException, KeyManagementException,
//                KeyStoreException, UnrecoverableKeyException {
//            super(truststore);
//            try {
//                MyX509TrustManager tm = new MyX509TrustManager();
//                sslContext.init(null, new TrustManager[]{tm}, null);
//            } catch (Exception e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//        }
//
//        public MySSLSocketFactory(SSLContext context)
//                throws KeyManagementException, NoSuchAlgorithmException,
//                KeyStoreException, UnrecoverableKeyException {
//            super(null);
//            sslContext = context;
//        }
//
//        @Override
//        public Socket createSocket(Socket socket, String host, int port,
//                                   boolean autoClose) throws IOException, UnknownHostException {
//            return sslContext.getSocketFactory().createSocket(socket, host,
//                    port, autoClose);
//        }
//
//        @Override
//        public Socket createSocket() throws IOException {
//            return sslContext.getSocketFactory().createSocket();
//        }
//    }

    /** --------------------上传文件https设置忽然证书----------------------------------- */

    /**
     * 像指定地址发送post请求提交数据. 不加密
     *
     * @param path      数据提交路径.
     * @param timeout   超时时间(毫秒).
     * @param attribute 发送请求参数,key为属性名,value为属性值.
     * @return 服务器的响应信息, 当发生错误时返回响应码.
     * @throws IOException      网络连接错误时抛出IOException.
     * @throws TimeoutException 网络连接超时时抛出TimeoutException.
     * @version 1.0
     * @createTime 2014年12月31日, 上午2:06:53
     * @updateTime 2014年12月31日, 上午2:06:53
     * @createAuthor CodeApe
     * @updateAuthor CodeApe
     * @updateInfo (此处输入修改内容, 若无修改可不写.)
     */
//    public String sendPost1(String path, int timeout,
//                            HashMap<String, String> attribute) throws IOException,
//            TimeoutException {
//        try {
//            URL url = new URL(path);
//            mConnection = getUrlConnectionByUrl(url);
//            if (TApplication.iSOUTNET == 0) {
//
//                //				mConnection.setRequestProperty("Authorization",
//                //						"Basic YndvaWw6YndvaWwxMjM=");// 主要是为了添加服务器验证
//            }
//            mConnection.setDoOutput(true); // 设置输出,post请求必须设置.
//            mConnection.setDoInput(true); // 设置输入,post请求必须设置.
//            mConnection.setUseCaches(false); // 设置是否启用缓存,post请求不能使用缓存.
//            // 设置Content-Type属性.
//            // conn.setRequestProperty("Content-Type",
//            // "text/html;charset=utf-8");
//            mConnection.setConnectTimeout(timeout);
//            mConnection.setReadTimeout(timeout);
//            mConnection.setRequestMethod("POST");
//            mConnection.connect(); // 打开网络链接.
//            dos = new DataOutputStream(mConnection.getOutputStream());
//            // String encode = DES3.encode(JSON.toJSONString(attribute));//
//            // 请求的数据字符串进行MD5加密
//            // LogUtil.i("send:" + path.replace(ServerConfig.SERVER_API_URL, "")
//            // + "\n" + attribute.toString());
//            // HashMap<String, String> hashMap = new HashMap<String, String>();
//            // hashMap.put("params", JSON.toJSONString(attribute));
//
//            dos.writeBytes(getParams(attribute)); // 将请求参数写入网络链接.
//            dos.flush();
//            return readResponse();
//        } catch (SocketTimeoutException e) {
//            throw new TimeoutException(e.getMessage());
//        } catch (Exception e) {
//            throw new TimeoutException(e.getMessage());
//        }
//    }

    /**
     * 像指定地址发送get请求.
     *
     * @param path    数据提交路径.
     * @param timeout 超时时间,单位为毫秒.
     * @return 服务器的响应信息, 当发生错误时返回响应码.
     * @throws IOException      网络连接错误时抛出IOException.
     * @throws TimeoutException 网络连接超时时抛出TimeoutException.
     * @version 1.0
     * @createTime 2014年12月31日, 上午3:04:16
     * @updateTime 2014年12月31日, 上午3:04:16
     * @createAuthor CodeApe
     * @updateAuthor CodeApe
     * @updateInfo (此处输入修改内容, 若无修改可不写.)
     */
//    public String sendGet(String path, int timeout) throws IOException,
//            TimeoutException {
//        try {
//            URL url = new URL(path);
//            mConnection = (HttpURLConnection) url.openConnection();
//            mConnection.setUseCaches(false); // 设置是否启用缓存,post请求不能使用缓存.
//            // 设置Content-Type属性.
//            // conn.setRequestProperty("Content-Type",
//            // "text/html;charset=utf-8");
//            mConnection.setConnectTimeout(timeout);
//            mConnection.setReadTimeout(timeout);
//            mConnection.setRequestMethod("GET");
//
//            mConnection.connect(); // 打开网络链接.
//            return readResponse();
//        } catch (SocketTimeoutException e) {
//            throw new TimeoutException(e.getMessage());
//        }
//    }

    /**
     * 读取服务器响应信息.
     *
     * @return 服务器的响应信息, 当发生错误时返回响应码.
     * @throws IOException 读取信息发生错误时抛出IOException.
     * @version 1.0
     * @createTime 2014年12月31日, 上午3:04:49
     * @updateTime 2014年12月31日, 上午3:04:49
     * @createAuthor CodeApe
     * @updateAuthor CodeApe
     * @updateInfo (此处输入修改内容, 若无修改可不写.)
     */
    private String readResponse() throws IOException {
        int code = mConnection.getResponseCode();
        if (code == HttpURLConnection.HTTP_OK) { // 若响应码以2开头则读取响应头总的返回信息
            bis = new BufferedInputStream(mConnection.getInputStream());
            ByteArrayBuffer arrayBuffer = new ByteArrayBuffer(1024);
            int length = -1;
            while ((length = bis.read()) != -1) {
                arrayBuffer.append(length);
            }
            result = EncodingUtils
                    .getString(arrayBuffer.toByteArray(), "UTF-8");
        } else { // 若响应码不以2开头则返回错误信息.
            return "error";
        }
        closeConnection();
        return result;
    }

    /**
     * 将发送请求的参数构造为指定格式.
     *
     * @param attribute 发送请求的参数,key为属性名,value为属性值.
     * @return 指定格式的请求参数.
     * @version 1.0
     * @createTime 2014年12月31日, 上午3:05:28
     * @updateTime 2014年12月31日, 上午3:05:28
     * @createAuthor CodeApe
     * @updateAuthor CodeApe
     * @updateInfo (此处输入修改内容, 若无修改可不写.)
     */
    private String getParams(HashMap<String, String> attribute) {
        Set<String> keys = attribute.keySet(); // 获取所有参数名
        Iterator<String> iterator = keys.iterator(); // 将所有参数名进行跌代
        StringBuffer params = new StringBuffer();
        // 取出所有参数进行构造
        while (iterator.hasNext()) {
            try {
                String key = iterator.next();
                @SuppressWarnings("deprecation")
                String param = key + "="
                        + URLEncoder.encode(attribute.get(key)) + "&";
                params.append(param);
            } catch (Exception e) {

            }

        }
        // 返回构造结果
        return params.toString().substring(0, params.toString().length() - 1);
    }

    /**
     * 关闭链接与所有从链接中获得的流.
     *
     * @throws IOException 关闭发生错误时抛出IOException.
     * @version 1.0
     * @createTime 2014年12月31日, 上午3:06:27
     * @updateTime 2014年12月31日, 上午3:06:27
     * @createAuthor CodeApe
     * @updateAuthor CodeApe
     * @updateInfo (此处输入修改内容, 若无修改可不写.)
     */
    private void closeConnection() throws IOException {
        if (bis != null) {
            bis.close();
        }
        if (dos != null) {
            dos.close();
        }
        if (mConnection != null) {
            mConnection.disconnect();
        }
    }

    /**
     * 下载文件,下载文件存储至指定路径.
     *
     * @param path     下载路径.
     * @param savePath 存储路径.
     * @return 下载成功返回true, 若下载失败则返回false.
     * @throws MalformedURLException 建立连接发生错误抛出MalformedURLException.
     * @throws IOException           下载过程产生错误抛出IOException.
     * @version 1.0
     * @createTime 2014年12月31日, 上午3:06:58
     * @updateTime 2014年12月31日, 上午3:06:58
     * @createAuthor CodeApe
     * @updateAuthor CodeApe
     * @updateInfo (此处输入修改内容, 若无修改可不写.)
     */
//    public boolean downloadFile(String path, String savePath)
//            throws MalformedURLException, IOException {
//        File file = null;
//        InputStream input = null;
//        OutputStream output = null;
//        boolean success = false;
//        try {
//            URL url = new URL(path);
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//            conn.setRequestMethod("GET");
//            conn.setDoInput(true);
//            conn.connect();
//            int code = conn.getResponseCode();
//            if (code == HttpURLConnection.HTTP_OK) {
//                input = conn.getInputStream();
//                file = new File(savePath);
//                file.createNewFile(); // 创建文件
//                output = new FileOutputStream(file);
//                byte buffer[] = new byte[1024];
//                int read = 0;
//                while ((read = input.read(buffer)) != -1) { // 读取信息循环写入文件
//                    output.write(buffer, 0, read);
//                }
//                output.flush();
//                success = true;
//            } else {
//                success = false;
//            }
//        } catch (MalformedURLException e) {
//            success = false;
//            throw e;
//        } catch (IOException e) {
//            success = false;
//            throw e;
//        } finally {
//            try {
//                output.close();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        return success;
//    }

    /**
     * 文件上传
     *
     * @param path   服务器地址
     * @param params 附带参数集合
     * @param files  文件集合，支持多文件上传
     * @return
     * @version 1.0
     * @createTime 2014年12月31日, 上午3:11:17
     * @updateTime 2014年12月31日, 上午3:11:17
     * @createAuthor CodeApe
     * @updateAuthor CodeApe
     * @updateInfo (此处输入修改内容, 若无修改可不写.)
     */
//    public static String sendMultyPartRequest(String path,
//                                              HashMap<String, String> params, HashMap<String, File> files,
//                                              final Handler handler) {
//        HttpClient httpClient = new DefaultHttpClient();
//        // 判断是http请求还是https请求
//        if (path.toLowerCase().startsWith("https")) {
//            httpClient = sslClient(httpClient);
//        }
//
//        httpClient.getParams().setParameter(
//                CoreConnectionPNames.CONNECTION_TIMEOUT, 60000);
//        httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,
//                60000);
//        HttpPost httpPost = new HttpPost(path);
//
//        if (TApplication.iSOUTNET == 0) {
//            httpPost.setHeader("Authorization", "Basic YndvaWw6YndvaWwxMjM=");
//        }
//        final NumberFormat pernt = NumberFormat.getPercentInstance();// 将double数据转换成百分比字符串
//        pernt.setMinimumFractionDigits(2);
//
//        MultipartEntity multipartEntity = new CustomMultipartEntity(
//                new ProgressListener() {
//
//                    @Override
//                    public void transferred(long num) {
//                        if (handler != null) {
//                            Message message = new Message();
//                            message.what = 0;
//                            Bundle data = new Bundle();
//                            double progrData = (double) num / totalSize;
//                            data.putString("pro", pernt.format(progrData));
//
//                            message.setData(data);
//                            handler.sendMessage(message);
//                        }
//
//                    }
//                });// new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
//        if (params.size() > 0) {
//            Set<String> paramKeys = params.keySet();
//            Iterator<String> paramIterator = paramKeys.iterator();
//            while (paramIterator.hasNext()) {
//                String key = paramIterator.next();
//                try {
//                    StringBody stringBody = new StringBody(params.get(key),
//                            Charset.forName(HTTP.UTF_8));
//                    multipartEntity.addPart(key, stringBody);
//                } catch (UnsupportedEncodingException e) {
//                    return EXCEPTION_UPLOAD_ERROR_STATUS;
//                }
//            }
//        }
//        if (files.size() > 0) {
//            Set<String> fileKeys = files.keySet();
//            Iterator<String> fileIterator = fileKeys.iterator();
//            while (fileIterator.hasNext()) {
//                String key = fileIterator.next();
//                FileBody fileBody = new FileBody(files.get(key));
//                multipartEntity.addPart(key, fileBody);
//            }
//        }
//        httpPost.setEntity(multipartEntity);
//        totalSize = multipartEntity.getContentLength();// 获得用户上传的文件的总长度
//        String result = null;
//        try {
//            HttpResponse response = httpClient.execute(httpPost);
//            int statueCode = response.getStatusLine().getStatusCode();
//            if (statueCode == HttpStatus.SC_OK) {
//                result = EntityUtils.toString(response.getEntity(), "utf-8");
//            } else {
//                result = EXCEPTION_UPLOAD_ERROR_STATUS;
//            }
//        } catch (Exception e) {
//            result = EXCEPTION_UPLOAD_ERROR_STATUS;
//            e.printStackTrace();
//        } finally {
//            try {
//                multipartEntity.consumeContent();
//                multipartEntity = null;
//                httpPost.abort();
//            } catch (UnsupportedOperationException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        return result;
//
//    }

    /**
     * 将HashMap 转成Json格式
     *
     * @param map
     * @return
     * @version 1.0
     * @createTime 2015-8-26,下午4:05:03
     * @updateTime 2015-8-26,下午4:05:03
     * @createAuthor XiaoHuan
     * @updateAuthor
     * @updateInfo (此处输入修改内容, 若无修改可不写.)
     */
    public static String hashMapToJson(HashMap<String, String> map) {

        String string = "{";
        for (Iterator it = map.entrySet().iterator(); it.hasNext(); ) {
            Entry e = (Entry) it.next();
            string += "\"" + e.getKey() + "\":";

            string += "\"" + e.getValue() + "\",";
        }
        string = string.substring(0, string.lastIndexOf(","));
        string += "}";

        LogUtil.out(string);

        return string;
    }

    /**
     * 判断是否wifi
     *
     * @param mContext
     * @return
     */
    public static boolean isWifi(Context mContext) {
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetInfo != null
                && activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            return true;
        }
        return false;
    }
}
