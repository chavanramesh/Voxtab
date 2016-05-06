package com.ariatech.lib_project.anim;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.ariatech.lib_project.R;

public class AnimationClass {

    public static final int ANIMATION_LEFT = 1;
    public static final int ANIMATION_RIGHT = 2;
    public static final int ANIMATION_FADE = 3;
    public static final int ANIMATION_SLIDE_UP = 4;

    public static void setAnimation(View v, Context c, int type, long time) {
        switch (type) {
            case ANIMATION_LEFT:
                setLeftAnimation(v, c, time);
                break;
            case ANIMATION_RIGHT:
                setRightAnimation(v, c, time);
                break;
            case ANIMATION_FADE:
                setFadeAnimation(v, c, time);
                break;
            case ANIMATION_SLIDE_UP:
                setSlideUpEnter(v, c, time);
                break;
        }
    }

    private static void setRightAnimation(View v, Context c, long time) {
        if (v.getVisibility() == View.VISIBLE) {
            // If the view is visible already, slide it out to the right
            Animation out = AnimationUtils.makeOutAnimation(c, true);
            out.setDuration(time);
            v.startAnimation(out);
            v.setVisibility(View.GONE);
        } else {
            // If the view is hidden, do a fade_in in-place
            Animation in = AnimationUtils.makeInAnimation(c, false);
            in.setDuration(time);
            v.startAnimation(in);
            v.setVisibility(View.VISIBLE);
        }
    }

    private static void setLeftAnimation(View v, Context c, long time) {
        if (v.getVisibility() == View.VISIBLE) {
            // If the view is visible already, slide it out to the right
            Animation out = AnimationUtils.makeOutAnimation(c, false);
            out.setDuration(time);
            v.startAnimation(out);
            v.setVisibility(View.GONE);
        } else {
            // If the view is hidden, do a fade_in in-place
            Animation in = AnimationUtils.makeInAnimation(c, true);
            in.setDuration(time);
            v.startAnimation(in);
            v.setVisibility(View.VISIBLE);
        }
    }

    private static void setFadeAnimation(View v, Context c, long time) {
        if (v.getVisibility() == View.VISIBLE) {
            // If the view is visible already, slide it out to the right
            Animation out = AnimationUtils.loadAnimation(c,
                    android.R.anim.fade_out);
            out.setDuration(time);
            v.startAnimation(out);
            v.setVisibility(View.GONE);
        } else {
            // If the view is hidden, do a fade_in in-place
            Animation in = AnimationUtils.loadAnimation(c,
                    android.R.anim.fade_in);
            in.setDuration(time);
            v.startAnimation(in);
            v.setVisibility(View.VISIBLE);
        }
    }

    public static void slideUpEnter(View v, Context c, long time) {
        Animation slideUp = AnimationUtils.loadAnimation(c,
                R.anim.slide_up_enter);
        v.startAnimation(slideUp);
    }

    private static void setSlideUpEnter(View v, Context c, long time) {
        if (v.getVisibility() == View.VISIBLE) {
            Animation slideUp = AnimationUtils.loadAnimation(c,
                    R.anim.slide_down);
            slideUp.setDuration(time);
            v.startAnimation(slideUp);
            v.setVisibility(View.GONE);
        } else {
            // If the view is hidden, do a fade_in in-place
            Animation slideUp = AnimationUtils.loadAnimation(c,
                    R.anim.slide_up);
            slideUp.setDuration(time);
            v.startAnimation(slideUp);
            v.setVisibility(View.VISIBLE);
        }
    }


    public static void rotateAnimation(Context c, final View fromView,
                                       final View toView) {

        final Animation shrink = AnimationUtils.loadAnimation(c,
                R.anim.shrink_center);
        final Animation grow = AnimationUtils.loadAnimation(c,
                R.anim.grow_center);

        fromView.startAnimation(shrink);

        shrink.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                fromView.setVisibility(View.GONE);
                toView.setVisibility(View.VISIBLE);
                toView.setAnimation(grow);
                toView.startAnimation(grow);
            }
        });
    }

    public static void slideLeft(Context c, final View fromView,
                                 final View toView, long time) {

        final Animation out = AnimationUtils.makeOutAnimation(c, false);
        final Animation in = AnimationUtils.makeInAnimation(c, false);
        in.setDuration(time);
        out.setDuration(time);
        fromView.startAnimation(out);

        out.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {

                toView.setVisibility(View.VISIBLE);
                fromView.setVisibility(View.GONE);

                toView.setAnimation(in);
                toView.startAnimation(in);
            }
        });
    }

    public static void slideRight(Context c, final View fromView,
                                  final View toView, long time) {

        final Animation out = AnimationUtils.makeOutAnimation(c, true);
        final Animation in = AnimationUtils.makeInAnimation(c, true);
        in.setDuration(time);
        out.setDuration(time);
        fromView.startAnimation(out);

        out.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {

                toView.setVisibility(View.VISIBLE);
                fromView.setVisibility(View.GONE);

                toView.setAnimation(in);
                toView.startAnimation(in);
            }
        });
    }

    public static void setActivityTransition(Activity c) {
        c.overridePendingTransition(android.R.anim.slide_in_left,
                android.R.anim.slide_out_right);
    }

    public static void setActivityTransitionFadeIn(Activity c) {
        c.overridePendingTransition(R.anim.fade_in,
                R.anim.fade_out);
    }

    public static void setActivityTransitionOut(Activity c) {
        c.overridePendingTransition(R.anim.left_to_right_enter,
                R.anim.right_to_left_exit);
    }

    public static void setActivityTransitionIn(Activity c) {
        c.overridePendingTransition(R.anim.right_to_left_enter,
                R.anim.left_to_right_exit);
    }

}
