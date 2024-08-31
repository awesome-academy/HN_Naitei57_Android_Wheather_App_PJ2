package com.sun.weather.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.sun.weather.R
import com.sun.weather.ui.MainActivity
import com.sun.weather.ui.setting.SettingFragment.Companion.DEFAULT_FLAG_RES_ID
import com.sun.weather.ui.setting.SettingFragment.Companion.KEY_FLAG_RES_ID
import com.sun.weather.utils.SharedPrefManager

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        SharedPrefManager.init(this)
        SharedPrefManager.putInt(KEY_FLAG_RES_ID, DEFAULT_FLAG_RES_ID)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.splashScreen)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
        )
        Handler(Looper.getMainLooper()).postDelayed(
            {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            },
            SPLASH_DELAY,
        )
    }
    companion object {
        private const val SPLASH_DELAY = 2000L
    }
}