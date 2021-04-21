package com.example.organizerapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var button = findViewById(R.id.button) as Button
        var button2 = findViewById(R.id.button2) as Button

        button.setOnClickListener {
            // Handler code here.
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent);
        }

        button2.setOnClickListener {
            // Handler code here.
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent);
        }
    }
}