package com.exchange_v1.app.base;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.DrawableRes;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.exchange_v1.app.Manifest;
import com.exchange_v1.app.R;
import com.exchange_v1.app.config.BroadcastFilters;
import com.exchange_v1.app.utils.ViewUtils;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.EventBus;

import java.util.Date;


/**
 * 基类Fragment
 *
 */
public abstract class BaseFragment extends Fragment {

    protected BaseFragment thisF = this;
    protected Activity thisA = null;
    /**
     * Standard activity result: operation canceled.
     */
    protected final int RESULT_CANCELED = 0;
    /**
     * Standard activity result: operation succeeded.
     */
    protected final int RESULT_OK = -1;
    /**
     * Start of user-defined activity results.
     */
    protected final int RESULT_FIRST_USER = 1;
    protected Context context;
    protected Long mInTime;
    /**
     * 父视图
     */
    protected View view_Parent;
    /**
     * 广播接收器
     */
    protected BroadcastReceiver receiver;
    /**
     * 广播过滤器
     */
    protected IntentFilter filter;
    /**
     * 标题
     */

    Handler mHandler;

    protected String pageName = "BasePage";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        filter = new IntentFilter();
        context = getActivity();
        thisA = getActivity();
        getInputTime();
        mHandler = new Handler();
        registerReceiver();
        initGetData();
        super.onCreate(savedInstanceState);
    }

    protected boolean useEventBus() {
        // TODO Auto-generated method stub
        return false;
    }

    @SuppressWarnings("unchecked")
    public <T extends View> T findViewById(int resId) {
        return (T) view_Parent.findViewById(resId);
    }

    private boolean isViewCreated = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // if ((ViewGroup) view_Parent.getParent() != null) {
        // ((ViewGroup) view_Parent.getParent()).removeView(view_Parent);
        // }
        if (getContentViewId() > 0) {
            view_Parent = View.inflate(thisA, getContentViewId(), null);
        } else {
            view_Parent = getViews();
        }

        isViewCreated = true;
        return view_Parent;
    }

    @Override
    public void onDestroyView() {
        // TODO Auto-generated method stub
        if (useEventBus()) {
            EventBus.getDefault().unregister(this);
        }

        super.onDestroyView();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        context = getActivity();
        if (useEventBus()) {
            EventBus.getDefault().register(this);
        }
        if (!isLazyLoadMode() || getUserVisibleHint()) {
            doUIInit();
        }
        pageName = thisA.getClass().getSimpleName();
        super.onActivityCreated(savedInstanceState);
    }

    private void doUIInit() {
        findViews();
        widgetListener();
        init();

        isLoadLazyDone = true;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            doLazyLoad();
        }
    }

    private boolean isLoadLazyDone = false;

    /***
     * 保证只运行一次
     */
    protected void doLazyLoad() {
        if (!isViewCreated) {
            return;
        }
        if (isLoadLazyDone) {
            return;
        }
        if (isLazyLoadMode()) {
            doUIInit();
        }
    }

    /**
     * 获取布局
     */
    protected View getViews() {
        return null;
    }

    ;

    protected int getContentViewId() {
        return 0;
    }

    ;

    /**
     * 查询View
     */
    protected abstract void findViews();

    /**
     * 主要用来初始化本地数据
     */
    protected abstract void initGetData();

    /**
     * 设置监听
     */
    protected abstract void widgetListener();

    /**
     * 主要用来获取网络数据
     */
    protected abstract void init();

    private void setFilterActions() {
        // 添加广播过滤器，在此添加广播过滤器之后，所有的子类都将收到该广播
        //???
    }

    private void registerReceiver() {
        setFilterActions();
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                thisF.onReceive(context, intent);
            }
        };
        filter.addAction(BroadcastFilters.ACTION_FORGET_PWD_CLOSE);
        filter.addAction(BroadcastFilters.ACTION_UPDATE_USER_INFO);
        filter.addAction(BroadcastFilters.ACTION_UPDATE_LOGIN_USER);
        filter.addAction(BroadcastFilters.ACTION_ORDER);
        filter.addAction(BroadcastFilters.ACTION_ORDER_CANCLE);

        String permission = Manifest.permission.bwoilpermiss;
        getActivity().registerReceiver(receiver, filter, permission, mHandler);


    }
    public Long getInputTime() {
        Date date= new Date();
        mInTime = date.getTime()/1000L;
        return mInTime;
    }

    public Long getEndTime() {
        Date date= new Date();
        Long OutTime= date.getTime()/1000L;
        return OutTime;
    }

    public Long getNowTime() {
        return mInTime;
    }

    @Override
    public void onResume() {
        super.onResume();

        MobclickAgent.onPageStart(this.getClass().getName());
    }

    @Override
    public void onPause() {
        super.onPause();
        getEndTime();
        MobclickAgent.onPageEnd(this.getClass().getName());
    }

    @Override
    public void onDestroy() {
        getActivity().unregisterReceiver(receiver);

        if (useEventBus()) {
            EventBus.getDefault().unregister(this);
        }
        super.onDestroy();

    }

    /**
     * 广播监听回调
     *
     * @param context
     * @param intent
     */
    protected void onReceive(Context context, Intent intent) {

    }

    /**
     * 是否懒加载
     *
     * @return
     */
    public boolean isLazyLoadMode() {
        return false;
    }

    /**
     * @param id  View的Id
     * @param <V> 不用管
     * @return 直接返回泛型的View
     */
    public final <V extends View> V getView(int id) {
        if (view_Parent == null) {
            return null;
        }
        if (id <= 0) {
            return null;
        }
        try {
            return (V) ViewUtils.getView(view_Parent, id);
        } catch (ClassCastException ex) {
            System.err.println("Could not cast View to concrete class." + ex);
            throw ex;
        }
    }

    protected Drawable getDrawable(@DrawableRes int id) {
        Resources res = getResources();
        if (res == null) {
            res = TApplication.getRes();
        }
        return ResourcesCompat.getDrawable(res, id, null);

    }

    protected int getActionBarSize() {
        TypedValue typedValue = new TypedValue();
        int[] textSizeAttr = new int[]{R.attr.actionBarSize};
        int indexOfAttrTextSize = 0;
        TypedArray a = context.obtainStyledAttributes(typedValue.data, textSizeAttr);
        int actionBarSize = a.getDimensionPixelSize(indexOfAttrTextSize, -1);
        a.recycle();
        return actionBarSize;
    }

    //RN主入口
    protected String getMainComponentName() {
        return "c2bphone";
    }
    //RN页面标识
    public Bundle getRNBundle() {
        return null;
    }

}