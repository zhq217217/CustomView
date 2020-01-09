package com.example.customview.toggleview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;


import com.example.customview.R;

import androidx.annotation.Nullable;

public class ToggleView extends View implements View.OnClickListener {
    private boolean openState = true;
    private int openBgColor, closeBgColor, toggleColor;
    private Paint paint;
    private float openLength;

    public ToggleView(Context context) {
        super(context);
    }

    public ToggleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray t = getContext().obtainStyledAttributes(attrs,
                R.styleable.ToggleView);
        openState = t.getBoolean(R.styleable.ToggleView_openState, false);
        openBgColor = t.getColor(R.styleable.ToggleView_openBgColor, getResources().getColor(R.color.color_1962E8));
        closeBgColor = t.getColor(R.styleable.ToggleView_closeBgColor, getResources().getColor(R.color.color_ccc));
        toggleColor = t.getColor(R.styleable.ToggleView_toggleColor, getResources().getColor(R.color.color_White));
        t.recycle();
        openLength = openState ? 1.0f : 0.0f;
    }


    public ToggleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setFilterBitmap(true);
        setOnClickListener(this);
    }

    float halfHeight;
    float halfWidth;

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        halfHeight = 1.0f * h / 2.0f;
        halfWidth = 1.0f * w / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(halfHeight * 1.9f);
        paint.setColor(openState ? openBgColor : closeBgColor);
        canvas.drawLine(halfHeight, halfHeight, halfWidth * 2.0f - halfHeight, halfHeight, paint);
        if (openState) {
            paint.setColor(closeBgColor);
            canvas.drawLine(halfHeight + ((halfWidth - halfHeight) * openLength) * 2.0f, halfHeight, halfWidth * 2.0f - halfHeight, halfHeight, paint);
        } else {
            paint.setColor(openBgColor);
            canvas.drawLine(halfHeight, halfHeight, halfHeight + (halfWidth - halfHeight) * openLength * 2.0f, halfHeight, paint);
        }
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(toggleColor);
        canvas.drawCircle(halfHeight + ((halfWidth - halfHeight) * openLength) * 2.0f, halfHeight, 1.0f * getHeight() / 2.2f, paint);
    }

    @Override
    public void onClick(final View view) {
        setClickable(false);
        ObjectAnimator objectAnimator;
        if (openState) {
            objectAnimator = ObjectAnimator.ofFloat(this, "openLength", 1.0f, 0.0f);
        } else {
            objectAnimator = ObjectAnimator.ofFloat(this, "openLength", 0.0f, 1.0f);
        }
        objectAnimator.setDuration(300);
        objectAnimator.start();
        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                setClickable(true);
                openState = !openState;
                invalidate();
                if (null != onOpenChange) {
                    onOpenChange.OpenChange(openState);
                }
            }
        });
    }

    public float getOpenLength() {
        return openLength;
    }

    public void setOpenLength(float openLength) {
        this.openLength = openLength;
        invalidate();
    }

    public interface OnOpenChange {
        void OpenChange(boolean openstate);
    }

    ;
    private OnOpenChange onOpenChange;

    public void setOnOpenChange(OnOpenChange onOpenChange) {
        this.onOpenChange = onOpenChange;
    }
}
