package com.mine.dearbear.view.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/***
 * 该类设置布局内宽度和高度一致
 * @author zhanggx2
 *
 */
public class SquareLayout extends RelativeLayout {

    public SquareLayout(Context context, AttributeSet attributeSet, int defStyle) {
        super(context, attributeSet, defStyle);
    }

    public SquareLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public SquareLayout(Context context) {
        super(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(getDefaultSize(0, widthMeasureSpec),
                getDefaultSize(0, heightMeasureSpec));

        int childWidthSize = getMeasuredWidth();
        int childHeightSize = getMeasuredHeight();

        heightMeasureSpec = widthMeasureSpec = MeasureSpec.makeMeasureSpec(childWidthSize, MeasureSpec.EXACTLY);
        // TODO Auto-generated method stub
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

}
