package com.example.macyg.androidmediaplayer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

public class WaveFormView extends View {
    private byte[] waveform;
    private Paint paint;

    public WaveFormView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        int color = ContextCompat.getColor(getContext(), R.color.colorAccent);
        paint.setColor(color);
        paint.setStrokeWidth(2f);
    }

    public void updateVisualizer(byte[] bytes) {
        waveform = bytes;
        invalidate(); // Triggers onDraw
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (waveform == null) return;

        int width = getWidth();
        int height = getHeight();

        float centerY = height / 2f;
        int len = waveform.length;

        float xIncrement = (float) width / (float) len;

        for (int i = 0; i < len - 1; i++) {
            float x1 = i * xIncrement;
            float x2 = (i + 1) * xIncrement;

            float y1 = centerY + ((byte) (waveform[i] + 128)) * (centerY / 128);
            float y2 = centerY + ((byte) (waveform[i + 1] + 128)) * (centerY / 128);

            canvas.drawLine(x1, y1, x2, y2, paint);
        }
    }
}