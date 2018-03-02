package com.lehand.indicator;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;

/**
 * @author zhou
 * <p>多色彩样式的标签</p>
 */

public class ColorTabView extends android.support.v7.widget.AppCompatTextView{

    private static final String TAG = ColorTabView.class.getName();

    private static final float sDEFAULT_TEXT_SIZE = 16.0f;
    private static final int sDEFAULT_PADDING_LEFT = 22;
    private static final int sDEFAULT_PADDING_TOP = 15;
    private static final int sDEFAULT_PADDING_RIGHT = 22;
    private static final int sDEFAULT_PADDING_BOTTOM = 20;

    private Layout mLayout;
    private Paint mColorPaint;
    private float mStartX;
    private float mOffset;
    private CharSequence mText;
    private float mTextWidth;

    private boolean isIncreasing;
    private int mD;
    private boolean isRed;

    // TODO: 2018/3/2 这儿的mD值是存在问题的，可能需要初始化回来
    private Runnable task = new Runnable() {
        @Override
        public void run() {
            mText = getText();
            mLayout = getLayout();
            mColorPaint = mLayout.getPaint();
            mTextWidth = calculateTextWidth();
            mStartX = calculateStartX();
            Log.d(TAG, "mTextWidth:" + mTextWidth + " mStartX:" + mStartX);
        }
    };

    public ColorTabView(Context context) {
        this(context,null);
    }

    public ColorTabView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setTextViewStyle();
    }

    public ColorTabView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        post(task);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        removeCallbacks(task);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if(mD == 0){
            drawStopState(canvas);
        }else{
            if (isIncreasing) {
                if (mD > 0) {
                    drawColorIncrease(canvas,mOffset);
                } else if (mD < 0) {
                    drawColorDecline(canvas,mOffset);
                }
            }else{
                if (mD > 0) {
                    drawColorDecline(canvas,mOffset);
                } else if (mD < 0) {
                    drawColorIncrease(canvas,mOffset);
                }
            }
        }


    }

    private void drawStopState(Canvas canvas) {
            mOffset = getMeasuredWidth();
            if (!isRed) {
                drawColorDecline(canvas,mOffset);
            }else{
                drawColorIncrease(canvas,mOffset);
            }
    }

    /**
     *绘制颜色递减
     * */
    private void drawColorDecline(Canvas canvas,float offset) {
        canvas.save();
        mColorPaint.setColor(Color.RED);
        canvas.clipRect(mStartX + offset, 0, getMeasuredWidth(), getMeasuredHeight());
        mLayout.draw(canvas,null,mColorPaint,0);
        mColorPaint.setColor(Color.BLACK);
        canvas.restore();
    }

    /**
     * 绘制颜色递增
     * */
    private void drawColorIncrease(Canvas canvas, float offset) {
        canvas.save();
        mColorPaint.setColor(Color.RED);
        canvas.clipRect(0, 0, mStartX + offset, getMeasuredHeight());
        mLayout.draw(canvas,null,mColorPaint,0);
        mColorPaint.setColor(Color.BLACK);
        canvas.restore();
    }

    private float calculateTextWidth() {
        if(mText != null){
            return mColorPaint.measureText(mText.toString());
        }
        return 0.0f;
    }

    private float calculateStartX() {
        return (getMeasuredWidth() - mTextWidth)/2;
    }

    private void setTextViewStyle() {
        // TODO: 2018/2/27 解决注释部分的问题
        setGravity(Gravity.CENTER);
        setTextSize(sDEFAULT_TEXT_SIZE);
//        setPadding(sDEFAULT_PADDING_LEFT,sDEFAULT_PADDING_TOP,
//                sDEFAULT_PADDING_RIGHT,sDEFAULT_PADDING_BOTTOM);
//        setTypeface(Typeface.create(Typeface.SANS_SERIF,Typeface.BOLD));
        setEllipsize(TextUtils.TruncateAt.END);
//        setSingleLine(true);
    }

//    public void onChanged(float offset, boolean isIncreasing) {
////        mOffset = offset * mTextWidth;
////        Log.d(TAG, "onChanged:" + mOffset);
////        this.isIncreasing = isIncreasing;
////        invalidate();
//    }

    public void onColorOffset(int d ,float offset, boolean isIncreasing) {
        mD = d;
        mOffset = offset * mTextWidth;
        this.isIncreasing = isIncreasing;
        invalidate();
    }

    public void setInitColor(boolean isRed) {
        this.isRed = isRed;
        mD = 0;
        invalidate();
    }
}
