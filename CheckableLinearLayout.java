package cn.dream.android.appstore.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Checkable;
import android.widget.LinearLayout;

/**
 * 支持checked状态的LinearLayout，会同时改变其子空间的checked状态
 */
public class CheckableLinearLayout extends LinearLayout implements Checkable {

    private static final int[] STATE_CHECKABLE = { android.R.attr.state_checked };

    private boolean isChecked;

    public CheckableLinearLayout(Context context) {
        super(context);
    }

    public CheckableLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CheckableLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setChecked(boolean checked) {
        isChecked = checked;

        refreshDrawableState();

        View child;

        for (int i = 0; i < getChildCount(); i++) {
            child = getChildAt(i);
            if (child instanceof Checkable) {
                ((Checkable) child).setChecked(isChecked);
            }
        }
    }

    @Override
    public boolean isChecked() {
        return isChecked;
    }

    @Override
    public void toggle() {
        setChecked(!isChecked);
    }

    @Override
    protected int[] onCreateDrawableState(int extraSpace) {
        int[] drawableState = super.onCreateDrawableState(extraSpace + 1);
        if (isChecked) {
            mergeDrawableStates(drawableState, STATE_CHECKABLE);
        }
        return drawableState;
    }
}
