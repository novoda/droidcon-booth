package com.novoda.canvas.faceoff;

import android.support.annotation.IntDef;

@IntDef(value = {YSide.TOP, YSide.BOTTOM})
@interface YSide {
    int TOP = 0;
    int BOTTOM = 1;
}
