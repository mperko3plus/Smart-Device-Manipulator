package cn.gavinliu.similar.photo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Log;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import cn.gavinliu.similar.photo.entry.Photo;

/**
 * Created by gavin on 2017/3/27.
 */

public class SimilarPhoto {

    private static final String TAG = SimilarPhoto.class.getSimpleName();


    public static MatchResult matches(List<Photo> refPhotos, Photo currentPhoto) {

        for (int i = 0; i < refPhotos.size(); i++) {
            Photo referencePhoto = refPhotos.get(i);

            List<Photo> temp = new ArrayList<>();
            temp.add(referencePhoto);

            int dist = hamDist(referencePhoto.getFinger(), currentPhoto.getFinger());
            if (dist < 7) {
                String deviceUuid = Paths.get(referencePhoto.getPath()).getParent().getFileName().toString();
                return new MatchResult(referencePhoto.getPath(), deviceUuid);
            }
        }
        return null;
    }

    public static void calculateFingerPrint(List<Photo> photos) {
        for (Photo p : photos) {
            calculateFingerPrint(p);
        }
    }

    public static Photo calculateFingerPrint(Bitmap bitmap) {
        Photo photo = new Photo();
        calcFingerPrint(photo, bitmap);
        return photo;
    }

    private static void calcFingerPrint(Photo photo, Bitmap bitmap) {
        float scale_width, scale_height;
        scale_width = 20.0f / bitmap.getWidth();
        scale_height = 12.0f / bitmap.getHeight();
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
        int height = pixels[0].length;
        int width = pixels.length;

        int size = height * width;

        byte[] bytes = new byte[size];

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
        for (int i = 0; i < size; i++) {
            if (i < size / 2) {
                fingerprint1 += (bytes[size - 1 - i] << i);
            } else {
                fingerprint2 += (bytes[size - 1 - i] << (i - (size / 2) - 1));
            }
        }

        return (fingerprint2 << (size / 2)) + fingerprint1;
    }

    private static double getGrayAvg(double[][] pixels) {
        int height = pixels[0].length;
        int width = pixels.length;
        int count = 0;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                count += pixels[i][j];
            }
        }
        return count / (width * height);
    }


    private static double[][] getGrayPixels(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        double[][] pixels = new double[width][height];
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

    public static class MatchResult {
        public final String path;
        public final String deviceUuid;

        public MatchResult(String path, String deviceUuid) {
            this.path = path;
            this.deviceUuid = deviceUuid;
        }
    }
}
