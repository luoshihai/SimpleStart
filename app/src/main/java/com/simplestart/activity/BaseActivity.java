package com.simplestart.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.simplestart.R;
import com.luoshihai.xxdialog.XXDialog;
import com.simplestart.network.CallServer;
import com.simplestart.network.HttpListener;
import com.simplestart.network.HttpResponseListener;
import com.simplestart.utils.Toast;
import com.simplestart.utils.viewutils.ViewUtils;
import com.yanzhenjie.nohttp.rest.Request;

import butterknife.ButterKnife;

/**
 * Author:lsh
 * Version: 1.0
 * Description:
 * Date: 2017/7/1
 */

public abstract class BaseActivity extends AppCompatActivity{


    private LinearLayout llStatubar;
    private RelativeLayout headView;
    private FrameLayout flBaseFrag;

    private RelativeLayout rlBaseheaderBack;
    private RelativeLayout rlBaseheaderHeader;
    private RelativeLayout rlBaseheaderRight;
    private XXDialog mXxDialog;
    //public BaseHandlerOperate mHander = BaseHandlerOperate.getBaseHandlerOperate();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.head_base_activity);
        llStatubar = (LinearLayout) findViewById(R.id.ll_statubar);
        headView = (RelativeLayout) findViewById(R.id.rl_head);
        headView.setBackgroundResource(R.color.blue3);
        rlBaseheaderBack = (RelativeLayout) findViewById(R.id.rl_baseheader_back);
        rlBaseheaderHeader = (RelativeLayout) findViewById(R.id.rl_baseheader_header);
        rlBaseheaderRight = (RelativeLayout) findViewById(R.id.rl_baseheader_right);
        flBaseFrag = (FrameLayout) findViewById(R.id.fl_baseFrag);
        View childView = View.inflate(this, getChildLayoutRes(), flBaseFrag);
        //给出抽象方法留给子类实现界面初始化
        rlBaseheaderRight.getChildAt(0).setVisibility(View.GONE);
        ButterKnife.bind(this);
        initView(headView, rlBaseheaderBack, rlBaseheaderHeader, rlBaseheaderRight, childView, llStatubar);
        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        };
        rlBaseheaderBack.setOnClickListener(clickListener);
        setStatusBar();
    }


    /**
     * 动态设置自定义状态栏的高度
     */
    private void setStatusBar() {
        //先把状态栏设置成透明
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //测量状态栏高度
        int statusBarHeight = ViewUtils.getStatusBarHeight(this);
        // 获取当前Statubar的布局参数
        LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) llStatubar.getLayoutParams();
        // 当控件的高强设置成真正庄状态栏的高度
        linearParams.height = statusBarHeight;
        // 使设置好的布局参数应用到控件myGrid
        llStatubar.setLayoutParams(linearParams);

        //设置状态栏为亮色模式
        ViewUtils.StatusBarLightMode(this, true);
        //设置状态栏的底色为纯白色
        llStatubar.setBackgroundColor(getResources().getColor(R.color.blue3));
    }

    /**
     * 获取实现类的布局资源
     *
     * @return
     */
    protected abstract int getChildLayoutRes();

    /**
     * 实现类需要把自己的具体布局填充到FrameLayout占位容器中
     *
     * @param childView
     */
    protected abstract void initView(RelativeLayout headView, RelativeLayout backBtn, RelativeLayout headerCenter
            , RelativeLayout headerRight, View childView, LinearLayout statubar);


    @Override
    protected void onResume() {
        /**
         * 设置为横屏
         */
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        super.onResume();
    }

    /**
     * 打开Activity，不finish
     *
     * @param clazz
     */
    protected void readyGo(Class<?> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

    /**
     * 打开Activity，可以传递参数，不finish
     *
     * @param clazz
     * @param bundle
     */
    protected void readyGo(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * 打开Activity，并finish掉
     *
     * @param clazz
     */
    protected void readyGoThenKill(Class<?> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
        finish();
    }




    public void showToast(String msg) {
        Toast.show(this,msg);
    }

    public void showToast(int msg) {
        Toast.show(this,msg +"");
    }

    //-------------- NoHttp -----------//

    /**
     * 用来标记取消。
     */
    private Object object = new Object();


    /**
     * 发起请求。
     *
     * @param what      what.
     * @param request   请求对象。
     * @param callback  回调函数。
     * @param canCancel 是否能被用户取消。
     * @param isLoading 实现显示加载框。
     * @param <T>       想请求到的数据类型。
     */
    public <T> void request(int what, Request<T> request, HttpListener<T> callback,
                            boolean canCancel, boolean isLoading) {
        request.setCancelSign(object);
//        mQueue.add(what, request, new HttpResponseListener<>(getActivity(), request, callback, canCancel, isLoading));
        CallServer.getInstance().request(what, request, new HttpResponseListener<>(this, request, callback, canCancel, isLoading));
    }


    @Override
    public void onDestroy() {
        // 和声明周期绑定，退出时取消这个队列中的所有请求，当然可以在你想取消的时候取消也可以，不一定和声明周期绑定。
        CallServer.getInstance().mRequestQueue.cancelBySign(object);

//        // 因为回调函数持有了activity，所以退出activity时请停止队列。
//            CallServer.getInstance().mRequestQueue.stop();

        super.onDestroy();
    }

    protected void cancelAll() {
        CallServer.getInstance().mRequestQueue.cancelAll();
    }

    protected void cancelBySign(Object object) {
        CallServer.getInstance().mRequestQueue.cancelBySign(object);
    }
}
