package com.simplestart.activity;

import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.simplestart.network.CallServer;
import com.simplestart.network.HttpListener;
import com.simplestart.network.HttpResponseListener;
import com.simplestart.utils.Toast;
import com.yanzhenjie.nohttp.rest.Request;

/**
 * Author:lsh
 * Version: 1.0
 * Description:
 * Date: 2017/7/1
 */

public abstract class BaseActivity extends AppCompatActivity{





    protected abstract void onViewInflated(View view);

    protected abstract int getChildLayout();


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
