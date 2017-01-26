package iammert.com.library;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

/**
 * Created by mertsimsek on 26/01/17.
 */

public class StatusView extends RelativeLayout {

    /**
     * Current status of status view
     */
    private Status currentStatus;

    /**
     * Automatically hide when status changed to complete
     */
    private boolean hideOnComplete;

    /**
     * Views for each status
     */
    private View completeView;
    private View errorView;
    private View loadingview;

    /**
     * Fade in out animations
     */
    private Animation fadeOut;
    private Animation fadeIn;

    /**
     * layout inflater
     */
    private LayoutInflater inflater;

    public StatusView(Context context) {
        super(context);
        init(context, null);
    }

    public StatusView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public StatusView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public StatusView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {

        /**
         * Load initial values
         */
        currentStatus = Status.IDLE;
        hideOnComplete = true;
        setVisibility(View.INVISIBLE);
        fadeIn = AnimationUtils.loadAnimation(context, R.anim.fade_in);
        fadeOut = AnimationUtils.loadAnimation(context, R.anim.fade_out);
        inflater = LayoutInflater.from(context);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.statusview);

        /**
         * get status layout ids
         */
        int completeLayoutId = a.getResourceId(R.styleable.statusview_complete, 0);
        int errorLayoutId = a.getResourceId(R.styleable.statusview_error, 0);
        int loadingLayoutId = a.getResourceId(R.styleable.statusview_loading, 0);

        /**
         * attach view to root
         */
        completeView = inflater.inflate(completeLayoutId, null);
        errorView = inflater.inflate(errorLayoutId, null);
        loadingview = inflater.inflate(loadingLayoutId, null);

        /**
         * set visibilities of childs
         */
        completeView.setVisibility(View.INVISIBLE);
        errorView.setVisibility(View.INVISIBLE);
        loadingview.setVisibility(View.INVISIBLE);

        a.recycle();

    }

    public void setStatus(final Status status) {
        if (currentStatus == Status.IDLE) {
            currentStatus = status;
            enterAnimation(getCurrentView(currentStatus));
        } else if (status != Status.IDLE) {
            switchAnimation(getCurrentView(currentStatus), getCurrentView(status));
            currentStatus = status;
        } else {
            exitAnimation(getCurrentView(currentStatus));
        }
    }

    private View getCurrentView(Status status) {
        if (status == Status.IDLE)
            return null;
        else if (status == Status.COMPLETE)
            return completeView;
        else if (status == Status.ERROR)
            return errorView;
        else if (status == Status.LOADING)
            return loadingview;
        return null;
    }

    private void switchAnimation(final View exitView, View enterView) {
        exitView.setVisibility(View.VISIBLE);
        enterView.setVisibility(View.VISIBLE);
        exitView.startAnimation(fadeOut);
        enterView.startAnimation(fadeIn);
        fadeOut.setAnimationListener(new SimpleAnimListener() {
            @Override
            public void onAnimationEnd(Animation animation) {
                exitView.setVisibility(View.INVISIBLE);
                fadeOut.setAnimationListener(null);
            }
        });
    }

    private void enterAnimation(View enterView) {
        if (enterView == null)
            return;

        enterView.setVisibility(VISIBLE);
        enterView.startAnimation(fadeIn);
    }

    private void exitAnimation(final View exitView) {
        if(exitView == null)
            return;

        exitView.startAnimation(fadeOut);
        fadeOut.setAnimationListener(new SimpleAnimListener() {
            @Override
            public void onAnimationEnd(Animation animation) {
                exitView.setVisibility(View.INVISIBLE);
                fadeOut.setAnimationListener(null);
            }
        });
    }
}
