package com.example.smartdevicemanipulator;

import android.app.Activity;
import android.graphics.ImageFormat;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Surface;
import android.view.TextureView;
import android.view.WindowManager;
import android.widget.Toast;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;

/**
 * More or less straight out of TextureView's doc.
 * <p>
 * TODO: add options for different display sizes, frame rates, camera selection, etc.
 */
public class LiveCameraWalkActivity extends Activity implements TextureView.SurfaceTextureListener {
    private static final String TAG = "LCA";

    private Camera mCamera;
    private SurfaceTexture mSurfaceTexture;


    private final long IMAGE_ANALYSIS_INTERVAL_MS = 500;

    private final AtomicLong lastAnalysisTimeMs = new AtomicLong(0L);
    private byte[] previewBuffer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextureView textureView = new TextureView(this);
        textureView.setSurfaceTextureListener(this);

        setContentView(textureView);
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
            Toast.makeText(this,
                    "Camera permission is needed to run this application", Toast.LENGTH_LONG).show();
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
}