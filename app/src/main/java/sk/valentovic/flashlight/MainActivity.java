package sk.valentovic.flashlight;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ImageButton switchOnOff;
    CameraManager cameraManager;
    boolean state;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        switchOnOff = (ImageButton) findViewById(R.id.PowerButton);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            cameraManager = (CameraManager) getSystemService(CAMERA_SERVICE);
        }

        if(getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)){
            if(getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)){
                switchOnOff.setEnabled(true);
            }else{
                Toast.makeText(MainActivity.this, "This device has no flash", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(MainActivity.this, "This device has no camera", Toast.LENGTH_SHORT).show();
        }

        switchOnOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!state){
                    switchOnOff.setImageResource(R.drawable.power_off);
                    String cameraID = null;
                    try {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            cameraID = cameraManager.getCameraIdList()[0];
                        }
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            cameraManager.setTorchMode(cameraID, true);
                            state = true;
                        }
                    } catch (CameraAccessException e) {
                    }

                } else {
                    switchOnOff.setImageResource(R.drawable.power_on);
                    String cameraID = null;
                    try {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            cameraID = cameraManager.getCameraIdList()[0];
                        }
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            cameraManager.setTorchMode(cameraID, false);
                            state = false;
                        }
                    } catch (CameraAccessException e) {
                    }

                }
            }
        });

    }
}