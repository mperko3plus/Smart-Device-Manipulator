package com.example.smartdevicemanipulator;

import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by gavin on 2017/3/27.
 */

public class GroupActivity extends AppCompatActivity {

    private Handler mHandler;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_group);
//        PermissionsUtils.getPermissions(this);
//        final List<Photo> photos;
//        try {
//            photos = PhotoRepository.getPhoto(this);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//        final ListView listView = (ListView) findViewById(R.id.list);
//
//        mHandler = new Handler(getMainLooper());
//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                final List<Group> groups = SimilarPhoto.find(GroupActivity.this, photos);
//
//                mHandler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        listView.setAdapter(new Adapter(groups));
//                    }
//                });
//            }
//        }).start();
//    }
//
//    private class Adapter extends BaseAdapter {
//
//        private List<Group> groups;
//
//        public Adapter(List<Group> groups) {
//            this.groups = groups;
//        }
//
//        @Override
//        public int getCount() {
//            return groups == null ? 0 : groups.size();
//        }
//
//        @Override
//        public Object getItem(int position) {
//            return null;
//        }
//
//        @Override
//        public long getItemId(int position) {
//            return 0;
//        }
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//            if (convertView == null) {
//                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_group, parent, false);
//            }
//
//            TextView name = (TextView) convertView.findViewById(R.id.name);
//            LinearLayout linearLayout = (LinearLayout) convertView.findViewById(R.id.images);
//            linearLayout.removeAllViews();
//
//            name.setText("Group: " + position);
//
//
//            List<Photo> photos = groups.get(position).getPhotos();
//
//            for (Photo p : photos) {
//                ImageView image = new ImageView(parent.getContext());
//                LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(300, 300);
//                linearLayout.addView(image, param);
//
//                Glide.with(GroupActivity.this)
//                        .load(p.getPath())
//                        .centerCrop()
////                        .crossFade()
//                        .into(image);
//            }
//
//
//            return convertView;
//        }
    }


}
