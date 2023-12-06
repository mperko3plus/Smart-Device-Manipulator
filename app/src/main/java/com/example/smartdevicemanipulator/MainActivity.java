package com.example.smartdevicemanipulator;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartdevicemanipulator.client.SystemWebDto;
import com.example.smartdevicemanipulator.client.V3Client;
import com.example.smartdevicemanipulator.service.DeviceService;

import java.util.concurrent.CompletableFuture;

public class MainActivity extends AppCompatActivity {
    private final DeviceService deviceService = new DeviceService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        deviceService.selectSystem("c3ebc59c-a79a-497f-a54a-5f5db7e0143e");

        // Find buttons by their IDs
        Button button1 = findViewById(R.id.button1);
        Button button2 = findViewById(R.id.button2);

        // Set click listeners for the buttons
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle button1 click
                Intent intent = new Intent(MainActivity.this, DeviceListActivity.class);
                startActivity(intent);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LiveCameraWalkActivity.class);
                startActivity(intent);
            }
        });
    }
}