package com.novoda.canvas.faceoff;

import android.support.annotation.DrawableRes;

import com.novoda.canvas.R;

enum Player {
    COLT(R.drawable.colt, XSide.LEFT, YSide.TOP),
    JAKE(R.drawable.jake, XSide.RIGHT, YSide.BOTTOM);

    @DrawableRes
    private final int drawableId;
    private final XSide xSide;
    @YSide
    private final int ySide;

    Player(int drawableId, XSide xSide, @YSide int ySide) {
        this.drawableId = drawableId;
        this.xSide = xSide;
        this.ySide = ySide;
    }

    @DrawableRes
    public int getDrawableId() {
        return drawableId;
    }

    public XSide getXSide() {
        return xSide;
    }

    @YSide
    public int getYSide() {
        return ySide;
    }
}
