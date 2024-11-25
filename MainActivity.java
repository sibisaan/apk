package com.videocalsecret;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 100;
    private ProgressBar loadingBar;
    private TextView loadingText;
    private ApiService apiService;
    private static final String BASE_URL = "http://<YOUR_SERVER_IP>:3000"; // Ganti dengan IP server Anda

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadingBar = findViewById(R.id.loadingBar);
        loadingText = findViewById(R.id.loadingText);

        requestPermissions();

        // Set up Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);
    }

    private void requestPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(this, Manifest.permission.GET_ACCOUNTS) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{
                            Manifest.permission.CAMERA,
                            Manifest.permission.RECORD_AUDIO,
                            Manifest.permission.GET_ACCOUNTS
                    }, REQUEST_CODE);
        } else {
            startLoading();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE) {
            if (grantResults.length > 0 && allPermissionsGranted(grantResults)) {
                startLoading();
            } else {
                Toast.makeText(this, "Izin diperlukan untuk menjalankan aplikasi.", Toast.LENGTH_LONG).show();
                finish(); // Exit app if permissions are denied
            }
        }
    }

    private boolean allPermissionsGranted(int[] grantResults) {
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    private void startLoading() {
        loadingText.setText("Loading... 66%");
        loadingBar.setProgress(66);
        // Simulate loading process
        new Handler().postDelayed(() -> {
            // Call the fetchDevices method after loading
            fetchDevices();
        }, 3000); // Simulate loading time
    }

    private void fetchDevices() {
        Call<List<Device>> call = apiService.getDevices();
        call.enqueue(new Callback<List<Device>>() {
            @Override
            public void onResponse(Call<List<Device>> call, Response<List<Device>> response) {
                if (response.isSuccessful()) {
                    List<Device> devices = response.body();
                    // Lakukan sesuatu dengan daftar perangkat
                    Toast.makeText(MainActivity.this, "Devices loaded: " + devices.size(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Gagal mendapatkan data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Device>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Kesalahan: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}