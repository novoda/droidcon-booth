package com.novoda.canvas;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.novoda.canvas.base.NovodaActivityTest;

import static com.novoda.canvas.NovodaActivity.RANDOM;
import static org.fest.assertions.api.Assertions.assertThat;

public class QrCodeActivityTest extends NovodaActivityTest {

    private static final String[] listOfUrls = {
            "https://novoda.com",
            "http://droidcon.co.uk",
            "http://sd.keepcalm-o-matic.co.uk/i/keep-calm-and-submit-a-pr-7.png",
            "https://www.youtube.com/watch?v=dQw4w9WgXcQ"
    };
    private static final int[] listOfColors = {
        Color.BLACK, Color.GREEN, Color.argb(255,17,63,84), Color.DKGRAY
    };

    @Override
    public void startTestFor(Activity activity) {
        ViewGroup view = getParent();
        ImageView qrCodeImageView = new ImageView(activity);
        view.addView(qrCodeImageView);

        int randomIndex = RANDOM.nextInt(listOfUrls.length);
        String url = listOfUrls[randomIndex];
        int color = listOfColors[randomIndex];
        int qrCodeSizeInPixels = 1000;

        try {
            BitMatrix qrCode = new QRCodeWriter().encode(url, BarcodeFormat.QR_CODE, qrCodeSizeInPixels, qrCodeSizeInPixels);
            int width = qrCode.getWidth();
            int height = qrCode.getHeight();
            Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    bmp.setPixel(x, y, qrCode.get(x, y) ? color : Color.WHITE);
                }
            }
            qrCodeImageView.setImageBitmap(bmp);
        } catch (WriterException e) {
           assertThat(e).isNull();
        }
    }
}
