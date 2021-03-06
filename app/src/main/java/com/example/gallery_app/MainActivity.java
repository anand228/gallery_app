package com.example.gallery_app;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    FloatingActionButton add, camera, gallery, share;
    Animation rotateOpen, rotateClose, fromBottom, toBottom;
    TextView text1, text2;
    GridView grid;

    boolean isOpen = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("GALLERY");
        setView();
        loadAnimation();
        onClickListeners();
    }

    private void setView() {
        add = findViewById(R.id.add_button);
        camera = findViewById(R.id.camera_button);
        share = findViewById(R.id.share_button);
        gallery = findViewById(R.id.gallery_button);
        text1 = findViewById(R.id.page1text1);
        text2 = findViewById(R.id.page1text2);
        grid = (GridView)findViewById(R.id.grid_image);
        grid.setAdapter(new GridAdapter(this));
    }

    private void loadAnimation() {
        rotateOpen = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_open_anim);
        rotateClose = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_close_anim);
        fromBottom = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.from_bottom_anim);
        toBottom = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.to_bottom_anim);
    }

    private void onClickListeners() {
        // add button animation and click function
        ////////////
        //////////
        add.setOnClickListener(view -> {
            if (isOpen) {
                camera.startAnimation(toBottom);
                share.startAnimation(toBottom);
                gallery.startAnimation(toBottom);
                add.startAnimation(rotateClose);

                share.setClickable(false);
                gallery.setClickable(false);
                camera.setClickable(false);
                isOpen = false;
            }
            else{
                camera.startAnimation(fromBottom);
                share.startAnimation(fromBottom);
                gallery.startAnimation(fromBottom);
                add.startAnimation(rotateOpen);

                share.setClickable(true);
                gallery.setClickable(true);
                camera.setClickable(true);

                isOpen = true;

            }
        });

        // camera button permission camera external storage
        ////////////
        //////////
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dexter.withContext(MainActivity.this)
                        .withPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new MultiplePermissionsListener() {
                            @Override
                            public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {

                                if(multiplePermissionsReport.areAllPermissionsGranted()){
                                    Toast.makeText(MainActivity.this, "all permissions are granted", Toast.LENGTH_SHORT).show();
                                    openCamera();
                                    text1.setVisibility(View.GONE);
                                    text2.setVisibility(View.GONE);
                                }
                                else Toast.makeText(MainActivity.this, "all permissions are not granted", Toast.LENGTH_SHORT).show();

                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                                    permissionToken.continuePermissionRequest();
                            }
                        }).check();
            }
        });

    }

    private void openCamera() {
        Intent cam = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivity(cam);
    }

}