package com.exchange_v1.app.fragment;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.exchange_v1.app.R;
import com.exchange_v1.app.adapter.OrderReceivingAdapter;
import com.exchange_v1.app.base.BaseFragment;
import com.exchange_v1.app.bean.OrderReceivingBean;
import com.exchange_v1.app.bean.ResponseBean;
import com.exchange_v1.app.biz.OrderBiz;
import com.exchange_v1.app.network.RequestHandle;
import com.exchange_v1.app.utils.StringUtil;
import com.exchange_v1.app.utils.ToastUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 接单中....
 */
public class HomeOrderReceivingFragment extends BaseFragment implements View.OnClickListener {

    private LinearLayout llView;
    private SmartRefreshLayout refreshLayout;
    private ListView lvListview;

    private OrderReceivingAdapter mAdapter;
    private List<OrderReceivingBean> receivingList;

    private int page = 1;
    private String limit="10";//每页条数
    private boolean isLoadMoreToast = false;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_home_order_receiving;
    }

    @Override
    protected void findViews() {
        llView = findViewById(R.id.ll_view);
        refreshLayout = (SmartRefreshLayout) findViewById(R.id.order_refresh_layout);
        lvListview = (ListView) findViewById(R.id.lv_listview);
    }

    @Override
    protected void initGetData() {

    }

    @Override
    protected void widgetListener() {
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page = 1;
                isLoadMoreToast = false;
                getCashingList(true, false);
            }
        });

        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                getCashingList(false, false);
            }
        });
    }

    private void getCashingList(final boolean isRefresh, boolean needDialog) {
        String processText = "";
        if (needDialog) {
            processText = getString(R.string.process_dialog_wait);
        }

        //没有分页和加装更多
        OrderBiz.GrabOrderList(context, new RequestHandle() {
            @Override
            public void onSuccess(ResponseBean result) {
                receivingList = (List<OrderReceivingBean>) result.getObject();

                if (null == receivingList || receivingList.size() < 10) {
                    refreshLayout.finishLoadMoreWithNoMoreData();
                    if (isLoadMoreToast) {
                        ToastUtil.showToast(context, "没有更多数据了");
                    }
                    isLoadMoreToast = true;
                } else {
                    refreshLayout.finishLoadMore();
                }

                if (isRefresh) {
                    refreshLayout.finishRefresh();
                    mAdapter.getList().clear();
                    mAdapter.getList().addAll(receivingList);
                    lvListview.setAdapter(mAdapter);
                } else {
                    refreshLayout.finishLoadMore();
                    mAdapter.getList().addAll(receivingList);
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFail(ResponseBean result) {
                ToastUtil.showToast(context,result.getInfo());
                refreshLayout.finishRefresh();
                refreshLayout.finishLoadMore();
            }
        });
    }

    @Override
    protected void init() {
        receivingList = new ArrayList<OrderReceivingBean>();
        mAdapter = new OrderReceivingAdapter(context, receivingList);
        lvListview.setAdapter(mAdapter);
        //获取数据
        refreshLayout.autoRefresh();
    }

    @Override
    public void onClick(View v) {

    }

//    @Override
//    protected void onReceive(Context context, Intent intent) {
//        super.onReceive(context, intent);
//        String order = intent.getStringExtra(FieldConfig.intent_str);
//        if (intent.getAction().equals(BroadcastFilters.ACTION_ORDER_ING)) {// 收到进行中的订单
//            Logger.i("进行中的订单号为："+order);
//            View viewContainer = LayoutInflater.from(thisA).inflate(R.layout.item_jpush_order_ing, null);
//            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DisplayUtil.dip2px(context, 60));
//            viewContainer.setLayoutParams(layoutParams);
//            //动态创建UI
//            TextView tvOrderId = viewContainer.findViewById(R.id.tv_order_id);
//            TextView affirm = viewContainer.findViewById(R.id.tv_affirm);
//            TextView cancle = viewContainer.findViewById(R.id.tv_cancle);
//            tvOrderId.setText(order);
//
//            affirm.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Logger.i("进行中订单确认："+order);
//                    //进行中确认
//                    grabOrdercComfirm(context, order, viewContainer);
//                }
//            });
//            cancle.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Logger.i("进行中订单取消："+order);
//
//                }
//            });
//
//            llView.addView(viewContainer);
//        }
//
//    }

    private void grabOrdercComfirm(Context context, String order, View childView) {
        if (!StringUtil.isEmpty(order)){
            OrderBiz.GrabOrderConfirm(context, order, new RequestHandle() {
                @Override
                public void onSuccess(ResponseBean result) {
                    llView.removeView(childView);
                    ToastUtil.showToast(context,"已确认");
                }

                @Override
                public void onFail(ResponseBean result) {
                    Integer status = result.getStatus();
                    ToastUtil.showToast(context,result.getInfo());
                }

            });
        }
    }


}
