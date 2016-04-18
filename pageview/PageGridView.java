package cn.dream.android.appstore.ui.view.pageview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ScrollView;

import cn.dream.android.appstore.R;

/**
 * 支持分页加载的GridView
 */
public class PageGridView extends ScrollView
        implements AbsListView.OnScrollListener {

    public interface LoadListener {
        public void load();
    }

    private GridView mGridView;
    private FooterView mFooterView;

    private int mState;
    private boolean mHasMorePage;

    private int mScrollState;

    private LoadListener mListener;

    public PageGridView(Context context) {
        this(context, null);
    }

    public PageGridView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PageGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        inflate(context, R.layout.view_page_grid, this);
        mGridView = (GridView) findViewById(R.id.gridView);
        mFooterView = (FooterView) findViewById(R.id.footer);
        mGridView.setOnScrollListener(this);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.PageGridView,
                defStyleAttr, 0);

        int numColumns = a.getInt(R.styleable.PageGridView_android_numColumns, 1);
        setNumColumns(numColumns);

        int stretchMode = a.getInt(R.styleable.PageGridView_android_stretchMode,
                GridView.STRETCH_COLUMN_WIDTH);
        setStretchMode(stretchMode);

        int columnWidth = a.getDimensionPixelSize(R.styleable.PageGridView_android_columnWidth, -1);
        setColumnWidth(columnWidth);

        int hSpacing =
                a.getDimensionPixelSize(R.styleable.PageGridView_android_horizontalSpacing, 0);
        setHorizontalSpacing(hSpacing);

        int vSpacing = a.getDimensionPixelSize(R.styleable.PageGridView_android_verticalSpacing, 0);
        setVerticalSpacing(vSpacing);

        Drawable selector = a.getDrawable(R.styleable.PageGridView_android_listSelector);
        setSelector(selector);

        int fSpacing = a.getDimensionPixelSize(R.styleable.PageGridView_footerSpacing, 0);
        setFooterSpacing(fSpacing);

        a.recycle();
    }

    public void setLoadListener(LoadListener listener) {
        mListener = listener;
        mFooterView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.load();
            }
        });
    }

    public void notifyState(int state, boolean hasMorePage) {
        notifyState(state);
        mHasMorePage = hasMorePage;
    }

    public void notifyState(int state) {
        mState = state;
        mFooterView.notifyState(state);
    }

    public void setHasMorePage(boolean hasMorePage) {
        mHasMorePage = hasMorePage;
    }

    public void setAdapter(ListAdapter adapter) {
        mGridView.setAdapter(adapter);
    }

    /**
     * @see GridView#setNumColumns(int)
     */
    public void setNumColumns(int numColumns) {
        mGridView.setNumColumns(numColumns);
    }

    /**
     * @see GridView#setStretchMode(int)
     */
    public void setStretchMode(int stretchMode) {
        switch (stretchMode) {
            case 0:
                mGridView.setStretchMode(GridView.NO_STRETCH);
                break;

            case 1:
                mGridView.setStretchMode(GridView.STRETCH_SPACING);
                break;

            case 2:
                mGridView.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
                break;

            case 3:
                mGridView.setStretchMode(GridView.STRETCH_SPACING_UNIFORM);
                break;

            default:
                throw new IllegalArgumentException("must be one of: GridView.NO_STRETCH, " +
                        "GridView.STRETCH_SPACING, GridView.STRETCH_COLUMN_WIDTH, " +
                        "GridView.STRETCH_SPACING_UNIFORM");
        }
    }

    /**
     * @see GridView#setColumnWidth(int)
     */
    public void setColumnWidth(int columnWidth) {
        if (columnWidth > 0) {
            mGridView.setColumnWidth(columnWidth);
        }
    }
    /**
     * @see GridView#setHorizontalSpacing(int)
     */
    public void setHorizontalSpacing(int hSpacing) {
        mGridView.setHorizontalSpacing(hSpacing);
    }

    /**
     * @see GridView#setVerticalSpacing(int)
     */
    public void setVerticalSpacing(int vSpacing) {
        mGridView.setVerticalSpacing(vSpacing);
    }

    public void setFooterSpacing(int fSpacing) {
        if (fSpacing > 0) {
            mFooterView.setPadding(mFooterView.getPaddingLeft(), fSpacing,
                    mFooterView.getPaddingRight(), mFooterView.getPaddingBottom());
        }
    }

    /**
     * @see android.widget.AbsListView#setSelector(int)
     */
    public void setSelector(@DrawableRes int resID) {
        mGridView.setSelector(resID);
    }

    /**
     * @see android.widget.AbsListView#setSelector(Drawable)
     */
    public void setSelector(Drawable drawable) {
        if (drawable != null) {
            mGridView.setSelector(drawable);
        }
    }

    public void smoothScrollToPosition(int position) {
        if (position < mGridView.getCount()) {
            mGridView.smoothScrollToPosition(position);
        }
    }

    public int getCount() {
        return mGridView.getCount();
    }

    public int getFirstVisiblePosition() {
        return mGridView.getFirstVisiblePosition();
    }

    public int getLastVisiblePosition() {
        return mGridView.getLastVisiblePosition();
    }

    public Object getItemAtPosition(int position) {
        return mGridView.getItemAtPosition(position);
    }

    public View getGridChildAt(int index) {
        return mGridView.getChildAt(index);
    }

    public int getScrollState() {
        return mScrollState;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        mScrollState = scrollState;
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
                         int totalItemCount) {

    }
}
