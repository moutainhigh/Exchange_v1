package com.exchange_v1.app.utils;

import android.os.Build;
import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 * WebViewUtil
 * Created by liangxg on 2016/10/24.
 */
public class WebViewUtil {

    /**官方对自适应的最佳实践*/
    public static String getHtmlData(String bodyHTML) {
        String head = "<head>" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\"> " +
                "<style>img{max-width: 100%; width:auto; height:auto;}</style>" +
                "</head>";
        return "<html>" + head + "<body>" + bodyHTML + "</body></html>";
    }

    /**
     * webview 显示utf8数据
     * @param webView
     * @param data
     */
    public static void loadDataWithUtf8(WebView webView, String data){
            if(null==webView){
                return;
            }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);
        } else {
            webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        }

        String htmlText= getHtmlData(data);
        webView.loadData(htmlText, "text/html; charset=utf-8", "utf-8");
    }


}
