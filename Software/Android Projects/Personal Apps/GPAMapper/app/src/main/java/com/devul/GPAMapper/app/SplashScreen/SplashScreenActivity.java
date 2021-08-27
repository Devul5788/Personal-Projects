package com.devul.GPAMapper.app.SplashScreen;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.devul.GPAMapper.app.LoginAndRegistration.UserLogin;
import com.devul.GPAMapper.app.R;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreenActivity extends AppCompatActivity {

    Animation fromBottom, fromtop;
    ImageView imageView;

    TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash_screen);

        fromBottom = AnimationUtils.loadAnimation(this, R.anim.frombottom);
        fromtop = AnimationUtils.loadAnimation(this, R.anim.fromtop);
        imageView = findViewById(R.id.imageView);
        txt = findViewById(R.id.textView3);
        imageView.setAnimation(fromtop);
        txt.setAnimation(fromBottom);

        LogoLauncher logoLauncher = new LogoLauncher();
        logoLauncher.start();
    }

    private class LogoLauncher extends Thread {
        public void run() {
            try {
                sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Intent intent = new Intent(SplashScreenActivity.this, UserLogin.class);
            startActivity(intent);
            SplashScreenActivity.this.finish();
        }
    }
}
