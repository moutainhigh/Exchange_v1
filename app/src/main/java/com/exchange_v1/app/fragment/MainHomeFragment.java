package com.exchange_v1.app.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.exchange_v1.app.R;
import com.exchange_v1.app.base.BaseFragment;
import com.exchange_v1.app.base.TApplication;
import com.exchange_v1.app.bean.MineUserInfoBean;
import com.exchange_v1.app.bean.ResponseBean;
import com.exchange_v1.app.bean.WebBean;
import com.exchange_v1.app.biz.UserBiz;
import com.exchange_v1.app.config.BroadcastFilters;
import com.exchange_v1.app.network.RequestHandle;
import com.exchange_v1.app.utils.FieldConfig;
import com.exchange_v1.app.utils.JWebSocketClient;
import com.exchange_v1.app.utils.Logger;
import com.exchange_v1.app.utils.StringUtil;
import com.exchange_v1.app.utils.StringUtils;
import com.exchange_v1.app.utils.ToastUtil;
import com.flyco.tablayout.SlidingTabLayout;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

/**
 * 首页
 */
public class MainHomeFragment extends BaseFragment implements View.OnClickListener {
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private HomeVpAdapter mAdapter;
    private int currentItem = 0;

    private RadioButton radioButton;
    private RadioButton openGrabBtn;
    //是否开启自动抢单，默认为false
    private boolean autoGrab;
    //是否开启抢单
    private boolean openGrab;
    //是否在线
    private boolean onLine = true;

    private MineUserInfoBean userBean;
    private TextView tvBalance;
    private TextView tvFreeze;
    private JWebSocketClient client;
    //离线在线按钮
    private TextView tvOnline;
    private MediaPlayer player;

    @Override
    protected View getViews() {
        return View.inflate(context, R.layout.f_home, null);
    }

    @Override
    protected void findViews() {
        tvBalance = findViewById(R.id.tv_balance);
        tvFreeze = findViewById(R.id.tv_freeze);

        mFragments.add(new HomeOrderReceiveFragment());
        mFragments.add(new HomeOrderReceivingFragment());

        ViewPager vp = getView(R.id.vp);
        mAdapter = new HomeVpAdapter(getChildFragmentManager(), mFragments);
        vp.setOffscreenPageLimit(1);
        vp.setAdapter(mAdapter);
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        SlidingTabLayout tabLayout = getView(R.id.tl);
        tabLayout.setViewPager(vp);
        vp.setCurrentItem(currentItem);

        radioButton = findViewById(R.id.rb_1);
        openGrabBtn = findViewById(R.id.rb_2);
        tvOnline = findViewById(R.id.tv_online);
    }


    @Override
    public void initGetData() {
        //创建播放对象
        player = new MediaPlayer();
        getOnVoice();
    }


    @Override
    protected void widgetListener() {
        //开启抢单
        openGrabBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (openGrab) {
                    openGrabBtn.setChecked(false);
                    openGrab = false;
                    offReciver();
                } else {
                    MineUserInfoBean mineUserInfo = TApplication.getMineUserInfo();
                    if (mineUserInfo!=null){
                        String cityId = mineUserInfo.getCityId();
                        String provinceId = mineUserInfo.getProvinceId();
                        if (!StringUtil.isEmpty(cityId)&&!StringUtil.isEmpty(provinceId)){//地区设置不为空才能开启抢单
                            openGrabBtn.setChecked(true);
                            openGrab = true;
                            onReciver();
                        }else {
                            ToastUtil.showToast(context,"必须设置地区，才能开启抢单！");
                        }
                    }
                }
            }
        });

        radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (autoGrab) {
                    radioButton.setChecked(false);
                    autoGrab = false;
                    ToastUtil.showToast(context, "已关闭自动接单");
                } else {
                    MineUserInfoBean mineUserInfo = TApplication.getMineUserInfo();
                    if (mineUserInfo!=null){
                        String cityId = mineUserInfo.getCityId();
                        String provinceId = mineUserInfo.getProvinceId();
                        if (!StringUtil.isEmpty(cityId)&&!StringUtil.isEmpty(provinceId)){//地区设置不为空才能开启抢单
                            radioButton.setChecked(true);
                            autoGrab = true;
                            ToastUtil.showToast(context, "自动接单已经打开");
                        }else {
                            ToastUtil.showToast(context,"必须设置地区，才能开启抢单！");
                        }
                    }
                }

            }
        });

        tvOnline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onLine){
                    tvOnline.setText("离线");
                    onLine = false;
                }else {
                    tvOnline.setText("在线");
                    onLine = true;
                }

                //播放中的音乐，要关闭声音然后再播放
                closeMedia();
                //播放
                getOnVoice();
            }
        });
    }

    /**
     * 暂停正在播放的声音
     */
    private void closeMedia() {
        if (player != null) {
            if (player.isPlaying()) {
                player.stop();
            }
            player.release();
        }
    }

    private void getOnVoice() {
        AssetManager assetManager;

        assetManager = getResources().getAssets();
        try {
            AssetFileDescriptor fileDescriptor = null;
            if (onLine){
                fileDescriptor = assetManager.openFd("on.mp3");
            }else {
                fileDescriptor = assetManager.openFd("off.mp3");
            }
            player.setDataSource(fileDescriptor.getFileDescriptor(), fileDescriptor.getStartOffset(), fileDescriptor.getStartOffset());
            player.prepare();
            player.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //打开接单
    private void onReciver() {
        UserBiz.onReceptive(context, new RequestHandle() {
            @Override
            public void onSuccess(ResponseBean result) {
                ToastUtil.showToast(context, "接单已经打开");
            }

            @Override
            public void onFail(ResponseBean result) {
                ToastUtil.showToast(context, result.getInfo());
            }
        });
    }

    /**
     * 关闭接单
     *
     */
    private void offReciver() {
        UserBiz.offReceptive(context, new RequestHandle() {
            @Override
            public void onSuccess(ResponseBean result) {
                ToastUtil.showToast(context, "已关闭接单");
            }

            @Override
            public void onFail(ResponseBean result) {
                ToastUtil.showToast(context, result.getInfo());
            }
        });
    }

    /**
     * 断开websocket连接
     */
    private void closeConnect(JWebSocketClient client) {
        try {
            if (null != client) {
                client.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            client = null;
        }
    }

    private static final long HEART_BEAT_RATE = 300 * 1000;//每隔五分钟 进行一次对长连接的心跳检测
    private Handler mHandler = new Handler();
    private Runnable heartBeatRunnable = new Runnable() {
        @Override
        public void run() {
            if (client != null) {
                if (client.isClosed()) {
                    reconnectWs();
                }else {
                    if (client.isOpen()){//开启的状态下，发送心跳包
                        //发送心跳包
                        client.send("{command:99}");
                        Log.i("JWebSocketClient", "发送心跳包");
                    }
                }

            } else {
                //如果client已为空，重新初始化websocket
                initSocketClient();
                //这里不需要再开启心跳检测了

            }
            //定时对长连接进行心跳检测
            mHandler.postDelayed(this, HEART_BEAT_RATE);
        }
    };

    /**
     * 初始化websocket
     */
    private void initSocketClient() {
        String token = TApplication.getToken();
        String webUrl = "ws://ws.meilishenghuo.cn/ws?info="+token;

        URI uri = URI.create(webUrl);
        client = new JWebSocketClient(uri) {
            @Override
            public void onMessage(String message) {
                //message就是接收到的消息
                Log.e("JWebSClientService", message);
                if (!StringUtils.isEmpty(message)){
                    WebBean webBean = JSON.parseObject(message, WebBean.class);

                    String orderId = webBean.getData().getOrderId();
                    if (webBean!=null&&webBean.getCommand() == 100&&!StringUtils.isEmpty(orderId)){
                        Log.i("JWebSClientService", "收到command 100 单号为："+orderId);
                        //发送广播给前台
                        Intent intent = new Intent();
                        intent.setAction(BroadcastFilters.ACTION_ORDER);
                        intent.putExtra(FieldConfig.intent_str,orderId);
                        intent.putExtra(FieldConfig.intent_str2, autoGrab);
                        context.sendBroadcast(intent);
                    }else if (webBean!=null&&webBean.getCommand() == 101&&!StringUtils.isEmpty(orderId)){
                        Log.i("JWebSClientService", "收到command 101 被抢走的单号为："+orderId);
                        //发送广播给前台
                        Intent intent = new Intent();
                        intent.setAction(BroadcastFilters.ACTION_ORDER_CANCLE);
                        intent.putExtra(FieldConfig.intent_str,orderId);
                        intent.putExtra(FieldConfig.intent_str2, autoGrab);
                        context.sendBroadcast(intent);
                    }
                }

            }
        };

        //连接时可以使用connect()方法或connectBlocking()方法，建议使用connectBlocking()方法，connectBlocking多出一个等待操作，会先连接再发送。
        try {
            client.connectBlocking();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 开启重连
     */
    private void reconnectWs() {
        mHandler.removeCallbacks(heartBeatRunnable);
        new Thread() {
            @Override
            public void run() {
                try {
                    //重连
                    client.reconnectBlocking();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }


    @Override
    protected void init() {
        setUIView(TApplication.getMineUserInfo());
        //开启websocket,接单
        initSocketClient();
        //开启心跳检测
        mHandler.postDelayed(heartBeatRunnable, HEART_BEAT_RATE);

    }

    private void setUIView(MineUserInfoBean userBean) {
        if (userBean != null) {
            //设置用户信息
            tvBalance.setText(userBean.getBalance() + "");
            tvFreeze.setText(userBean.getFreezeBalance() + "");
        }
    }

    @Override
    public void onClick(View v) {

    }


    @Override
    protected void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (intent.getAction().equals(BroadcastFilters.ACTION_UPDATE_USER_INFO)) {
            Logger.i("首页收到了广播");
            setUIView(TApplication.getMineUserInfo());
        }
    }


    public class HomeVpAdapter extends FragmentStatePagerAdapter {

        private ArrayList<Fragment> mFragments;
        private final String[] mTitles = {"接单", "进行中"};

        public HomeVpAdapter(FragmentManager childFragmentManager, ArrayList<Fragment> list) {
            super(childFragmentManager);
            this.mFragments = list;
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

    }
}
