package com.kefan.blackstone.ui.activity;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by MY SHIP on 2017/5/4.
 */

public class CustomDrawable extends Drawable {
    private Paint mpaint;

    public CustomDrawable(int color) {
        mpaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mpaint.setColor(color);
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        final Rect r=getBounds();
        float cx=r.exactCenterX();
        float cy=r.exactCenterY();
        canvas.drawCircle(cx,cy,Math.min(cx,cy),mpaint);
    }

    @Override
    public void setAlpha(@IntRange(from = 0, to = 255) int alpha) {
        mpaint.setAlpha(alpha);
        invalidateSelf();
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        mpaint.setColorFilter(colorFilter);
        invalidateSelf();;
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }
}
