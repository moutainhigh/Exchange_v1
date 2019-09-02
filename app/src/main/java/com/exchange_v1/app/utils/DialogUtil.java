package com.exchange_v1.app.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.exchange_v1.app.R;
import com.exchange_v1.app.base.TApplication;
import com.exchange_v1.app.bean.UpdateBean;
import com.exchange_v1.app.config.FileConfig;
import com.exchange_v1.app.config.RequestCode;
import com.exchange_v1.app.view.CustomDialog;
import com.exchange_v1.app.view.HorizontalCustomDialog;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


/**
 * 对话框封装工具类
 *
 * @author 罗文忠
 * @version 1.0
 * @date 2013-03-20
 */
@SuppressLint("ResourceAsColor")
public class DialogUtil {


    public static boolean isDialogShow = false;


    /**
     * 显示日期选择对话框
     *
     * @param context 上下文
     * @param v       需要设置日期的控件 此处需要传递button对象
     * @author 罗文忠
     * @date 2013-03-20
     * @version 1.0
     */
    public static void showDatePickDialog(Context context, final View v) {
        DatePickerDialog.OnDateSetListener odsl = new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                Calendar c = Calendar.getInstance();
                c.set(year, monthOfYear, dayOfMonth);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String dateString = sdf.format(new Date(c.getTimeInMillis()));
                ((Button) v).setText(dateString);
            }
        };

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dpd = new DatePickerDialog(context, odsl, year, month,
                day);
        dpd.show();
    }

    /**
     * 显示日期选择对话框
     *
     * @param context
     * @version 1.0
     * @createTime 2014年11月27日, 下午5:36:32
     * @updateTime 2014年11月27日, 下午5:36:32
     * @createAuthor WangYuWen
     * @updateAuthor WangYuWen
     * @updateInfo (此处输入修改内容, 若无修改可不写.)
     */
    public static void showDatePickDialog(Context context, final EditText edit) {
        DatePickerDialog.OnDateSetListener odsl = new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                Calendar c = Calendar.getInstance();
                c.set(year, monthOfYear, dayOfMonth);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String dateString = sdf.format(new Date(c.getTimeInMillis()));
                edit.setText(dateString);
            }
        };

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dpd = new DatePickerDialog(context, odsl, year, month,
                day);
        dpd.show();
    }

    /**
     * 显示有默认日期的日期选择对话框
     *
     * @param context 上下文
     * @param date    默认显示日期 格式为 yyyy-MM-dd
     * @param v       需要设置日期的控件 此处需要传递button对象
     * @author 罗文忠
     * @date 2013-03-20
     * @version 1.0
     */
    public static void showDatePickDialog(Context context, String date,
                                          final View v) {
        DatePickerDialog.OnDateSetListener odsl = new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                Calendar c = Calendar.getInstance();
                c.set(year, monthOfYear, dayOfMonth);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String dateString = sdf.format(new Date(c.getTimeInMillis()));
                ((Button) v).setText(dateString);
            }
        };

        int year = 1970;
        int month = 0;
        int day = 1;
        String[] dates = date.split("-");

        if (dates.length != 3) { // 如果传入日期格式不合法，将默认显示当前日期
            Calendar calendar = Calendar.getInstance();
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            day = calendar.get(Calendar.DAY_OF_MONTH);
        } else {
            year = Integer.parseInt(dates[0]);
            month = Integer.parseInt(dates[1]) - 1;
            day = Integer.parseInt(dates[2]);
        }

        DatePickerDialog dpd = new DatePickerDialog(context, odsl, year, month,
                day);
        dpd.show();
    }

    /**
     * 显示日期对话框
     *
     * @param context  上下文
     * @param date     日期
     * @param listener 确定监听按钮
     * @version 1.0
     * @createTime 2013-12-27,下午2:34:16
     * @updateTime 2014-03-05,下午15:34:16
     * @createAuthor CodeApe
     * @updateAuthor wujianxing
     * @updateInfo (重構了一下方法)
     */
    public static void showDatePickDialog(Context context, String date,
                                          final CustomDialog.OnClickListener listener) {
        DatePickerDialog.OnDateSetListener odsl = new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                Calendar c = Calendar.getInstance();
                c.set(year, monthOfYear, dayOfMonth);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String dateString = sdf.format(new Date(c.getTimeInMillis()));
                listener.onClick(null, -1, dateString);
            }
        };
        showDatePickDialog(context, date, odsl);
    }

    /**
     * 显示日期对话框,传入日期不得小于当前日期
     *
     * @param context 上下文
     * @param date    日期
     * @param odsl    日期的設置的監聽
     * @version 1.0
     * @createTime 2013-12-27,下午2:34:16
     * @updateTime 2014-03-05,下午15:34:16
     * @createAuthor CodeApe
     * @updateAuthor wujianxing
     * @updateInfo (重構了一下方法)
     * @updateAuthor XinGo
     * @updateInfo (预约时间修改)
     */
    public static void showDatePickDialog(final Context context, String date,
                                          DatePickerDialog.OnDateSetListener odsl) {
        int year = 1970;
        int month = 0;
        int day = 1;

        Calendar cal = Calendar.getInstance();
        int sysYear = cal.get(Calendar.YEAR); // 系统年份
        int sysMonth = cal.get(Calendar.MONTH);// 系统月份
        int sysDay = cal.get(Calendar.DAY_OF_MONTH);// 系统日期
        if (TextUtils.isEmpty(date)) {
            Calendar calendar = Calendar.getInstance();
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            day = calendar.get(Calendar.DAY_OF_MONTH);
        } else {
            String[] dates = date.split("-");
            if (dates.length != 3) { // 如果传入日期格式不合法，将默认显示当前日期
                Calendar calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);
            } else {
                year = Integer.parseInt(dates[0]);
                month = Integer.parseInt(dates[1]) - 1;
                day = Integer.parseInt(dates[2]);
                // 如果日期小于等于今天的日期 则加1天
                if (year < sysYear || month < sysMonth || day <= sysDay) {
                    year = sysYear;
                    month = sysMonth;
                    day = sysDay + 1;
                }
            }
        }

        DatePickerDialog dpd = new DatePickerDialog(context, odsl, year, month,
                day);
        dpd.show();
    }

    /**
     * 显示日期对话框，可以传入任何日期
     *
     * @param context 上下文
     * @param date    日期
     * @param odsl    日期的設置的監聽
     * @version 1.0
     * @createTime 2013-12-27,下午2:34:16
     * @updateTime 2014-03-05,下午15:34:16
     * @createAuthor CodeApe
     * @updateAuthor wujianxing
     * @updateInfo (重構了一下方法)
     * @updateAuthor XinGo
     * @updateInfo (预约时间修改)
     */
    public static void showDatePickDialogAll(final Context context,
                                             String date, DatePickerDialog.OnDateSetListener odsl) {
        int year = 1970;
        int month = 0;
        int day = 1;

        if (TextUtils.isEmpty(date)) {
            Calendar calendar = Calendar.getInstance();
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            day = calendar.get(Calendar.DAY_OF_MONTH);
        } else {
            String[] dates = date.split("-");
            if (dates.length != 3) { // 如果传入日期格式不合法，将默认显示当前日期
                Calendar calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);
            } else {
                year = Integer.parseInt(dates[0]);
                month = Integer.parseInt(dates[1]) - 1;
                day = Integer.parseInt(dates[2]);
            }
        }

        DatePickerDialog dpd = new DatePickerDialog(context, odsl, year, month,
                day);
        dpd.show();
    }

    /**
     * 显示选择时间控件
     *
     * @param context  上下文
     * @param time     时间 hh：mm
     * @param callBack 回调接口
     * @version 1.0
     * @createTime 2014年4月16日, 下午5:44:04
     * @updateTime 2014年4月16日, 下午5:44:04
     * @createAuthor CodeApe
     * @updateAuthor CodeApe
     * @updateInfo (此处输入修改内容, 若无修改可不写.)
     */
    public static void showTimePickDialog(final Context context, String time,
                                          OnTimeSetListener callBack) {

        Calendar cal = Calendar.getInstance();
        int hourOfDay = cal.get(Calendar.HOUR_OF_DAY); // 系统时间
        int minute = cal.get(Calendar.MINUTE);// 系统分钟

        if (!TextUtils.isEmpty(time)) {
            String[] times = time.split(":");
            if (times.length != 2) { // 如果传入时间格式不合法，将默认显示当前日期

                hourOfDay = Integer.parseInt(times[0]);
                minute = Integer.parseInt(times[1]);
            }
        }

        TimePickerDialog tpd = new TimePickerDialog(context, callBack,
                hourOfDay, minute, true);
        tpd.show();
    }

    /**
     * 显示日期对话框
     *
     * @param context 上下文
     * @param otsl    時間的設置的監聽
     * @version 1.0
     * @createTime 2014-03-05,下午15:34:16
     * @updateTime 2014-03-05,下午15:34:16
     * @createAuthor wujianxing
     * @updateAuthor wujianxing
     * @updateInfo (重構了一下方法)
     */
    public static void showTimePickDialog(Context context,
                                          TimePickerDialog.OnTimeSetListener otsl) {
        Calendar calendar = Calendar.getInstance();
        TimePickerDialog dialog = new TimePickerDialog(context, otsl,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE), true); // 是否为二十四制
        dialog.show();
    }

    /**
     * 显示选择车型
     *
     * @param context
     * @param v
     * @version 1.0
     * @createTime 2013-8-21,下午9:51:42
     * @updateTime 2013-8-21,下午9:51:42
     * @createAuthor 罗文忠
     * @updateAuthor 罗文忠
     * @updateInfo (此处输入修改内容, 若无修改可不写.)
     */
    public static void showSelectCarTypeDg(Context context, final View v) {

        final String[] str = {"BYD_S6", "BYD_G6", "BYD_S3", "BYD_F3",
                "BYD_F0", "BYD_F6", "BYD_L3", "BYD_M6", "无车", "其他"};

        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle("请选择车型");
        dialog.setItems(str, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                ((Button) v).setText(str[which]);
                dialog.dismiss();
            }
        });

        dialog.show();

    }



    @NonNull
    private static View.OnClickListener getOnClickInstallAPk(final UpdateBean bean, final Context context) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String apkPath = bean.getUrl();
                ApkUtil.installApk(new File(FileConfig.PATH_DOWNLOAD
                        + apkPath.substring(apkPath.lastIndexOf("/") + 1)));
            }
        };
    }


    /**
     * 显示选取图片对话框(自定义图库)
     *
     * @param context
     * @version 1.0
     * @createTime 2013-8-25,上午10:25:25
     * @updateTime 2013-8-25,上午10:25:25
     * @createAuthor 罗文忠
     * @updateAuthor 罗文忠
     * @updateInfo (此处输入修改内容, 若无修改可不写.)
     */
    public static void showSelectCustomImageDg(final Context context) {

        String[] items = context.getResources().getStringArray(
                R.array.select_image);

        CustomDialog dialog = new CustomDialog(context);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);

        dialog.setTitle(context.getString(R.string.dialog_select_image_title));

        dialog.setSingleSelectItems(items, new CustomDialog.OnClickListener() {

            @Override
            public void onClick(CustomDialog dialog, int id, Object object) {
                switch (id) {
                    case 0:
                        Uri uri = Uri
                                .fromFile(new File(FileConfig.PATH_IMAGE_TEMP));
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                        ((Activity) context).startActivityForResult(intent,
                                RequestCode.REQUEST_CODE_SELECT_PHOTOGRAPH);
                        break;
                    case 1:
                        // IntentUtil.gotoActivityForResult(context,
                        // PhotoAlbumActivity.class,
                        // RequestCode.REQUEST_CODE_SELECT_IMAGE);
                        break;

                    default:
                        break;
                }
                dialog.dismiss();
            }
        });

        dialog.show();

    }

    /**
     * 显示选取图片对话框(系统图库)
     *
     * @param context
     * @version 1.0
     * @createTime 2013-8-25,上午10:25:25
     * @updateTime 2013-8-25,上午10:25:25
     * @createAuthor 罗文忠
     * @updateAuthor 罗文忠
     * @updateInfo (此处输入修改内容, 若无修改可不写.)
     */
    public static void showSelectSystemImageDg(final Context context) {
        showSelectSystemImageDg(context, null);
    }


    public static void showSelectSystemImageDg(final Context context,
                                               final String photo_path) {
        String[] items = context.getResources().getStringArray(
                R.array.select_image);
        CustomDialog dialog = new CustomDialog(context);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setTitle(context.getString(R.string.dialog_select_image_title));
        dialog.setSingleSelectItems(items, new CustomDialog.OnClickListener() {
            @Override
            public void onClick(CustomDialog dialog, int id, Object object) {
                switch (id) {
                    case 0:
                        Uri uri = null;
                        if (photo_path == null) {
                            uri = Uri
                                    .fromFile(new File(FileConfig.PATH_IMAGE_TEMP));
                        } else {
                            uri = Uri.fromFile(new File(photo_path));
                        }
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                        ((Activity) context).startActivityForResult(intent,
                                RequestCode.REQUEST_CODE_SELECT_PHOTOGRAPH);
                        break;
                    case 1:
                        Intent getImage = new Intent(Intent.ACTION_GET_CONTENT);
                        getImage.addCategory(Intent.CATEGORY_OPENABLE);
                        getImage.setType("image/*");
                        ((Activity) context).startActivityForResult(
                                Intent.createChooser(getImage, "Select Picture"),
                                RequestCode.REQUEST_CODE_SELECT_LOCAL);
                        break;
                    default:
                        break;
                }
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    /**
     * 显示提示信息对话框
     *
     * @param context
     * @version 1.0
     * @createTime 2013-10-2,上午10:36:57
     * @updateTime 2013-10-2,上午10:36:57
     * @createAuthor 罗文忠
     * @updateAuthor 罗文忠
     * @updateInfo (此处输入修改内容, 若无修改可不写.)
     */
    public static void showMessageDg(Context context, String title, String msg,
                                     CustomDialog.OnClickListener listener) {
        CustomDialog dialog = new CustomDialog(context);
        if (!TextUtils.isEmpty(title)) {
            dialog.setTitle(title);
        } else {
            dialog.setTitle(context.getString(R.string.prompt));
        }
        dialog.setMessage(msg);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);

        dialog.setButton1(context.getString(R.string.enter), listener);

        dialog.setButton2(context.getString(R.string.cancel),
                new CustomDialog.OnClickListener() {
                    @Override
                    public void onClick(CustomDialog dialog, int id,
                                        Object object) {
                        dialog.dismiss();
                    }
                });
        dialog.show();
    }


    /**
     * 显示提示信息对话框
     *
     * @param context
     * @version 1.0
     * @createTime 2013-10-2,上午10:36:57
     * @updateTime 2013-10-2,上午10:36:57
     * @createAuthor 罗文忠
     * @updateAuthor 罗文忠
     * @updateInfo (此处输入修改内容, 若无修改可不写.)
     */
    public static void showMessageDg(Context context, String title, String msg) {
        CustomDialog dialog = new CustomDialog(context);
        if (!TextUtils.isEmpty(title)) {
            dialog.setTitle(title);
        } else {
            dialog.setTitle(context.getString(R.string.prompt));
        }
        dialog.setMessage(msg);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);

        dialog.setButton1("知道了",
                new CustomDialog.OnClickListener() {
                    @Override
                    public void onClick(CustomDialog dialog, int id,
                                        Object object) {
                        dialog.dismiss();
                    }
                });
        dialog.show();
    }

    /**
     * 显示提示信息对话框
     *
     * @param context
     * @version 1.0
     * @createTime 2013-10-2,上午10:36:57
     * @updateTime 2013-10-2,上午10:36:57
     * @createAuthor 罗文忠
     * @updateAuthor 罗文忠
     * @updateInfo (此处输入修改内容, 若无修改可不写.)
     */
    public static void showMessageDgBtnNoBg(Context context,
                                            String title, String titleTextColor, int titleTextSize,
                                            String msg, String msgTextColor, int msgTextSize,
                                            String confirmStr, String confirmBtnTextColor, int confirmTextSize, boolean isCancelable,
                                            final CustomDialog.OnClickListener listener) {
        CustomDialog dialog = new CustomDialog(context);
        if (!TextUtils.isEmpty(title)) {
            dialog.setTitle(title);
        } else {
            dialog.setTitle(context.getString(R.string.prompt));
        }
        dialog.setTitleSizeAndColor(titleTextColor, titleTextSize);
        dialog.setMessage(msg);
        dialog.setMsgSizeAndColor(msgTextColor, msgTextSize);
        dialog.getMsgView().setGravity(Gravity.CENTER);
        dialog.setCancelable(isCancelable);
        dialog.setCanceledOnTouchOutside(isCancelable);
        dialog.hideTitleBetweenMsgLine();
        dialog.showMsgBetweenBtnLine();

        Button confirmBtn = dialog.setButton1(confirmStr, new CustomDialog.OnClickListener() {
            @Override
            public void onClick(CustomDialog dialog, int id, Object object) {
                listener.onClick(dialog, id, object);
                dialog.dismiss();
                isDialogShow = false;
            }
        });
        confirmBtn.setTextColor(Color.parseColor(confirmBtnTextColor));
        confirmBtn.setTextSize(TypedValue.COMPLEX_UNIT_PX, confirmTextSize);
        confirmBtn.setBackgroundColor(Color.TRANSPARENT);
        LinearLayout.LayoutParams llLp = (LinearLayout.LayoutParams) confirmBtn.getLayoutParams();
        llLp.setMargins(0, 0, 0, 0);
        confirmBtn.setLayoutParams(llLp);

        ViewGroup.MarginLayoutParams View_Buttons_llLp = (ViewGroup.MarginLayoutParams) dialog.getView_Buttons().getLayoutParams();
        View_Buttons_llLp.setMargins(0, 0, 0, 0);
        dialog.getView_Buttons().setLayoutParams(View_Buttons_llLp);

        dialog.getView_Content().setPadding(
                0,
                dialog.getView_Content().getPaddingTop(),
                0,
                0
        );

        dialog.show();
        isDialogShow = true;
    }

    public static void showMessageDgBtnNoBg(Context c,
                                            String title,
                                            String msg,
                                            String confirmStr,
                                            final CustomDialog.OnClickListener listener) {
        DialogUtil.showMessageDgBtnNoBg(c,
                title
                , "#333333", getDimen(c, R.dimen.common_text_size_title_16sp),
                msg
                , "#666666", getDimen(c, R.dimen.common_text_size_btn_14sp),
                confirmStr
                , "#389bff", getDimen(c, R.dimen.common_text_size_title_16sp),
                true,
                listener);
    }

    //弹出不可取消对话框
    public static void showMessageDgBtnNoBgNoCancel(Context c,
                                                    String title,
                                                    String msg,
                                                    String confirmStr,
                                                    final CustomDialog.OnClickListener listener) {
        DialogUtil.showMessageDgBtnNoBg(c,
                title
                , "#333333", getDimen(c, R.dimen.common_text_size_title_16sp),
                msg
                , "#515151", getDimen(c, R.dimen.common_text_size_btn_14sp),
                confirmStr
                , "#0078ff", getDimen(c, R.dimen.common_text_size_btn_14sp),
                false,
                listener);
    }

    public static int getDimen(Context c, int dimenId) {
        return c.getResources().getDimensionPixelSize(dimenId);
    }



    /**
     * 显示提示信息对话框
     *
     * @param context
     * @version 1.0
     * @createTime 2013-10-2,上午10:36:57
     * @updateTime 2013-10-2,上午10:36:57
     * @createAuthor 罗文忠
     * @updateAuthor 罗文忠
     * @updateInfo (此处输入修改内容, 若无修改可不写.)
     */
    public static void showSingleMessageDg(Context context, String title,
                                           String msg, CustomDialog.OnClickListener listener) {
        CustomDialog dialog = new CustomDialog(context);
        if (!TextUtils.isEmpty(title)) {
            dialog.setTitle(title);
        } else {
            dialog.setTitle(context.getString(R.string.prompt));
        }
        dialog.setMessage(msg);
        dialog.setButton3(context.getString(R.string.enter), listener);
        dialog.show();
    }

    /**
     * 显示提示信息对话框
     *
     * @param context
     * @version 1.0
     * @createTime 2013-10-2,上午10:36:57
     * @updateTime 2013-10-2,上午10:36:57
     * @createAuthor 罗文忠
     * @updateAuthor 罗文忠
     * @updateInfo (此处输入修改内容, 若无修改可不写.)
     */
    public static void showSingleMessageDg(Context context, String title,
                                           Spanned msg, String btn_text, CustomDialog.OnClickListener listener) {


        CustomDialog dialog = new CustomDialog(context);
        if (!TextUtils.isEmpty(title)) {
            dialog.setTitle(title);
        } else {
            dialog.setTitle(context.getString(R.string.prompt));
        }
        dialog.setMessage(msg);
        dialog.setButton3(btn_text, listener);
        dialog.show();
    }

    /**
     * 描述：无标题 提示信息对话框
     *
     * @version 1.0
     * @createTime 2014-4-23 下午1:07:16
     * @updateTime 2014-4-23 下午1:07:16
     * @createAuthor XinGo
     * @updateAuthor
     * @updateInfo (修改内容描述)
     */
    public static void showSingleMessageDg(Context context, String msg,
                                           CustomDialog.OnClickListener listener) {
        CustomDialog dialog = new CustomDialog(context);
        dialog.setMessage(msg);
        dialog.setButton3(context.getString(R.string.enter), listener);
        dialog.show();
    }


    /**
     * 显示退出对话框
     *
     * @param context
     * @version 1.0
     * @createTime 2013-10-2,上午10:36:57
     * @updateTime 2013-10-2,上午10:36:57
     * @createAuthor 罗文忠
     * @updateAuthor 罗文忠
     * @updateInfo (此处输入修改内容, 若无修改可不写.)
     */
    public static void showExitsDg(final Context context, final boolean quitActivity) {

        HorizontalCustomDialog dialog = new HorizontalCustomDialog.Builder(
                context).setTitle("退出登录").setMessage("立即退出您的帐号？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        dialog.dismiss();
                        String userNo = "";
                        if (TApplication.getUserInfoBean() != null) {
                            userNo = TApplication.getUserInfoBean().getUserNo();
                        }
                        if (quitActivity) {
                            ((Activity) context).finish();
                        }
                        //退出置空
                        TApplication.setSecretKey("");
                        logout(context);
                        //友盟、百度统计埋点--退出登录
                        CountUtil.sendDataForStatistics(context, "MyPage_Set_logout");
                        //C2B 统计
                        CountUtil.sendC2bLoginOut(context);

                        UserInfoUtil.logout();
                        TApplication.setUserInfoBean(null);
//                        TApplication.setUserAccountBean(null);
                        //						 TApplication.token=null;
                        //						 TApplication.clearToken();
                        TApplication.clearToken();
                        TApplication.IsOnTop = true;

//                        Intent intent = new Intent();
//                        intent.setAction(BroadcastFilters.ACTION_LGOIN_LOGOUT);
                        //TApplication.context.sendBroadcast(intent);
//                        Util.sendBroadcast(context, intent);

//                        EventBus.getDefault().post(new LoginEvent(false, userNo));
//                        EventBus.getDefault().post(new UpdateHomeEvent());
//                        EventBus.getDefault().post(new MainMenuEvent(300));


                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub

                        dialog.dismiss();
                    }
                }).setPositiveBgId(R.drawable.shape_dia_right_gray).setPositiveTextColor(R.color.title_bg_gray).setNegativeBgId(R.drawable.shape_dia_left_red).setNegativeTextColor(R.color.white).create();
        dialog.show();

    }

    private static void logout(Context context) {
    }


    /**
     * 显示退出对话框
     *
     * @param context
     * @version 1.0
     * @createTime 2013-10-2,上午10:36:57
     * @updateTime 2013-10-2,上午10:36:57
     * @createAuthor 罗文忠
     * @updateAuthor 罗文忠
     * @updateInfo (此处输入修改内容, 若无修改可不写.)
     */
    public static void showExitsDg(final Context context) {
        showExitsDg(context, true);

    }



    /**
     * 显示单选对话框
     *
     * @param context 上下文
     * @param button  目标按钮
     * @param items   选项数组
     * @version 1.0
     * @createTime 2013-10-10,下午10:10:23
     * @updateTime 2013-10-10,下午10:10:23
     * @createAuthor 罗文忠
     * @updateAuthor 罗文忠
     * @updateInfo (此处输入修改内容, 若无修改可不写.)
     */
    public static void showSingleSelectDg(Context context, final Button button,
                                          final String[] items) {
        CustomDialog dialog = new CustomDialog(context);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);

        int checkedItem = -1;
        for (int i = 0; i < items.length; i++) {
            if (items[i].equals(button.getText().toString().trim())) {
                checkedItem = i;
                break;
            }
        }
        dialog.setSingleSelectItems(items, checkedItem,
                new CustomDialog.OnClickListener() {

                    @Override
                    public void onClick(CustomDialog dialog, int id,
                                        Object object) {

                        button.setText(items[id]);
                        dialog.dismiss();

                    }
                });

        dialog.show();
    }

    /**
     * 显示单选对话框
     *
     * @param context  上下文
     * @param title    对话框标题
     * @param items    选项组合
     * @param listener 单击监听
     * @version 1.0
     * @createTime 2013-10-12,上午11:11:36
     * @updateTime 2013-10-12,上午11:11:36
     * @createAuthor 罗文忠
     * @updateAuthor 罗文忠
     * @updateInfo (此处输入修改内容, 若无修改可不写.)
     */
    public static void showSingleSelectDg(Context context, String title,
                                          String check, final String[] items,
                                          CustomDialog.OnClickListener listener) {
        CustomDialog dialog = new CustomDialog(context);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);

        int checkedItem = -1;
        for (int i = 0; i < items.length; i++) {
            if (items[i].equals(check)) {
                checkedItem = i;
                break;
            }
        }
        dialog.setTitle(title);
        dialog.setSingleSelectItems(items, checkedItem, listener);

        dialog.show();
    }

    /**
     * 显示单选对话框
     *
     * @param context  上下文
     * @param items    选项组合
     * @param listener 单击监听
     * @version 1.0
     * @createTime 2013-10-12,上午11:11:36
     * @updateTime 2013-10-12,上午11:11:36
     * @createAuthor 罗文忠
     * @updateAuthor 罗文忠
     * @updateInfo (此处输入修改内容, 若无修改可不写.)
     */
    public static void showSingleSelectDg(Context context, String check,
                                          final String[] items, CustomDialog.OnClickListener listener) {
        CustomDialog dialog = new CustomDialog(context);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);

        int checkedItem = -1;
        for (int i = 0; i < items.length; i++) {
            if (items[i].equals(check)) {
                checkedItem = i;
                break;
            }
        }
        dialog.setSingleSelectItems(items, checkedItem, listener);

        dialog.show();
    }

    /**
     * 显示单选对话框
     *
     * @param context  上下文
     *                 选中项
     * @param items    选项组合
     * @param listener 单击监听
     * @version 1.0
     * @createTime 2013-10-12,上午11:11:36
     * @updateTime 2013-10-12,上午11:11:36
     * @createAuthor 罗文忠
     * @updateAuthor 罗文忠
     * @updateInfo (此处输入修改内容, 若无修改可不写.)
     */
    public static void showSingleSelectDg(Context context, String check,
                                          final ArrayList<String> items, CustomDialog.OnClickListener listener) {
        CustomDialog dialog = new CustomDialog(context);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);

        int checkedItem = -1;
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).equals(check)) {
                checkedItem = i;
                break;
            }
        }
        dialog.setSingleSelectItems(items, checkedItem, listener);

        dialog.show();
    }

    /**
     * 显示多项操作对话框
     *
     * @param context
     * @param items
     * @param listener
     * @version 1.0
     * @createTime 2013-10-11,下午2:54:11
     * @updateTime 2013-10-11,下午2:54:11
     * @createAuthor 罗文忠
     * @updateAuthor 罗文忠
     * @updateInfo (此处输入修改内容, 若无修改可不写.)
     */
    public static void showMultiOperDg(Context context, final String[] items,
                                       CustomDialog.OnClickListener listener) {
        CustomDialog dialog = new CustomDialog(context);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setTitle(context.getString(R.string.hint_select_oper));
        dialog.setSingleSelectItems(items, listener);
        dialog.show();
    }

    /**
     * 描述：输入法的控制
     * <p>
     * createTime 2014-3-17 下午4:57:33 createAuthor kpxingxing
     * <p>
     * updateTime 2014-3-17 下午4:57:33 updateAuthor kpxingxing updateInfo
     *
     * @param view
     * @param show
     */
    public static void showInput(View view, boolean show) {
        try {
            Context context = TApplication.context;
            if (show) {
                InputMethodManager imm = (InputMethodManager) context
                        .getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(view, 0);
            } else {
                InputMethodManager imm = (InputMethodManager) context
                        .getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 联系客服
     *
     * @param context
     */
    public static void contactCustomerServiceDg(final Context context) {

        HorizontalCustomDialog dialog = new HorizontalCustomDialog.Builder(
                context).setTitle("是否呼叫").setMessage("400-809-8888")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {

                    @SuppressLint("MissingPermission")
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Intent.ACTION_CALL, Uri
                                .parse("tel:4008098888"));
                        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            return;
                        }
                        context.startActivity(intent);
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                }).setPositiveBgId(R.drawable.shape_dia_right_gray).setPositiveTextColor(R.color.title_bg_gray).setNegativeBgId(R.drawable.shape_dia_left_red).setNegativeTextColor(R.color.white).create();
        dialog.show();
    }

    private static final int CALL_PHONE_REQUEST_CODE = 9;

    public static void contactCustomerServiceDg(final Context context, String title, final String num) {

        HorizontalCustomDialog dialog = new HorizontalCustomDialog.Builder(
                context).setTitle(title).setMessage(num)
                .setPositiveButton("呼叫", new DialogInterface.OnClickListener() {

                    @SuppressLint("MissingPermission")
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Intent.ACTION_CALL, Uri
                                .parse("tel:" + num));
                        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.

                            return;
                        }
                        //
                        //						if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context,
                        //								Manifest.permission.CALL_PHONE)) {
                        //							// 返回值：
                        ////                          如果app之前请求过该权限,被用户拒绝, 这个方法就会返回true.
                        ////                          如果用户之前拒绝权限的时候勾选了对话框中”Don’t ask again”的选项,那么这个方法会返回false.
                        ////                          如果设备策略禁止应用拥有这条权限, 这个方法也返回false.
                        //							// 弹窗需要解释为何需要该权限，再次请求授权
                        //
                        //							// 帮跳转到该应用的设置界面，让用户手动授权
                        //							Intent settingIntent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        //							Uri uri = Uri.fromParts("package", getPackageName(), null);
                        //							intent.setData(uri);
                        //							context.startActivity(settingIntent);
                        //						}else{
                        //							// 不需要解释为何需要该权限，直接请求授权
                        //
                        //							ActivityCompat.requestPermissions((Activity) context,
                        //									new String[]{Manifest.permission.CALL_PHONE},
                        //									CALL_PHONE_REQUEST_CODE);
                        //						}

                        context.startActivity(intent);
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                }).create();
        dialog.show();
    }

    //	public static void showGpsDialog(final Context context, String title, final String num) {
    //
    //		HorizontalCustomDialog dialog = new HorizontalCustomDialog.Builder(
    //				context).setTitle(title).setMessage(num)
    //				.setPositiveButton("设置", new DialogInterface.OnClickListener() {
    //
    //					@Override
    //					public void onClick(DialogInterface dialog, int which) {
    //						// 转到手机设置界面，用户设置GPS
    //						Intent intent = new Intent(
    //								Settings.ACTION_LOCATION_SOURCE_SETTINGS);
    //						((Activity)context).startActivityForResult(intent, 0); // 设置完成后返回到原来的界面
    //						dialog.dismiss();
    //					}
    //				})
    //				.setNegativeButton("取消", new DialogInterface.OnClickListener() {
    //					@Override
    //					public void onClick(DialogInterface dialog, int which) {
    //
    //						dialog.dismiss();
    //					}
    //				}).create();
    //		dialog.show();
    //	}


    /**
     * 点击实名认证----实名认证失败
     *
     * @param context
     * @param identity_type 认证类型（0国内1外籍）
     */
//    public static void authFailureDg(final Context context,
//                                     final int identity_type) {
//        CustomDialog dialog = new CustomDialog(context);
//        dialog.setTitle(context.getString(R.string.prompt));
//        dialog.setMessage(context.getString(R.string.warning_auth_failure));
//        dialog.setEnterBtn(new CustomDialog.OnClickListener() {
//            @Override
//            public void onClick(CustomDialog dialog, int id, Object object) {
//                // 确认后跳转认证界面
//                Bundle bundle = new Bundle();
//                bundle.putInt("identity_type", identity_type);
//                IntentUtil.gotoActivity(context, RealDeemActiviy.class, bundle);
//                dialog.dismiss();
//            }
//        });
//        dialog.setCancelBtn(new CustomDialog.OnClickListener() {
//
//            @Override
//            public void onClick(CustomDialog dialog, int id, Object object) {
//                dialog.dismiss();
//            }
//
//        });
//        dialog.show();
//    }

    /**
     * 弹出提示
     *
     * @param hintText
     */
    public static void showDialogHint(Context context, String hintText) {
        ProgressDialog progressDialog = new ProgressDialog(context,
                R.style.NobackDialog);
        View view = View.inflate(context, R.layout.d_hint_text, null);
        TextView hint_tv = (TextView) view.findViewById(R.id.hint_tv);
        hint_tv.setText(hintText);

        WindowManager.LayoutParams params = progressDialog.getWindow()
                .getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        progressDialog.getWindow().setAttributes(params);
        progressDialog.setCancelable(true);
        progressDialog.show();
        progressDialog.setContentView(view);
    }

    /**
     * 通用的Dialog
     *
     * @return
     */
    public static Dialog showAwardDialog(Context context, String count_str) {


        final Dialog dialog = new Dialog(context, R.style.NobackDialog);
        View view = View.inflate(context, R.layout.award_dialog, null);
        view.findViewById(R.id.custom_dialog_btn_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.setContentView(view);
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.getWindow().setAttributes(params);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        return dialog;


    }


    public static void showEditDg(Context context, String title,
                                  CustomDialog.OnClickListener listener) {
        CustomDialog dialog = new CustomDialog(context);
        if (!TextUtils.isEmpty(title)) {
            dialog.setTitle(title);
        } else {
            dialog.setTitle(context.getString(R.string.prompt));
        }

        dialog.setEditInput("请输入其他银行", 1);
        //		dialog.setMessage(msg);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);

        dialog.setButton1(context.getString(R.string.enter), listener);

        dialog.setButton2(context.getString(R.string.cancel),
                new CustomDialog.OnClickListener() {
                    @Override
                    public void onClick(CustomDialog dialog, int id,
                                        Object object) {
                        dialog.dismiss();
                    }
                });
        dialog.show();
    }


}
