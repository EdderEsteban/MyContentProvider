package com.ddrssoft.mycontentprovider;

import androidx.appcompat.app.AppCompatActivity;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.ddrssoft.mycontentprovider.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        permisoSMS();

        binding.btnStartService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startMyService();
}
        });

        binding.btnStopService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopMyService();
            }
        });


    }
    private void startMyService() {
        // Iniciar el servicio
        Intent serviceIntent = new Intent(this, Servicio.class);
        startService(serviceIntent);
    }

    private void stopMyService() {
        // Detener el servicio
        Intent serviceIntent = new Intent(this, Servicio.class);
        stopService(serviceIntent);
    }

    private void permisoSMS(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
        && checkSelfPermission(Manifest.permission.READ_SMS)
        != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.READ_SMS}, 1003);
        }
    }
}