package com.novoda.canvas.mememefy;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.novoda.canvas.R;

/**
 * Created by frederikschweiger on 30.10.15.
 */
public class BitmapProvider {

    public static Bitmap getMemeBitmap(Resources res, int which) {
        switch (which) {
            case 1:
                return BitmapFactory.decodeResource(res, R.drawable.meme01);
            case 2:
                return BitmapFactory.decodeResource(res, R.drawable.meme02);
            case 3:
                return BitmapFactory.decodeResource(res, R.drawable.meme03);
            case 4:
                return BitmapFactory.decodeResource(res, R.drawable.meme04);
            default:
                return BitmapFactory.decodeResource(res, R.drawable.meme01);
        }
    }
}
