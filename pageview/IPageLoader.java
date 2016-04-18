package cn.dream.android.appstore.ui.view.pageview;

import android.view.View;

/**
 * Interface definition for {@link android.widget.ListView}
 * to support page loading when scroll to bottom or click footer.
 *
 * Created by Vivam Kang on 2015/10/16.
 */
public interface IPageLoader {

    public static final int STATE_NONE = 0;

    /** no more pages */
    public static final int STATE_NO_MORE = 1;

    /** has more pages */
    public static final int STATE_HAS_MORE = 2;

    /** loading next page */
    public static final int STATE_LOADING = 3;

    /** load failed */
    public static final int STATE_FAIL = -1;

    /**
     * Interface definition for a call to be invoked
     * {@link android.widget.ListView} scrolled to bottom.
     */
    interface BottomListener {
        void onScrollToBottom();
    }

    /**
     * Interface definition for a call to be invoked when the footer
     * was on click.
     */
    interface OnFooterClickListener {
        void onFooterClick(View view, int state);
    }

   /* *//**
     * The state of {@link android.widget.ListView}
     *//*
    enum State {
        LOADING, FAILED, SUCCESS, EMPTY
    }*/

    void setBottomListener(BottomListener listener);

    void setOnFooterClickListener(OnFooterClickListener listener);

    /**
     * Get the current state of {@link android.widget.ListView}
     * @return current state
     */
    int getCurrentState();

    /**
     * Set current state.
     *
     * @param state current state
     * @see #getCurrentState()
     */
    void setCurrentState(int state);


    /**
     * Indicates whether the data has more pages to load.
     *
     * @return true if there are more pages to load,
     *          or false if no more pages.
     */
    boolean hasMorePage();

     /**
     * @see #hasMorePage()
     */
    void setHasMorePage(boolean hasMorePage);
}
