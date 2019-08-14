package com.exchange_v1.app.bean;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 版本更新属性类
 *
 * @author 罗文忠
 * @version 1.0
 * @date 2013-5-12
 */
public class UpdateBean extends BaseBean {

    /**
     * @author yeqing
     * @version 1.0
     * @date 2015-5-8
     */
    private static final long serialVersionUID = -2252012243305551446L;
    /**
     * 系统名称
     */
    private String system_Name;
    /**
     * 新版版本号
     */
    private String version;
    /**
     * 下载地址
     */
    private String url;
    /**
     * 版本说明
     */
    private String note;
    /**
     * 录入时间
     */
    private String dateLine;
    /**
     * 强制更新 (0为不强制，1为强制)'
     */
    private String is_sure;
    /**
     * 文件MD5值
     */
    private String file_MD5;
    /**
     * 服务器地址
     */
    private String server_Url;
    /**
     * 文件大小
     */
    private String file_Size;
    /**
     * 版本名称
     */
    private String version_name;

    public String getForceUpdteVer() {
        return forceUpdteVer;
    }

    public void setForceUpdteVer(String forceUpdteVer) {
        this.forceUpdteVer = forceUpdteVer;
    }

    public String getJbundleUrl() {
        return JbundleUrl;
    }

    public void setJbundleUrl(String jbundleUrl) {
        JbundleUrl = jbundleUrl;
    }

    public String getJbundle() {
        return Jbundle;
    }

    public void setJbundle(String jbundle) {
        Jbundle = jbundle;
    }

    //	最低强制更新版本号
    private String forceUpdteVer;
    //	强制更新RN地址
    private String JbundleUrl;
    //	意义未知道  老魏要求增加
    private String Jbundle;

    @Override
    protected void init(JSONObject jSon) throws JSONException {
        setUrl(jSon.optString("Update_url"));
        setVersion(jSon.optString("Version_build"));
        setIs_sure(jSon.optString("Force_Update"));
        setVersion_name(jSon.optString("Version_no"));
        setNote(jSon.optString("Note"));
        setForceUpdteVer(jSon.optString("forceUpdteVer"));
        setJbundle(jSon.optString("Jbundle"));
        setJbundleUrl(jSon.optString("JbundleUrl"));

    }

    public String getSystem_Name() {
        return system_Name;
    }

    public void setSystem_Name(String system_Name) {
        this.system_Name = system_Name;
    }

    public String getVersion_name() {
        return version_name;
    }

    public void setVersion_name(String version_name) {
        this.version_name = version_name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getDateLine() {
        return dateLine;
    }

    public void setDateLine(String dateLine) {
        this.dateLine = dateLine;
    }

    public String getIs_sure() {
        return is_sure;
    }

    public void setIs_sure(String is_sure) {
        this.is_sure = is_sure;
    }

    public String getFile_MD5() {
        return file_MD5;
    }

    public void setFile_MD5(String file_MD5) {
        this.file_MD5 = file_MD5;
    }

    public String getServer_Url() {
        return server_Url;
    }

    public void setServer_Url(String server_Url) {
        this.server_Url = server_Url;
    }

    public String getFile_Size() {
        return file_Size;
    }

    public void setFile_Size(String file_Size) {
        this.file_Size = file_Size;
    }

}
