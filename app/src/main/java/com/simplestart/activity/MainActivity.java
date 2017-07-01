package com.simplestart.activity;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.simplestart.R;
import com.simplestart.fragment.FragmentA;
import com.simplestart.fragment.FragmentB;
import com.simplestart.fragment.FragmentC;
import com.simplestart.fragment.FragmentD;
import com.simplestart.fragment.FragmentE;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.main_fragment_container)
    FrameLayout mMainFragmentContainer;
    @BindView(R.id.main_bottome_switcher_container)
    LinearLayout mMainBottomeSwitcherContainer;
    @BindView(R.id.fl_a)
    FrameLayout mFlA;
    @BindView(R.id.fl_b)
    FrameLayout mFlB;
    @BindView(R.id.fl_c)
    FrameLayout mFlC;
    @BindView(R.id.fl_d)
    FrameLayout mFlD;
    @BindView(R.id.fl_e)
    FrameLayout mFlE;

    private FragmentA fg1;
    private FragmentB fg2;
    private FragmentC fg3;
    private FragmentD fg4;
    private FragmentE fg5;
    private FragmentManager fragmentManager;


    @Override
    protected int getChildLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView(RelativeLayout headView, RelativeLayout backBtn, RelativeLayout headerCenter, RelativeLayout headerRight, View childView, LinearLayout statubar) {
        fragmentManager = getSupportFragmentManager();
        mFlA.performClick();
        mMainFragmentContainer.setOnClickListener(this);
    }




    @OnClick({R.id.fl_a, R.id.fl_b, R.id.fl_c, R.id.fl_d, R.id.fl_e})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fl_a:
                setChioceItem(0);
                break;
            case R.id.fl_b:
                setChioceItem(1);
                break;
            case R.id.fl_c:
                setChioceItem(2);
                break;
            case R.id.fl_d:
                setChioceItem(3);
                break;
            case R.id.fl_e:
                setChioceItem(4);
                break;
        }
    }

    private void setChioceItem(int index) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        clearChioce(); // 清空, 重置选项, 隐藏所有Fragment
        hideFragments(fragmentTransaction);
        Toast.makeText(this, "index:" + index, Toast.LENGTH_SHORT).show();
        switch (index) {

            case 0:
                mFlA.getChildAt(0).setEnabled(false);
                mFlA.getChildAt(1).setEnabled(false);
                if (fg1 == null) {
                    fg1 = new FragmentA();
                    fragmentTransaction.add(R.id.main_fragment_container, fg1);
                } else {
                    // 如果不为空，则直接将它显示出来
                    fragmentTransaction.show(fg1);
                }
                break;
            case 1:
                mFlB.getChildAt(0).setEnabled(false);
                mFlB.getChildAt(1).setEnabled(false);
                if (fg2 == null) {
                    fg2 = new FragmentB();
                    fragmentTransaction.add(R.id.main_fragment_container, fg2);
                } else {
                    fragmentTransaction.show(fg2);
                }
                break;
            case 2:
                mFlC.getChildAt(0).setEnabled(false);
                mFlC.getChildAt(1).setEnabled(false);
                if (fg3 == null) {
                    fg3 = new FragmentC();
                    fragmentTransaction.add(R.id.main_fragment_container, fg3);
                } else {
                    fragmentTransaction.show(fg3);
                }
                break;
            case 3:
                mFlD.getChildAt(0).setEnabled(false);
                mFlD.getChildAt(1).setEnabled(false);
                if (fg4 == null) {
                    fg4 = new FragmentD();
                    fragmentTransaction.add(R.id.main_fragment_container, fg4);
                } else {
                    fragmentTransaction.show(fg4);
                }
                break;
            case 4:
                mFlE.getChildAt(0).setEnabled(false);
                mFlE.getChildAt(1).setEnabled(false);
                if (fg5 == null) {
                    fg5 = new FragmentE();
                    fragmentTransaction.add(R.id.main_fragment_container, fg5);
                } else {
                    fragmentTransaction.show(fg5);
                }
                break;
        }
        fragmentTransaction.commit(); // 提交
    }

    /**
     * 当选中其中一个选项卡时，其他选项卡重置为默认
     */
    private void clearChioce() {
        mFlA.getChildAt(0).setEnabled(true);
        mFlA.getChildAt(1).setEnabled(true);
        mFlB.getChildAt(0).setEnabled(true);
        mFlB.getChildAt(1).setEnabled(true);
        mFlC.getChildAt(0).setEnabled(true);
        mFlC.getChildAt(1).setEnabled(true);
        mFlD.getChildAt(0).setEnabled(true);
        mFlD.getChildAt(1).setEnabled(true);
        mFlE.getChildAt(1).setEnabled(true);
        mFlE.getChildAt(1).setEnabled(true);
    }

    private void hideFragments(FragmentTransaction fragmentTransaction) {
        if (fg1 != null) {
            fragmentTransaction.hide(fg1);
        }
        if (fg2 != null) {
            fragmentTransaction.hide(fg2);
        }
        if (fg3 != null) {
            fragmentTransaction.hide(fg3);
        }
        if (fg4 != null) {
            fragmentTransaction.hide(fg4);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitBy2Click(); //调用双击退出函数
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    /**
     * 双击退出函数
     */
    private static Boolean isExit = false;

    private void exitBy2Click() {
        Timer tExit = null;
        if (isExit == false) {
            isExit = true; // 准备退出
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false; // 取消退出
                }
            }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务

        } else {
            finish();
            System.exit(0);
        }
    }
}
