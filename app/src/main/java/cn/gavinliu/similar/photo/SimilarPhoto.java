package cn.gavinliu.similar.photo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

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

            int dist = PHash.getHammingDistance(referencePhoto.getFinger2(), currentPhoto.getFinger2());
            if (dist <= 15) {
                String deviceUuid = Paths.get(referencePhoto.getPath()).getParent().getFileName().toString();
                MatchResult matchResult = new MatchResult(referencePhoto.getPath(), deviceUuid, 100 - dist);
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

    private static void calcFingerPrint(Photo photo, Bitmap bitmap) {
        String hash = PHash.calculateHash(bitmap);
        photo.setFinger2(hash);
    }


    public static void calculateFingerPrint(Photo photo) {
        Bitmap bitmap = BitmapFactory.decodeFile(photo.getPath());
        calcFingerPrint(photo, bitmap);
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
