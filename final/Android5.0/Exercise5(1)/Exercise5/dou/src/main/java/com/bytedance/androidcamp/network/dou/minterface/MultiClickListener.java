package com.bytedance.androidcamp.network.dou.minterface;

import android.view.View;
import android.view.ViewGroup;

public abstract class MultiClickListener implements View.OnClickListener{
    public static final int TIMEOUT = 400;
    private View view;
    private  long lastClickTime;
    private Runnable singleClickRunnable = new Runnable(){
        @Override
        public void run() {
                if(view!=null){
                    onSingleClick(view);
                    view = null;
                    lastClickTime=0;
                }
        }
    };
    @Override
    public void onClick(View v) {
        long now = System.currentTimeMillis();
        if (view != null)
            view.removeCallbacks(singleClickRunnable);
        if(now-lastClickTime<TIMEOUT&&view!=null){
            onDoubleClick(v);
            view = null;
            lastClickTime=0;
        }else{
            view = v;
            lastClickTime = 0;
        }
        lastClickTime = 0;
    }
    abstract  public void onSingleClick(View v);
    abstract  public void onDoubleClick(View v);
}
