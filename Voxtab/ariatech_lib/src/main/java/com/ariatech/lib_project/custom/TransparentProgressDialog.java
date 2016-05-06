package com.ariatech.lib_project.custom;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ariatech.lib_project.R;

/**
 * Created by Abhijeet on 6/5/2015.
 */
public class TransparentProgressDialog extends Dialog {

    private ImageView iv;


    public TransparentProgressDialog(Context context) {
//        super(context, R.style.TransparentProgressDialog);
       /* WindowManager.LayoutParams wlmp = getWindow().getAttributes();
        wlmp.gravity = Gravity.CENTER_HORIZONTAL;
        getWindow().setAttributes(wlmp);
        setTitle(null);
        setCancelable(false);
        setOnCancelListener(null);
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        iv = new ImageView(context);
        iv.setImageResource(resourceIdOfImage);
        layout.addView(iv, params);
        addContentView(layout, params);*/

        this(context, R.drawable.img1, R.drawable.img2, R.drawable.img3 );
        setCancelable(false);

    }

    public TransparentProgressDialog(Context context,String title) {
//        super(context, R.style.TransparentProgressDialog);
       /* WindowManager.LayoutParams wlmp = getWindow().getAttributes();
        wlmp.gravity = Gravity.CENTER_HORIZONTAL;
        getWindow().setAttributes(wlmp);
        setTitle(title);
        setCancelable(false);
        setOnCancelListener(null);
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        iv = new ImageView(context);
        iv.setImageResource(resourceIdOfImage);
        layout.addView(iv, params);
        addContentView(layout, params);*/

        this(context, R.drawable.img1, R.drawable.img2, R.drawable.img3, title );
    }

   /* @Override
    public void show() {
        super.show();
        RotateAnimation anim = new RotateAnimation(0.0f, 360.0f , Animation.RELATIVE_TO_SELF, .5f, Animation.RELATIVE_TO_SELF, .5f);
        anim.setInterpolator(new LinearInterpolator());
        anim.setRepeatCount(Animation.INFINITE);
        anim.setDuration(3000);
        iv.setAnimation(anim);
        iv.startAnimation(anim);
    }*/



    FrameLayout layout;
    public TransparentProgressDialog(Context context, int image1, int image2, int image3) {
        super(context, R.style.TransparentProgressDialog);
        WindowManager.LayoutParams wlmp = getWindow().getAttributes();
        wlmp.gravity = Gravity.CENTER;
        getWindow().setAttributes(wlmp);
        setTitle(null);
        setOnCancelListener(null);
        layout = new FrameLayout(context);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        addCircle(context, image1, 1);
        addCircle(context, image2, 2);
        addCircle(context, image3, 3);
        addContentView(layout, params);
    }

    ImageView iv1;
    ImageView iv2;
    ImageView iv3;

    private void addCircle(Context context, int image, int index){
//        LinearLayout.LayoutParams paramsll = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//        LinearLayout ll = new LinearLayout(context);
        switch (index){
            case 1:
                iv1 = new ImageView(context);
                iv1.setImageResource(image);

                layout.addView(iv1);
                break;
            case 2:
                iv2 = new ImageView(context);
                iv2.setImageResource(image);

                layout.addView(iv2);
                break;
            case 3:
                iv3 = new ImageView(context);
                iv3.setImageResource(image);

                layout.addView(iv3);
                break;
        }

    }

    public TransparentProgressDialog(Context context, int image1, int image2, int image3,String title) {
        super(context, R.style.TransparentProgressDialog);
        WindowManager.LayoutParams wlmp = getWindow().getAttributes();
        wlmp.gravity = Gravity.CENTER;
        getWindow().setAttributes(wlmp);
        setTitle(title);
        setOnCancelListener(null);
        layout = new FrameLayout(context);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        addCircle(context, image1, 1);
        addCircle(context, image2, 2);
        addCircle(context, image3, 3);
        addContentView(layout, params);
    }

    @Override
    public void show() {
        super.show();
        handler = new Handler();
        if(iv1!=null) {
            handler.post(thread1);
        }

        if(iv2!=null) {
            handler.post(thread2);
        }

        if(iv3!=null) {
            showAnimation(iv3, TIME_LONG);
        }
    }
    boolean fastFlag1;
    boolean fastFlag2;
    Handler handler;
    static final int TIME_LONG = 3000; //TIME_NORM + TIME_SHORT
    static final int TIME_NORM = 1600;
    static final int TIME_SHORT = 1400;

    Runnable thread1 = new Runnable() {
        @Override
        public void run() {
            if(fastFlag1) {
                showAnimation(iv1, TIME_NORM);
                handler.postDelayed(thread1, TIME_NORM);
                fastFlag1 = false;
            }else{
                showAnimation(iv1, TIME_SHORT);
                handler.postDelayed(thread1, TIME_SHORT);
                fastFlag1 = true;
            }
        }
    };

    Runnable thread2 = new Runnable() {
        @Override
        public void run() {
            if(fastFlag2) {
                showAnimation(iv2, TIME_SHORT);
                handler.postDelayed(thread2, TIME_SHORT);
                fastFlag2 = false;
            }else{
                showAnimation(iv2, TIME_NORM);
                handler.postDelayed(thread2,TIME_NORM);
                fastFlag2 = true;
            }
        }
    };

    private void showAnimation(View v, int duration){
        RotateAnimation anim = new RotateAnimation(0.0f, 360.0f , Animation.RELATIVE_TO_SELF, .5f, Animation.RELATIVE_TO_SELF, .5f);
        anim.setInterpolator(new LinearInterpolator());
        anim.setRepeatCount(Animation.INFINITE);
        anim.setDuration(duration);
        v.setAnimation(anim);
        v.startAnimation(anim);
    }

    @Override
    public void setOnDismissListener(OnDismissListener listener) {
        super.setOnDismissListener(listener);
        if(handler!=null){
            handler.removeCallbacks(thread1);
            handler.removeCallbacks(thread2);

        }
    }

    @Override
    public void setOnCancelListener(OnCancelListener listener) {
        super.setOnCancelListener(listener);
        if(handler!=null){
            handler.removeCallbacks(thread1);
            handler.removeCallbacks(thread2);

        }
    }
}
