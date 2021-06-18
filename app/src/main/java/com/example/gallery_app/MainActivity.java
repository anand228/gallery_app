package com.example.gallery_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    FloatingActionButton add, camera, gallery, share;
    Animation rotateOpen, rotateClose, fromBottom, toBottom;

    boolean isOpen = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        add = findViewById(R.id.add_button);
        camera = findViewById(R.id.camera_button);
        share = findViewById(R.id.share_button);
        gallery = findViewById(R.id.gallery_button);

        rotateOpen = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_open_anim);
        rotateClose = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_close_anim);
        fromBottom = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.from_bottom_anim);
        toBottom = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.to_bottom_anim);

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
    }
}