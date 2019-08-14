//package com.exchange_v1.app.utils;
//
//import android.annotation.SuppressLint;
//import android.content.Context;
//import android.net.ConnectivityManager;
//import android.net.NetworkInfo;
//import android.text.TextUtils;
//
//import com.alibaba.fastjson.JSON;
//import com.brightoilonline.c2b_phone.config.ServerConfig;
//import com.brightoilonline.c2b_phone.utils.Cancel.CancelException;
//
//import org.apache.http.HttpResponse;
//import org.apache.http.HttpStatus;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.entity.mime.HttpMultipartMode;
//import org.apache.http.entity.mime.MultipartEntity;
//import org.apache.http.entity.mime.content.FileBody;
//import org.apache.http.entity.mime.content.StringBody;
//import org.apache.http.impl.client.DefaultHttpClient;
//import org.apache.http.protocol.HTTP;
//import org.apache.http.util.EncodingUtils;
//import org.apache.http.util.EntityUtils;
//
//import java.io.BufferedInputStream;
//import java.io.ByteArrayOutputStream;
//import java.io.DataOutputStream;
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.io.UnsupportedEncodingException;
//import java.net.HttpURLConnection;
//import java.net.MalformedURLException;
//import java.net.SocketTimeoutException;
//import java.net.URL;
//import java.net.URLEncoder;
//import java.nio.charset.Charset;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.Set;
//import java.util.concurrent.TimeoutException;
//
///**
// * 网络操作类.
// *
// * @Description 用于网络的POST 、 GET 、 MutilPart等操作
// * @author CodeApe
// * @version 1.0
// * @date 2014年3月30日
// * @Copyright: Copyright (c) 2013 深圳光汇云油电商有限公司.
// *
// */
//public class Net {
//
//	/** 本地文件上传失败 */
//	public static final String EXCEPTION_UPLOAD_ERROR_STATUS = "805";
//
//	/** 返回结果 */
//	private String result;
//	/** 输入流 */
//	private BufferedInputStream input;
//	/** http链接 */
//	private HttpURLConnection conn;
//	/** 输出流 */
//	private DataOutputStream output;
//
//	/**
//	 * 获取当前网络链接状态.
//	 *
//	 * @param context 上下文环境.
//	 * @return 有任意网络通畅时返回true,否则返回false.
//	 *
//	 * @version 1.0
//	 * @createTime 2013-3-5,下午4:21:27
//	 * @updateTime 2013-3-5,下午4:21:27
//	 * @createAuthor paladin
//	 * @updateAuthor paladin
//	 * @updateInfo
//	 */
//	public boolean isNetworkAvailable(Context context) {
//		ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE); // 获取网络服务
//		if (connectivity == null) { // 判断网络服务是否为空
//			return false;
//		} else { // 判断当前是否有任意网络服务开启
//			@SuppressLint("MissingPermission") NetworkInfo[] info = connectivity.getAllNetworkInfo();
//			if (info != null) {
//				for (int i = 0; i < info.length; i++) {
//					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
//						return true;
//					}
//				}
//			}
//		}
//		return false;
//	}
//
//	/**
//	 * 像指定地址发送post请求提交数据.
//	 *
//	 * @version 1.0
//	 * @createTime 2014年12月31日,上午2:06:53
//	 * @updateTime 2014年12月31日,上午2:06:53
//	 * @createAuthor CodeApe
//	 * @updateAuthor CodeApe
//	 * @updateInfo (此处输入修改内容,若无修改可不写.)
//	 *
//	 * @param path 数据提交路径.
//	 * @param timeout 超时时间(毫秒).
//	 * @param attribute 发送请求参数,key为属性名,value为属性值.
//	 * @return 服务器的响应信息,当发生错误时返回响应码.
//	 * @throws IOException 网络连接错误时抛出IOException.
//	 * @throws TimeoutException 网络连接超时时抛出TimeoutException.
//	 *
//	 */
//	public String sendPost(String path, int timeout, HashMap<String, String> attribute) throws IOException, TimeoutException, CancelException {
//		try {
//			Cancel.checkInterrupted();
//			URL url = new URL(path);
//			conn = (HttpURLConnection) url.openConnection();
//			conn.setDoOutput(true); // 设置输出,post请求必须设置.
//			conn.setDoInput(true); // 设置输入,post请求必须设置.
//			conn.setUseCaches(false); // 设置是否启用缓存,post请求不能使用缓存.
//			// 设置Content-Type属性.
//			// conn.setRequestProperty("Content-Type",
//			// "text/html;charset=utf-8");
//			conn.setConnectTimeout(timeout);
//			conn.setReadTimeout(timeout);
//			conn.setRequestMethod("POST");
//			conn.connect(); // 打开网络链接.
//			Cancel.checkInterrupted();
//			output = new DataOutputStream(conn.getOutputStream());
//			LogUtil.i("send=>" + path.replace(ServerConfig.SERVER_API_URL, "") + "\n" + attribute.toString());
//			String encode = DES3.encode(JSON.toJSONString(attribute));
//			// 请求的数据字符串进行MD5加密
//			HashMap<String, String> hashMap = new HashMap<String, String>();// 请求的数据以
//			hashMap.put("params", encode);
//
//			output.writeBytes(getParams(hashMap)); // 将请求参数写入网络链接.
//			output.flush();
//			Cancel.checkInterrupted();
//			return readResponse();
//		} catch (SocketTimeoutException e) {
//			throw new TimeoutException(e.getMessage());
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new TimeoutException(e.getMessage());
//		}
//	}
//
//	/**
//	 * 像指定地址发送get请求.
//	 *
//	 * @param path 数据提交路径.
//	 * @param timeout 超时时间,单位为毫秒.
//	 * @return 服务器的响应信息,当发生错误时返回响应码.
//	 * @throws IOException 网络连接错误时抛出IOException.
//	 * @throws TimeoutException 网络连接超时时抛出TimeoutException.
//	 *
//	 * @version 1.1
//	 * @createTime 2013-3-5,下午4:40:14
//	 * @updateTime 2013-4-17,下午3:28:56
//	 * @createAuthor paladin
//	 * @updateAuthor paladin
//	 * @updateInfo 捕获非致命异常SocketTimeoutException同时抛出致命异常TimeoutException.
//	 */
//	public String sendGet(String path, int timeout) throws IOException, TimeoutException, CancelException {
//		try {
//			Cancel.checkInterrupted();
//			URL url = new URL(path);
//			conn = (HttpURLConnection) url.openConnection();
//			conn.setUseCaches(false); // 设置是否启用缓存,post请求不能使用缓存.
//			// 设置Content-Type属性.
//			// conn.setRequestProperty("Content-Type",
//			// "text/html;charset=utf-8");
//			conn.setConnectTimeout(timeout);
//			conn.setReadTimeout(timeout);
//			conn.setRequestMethod("GET");
//
//			conn.connect(); // 打开网络链接.
//			Cancel.checkInterrupted();
//			return readResponse();
//		} catch (SocketTimeoutException e) {
//			throw new TimeoutException(e.getMessage());
//		}
//	}
//
//	/**
//	 * 读取服务器响应信息.
//	 *
//	 * @return 服务器的响应信息,当发生错误时返回响应码.
//	 * @throws IOException 读取信息发生错误时抛出IOException.
//	 *
//	 * @version 1.0
//	 * @createTime 2013-3-5,下午4:48:00
//	 * @updateTime 2013-3-5,下午4:48:00
//	 * @createAuthor paladin
//	 * @updateAuthor paladin
//	 * @updateInfo
//	 */
//	private String readResponse() throws IOException, CancelException {
//		int code = conn.getResponseCode();
//		if (code == HttpURLConnection.HTTP_OK) { // 若响应码以2开头则读取响应头总的返回信息
//
//			input = new BufferedInputStream(conn.getInputStream());
//			byte[] buffer = new byte[1024];
//			ByteArrayOutputStream bos = new ByteArrayOutputStream();
//			int length = -1;
//			Thread currentThread = Thread.currentThread();
//
//			while ((length = input.read(buffer)) != -1) {
//				Cancel.checkInterrupted(currentThread);
//				bos.write(buffer, 0, length);
//			}
//			result = EncodingUtils.getString(bos.toByteArray(), "UTF-8");
//
//			// 截取前三个字节，判断密文是否有bom头（EFBBBF）
//			String resultHeader = StringUtil.getHexString(result.getBytes(), 3);
//			if (!TextUtils.isEmpty(result) && !TextUtils.isEmpty(resultHeader) && resultHeader.equals("EFBBBF")) {
//				result = new String(result.getBytes(), 3, result.getBytes().length - 3);
//			}
//			// result = AuthcodeTwo.authcodeDecode(result, ServerConfig.KEY);
//			bos.close();
//			input.close();
//			Cancel.checkInterrupted(currentThread);
//		} else { // 若响应码不以2开头则返回错误信息.
//			return "error";
//		}
//		closeConnection();
//		return result;
//	}
//
//	/**
//	 * 将发送请求的参数构造为指定格式.
//	 *
//	 * @param attribute 发送请求的参数,key为属性名,value为属性值.
//	 * @return 指定格式的请求参数.
//	 *
//	 * @version 1.0
//	 * @createTime 2013-3-5,下午4:49:45
//	 * @updateTime 2013-3-5,下午4:49:45
//	 * @createAuthor paladin
//	 * @updateAuthor paladin
//	 * @updateInfo
//	 */
//	private String getParams(HashMap<String, String> attribute) {
//		Set<String> keys = attribute.keySet(); // 获取所有参数名
//		Iterator<String> iterator = keys.iterator(); // 将所有参数名进行跌代
//		StringBuffer params = new StringBuffer();
//		// 取出所有参数进行构造
//		while (iterator.hasNext()) {
//			try {
//				String key = iterator.next();
//				@SuppressWarnings("deprecation")
//                String param = key + "=" + URLEncoder.encode(attribute.get(key)) + "&";
//				params.append(param);
//			} catch (Exception e) {
//
//			}
//
//		}
//
//		// 返回构造结果
//		String result = params.toString().substring(0, params.toString().length() - 1);
//		return result;
//		// return AuthcodeTwo.authcodeEncode(string, ServerConfig.KEY);
//	}
//
//	/**
//	 * 关闭链接与所有从链接中获得的流.
//	 *
//	 * @throws IOException 关闭发生错误时抛出IOException.
//	 *
//	 * @version 1.0
//	 * @createTime 2013-3-5,下午4:51:31
//	 * @updateTime 2013-3-5,下午4:51:31
//	 * @createAuthor paladin
//	 * @updateAuthor paladin
//	 * @updateInfo
//	 */
//	private void closeConnection() throws IOException {
//		if (input != null) {
//			input.close();
//		}
//		if (output != null) {
//			output.close();
//		}
//		if (conn != null) {
//			conn.disconnect();
//		}
//	}
//
//	/**
//	 * 下载文件,下载文件存储至指定路径.
//	 *
//	 * @param path 下载路径.
//	 * @param savePath 存储路径.
//	 * @return 下载成功返回true,若下载失败则返回false.
//	 * @throws MalformedURLException 建立连接发生错误抛出MalformedURLException.
//	 * @throws IOException 下载过程产生错误抛出IOException.
//	 *
//	 * @version 1.2
//	 * @createTime 2013-3-6,下午4:10:13
//	 * @updateTime 2013-5-29,下午4:56:43
//	 * @createAuthor paladin
//	 * @updateAuthor paladin
//	 * @updateInfo 取消图片的下载后缀,取消文件下载（除.jpg文件外）的tmp流程.
//	 */
//	public boolean downloadFile(String path, String savePath) throws MalformedURLException, IOException {
//		File file = null;
//		InputStream input = null;
//		OutputStream output = null;
//		boolean success = false;
//		try {
//			URL url = new URL(path);
//			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//			conn.setRequestMethod("GET");
//			conn.setDoInput(true);
//			conn.connect();
//			int code = conn.getResponseCode();
//			if (code == HttpURLConnection.HTTP_OK) {
//				input = conn.getInputStream();
//				file = new File(savePath);
//				file.createNewFile(); // 创建文件
//				output = new FileOutputStream(file);
//				byte buffer[] = new byte[1024];
//				int read = 0;
//				while ((read = input.read(buffer)) != -1) { // 读取信息循环写入文件
//					output.write(buffer, 0, read);
//				}
//				output.flush();
//				success = true;
//			} else {
//				success = false;
//			}
//		} catch (MalformedURLException e) {
//			success = false;
//			throw e;
//		} catch (IOException e) {
//			success = false;
//			throw e;
//		} finally {
//			try {
//				output.close();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//		return success;
//	}
//
//	/**
//	 * 上传文件
//	 *
//	 * @author 罗文忠
//	 * @version 1.0
//	 * @date 2013-4-15
//	 *
//	 * @param path
//	 * @param files
//	 * @return
//	 */
//	public static String sendMultyPartRequest(String path, HashMap<String, String> params, HashMap<String, File> files) {
//		HttpClient httpClient = new DefaultHttpClient();
//		HttpPost httpPost = new HttpPost(path);
//		MultipartEntity multipartEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
//		if (params.size() > 0) {
//			Set<String> paramKeys = params.keySet();
//			Iterator<String> paramIterator = paramKeys.iterator();
//			while (paramIterator.hasNext()) {
//				String key = paramIterator.next();
//				try {
//					StringBody stringBody = new StringBody(params.get(key), Charset.forName(HTTP.UTF_8));
//					multipartEntity.addPart(key, stringBody);
//				} catch (UnsupportedEncodingException e) {
//					return EXCEPTION_UPLOAD_ERROR_STATUS;
//				}
//			}
//		}
//		if (files.size() > 0) {
//			Set<String> fileKeys = files.keySet();
//			Iterator<String> fileIterator = fileKeys.iterator();
//			while (fileIterator.hasNext()) {
//				String key = fileIterator.next();
//				FileBody fileBody = new FileBody(files.get(key));
//				multipartEntity.addPart(key, fileBody);
//			}
//		}
//		httpPost.setEntity(multipartEntity);
//
//		String result = null;
//		try {
//			HttpResponse response = httpClient.execute(httpPost);
//			int statueCode = response.getStatusLine().getStatusCode();
//			if (statueCode == HttpStatus.SC_OK) {
//				result = EntityUtils.toString(response.getEntity(), "utf-8");
//			} else {
//				result = EXCEPTION_UPLOAD_ERROR_STATUS;
//			}
//		} catch (Exception e) {
//			result = EXCEPTION_UPLOAD_ERROR_STATUS;
//			e.printStackTrace();
//		} finally {
//			try {
//				multipartEntity.consumeContent();
//				multipartEntity = null;
//				httpPost.abort();
//			} catch (UnsupportedOperationException e) {
//				e.printStackTrace();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
//		return result;
//
//	}
//
//	/**
//	 * 校验链接是否有效
//	 *
//	 * @version 1.0
//	 * @createTime 2015年4月4日,上午12:48:54
//	 * @updateTime 2015年4月4日,上午12:48:54
//	 * @createAuthor CodeApe
//	 * @updateAuthor CodeApe
//	 * @updateInfo (此处输入修改内容,若无修改可不写.)
//	 *
//	 * @param url 链接
//	 * @return false 无效，true 有效
//	 */
//	public static boolean checkURL(String url) {
//		boolean value = true;
//		HttpURLConnection conn = null;
//		InputStream is = null;
//		try {
//			conn = (HttpURLConnection) new URL(url).openConnection();
//			is = conn.getInputStream();
//			int size = is.available();
//			if (size <= 1) {
//				value = false;
//			}
//		} catch (IOException e) {
//			value = false;
//			e.printStackTrace();
//		} finally {
//			try {
//				is.close();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//			if (null != conn) {
//				conn.disconnect();
//				conn = null;
//			}
//		}
//		return value;
//	}
//
//}
