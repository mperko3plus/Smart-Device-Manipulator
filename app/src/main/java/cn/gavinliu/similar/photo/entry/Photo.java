package cn.gavinliu.similar.photo.entry;

/**
 * Created by gavin on 2017/3/27.
 */

public class Photo {

    private long id;

    private String path;

    private String name;

    private long finger;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getFinger() {
        return finger;
    }

    public void setFinger(long finger) {
        this.finger = finger;
    }
}
