package com.exchange_v1.app.utils;

import android.content.Context;

import com.brightoilonline.c2b_phone.bean.ProductClickNewCountBean;
import com.brightoilonline.c2b_phone.bean.ProductClickNewItemBean;

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

public class ClickListUtils {



    public static ClickListUtils getListUtils(){
        ClickListUtils listUtils=new ClickListUtils();
        return listUtils;
    }
//    //context 上下文   event_id 事件id   event_name 事件名字 　　　event_num 事件数量  event_content 备注, event_remark 备注
     public  void setStatisticsBeanData(Context context, String event_id, String event_name, String event_num, String event_content, String event_remark){

         ProductClickNewCountBean databean = new ProductClickNewCountBean();

         ProductClickNewItemBean itemBean = new ProductClickNewItemBean(event_id, event_name, event_num, "", event_remark);
//         map.put("event_id",event_id);
//         map.put("event_name",event_name);
//         map.put("event_num",event_num);
//         map.put("event_content",event_content);
//         map.put("event_remark","");
//         databean.setParam_json(map);
         databean.setParamJsonBean(itemBean);

         List<ProductClickNewCountBean> mlist=getStorageEntities();
         mlist.add(databean);
         saveStorage2SDCard(mlist);
        }

//    public  void setStatisticsBeanData(Context context, String event_id, String event_name, String event_num, String event_content, String event_remark){
//        ClickBean bean=new ClickBean();
//        ClickBean.ParamJsonBean paramJsonBean= new ClickBean.ParamJsonBean();
//        bean.setEvent_type("click");
//        paramJsonBean.setEvent_id(event_id);
//        paramJsonBean.setEvent_name(event_name);
//        paramJsonBean.setEvent_num(event_num);
//        paramJsonBean.setEvent_content(event_content);
//        paramJsonBean.setEvent_remark(event_remark);
//        bean.setParam_json(paramJsonBean);
//         List<ClickBean> mlist=getStorageEntities();
//         mlist.add(bean);
//         saveStorage2SDCard(mlist);
//        }

        /**
         * 数据存放在本地
         *
         * @param tArrayList
         */
        public static void saveStorage2SDCard(Object tArrayList) {
            File file1 = new File( "/sdcard/c2b_phone/Camera/","product_click.xml");
            if (file1.exists()){
                file1.delete();
            }
            FileOutputStream fileOutputStream = null;
            ObjectOutputStream objectOutputStream = null;
            try {
                File file = new File( "/sdcard/c2b_phone/Camera/","product_click.xml");
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
        public static ArrayList<ProductClickNewCountBean> getStorageEntities() {
            ObjectInputStream objectInputStream = null;
            FileInputStream fileInputStream = null;
            File file=null;
            ArrayList<ProductClickNewCountBean> savedArrayList = new ArrayList<>();
            try {
                file= new File( "/sdcard/c2b_phone/Camera/", "product_click.xml");
                if (!file.exists()) {
                    file.getParentFile().mkdirs();
                    file.createNewFile();
                }
                fileInputStream = new FileInputStream(file.toString());
                objectInputStream = new ObjectInputStream(fileInputStream);
                savedArrayList = (ArrayList<ProductClickNewCountBean>) objectInputStream.readObject();
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
                File file = new File( "/sdcard/c2b_phone/Camera/", fileName);
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
