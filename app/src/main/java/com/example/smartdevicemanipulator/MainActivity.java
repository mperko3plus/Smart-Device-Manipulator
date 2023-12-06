package com.example.smartdevicemanipulator;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartdevicemanipulator.client.SystemWebDto;
import com.example.smartdevicemanipulator.client.V3Client;

import java.util.concurrent.CompletableFuture;

public class MainActivity extends AppCompatActivity {
    private final V3Client v3 = V3Client.v3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CompletableFuture<Boolean> selectedSystem = CompletableFuture.supplyAsync(() -> {
            SystemWebDto systemWebDto = v3.selectSystem();
            return systemWebDto != null;
        });
        try {
            Boolean success = selectedSystem.get();
            if (!success) {
                Log.e("System select exception", "Failed to select system");
            }
        } catch (Exception ex) {
            Log.e("System select exception", ex.getMessage(), ex);
        }

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