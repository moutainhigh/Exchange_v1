package com.exchange_v1.app.navition;

public class NativeAction {

	static{
		System.loadLibrary("marine");
	}
	
	public static native int isDexComplete(String crc);
	
    /**
     * @param apkPath apkPath
     * @return apk指纹
     */
    public static native String createMD5(String apkPath);
}
