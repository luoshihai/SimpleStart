package com.simplestart.fragment;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.simplestart.R;

/**
 * Author:lsh
 * Version: 1.0
 * Description:
 * Date: 2017/7/1
 */

public class FragmentE extends BaseFragment {
    @Override
    protected int getChildLayoutRes() {
        return R.layout.fragment_e;
    }

    @Override
    protected void initView(View childHeadView, RelativeLayout rlBaseheaderBack, RelativeLayout rlBaseheaderHeader, RelativeLayout rlBaseheaderRight, View childView, LinearLayout myStatusBar) {
            
    }
}
