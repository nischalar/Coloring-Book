

package com.rjgram.coloringbook;

import android.graphics.Color;
import android.graphics.Point;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import android.view.MotionEvent;
import android.view.View;


import androidx.annotation.Nullable;

import com.rjgram.coloringbook.Activity.SingleViewActivity;
import com.rjgram.coloringbook.Activity.SingleViewActivity.myTask;

import java.util.ArrayList;
import java.util.List;

public class DrawingView extends View {

    private Bitmap bitmap;
    private static final int Max = 10;
    private List<Bitmap> bitmapList = new ArrayList<>();
    private Bitmap defaultBitmap = null;
    Context context;
    SingleViewActivity singleViewActivity;

    public DrawingView(Context context) {
        super(context);
        this.context = context;
    }

    public DrawingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        if (bitmap == null) {

            Bitmap srcBitmap = BitmapFactory.decodeResource(getResources(), common.SELECTED_IMAGE)
                    .copy(Bitmap.Config.ARGB_8888, true);
            bitmap = Bitmap.createScaledBitmap(srcBitmap, w, h, false);

        }
        if (defaultBitmap == null) {
            defaultBitmap = Bitmap.createBitmap(bitmap);

        }
    }


    public void setupBitmap() {
        for (int i = 0; i < bitmap.getWidth(); i++) {
            for (int j = 0; j < bitmap.getHeight(); j++) {
                int alpha = 255 - ((bitmap.getPixel(i, j) >> 16) & 0xff);
                if (alpha < 200) {
                    bitmap.setPixel(i, j, Color.WHITE);
                } else {
                    bitmap.setPixel(i, j, Color.BLACK);
                }
            }
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(bitmap, 0, 0, null);
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        paint((int) event.getX(), (int) event.getY());

        return true;
    }


    private void paint(int x, int y) {
        if (x < 0 || y < 0 || x >= bitmap.getWidth() || y >= bitmap.getHeight()) {
            return;
        }
        int targetColor = bitmap.getPixel(x, y);
        if (targetColor != Color.BLACK) {
            addLastAction(Bitmap.createBitmap(getBitmap()));
            singleViewActivity = new SingleViewActivity();

            new myTask(bitmap, context, new Point(x, y), common.SELECTED_COLOR, targetColor)
                    .execute(new Void[0]);
            // FloodFill.floodFill(bitmap, new Point(x, y), targetColor, common.SELECTED_COLOR);
            if (bitmapList.size() > Max) {
                for (int i = 0; i < 5; i++) {
                    bitmapList.remove(i);
                }
            }
            invalidate();
        }

    }


    public Bitmap getBitmap() {
        return bitmap;
    }

    public void undoLastAction() {

        if (bitmapList.size() > 0) {
            bitmapList.remove(bitmapList.size() - 1);
            if (bitmapList.size() > 0) {
                bitmap = bitmapList.get(bitmapList.size() - 1);
            } else {
                bitmap = Bitmap.createBitmap(defaultBitmap);
            }
            invalidate();
        }

    }

    private void addLastAction(Bitmap b) {
        bitmapList.add(b);
    }

    public void newPage() {
        bitmap = Bitmap.createBitmap(defaultBitmap);
    }

    public void DeleteAll() {
        if (bitmapList.size() > 0) {
            bitmapList.removeAll(bitmapList);
            bitmap = Bitmap.createBitmap(defaultBitmap);
            invalidate();
        }
    }
}
