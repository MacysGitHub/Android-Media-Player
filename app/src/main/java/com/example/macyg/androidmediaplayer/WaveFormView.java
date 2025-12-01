package com.example.macyg.androidmediaplayer;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class WaveFormView extends View {
    private byte[] waveform;
    private Paint paint;

    public WaveFormView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        int color = Color.CYAN;
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

        float centerX = width / 2f;
        float centerY = height / 2f;

        // Base radius of the circular ring
        float baseRadius = Math.min(centerX, centerY) * 0.6f;
        int len = waveform.length;

        // Angle between each point (in radians)
        float angleStep = (float) (2 * Math.PI / len);

        for (int i = 0; i < len; i++) {
            float angle = i * angleStep;

            // Normalize amplitude from waveform (value between 0 and 1)
            float amplitude = (byte)(waveform[i] + 128) / 255f;

            // Determine how much longer than the base radius the line should be
            float lineLength = amplitude * baseRadius * 0.5f;

            // Starting point (on the base circle)
            float startX = centerX + baseRadius * (float) Math.cos(angle);
            float startY = centerY + baseRadius * (float) Math.sin(angle);

            // End point (extending outward based on waveform amplitude)
            float endX = centerX + (baseRadius + lineLength) * (float) Math.cos(angle);
            float endY = centerY + (baseRadius + lineLength) * (float) Math.sin(angle);

            canvas.drawLine(startX, startY, endX, endY, paint);
        }
    }
}