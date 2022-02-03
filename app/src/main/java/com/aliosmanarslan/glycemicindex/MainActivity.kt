package com.aliosmanarslan.glycemicindex

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.aliosmanarslan.glycemicindex.database.DB
import com.aliosmanarslan.glycemicindex.databinding.ActivityMainBinding
import com.aliosmanarslan.glycemicindex.view.ListCategory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var db: DB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = DB(this)
        db.allFood()
        Handler().postDelayed({
            val intent = Intent(this@MainActivity, ListCategory::class.java)
            startActivity(intent)
            finish()
        }, 2500)
    }
}