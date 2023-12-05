package cn.gavinliu.similar.photo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import cn.gavinliu.similar.photo.entry.Photo;

/**
 * Created by gavin on 2017/3/27.
 */

public class SimilarPhoto {

    private static final String TAG = SimilarPhoto.class.getSimpleName();


    public static boolean matches(List<Photo> refPhotos, Photo currentPhoto) {
//        calculateFingerPrint(currentPhoto);

        for (int i = 0; i < refPhotos.size(); i++) {
            Photo photo = refPhotos.get(i);

            List<Photo> temp = new ArrayList<>();
            temp.add(photo);

            int dist = hamDist(photo.getFinger(), currentPhoto.getFinger());
            if (dist < 20) {
                return true;
            }
        }
        return false;
    }

    public static void calculateFingerPrint(List<Photo> photos) {
        for (Photo p : photos) {
            calculateFingerPrint(p);
        }
    }

    public static Photo calculateFingerPrint(byte[] photoBytes) {
        Photo photo = new Photo();
        // TODO bitmap ispadne null, ovo ne valja
        Bitmap bitmap = BitmapFactory.decodeByteArray(photoBytes, 0, photoBytes.length);

        calcFingerPrint(photo, bitmap);
        return photo;
    }

    private static void calcFingerPrint(Photo photo, Bitmap bitmap) {
        float scale_width, scale_height;
        scale_width = 8.0f / bitmap.getWidth();
        scale_height = 8.0f / bitmap.getHeight();
        Matrix matrix = new Matrix();
        matrix.postScale(scale_width, scale_height);

        Bitmap scaledBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, false);
        photo.setFinger(getFingerPrint(scaledBitmap));

        bitmap.recycle();
        scaledBitmap.recycle();
    }

    public static void calculateFingerPrint(Photo photo) {
        Bitmap bitmap = BitmapFactory.decodeFile(photo.getPath());
        calcFingerPrint(photo, bitmap);
    }

    private static long getFingerPrint(Bitmap bitmap) {
        double[][] grayPixels = getGrayPixels(bitmap);
        double grayAvg = getGrayAvg(grayPixels);
        return getFingerPrint(grayPixels, grayAvg);
    }

    private static long getFingerPrint(double[][] pixels, double avg) {
        int width = pixels[0].length;
        int height = pixels.length;

        byte[] bytes = new byte[height * width];

        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (pixels[i][j] >= avg) {
                    bytes[i * height + j] = 1;
                    stringBuilder.append("1");
                } else {
                    bytes[i * height + j] = 0;
                    stringBuilder.append("0");
                }
            }
        }

        Log.d(TAG, "getFingerPrint: " + stringBuilder.toString());

        long fingerprint1 = 0;
        long fingerprint2 = 0;
        for (int i = 0; i < 64; i++) {
            if (i < 32) {
                fingerprint1 += (bytes[63 - i] << i);
            } else {
                fingerprint2 += (bytes[63 - i] << (i - 31));
            }
        }

        return (fingerprint2 << 32) + fingerprint1;
    }

    private static double getGrayAvg(double[][] pixels) {
        int width = pixels[0].length;
        int height = pixels.length;
        int count = 0;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                count += pixels[i][j];
            }
        }
        return count / (width * height);
    }


    private static double[][] getGrayPixels(Bitmap bitmap) {
        int width = 8;
        int height = 8;
        double[][] pixels = new double[height][width];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                pixels[i][j] = computeGrayValue(bitmap.getPixel(i, j));
            }
        }
        return pixels;
    }

    private static double computeGrayValue(int pixel) {
        int red = (pixel >> 16) & 0xFF;
        int green = (pixel >> 8) & 0xFF;
        int blue = (pixel) & 255;
        return 0.3 * red + 0.59 * green + 0.11 * blue;
    }

    private static int hamDist(long finger1, long finger2) {
        int dist = 0;
        long result = finger1 ^ finger2;
        while (result != 0) {
            ++dist;
            result &= result - 1;
        }
        return dist;
    }
}
