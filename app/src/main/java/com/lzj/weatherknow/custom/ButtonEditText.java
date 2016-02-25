package com.lzj.weatherknow.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Administrator on 2/24 0024.
 * 在一个EditText中嵌入一个Button
 */
public class ButtonEditText extends EditText {
    /**
     * 自定义一个Button以及它的高宽的padding
     */
    private Button mButton;
    private int mButtonHeightPadding = 10;
    private int mButtonWeightPadding = 10;

    public ButtonEditText(Context context){
        super(context);
        init();
    }

    public ButtonEditText(Context context, AttributeSet attrs){
        super(context, attrs);
        init();
    }

    public ButtonEditText(Context context, AttributeSet attrs, int defStyle){
        super(context, attrs, defStyle);
        init();
    }

    public void init(){
        mButton = new Button(getContext());
        mButton.setText("取消");
        mButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "你点击了取消", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 为了实现Button的点击效果，必须要把Touch的事件传递给Button，所以必须要重载dispatchTouchEvent()函数
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent event){
        if (event.getY() > mButtonHeightPadding && event.getY() < getHeight()-mButtonHeightPadding &&
                event.getX() > getWidth()-mButtonWeightPadding-mButton.getMeasuredWidth() &&
                event.getX() < getWidth()-mButtonWeightPadding){
            return mButton.dispatchTouchEvent(event);
        }
        return super.dispatchTouchEvent(event);
    }

    /**
     * 下面就需要对Button的显示进行处理了。显示首先是measure()、layout()，然后是draw()，缺一不可
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        mButton.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
                MeasureSpec.makeMeasureSpec(getMeasuredHeight()-mButtonHeightPadding*2,
                        MeasureSpec.EXACTLY));
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom){
        super.onLayout(changed, left, top, right, bottom);

        mButton.layout(0, 0, mButton.getMeasuredWidth(), mButton.getMeasuredHeight());
    }

    @Override
    protected void dispatchDraw(Canvas canvas){
        super.dispatchDraw(canvas);

        canvas.save();
        canvas.translate(getMeasuredWidth() - (mButton.getMeasuredWidth() + mButtonWeightPadding), mButtonHeightPadding);
        mButton.draw(canvas);
        canvas.restore();
    }
}
