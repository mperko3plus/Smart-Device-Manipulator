package cn.gavinliu.similar.photo.util;

import android.content.Context;
import android.provider.MediaStore;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

import cn.gavinliu.similar.photo.entry.Photo;

/**
 * Created by gavin on 2017/3/27.
 */

public class PhotoRepository {

    private static final String TAG = PhotoRepository.class.getSimpleName();

    private static final String[] STORE_IMAGES = {
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DATA,
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.ImageColumns.MIME_TYPE,
            MediaStore.Images.Media.SIZE,
    };

    public static List<Photo> getPhoto(Context context) throws IOException {
//        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
//        ContentResolver contentResolver = context.getContentResolver();
//        String sortOrder = MediaStore.Images.Media.DATE_TAKEN + " desc";
//        Cursor cursor = contentResolver.query(uri, STORE_IMAGES, null, null, sortOrder);
//
//        List<Photo> result = new ArrayList<>();
//
//        while (cursor != null && cursor.moveToNext()) {
//            Photo photo = new Photo();
//
//            photo.setId(cursor.getLong(0));
//            photo.setPath(cursor.getString(1));
//            photo.setName(cursor.getString(2));
////            photo.setMimetype(cursor.getString(3));
//            photo.setSize(cursor.getLong(4));
//
//            result.add(photo);
//        }
//
//        if (cursor != null) cursor.close();
//
//        Log.d(TAG, "getPhoto: size=" + result.size());
        var result = new ArrayList<Photo>();
        Path appImagesRoot = context.getFilesDir().toPath();
        Files.walkFileTree(appImagesRoot, new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Photo photo = new Photo();
                String path = file.toAbsolutePath().toString();

                if (!path.endsWith(".config")) {
                    try {
                        Long.parseLong(file.getFileName().toString());
                    } catch (Exception e) {
                        return FileVisitResult.CONTINUE;
                    }
                    photo.setPath(path);
                    result.add(photo);
                }
                return FileVisitResult.CONTINUE;
            }
        });

        return result;
    }

}
