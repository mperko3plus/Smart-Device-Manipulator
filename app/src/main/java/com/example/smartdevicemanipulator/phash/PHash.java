package com.example.smartdevicemanipulator.phash;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;

public class PHash {

    public static String calculateHash(Bitmap originalBitmap) {
        Bitmap resized = resize(originalBitmap, 8);
        Bitmap greyscaled = toGreyscale(resized);
        var hash = buildHash(greyscaled);
        return hash;
    }

    public static int getHammingDistance(String one, String two) {
        if (one.length() != two.length()) {
            return -1;
        }

        int counter = 0;
        for (int i = 0; i < one.length(); i++) {
            if (one.charAt(i) != two.charAt(i)) {
                counter++;
            }
        }
        return counter;
    }

    private static Bitmap resize(Bitmap bitmap, int N) {
        return Bitmap.createScaledBitmap(bitmap, N, N, true);
    }

    private static Bitmap toGreyscale(Bitmap bmpOriginal) {
        int height = bmpOriginal.getHeight();
        int width = bmpOriginal.getWidth();

        Bitmap bmpGrayscale = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmpGrayscale);

        ColorMatrix ma = new ColorMatrix();
        ma.setSaturation(0f);

        Paint paint = new Paint();
        paint.setColorFilter(new ColorMatrixColorFilter(ma));

        canvas.drawBitmap(bmpOriginal, 0f, 0f, paint);

        return bmpGrayscale;
    }


    private static String buildHash(Bitmap grayscaleBitmap) {
        int myHeight = grayscaleBitmap.getHeight();
        int myWidth = grayscaleBitmap.getWidth();
        int totalPixVal = 0;

        for (int i = 0; i < myWidth; i++) {
            for (int j = 0; j < myHeight; j++) {
                int currPixel = grayscaleBitmap.getPixel(i, j) & 0xff; // read lowest byte of pixels
                totalPixVal += currPixel;
            }
        }

        int average = totalPixVal / (myWidth * myHeight);
        StringBuilder hashVal = new StringBuilder();

        for (int i = 0; i < myWidth; i++) {
            for (int j = 0; j < myHeight; j++) {
                int currPixel = grayscaleBitmap.getPixel(i, j) & 0xff; // read lowest byte of pixels
                hashVal.append(currPixel >= average ? "1" : "0");
            }
        }

        return hashVal.toString();
    }

}
