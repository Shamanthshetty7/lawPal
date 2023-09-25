package com.example.matrix;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Flash animation
        blinkPage(2000);

        // Delay for 2 seconds
        int delayMillis = 2000;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Start next activity
                Intent intent = new Intent(MainActivity.this, SignupActivity.class);
                startActivity(intent);
                finish();
            }
        }, delayMillis);
    }

    private void blinkPage(int duration) {
        Animation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(duration);
        anim.setStartOffset(20);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);
        View view = findViewById(android.R.id.content);
        view.startAnimation(anim);
    }
}


