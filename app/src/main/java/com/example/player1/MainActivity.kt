
package com.example.player1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val buttonone: Button = findViewById(R.id.buttonone)
        val buttontwo: Button = findViewById(R.id.buttontwo)

        buttonone.setOnClickListener() {
            Toast.makeText(this, "You clicked buttonone", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MainActivity2::class.java)
            startActivity(intent)
        }
        buttontwo.setOnClickListener() {
            Toast.makeText(this, "You clicked buttonone", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MainActivity3::class.java)
            startActivity(intent)
        }
    }
}
