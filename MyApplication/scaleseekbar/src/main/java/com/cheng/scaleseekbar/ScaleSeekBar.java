package com.cheng.scaleseekbar;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;



/**
 * Created by baina on 17-9-20.
 */


public class ScaleSeekBar extends View {

    int mSmallCircleRadius;
    int mBigCircleInnerRadius;
    int mBigCircleOuterRadius;
    int mPointNum;
    int mSpace;
    int mPaddingTop;
    int mPaintWidth;
    int mColors[] = new int[]{Color.parseColor("#4a7feb"), Color.parseColor("#e8e8e9")};
    int mCircleColor[] = new int[]{Color.parseColor("#4a7feb"), Color.parseColor("#dde8ff")};
    Paint mPaint;
    private int mWidth;
    private int mSegmentLength;
    private int mLength;
    private int mEndPoint;

    private int mOldProgress, mNewProgress;
    private ProgressListener mListener;


    public ScaleSeekBar(Context context) {
        super(context);
    }

    public ScaleSeekBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScaleSeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintWidth = Util.dipToPixel(2);
        mPaint.setStrokeWidth(mPaintWidth);
        mSpace = Util.dipToPixel(12);
        mPointNum = 5;

        mSmallCircleRadius = Util.dipToPixel(4);
        mBigCircleInnerRadius = Util.dipToPixel(8);
        mBigCircleOuterRadius = Util.dipToPixel(10);
        mPaddingTop = getPaddingTop() + mBigCircleOuterRadius;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        mWidth = getMeasuredWidth();
        mLength = mWidth - mSpace * 2;// 去除左右两边的边距
        mSegmentLength = mLength / (mPointNum - 1);// 5个点分割成4段 每段的长度
        mEndPoint = mSpace + (mSegmentLength * (mPointNum - 1));//终止点的位置.
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.mPaint.setColor(this.mColors[1]);
        canvas.drawLine(this.mSpace, this.mSpace + this.mPaddingTop, this.mEndPoint, this.mSpace + this.mPaddingTop, this.mPaint);// 整体绘制一条灰色线贯穿整体
        this.mPaint.setColor(this.mColors[0]);
//        canvas.drawLine(this.mSpace, this.mSpace * 2 / 3 + this.mPaddingTop,
        canvas.drawCircle(this.mSpace, this.mSpace + this.mPaddingTop, mSmallCircleRadius, this.mPaint);//画了一个小圆环
        canvas.drawLine(this.mSpace, this.mSpace + this.mPaddingTop, this.mSpace + this.mSegmentLength * this.mNewProgress, this.mSpace + this.mPaddingTop, this.mPaint); // 根据进度条画了新的颜色线
        int index = 1;
        if (index < this.mPointNum) {
            for (; index < this.mPointNum; index++) {
                if (this.mNewProgress > index) {
                    this.mPaint.setColor(this.mColors[0]);
                } else {
                    this.mPaint.setColor(this.mColors[1]);
                }
                canvas.drawCircle(this.mSpace + index * mSegmentLength, this.mSpace + this.mPaddingTop, mSmallCircleRadius, this.mPaint);//每个位置画一个小圆 颜色不一样
            }

        }
        this.mPaint.setColor(this.mCircleColor[1]);
        canvas.drawCircle(this.mSpace + this.mSegmentLength * this.mNewProgress, this.mSpace + this.mPaddingTop, this.mBigCircleOuterRadius, this.mPaint);
        this.mPaint.setColor(this.mCircleColor[0]);
        canvas.drawCircle(this.mSpace + this.mSegmentLength * this.mNewProgress, this.mSpace + this.mPaddingTop, this.mBigCircleInnerRadius, this.mPaint);


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        int x = (int) event.getX();
        notifyDataChanged(x);
        return true;

    }

    private void notifyDataChanged(int x) {
        this.mNewProgress = (this.mSegmentLength / 3 + x - mSpace) / this.mSegmentLength;
        if ((this.mListener != null)) {
            this.mListener.notifyProgressChanged(this.mNewProgress);
        }
        invalidate();
    }

    public void setProgressListener(ProgressListener listener) {
        this.mListener = listener;
    }

    public int getProgress() {
        return mNewProgress;
    }

    public void setProgress(int progress) {
        this.mNewProgress = progress;
    }

    public interface ProgressListener {
        void notifyProgressChanged(int progress);
    }
}

