package com.exchange_v1.app.utils;//package com.brightoilonline.c2b_phone.utils;
//
//import android.content.res.AssetManager;
//
//import com.brightoilonline.c2b_phone.config.TApplication;
//
//import java.io.InputStream;
//import java.security.GeneralSecurityException;
//import java.security.KeyStore;
//import java.security.cert.Certificate;
//import java.security.cert.CertificateFactory;
//
//import javax.net.ssl.SSLContext;
//import javax.net.ssl.TrustManager;
//
//public class HttpsUtil {
//	static SSLContext context;
//	static KeyStore keyStore;
//
//	//	private static final String KEYSTORE_PASSWORD = "pass";
//	//	private static final String TRUSTSTORE_PASSWORD = "secret";
//
//	// private KeyStore loadKeyStore() {
//	// if (keyStore != null) {
//	// return keyStore;
//	// }
//	//
//	// try {
//	// keyStore = KeyStore.getInstance("PKCS12");
//	// InputStream in = TApplication.context.getResources()
//	// .openRawResource(R.raw.mykeystore);
//	// try {
//	// keyStore.load(in, KEYSTORE_PASSWORD.toCharArray());
//	// } finally {
//	// in.close();
//	// }
//	//
//	// return keyStore;
//	// } catch (Exception e) {
//	// throw new RuntimeException(e);
//	// }
//	// }
//
//	public static KeyStore getKeyStore() throws Exception {
//		if (null != keyStore) {
//			return keyStore;
//		}
//
//		AssetManager am = TApplication.context.getAssets();
//		InputStream ins = am.open("bwoil1.cer");
//
//		// 读取证书
//		CertificateFactory cerFactory = CertificateFactory.getInstance("X.509"); // 问1
//		Certificate cer = cerFactory.generateCertificate(ins);
//		// 创建一个证书库，并将证书导入证书库
//		keyStore = KeyStore.getInstance("PKCS12", "BC"); // 问2
//		keyStore.load(null, null);
//		keyStore.setCertificateEntry("trust", cer);
//		ins.close();
//		return keyStore;
//	}
//
//	public static SSLContext createSslContext(boolean clientAuth)
//			throws GeneralSecurityException, Exception {
//		if (null == context) {
//
//			KeyStore keyStore = getKeyStore();
//			//KeyStore trustStore = loadTrustStore();
//			MyTrustManager myTrustManager = new MyTrustManager(keyStore);
//			TrustManager[] tms = new TrustManager[] { myTrustManager };
//
////			KeyManager[] kms = null;
////			if (clientAuth) {
////				KeyManagerFactory kmf = KeyManagerFactory
////						.getInstance(KeyManagerFactory.getDefaultAlgorithm());
////				kmf.init(keyStore, KEYSTORE_PASSWORD.toCharArray());
////				kms = kmf.getKeyManagers();
////			}
//			context = SSLContext.getInstance("TLS");
//			context.init(null, tms, null);
//		}
//
//		return context;
//	}
//
////	private static KeyStore loadTrustStore() {
////		AssetManager am = TApplication.context.getAssets();
////		try {
////			KeyStore localTrustStore = KeyStore.getInstance("BKS");
////			// InputStream in =
////			// getResources().openRawResource(R.raw.mytruststore);
////			InputStream in = am.open("bwoil1.cer");
////			try {
////				localTrustStore.load(in,null);
////			} finally {
////				in.close();
////			}
////
////			return localTrustStore;
////		} catch (Exception e) {
////			throw new RuntimeException(e);
////		}
////	}
//}
