package cn.gavinliu.similar.photo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.appcompat.view.menu.MenuBuilder;

import com.example.smartdevicemanipulator.phash.PHash;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import cn.gavinliu.similar.photo.entry.Photo;

/**
 * Created by gavin on 2017/3/27.
 */

public class SimilarPhoto {

    private static final String TAG = SimilarPhoto.class.getSimpleName();


    public static MatchResult matches(List<Photo> refPhotos, Photo currentPhoto) {

        List<MatchResult> results = new LinkedList<>();
        for (int i = 0; i < refPhotos.size(); i++) {
            Photo referencePhoto = refPhotos.get(i);

            List<Photo> temp = new ArrayList<>();
            temp.add(referencePhoto);

//            int dist = hamDist(referencePhoto.getFinger(), currentPhoto.getFinger());
            int dist = PHash.getHammingDistance(referencePhoto.getFinger2(), currentPhoto.getFinger2());
            if (dist <= 15) {
                String deviceUuid = Paths.get(referencePhoto.getPath()).getParent().getFileName().toString();
                MatchResult matchResult = new MatchResult(referencePhoto.getPath(), deviceUuid, 100-dist);
                results.add(matchResult);
            }
        }
        return results.stream().max(Comparator.comparing(a -> a.similarity)).orElse(null);
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

//    private static void calcFingerPrint(Photo photo, Bitmap bitmap) {
//        float scale_width, scale_height;
//        scale_width = 8.0f / bitmap.getWidth();
//        scale_height = 8.0f / bitmap.getHeight();
//        Matrix matrix = new Matrix();
//        matrix.postScale(scale_width, scale_height);
//
//        Bitmap scaledBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, false);
//        photo.setFinger(getFingerPrint(scaledBitmap));
//
//        bitmap.recycle();
//        scaledBitmap.recycle();
//    }

    private static void calcFingerPrint(Photo photo, Bitmap bitmap) {
        String hash = PHash.calculateHash(bitmap);
        photo.setFinger2(hash);
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

    public static class MatchResult {
        public final String path;
        public final String deviceUuid;

        public final int similarity;

        public MatchResult(String path, String deviceUuid, int similarity) {
            this.path = path;
            this.deviceUuid = deviceUuid;
            this.similarity = similarity;
        }
    }
}
