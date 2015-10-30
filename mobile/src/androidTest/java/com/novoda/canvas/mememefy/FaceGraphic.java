/*
 * Copyright (C) The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.novoda.canvas.mememefy;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.google.android.gms.vision.face.Face;

public class FaceGraphic extends GraphicOverlay.Graphic {

    private volatile Face mFace;

    private static Bitmap mBitmapMeme1, mBitmapMeme2, mBitmapMeme3, mBitmapMeme4;

    public FaceGraphic(GraphicOverlay overlay) {
        super(overlay);

        if(mBitmapMeme1 == null)
            mBitmapMeme1 = BitmapProvider.getMemeBitmap(overlay.getContext().getResources(), 1);

        if(mBitmapMeme2 == null)
            mBitmapMeme2 = BitmapProvider.getMemeBitmap(overlay.getContext().getResources(), 2);

        if(mBitmapMeme3 == null)
            mBitmapMeme3 = BitmapProvider.getMemeBitmap(overlay.getContext().getResources(), 3);

        if(mBitmapMeme4 == null)
            mBitmapMeme4 = BitmapProvider.getMemeBitmap(overlay.getContext().getResources(), 4);
    }

    public void setId(int id) {
    }

    public void updateFace(Face face) {
        mFace = face;
        postInvalidate();
    }

    @Override
    public void draw(Canvas canvas) {
        Face face = mFace;
        if (face == null) {
            return;
        }

        // Draws a circle at the position of the detected face, with the face's track id below.
        float x = translateX(face.getPosition().x + face.getWidth() / 2);
        float y = translateY(face.getPosition().y + face.getHeight() / 2);

        // Draws a bounding box around the face.
        float xOffset = scaleX(face.getWidth() / 2.0f);
        float yOffset = scaleY(face.getHeight() / 2.0f);
        float left = x - xOffset;
        float top = y - yOffset;

        int faceCover = (int)(face.getWidth() * 3.2);
        float smiling = mFace.getIsSmilingProbability();
        Bitmap scaled;

        if(smiling <= 0.2) {
            scaled = Bitmap.createScaledBitmap(mBitmapMeme2, faceCover, faceCover, false);
        } else if(smiling > 0.2 && smiling <= 0.4){
            scaled = Bitmap.createScaledBitmap(mBitmapMeme4, faceCover, faceCover, false);
        } else if(smiling > 0.4 && smiling <= 0.6){
            scaled = Bitmap.createScaledBitmap(mBitmapMeme3, faceCover, faceCover, false);
        } else {
            scaled = Bitmap.createScaledBitmap(mBitmapMeme1, faceCover, faceCover, false);
        }

        canvas.drawBitmap(scaled, left - (int) (faceCover * 0.1), top + (int) (faceCover * 0.15) , null);
        //canvas.drawRect(left, top, right, bottom, mBoxPaint);
    }
}
