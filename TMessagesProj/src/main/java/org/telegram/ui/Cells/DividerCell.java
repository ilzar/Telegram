/*
 * This is the source code of Telegram for Android v. 1.7.x.
 * It is licensed under GNU GPL v. 2 or later.
 * You should have received a copy of the license in this archive (see LICENSE).
 *
 * Copyright Nikolai Kudashov, 2013-2014.
 */

package org.telegram.ui.Cells;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import org.telegram.messenger.AndroidUtilities;
import org.telegram.messenger.R;

public class DividerCell extends BaseCell {

    private final Paint paint;

    private final Paint getDividerPaint(int color) {
        final Paint paint = new Paint();
        paint.setColor(color);
        paint.setStrokeWidth(1);
        return paint;
    }

    public enum Scheme {
        Light,
        Dark
    }

    public DividerCell(Context context, Scheme scheme) {
        super(context);
        paint = getDividerPaint(context.getResources().getColor(scheme == Scheme.Light ? R.color.base_font : R.color.base_background));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), AndroidUtilities.dp(16) + 1);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawLine(getPaddingLeft(), AndroidUtilities.dp(8), getWidth() - getPaddingRight(), AndroidUtilities.dp(8), paint);
    }
}
