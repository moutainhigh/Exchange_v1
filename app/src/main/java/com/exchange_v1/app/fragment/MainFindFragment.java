package com.exchange_v1.app.fragment;

import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.exchange_v1.R;
import com.exchange_v1.app.base.BaseFragment;



/**
 * 主页的发现页面
 */
public class MainFindFragment extends BaseFragment implements OnClickListener {

    private static final int CARMERA_REQUEST_CODE = 1;

    @Override
    protected View getViews() {
        return View.inflate(context, R.layout.f_mainfind_activities, null);
    }


    @Override
    protected void findViews() {
    }


    @Override
    protected void initGetData() {

    }


    @Override
    protected void widgetListener() {
    }

    @Override
    public void onClick(View v) {

    }



    @Override
    protected void init() {
    }


    //targetSdkVersion如果是23以下，调用ActivityCompat.requestPermissions()，会弹出权限选择对话框，但是选择拒绝授权，
    // onRequestPermissionsResult中的返回值却是PERMISSION_GRANTED,但选择同意授权，会把应用关闭重新开启当前activity，而不会调用onRequestPermissionsResult中的方法，
    // 所以不要在targetSdkVersion设置为23以下，又把complierSdkversion设置为23，这样会出现上述的问题。最好的方式是把targetSdkVersion也设置为23，就可以解决。一切完美运行
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // NOTE: delegate the permission handling to generated method
        if (requestCode == CARMERA_REQUEST_CODE && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission Granted
//                scanReful();
            } else {
                // Permission Denied
                Toast.makeText(getActivity(), "需要开启权限才能使用照相机功能", Toast.LENGTH_SHORT).show();
            }
        }


    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }




}
