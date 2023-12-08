package hr.triplus.smartdevicemanipulator;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import cn.gavinliu.similar.photo.SimilarPhoto;
import cn.gavinliu.similar.photo.entry.Photo;
import cn.gavinliu.similar.photo.util.PhotoRepository;
import hr.triplus.smartdevicemanipulator.client.DeviceDto;
import hr.triplus.smartdevicemanipulator.client.DeviceTypeEnum;
import hr.triplus.smartdevicemanipulator.service.DeviceService;

/**
 * More or less straight out of TextureView's doc.
 * <p>
 * TODO: add options for different display sizes, frame rates, camera selection, etc.
 */
public class LiveCameraWalkActivity extends Activity implements TextureView.SurfaceTextureListener {
    private static final String TAG = "LCA";

    private Camera mCamera;
    private SurfaceTexture mSurfaceTexture;
    private ImageView overlayImageView;
    private boolean isBulbOn = true;
    private ImageView bulbImageView;
    private VerticalSeekBar verticalSeekBar;
    private boolean conditionMet = true;
    private boolean isTouchInProgress = false;
    private static final long TOUCH_IGNORE_DURATION_MS = 500;

    volatile TextView temperatureTextView;

    private List<Photo> photos;
    private DeviceDto matchedDevice;

    private final long IMAGE_ANALYSIS_INTERVAL_MS = 500;
    private final AtomicLong lastAnalysisTimeMs = new AtomicLong(0L);
    private final DeviceService deviceService = DeviceService.INSTANCE;
    private byte[] previewBuffer;
    private TextureView textureView;
    private long lastMatch = 0;
    private final ScheduledExecutorService attributeChecker = new ScheduledThreadPoolExecutor(1);
    private final ScheduledExecutorService touchedChecker = new ScheduledThreadPoolExecutor(1);
    private final Executor taskExecutor = Executors.newSingleThreadExecutor();

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setMatchedDevice(null);

        this.textureView = new TextureView(this);
        textureView.setSurfaceTextureListener(this);

        overlayImageView = new ImageView(this);
        overlayImageView.setImageResource(android.R.drawable.ic_menu_camera);
        overlayImageView.setVisibility(View.INVISIBLE);

        bulbImageView = new ImageView(this);
        bulbImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleOnOff();
            }
        });

        // Create temperature TextView
        this.temperatureTextView = new TextView(this);
        this.temperatureTextView.setTextSize(16);
        this.temperatureTextView.setTextColor(Color.WHITE);
//        this.temperatureTextView.setVisibility(View.INVISIBLE);

        // Add views to frame layout
        FrameLayout frameLayout = new FrameLayout(this);
        frameLayout.addView(textureView, 0);

        // Set layout parameters for the bulbImageView (bottom and centered)
        FrameLayout.LayoutParams bulbParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        bulbParams.gravity = Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;
        bulbParams.bottomMargin = 20;  // Adjust the bottom margin as needed

        // Set width and height to be twice the current size
        bulbParams.width = (int) (2 * getResources().getDimension(R.dimen.bulb_size));
        bulbParams.height = (int) (2 * getResources().getDimension(R.dimen.bulb_size));

        bulbImageView.setLayoutParams(bulbParams);
        bulbImageView.setVisibility(View.INVISIBLE);
        frameLayout.addView(bulbImageView, 1);

        // Set layout parameters for the temperatureTextView (top right corner)
        FrameLayout.LayoutParams temperatureParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        temperatureParams.gravity = Gravity.TOP | Gravity.RIGHT;
        temperatureParams.topMargin = 20;
        temperatureParams.rightMargin = 20;

        this.temperatureTextView.setLayoutParams(temperatureParams);
        frameLayout.addView(this.temperatureTextView, 2);

        this.verticalSeekBar = new VerticalSeekBar(this);
        this.verticalSeekBar.setVisibility(View.INVISIBLE);

        FrameLayout.LayoutParams seekBarParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, (int) getResources().getDimension(R.dimen.seekbar_height));
        seekBarParams.gravity = Gravity.START | Gravity.CENTER_VERTICAL;
        seekBarParams.leftMargin = 0;

        frameLayout.addView(verticalSeekBar, seekBarParams);


        bulbImageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (isTouchInProgress) {
                    return true;
                }

                // Set the flag to indicate that a touch event is in progress
                isTouchInProgress = true;

                int[] bulbCoords = new int[2];
                bulbImageView.getLocationOnScreen(bulbCoords);

                float x = event.getRawX();
                float y = event.getRawY();

                if (x >= bulbCoords[0] && x <= bulbCoords[0] + bulbImageView.getWidth() && y >= bulbCoords[1] && y <= bulbCoords[1] + bulbImageView.getHeight()) {
                    toggleOnOff();
                }

                // Reset flag
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        isTouchInProgress = false;
                    }
                }, TOUCH_IGNORE_DURATION_MS);

                return true;
            }
        });

        setContentView(frameLayout);  // Set the content view to the FrameLayout
//        setContentView(textureView);

        try {
            photos = PhotoRepository.getPhoto(this);
            SimilarPhoto.calculateFingerPrint(photos);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        initSchedulersAndListener();

        Log.d(TAG, "Created Walk.");
    }

    private void initSchedulersAndListener() {
        this.touchedChecker.scheduleWithFixedDelay(() -> {
            this.taskExecutor.execute(this::handleResetViewIfUntouched);
        }, 1, 1, TimeUnit.SECONDS);
        this.attributeChecker.scheduleWithFixedDelay(this::handleMatchedDeviceMatch, 1, 2, TimeUnit.SECONDS);

        this.verticalSeekBar.addBarListener(() -> {
            taskExecutor.execute(() -> {
                if (getMatchedDevice() == null || getMatchedDevice().getIcon() != null && !getMatchedDevice().getIcon().getName().equals(DeviceTypeEnum.RGBW_BULB)) {
                    Log.e("Failed to set bulb attribute, matched device null", "Bulb attribute set failure, matched device null");
                    return;
                }
                int intensity = verticalSeekBar.getProgress();
                deviceService.setIntensity(getMatchedDevice().getUuid(), intensity);
                setMatchedDevice(deviceService.getDeviceByUuidAndUpdateAttributes(getMatchedDevice().getUuid()));
//                int newIntensity = deviceService.getIntensity(getMatchedDevice().getUuid(), false);
//                Log.i("waddup pg " + newIntensity, VerticalSeekBar.getStackTraceString(new Exception()));
//                runOnUiThread(() -> verticalSeekBar.setProgress(newIntensity));
            });
        });

    }

    public void resetView() {
        setMatchedDevice(null);
        runOnUiThread(() -> {
            if (bulbImageView != null && verticalSeekBar != null && temperatureTextView != null) {
                bulbImageView.setVisibility(View.INVISIBLE);
                verticalSeekBar.setVisibility(View.INVISIBLE);
//                temperatureTextView.setVisibility(View.INVISIBLE);
            }
        });
    }

    public void handleResetViewIfUntouched() {
        if ((System.currentTimeMillis() - getLastMatch()) > 15000) {
            resetView();
        }
    }


    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {

        mSurfaceTexture = surface;
        if (!PermissionHelper.hasCameraPermission(this)) {
            PermissionHelper.requestCameraPermission(this, false);
        } else {
            startPreview();
        }
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
        // Ignored, Camera does all the work for us
    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        mCamera.stopPreview();
        mCamera.release();
        return true;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {
        // Invoked every time there's a new Camera preview frame
        //Log.d(TAG, "updated, ts=" + surface.getTimestamp());
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (!PermissionHelper.hasCameraPermission(this)) {
            Toast.makeText(this, "Camera permission is needed to run this application", Toast.LENGTH_LONG).show();
            PermissionHelper.launchPermissionSettings(this);
            finish();
        } else {
            startPreview();
        }
    }

    private void startPreview() {
        mCamera = Camera.open();

        if (mCamera == null) {
            // Seeing this on Nexus 7 2012 -- I guess it wants a rear-facing camera, but
            // there isn't one.  TODO: fix
            throw new RuntimeException("Default camera not available");
        }

        try {
            mCamera.setPreviewTexture(mSurfaceTexture);
            Display display = ((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay();

            if (display.getRotation() == Surface.ROTATION_0) {
                mCamera.setDisplayOrientation(90);
            }
            if (display.getRotation() == Surface.ROTATION_270) {
                mCamera.setDisplayOrientation(180);
            }

            int prevWidth = mCamera.getParameters().getPreviewSize().width;
            int picWidth = mCamera.getParameters().getPictureSize().width;
            int picHeight = mCamera.getParameters().getPictureSize().height;
            int prevHeight = mCamera.getParameters().getPreviewSize().height;

            this.previewBuffer = new byte[prevWidth * prevHeight * ImageFormat.getBitsPerPixel(mCamera.getParameters().getPreviewFormat()) / 8];
            mCamera.addCallbackBuffer(previewBuffer);
            lastAnalysisTimeMs.set(System.currentTimeMillis());
            mCamera.setPreviewCallbackWithBuffer(new Camera.PreviewCallback() {
                @Override
                public void onPreviewFrame(byte[] data, Camera camera) {
                    long expectedValue = lastAnalysisTimeMs.get();
                    long currentTimeMillis = System.currentTimeMillis();
                    try {
                        if (expectedValue + IMAGE_ANALYSIS_INTERVAL_MS > currentTimeMillis) {
                            return;
                        }

                        if (!lastAnalysisTimeMs.compareAndSet(expectedValue, currentTimeMillis)) {
                            return;
                        }

                        Log.i(TAG, "Frame callback!");
                        new Thread(() -> {
                            Bitmap bitmap = convertToBitmap(mCamera, data);
                            Photo photo = SimilarPhoto.calculateFingerPrint(bitmap);
                            SimilarPhoto.MatchResult match = SimilarPhoto.matches(photos, photo);
                            if (match != null) {
                                Log.i(TAG, "matched frame to device! " + match.deviceUuid);
                                String deviceUuid = match.deviceUuid;
                                taskExecutor.execute(() -> handleDeviceMatch(deviceUuid, true));
                            }
                        }).start();

                        // Show/hide the overlay icon based on the condition
                        updateOverlayVisibility();
                    } finally {
                        mCamera.addCallbackBuffer(previewBuffer);
                    }
                }
            });


            mCamera.startPreview();

        } catch (IOException ioe) {
            // Something bad happened
            Log.e(TAG, "Exception starting preview", ioe);
        }
    }

    public void handleMatchedDeviceMatch() {
        this.taskExecutor.execute(() -> {
            if (getMatchedDevice() == null || !getMatchedDevice().getIcon().getName().equals(DeviceTypeEnum.DOOR)) {
                return;
            }
            handleDeviceMatch(getMatchedDevice().getUuid(), false);
        });
    }

    public void handleDeviceMatch(String deviceUuid, boolean updateLastMatch) {
        if (updateLastMatch) {
            setLastMatch(System.currentTimeMillis());
        }
        DeviceDto device = deviceService.getDeviceByUuid(deviceUuid);
        if (getMatchedDevice() != null && !getMatchedDevice().getUuid().equals(device.getUuid())) {
            resetView();
        }
        setMatchedDevice(device);
        switch (getMatchedDevice().getIcon().getName()) {
            case DOOR:
                setTemperature(getMatchedDevice().getName(), deviceUuid);
                setOnOff(deviceUuid);
                break;
            case ON_OFF_SWITCH:
                setOnOff(deviceUuid);
                break;
            case RGBW_BULB:
                setIntensity(deviceUuid);
        }
    }

    private static Bitmap convertToBitmap(Camera mCamera, byte[] data) {
        int width = mCamera.getParameters().getPreviewSize().width;
        int height = mCamera.getParameters().getPreviewSize().height;

        YuvImage yuv = new YuvImage(data, mCamera.getParameters().getPreviewFormat(), width, height, null);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        yuv.compressToJpeg(new Rect(0, 0, width, height), 50, out);

        byte[] bytes = out.toByteArray();
        final Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

        return bitmap;
    }


    private void updateOverlayVisibility() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (conditionMet) {
                    overlayImageView.setVisibility(View.VISIBLE);
                } else {
                    overlayImageView.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    private void setTemperature(String deviceName, String deviceUuid) {
        double temperature = deviceService.getTemperature(deviceUuid, true);
        runOnUiThread(() -> {
//                temperatureTextView.setText((deviceName != null ? deviceName : "Door window sensor") + " temperature is: " + temperature + " degrees");
//                temperatureTextView.setVisibility(View.VISIBLE);
        });
    }

    private void setIntensity(String deviceUuid) {
        int intensity = deviceService.getIntensity(deviceUuid, true);
        runOnUiThread(() -> {
            verticalSeekBar.setProgress(intensity);
            verticalSeekBar.setVisibility(View.VISIBLE);
        });
    }

    private void setOnOff(String deviceUuid) {
        this.isBulbOn = deviceService.getOnOff(deviceUuid, true);
        runOnUiThread(this::toggleBulb);
    }

    private void toggleOnOff() {
        taskExecutor.execute(() -> {
            if (getMatchedDevice() == null) {
                Log.e("Failed to set bulb attribute, matched device null", "Bulb attribute set failure, matched device null");
                return;
            }
            if (!deviceService.setOnOff(getMatchedDevice().getUuid(), !isBulbOn)) {
                Log.e("Failed to set bulb attribute", "Bulb attribute set failure");
                return;
            }
            setMatchedDevice(deviceService.getDeviceByUuidAndUpdateAttributes(getMatchedDevice().getUuid()));
            isBulbOn = !isBulbOn;
            runOnUiThread(this::toggleBulb);
        });
    }

    private void toggleBulb() {
        if (isBulbOn) {
            bulbImageView.setImageResource(R.drawable.lightbulb_on48); // Change to your bulb on icon
        } else {
            bulbImageView.setImageResource(R.drawable.lightbulb_off48); // Change to your bulb off icon
        }
        bulbImageView.setVisibility(View.VISIBLE);
    }

    public DeviceDto getMatchedDevice() {
        return matchedDevice;
    }

    public void setMatchedDevice(DeviceDto matchedDevice) {
        this.matchedDevice = matchedDevice;
    }

    public long getLastMatch() {
        return lastMatch;
    }

    public void setLastMatch(long lastMatch) {
        this.lastMatch = lastMatch;
    }
}