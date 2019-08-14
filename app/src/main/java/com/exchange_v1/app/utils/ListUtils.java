package com.exchange_v1.app.utils;

import android.content.Context;

import com.brightoilonline.c2b_phone.bean.StatisticsBean;
import com.brightoilonline.c2b_phone.bean.dto.ListStatisticsBean;
import com.brightoilonline.c2b_phone.config.TApplication;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yanchuang on 2017/11/9.
 */

public class ListUtils {
    public static String LOGIN_FAIL = "login_fail";
    public static String PAY_FAIL = "pay_fail";
    StatisticsBean mBean = new StatisticsBean();
    StatisticsBean.ParamJsonBean mParamJsonBean = new StatisticsBean.ParamJsonBean();
    ListStatisticsBean mListStatisticsBean = new ListStatisticsBean();

    public static ListUtils getListUtils() {
        ListUtils listUtils = new ListUtils();
        return listUtils;
    }

    //context 上下文   event_type 事件类型   end_time 离开时间  　　　page_name 页面名称   start_time 进入时间
    public void setStatisticsBeanData(Context context, String out_time, String page_id, String in_time) {
        String phoneNum="";
        String member_id="";
        int  visioncode = UpdateManager.getVersionCode(context);// 本地版本号
        String visioncodeS=visioncode+"";
        String ver_info=visioncodeS.substring(0,1)+"."+visioncodeS.substring(1,2)+"."+visioncodeS.substring(2,visioncodeS.length());
        if (TApplication.userAccountBean!=null){
          phoneNum = TApplication.userAccountBean.getMoblie();
        }
        if (TApplication.userInfoBean!=null){
             member_id = TApplication.userInfoBean.getMember_id();
        }


        String equipment_id = Installation.idNoLocation(context);//设备id
        String from = SystemUtil.getUmengChannel(context);//渠道号
        String longitude = SystemUtil.getLongitude();//经度
        String latitude = SystemUtil.getLatitude();//纬度
        String remark = "";//备注 默认为空
        String terminal_type = "APP";//终端类型 APP
        String log_type = "";
        String event_type = "page";
        if (event_type.equals(PAY_FAIL) || event_type.equals(LOGIN_FAIL)) {
            log_type = "异常操作"; /*异常操作*/
        } else {
            log_type = "正常操作"; /*正常操作*/
        }
        int duration = 0;
        String key = "";
        String ver = ""+visioncode;
        String version_info="Android("+ver_info+")";
        mBean.setEvent_type(event_type);
        mParamJsonBean.setVersion_info(version_info);
        mParamJsonBean.setDuration(duration);
        mParamJsonBean.setEquipment_id(equipment_id);
        mParamJsonBean.setOut_time(out_time);
        mParamJsonBean.setFrom(from);
        mParamJsonBean.setKey(key);
        mParamJsonBean.setLatitude(latitude);
        mParamJsonBean.setLog_type(log_type);
        mParamJsonBean.setLongitude(longitude);
        mParamJsonBean.setPage_id(page_id);
        mParamJsonBean.setRemark(remark);
        mParamJsonBean.setIn_time(in_time);
        mParamJsonBean.setTerminal_type(terminal_type);
        mParamJsonBean.setVer(ver);
        mParamJsonBean.setMember_id(member_id);
        mParamJsonBean.setPhone(phoneNum);
        mBean.setParam_json(mParamJsonBean);
        List<StatisticsBean> mlist=getStorageEntities();
        mlist.add(mBean);
        saveStorage2SDCard(mlist);
//         List<StatisticsBean> mlist=getStorageEntities();
//         mlist.add(mBean);
    }

    /**
     * 数据存放在本地
     *
     * @param tArrayList
     */
    public static void saveStorage2SDCard(Object tArrayList) {
        File file1 = new File("/sdcard/c2b_phone/Camera/", "user_click.xml");
        if (file1.exists()) {
            file1.delete();
        }
        FileOutputStream fileOutputStream = null;
        ObjectOutputStream objectOutputStream = null;
        try {
            File file = new File("/sdcard/c2b_phone/Camera/", "user_click.xml");
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            fileOutputStream = new FileOutputStream(file);  //新建一个内容为空的文件
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(tArrayList);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (objectOutputStream != null) {
            try {
                objectOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (fileOutputStream != null) {
            try {
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 获取本地的List数据
     *
     * @return
     */
    public static ArrayList<StatisticsBean> getStorageEntities() {
        ObjectInputStream objectInputStream = null;
        FileInputStream fileInputStream = null;
        File file = null;
        ArrayList<StatisticsBean> savedArrayList = new ArrayList<>();
        try {
            file = new File("/sdcard/c2b_phone/Camera/", "user_click.xml");
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            fileInputStream = new FileInputStream(file.toString());
            objectInputStream = new ObjectInputStream(fileInputStream);
            savedArrayList = (ArrayList<StatisticsBean>) objectInputStream.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (objectInputStream != null) {
                    objectInputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (fileInputStream != null) {
                    fileInputStream.close();

                }
            } catch (IOException e) {
                e.printStackTrace();
            }


        }

        return savedArrayList;
    }


    /**
     * 获取本地的List数据
     *
     * @return
     */
    public static ArrayList<Integer> getStorageEntitiesSpinner(String fileName) {
        ObjectInputStream objectInputStream = null;
        FileInputStream fileInputStream = null;
        ArrayList<Integer> savedArrayList = new ArrayList<>();
        try {
            File file = new File("/sdcard/c2b_phone/Camera/", fileName);
            fileInputStream = new FileInputStream(file.toString());
            objectInputStream = new ObjectInputStream(fileInputStream);
            savedArrayList = (ArrayList<Integer>) objectInputStream.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (objectInputStream != null) {
                    objectInputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }


        }

        return savedArrayList;
    }

}
