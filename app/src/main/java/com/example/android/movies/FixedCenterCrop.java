package com.example.android.movies;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

/**
 * Created by Guto on 20/10/2017.
 */

public class FixedCenterCrop extends android.support.v7.widget.AppCompatImageView
{
    Context context;
    private float scale;
    private int width,height;

    public FixedCenterCrop(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public FixedCenterCrop(final Context context){
        super(context);

    }

    @Override
    protected void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec)
    {
        final Drawable d = this.getDrawable();

        if(d != null) {
            scale = FixedCenterCrop.this.getResources().getDisplayMetrics().density;
//            width = FixedCenterCrop.this.getResources().getDisplayMetrics().widthPixels - (int)(14 * scale + 0.5f);
//            height = (width / 16) * 9;

//            int height = MeasureSpec.getSize(heightMeasureSpec);
//            int width = MeasureSpec.getSize(widthMeasureSpec);

            if(width >= height)
                width = FixedCenterCrop.this.getResources().getDisplayMetrics().widthPixels - (int)(14 * scale + 0.5f);
//                height = (int) Math.ceil(width * (float) d.getIntrinsicHeight() / d.getIntrinsicWidth());
            else
                height = (width / 16) * 9;
//                width = (int) Math.ceil(height * (float) d.getIntrinsicWidth() / d.getIntrinsicHeight());

            this.setMeasuredDimension(width, height);

        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }
}
