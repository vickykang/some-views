package cn.dream.android.appstore.ui.view;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;

/**
 * 绘制带描边的字体
 */
public class StrokeTextDrawable extends Drawable {
    
    private final Paint mPaint;
    private String mText;
    private float mX;
    private float mY;
    private int mTextSize;
    private int mStrokeWidth;
    private int mStrokeColor;
    private int mTextColor;

    public StrokeTextDrawable(String text,
                              float x,
                              float y,
                              int textSize,
                              int strokeWidth,
                              int strokeColor,
                              int textColor) {
        mText = text;
        mX = x;
        mY = y;
        mTextSize = textSize;
        mStrokeWidth = strokeWidth;
        mStrokeColor = strokeColor;
        mTextColor = textColor;
        mPaint = new Paint();
    }

    public void setText(String text) {
        mText = text;
    }

    @Override
    public void draw(Canvas canvas) {
        drawStroke(canvas);
        drawText(canvas);
    }

    private void drawStroke(Canvas canvas) {
        initPaint();
        mPaint.setStrokeWidth(mStrokeWidth);
        mPaint.setColor(mStrokeColor);
        mPaint.setStyle(Paint.Style.STROKE);

        canvas.drawText(mText, mX, mY - mPaint.ascent() - mTextSize / 2, mPaint);
    }

    private void drawText(Canvas canvas) {
        initPaint();
        mPaint.setColor(mTextColor);
        mPaint.setStyle(Paint.Style.FILL);

        canvas.drawText(mText, mX, mY - mPaint.ascent() - mTextSize / 2, mPaint);
    }

    private void initPaint() {
        mPaint.reset();
        mPaint.setAntiAlias(true);
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setTextSize(mTextSize);
        mPaint.setTypeface(Typeface.SERIF);
    }

    @Override
    public void setAlpha(int alpha) {
        mPaint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        mPaint.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        return 1 - mPaint.getAlpha();
    }
}
