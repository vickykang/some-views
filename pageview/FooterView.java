package cn.dream.android.appstore.ui.view.pageview;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import cn.dream.android.appstore.R;

/**
 * 分页加载的底部
 */
public class FooterView extends FrameLayout {

    protected View hasMoreView;
    protected ImageView loadingView;
    protected View failedView;
    protected View emptyView;

    protected AnimationDrawable loadingAnimDrawable;

    public FooterView(Context context) {
        this(context, null, 0);
    }

    public FooterView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FooterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.view_page_footer, this);
        hasMoreView = findViewById(R.id.has_more_view);
        loadingView = (ImageView) findViewById(R.id.page_loading_view);
        failedView = findViewById(R.id.page_failed_view);
        emptyView = findViewById(R.id.page_empty_view);

        loadingAnimDrawable = (AnimationDrawable) getContext().getResources()
                .getDrawable(R.drawable.anim_page_loading);
        loadingView.setImageDrawable(loadingAnimDrawable);
    }

    public void notifyState(int state) {
        switch (state) {
            case IPageLoader.STATE_NONE:
                setVisibility(GONE);
                if (loadingAnimDrawable.isRunning()) {
                    loadingAnimDrawable.stop();
                }
                break;

            case IPageLoader.STATE_HAS_MORE:
                setVisibility(View.VISIBLE);
                hasMoreView.setVisibility(VISIBLE);
                loadingView.setVisibility(GONE);
                loadingAnimDrawable.stop();
                failedView.setVisibility(GONE);
                emptyView.setVisibility(GONE);
                break;

            case IPageLoader.STATE_LOADING:
                setVisibility(VISIBLE);
                hasMoreView.setVisibility(GONE);
                loadingView.setVisibility(VISIBLE);
                loadingAnimDrawable.start();
                failedView.setVisibility(GONE);
                emptyView.setVisibility(GONE);
                break;

            case IPageLoader.STATE_NO_MORE:
                setVisibility(View.VISIBLE);
                hasMoreView.setVisibility(GONE);
                loadingView.setVisibility(GONE);
                loadingAnimDrawable.stop();
                failedView.setVisibility(GONE);
                emptyView.setVisibility(VISIBLE);
                break;

            case IPageLoader.STATE_FAIL:
                setVisibility(VISIBLE);
                hasMoreView.setVisibility(GONE);
                loadingView.setVisibility(GONE);
                loadingAnimDrawable.stop();
                failedView.setVisibility(VISIBLE);
                emptyView.setVisibility(GONE);
                break;
        }
    }

    public void setOnClickListener(OnClickListener listener) {
        hasMoreView.setOnClickListener(listener);
        failedView.setOnClickListener(listener);
    }

    @Override
    protected void onDetachedFromWindow() {
        loadingAnimDrawable.stop();
        super.onDetachedFromWindow();
    }
}
