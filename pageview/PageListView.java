package cn.dream.android.appstore.ui.view.pageview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

/**
 * 支持分页加载的ListView
 */
public class PageListView extends ListView implements IPageLoader {

    private FooterView mFooter;

    private BottomListener mBottomListener;
    private OnFooterClickListener mFooterClickListener;

    private int mCurrentState;
    private boolean mHasMorePage;

    private OnScrollListener mScrollListner;

    public PageListView(Context context) {
        this(context, null, 0);
    }

    public PageListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PageListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mScrollListner = new LoadMoreScrollListener();
        initFooter();
    }

    private void initFooter() {
        mFooter = getFooterView(getContext());
        if (mFooter == null) return;
        mFooter.setVisibility(GONE);
        addFooterView(mFooter);
        mFooter.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mFooterClickListener != null) {
                    mFooterClickListener.onFooterClick(v, mCurrentState);
                }
            }
        });
    }

    protected FooterView getFooterView(Context context) {
        return new FooterView(context);
    }

    protected void onStateChanged(int state) {

    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        super.onTouchEvent(ev);
        return false;
    }

    @Override
    public void setBottomListener(BottomListener listener) {
        mBottomListener = listener;
    }

    @Override
    public void setOnFooterClickListener(OnFooterClickListener listener) {
        mFooterClickListener = listener;
    }

    @Override
    public int getCurrentState() {
        return mCurrentState;
    }

    @Override
    public void setCurrentState(int state) {
        if (mCurrentState == state) return;

        mCurrentState = state;

        if (mFooter == null) return;

        onStateChanged(state);
        mFooter.notifyState(state);
    }

    @Override
    public boolean hasMorePage() {
        return mHasMorePage;
    }

    @Override
    public void setHasMorePage(boolean hasMorePage) {
        mHasMorePage = hasMorePage;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // Calculate entire height by providing a very large height hint.
        // View.MEASURE_SIZE_MASK represents the largest height possible.
        int expandSpec = MeasureSpec.makeMeasureSpec(MEASURED_SIZE_MASK, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);

        ViewGroup.LayoutParams lp = getLayoutParams();
        lp.height = getMeasuredHeight();
    }

    private class LoadMoreScrollListener implements OnScrollListener {

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            if (view.getLastVisiblePosition() == (view.getCount() - 1)
                    && mBottomListener != null) {
                mBottomListener.onScrollToBottom();
            }
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
                             int totalItemCount) {

        }
    }
}
