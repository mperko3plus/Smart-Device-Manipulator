package com.example.smartdevicemanipulator;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartdevicemanipulator.client.DeviceDto;
import com.example.smartdevicemanipulator.client.V3Client;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class DeviceListActivity extends AppCompatActivity {
    private static final int CAMERA_REQUEST_CODE = 1001; // Use any unique integer value
    private final V3Client v3 = V3Client.v3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_list);

        ArrayList<String> deviceOptions = getDeviceOptions();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, deviceOptions);

        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedDevice = deviceOptions.get(position);

                // Pass the selected device information to the CameraActivity
                Intent intent = new Intent(DeviceListActivity.this, CameraActivity.class);
                intent.putExtra("SELECTED_DEVICE", selectedDevice);
                startActivityForResult(intent, CAMERA_REQUEST_CODE);
            }
        });
    }


    private ArrayList<String> getDeviceOptions() {
        CompletableFuture<ArrayList<String>> future = CompletableFuture.supplyAsync(() -> {
            return v3.getDevices().stream().filter(d -> d.getName() != null && !d.getName().trim().isEmpty()).map(DeviceDto::getName).collect(Collectors.toCollection(ArrayList::new));
        });
        try {
            return future.get();
        } catch (Exception ex) {
            return new ArrayList<>();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                String selectedDevice = data.getStringExtra("SELECTED_DEVICE");
                Log.println(Log.ASSERT, "1", "je");
                @SuppressLint("DefaultLocale") String imageName = String.format("%s_%d", selectedDevice, System.currentTimeMillis());
                Path appImagesRoot = getApplicationContext().getFilesDir().toPath();
                Path imagePath = appImagesRoot.resolve(imageName);

                Path imagesConfig = appImagesRoot.resolve(selectedDevice + ".config");
                if (!Files.exists(imagesConfig)) {
                    try {
                        Files.createFile(imagesConfig);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }

                try {
                    Files.write(imagesConfig, List.of(imagePath.toString(), System.lineSeparator()), StandardCharsets.UTF_8, StandardOpenOption.APPEND);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                try (FileOutputStream fileOutputStream = new FileOutputStream(imagePath.toFile())) {
                    ((Bitmap) data.getExtras().get("BITMAP")).compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
